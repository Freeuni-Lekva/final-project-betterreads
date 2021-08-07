package Service;

import Model.User;

import java.sql.SQLException;

public interface UserServiceInterface {
    /**
     *
     * @param email
     * @return true if user with this mail exists
     */
    boolean checkMailExists(String email) throws SQLException;

    /**
     *
     * @param username
     * @return true if user with this username exists
     */
    boolean checkUsernameExists(String username) throws SQLException;

    /**
     *
     * @param email
     * @return user with this mail
     */
    User getUserByMail(String email) throws SQLException;

    /**
     * @param username
     * @return user with this username
     */
    User getUserByUsername(String username) throws SQLException;

    /**
     *
     * @param user_id
     * @return user with given id
     */
    User getUserById(int user_id) throws SQLException;

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
    boolean addUser(String firstname, String lastname, String username, String password, String mail) throws SQLException;

}
