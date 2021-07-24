package Dao;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements UserDaoInterface {
    private String dbName;
    private Connection connection;

    public UserDao(String dbName){
        this.dbName = dbName;
        try {
            connection = Connector.getConnection(dbName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getDBName(){
        return this.dbName;
    }

    @Override
    public boolean create(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement("insert into user (first_name," +
                    " last_name, email, username, password_hash) values(?,?,?,?,?)");
            pst.setString(1, user.getFirst_name());
            pst.setString(2, user.getLast_name());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getUsername());
            pst.setString(5, user.getPassword_hash());
            if (pst.executeUpdate() == 0) {
                return false;
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByMail(String mail) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from Users where mail = ?");
            pst.setString(1, mail);
            ResultSet resSet = pst.executeQuery();
            return createUserByRS(resSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from Users where username = ?");
            pst.setString(1, username);
            ResultSet resSet = pst.executeQuery();
            return createUserByRS(resSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from Users where user_id = ?");
            pst.setInt(1, id);
            ResultSet resSet = pst.executeQuery();
            return createUserByRS(resSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean containsUserByMail(String mail) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from Users where mail = ?");
            pst.setString(1, mail);
            ResultSet resSet = pst.executeQuery();
            return resSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean containsUserByUserName(String username) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from Users where username = ?");
            pst.setString(1, username);
            ResultSet resSet = pst.executeQuery();
            return resSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private User createUserByRS(ResultSet resultSet) {
        try {
            User user = new User();
            user.setUser_id(resultSet.getInt(1));
            user.setFirst_name(resultSet.getString(2));
            user.setLast_name(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setUsername(resultSet.getString(5));
            user.setPassword_hash(resultSet.getString(6));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
