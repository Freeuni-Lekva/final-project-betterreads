package Dao;

import Model.Author;
import Model.Book;

import java.util.List;

public interface AuthorDaoInterface {
    /**
     *
     * @param book_id
     * @return Author
     */
    public Author getAuthor(int book_id);

    /**
     *
     * @param author_id
     * @return List of books by given author
     */
    public List<Book> getBooksByAuthor(int author_id);

    /**
     *
     * @param author_name
     * @return Author
     */
    public Author getAuthorByName(String author_name);

}
