package Dao;

import Model.Book;
import Model.Rating;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDao implements RatingDaoInterface {
    private Connection connection;

    public RatingDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Rating> getRatingsByBook(int book_id) {
        List<Rating> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ratings " +
                    "WHERE book_id = ?;");
            preparedStatement.setInt(1, book_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                res.add(getRating(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    private Rating getRating(ResultSet resultSet){
        Rating r =  new Rating();
        try {
            r.setRating_id(resultSet.getInt("rating_id"));
            r.setBook_id(resultSet.getInt("book_id"));
            r.setUser_id(resultSet.getInt("user_id"));
            r.setBook_rating(resultSet.getInt("book_rating"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;
    }

    @Override
    public List<Rating> getRatingsByUser(int user_id) {
        List<Rating> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ratings " +
                    "WHERE user_id = ?;");
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                res.add(getRating(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Rating getRatingForBookByUser(int user_id, int book_id) {
        Rating res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ratings " +
                    "WHERE user_id = ? AND book_id = ?;");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, book_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                res = getRating(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    private User getUser(ResultSet resultSet){
        User u =  new User();
        try {
            u.setUser_id(resultSet.getInt("user_id"));
            u.setFirst_name(resultSet.getString("first_name"));
            u.setLast_name(resultSet.getString("last_name"));
            u.setUsername(resultSet.getString("username"));
            u.setEmail(resultSet.getString("email"));
            u.setPassword_hash(resultSet.getString("password_hash"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    private Book getBook(ResultSet resultSet){
        Book b = new Book();
        try {
            b.setBook_id(resultSet.getInt("book_id"));
            b.setBook_name(resultSet.getString("book_name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return b;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select distinct r.user_id, first_name, last_name, username, email, password_hash from ratings r join users u on u.user_id = r.user_id; ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                res.add(getUser(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT distinct b.book_id, book_name FROM ratings r " +
                    "JOIN books b ON b.book_id = r.book_id; ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                res.add(getBook(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean rateBook(int user_id, int book_id, int book_rating){
        if(getRatingForBookByUser(user_id, book_id) == null){

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO ratings (user_id, book_id, book_rating) " +
                        "values (?, ?, ?);");
                preparedStatement.setInt(1, user_id);
                preparedStatement.setInt(2, book_id);
                preparedStatement.setInt(3, book_rating);
                return preparedStatement.executeUpdate() != 0;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        } else {
            try {
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM ratings WHERE " +
                        "user_id = ? AND book_id = ?;");
                deleteStatement.setInt(1, user_id);
                deleteStatement.setInt(2, book_id);
                if(deleteStatement.executeUpdate() == 0) return false;

                return rateBook(user_id, book_id, book_rating);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean updateBookRating(Book book, double book_rating) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE books SET book_rating = ? WHERE book_id = ?;");
            statement.setDouble(1, book_rating);
            statement.setInt(2, book.getBook_id());
            statement.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
