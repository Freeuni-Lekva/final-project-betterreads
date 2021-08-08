import Dao.CDB;
import Dao.Connector;
import Dao.RatingDao;
import Dao.UserDao;
import Model.Book;
import Model.Rating;
import Model.User;
import junit.framework.TestCase;

import java.sql.*;
import java.util.List;

public class RatingDaoTest extends TestCase {
    private String dbName;
    private Connection connection;

    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement avt = connection.prepareStatement("insert into authors(author_name) values(?);");
        avt.setString(1,"avtori");
        avt.executeUpdate();

        PreparedStatement statement = connection.prepareStatement("insert into books(book_name, release_year, author_id, available_count) values(?,?,?,?);");
        statement.setString(1,"wigni");
        statement.setInt(2,2021);
        statement.setInt(3,1);
        statement.setInt(4,1);
        statement.executeUpdate();

        PreparedStatement user = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        user.setString(1,"first");
        user.setString(2,"last");
        user.setString(3,"flast@gmail.com");
        user.setString(4,"f.last");
        user.setString(5,"1234");
        user.executeUpdate();

    }

    public void testGetRatingsByBook() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        List<Rating> list = dao.getRatingsByBook(1);
        assertEquals(0, list.size());

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        list = dao.getRatingsByBook(1);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0).getBook_rating());
    }

    public void testGetRatingsByUser() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        List<Rating> list = dao.getRatingsByUser(1);
        assertEquals(0, list.size());

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        list = dao.getRatingsByUser(1);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0).getBook_rating());
    }

    public void testGetRatingForBookByUser() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        Rating r = dao.getRatingForBookByUser(1, 1);
        assertNull(r);

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        r = dao.getRatingForBookByUser(1, 1);
        assertNotNull(r);
        assertEquals(5, r.getBook_rating());
    }

    public void testGetAllUsers() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        List<User> users = dao.getAllUsers();
        assertEquals(0, users.size());

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        users = dao.getAllUsers();
        assertEquals(1, users.size());
    }

    public void testGetAllBooks() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        List<Book> books = dao.getAllBooks();
        assertEquals(0, books.size());

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        books = dao.getAllBooks();
        assertEquals(1, books.size());
    }

    public void testRateBook() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));
        boolean b = dao.rateBook(1, 1, 5);
        assertTrue(b);

        Rating r = dao.getRatingForBookByUser(1,1);
        assertEquals(5, r.getBook_rating());
        assertEquals(1, r.getBook_id());
        assertEquals(1, r.getUser_id());

        b = dao.rateBook(1, 1, 4);
        assertTrue(b);

        r = dao.getRatingForBookByUser(1,1);
        assertEquals(4, r.getBook_rating());
        assertEquals(1, r.getBook_id());
        assertEquals(1, r.getUser_id());
    }

    public void testUpdateBookRating() throws SQLException {
        RatingDao dao = null;
        dao = new RatingDao(Connector.getConnection(dbName));

        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement rate = connection.prepareStatement("insert into ratings(user_id,book_id,book_rating) values(?,?,?)");
        rate.setInt(1,1);
        rate.setInt(2,1);
        rate.setInt(3,5);
        rate.executeUpdate();

        Book b = dao.getAllBooks().get(0);
        dao.updateBookRating(b, 5);

        PreparedStatement book = connection.prepareStatement("select * from books;");
        ResultSet rs = book.executeQuery();
        rs.next();
        assertEquals(5, rs.getInt("book_rating"));
    }
}
