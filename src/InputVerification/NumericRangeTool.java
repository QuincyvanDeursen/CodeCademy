package InputVerification;

public class NumericRangeTool {
    public static boolean isValidPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            return true;
        }
        return false;
    }
}
