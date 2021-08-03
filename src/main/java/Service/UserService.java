package Service;

import Constants.SharedConstants;
import Dao.UserDao;
import Model.User;

public class UserService implements UserServiceInterface{
    private UserDao userDao;
    private HashService hashService;
    public UserService(){
        userDao = new UserDao(SharedConstants.DATA_BASE_NAME);
        hashService = new HashService();
    }
    @Override
    public boolean checkMailExists(String email) {
        return userDao.containsUserByMail(email);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userDao.containsUserByUserName(username);
    }

    @Override
    public User getUserByMail(String email) {
        return userDao.getUserByMail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return hashService.hashPassword(password).equals(user.getPassword_hash());
    }
}
