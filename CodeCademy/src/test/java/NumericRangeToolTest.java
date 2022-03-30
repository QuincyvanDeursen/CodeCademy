

import org.junit.Test;
import InputVerification.NumericRangeTool;
import static org.junit.Assert.*;

public class NumericRangeToolTest {
    /**
     * @desc Validates if the input is within range of 0-100%
     *
     * @subcontract value within valid range {
     *   @requires 0 <= percentage <= 100;
     *   @ensures \result = true;
     * }
     */


    @Test
    public void testInputIsWithinRangeRequires100EnsuresTrue(){
        //Arrange
        int percentage = 100;

        //Act
        Boolean result = NumericRangeTool.isValidPercentage(percentage);

        //Assert
        assertEquals(true, result);
    }

    @Test
    public void testInputIsWithinRangeRequires50EnsuresTrue(){
        //Arrange
        int percentage = 50;

        //Act
        Boolean result = NumericRangeTool.isValidPercentage(percentage);

        //Assert
        assertEquals(true, result);
    }


    @Test
    public void testInputIsWithinRangeRequires0EnsuresTrue(){
        //Arrange
        int percentage = 0;

        //Act
        Boolean result = NumericRangeTool.isValidPercentage(percentage);

        //Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract value out of range low {
     *   @requires percentage < 0;
     *   @ensures \result = false;
     * }
     */

    @Test()
    public void testInputIsWithinRangeRequiresMinus1EnsuresFalse(){
        //Arrange
        int percentage = -1;

        //Act
        Boolean result = NumericRangeTool.isValidPercentage(percentage);

        //Assert
        assertEquals(false, result);
    }

    /** @subcontract value out of range high {
     *   @requires percentage > 100;
     *   @signals \result = false;
     * }
     *
     */

    @Test()
    public void testInputIsWithinRangeRequires101EnsuresFalse(){
        //Arrange
        int percentage = 101;

        //Act
        Boolean result = NumericRangeTool.isValidPercentage(percentage);

        //Assert
        assertEquals(false, result);
    }
}

