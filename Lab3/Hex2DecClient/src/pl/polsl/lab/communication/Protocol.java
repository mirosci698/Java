// definition of the package in which class is placed
package pl.polsl.lab.communication;

import java.io.*;
import pl.polsl.lab.exception.*;

/**
 * Class representing client side of protocol. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Protocol {
    
    /**
     * Constant representing number of separator of requests.
     */
    private final String SEPARATOR = ":";
    
    /**
     * Constant representing code of correct request.
     */
    private final int CODE_OK = 0;
    
    /**
     * Constant representing code of wrong start of protocol.
     */
    private final int CODE_WRONG_HELLO = 1;
    
    /**
     * Constant representing code of wrong type of input.
     */
    private final int CODE_WRONG_TYPE = 2;
    
    /**
     * Constant representing code of wrong number.
     */
    private final int CODE_WRONG_NUMBER = 3;
    
    /**
     * Constant representing code of wrong conversion.
     */
    private final int CODE_WRONG_CONVERSION = 4;
    
    /**
     * Constant representing code of wrong type conversion.
     */
    private final int CODE_WRONG_HEX = 5;
    
    /**
     * Constant representing code of wrong order of requests.
     */
    private final int CODE_WRONG_ORDER = 6;
    
    /**
     * Field representing sended mode of input.
     */
    private int sentMode;
    
    /**
     * Field representing sended decimal number.
     */
    private double sentDecNumber;
    
    /**
     * Field representing sended hexadecimal number.
     */
    private String sentHexNumber;
    
    /**
     * Field representing sended mode of conversion.
     */
    private int sentConversion; 
    
    /**
     * Field representing string representation of result.
     */
    private String result;
    
    /**
     * Field representing buffered input character stream.
     */
    private BufferedReader input;
    
    /**
     * Field representing formatted output character stream.
     */
    private PrintWriter output;
    
    /**
     * Constructor for assigning streams from client.
     * 
     * @param in input BufferedReader
     * @param out output PrintWriter
     */
    public Protocol(BufferedReader in, PrintWriter out) {
        input = in;
        output = out;
    }
    
    /**
     * Method for transferring information to the server.
     * 
     * @param input type of input (hex or dec)
     * @param number number to convert (string representation)
     * @param mode type of conversion (Hex2Dec od Dec2Hex)
     * @return information about success or fail
     */
    public boolean protocolTransfer(int input, String number, int mode) throws NumberFormatException,
            InvalidConversionException, InvalidHexNumberException {
        if (input == 1) { //correct setting according to input
            double decNumber = Double.parseDouble(number);
            sentDecNumber = decNumber;
        } else {
            sentHexNumber = number;
        }
        sentMode = input;
        sentConversion = mode;
        try { //correct order of requests
            hello();
            setType();
            setNumber();
            convert();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Method for first stage of protocol - start.
     * 
     * @throws IOException in case or error in protocol
     */
    public void hello() throws IOException {
        output.println("HELLO");
        String received = input.readLine().toUpperCase();
        if (!received.equals(CODE_OK + " - " + "HELLO"))
               throw new IOException(received);
    }
    
    /**
     * Method for second stage of protocol - setting type.
     * 
     * @throws IOException in case or error in protocol
     */
    public void setType() throws IOException {
        output.println("SET_TYPE:" + sentMode);
        String received = input.readLine().toUpperCase();
        if (!received.equals(CODE_OK + " - " + "SET_TYPE:" + sentMode))
            throw new IOException(received);
    }
    
    /**
     * Method for second stage of protocol - setting number.
     * 
     * @throws IOException in case or error in protocol
     */
    public void setNumber() throws IOException {
        if (sentMode == 0)
            output.println("SET_NUMBER:" + sentHexNumber);
        else
            output.println("SET_NUMBER:" + sentDecNumber);
        String received = input.readLine().toUpperCase();
        boolean firstCondition;
        if (sentHexNumber != null)
            firstCondition = !(received.equals(CODE_OK + " - " + "SET_NUMBER:" + sentHexNumber.toUpperCase()) && sentMode == 0);
        else
            firstCondition = !(received.equals(CODE_OK + " - " + "SET_NUMBER:" + sentHexNumber) && sentMode == 0);
        boolean secondCondition = !(received.equals(CODE_OK + " - " + "SET_NUMBER:" + sentDecNumber) && sentMode == 1);
        if (firstCondition && secondCondition)
            throw new IOException(received);
    }
    
    /**
     * Method for second stage of protocol - setting type of conversion.
     * 
     * @throws IOException in case or error in protocol
     */
    public void convert() throws IOException, InvalidConversionException, InvalidHexNumberException {
        output.println("CONVERT:" + sentConversion);
        String received = input.readLine().toUpperCase();
        if (!received.startsWith("" + CODE_OK))
        {
            if (received.startsWith("ERROR:" + "Conversion")) //exception on server side
                if (sentMode == 0 && sentConversion == 0)
                    throw new InvalidConversionException(ConversionType.HEX_2_HEX);
                else if (sentMode == 0 && sentConversion == 0)
                    throw new InvalidConversionException(ConversionType.DEC_2_DEC);
                    //exception on server side
            else if (received.startsWith("ERROR:" + "Invalid hex number"))
                throw new InvalidHexNumberException("Invalid hex number");
            else //other error
                throw new IOException(received);
        } else { //RESPONSE on first element
            String response = input.readLine().toUpperCase();
            String[] splittedResponse = response.split(":");
            try {
                result = splittedResponse[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IOException();
            }
        }
    }
    
    /**
     * Getter for result.
     * 
     * @return string with result
     */
    public String getResult() {
        return result;
    }
}
