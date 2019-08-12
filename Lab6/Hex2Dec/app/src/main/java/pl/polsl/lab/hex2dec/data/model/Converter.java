// definition of the package in which class is placed
package pl.polsl.lab.hex2dec.data.model;

import java.util.*;

/**
 * Class representing converter of numbers.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class Converter {

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
     * Constant maximal number of steps in product - quotient method (not static according to instructions on page platforma.polsl.pl.).
     */
    final int STEPS = 8;

    /**
     * Constant representing offset for digits in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    final int OFFSET_FOR_DIGITS = 48;

    /**
     * Constant representing offset for letters in Unicode (not static according to instructions on page platforma.polsl.pl.).
     */
    final int OFFSET_FOR_LETTERS = 55;

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
     * Field used as flag to indicate whether dot was found or not.
     */
    private boolean wasDot = false;

    /**
     * Collection for hexadecimal digits (not static according to instructions on page platforma.polsl.pl.).
     */
    private List<Character> digits;

    /**
     * Field for decimal value.
     */
    private Double decNumber;

    /**
     * Field for information about negative value.
     */
    private boolean isNegative = false;

    /**
     * History of conversions.
     */
    private Hashtable<Integer, String> history;

    /**
     * Counter for history of conversions.
     */
    private int counter;

    /**
     * Constant representing precision - floating-point error (not static according to instructions on page platforma.polsl.pl.).
     */
    private static final double PRECISION = 0.00000001;

    /**
     * Interface for lambda expressions.
     */
    interface HexString {

        String makeHexString(List<Character> list);

    }

    /**
     * Constructor which fill table with hexadecimal digits.
     *
     * @param dec double with decimal number
     */
    public Converter(double dec) {
        decNumber = new Double(dec);
        this.fillDigitsTable();
        counter = 0;
        history = new Hashtable<Integer, String>();
    }

    /**
     * Constructor which check hex value.
     *
     * @param hex string with hexadecimal number
     */
    public Converter(String hex) {
        this.hexNumber = hex;
        //operations on capital letters and checking for minus
        this.hexNumber = this.hexNumber.toUpperCase();
        if (this.hexNumber.charAt(0) == MINUS) {
            isNegative = true;
            this.hexNumber = this.hexNumber.replaceFirst("-", "");
        }
        counter = 0;
        history = new Hashtable<Integer, String>();
    }

    /**
     * Method overriding toString() - return info about state of conversion.
     *
     * @return String with info about state of conversion
     */
    @Override
    public String toString() {
        String result;
        if (digits == null) //Hex2Dec conversion
            if (decNumber != null) //there was no conversion
                result = "Hex2Dec conversion result > " + hexNumber + " -> " + Double.toString(decNumber);
            else
            if (isNegative == true) //Constructo replaces minus with empty string
                result = "Prepared for Hex2Dec conversion > " + "-" + hexNumber;
            else
                result = "Prepared for Hex2Dec conversion > " + hexNumber;
        else //Dec2Hex conversion
            if (hexNumber != null)  //there was no conversion
                result = "Dec2Hex conversion result > " + decNumber.toString() + " -> " + hexNumber;
            else
                result = "Prepared for Dec2Hex conversion > " + decNumber.toString();
        return result;
    }

    /**
     * Method which fill hexadecimal digits table.
     */
    private void fillDigitsTable() {
        digits = new ArrayList<>(BASE);
        char valueOfDigit = OFFSET_FOR_DIGITS;
        do { //fill list with unicode codes of signs
            digits.add(valueOfDigit++);
            if (valueOfDigit == DIGITS_END_CODE + 1) //change into letters
                valueOfDigit = LETTERS_START_CODE;
        } while (valueOfDigit <= LETTERS_END_CODE);
    }

    /**
     * Method which finds multiplier for dividing in conversion.
     *
     * @return multiplier as power of 16
     */
    private double findMultiplier() {
        //seeking comma delimiter
        int positionOfDot = hexNumber.indexOf(DOT);
        //when not assume that it was at the end of string
        if (positionOfDot == -1)
            positionOfDot = hexNumber.length();
        //get multiplier of first position
        return Math.pow(BASE, positionOfDot - 1);
    }

    /**
     * Method for getting Unicode code of sign.
     *
     * @param digit char with digit of number
     * @return Unicode code of digit
     * @throws InvalidHexNumberException in case of sign which is not hexadecimal digit
     */
    @SuppressWarnings("unchecked")
    private int getCodeOfSign(char digit) throws InvalidHexNumberException {
        //getting correct offset or throwing exception with position of fail
        if (digit >= DIGITS_START_CODE && digit <= DIGITS_END_CODE)
            return digit - OFFSET_FOR_DIGITS;
        else
        if (digit >= LETTERS_START_CODE && digit <= LETTERS_END_CODE)
            return digit - OFFSET_FOR_LETTERS;
        else
            throw new InvalidHexNumberException("Invalid hex number");
    }

    /**
     * Method for converting with product - quotient method.
     *
     * @param result list where signs are inserted
     */
    @SuppressWarnings("unchecked") //case of cast
    private void productQuotientMethod(LinkedList result) {
        //getting floor and fraction of decimal value
        int floor = (int)Math.floor(decNumber);
        double fraction = decNumber - Math.floor(decNumber);
        do { //converting integer part
            int index = floor % BASE;
            result.addFirst(digits.get(index));
            floor = (floor - index)/BASE;
        } while (floor > 0);
        result.addLast(DOT);
        int step = 0;
        do { //converting fraction part
            int index = (int)(fraction * BASE);
            result.addLast(digits.get(index));
            fraction = fraction * BASE - Math.floor(fraction * BASE);
            step++;
        } while (fraction > PRECISION && step < STEPS);
    }

    /**
     * Method for calculating hexadecimal to decimal.
     *
     * @throws InvalidHexNumberException in case of sign which is not hexadecimal digit
     * @throws InvalidConversionException in case of conversion only with decimal
     */
    public void calculateHex2Dec() throws InvalidHexNumberException, InvalidConversionException {
        if (digits != null)
            throw new InvalidConversionException(ConversionType.DEC_2_DEC);
        Double result = new Double(0.0);
        int buffer = 0;
        double multiplier = findMultiplier();
        char[] charsOfHexNumber = hexNumber.toCharArray();
        for (char digit: charsOfHexNumber) {
            if (digit == DOT && wasDot == false) {
                wasDot = true;
                continue;
            }
            buffer = getCodeOfSign(digit); //code or exception for invalid
            result += buffer * multiplier; //adding value of current position
            multiplier /= BASE; //preparing multiplier for next
        }
        if (isNegative == true) { //cheking for negative value of input
            result *= MINUS_MULTIPLIER;
            hexNumber = "-" + hexNumber;
        }
        decNumber = result;
        history.put(++counter, toString());
    }

    /**
     * Method for calculating decimal to hexadecimal.
     *
     * @throws InvalidConversionException in case of conversion only with hexadecimal
     */
    public void calculateDec2Hex() throws InvalidConversionException {
        if (digits == null)
            throw new InvalidConversionException(ConversionType.HEX_2_HEX);
        //LinkedList as list of hex characters, LinkedList because of addFirst and addLast methods
        LinkedList<Character> result = new LinkedList<>();
        if (decNumber < 0) { //operate on positive, save information about minus
            decNumber *= MINUS_MULTIPLIER;
            isNegative = true;
        }
        productQuotientMethod(result); //calculating decimal number
        if (isNegative == true) //check and negate value
            result.addFirst(MINUS);
        HexString parser = (n) -> { //definition of lambda exprssion
            char[] charTable = new char[n.size()];
            int index = 0;
            for (Character hexDigit: n) { //iterate and put into table
                charTable[index++] = hexDigit;
            }
            return new String(charTable); //make String from table
        };
        if (isNegative == true)
            decNumber *= MINUS_MULTIPLIER;
        hexNumber = parser.makeHexString(result);
        history.put(++counter, toString());
    }

    /**
     * Method for calculating all type of numbers.
     *
     * @throws InvalidHexNumberException in case of sign which is not hexadecimal digit
     * @throws InvalidConversionException in case of conversion only in one type (Dec2Dec, Hex2Hex)
     */
    @Deprecated
    public void calculate() throws InvalidHexNumberException, InvalidConversionException {
        if (digits == null)
            calculateHex2Dec();
        else
            calculateDec2Hex();
    }

    /**
     * Getter for hexadecimal number.
     *
     * @return String with hexadecimal number
     */
    public String getHexNumber() {
        return hexNumber;
    }

    /**
     * Getter for decimal number.
     *
     * @return String with decimal number
     */
    public Double getDecNumber() {
        return decNumber;
    }

    /**
     * Getter for history of conversions.
     *
     * @return Hashtable with history of conversions.
     */
    public Hashtable<Integer, String> getHistory() {
        return history;
    }

    /**
     * Method for adding to history of conversions.
     *
     * @param name String with reprsentation of conversion
     */
    public void addToHistory(String name) {
        history.put(++counter, name);
    }

    /**
     * Setter for hexadecimal number.
     *
     * @param hex String with hexadecimal number
     */
    public void setHexNumber(String hex) {
        wasDot = false;
        isNegative = false;
        digits = null;
        decNumber = null;
        this.hexNumber = hex;
        //operations on capital letters and checking for minus
        this.hexNumber = this.hexNumber.toUpperCase();
        if (this.hexNumber.charAt(0) == MINUS) {
            isNegative = true;
            this.hexNumber = this.hexNumber.replaceFirst("-", "");
        }
    }

    /**
     * Setter for decimal number.
     *
     * @param dec double with decimal number
     */
    public void setDecNumber(double dec) {
        wasDot = false;
        isNegative = false;
        hexNumber = null;
        decNumber = dec;
        this.fillDigitsTable();
    }

}
