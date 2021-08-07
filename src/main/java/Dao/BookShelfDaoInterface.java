package Dao;

import Model.Book;

import java.util.List;

public interface BookShelfDaoInterface {
    /**
     *
     * @param user_id
     * @return List of all books that are stored in book shelf
     */
    List<Book> getAllBooksInBookShelf(int user_id);

    /**
     *
     * @param user_id
     * @return List of books that user has already read
     */

    List<Book> getAlreadyReadBooks(int user_id);

    /**
     *
     * @param user_id
     * @return List of books that user has marked to read for future
     */
    List<Book> getMarkedBooks(int user_id);

    /**
     *  @param user_id
     * @param book_id
     * @return
     */
    boolean addMarkedBook(int user_id, int book_id);

    void removeBook(int user_id, int book_id);

    /**
     * marks book as read
     * @param user_id
     * @param book_id
     */
    void markAsAlreadyRead(int user_id, int book_id);

}
