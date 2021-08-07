package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.ReviewDao;
import Model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService implements ReviewServiceInterface{
    private ReviewDao reviewDao;

    public ReviewService(){
        try {
            reviewDao = new ReviewDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Review> getReviewsByBookId(int book_id) {
        return reviewDao.getReviews(book_id);
    }

    @Override
    public List<Review> getReviewsByBookIdUserId(int book_id, int user_id) {
        return reviewDao.getReviewsByUserId(book_id, user_id);
    }

    @Override
    public void addReview(int user_id, int book_id, String comment, String date, int num_likes) {
        reviewDao.addReview(user_id, book_id, comment, date, num_likes);
    }
}
