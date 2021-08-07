package Dao;

import Model.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReservationsDaoInterface {

    List<Reservation> getAllReservations();

    boolean addReservation(int user_id, int book_id);
    /**
     *
     * @param reservationId int
     * @return Reservation object with given reservationId
     *         null when there's no reservation with given reservationId
     * @throws SQLException
     */
    Reservation getReservationById(int reservationId) throws SQLException;

    /**
     *
     * @param userId int
     * @return List of reservations for user with given userId
     *         null when there are no reservations for this user
     * @throws SQLException
     */
    List<Reservation> getReservationByUser(int userId) throws SQLException;

    /**
     *
     * @param deadline Date
     * @return List of reservations with given deadline
     *         null when there are no reservation deadlines for that date
     * @throws SQLException
     */
    List<Reservation> getReservationByDeadline(Date deadline) throws SQLException;

    /**
     *
     * @param deadline Date
     * @return List of reservations of given user and with given deadline
     *         null when there are no reservation deadlines for that date
     * @throws SQLException
     */
    List<Reservation> getReservationByDeadlineAndUser(Date deadline, int userId) throws SQLException;

    void removeReservation(int reservation_id);
}
