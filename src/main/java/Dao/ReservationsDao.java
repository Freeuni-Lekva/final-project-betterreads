package Dao;

import Model.Book;
import Model.Reservation;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReservationsDao implements ReservationsDaoInterface{
    private Connection connection;

    public ReservationsDao(String dbName) throws SQLException {
        this.connection = Connector.getConnection(dbName);
    }

    @Override
    public Reservation getReservationById(int reservationId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from reservations" +
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
                ("select * from reservations" +
                        "join users on reservations.user_id = users.user_id " +
                        "join books on books.book_id = reservations.book_id " +
                        "where reservations.user_id = ?;");
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Reservation> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(reservationBuilder(resultSet));
        }

        return null;
    }

    private Reservation reservationBuilder(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBook_id(resultSet.getInt("book_id"));
        book.setBook_name(resultSet.getString("book_name"));
        book.setAuthor_id(resultSet.getInt("author_id"));
        book.setBook_rating(resultSet.getDouble("book_rating"));
        book.setBook_description(resultSet.getString("book_description"));
        book.setRelease_year(resultSet.getInt("realise_year"));

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
    public List<Reservation> getReservationByDeadline(Date deadline) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from reservations" +
                        "join users on reservations.user_id = users.user_id " +
                        "join books on books.book_id = reservations.book_id " +
                        "where reservations.deadline = '?';");
        preparedStatement.setString(1, deadline.toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Reservation> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(reservationBuilder(resultSet));
        }

        return null;
    }
}