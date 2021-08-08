
import Constants.SharedConstants;
import Service.VallidationService;
import junit.framework.TestCase;
import org.junit.Test;

public class VallidationServiceTest extends TestCase{
    private VallidationService vs;

    protected void setUp() {
        vs = new VallidationService();
    }

    @Test
    public void testIsFirstNameCorrect(){
        assertTrue(vs.isFirstNameCorrect("Tornike"));
        assertTrue(vs.isFirstNameCorrect("Giorgi"));
        assertFalse(vs.isFirstNameCorrect(""));
    }

    @Test
    public void testIsLastNameCorrect(){
        assertTrue(vs.isLastNameCorrect("Jiji"));
        assertTrue(vs.isLastNameCorrect("Lekva"));
        assertFalse(vs.isLastNameCorrect(""));
    }

    @Test
    public void testIsUsernameCorrect(){
        assertFalse(vs.isUsernameEmpty("Jiji123"));
        assertFalse(vs.isUsernameEmpty("Lekva321"));
        assertTrue(vs.isUsernameEmpty(""));
    }

    @Test
    public void testIsMailCorrect(){
        assertTrue(vs.isMailCorrect("Jiji@freeuni.edu.ge"));
        assertTrue(vs.isMailCorrect("Lekva@gmail.com"));
        assertFalse(vs.isFirstNameCorrect(""));
    }

    @Test
    public void testPasssword(){
        assertTrue(vs.isPasswordCorrect("4815162342"));
        assertTrue(vs.isPasswordCorrect("MaqsiMaqsi123"));
        assertFalse(vs.isPasswordCorrect("12345"));
    }

    @Test
    public void testGetErrorMassage(){
        String er1 = vs.getErrorMessage("", "Jijealva",
                "Jiji123", "tgmail.com", "32");
        String er2 = vs.getErrorMessage("Tornike", "",
                "Jiji123", "t@gmail.com", "32");
        String er3 = vs.getErrorMessage("Tornike", "Jijealva",
                "", "t@gmail.com", "32");
        String er4 = vs.getErrorMessage("Tornike", "Jijealva",
                "Jiji123,", "wgergewrgw", "");
        String er5 = vs.getErrorMessage("Tornike", "Jijealva",
                "eqw@gmail.com", "", "3r2");
        String er6 = vs.getErrorMessage("Tornike", "Jijealva",
                "t@gmail.com", "rrweqqer2", "Jiji123");

        assertTrue(er1.equals(SharedConstants.FIRST_NAME_ERROR));
        assertTrue(er2.equals(SharedConstants.LAST_NAME_ERROR));
        assertTrue(er3.equals(SharedConstants.EMAIL_ERROR));
        assertTrue(er4.equals(SharedConstants.USERNAME_ERROR));
        assertTrue(er5.equals(SharedConstants.PASSWORD_ERROR));
        assertTrue(er6.length() == 0);
    }
}
