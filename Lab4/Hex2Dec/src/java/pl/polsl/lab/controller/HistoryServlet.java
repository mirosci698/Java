// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.view.View;

/**
 * Class representing history servlet. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class HistoryServlet extends HttpServlet {

    /**
     * Counter of checking history.
     */
    private int counter = 0;
    
    /**
     * Field for view of application.
     */
    private View view;
    
    /**
     * Method initializing servlet with model and view.
     *
     * @param config servlet configuration
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        view = new View();
    }
    
    /**
     * Getting and setting correct info to cookie.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private String getCookieInfo(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String number = "";
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("checkNumber")) {
                    number = cookie.getValue();
                    cookie.setValue("You checked history " + String.valueOf(++counter) + " times.");
                    response.addCookie(cookie);
                    break;
                }
        if (number.length() == 0)
        {
            number = "There was no checking of the history.";
            String newValue = "You checked history " + String.valueOf(++counter) + " times.";
            Cookie newCookie = new Cookie("checkNumber", newValue);
            response.addCookie(newCookie);
        }  
        return number;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String information;
        HttpSession session = request.getSession(true);
        Hashtable<Integer, String> historyTable = (Hashtable<Integer, String>)session.getAttribute("history");
        if (historyTable == null)
            information = "No history!";
        else {
            information = historyTable.toString();
            information = information.replace(",", "<br>");
            information = information.replace('{', ' ');
            information = information.replace('}', ' ');          
        }      
        String number = getCookieInfo(request, response); 
        try (PrintWriter out = response.getWriter()) {
            view.setOutput(out);
            view.printResultInfo(information + "<br>" + number);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "History Servlet";
    }// </editor-fold>

}
