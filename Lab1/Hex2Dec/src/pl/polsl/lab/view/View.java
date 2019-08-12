// definition of the package in which class is placed
package pl.polsl.lab.view;

/**
 * Class representing view for console application. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class View {
    
    /**
    * Non-argument constructor.
    */
    public View() {
        
    }
    
    /**
    * Method for printing warnings.
    * 
    * @param warning string with warning info
    */
    public void printWarningInfo(String warning) {
        System.out.println("Warning! " + warning);
    }
    
    /**
    * Method for printing info about mode.
    */
    public void printNewModeInfo() {
        System.out.print("Write mode (0 - Hex2Dec/1 - Dec2Hex): ");
    }
    
    /**
    * Method for printing info about value.
    */
    public void printNewValueInfo() {
        System.out.print("Write valid value: ");
    }
    
    /**
    * Method for printing decimal result.
    * 
    * @param result double with result
    */
    public void printDecimalResult(double result) {
        System.out.println("Hex2Dec conversion result: " + result);
    }
    
    /**
    * Method for printing hexadecimal result.
    * 
    * @param result string with result
    */
    public void printHexadecimalResult(String result) {
        System.out.println("Dec2Hex conversion result: " + result);
    }
    
}
