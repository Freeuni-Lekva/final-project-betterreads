package Service;

import Model.Review;

import java.util.List;

public interface ReviewServiceInterface {
    /**
     *
     * @param book_id
     * @return list of all reviews written about the book with given id
     */
    List<Review> getReviewsByBookId(int book_id);

    /**
     *
     * @param book_id
     * @param user_id
     * @return list of reviews
     */
    List<Review> getReviewsByBookIdUserId(int book_id, int user_id);

    /**
     * adds review into the database
     * @param user_id
     * @param book_id
     * @param comment
     * @param date
     * @param num_likes
     */
    void addReview(int user_id, int book_id, String comment, String date, int num_likes);
}
