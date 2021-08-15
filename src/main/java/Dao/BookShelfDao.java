package Dao;

import Constants.SharedConstants;
import Model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookShelfDao implements BookShelfDaoInterface{

    private Connection connection;
    public BookShelfDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Book> getAllBooksInBookShelf(int user_id) throws SQLException {
        List<Book> resultList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf join books on book_shelf.book_id = books.book_id where book_shelf.user_id = ?;");
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet);
                resultList.add(book);
            }
            return resultList;
    }

    @Override
    public List<Book> getAlreadyReadBooks(int user_id) throws SQLException {
        List<Book> resultList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf " +
                    "join books on book_shelf.book_id = books.book_id where  book_shelf.user_id = ? and book_shelf.already_read = ?;");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2, SharedConstants.ALREADY_READ);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet);

                resultList.add(book);
            }
        return resultList;
    }

    @Override
    public List<Book> getMarkedBooks(int user_id) throws SQLException {
        List<Book> resultList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf " +
                    "join books on book_shelf.book_id = books.book_id where  book_shelf.user_id = ? and book_shelf.already_read = ?;");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2, SharedConstants.MARKED_FOR_FUTURE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Book book = createBook(resultSet);

                resultList.add(book);
            }
            return resultList;
    }

    @Override
    public boolean addMarkedBook(int user_id, int book_id) throws SQLException {
        boolean exists = false;
        List<Book> markedBooks = getMarkedBooks(user_id);
        for(Book b : markedBooks){
            if(b.getBook_id() == book_id){
                exists = true;
                break;
            }
        }

        if(exists) return false;
            PreparedStatement statement = connection.prepareStatement("insert into book_shelf " +
                    "(user_id, book_id, already_read) values(?,?,?);");
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            statement.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
            return statement.executeUpdate() != 0;
    }

    @Override
    public void removeBook(int user_id, int book_id) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("delete from book_shelf " +
                    "where user_id = ? and book_id = ?;");
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            statement.executeUpdate();
    }


    private Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBook_id(resultSet.getInt("book_id"));
        book.setBook_name(resultSet.getString("book_name"));
        book.setBook_description(resultSet.getString("book_description"));
        book.setBook_photo(resultSet.getString("book_photo"));
        book.setRelease_year(resultSet.getInt("release_year"));
        book.setAuthor_id(resultSet.getInt("author_id"));
        book.setBook_rating(resultSet.getDouble("book_rating"));
        book.setAvailable_count(resultSet.getInt("available_count"));
        return book;
    }

    @Override
    public void markAsAlreadyRead(int user_id, int book_id) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from book_shelf " +
                    "where user_id = ? and book_id = ?;");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, book_id);
            preparedStatement.executeUpdate();

            PreparedStatement addStatement = connection.prepareStatement("insert into book_shelf(user_id, book_id, already_read) " +
                    "values(?, ?, ?);");
            addStatement.setInt(1, user_id);
            addStatement.setInt(2, book_id);
            addStatement.setInt(3, SharedConstants.ALREADY_READ);
            addStatement.executeUpdate();
    }
}
