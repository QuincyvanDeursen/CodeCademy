package InputVerification;

import java.time.LocalDate;

public class DateTools {
    //This method validates the given date. It doesn't accept non possible dates or dates which are in the future.
    public static boolean validateDate(int day, int month, int year) {
        if (year < 1900 || year > LocalDate.now().getYear() || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        if ((month == 2 && (day >= 1 && day <= 29) && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))) {
            return true;
        }
        if ((month == 2 && (day >= 1 && day <= 28)) && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0))) {
            return true;
        }
        //Check to see if the 31-day-months do indeed have a maximum of 31 days.
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31 && day >= 1)) {
            return true;
        }
        //Check to see if the 30-day-months do indeed have a maximum of 31 days.
        return (month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30 && day >= 1);

    }


}




