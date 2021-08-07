package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.ReviewDao;
import Model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService implements ReviewServiceInterface{
    private ReviewDao reviewDao;

    public ReviewService() throws SQLException {
        reviewDao = new ReviewDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
    }

    @Override
    public List<Review> getReviewsByBookId(int book_id) throws SQLException {
        return reviewDao.getReviews(book_id);
    }

    @Override
    public List<Review> getReviewsByBookIdUserId(int book_id, int user_id) throws SQLException {
        return reviewDao.getReviewsByUserId(book_id, user_id);
    }

    @Override
    public void addReview(int user_id, int book_id, String comment, String date, int num_likes) throws SQLException {
        reviewDao.addReview(user_id, book_id, comment, date, num_likes);
    }

    @Override
    public boolean hasUserLikedAlready(int review_id, int user_id) throws SQLException {
        return reviewDao.alreadyLiked(user_id, review_id);
    }

    @Override
    public boolean likeReview(int user_id, int review_id) throws SQLException {
        return reviewDao.likeReview(user_id, review_id);
    }

    @Override
    public boolean unlikeReview(int user_id, int review_id) throws SQLException {
        return reviewDao.unlikeReview(user_id, review_id);
    }

    @Override
    public boolean deleteReview(int review_id) throws SQLException {
        return reviewDao.deleteReview(review_id);
    }
}
