package Service;

import java.security.NoSuchAlgorithmException;

public interface HashServiceInterface {
    /**
     *
     * @param password: original password that client knows
     * @return hashed password
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException;
}
