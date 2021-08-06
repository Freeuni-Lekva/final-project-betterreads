package Dao;

import Model.User;

import java.sql.*;

public class UserDao implements UserDaoInterface {
    private String dbName;
    private Connection connection;

    public UserDao(Connection connection){
        this.connection = connection;
    }

    public String getDBName(){
        return this.dbName;
    }

    @Override
    public boolean create(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement("insert into users(first_name," +
                    " last_name, email, username, password_hash) values(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, user.getFirst_name());
            pst.setString(2, user.getLast_name());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getUsername());
            pst.setString(5, user.getPassword_hash());
            pst.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByMail(String mail) {
        try {
            PreparedStatement pst = connection.prepareStatement("select * from users where email = ?;");
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
            PreparedStatement pst = connection.prepareStatement("select * from users where username = ?;");
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
            PreparedStatement pst = connection.prepareStatement("select * from users where user_id = ?;");
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
            PreparedStatement pst = connection.prepareStatement("select * from users where email = ?;");
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
            PreparedStatement pst = connection.prepareStatement("select * from users where username = ?;");
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
            resultSet.next();
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
