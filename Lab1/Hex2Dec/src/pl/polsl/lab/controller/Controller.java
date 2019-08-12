// definition of the package in which class is placed
package pl.polsl.lab.controller;

import java.util.Scanner;
import pl.polsl.lab.model.*;
import pl.polsl.lab.view.View;

/**
 * Class representing controller of MVC application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Controller {
    
    /**
     * Constant representing number of recquired arguments (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int NUMBER_OF_ARGS = 2;
    
    /**
     * Field for view of application.
     */
    private View view;
    
    /**
     * Field for required model.
     */
    private Model model;
    
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
        //getting two arguments with wanted info
        for (int i = 0; i < NUMBER_OF_ARGS; i++) {
            if (i == 0) 
                this.view.printNewModeInfo();
            else
                this.view.printNewValueInfo();
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
                model = new Hex2Dec(number);
                break;
            case 1:
                model = new Dec2Hex(Double.parseDouble(number));
                break;
            default:
                //for other integer than 0 or 1
                throw new NumberFormatException();
        }
    }
    
    /**
     * Method for getting input after getting wrong program parameters.
     */
    private void convert() {
        //checking what object is in model and cast
        if (model instanceof Hex2Dec) {
            Hex2Dec currentModel = (Hex2Dec)model;
            try {
                //calculating and printing (exception for wrong hexadecimal value)
                view.printDecimalResult(currentModel.calculate());
            }
            catch (InvalidHexNumberException e) {
                view.printWarningInfo(e.getMessage());
                //getting correct arguments
                this.userInteraction();
            }
        } else {
            Dec2Hex currentModel = (Dec2Hex)model;
            //calculating and printing
            view.printHexadecimalResult(currentModel.calculate());           
        }
    }
    
    /**
     * Method for getting input and setting variables.
     * 
     * @return info about success of operations (no exceptions)
     */
    private boolean inputAndProcessControl(String args[]) {
        int mode = Integer.parseInt(args[0]); 
        this.setModel(mode, args[1]);
        //try to convert
        this.convert();
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
                catch (NumberFormatException exception) {
                    this.view.printWarningInfo("Wrong format of argument. Try again.");
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
        }
        catch(ArrayIndexOutOfBoundsException e) {
            controller.view.printWarningInfo("Wrong number of parameters.");
            controller.userInteraction();
        }
        catch(NumberFormatException e) {
            controller.view.printWarningInfo("Wrong format of argument.");
            controller.userInteraction();
        }
    }
 
}
