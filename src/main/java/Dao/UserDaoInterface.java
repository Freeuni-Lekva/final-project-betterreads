package Dao;

import Model.User;

import javax.xml.transform.Result;
import java.sql.ResultSet;

public interface UserDaoInterface {

    /**
     * @param user
     */
    boolean create(User user);

    /**
     * @param mail of user
     * @return user by mail
     */
    User getUserByMail(String mail);

    /**
     * @param username of user
     * @return user by username
     */
    User getUserByUsername(String username);

    /**
     * @param id of user
     * @return user by id
     */
    User getUserById(int id);

    /**
     * @param mail of user
     * @return true if passed mail exists, else false
     */
    boolean containsUserByMail(String mail);

    /**
     * @param username of user.
     * @return true if passed username exists, else false
     */
    boolean containsUserByUserName(String username);

}
