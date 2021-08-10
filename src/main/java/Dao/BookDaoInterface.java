package Dao;

import Model.Book;
import Model.Genre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDaoInterface {

    /**
     *
     * @return list of all books
     */
    List<Book> getAllBooks() throws SQLException;


    /**
     *
     * @param id
     * @return
     */
    public Book getBookById(int id) throws SQLException;

    /**
     *
     * @return list of available books
     */
    public List<Book> getAvailableBooks() throws SQLException;

    /**
     *
     * @param genre
     * @return list of books of this genre
     */
    List<Book> getBookByGenre(String genre) throws SQLException;

    /**
     *
     * @param bookId
     * @return
     * @throws SQLException
     */
    int getBookCount(int bookId) throws SQLException;

    /**
     *
     * @param bookId
     * @param count
     * @throws SQLException
     */
    void setBookCount(int bookId, int count) throws SQLException;
}
