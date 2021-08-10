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
    public List<Book> getAllBooks() throws SQLException {
        PreparedStatement statement;
        List<Book> bookList = new ArrayList<>();
            statement = connection.prepareStatement("select * from books;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                bookList.add(getBookByRS(rs));
            }

        return bookList;
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
    public List<Book> getBookByGenre(String genre) throws SQLException {
        PreparedStatement statement;
        List<Book> result = new ArrayList<>();
            statement = connection.prepareStatement("select * from book_genres bg "+
                    " join books b on b.book_id = bg.book_id " +
                    " join genres g on g.genre_id = bg.genre_id "+
                    " where g.genre_name = ?;");
            statement.setString(1, genre);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                result.add(getBookByRow(rs));
            }

        return result;
    }

    @Override
    public int getBookCount(int bookId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select available_count " +
                    "from books where book_id = ?;");
        statement.setInt(1, bookId);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) return rs.getInt(1);
        return 0;
    }

    @Override
    public void setBookCount(int bookId, int count) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update books set available_count " +
                "= ? where book_id = ?;");
        statement.setInt(1, count);
        statement.setInt(2, bookId);
        statement.execute();
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
    public List<Book> getAvailableBooks() throws SQLException {
        PreparedStatement statement;
        List<Book> bookList = new ArrayList<>();
            statement = connection.prepareStatement("select * from books where available_count > 0;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                bookList.add(getBookByRS(rs));
            }

        return bookList;
    }
}
