// definition of the package in which class is placed
package pl.polsl.lab.communication;

import java.io.*;

/**
 * Class representing server side of protocol. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Protocol {
    
    /**
     * Constant representing number of separator of responses.
     */
    private final String SEPARATOR = " - ";
    
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
     * Constant representing code of wrong quit.
     */
    private final int CODE_QUIT = 7;
    
    /**
     * Field representing number of last request.
     */
    private int lastRequest;
    
    /**
     * Field representing number of last response.
     */
    private int lastResponse;
    
    /**
     * Field representing received mode of input.
     */
    private int receivedMode;
    
    /**
     * Field representing received decimal number.
     */
    private double receivedDecNumber;
    
    /**
     * Field representing received hexadecimal number.
     */
    private String receivedHexNumber;
    
    /**
     * Field representing received mode of conversion.
     */
    private int receivedConversion; 
    
    /**
     * Field representing buffered input character stream.
     */
    private BufferedReader input;
    
    /**
     * Field representing formatted output character stream.
     */
    private PrintWriter output;
    
    /**
     * Constructor for assigning streams from server.
     * 
     * @param in input BufferedReader
     * @param out output PrintWriter
     */
    public Protocol(BufferedReader in, PrintWriter out) {
        lastRequest = 0;
        lastResponse = 0;
        input = in;
        output = out;
    }
    
    /**
     * Method for transferring information to the server.
     * 
     * @return information about success or fail
     * @throws IOException in case of errors on the protocol
     */
    public boolean protocolTransfer() throws IOException {
        try {
            this.hello();
            this.setType();
            this.setNumber();
            this.convert();
            return true;
        } catch (IOException e) {
            if (!e.getMessage().equals("QUIT") && !e.getMessage().equals("HELP"))
                throw new IOException(e.getMessage());
            else
                return false;
        }
    }
    
    /**
     * Method for first stage of protocol - start.
     * 
     * @throws IOException in case or error in protocol
     */
    public void hello() throws IOException {
        String received = input.readLine().toUpperCase();
        if (received.equals("HELLO"))
        {
            lastRequest = 1;
            output.println(CODE_OK + SEPARATOR + received);
            lastResponse = 1;
        }
        else if (received.equals("QUIT"))
        {
            output.println(CODE_QUIT + SEPARATOR + received);
            throw new IOException("QUIT");
        } else if (received.equals("HELP")) {
            output.println("HELLO, SET_TYPE:<0 for hex or 1 for dec>, SET_NUMBER:<number>, CONVERT:<0 for hex or 1 for dec>, QUIT, HELP");
            try{
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            throw new IOException("HELP");
        } else {
            output.println(CODE_WRONG_HELLO + SEPARATOR + received);
            throw new IOException("" + CODE_WRONG_HELLO);
        }
    }
    
    /**
     * Method for second stage of protocol - setting type.
     * 
     * @throws IOException in case or error in protocol
     */
    public void setType() throws IOException {
        try {
            String received = input.readLine().toUpperCase();
            String[] splittedCommand = received.split(":");
            if (splittedCommand[0].equals("SET_TYPE") && lastRequest == 1)
            {
                lastRequest = 2;
                receivedMode = Integer.parseUnsignedInt(splittedCommand[1]);
                output.println(CODE_OK + SEPARATOR + received);
                lastResponse = 2;
            } else {
                if (splittedCommand[0].equals("QUIT")) {
                    output.println(CODE_QUIT + SEPARATOR + received);
                    throw new IOException("QUIT");
                }
                else if (lastRequest == 1)
                {
                    output.println(CODE_WRONG_TYPE + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_TYPE);
                }
                else
                {
                    output.println(CODE_WRONG_ORDER + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_ORDER);
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println(e.getMessage());
            throw new IOException(e.getMessage());            
        }
    }
    
    /**
     * Method for second stage of protocol - setting number.
     * 
     * @throws IOException in case or error in protocol
     */
    public void setNumber() throws IOException {
        try {
            String received = input.readLine().toUpperCase();
            String[] splittedCommand = received.split(":");
            if (splittedCommand[0].equals("SET_NUMBER") && lastRequest == 2)
            {
                lastRequest = 3;
                if (receivedMode == 0) {
                    receivedHexNumber = splittedCommand[1];
                } else {
                    receivedDecNumber = Double.parseDouble(splittedCommand[1]);
                }
                output.println(CODE_OK + SEPARATOR + received);
                lastResponse = 3;
            } else {
                if (splittedCommand[0].equals("QUIT")) {
                    output.println(CODE_QUIT + SEPARATOR + received);
                    throw new IOException("QUIT");
                }
                else if (lastRequest == 2)
                {
                    output.println(CODE_WRONG_NUMBER + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_NUMBER);
                }
                else
                {
                    output.println(CODE_WRONG_ORDER + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_ORDER);
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println(e.getMessage());
            throw new IOException(e.getMessage());            
        }
    }
    
    
    /**
     * Method for second stage of protocol - setting type of conversion.
     * 
     * @throws IOException in case or error in protocol
     */
    public void convert() throws IOException {
        try {
            String received = input.readLine().toUpperCase();
            String[] splittedCommand = received.split(":");
            if (splittedCommand[0].equals("CONVERT") && lastRequest == 3)
            {
                receivedConversion = Integer.parseUnsignedInt(splittedCommand[1]);
                output.println(CODE_OK + SEPARATOR + received);
            } else {
                if (splittedCommand[0].equals("QUIT")) {
                    output.println(CODE_QUIT + SEPARATOR + received);
                    throw new IOException("QUIT");
                }
                else if (lastRequest == 3)
                {
                    output.println(CODE_WRONG_CONVERSION + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_CONVERSION);
                }
                else
                {
                    output.println(CODE_WRONG_ORDER + SEPARATOR + received);
                    throw new IOException("" + CODE_WRONG_ORDER);
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println(e.getMessage());
            throw new IOException(e.getMessage());            
        }
    }
    
    /**
     * Method that generates response.
     * 
     * @param value string with response
     */
    public void response(String value) {
        output.println("RESPONSE:" + value);
    }
    
    /**
     * Getter for type of mode.
     * 
     * @return int with mode
     */
    public int getMode() {
        return receivedMode;
    }
    
    /**
     * Getter for hexadecimal number.
     * 
     * @return string with hexadecimal number
     */
    public String getHexNumber() {
        return receivedHexNumber;
    }
    
    /**
     * Getter for decimal number.
     * 
     * @return string with decimal number
     */
    public double getDecNumber() {
        return receivedDecNumber;
    }
    
    /**
     * Getter for type of conversion.
     * 
     * @return int with conversion
     */
    public int getConversion() {
        return receivedConversion;
    }
}
