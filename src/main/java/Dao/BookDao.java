package Dao;

import Constants.SharedConstants;
import Model.Book;
import Model.Genre;
import Service.SearchService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookDao implements BookDaoInterface{
    Connection connection;

    public BookDao(Connection connection){
        this.connection = connection;
    }



    @Override
    public Book getBookById(int id) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where book_id = ?;");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) return null;
        return getBookByRS(rs);
    }


    @Override
    public List<Book> getAllBooks() {
        PreparedStatement statement;
        List<Book> bookList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("select * from books;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                bookList.add(getBookByRS(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookList;
    }

    @Override
    public Book getBook(int bookID) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where book_id = ?;");
        statement.setInt(1, bookID);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) return null;
        return getBookByRS(rs);
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

    @Override
    public List<Book> getBookByAuthor(int authorID) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where author_id = ?;");
        statement.setInt(1, authorID);
        ResultSet rs = statement.executeQuery();
        List<Book> result = new ArrayList<>();
        while(rs.next()){
            result.add(getBookByRS(rs));
        }
        return result;
    }

    @Override
    public List<Book> getBookByName(String name) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where book_name = ?;");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        List<Book> result = new ArrayList<>();
        while(rs.next()){
            result.add(getBookByRS(rs));
        }
        return result;
    }

    @Override
    public List<Book> getBookByYear(int from, int to) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books where release_year between ? and ?;");
        statement.setInt(1, from);
        statement.setInt(2, to);
        ResultSet rs = statement.executeQuery();
        List<Book> result = new ArrayList<>();
        while(rs.next()){
            result.add(getBookByRS(rs));
        }
        return result;
    }

    @Override
    public List<Book> getBookByGenre(Genre genre) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from books b"+
                "join book_genres bg on b.book_id = bg.book_id " +
                "where bg.genre_id = " + genre.getGenre_id() + ";");
        ResultSet rs = statement.executeQuery();
        List<Book> result = new ArrayList<>();
        while(rs.next()){
            result.add(getBookByRS(rs));
        }
        return result;
    }

    @Override
    public List<Book> getBookByGenre(String genre)  {
        PreparedStatement statement;
        List<Book> result = new ArrayList<>();
        try {
            statement = connection.prepareStatement("select * from book_genres bg "+
                    " join books b on b.book_id = bg.book_id " +
                    " join genres g on g.genre_id = bg.genre_id "+
                    " where g.genre_name = ?;");
            statement.setString(1, genre);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                result.add(getBookByRow(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private Book getBookByRow(ResultSet rs) throws SQLException {
        Book res = new Book();
        res.setBook_id(rs.getInt(1));
        res.setBook_name(rs.getString(4));
        res.setBook_description(rs.getString(5));
        res.setRelease_year(rs.getInt(6));
        res.setAuthor_id(rs.getInt(7));
        res.setBook_rating(rs.getDouble(8));
        res.setAvailable_count(rs.getInt(9));
        res.setBook_photo(rs.getString(10));
        return res;
    }

    @Override
    public List<Book> getAvailableBooks() {
        PreparedStatement statement;
        List<Book> bookList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("select * from books where available_count > 0;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                bookList.add(getBookByRS(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookList;
    }
}
