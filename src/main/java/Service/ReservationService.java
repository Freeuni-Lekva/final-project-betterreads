package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.Connector;
import Dao.ReservationsDao;
import Model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class ReservationService implements ReservationServiceInterface{
    ReservationsDao rd;
    BookDao bd;

    {
        try {
            bd = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            rd = new ReservationsDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ReservationService() throws SQLException {
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            return rd.getAllReservations();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeReservation(int reservation_id) {
        int bookID = 0;
        try {
            bookID = rd.getReservationById(reservation_id).getReservedBook().getBook_id();
            int currCount = bd.getBookCount(bookID);
            bd.setBookCount(bookID, currCount + 1);
            rd.removeReservation(reservation_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Reservation getReservationById(int reservation_id)   {
        try {
            return rd.getReservationById(reservation_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
