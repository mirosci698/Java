// definition of the package in which class is placed
package pl.polsl.lab.server;

import java.net.*;
import java.io.*;
import java.util.*;
import pl.polsl.lab.model.*;

/**
 * Class representing server of TCP client-server application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Hex2DecServer implements Closeable {

    /**
     * Constant representing name of port property.
     */
    private static final String PORT_NAME = "port";
    
    /**
     * Field for port number.
     */
    private int port;

    /**
     * Field represents the socket waiting for client connections.
     */
    private ServerSocket serverSocket;
    
    /**
     * Field for model (final because it is created once in a lifecycle).
     */
    private final Converter model;
    
    /**
     * Creates the server socket
     *
     * @throws IOException when prot is already bind
     */
    Hex2DecServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        model = new Converter(0.0); //starter value of created model
    }
    
    /**
     * Method for closing.
     * 
     * @throws IOException when problem with closing socket
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
    
    /**
     * Main method of server, which starts and control it.
     * 
     * @param args all parameters are ignored
     */
    public static void main(String[] args) {
        
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.load();
        int port = 0; //port reserved, control value
        try {
            port = Integer.parseUnsignedInt(serverProperties.getProperty(PORT_NAME));
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
        try (Hex2DecServer tcpServer = new Hex2DecServer(port)) {
            System.out.println("Server started");
            while (true) {
                Socket socket = tcpServer.serverSocket.accept();
                try (SingleService singleService = new SingleService(socket, tcpServer.model)) {
                    singleService.realize();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
