// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
 * @version 1.1
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
     * Field for connection with database.
     */
    private Connection connection;
    
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
     * Method which creates table in case of lack.
     * 
     * @throws SQLException in case of DB error
     */
    private void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        try (ResultSet rs = statement.executeQuery("SELECT * FROM Calculations")) {
        } catch (SQLException sqle) {
            if (sqle.getMessage().equals("Tabela/widok 'CALCULATIONS' nie istnieje."))
            {
                statement.executeUpdate("create table CALCULATIONS " +
                    "(" +
                    "	ID INTEGER not null primary key, " +
                    "	HEX VARCHAR(100) not null, " +
                    "	NORMAL REAL not null, " +
                    "	ERROR VARCHAR(100) not null, " +
                    "	TYPE VARCHAR(100) not null " +
                    ")");
            }
        }
    }
    
    /**
     * Method for creating connection with DB in case of lack.
     */
    private void createConnection() {
        String driver = getInitParameter("driver");
        try {
            // loading the JDBC driver
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
        String url = getInitParameter("url");
        String login = getInitParameter("login");
        String password = getInitParameter("password");
        // make a connection to DB
        try {
            connection = DriverManager.getConnection(url, login, password);
            createTable();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
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
     * Method for getting all values from DB.
     * 
     * @return String with values in HTML 
     * @throws SQLException in case of DB error
     */
    private String getValues() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Calculations");
        String responseToClient = "";
        // Getting values from DB
        responseToClient = String.format("|%-3s|%-10s|%-10s|%-20s|%-7s|<br>", "ID", "hex", "dec", "error", "type");
        responseToClient += "-----------------------------------<br>";
        while (rs.next()) {
        responseToClient += String.format("|%-3s", rs.getInt("ID"));
        responseToClient += String.format("|%-10s", rs.getString("hex"));
        responseToClient += String.format("|%-10s", rs.getDouble("normal"));
        responseToClient += String.format("|%-20s", rs.getString("error"));
        responseToClient += String.format("|%-7s|<br>", rs.getString("type"));
        }
        responseToClient += "-----------------------------------<br>";
        rs.close();
        return responseToClient;
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
        connection = (Connection)session.getAttribute("connection");
        if (connection == null) {
            createConnection();
            session.setAttribute("connection", connection);
        }
        String databaseInfo = null;
        String exceptionMessage = null;
        try {
            databaseInfo = getValues();
        } catch (SQLException sqle) {
            exceptionMessage = sqle.getMessage();
        }
        try (PrintWriter out = response.getWriter()) {
            view.setOutput(out);
            view.printResultInfo(information + "<br>" + number + "<br>");
            if (exceptionMessage != null)
                view.printWarningInfo(exceptionMessage);
            else
                view.printResultInfo("DB values:" + "<br>" + databaseInfo);
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
