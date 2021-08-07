package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.RatingDao;
import Model.Book;
import Model.Rating;

import java.util.List;
import java.sql.SQLException;

public class RatingService implements RatingServiceInterface{
    private RatingDao ratingDao;
    public RatingService(){
        try {
            ratingDao = new RatingDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void rateBook(int user_id, int book_id, int book_rating) {
        ratingDao.rateBook(user_id,book_id,book_rating);
    }

    @Override
    public int getBookRatingCount(int book_id) {
        List<Rating> ratings = ratingDao.getRatingsByBook(book_id);
        return ratings.size();
    }

    @Override
    public void updateBookRating(int user_id, Book book, int book_rating) {
        int num_ratings = getBookRatingCount(book.getBook_id());
        Rating oldRating = ratingDao.getRatingForBookByUser(user_id,book.getBook_id());
        double new_rating;
        if(oldRating != null){
            new_rating = (book.getBook_rating()*num_ratings - oldRating.getBook_rating() + book_rating)/num_ratings;
        } else if(book.getBook_rating() == 0){
            new_rating = book_rating;
        } else {
            new_rating = (book.getBook_rating()*num_ratings + book_rating)/(num_ratings + 1);
        }
        if(ratingDao.updateBookRating(book,new_rating)){
            book.setBook_rating(new_rating);
        }
    }

}
