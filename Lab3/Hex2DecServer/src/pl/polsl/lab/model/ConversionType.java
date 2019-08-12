// definition of the package in which class is placed
package pl.polsl.lab.model;

/**
 * Class representing enumeration type, providing types of wrong conversion. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public enum ConversionType {
    
    HEX_2_HEX("HEX_2_HEX", "Conversion of type hexadecimal to hexadecimal."),
    DEC_2_DEC("DEC_2_DEC", "Conversion of type decimal to decimal.");
    
    /**
     * Fiels representing type of wrong conversion.
     */
    private final String type;
    
    /**
     * Fiels representing information about wrong conversion.
     */
    private final String info;
    
    /**
     * Constructor which assing type and info into object of wrong conversion
     * 
     * @param type String with type of wrong conversion
     * @param type String with information about wrong conversion
     */
    ConversionType(String type, String info) {
        this.type = type;
        this.info = info;
    }
    
    /**
     * Getter for type of wrong conversion.
     * 
     * @return String with type
     */
    public String type() {
        return type;
    }
    
    /**
     * Getter for information about wrong conversion.
     * 
     * @return String with info
     */
    public String info() {
        return info;
    }
    
}
