package Service;

import Model.Book;

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
    List<Book> getAlreadyReadBooks(int user_id);

    /**
     *
     * @param user_id
     * @return List of marked, but not reserved books
     */
    List<Book> getBooksForFuture(int user_id);

    void addReservedBook(int user_id, int book_id);

    void addBooksForFuture(int user_id, int book_id);
}
