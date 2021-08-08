package Dao;

import Model.User;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDaoInterface {

    /**
     * @param user
     */
    boolean create(User user) throws SQLException;

    /**
     * @param mail of user
     * @return user by mail
     */
    User getUserByMail(String mail) throws SQLException;

    /**
     * @param username of user
     * @return user by username
     */
    User getUserByUsername(String username) throws SQLException;

    /**
     * @param id of user
     * @return user by id
     */
    User getUserById(int id) throws SQLException;

    /**
     * @param mail of user
     * @return true if passed mail exists, else false
     */
    boolean containsUserByMail(String mail) throws SQLException;

    /**
     * @param username of user.
     * @return true if passed username exists, else false
     */
    boolean containsUserByUserName(String username) throws SQLException;

}
