// definition of the package in which class is placed
package pl.polsl.lab.exception;

/**
 * Exception class for objects thrown when there are invald signs for hexadecimal system.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class InvalidHexNumberException extends Exception {
    
    /**
     * Field for position in hexadecimal number. 
     */
    private int position;
    
    /**
     * Non-parameter constructor. 
     */
    InvalidHexNumberException() {
        
    }
    
    /**
     * Constructor with construction of super class.
     *
     * @param message display message of exception
     */
    public InvalidHexNumberException(String message) {
        super(message);
    }
    
    /**
     * Constructor with construction of super class.
     *
     * @param message display message of exception
     * @param position display position of wrong value
     */
    InvalidHexNumberException(String message, int position) {
        super(message);
        this.position = position;
    }
    
    /**
     * Getter for position of wrong sign.
     *
     * @return position of wrong sign
     */
    public int getPosition() {
        return position;
    }
    
}
