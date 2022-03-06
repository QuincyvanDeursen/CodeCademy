
import InputVerification.DateTools;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;


public class DateToolsTest {
    /**
     * @desc Validates is a given date in the form of day, month and year is valid.
     * @subcontract 31 days in month {
     * @requires (month = = 1 | | month = = 3 | | month = = 5 | | month = = 7 | |
     *month = = 8 | | month = = 10 | | month = = 12) && 1 <= day <= 31;
     * @ensures \result = true;
     * }
     */

    @Test
    public void testInputRequires31Days5Months2021YearsEnsuresTrue() {

        //Arrange
        int days = 31;
        int month = 5;
        int year = 2021;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    @Test
    public void testInputRequires1Days5Months2021YearsEnsuresTrue() {

        //Arrange
        int days = 1;
        int month = 5;
        int year = 2021;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    /**
     * @subcontract 30 days in month {
     * @requires (month = = 4 | | month = = 6 | | month = = 9 | | month = = 11) &&
     * 1 <= day <= 30;
     * @ensures \result = true;
     * }
     */

    @Test
    public void testInputRequires30Days4Months2022YearsEnsuresTrue() {

        //Arrange
        int days = 30;
        int month = 4;
        int year = 2022;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    @Test
    public void testInputRequires1Days4Months2022YearsEnsuresTrue() {

        //Arrange
        int days = 1;
        int month = 4;
        int year = 2022;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }


    /**
     * @subcontract 29 days in month {
     * @requires month == 2 && 1 <= day <= 29 &&
     * (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     * @ensures \result = true;
     * }
     */

    @Test
    public void testInputRequires29Days4Months2020YearsEnsuresTrue() {

        //Arrange
        int days = 29;
        int month = 2;
        int year = 2020;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    @Test
    public void testInputRequires29Days4Months2000YearsEnsuresTrue() {

        //Arrange
        int days = 29;
        int month = 2;
        int year = 2000;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    /**
     * @subcontract 28 days in month {
     * @requires month == 2 && 1 <= day <= 28 &&
     * (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
     * @ensures \result = true;
     * }
     */

    @Test
    public void testInputRequires28Days2Months2001YearsEnsuresTrue() {

        //Arrange
        int days = 28;
        int month = 2;
        int year = 2001;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }

    @Test
    public void testInputRequires28Days2Months1900YearsEnsuresTrue() {

        //Arrange
        int days = 28;
        int month = 2;
        int year = 1900;
        //Act
        Boolean result = DateTools.validateDate(days, month, year);
        //Assert
        Assert.assertEquals(true, result);

    }


    /**
     * @subcontract all other cases {
     * @requires no other accepting precondition;
     * @ensures \result = false;
     * }
     */

    // test to check if a 31-day-month is not allowed to have more days than 31.
    @Test
    public void testInputRequires32days1months2020YearEnsuresFalse() {
        //Arrange
        int day = 32;
        int month = 1;
        int year = 2020;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }

    //Test to check if a 30-day-month could not have 31 days.
    @Test
    public void testInputRequires31days4months2020YearEnsuresFalse() {
        //Arrange
        int day = 31;
        int month = 4;
        int year = 2020;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);

    }

    //tests to check if february in a non-leap year doesn't have more than 29 days
    @Test
    public void testInputRequires29days2months2019YearEnsuresFalse() {
        //Arrange
        int day = 29;
        int month = 2;
        int year = 2019;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);

    }
    //tests to check if february in a non-leap year doesn't have more than 29 days (specific for centuries which are dividable by four, but are not a leap year.)
    @Test
    public void testInputRequires29days2months1900YearEnsuresFalse() {
        //Arrange
        int day = 29;
        int month = 2;
        int year = 1900;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }

    //test to check if the day value can't be less than 1.
    @Test
    public void testInputRequires0days1months2020YearEnsuresFalse() {
        //Arrange
        int day = 0;
        int month = 1;
        int year = 2020;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }

    //test to check if the month value can't be less than 1.
    @Test
    public void testInputRequires1days0months2019YearEnsuresFalse() {
        //Arrange
        int day = 1;
        int month = 0;
        int year = 2019;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }

    //Test to ensure the date isn't from earlier than the year 1900
    @Test
    public void testInputRequires1Days1Months1899YearEnsuresFalse() {
        //Arrange
        int day = 1;
        int month = 1;
        int year = 1899;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }

    //Test to check if a date in future is not possible.
    @Test
    public void testInputRequiresMonth5Day15NextYearEnsuresFalse() {
        //Arrange
        int day = 15;
        int month = 5;
        int year = LocalDate.now().getYear() + 1;

        //Act
        Boolean result = DateTools.validateDate(day, month, year);

        //Assert
        Assert.assertEquals(false, result);
    }
}


