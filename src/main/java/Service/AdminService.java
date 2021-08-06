package Service;

import Constants.SharedConstants;
import Dao.AdminDao;
import Dao.Connector;
import Model.User;

import java.sql.SQLException;

public class AdminService implements AdminServiceInterface{
    private AdminDao adminDao;
    public AdminService(){
        try {
            adminDao = new AdminDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public boolean isAdmin(User user) {
        return adminDao.isAdmin(user);
    }
}
