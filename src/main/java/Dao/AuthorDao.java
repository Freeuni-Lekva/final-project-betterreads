package Dao;

import Model.Author;
import Model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao implements AuthorDaoInterface{
    private Connection connection;

    public AuthorDao(String dataBaseName) throws SQLException {
        connection = Connector.getConnection(dataBaseName);
    }
    @Override
    public Author getAuthor(int book_id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books " +
                    "join authors on books.author_id = authors.author_id " +
                    "where books.book_id = ?;");
            preparedStatement.setInt(1,book_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Author author = new Author(resultSet.getInt("author_id"),resultSet.getString("author_name"));
            return author;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBooksByAuthor(int author_id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where books.author_id = ?");
            preparedStatement.setInt(1,author_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(getBookByRS(resultSet));
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Book getBookByRS(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBook_id(rs.getInt(1));
        book.setBook_name(rs.getString(2));
        book.setBook_description(rs.getString(3));
        book.setRelease_year(rs.getInt(4));
        book.setAuthor_id(rs.getInt(5));
        book.setBook_rating(rs.getDouble(6));
        book.setAvailable_count(rs.getInt(7));
        book.setBook_photo(rs.getString(8));
        return book;
    }
}
