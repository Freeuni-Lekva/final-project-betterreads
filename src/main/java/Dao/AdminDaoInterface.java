package Dao;

import Model.User;


import java.util.List;

public interface AdminDaoInterface {

    public List<User> getAllAdmins();

    public boolean isAdmin(User user);

}
