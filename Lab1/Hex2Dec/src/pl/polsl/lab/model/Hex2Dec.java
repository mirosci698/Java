// definition of the package in which class is placed
package pl.polsl.lab.model;

/**
 * Class representing hexadecimal to decimal conversion. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Hex2Dec implements Model {
    
    /**
     * Constant representing start code of digits in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int DIGITS_START_CODE = 48;
    
    /**
     * Constant representing end code of digits in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int DIGITS_END_CODE = 57;
    
    /**
     * Constant representing start code of letters in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int LETTERS_START_CODE = 65;
    
    /**
     * Constant representing end code of letters in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int LETTERS_END_CODE = 70;
    
    /**
     * Field for string with hexadecimal value.
     */
    private String hexNumber;
    
    /**
     * Field for value of offset for sign (minus).
     */
    private int offsetForSign = 0;
    
    /**
     * Constructor which check hex value.
     * 
     * @param hex string with hexadecimal number
     */
    public Hex2Dec(String hex) {
        this.hexNumber = hex;
        //operations on capital letters and checking for minus
        this.hexNumber = this.hexNumber.toUpperCase();
        if (this.hexNumber.charAt(0) == MINUS)
            offsetForSign = 1;
    }
    
    /**
     * Method which calculates decimal value.
     * 
     * @return double value of converted number
     * @throws InvalidHexNumberException when ther is sign other than dot, capital letter or digit
     */
    public double calculate() throws InvalidHexNumberException {
        double result = 0;
        int buffer = 0;
        //seeking comma delimiter
        int positionOfDot = hexNumber.indexOf(DOT);
        //when not assume that it was at the end of string
        if (positionOfDot == -1)
            positionOfDot = hexNumber.length() - 1;
        //get multiplier of last position
        double multiplier = Math.pow(16, positionOfDot - hexNumber.length() + 1);
        //for all signs (except minus and dot) get hexadecimal value
        for (int i = hexNumber.length() - 1; i > - 1 + offsetForSign; i--){
            if (hexNumber.charAt(i) == DOT)
                continue;
            char digit = hexNumber.charAt(i);
            //getting correct offset or throwing exception with position of fail
            if (digit >= DIGITS_START_CODE && digit <= DIGITS_END_CODE)
                buffer = digit - OFFSET_FOR_DIGITS;
            else
                if (digit >= LETTERS_START_CODE && digit <= LETTERS_END_CODE)
                    buffer = digit - OFFSET_FOR_LETTERS;
                else
                    throw new InvalidHexNumberException("Invalid hex number", i);
            //adding value of current position and preparing multiplier for next
            result += buffer * multiplier;
            multiplier *= BASE;
        }
        //cheking for negative value of input
        if (offsetForSign == 1)
            result *= MINUS_MULTIPLIER;
        return result;
    }
}
