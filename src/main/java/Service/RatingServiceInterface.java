package Service;

import Model.Book;

public interface RatingServiceInterface {
    public void rateBook(int user_id, int book_id, int book_rating);

    public int getBookRatingCount(int book_id);

    public void updateBookRating(int user_id, Book book, int book_rating);

}
