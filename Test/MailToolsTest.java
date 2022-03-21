import InputVerification.MailTools;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailToolsTest {
    @Test
    public void testInputRequiresValidEmailEnsuresTrue() {
        // Arrange
        String email = "test@mail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testInputRequiresEmailWithMultipleSubdomainEnsuresFalse() {
        // Arrange
        String email = "test@student.mail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testInputRequiresEmailWithoutAtSignEnsuresFalse() {
        // Arrange
        String email = "testmail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testInputRequiresEmailWithoutSubdomainEnsuresFalse() {
        // Arrange
        String email = "test@.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertEquals(false, result);
    }


    @Test
    public void testInputRequiresEmailWithoutTLDPartEnsuresFalse() {
        // Arrange
        String email = "test@mail.";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertEquals(false, result);
    }
}