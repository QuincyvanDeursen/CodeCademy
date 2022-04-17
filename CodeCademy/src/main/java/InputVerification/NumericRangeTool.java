package InputVerification;

public class NumericRangeTool {
    public static boolean isValidPercentage(int percentage) {
        return percentage >= 0 && percentage <= 100;
    }
}
