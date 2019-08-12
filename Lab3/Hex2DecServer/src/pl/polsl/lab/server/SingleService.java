// definition of the package in which class is placed
package pl.polsl.lab.server;

import java.net.*;
import java.io.*;
import pl.polsl.lab.communication.*;
import pl.polsl.lab.model.*;

/**
 * Class representing single service of application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class SingleService implements Closeable {
    
    /**
     * Socket representing connection to the client.
     */
    private Socket socket;
    
    /**
     * Buffered input character stream.
     */
    private BufferedReader input;
    
    /**
     * Formatted output character stream.
     */
    private PrintWriter output;
    
    /**
     * Field for model of converter.
     */
    private Converter model;
    

    /**
     * The constructor of instance of the SingleService class (using socket and model as
     * a parameter)
     *
     * @param socket socket representing connection to the client
     * @param serverModel created model
     */
    public SingleService(Socket socket, Converter serverModel) throws IOException {
        this.socket = socket;
        output = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        char[] buffer = new char[100]; //clearing buffer
        input.read(buffer, 0, 100);
        model = serverModel;
    }

    /**
     * Method that realizes the service.
     */
    public void realize() {
        try {
            Protocol protocol = null;
            loop(protocol);
        } catch (InvalidConversionException | InvalidHexNumberException | NullPointerException e) {
            output.println("ERROR:" + e.getMessage()); //info about error
            try{
                Thread.sleep(5000); //pause for showing info
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            System.err.println(e.getMessage());
        } catch (IOException e) {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            output.println(e.getMessage());
            System.err.println(e.getMessage());
        } finally {
            System.out.println("closing...");
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Method representing loop of working.
     * 
     * @throws IOException in case of in-out errors
     * @throws InvalidConversionException in case of conversion type same-same
     * @throws InvalidHexNumberException in case of invalid hexadecimal number
     * @throws NullPointerException in case null in reference
     */
    private void loop(Protocol protocol) throws IOException, InvalidConversionException,
            InvalidHexNumberException, NullPointerException {
        while (true) {
                protocol = new Protocol(input, output);
                if (protocol.protocolTransfer())//if there was success on transfer
                { //operations in correct order
                    int mode = protocol.getMode();
                    if (mode == 0)
                        model.setHexNumber(protocol.getHexNumber());
                    else
                        model.setDecNumber(protocol.getDecNumber());
                    int conversion = protocol.getConversion();
                    if (conversion == 0)
                        model.calculateDec2Hex();
                    else
                        model.calculateHex2Dec();
                    protocol.response(model.toString()); //make response
                } else { 
                    protocol = null;
                    break; //quitting
                }
            }
    }

    /**
     * Method that closing the service.
     * 
     * @throws IOException in case of problems with closing socket
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
    
}
