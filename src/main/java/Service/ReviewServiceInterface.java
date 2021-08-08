package Service;

import Model.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewServiceInterface {
    /**
     *
     * @param book_id
     * @return list of all reviews written about the book with given id
     */
    List<Review> getReviewsByBookId(int book_id) throws SQLException;

    /**
     *
     * @param book_id
     * @param user_id
     * @return list of reviews
     */
    List<Review> getReviewsByBookIdUserId(int book_id, int user_id) throws SQLException;

    /**
     * adds review into the database
     * @param user_id
     * @param book_id
     * @param comment
     * @param date
     * @param num_likes
     */
    void addReview(int user_id, int book_id, String comment, String date, int num_likes) throws SQLException;

    /**
     *
     * @param review_id
     * @param user_id
     * @return true if given user has already liked given review
     */
    boolean hasUserLikedAlready(int review_id, int user_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param review_id
     * @return true if like data has been successfully added to database, false otherwise
     */
    boolean likeReview(int user_id, int review_id) throws SQLException;

    /**
     *
     * @param user_id
     * @param review_id
     * @return true if unlike data has been successfully deleted from database, false otherwise
     */
    boolean unlikeReview(int user_id, int review_id) throws SQLException;

    /**
     *
     * @param review_id
     * @return true if review was successfully deleted from database, false otherwise
     */
    boolean deleteReview(int review_id) throws SQLException;
}
