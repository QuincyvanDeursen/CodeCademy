import InputVerification.PostalCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostalCodeTest {

    //No parameter in the method throws Null Pointer Exception.
    @Test(expected = NullPointerException.class)
    public void testInputRequiresNullSignalsNullPointerException() {
        // Execute
        PostalCode.formatPostalCode(null);
    }

    //Spaces added and letters capitalized.
    @Test
    public void testInputRequires1234aAEnsures1234_AA(){
        // Execute
        String result = PostalCode.formatPostalCode("1111aA");

        // Assert
        assertEquals("1111 AA", result);
    }

    //Spaces trimmed
    @Test
    public void testInputRequires_1111_AA_Ensures1111_AA(){
        // Execute
        String result = PostalCode.formatPostalCode(" 1111 AA ");

        // Assert
        assertEquals("1111 AA", result);
    }

    //Correct postalcodes will not be altered.
    @Test
    public void testInputRequires1234_ABEnsures1234_AB(){
        // Execute
        String result = PostalCode.formatPostalCode("1234 AB");

        // Assert
        assertEquals("1234 AB", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputRequires1234AInvalidSignSignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1234A$");
    }

    //No more than 4 numbers allowed
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequires10000ABSignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("10000AB");
    }

    //No less than 4 numbers allowed
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequiresRequires333ABSignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("333AB");
    }

    //No less than 2 letters allowed
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequiresRequires1000ASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1000A");
    }

    //No more than 2 symbols allowed in the letter part.
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequires1000ABASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1000AAA");
    }

    //Number part cannot contain letters.
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequiresAAAA_AASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("123A AA");
    }

    //Letter part cannot contain numbers.
    @Test(expected = IllegalArgumentException.class)
    public void testInputRequires1234_5ASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1234 5A");
    }
}
