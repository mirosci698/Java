// definition of the package in which class is placed
package pl.polsl.lab.model;

/**
 * Exception class for objects thrown when there is a attempt to Dec2Dec or Hex2Hex conversion.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class InvalidConversionException extends Exception {
    
    private String type;
    
    /**
     * Non-parameter constructor. 
     */
    InvalidConversionException() {
        
    }
    
    /**
     * Constructor with construction of super class which gets info from enum ConversionType.
     *
     * @param conversionType display message of exception from enum type
     */
    InvalidConversionException(ConversionType conversionType) {
        super(conversionType.info());
        type = conversionType.type();
        
    }
    
    /**
     * Getter for type of wrong conversion.
     *
     * @return type of wrong conversion
     */
    public String getType() {
        return type;
    }
    
}
