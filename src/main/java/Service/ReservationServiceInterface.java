package Service;

import Model.Reservation;

import java.sql.SQLException;
import java.util.List;

public interface ReservationServiceInterface {
    List<Reservation> getAllReservations();
    void removeReservation(int reservation_id) throws SQLException;
    Reservation getReservationById(int reservation_id);
}
