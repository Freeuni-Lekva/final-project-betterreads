package Dao;

import Model.Book;
import Model.Rating;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface RatingDaoInterface {
    /**
     *
     * @param book_id
     * @return all ratings for given book
     */
    List<Rating> getRatingsByBook(int book_id) throws SQLException;

    /**
     *
     * @param user_id
     * @return all ratings written by given user
     */
    List<Rating> getRatingsByUser(int user_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param book_id
     * @return rating given user wrote about given book
     */
    Rating getRatingForBookByUser(int user_id, int book_id) throws SQLException;

    /**
     *
     * @return list of users who have written rating at least once
     */
    List<User> getAllUsers() throws SQLException;

    /**
     *
     * @return all books
     */
    List<Book> getAllBooks() throws SQLException;

    /**
     * saves given rating by given user for given book
     * if given book is already rated by this user
     * replaces previous rating
     * @param user_id
     * @param book_id
     * @param book_rating
     * @return true if successfully added to database
     */
    boolean rateBook(int user_id, int book_id, int book_rating) throws SQLException;

    boolean updateBookRating(Book book, double book_rating) throws SQLException;
}
