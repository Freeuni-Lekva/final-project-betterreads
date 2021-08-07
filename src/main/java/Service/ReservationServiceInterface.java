package Service;

import Model.Reservation;

import java.util.List;

public interface ReservationServiceInterface {
    List<Reservation> getAllReservations();
    void removeReservation(int reservation_id);
    Reservation getReservationById(int reservation_id);
}
