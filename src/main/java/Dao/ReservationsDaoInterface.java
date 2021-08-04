package Dao;

import Model.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReservationsDaoInterface {
    /**
     *
     * @param reservationId int
     * @return Reservation object with given reservationId
     *         null when there's no reservation with given reservationId
     * @throws SQLException
     */
    public Reservation getReservationById(int reservationId) throws SQLException;

    /**
     *
     * @param userId int
     * @return List of reservations for user with given userId
     *         null when there are no reservations for this user
     * @throws SQLException
     */
    public List<Reservation> getReservationByUser(int userId) throws SQLException;

    /**
     *
     * @param deadline Date
     * @return List of reservations with given deadline
     *         null when there are no reservation deadlines for that date
     * @throws SQLException
     */
    public List<Reservation> getReservationByDeadline(Date deadline) throws SQLException;

    /**
     *
     * @param deadline Date
     * @return List of reservations of given user and with given deadline
     *         null when there are no reservation deadlines for that date
     * @throws SQLException
     */
    public List<Reservation> getReservationByDeadlineAndUser(Date deadline, int userId) throws SQLException;
}
