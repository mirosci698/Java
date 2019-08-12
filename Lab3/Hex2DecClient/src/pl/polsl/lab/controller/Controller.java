// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.util.Scanner;
import pl.polsl.lab.view.View;
import pl.polsl.lab.client.Hex2DecClient;
import java.io.*;
import pl.polsl.lab.exception.*;
import pl.polsl.lab.communication.Protocol;

/**
 * Class representing controller of MVC application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.2
 */
public class Controller {
    
    /**
     * Constant representing number of recquired arguments (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int NUMBER_OF_ARGS = 3;
    
    /**
     * Field for view of application.
     */
    private View view;
    
    /**
     * Field for client class of client-server application.
     */
    private Hex2DecClient client;
    
    /**
     * Field for protocol class of client-server application.
     */
    private Protocol protocol;
    
    /**
     * Field for string representation of result (only one in lifecycle).
     */
    String result;
    
    /**
     * Non-argument constructor (constructing view).
     */
    public Controller() {
        view = new View();
    }
    
    /**
     * Method for initializing of client.
     */
    private void initializeClient() throws IOException {
        client = new Hex2DecClient();
    }
    
    /**
     * Method for getting input after getting wrong program parameters.
     * 
     * @return table of strings with arguments
     */
    private String[] inputFromKeyboard() {
        //scanner for getting input and table of strings for saving
        Scanner scanner = new Scanner(System.in);
        String result[] = new String[NUMBER_OF_ARGS];
        //getting three arguments with wanted info
        for (int i = 0; i < NUMBER_OF_ARGS; i++) {
            if (i == 1) 
                this.view.printNewValueInfo();
            else
                this.view.printNewNumberTypeInfo();
            result[i] = scanner.next();
        }
        return result;
    }
    
    /**
     * Method for sending conversion to server.
     * 
     * @param input type of input (hex or dec)
     * @param number number to convert (string representation)
     * @param mode type of conversion (Hex2Dec od Dec2Hex)
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void sendConvertion (int input, String number, int mode) throws NumberFormatException,
            InvalidConversionException {
        try { //in case of success of protocol - get result
            protocol = new Protocol(client.getInput(), client.getOutput());
            if (protocol.protocolTransfer(input, number, mode))
                result = protocol.getResult();
        } catch (InvalidHexNumberException e) {
            view.printWarningInfo(e.getMessage());
            //getting correct arguments
            this.userInteraction();
        }
    }
    
    /**
     * Method for getting input and setting variables.
     * 
     * @param args array with arguments of application
     * @return info about success of operations (no exceptions)
     * @throws InvalidConversionException in case of wrong type of conversion
     * @throws NumberFormatException in case of wrong type of input or type of result in conversion
     */
    private boolean inputAndProcessControl(String args[]) throws InvalidConversionException,
            NumberFormatException {
        int inputType = Integer.parseInt(args[0]); 
        String number = args[1];
        int typeOfConvertion = Integer.parseInt(args[2]);
        if ((inputType != 0 && inputType != 1) || (typeOfConvertion != 0 && typeOfConvertion != 1)) 
            throw new NumberFormatException();
        //try to convert
        this.sendConvertion(inputType, number, typeOfConvertion);
        return true;
    }
    
    /**
     * Method for getting input and setting variables.
     */
    private void userInteraction() {
        boolean success = false;
        do {
            //checking that input values are correct until success
                try {
                    success = this.inputAndProcessControl(this.inputFromKeyboard());
                }
                catch (NumberFormatException | InvalidConversionException exception) {
                    this.view.printWarningInfo(exception.getMessage());
                }
            } while (success != true);
    }
    
    /**
     * Main method of program, which construct controller and parsing arguments from configuration.
     * 
     * @param args table of strings with two arguments: integer with mode and string with parsable
     * hex (accept e.g. AB, ab, Ab, aB, -AB, -AB.8, AB.8, .8, -.8, -AB., AB., 0.8, -0.8) 
     * or double value (accept e.g. 171, -171, -171.5, 171.5, 0.5, -0.5, .5, -.5, 171., -171.)
     */
    public static void main(String args[]) {
        Controller controller = new Controller();
        try {
            controller.initializeClient();
            boolean success = false;
            //check if there was success of parsing and operation, else provide user interaction
            success = controller.inputAndProcessControl(args);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch(ArrayIndexOutOfBoundsException e) {
            controller.view.printWarningInfo("Wrong number of parameters.");
            controller.userInteraction();
        } catch(NumberFormatException | InvalidConversionException e) {
            controller.view.printWarningInfo(e.getMessage());
            controller.userInteraction();
        } finally {
            controller.view.printResultInfo(controller.result);
        }
    }
 
}
