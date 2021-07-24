package Dao;

import Model.Book;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookShelfDao implements BookShelfDaoInterface{
    private final int ALREADY_READ = 1;
    private final int MARKED_FOR_FUTURE = 0;
    private String dbName;
    private Connection connection;
    public BookShelfDao(String dbName){
        this.dbName = dbName;
        try {
            connection = Connector.getConnection(dbName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
    public List<Book> getAllBooksInBookShelf(int user_id) {
        List<Book> resultList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf where book_id = ? join books on book_shelf.book_id = books.book_id");
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet.getInt("book_id"),resultSet.getString("book_name"),
                        resultSet.getString("book_description"),resultSet.getInt("release_year"),resultSet.getInt("author_id"),
                        resultSet.getDouble("book_rating"),resultSet.getInt("available_count"));

                resultList.add(book);
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Book> getAlreadyReadBooks(int user_id) {
        List<Book> resultList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf where book_id = ? and already_read = ? join books on book_shelf.book_id = books.book_id");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,ALREADY_READ);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet.getInt("book_id"),resultSet.getString("book_name"),
                        resultSet.getString("book_description"),resultSet.getInt("release_year"),resultSet.getInt("author_id"),
                        resultSet.getDouble("book_rating"),resultSet.getInt("available_count"));

                resultList.add(book);
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Book> getMarkedBooks(int user_id) {
        List<Book> resultList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf where book_id = ? and already_read = ? join books on book_shelf.book_id = books.book_id");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,MARKED_FOR_FUTURE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet.getInt("book_id"),resultSet.getString("book_name"),
                        resultSet.getString("book_description"),resultSet.getInt("release_year"),resultSet.getInt("author_id"),
                        resultSet.getDouble("book_rating"),resultSet.getInt("available_count"));

                resultList.add(book);
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }
    private Book createBook(int book_id, String book_name, String book_description, int release_year,int author_id,double book_rating, int available_count){
        Book book = new Book();
        book.setBook_id(book_id);
        book.setBook_name(book_name);
        book.setBook_description(book_description);
        book.setRelease_year(release_year);
        book.setAuthor_id(author_id);
        book.setBook_rating(book_rating);
        book.setAvailable_count(available_count);
        return book;
    }
}
