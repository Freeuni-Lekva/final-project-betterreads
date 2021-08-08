import Service.HashService;
import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;

public class HashServiceTest extends TestCase {

    public void testHashEquals() throws NoSuchAlgorithmException {
        HashService hashService = new HashService();
        String str1 = "testHashService";
        String str2 = "testHashService";
        assertEquals(hashService.hashPassword(str1),hashService.hashPassword(str2));
    }
    public void testHashNotEqual() throws NoSuchAlgorithmException {
        HashService hashService = new HashService();
        String str1 = "testHashService";
        String str2 = "!testHashService";
        assertFalse(hashService.hashPassword(str1).equals(hashService.hashPassword(str2)));
    }
}
