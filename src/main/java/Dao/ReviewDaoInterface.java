package Dao;

import Model.Review;

import java.sql.Date;
import java.util.List;

public interface ReviewDaoInterface {
    /**
     * adds review to database
     * @param user_id user id in database
     * @param book_id book id in database
     * @param comment comment user wrote
     * @param date date review was posted
     * @param num_likes number of likes review has
     */
    void addReview(int user_id, int book_id, String comment, String date, int num_likes);

    /**
     * @param book_id id of book
     * @return list of Reviews for given book
     */
    List<Review> getReviews(int book_id);

    /**
     *
     * @param book_id
     * @param user_id
     * @return list of reviews for given book written by given user
     */
    List<Review> getReviewsByUserId(int book_id, int user_id);
}
