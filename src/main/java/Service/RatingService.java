package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.RatingDao;

import java.sql.SQLException;

public class RatingService implements RatingServiceInterface{
    public RatingService(){

    }
    @Override
    public void rateBook(int user_id, int book_id, int book_rating) {
        try {
            RatingDao ratingDao = new RatingDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            ratingDao.rateBook(user_id, book_id, book_rating);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
