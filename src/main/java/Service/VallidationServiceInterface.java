package Service;

public interface VallidationServiceInterface {

    /**
     *
     * @param firstname
     * @return true if first name is correct
     */
    boolean isFirstNameCorrect(String firstname);

    /**
     *
     * @param lastname
     * @return true is last name is correct
     */
    boolean isLastNameCorrect(String lastname);

    /**
     *
     * @param mail
     * @return true if mail is correct
     */
    boolean isMailCorrect(String mail);

    /**
     *
     * @param password
     * @return true if password is correct
     */
    boolean isPasswordCorrect(String password);

    /**
     *
     * @param userName
     * @return true is username is empty
     */
    boolean isUsernameEmpty(String userName);

    /**
     *
     * @param firstname
     * @param lastname
     * @param mail
     * @param password
     * @param username
     * @return error message if parametrs are not correct
     */
    String getErrorMessage(String firstname, String lastname, String mail,
                           String password, String username);

}
