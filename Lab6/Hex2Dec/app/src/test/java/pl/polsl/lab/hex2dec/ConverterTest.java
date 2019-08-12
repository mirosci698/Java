package pl.polsl.lab.hex2dec;

import pl.polsl.lab.hex2dec.data.model.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.rules.ExpectedException;

/**
 * Test cases for converter methods.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class ConverterTest {

    Converter converter;

    @Before
    public void setup() {
        converter = new Converter("-AB.8");
    }

    //tests for correct values
    @Test
    public void testCalculateHex2DecMinusFullDouble() {
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -AB.8 -> -171.5", converter.getDecNumber(), -171.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecFullDouble() {
        converter = new Converter("AB.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: AB.8 -> 171.5", converter.getDecNumber(), 171.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecMinusFraction() {
        converter = new Converter("-0.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -0.8 -> -0.5", converter.getDecNumber(), -0.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecFraction() {
        converter = new Converter("0.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: 0.8 -> 0.5", converter.getDecNumber(), 0.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testCalculateHex2DecMinusInteger() {
        converter = new Converter("-AB");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -AB -> -171", converter.getDecNumber(), -171, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecInteger() {
        converter = new Converter("AB");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: AB -> 171", converter.getDecNumber(), 171, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //tests for boundary values
    @Test
    public void testCalculateHex2DecDotAtTheEnd() {
        converter = new Converter("AB.");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: AB. -> 171", converter.getDecNumber(), 171, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecMinusDotAtTheEnd() {
        converter = new Converter("-AB.");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -AB. -> -171", converter.getDecNumber(), -171, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecMinusDotAtTheStart() {
        converter = new Converter("-.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -.8 -> -0.5", converter.getDecNumber(), -0.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecDotAtTheStart() {
        converter = new Converter(".8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: .8 -> 0.5", converter.getDecNumber(), 0.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecDot() {
        converter = new Converter(".");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: . -> 0.0", converter.getDecNumber(), 0, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecMinusDot() {
        converter = new Converter("-.");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -. -> 0.0", converter.getDecNumber(), 0, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2DecSmallLetters() {
        converter = new Converter("-ab.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Not equal: -ab.8. -> -171.5", converter.getDecNumber(), -171.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //tests for wrong values
    @Test
    public void testCalculateHex2DecRandomString() {
        converter = new Converter("1234567890ABCDEFGHIJKLMNOPQRSTUWVXYZ-=[];',./_+{}:<>?");
        try {
            converter.calculateHex2Dec();
            fail("Should not work for random string.") ;
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testCalculateHex2DecComma() {
        converter = new Converter("AB,8");
        try {
            converter.calculateHex2Dec();
            fail("Should not work for comma.") ;
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testCalculateHex2DecWrongHexDigits() {
        converter = new Converter("AGC.8Y");
        try {
            converter.calculateHex2Dec();
            fail("Should not work for hex digits other than ABCDEF.") ;
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testCalculateHex2DecWhitespaces() {
        converter = new Converter("AGC 8Y");
        try {
            converter.calculateHex2Dec();
            fail("Should not work for whitespaces.") ;
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testCalculateHex2DecDecimalDouble() {
        converter = new Converter(-171.5);
        try {
            converter.calculateHex2Dec();
            fail("Should not work for double.") ;
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    //tests for correct values
    @Test
    public void testCalculateDec2HexFullDouble() {
        converter = new Converter(171.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: 171.5 -> AB.8", converter.getHexNumber(), "AB.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexMinusFullDouble() {
        converter = new Converter(-171.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: -171.5 -> -AB.8", converter.getHexNumber(), "-AB.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexFraction() {
        converter = new Converter(0.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: 0.5 -> 0.8", converter.getHexNumber(), "0.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexMinusFraction() {
        converter = new Converter(-0.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: -0.5 -> -0.8", converter.getHexNumber(), "-0.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexInteger() {
        converter = new Converter(171);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: 171 -> AB.0", converter.getHexNumber(), "AB.0");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexMinusInteger() {
        converter = new Converter(-171);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: -171 -> -AB.0", converter.getHexNumber(), "-AB.0");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //tests for boundary values
    @Test
    public void testCalculateDec2HexMinusDotAtTheEnd() {
        converter = new Converter(-171.);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: -171. -> -AB.0", converter.getHexNumber(), "-AB.0");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexDotAtTheEnd() {
        converter = new Converter(171.);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: 171. -> AB.0", converter.getHexNumber(), "AB.0");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexMinusDotAtTheStart() {
        converter = new Converter(-.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: -.5 -> -0.8", converter.getHexNumber(), "-0.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDec2HexDotAtTheStart() {
        converter = new Converter(.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Not equal: .5 -> 0.8", converter.getHexNumber(), "0.8");
        } catch (InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //tests for wrong values
    @Test
    public void testCalculateDec2HexString() {
        converter = new Converter("-AB.8");
        try {
            converter.calculateDec2Hex();
            fail("Should not convert for string.");
        } catch (InvalidConversionException e) {
        }
    }

    //tests for correct values
    @Test
    public void testCalculateDec2Hex() {
        converter = new Converter(171.5);
        try {
            converter.calculate();
            assertEquals("Not equal: 171.5 -> AB.8", converter.getHexNumber(), "AB.8");
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateHex2Dec() {
        converter = new Converter("AB.8");
        try {
            converter.calculate();
            assertEquals("Not equal: AB.8 -> 171.5", converter.getDecNumber(), 171.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //tests for boundary values
    @Test
    public void testCalculateBoundaryDouble() {
        converter = new Converter(-.5);
        try {
            converter.calculate();
            assertEquals("Not equal: -.5 -> -0.8", converter.getHexNumber(), "-0.8");
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateBoundaryHex() {
        converter = new Converter("-.8");
        try {
            converter.calculate();
            assertEquals("Not equal: -.8 -> -0.5", converter.getDecNumber(), -0.5, 0.01);
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            fail("Exception: " + e.getMessage());
        }
    }

    //test for wrong values
    @Test
    public void testCalculateWrongString() {
        converter = new Converter("abcdefghijklmnopqrstuwvxyz");
        try {
            converter.calculate();
            fail("Should not convert for string.");
        } catch (InvalidHexNumberException | InvalidConversionException e) {
        }
    }

    @Test
    public void testToStringHex() {
        converter = new Converter("-AB.8");
        assertEquals("Wrong to string conversion: ", converter.toString(), "Prepared for Hex2Dec conversion > -AB.8");
    }

    @Test
    public void testToStringHex2Dec() {
        converter = new Converter("-AB.8");
        try {
            converter.calculateHex2Dec();
            assertEquals("Wrong to string conversion: ", converter.toString(), "Hex2Dec conversion result > -AB.8 -> -171.5");
        } catch (InvalidHexNumberException | InvalidConversionException e) {

        }
    }

    @Test
    public void testToStringDec() {
        converter = new Converter(-171.5);
        assertEquals("Wrong to string conversion: ", converter.toString(), "Prepared for Dec2Hex conversion > -171.5");
    }
    @Test
    public void testToStringDec2Hex() {
        converter = new Converter(-171.5);
        try {
            converter.calculateDec2Hex();
            assertEquals("Wrong to string conversion: ", converter.toString(), "Dec2Hex conversion result > -171.5 -> -AB.8");
        } catch (InvalidConversionException e) {

        }
    }

    @Test
    public void testInvalidHexNumberException() {
        Exception exception = null;
        try {
            converter = new Converter("-AG.8");
            converter.calculateHex2Dec();
        } catch (InvalidHexNumberException | InvalidConversionException e) {
            exception = e;
        }
        assertThat("Wrong type of the exception: ", exception, is(instanceOf(InvalidHexNumberException.class)));
        assertThat("Wrong message: ", exception.getMessage(), startsWith("Invalid hex number"));
        assertNull("Decimal number shouldn't be changed!", converter.getDecNumber());
    }

    @Test
    public void testInvalidConversionExceptionHex2Hex() {
        Exception exception = null;
        try {
            converter = new Converter("-AB.8");
            converter.calculateDec2Hex();
        } catch (InvalidConversionException e) {
            exception = e;
        }
        assertThat("Wrong type of the exception: ", exception, is(instanceOf(InvalidConversionException.class)));
        assertThat("Wrong message: ", exception.getMessage(), is(ConversionType.HEX_2_HEX.info()));
        assertNull("Decimal number shouldn't be changed!", converter.getDecNumber());
    }

    @Test
    public void testInvalidConversionExceptionDec2Dec() {
        Exception exception = null;
        try {
            converter = new Converter(-171.5);
            converter.calculateHex2Dec();
        } catch (InvalidConversionException | InvalidHexNumberException e) {
            exception = e;
        }
        assertThat("Wrong type of the exception: ", exception, is(instanceOf(InvalidConversionException.class)));
        assertThat("Wrong message: ", exception.getMessage(), is(ConversionType.DEC_2_DEC.info()));
        assertNull("Hexadecimal number shouldn't be changed!", converter.getHexNumber());
    }

}
