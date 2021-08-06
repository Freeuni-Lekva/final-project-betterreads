
import Constants.SharedConstants;
import Dao.BookShelfDao;
import Dao.CDB;
import Dao.Connector;
import Model.Book;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BookShelfDaoTest extends TestCase {
    private Book book1;
    private Book book2;
    private Connection conn;

    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        String dbName = "testLibrary";
        this.conn = Connector.getConnection(dbName);
        Statement stm = conn.createStatement();
        stm.execute("use testLibrary;");
        createBooks();
    }

    private void createBooks(){
        book1 = new Book();
        book1.setBook_name("Harry Potter");
        book1.setAuthor_id(1);
        book1.setBook_description("oboli bichis tavgadasavali romelis jadokari agmochndeba");
        book1.setRelease_year(1980);
        book2 = new Book();
        book2.setBook_name("Mglebi");
        book2.setAuthor_id(1);
        book2.setBook_description("Am cxovrebashi mgeli unda iyo da mgelze ufro mgeli");
        book2.setRelease_year(1999);
    }

    @Test
    public void testgetMarkedBooks() throws SQLException {
        addUser1();
        addBook1();
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        List<Book> list = bsd.getMarkedBooks(1);

        assertEquals(1, list.size());
        assertTrue(book1.equals(list.get(0)));
    }
    @Test
    public void testgetAlreadyReadBooks() throws SQLException {
        addUser2();
        addBook2();
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        List<Book> list = bsd.getAlreadyReadBooks(1);

        assertEquals(1, list.size());
        assertTrue(book2.equals(list.get(0)));

    }

    @Test
    public void test1getAllBooksInBookShelfTest() throws SQLException {
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        List<Book> list = bsd.getAllBooksInBookShelf(1);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetMarkedBooks() throws SQLException {
        addUser1();
        addBook1();
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        List<Book> list = bsd.getMarkedBooks(1);
        assertEquals(1, list.size());
        assertTrue(book1.equals(list.get(0)));
    }

    @Test
    public void testAddMarkedBook() throws SQLException {
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        addUser1();
        addBook1();
        addBook2();
        assertFalse(bsd.addMarkedBook(1, 1));
    }

    @Test
    public void testRemoveBook() throws SQLException {
        BookShelfDao bsd = new BookShelfDao(Connector.getConnection("testLibrary"));
        addUser1();
        addBook1();
        addUser2();
        addBook2();
        bsd.removeBook(1, 1);
        bsd.removeBook(2, 2);
        List<Book> list = bsd.getAllBooksInBookShelf(1);
        assertEquals(0, list.size());
    }

    private void addUser1() throws SQLException {
        PreparedStatement pst = conn.prepareStatement("insert into users(first_name, last_name, email," +
                "username, password_hash)" +
                " values(?, ?, ?, ?, ?);");
        pst.setString(1,"Tornike");
        pst.setString(2, "Jijelava");
        pst.setString(3, "tornike01@gmail.com");
        pst.setString(4,"tttt");
        pst.setString(5, "12345");
        pst.execute();
    }

    private void addUser2() throws SQLException {
        PreparedStatement pst = conn.prepareStatement("insert into users(first_name, last_name, email," +
                "username, password_hash)" +
                " values(?, ?, ?, ?, ?);");
        pst.setString(1,"Eleniko");
        pst.setString(2, "Tsiramua");
        pst.setString(3, "TsTs@gmail.com");
        pst.setString(4,"TsTs123");
        pst.setString(5, "ts789");
        pst.execute();
    }

    private void addBook1() throws SQLException {
        PreparedStatement pst1 = conn.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        pst1.setString(1,"J.K.Rowling");
        pst1.execute();

        PreparedStatement pst2 = conn.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");
        pst2.setString(1,"Harry Potter");
        pst2.setString(2, "oboli bichis tavgadasavali romelis jadokari agmochndeba");
        pst2.setInt(3, 1980);
        pst2.setInt(4,1);
        pst2.setInt(5, 0);
        pst2.setInt(6, 14);
        pst2.execute();

        PreparedStatement pst3 = conn.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst3.setInt(1,1);
        pst3.setInt(2, 1);
        pst3.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
        pst3.execute();
    }

    private void addBook2() throws SQLException {
        PreparedStatement pst1 = conn.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        pst1.setString(1,"Gocha manvelidze");
        pst1.execute();

        PreparedStatement pst2 = conn.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");
        pst2.setString(1,"Mglebi");
        pst2.setString(2, "Am cxovrebashi mgeli unda iyo da mgelze ufro mgeli");
        pst2.setInt(3, 1999);
        pst2.setInt(4,1);
        pst2.setInt(5, 0);
        pst2.setInt(6, 17);
        pst2.execute();

        PreparedStatement pst3 = conn.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst3.setInt(1,1);
        pst3.setInt(2, 1);
        pst3.setInt(3, SharedConstants.ALREADY_READ);
        pst3.execute();
    }
}


