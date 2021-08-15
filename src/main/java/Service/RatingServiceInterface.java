package Service;

import Model.Book;

import java.sql.SQLException;

public interface RatingServiceInterface {
    void rateBook(int user_id, int book_id, int book_rating) throws SQLException;

    int getBookRatingCount(int book_id) throws SQLException;

    void updateBookRating(int user_id, Book book, int book_rating) throws SQLException;

    int getRatingForBookByUser(int user_id, int book_id);
}
