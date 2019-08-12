// definition of the package in which class is placed
package pl.polsl.lab.view;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class representing view for console application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class View {
    
    /**
    * Field for output.
    */
    private PrintWriter out;
    
    /**
    * Non-argument constructor.
    */
    public View() {
        
    }
    
    /**
    * Method for setting output writer.
    * 
    * @param output writer for output
    */
    public void setOutput(PrintWriter output) {
        out = output;
    }
    
    /**
    * Method for printing warnings.
    * 
    * @param warning string with warning info
    */
    public void printWarningInfo(String warning) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Warning!</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Warning! " + warning + "</h2>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
    * Method for printing result.
    * 
    * @param information information to print
    */
    public void printResultInfo(String information) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Information</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>INFO: " + information + "</h2>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
    * Method for printing decimal result.
    * 
    * @param result double with result
    */
    @Deprecated
    public void printDecimalResult(double result) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Result</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Hex2Dec conversion result: " + result + "</h2>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
    * Method for printing hexadecimal result.
    * 
    * @param result string with result
    */
    @Deprecated
    public void printHexadecimalResult(String result) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Result</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Dec2Hex conversion result: " + result + "</h2>");
        out.println("</body>");
        out.println("</html>");
    }
    
}