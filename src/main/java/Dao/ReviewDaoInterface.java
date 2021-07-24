package Dao;

import Model.Review;

import java.sql.Date;
import java.util.List;

public interface ReviewDaoInterface {
    /**
     * adds review to database
     * @param user_id user id in database
     * @param book_id book id in database
     * @param rating rating user gave this book
     * @param comment comment user wrote
     * @param date date review was posted
     * @param num_likes number of likes review has
     */
    void addReview(int user_id, int book_id, double rating, String comment, Date date, int num_likes);

    /**
     * @param book_id id of book
     * @return list of Reviews for given book
     */
    List<Review> getReviews(int book_id);
}
