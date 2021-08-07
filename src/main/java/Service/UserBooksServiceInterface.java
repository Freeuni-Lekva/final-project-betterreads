package Service;

import Model.Book;

import java.sql.SQLException;
import java.util.List;

public interface UserBooksServiceInterface {
    /**
     *
     * @param user_id
     * @return List of reserved books
     */
    List<Book> getReservedBooks(int user_id);

    /**
     *
     * @param user_id
     * @return List of already read books
     */
    List<Book> getAlreadyReadBooks(int user_id) throws SQLException;

    /**
     *
     * @param user_id
     * @return List of marked, but not reserved books
     */
    List<Book> getBooksForFuture(int user_id) throws SQLException;

    void addReservedBook(int user_id, int book_id);

    void addBooksForFuture(int user_id, int book_id) throws SQLException;

    boolean hasBookForFuture(int user_id, int book_id) throws SQLException;

    boolean hasBookReserved(int user_id, int book_id);

    void removeBookFromFuture(int user_id, int book_id) throws SQLException;

    /**
     * marks book as read
     * @param user_id
     * @param book_id
     */
    void markBookAsRead(int user_id, int book_id) throws SQLException;
}
