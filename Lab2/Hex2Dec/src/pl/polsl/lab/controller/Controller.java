// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.util.Scanner;
import pl.polsl.lab.model.*;
import pl.polsl.lab.view.View;

/**
 * Class representing controller of MVC application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.1
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
     * Field for required model.
     */
    private Converter model;
    
    /**
     * Non-argument constructor (constructing view).
     */
    public Controller() {
        view = new View();
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
     * Method for getting input after getting wrong program parameters.
     * 
     * @param mode information about type of conversion
     * @param number string with number to convert 
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void setModel (int mode, String number) throws NumberFormatException {
        switch(mode) {
            case 0:
                model = new Converter(number);
                break;
            case 1:
                model = new Converter(Double.parseDouble(number));
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
    private void setConvertion (int mode) throws NumberFormatException {
        switch(mode) {
            case 0:
                try {
                    model.calculateDec2Hex();
                } catch (InvalidConversionException e) {
                    view.printWarningInfo(e.getMessage());
                    //getting correct arguments
                    this.userInteraction();
                }
                break;
            case 1:
               try {
                    model.calculateHex2Dec();
                } catch (InvalidHexNumberException | InvalidConversionException e) {
                    view.printWarningInfo(e.getMessage());
                    //getting correct arguments
                    this.userInteraction();
                }
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
    private boolean inputAndProcessControl(String args[]) throws InvalidConversionException {
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
        boolean success = false;
        //check if there was success of parsing and operation, else provide user interaction
        try {
           success = controller.inputAndProcessControl(args);
           controller.view.printResultInfo(controller.model.toString());
        }
        catch(ArrayIndexOutOfBoundsException e) {
            controller.view.printWarningInfo("Wrong number of parameters.");
            controller.userInteraction();
        }
        catch(NumberFormatException | InvalidConversionException e) {
            controller.view.printWarningInfo(e.getMessage());
            controller.userInteraction();
        }
    }
 
}
