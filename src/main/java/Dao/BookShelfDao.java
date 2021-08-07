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
    public List<Book> getAllBooksInBookShelf(int user_id) {
        List<Book> resultList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf join books on book_shelf.book_id = books.book_id where book_shelf.user_id = ?;");
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf " +
                    "join books on book_shelf.book_id = books.book_id where  book_shelf.user_id = ? and book_shelf.already_read = ?;");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2, SharedConstants.ALREADY_READ);
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_shelf " +
                    "join books on book_shelf.book_id = books.book_id where  book_shelf.user_id = ? and book_shelf.already_read = ?;");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2, SharedConstants.MARKED_FOR_FUTURE);
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
    public boolean addMarkedBook(int user_id, int book_id) {
        boolean exists = false;
        List<Book> markedBooks = getMarkedBooks(user_id);
        for(Book b : markedBooks){
            if(b.getBook_id() == book_id){
                exists = true;
                break;
            }
        }

        if(exists) return false;

        try {
            PreparedStatement statement = connection.prepareStatement("insert into book_shelf " +
                    "(user_id, book_id, already_read) values(?,?,?);");
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            statement.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
            return statement.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeBook(int user_id, int book_id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from book_shelf " +
                    "where user_id = ? and book_id = ?;");
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private Book createBook(int book_id, String book_name, String book_description, int release_year, int author_id, double book_rating, int available_count){
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

    @Override
    public void markAsAlreadyRead(int user_id, int book_id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from book_shelf " +
                    "where user_id = ? and book_id = ?;");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, book_id);
            preparedStatement.executeUpdate();
            System.out.println("movediii");
            PreparedStatement addStatement = connection.prepareStatement("insert into book_shelf(user_id, book_id, already_read) " +
                    "values(?, ?, ?);");
            addStatement.setInt(1, user_id);
            addStatement.setInt(2, book_id);
            addStatement.setInt(3, SharedConstants.ALREADY_READ);
            addStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
