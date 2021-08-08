package Service;

import Model.Book;

import java.sql.SQLException;

public interface RatingServiceInterface {
    public void rateBook(int user_id, int book_id, int book_rating) throws SQLException;

    public int getBookRatingCount(int book_id) throws SQLException;

    public void updateBookRating(int user_id, Book book, int book_rating) throws SQLException;

}
