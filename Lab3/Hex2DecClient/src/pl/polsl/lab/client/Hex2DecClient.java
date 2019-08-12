// definition of the package in which class is placed
package pl.polsl.lab.client;

import java.io.*;
import java.net.*;

/**
 * Class representing client of TCP client-server application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Hex2DecClient implements Closeable {

    /**
     * Constant representing name of port property.
     */
    private static final String PORT_NAME = "port";
    
    /**
     * Constant representing name of host property.
     */
    private static final String HOST_NAME = "host";
    
    /**
     * Field for port number.
     */
    private int port;
    
    /**
     * Field for host name.
     */
    private String host;
    
    /**
     * Field for socket.
     */
    Socket socket;
    
    /**
     * Field for input BufferedReader.
     */
    BufferedReader input;
    
    /**
     * Field for output PrintWriter.
     */
    PrintWriter output;
    
    /**
     * Non-argument constructor.
     * 
     * @throws IOException in case of constructing fields errors. 
     */
    public Hex2DecClient() throws IOException {
        this.getProperties(); //getting properties from file
        socket = new Socket(host, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        output.println("Test"); //for clearing buffer on server side
    }
    
    /**
     * Getter for input stream.
     * 
     * @return BufferedReader input stream
     */
    public BufferedReader getInput() {
        return input;
    }
    
    /**
     * Getter for output stream.
     * 
     * @return PrintWriter output stream
     */
    public PrintWriter getOutput() {
        return output;
    }
    
    /**
     * Method for getting properties from file and assign.
     */
    private void getProperties() {
        ClientProperties clientProperties = new ClientProperties();
        clientProperties.load(); //loading client properties
        host = clientProperties.getProperty(HOST_NAME);
        if (host == null) //when file is corrupted
            host = "undefined host";
        try {
            port = Integer.parseUnsignedInt(clientProperties.getProperty(PORT_NAME));
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Method for closing.
     * 
     * @throws IOException when problem with closing socket
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
