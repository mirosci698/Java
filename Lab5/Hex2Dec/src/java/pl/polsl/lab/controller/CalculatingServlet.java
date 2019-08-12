// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.model.*;
import pl.polsl.lab.view.View;

/**
 * Class representing calculating servlet. 
 * 
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class CalculatingServlet extends HttpServlet {

    /**
     * Constant representing number of recquired arguments (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int NUMBER_OF_ARGS = 3;
    
    /**
     * Field for view of application.
     */
    private View view;
    
    /**
     * Field for required model.
     */
    private Converter model;
    
    /**
     * Field for connection with database.
     */
    private Connection connection;
    
    /**
     * Field for cunter of ID's from DB.
     */
    private int counterId = 0;
    
    /**
     * Method Method which creates table in case of lack.
     * 
     * @throws SQLException in case of DB error
     */
    private void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        try (ResultSet rs = statement.executeQuery("SELECT * FROM Calculations")) {
        } catch (SQLException sqle) {
            if (sqle.getMessage().equals("Tabela/widok 'CALCULATIONS' nie istnieje."))
            { //in polish because of installed version of netbeans
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
     * Method for setting model.
     * 
     * @param mode information about type of conversion
     * @param number string with number to convert 
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void setModel (int mode, String number) throws NumberFormatException {
        switch(mode) {
            case 0:
                model.setHexNumber(number);
                break;
            case 1:
                model.setDecNumber(Double.parseDouble(number));
                break;
            default:
                //for other integer than 0 or 1
                throw new NumberFormatException();
        }
    }
    
    /**
     * Method for getting input after getting wrong program parameters.
     * 
     * @param mode information about type of conversion
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void setConvertion (int mode) throws NumberFormatException, 
            InvalidConversionException, InvalidHexNumberException {
        switch(mode) {
            case 0:
                model.calculateDec2Hex();
                break;
            case 1:
                model.calculateHex2Dec();
                break;
            default:
                //for other integer than 0 or 1
                throw new NumberFormatException();
        }
    }
    
    /**
     * Method for getting input and setting variables.
     * 
     * @return info about success of operations (no exceptions)
     * @throws InvalidConversionException in case of wrong type of conversion
     */
    private boolean inputAndProcessControl(String args[]) throws InvalidConversionException,
            InvalidHexNumberException{
        int inputType = Integer.parseInt(args[0]); 
        this.setModel(inputType, args[1]);
        int typeOfConvertion = Integer.parseInt(args[2]);
        if (typeOfConvertion != 0 && typeOfConvertion != 1) 
            throw new NumberFormatException();
        //try to convert
        this.setConvertion(typeOfConvertion);
        return true;
    }
    
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
        model = new Converter(0.0); //starter value of created model
    }
    
    /**
     * Method for getting arguments properly.
     *
     * @param request actual request
     */
    private String[] getArguments(HttpServletRequest request) {
        ArrayList<String> loadedArguments = new ArrayList<String>();
        String temporary = request.getParameter("type"); //getting from proper parameters
        if (!temporary.isEmpty())
            loadedArguments.add(temporary);
        temporary = request.getParameter("model");
        if (!temporary.isEmpty())
            loadedArguments.add(temporary);
        temporary = request.getParameter("conversion");
        if (!temporary.isEmpty())
            loadedArguments.add(temporary);
        String[] result = new String[loadedArguments.size()];
        result = loadedArguments.toArray(result);
        return result;
    }
    
    /**
     * Method for inserting data into DB.
     * 
     * @param errorMessage String with error message
     * @param typeMessage String with type message
     * @throws SQLException in case of DB error
     */
    private void insertValues(String errorMessage, String typeMessage) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Calculations VALUES( ?, ?, ?, ?, ? )" );
        pstm.setInt( 1, ++counterId );
        String temporary = model.getHexNumber();
        if (temporary == null) //don't want null's in DB
            temporary = "<empty>";
        pstm.setString( 2, temporary );
        Double temporaryDouble = model.getDecNumber();
        if (temporaryDouble == null)
            temporaryDouble = 0.0;//don't want null's in DB - indicates no calculation
        pstm.setDouble( 3, temporaryDouble );
        pstm.setString( 4, errorMessage );
        pstm.setString( 5, typeMessage );
        pstm.execute();
    }
    
    /**
     * Method for getting last Id from DB.
     * 
     * @return last Id from DB
     * @throws SQLException in case of DB error
     */
    private int getId() throws SQLException {
        int result = 0;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Calculations ORDER BY ID DESC");
        if (rs.next()) //if something was in DB
            result = rs.getInt("ID");
        rs.close();
        return result;
    }
    
    /**
     * Method for inserting data into DB.
     * 
     * @param sessionFromRequest session for setting history
     * @param arguments array of strings with arguments
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void interaction(HttpSession sessionFromRequest, String[] arguments, HttpServletResponse response)
                throws IOException {
        String error = "<None>";
        PrintWriter out = null;
        try {
            out = response.getWriter();
            view.setOutput(out);
            if (inputAndProcessControl(arguments))
                view.printResultInfo(model.toString());
            sessionFromRequest.setAttribute("history", model.getHistory());
        }
        catch(ArrayIndexOutOfBoundsException e) {
            view.printWarningInfo("Wrong number of parameters.");
            error = "Wrong number of parameters.";
        }
        catch(NumberFormatException | InvalidConversionException | InvalidHexNumberException e) {
            view.printWarningInfo(e.getMessage());
            error = e.getMessage();
        }
        finally {
            if (error == null || error.equals("null"))
                error = "NullPointerException"; //correct name of error
            try {
                String messageOfType = "" + arguments[0] + arguments[2];
                insertValues(error, messageOfType); 
            } catch (ArrayIndexOutOfBoundsException | NullPointerException | SQLException e) {
                view.printWarningInfo(e.getMessage());
            }
            if (out != null)
                out.close();
        }
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
        String[] arguments = getArguments(request);
        //check if there was success of parsing and operation, else provide user interaction
        HttpSession session = request.getSession(true);
        connection = (Connection)session.getAttribute("connection");
        if (connection == null) {
            createConnection();
            session.setAttribute("connection", connection);
        }
        try {
            counterId = getId();
        } catch (SQLException sqle) {
            view.printWarningInfo(sqle.getMessage());
        }
        interaction(session, arguments, response);
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
        return "Servlet for converting";
    }// </editor-fold>

}
