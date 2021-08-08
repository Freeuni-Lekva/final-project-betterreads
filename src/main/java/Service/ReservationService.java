package Service;

import Constants.SharedConstants;
import Dao.Connector;
import Dao.ReservationsDao;
import Model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class ReservationService implements ReservationServiceInterface{
    ReservationsDao rd;

    {
        try {
            rd = new ReservationsDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return rd.getAllReservations();
    }

    @Override
    public void removeReservation(int reservation_id) {
        rd.removeReservation(reservation_id);
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
