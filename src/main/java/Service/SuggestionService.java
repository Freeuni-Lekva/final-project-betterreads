package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.Connector;
import Dao.RatingDao;
import Dao.UserDao;
import Model.Book;
import Model.Rating;
import Model.User;

import java.sql.SQLException;
import java.util.*;

public class SuggestionService implements SuggestionServiceInterface {

    @Override
    public List<Book> suggestByUser(User user) {
        try {
            UserDao userDao = new UserDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            BookDao bookDao = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            RatingDao ratingDao = new RatingDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            List<User> users = ratingDao.getAllUsers();
            List<Double> similarity = new ArrayList<>();
            for(User u : users){
                double d = countSimilarity(user, u);
                similarity.add(d);
            }
            List<Book> bookList = ratingDao.getAllBooks();
            List<Book> books = new ArrayList<>();
            for(Book b : bookList){
                Book book = bookDao.getBookById(b.getBook_id());
                double p = projectedRating(user, book);
                for(int i = 0; i < (int)(10 * p); i++){
                    books.add(book);
                }
            }
            Random rand = new Random();
            List<Book> ret = new ArrayList<>();
            for(int i = 0; i < 3; i++){
                if(books.size() == 0) break;
                int r = rand.nextInt(books.size());
                ret.add(books.get(r));
                Book rem = books.get(r);
                books.removeAll(Collections.singletonList(rem));
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double countSimilarity(User u1, User u2) throws SQLException {
        RatingDao ratingDao = null;
        try {
            ratingDao = new RatingDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Rating> ratings1 = ratingDao.getRatingsByUser(u1.getUser_id());
        double c = 0.0;
        int k = 0;
        for(Rating r1 : ratings1){
            Rating r2 = ratingDao.getRatingForBookByUser(u2.getUser_id(), r1.getBook_id());
            if(r2 != null) {
                c += (1 - (Math.abs(r1.getBook_rating() - r2.getBook_rating())));
                k++;
            }
        }
        c /= k;
        return c;
    }

    private double projectedRating(User user, Book book){
        UserDao userDao = null;
        try {
            userDao = new UserDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            BookDao bookDao = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            RatingDao ratingDao = new RatingDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            double projectedRating = 0.0;
            double sum = 0.0;
            List<Rating> br = ratingDao.getRatingsByBook(book.getBook_id());
            for(Rating r : br) {
                double c = countSimilarity(user, userDao.getUserById(r.getUser_id()));
                projectedRating += c * r.getBook_rating();
                sum += c;
            }
            return projectedRating / sum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
