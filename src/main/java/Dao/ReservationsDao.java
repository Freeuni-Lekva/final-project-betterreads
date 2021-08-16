package Dao;

import Model.Book;
import Model.Reservation;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReservationsDao implements ReservationsDaoInterface{
    private Connection connection;

    public ReservationsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Reservation> getAllReservations() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("select * from reservations " +
                    "join users on reservations.user_id = users.user_id " +
                    "join books on books.book_id = reservations.book_id;");
            ResultSet rs = statement.executeQuery();
            List<Reservation> res = new ArrayList<>();
            while(rs.next())
                res.add(reservationBuilder(rs));
            return res;

    }

    @Override
    public boolean addReservation(int user_id, int book_id) throws SQLException {
        boolean exists = false;
            List<Reservation> reservations = getReservationByUser(user_id);

            PreparedStatement statement = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values (?, ?, ?)");
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            Date afterAdding2Weeks = new Date(timeInSecs + (14 * 24 * 60 * 60 * 1000));
            //2min after
            statement.setDate(3, afterAdding2Weeks);
            return statement.executeUpdate() != 0;

    }

    @Override
    public Reservation getReservationById(int reservationId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from reservations " +
                        "join users on reservations.user_id = users.user_id " +
                        "join books on books.book_id = reservations.book_id " +
                        "where reservations.reservation_id = ?;");
        preparedStatement.setInt(1, reservationId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            return reservationBuilder(resultSet);
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationByUser(int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from reservations " +
                        "join users on reservations.user_id = users.user_id " +
                        "join books on books.book_id = reservations.book_id " +
                        "where reservations.user_id = ?;");
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Reservation> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(reservationBuilder(resultSet));
        }

        return result;
    }

    private Reservation reservationBuilder(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBook_id(resultSet.getInt("book_id"));
        book.setBook_name(resultSet.getString("book_name"));
        book.setAuthor_id(resultSet.getInt("author_id"));
        book.setBook_photo(resultSet.getString("book_photo"));
        book.setBook_rating(resultSet.getDouble("book_rating"));
        book.setBook_description(resultSet.getString("book_description"));
        book.setRelease_year(resultSet.getInt("release_year"));

        User user = new User();
        user.setUser_id(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setFirst_name(resultSet.getString("first_name"));
        user.setLast_name(resultSet.getString("last_name"));
        user.setPassword_hash(resultSet.getString("password_hash"));

        return new Reservation(resultSet.getInt("reservation_id"),
                book, user, resultSet.getDate("deadline"));
    }

    @Override
    public List<Reservation> getReservationByDeadlineAndUser(Date deadline, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from reservations " +
                        "join users on reservations.user_id = users.user_id " +
                        "join books on books.book_id = reservations.book_id " +
                        "where reservations.deadline > ?" +
                        "and reservations.user_id = ?;");
        preparedStatement.setDate(1, deadline);
        preparedStatement.setInt(2, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Reservation> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(reservationBuilder(resultSet));
        }

        return result;
    }

    @Override
    public void removeReservation(int reservation_id) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("delete from reservations " +
                    "where reservation_id = ?;");
            statement.setInt(1, reservation_id);
            statement.executeUpdate();

    }
}
