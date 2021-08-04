package Service;

import Model.User;

public interface UserServiceInterface {
    /**
     *
     * @param email
     * @return true if user with this mail exists
     */
    boolean checkMailExists(String email);

    /**
     *
     * @param username
     * @return true if user with this username exists
     */
    boolean checkUsernameExists(String username);

    /**
     *
     * @param email
     * @return user with this mail
     */
    User getUserByMail(String email);

    /**
     * @param username
     * @return user with this username
     */
    User getUserByUsername(String username);

    /**
     *
     * @param user
     * @return true if password for this user is correct
     */
    boolean checkPassword(User user,String password);

    /**
     *
     * @param firstname
     * @param lastname
     * @param username
     * @param password
     * @param mail
     * @return true if user successfully registrated
     */
    boolean addUser(String firstname, String lastname, String username, String password, String mail);

}
