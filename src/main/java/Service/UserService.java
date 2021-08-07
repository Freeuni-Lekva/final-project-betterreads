package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.UserDao;
import Model.User;

import java.sql.SQLException;

public class UserService implements UserServiceInterface{
    private UserDao userDao;
    private HashService hashService;
    public UserService(){
        try {
            userDao = new UserDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        hashService = new HashService();
    }
    @Override
    public boolean checkMailExists(String email) throws SQLException {
        return userDao.containsUserByMail(email);
    }

    @Override
    public boolean checkUsernameExists(String username) throws SQLException {
        return userDao.containsUserByUserName(username);
    }

    @Override
    public User getUserByMail(String email) throws SQLException {
        return userDao.getUserByMail(email);
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getUserById(int user_id) throws SQLException { return userDao.getUserById(user_id); }

    @Override
    public boolean checkPassword(User user, String password) {
        return hashService.hashPassword(password).equals(user.getPassword_hash());
    }


    @Override
    public boolean addUser(String first_name, String last_name, String username, String password, String email) throws SQLException {
        User newUser = new User();
        newUser.setPassword_hash(password);
        newUser.setUsername(username);
        newUser.setFirst_name(first_name);
        newUser.setLast_name(last_name);
        newUser.setEmail(email);
        return userDao.create(newUser);
    }
}
