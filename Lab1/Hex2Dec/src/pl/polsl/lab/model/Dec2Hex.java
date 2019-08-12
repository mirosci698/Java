// definition of the package in which class is placed
package pl.polsl.lab.model;

/**
 * Class representing hexadecimal to decimal conversion. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class Dec2Hex implements Model {
    
    /**
     * Field for hexadecimal digits (not static according to instructions on page platforma.polsl.pl.).
     */
    private char[] digits = new char[16];
    
    /**
     * Field for decimal value.
     */
    private double decNumber;
    
    /**
     * Field for information about negative value.
     */
    private boolean isNegative = false;
    
    /**
     * Constant representing precision - floating-point error (not static according to instructions on page platforma.polsl.pl.).
     */
    private static final double PRECISION = 0.00000001;
    
    /**
     * Constructor which fill table with hexadecimal digits.
     * 
     * @param dec double with decimal number
     */
    public Dec2Hex(double dec) {
        this.fillDigitsTable();
        decNumber = dec;
    }
    
    /**
     * Method which fill hexadecimal digits table.
     */
    private void fillDigitsTable() {
        for (int i = 0; i < digits.length; i++)
            if (i < 10)
                digits[i] = (char)(i + OFFSET_FOR_DIGITS);
            else
                digits[i] = (char)(i + OFFSET_FOR_LETTERS);
    }
    
    /**
     * Method which calculates hexadecimal value.
     * 
     * @return string with hexadecimal value
     */
    public String calculate() {
        //empty string for result
        String result = new String();
        //operating always on positive value and saving information about negative value
        if (decNumber < 0) {
            decNumber *= MINUS_MULTIPLIER;
            isNegative = true;
        }
        //getting floor and fraction of decimal value
        int floor = (int)Math.floor(decNumber);
        double fraction = decNumber - Math.floor(decNumber);
        //dividing by 16 of floor and multiplying fraction until getting 0 or wanted prcision
        do {
            int index = floor % BASE;
            result = digits[index] + result;
            floor = (floor - index)/BASE;
        } while (floor > 0);
        result = result + DOT;
        int step = 0;
        do {
            int index = (int)(fraction * BASE);
            result = result + digits[index];
            fraction = fraction * BASE - Math.floor(fraction * BASE);
            step++;
        } while (fraction > PRECISION && step < 8);
        //checking and negating of value
        if (isNegative == true)
            result = MINUS + result;
        return result;
    }
    
}
