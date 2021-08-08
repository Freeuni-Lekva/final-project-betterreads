package Dao;

import Model.Author;
import Model.Book;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDaoInterface {
    /**
     *
     * @param book_id
     * @return Author
     */
    Author getAuthor(int book_id) throws SQLException;

    /**
     *
     * @param author_id
     * @return List of books by given author
     */
    List<Book> getBooksByAuthor(int author_id) throws SQLException;

    boolean addAuthor(String author_name) throws SQLException;

    /**
     *
     * @param author_name
     * @return Author
     */
    public Author getAuthorByName(String author_name) throws SQLException;

}
