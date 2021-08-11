package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.BookShelfDao;
import Dao.Connector;
import Dao.ReservationsDao;
import Model.Book;
import Model.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBooksService implements UserBooksServiceInterface{
    private static BookShelfDao bookShelfDao;

    static {
        try {
            bookShelfDao = new BookShelfDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ReservationsDao reservationsDao;

    static {
        try {
            reservationsDao = new ReservationsDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Book> getReservedBooks(int user_id) {
        long time = System.currentTimeMillis();
        Date currDate = new Date(time);
        List<Book> books = new ArrayList<>();
        try {
            List<Reservation> reservations = reservationsDao.getReservationByDeadlineAndUser(currDate,user_id);
            for(Reservation reservation : reservations){
                if(reservation.getDeadline().after(currDate)){
                    books.add(reservation.getReservedBook());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> getAlreadyReadBooks(int user_id) {
        List<Book> books = null;
        try {
            books = bookShelfDao.getAlreadyReadBooks(user_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksForFuture(int user_id)  {
        List<Book> forFuture = null;
        try {
            forFuture = bookShelfDao.getMarkedBooks(user_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> result = new ArrayList<>();
        for(Book book : forFuture){
            result.add(book);
        }
        return result;
    }

    @Override
    public void addReservedBook(int user_id, int book_id) {
        try {
            reservationsDao.addReservation(user_id, book_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addBooksForFuture(int user_id, int book_id) {
        System.out.println("notnull in service");
        try {
            bookShelfDao.addMarkedBook(user_id, book_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean hasBookForFuture(int user_id, int book_id) {
        List<Book> lst = getBooksForFuture(user_id);
        for(Book b : lst){
            if(b.getBook_id() == book_id)
                return true;
        }
        return false;
    }

    @Override
    public boolean hasBookReserved(int user_id, int book_id) {
        List<Book> lst = getReservedBooks(user_id);
        for(Book b : lst){
            if(b.getBook_id() == book_id)
                return true;
        }
        return false;
    }

    @Override
    public void removeBookFromFuture(int user_id, int book_id){
        try {
            bookShelfDao.removeBook(user_id, book_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void markBookAsRead(int user_id, int book_id) {
        try {
            bookShelfDao.markAsAlreadyRead(user_id, book_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasReadBook(int user_id, int book_id) throws SQLException {
        List<Book> read = bookShelfDao.getAlreadyReadBooks(user_id);
        for(int i = 0; i < read.size(); i++){
            if(read.get(i).getBook_id() == book_id)
                return true;
        }
        return false;
    }
}
