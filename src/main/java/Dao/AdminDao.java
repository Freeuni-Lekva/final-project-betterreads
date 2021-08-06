package Dao;

import Model.Book;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements AdminDaoInterface{
    private Connection connection;
    public AdminDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<User> getAllAdmins() {
        PreparedStatement statement;
        List<User> admins = new ArrayList<>();
        try {
            statement = connection.prepareStatement("select * from admins;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                admins.add(getUserByRS(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return admins;
    }

    @Override
    public boolean isAdmin(User user) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select * from admins where user_id = ?;");
            statement.setInt(1,user.getUser_id());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private User getUserByRS(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUser_id(rs.getInt(1));
        user.setFirst_name(rs.getString(2));
        user.setLast_name(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setUsername(rs.getString(5));
        user.setPassword_hash(rs.getString(6));

        return user;
    }
}
