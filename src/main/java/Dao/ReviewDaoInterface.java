package Dao;

import Model.Review;

import java.sql.Date;
import java.sql.SQLException;
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
    void addReview(int user_id, int book_id, String comment, String date, int num_likes) throws SQLException;

    /**
     * @param book_id id of book
     * @return list of Reviews for given book
     */
    List<Review> getReviews(int book_id) throws SQLException;

    /**
     *
     * @param book_id
     * @param user_id
     * @return list of reviews for given book written by given user
     */
    List<Review> getReviewsByUserId(int book_id, int user_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param review_id
     * @return true if given user has already liked given review, false otherwise
     */
    boolean alreadyLiked(int user_id, int review_id) throws SQLException;

    /**
     *
     * @param review_id
     * @return
     */
    int getNumLikesById(int review_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param review_id
     * @return returns true if like has been added to database, false otherwise
     */
    boolean likeReview(int user_id, int review_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param review_id
     * @return true if like was successfully deleted from database, false otherwise
     */
    boolean unlikeReview(int user_id, int review_id) throws SQLException;

    /**
     *
     * @param review_id
     * @return true if review was successfully deleted from database, false otherwise
     */
    boolean deleteReview(int review_id) throws SQLException;
}
