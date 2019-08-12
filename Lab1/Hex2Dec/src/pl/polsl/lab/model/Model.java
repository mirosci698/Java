// definition of the package in which class is placed
package pl.polsl.lab.model;

/**
 * Base interface for classes providing conversion.
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 * 
 */
public interface Model {
    
    /**
     * Constant representing comma for numbers (not static according to instructions on page platforma.polsl.pl.).
     */
    final char DOT = '.';
    
    /**
     * Constant representing minus for numbers (not static according to instructions on page platforma.polsl.pl.).
     */
    final char MINUS = '-';
    
    /**
     * Constant representing base of hexadecimal system (not static according to instructions on page platforma.polsl.pl.).
     */
    final int BASE = 16;
    
    /**
     * Constant multiplier for negating number (not static according to instructions on page platforma.polsl.pl.).
     */
    final double MINUS_MULTIPLIER = -1.0;
    
    /**
     * Constant representing offset for digits in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    final int OFFSET_FOR_DIGITS = 48;
    
    /**
     * Constant representing offset for letters in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    final int OFFSET_FOR_LETTERS = 55;
    
}
