import Dao.*;
import Model.Reservation;
import junit.framework.TestCase;

import java.sql.*;
import java.util.List;

public class ReservationDaoTest extends TestCase {
    private String dbName;
    private Connection connection;

    private void helper(){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
            statement.setString(1,"Jiji");
            statement.setString(2,"Jijelava");
            statement.setString(3,"tjije19@gmail.com");
            statement.setString(4,"tjije19");
            statement.setString(5,"4321");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            PreparedStatement statement1 = connection.prepareStatement("insert into authors (author_name) " +
                    "values('Shota Rustaveli');");
            statement1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
            statement.setString(1,"Jiji2");
            statement.setString(2,"Jijelava2");
            statement.setString(3,"tjije192@gmail.com");
            statement.setString(4,"tjije192");
            statement.setString(5,"43212");
            statement.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement("insert into books " +
                    "(book_name, book_description, release_year, author_id, book_rating, available_count, book_photo) " +
                    "values (?, ?, ?, ?, ?, ?, ?);");
            statement2.setString(1,"book1");
            statement2.setString(2, "book1_description");
            statement2.setInt(3, 1);
            statement2.setInt(4, 1);
            statement2.setInt(5, 0);
            statement2.setInt(6, 1);
            statement2.setString(7, "");
            statement2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void helper2(){
        try {
            PreparedStatement ps5 = connection.prepareStatement("insert into authors (author_name) " +
                    "values('Fyodor Dostoevsky');");
            ps5.execute();

            PreparedStatement ps9 = connection.prepareStatement
                    ("insert into authors(author_name) values('Leo Tolstoy'); ");
            ps9.execute();

            PreparedStatement ps6 = connection.prepareStatement("insert into authors(author_name) values('William Shakespeare'); ");
            ps6.execute();

            PreparedStatement ps7 = connection.prepareStatement("insert into authors(author_name) values('Charles Dickens');");
            ps7.execute();

            PreparedStatement ps8 = connection.prepareStatement("insert into authors(author_name) values('Victor Hugo'); ");
            ps8.execute();
            PreparedStatement p1 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                    "           values(\"The Idiot\", \"The Idiot, novel by Fyodor Dostoyevsky, published in Russian as Idiot in\n" +
                    "                 in 1868�69. The narrative concerns the unsettling effect of the �primitive� Prince Myshkin on the \n" +
                    "                 sophisticated, conservative Yepanchin family and their friends. Myshkin visits the Yepanchins, and\n" +
                    "                 his odd manner and lack of concern for appearances quickly make him an object of fascination.\n" +
                    "                 His hosts, who are given to sensuality, acquisitiveness, and crime, test his moral compass. Myshkin \n" +
                    "                 maintains a guileless benevolence toward all, but, though his faith and radiant personality\n" +
                    "                 draw them to him, his message of service, compassion, and brotherly love finally fails.\", \n" +
                    "           1869,  1, 0, 4);");
            p1.execute();

            PreparedStatement p2 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                    "           values(\"War and Peace\", \"In Russia's struggle with Napoleon, Tolstoy saw a tragedy that involved all mankind.\n" +
                    "                 Greater than a historical chronicle, War and Peace is an affirmation of life itself, `a complete picture', \n" +
                    "                 as a contemporary reviewer put it, `of everything in which people find their happiness and greatness, their \n" +
                    "                 grief and humiliation'. Tolstoy gave his personal approval to this translation, published here in a new single  \n" +
                    "                 volume edition, which includes an introduction by Henry Gifford, and Tolstoy's important essay `Some Words about\n" +
                    "                 War and Peace'.\", \n" +
                    "           1867,  2, 0, 3);");
            p2.execute();

            PreparedStatement p3 = connection.prepareStatement("insert into genres(genre_name)\n" +
                    "           values(\"Mystery\"); ");
            p3.execute();

            PreparedStatement p4 = connection.prepareStatement("insert into genres (genre_name)\n" +
                    "           values(\"Thriller\"); ");
            p4.execute();

            PreparedStatement p5 = connection.prepareStatement("insert into genres(genre_name)\n" +
                    "           values(\"Horror\");");
            p5.execute();

            PreparedStatement p6 = connection.prepareStatement("insert into genres(genre_name)\n" +
                    "           values(\"Historical\");");
            p6.execute();

            PreparedStatement p7 = connection.prepareStatement("insert into genres(genre_name)\n" +
                    "           values(\"Romance\"); ");
            p7.execute();

            PreparedStatement p10 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                    "\t\t\tvalues(\"Crime and Punishment\",\"Crime and Punishment focuses on the mental anguish and moral dilemmas\n" +
                    "\t\t\t\t\tof Rodion Raskolnikov, an impoverished ex-student in Saint Petersburg who formulates a plan \n" +
                    "                    to kill an unscrupulous pawnbroker for her money\",\n" +
                    "                    1866,1,0,0);");
            p10.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;
    }

    public void testGetReservationById(){
        helper();
        ReservationsDao rd;
        BookDao bd;
        UserDao ud;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
            rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            ud = new UserDao(Connector.getConnection("testDataBase"));
            PreparedStatement statement1 = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values(1, 1, ?)");
            Date d = new Date(System.currentTimeMillis());
            statement1.setDate(1, d);
            statement1.execute();
            Reservation expected = new Reservation();
            expected.setReservationId(1);
            expected.setDeadline(d);
            expected.setReservedBook(bd.getBookById(1));
            expected.setUser(ud.getUserById(1));
            Reservation res = rd.getReservationById(1);
            assertEquals(res.getReservationId(), expected.getReservationId());
            //assertEquals(res.getDeadline(), expected.getDeadline());
            assertEquals(res.getUser(), expected.getUser());
            assertEquals(res.getReservedBook(), expected.getReservedBook());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testGetReservationByUser(){
        helper();
        ReservationsDao rd;
        BookDao bd;
        UserDao ud;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
            rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            ud = new UserDao(Connector.getConnection("testDataBase"));
            PreparedStatement statement1 = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values(1, 1, ?)");
            Date d = new Date(System.currentTimeMillis());
            statement1.setDate(1, d);
            statement1.execute();
            Reservation expected = new Reservation();
            expected.setReservationId(1);
            expected.setDeadline(d);
            expected.setReservedBook(bd.getBookById(1));
            expected.setUser(ud.getUserById(1));
            List<Reservation> result = rd.getReservationByUser(1);
            assertEquals(result.size(), 1);
            Reservation res = result.get(0);
            assertEquals(res.getReservationId(), expected.getReservationId());
            //assertEquals(res.getDeadline(), expected.getDeadline());
            assertEquals(res.getUser(), expected.getUser());
            assertEquals(res.getReservedBook(), expected.getReservedBook());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testGetReservationByUserAndDeadline(){
        helper();
        helper2();

        try {
            ReservationsDao rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            PreparedStatement statement = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values (?, ?, ?);");
            statement.setInt(1, 1);
            statement.setInt(2, 1);
            statement.setDate(3, Date.valueOf("2021-01-01"));
            statement.execute();
            statement.setInt(1, 1);
            statement.setInt(2, 2);
            statement.setDate(3, Date.valueOf("2020-01-01"));
            statement.execute();
            List<Reservation> result = rd.getReservationByDeadlineAndUser(Date.valueOf("2020-09-09"), 1);
            assertEquals(1, result.size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testGetAllReservations(){
        helper();
        helper2();

        try {
            ReservationsDao rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            PreparedStatement statement = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values (?, ?, ?);");
            statement.setInt(1, 1);
            statement.setInt(2, 1);
            statement.setDate(3, Date.valueOf("2021-01-01"));
            statement.execute();
            statement.setInt(1, 1);
            statement.setInt(2, 2);
            statement.setDate(3, Date.valueOf("2020-01-01"));
            statement.execute();
            List<Reservation> result = rd.getAllReservations();
            assertEquals(2, result.size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testAddReservation(){
        helper();
        ReservationsDao rd;
        try {
            rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            rd.addReservation(1, 1);
            PreparedStatement statement = connection.prepareStatement("select * from reservations " +
                    "where user_id = 1;");
            ResultSet rs = statement.executeQuery();
            if(rs.next()) assertFalse(rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testRemoveReservation(){
        helper();
        ReservationsDao rd;
        try {
            rd = new ReservationsDao(Connector.getConnection("testDataBase"));
            PreparedStatement statement1 = connection.prepareStatement("insert into reservations " +
                    "(user_id, book_id, deadline) values(1, 1, ?);");
            Date d = new Date(System.currentTimeMillis());
            statement1.setDate(1, d);
            statement1.execute();
            rd.removeReservation(1);
            PreparedStatement statement = connection.prepareStatement("select * from reservations " +
                    "where user_id = 1;");
            ResultSet rs = statement.executeQuery();
            assertFalse(rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
