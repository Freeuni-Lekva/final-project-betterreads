package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.BookShelfDao;
import Dao.ReservationsDao;
import Model.Book;
import Model.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBooksService implements UserBooksServiceInterface{
    private static BookShelfDao bookShelfDao = new BookShelfDao(SharedConstants.DATA_BASE_NAME);

    private static ReservationsDao reservationsDao;

    static {
        try {
            reservationsDao = new ReservationsDao(SharedConstants.DATA_BASE_NAME);

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
            System.out.println(reservations.size());
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
        List<Book> books = bookShelfDao.getAlreadyReadBooks(user_id);
        return books;
    }

    @Override
    public List<Book> getBooksForFuture(int user_id) {
        List<Book> books = getReservedBooks(user_id);
        List<Book> forFuture = bookShelfDao.getMarkedBooks(user_id);
        List<Book> result = new ArrayList<>();
        for(Book book : forFuture){
            if(!books.contains(book)){
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public void addReservedBook(int user_id, int book_id) {
        reservationsDao.addReservation(user_id, book_id);
    }

    @Override
    public void addBooksForFuture(int user_id, int book_id) {
        System.out.println("notnull in service");
        bookShelfDao.addMarkedBook(user_id, book_id);
    }
}
