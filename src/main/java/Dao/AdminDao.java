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

    public boolean addBook(Book book, String[] genres){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into books(book_name, book_description, release_year," +
                    "author_id, book_rating, available_count,book_photo)" +
                    " values(?, ?, ?, ?, ?, ?,?);");
            preparedStatement.setString(1,book.getBook_name());
            preparedStatement.setString(2, book.getBook_description());
            preparedStatement.setInt(3, book.getRelease_year());
            preparedStatement.setInt(4,book.getAuthor_id());
            preparedStatement.setDouble(5,book.getBook_rating());
            preparedStatement.setInt(6, book.getAvailable_count());
            preparedStatement.setString(7,book.getBook_photo());
            if(preparedStatement.executeUpdate() == 1){
                int book_id = getBook(book.getBook_name()).getBook_id();
                insertBookGenres(book_id,genres);
                return true;
            } else{
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private Book getBook(String bookName) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where book_name = ?;");
        statement.setString(1, bookName);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) return null;
        return getBookByRS(rs);
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

    private Book getBookByRS(ResultSet rs) throws SQLException {
        Book res = new Book();
        res.setBook_id(rs.getInt(1));
        res.setBook_name(rs.getString(2));
        res.setBook_description(rs.getString(3));
        res.setRelease_year(rs.getInt(4));
        res.setAuthor_id(rs.getInt(5));
        res.setBook_rating(rs.getDouble(6));
        res.setAvailable_count(rs.getInt(7));
        res.setBook_photo(rs.getString(8));
        return res;
    }

    private int getGenreIdByName(String genre_name){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select * from genres where genre_name = ?;");
            statement.setString(1, genre_name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int genre_id = rs.getInt(1);
            return genre_id;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    private void insertBookGenres(int book_id, String[] genres){
        for(int i = 0; i < genres.length; i++){
            String genre_name = genres[i];
            int genre_id = getGenreIdByName(genre_name);
            try {
                PreparedStatement statement = connection.prepareStatement("insert into book_genres(book_id,genre_id) values(?,?);");
                statement.setInt(1,book_id);
                statement.setInt(2,genre_id);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
