
import Constants.SharedConstants;
import Dao.BookShelfDao;
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
    private boolean created = false;
    private Connection c;

    protected void setUp() throws SQLException {
        String dbName = "testLibrary";
        this.c = Connector.getConnection(dbName);
        Statement stm = c.createStatement();
        stm.execute("use testLibrary;");

        //addInBookShelf(c);
        createBooks();
        created = true;
    }

    private void createBooks(){
        book2 = new Book();
        book2.setBook_name("Mglebi");
        book2.setAuthor_id(2);
        book2.setBook_description("Am cxovrebashi mgeli unda iyo da mgelze ufro mgeli");
        book2.setRelease_year(1999);
        book1 = new Book();
        book1.setBook_name("Harry Potter");
        book1.setAuthor_id(1);
        book1.setBook_description("oboli bichis tavgadasavali romelis jadokari agmochndeba");
        book1.setRelease_year(1980);
    }




    private void addInBookShelf(Connection c) throws SQLException {
        PreparedStatement pst5 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst5.setInt(1,1);
        pst5.setInt(2, 1);
        pst5.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
        pst5.execute();

        PreparedStatement pst6 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst6.setInt(1,1);
        pst6.setInt(2, 2);
        pst6.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
        pst6.execute();

        PreparedStatement pst7 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst7.setInt(1,2);
        pst7.setInt(2, 1);
        pst7.setInt(3, SharedConstants.ALREADY_READ);
        pst7.execute();

        PreparedStatement pst8 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst8.setInt(1,2);
        pst8.setInt(2, 2);
        pst8.setInt(3, SharedConstants.ALREADY_READ);
        pst8.execute();
    }





    @Test
    public void testgetMarkedBooks() throws SQLException {
        addUser1();
        addBook1();
        BookShelfDao bsd = new BookShelfDao("testLibrary");
        List<Book> list = bsd.getMarkedBooks(1);
        assertEquals(1, list.size());
        System.out.println();
        assertTrue(book1.equals(list.get(0)));
    }
    @Test
    public void testgetAlreadyReadBooks() throws SQLException {
        addUser2();
        addBook2();
        BookShelfDao bsd = new BookShelfDao("testLibrary");
        List<Book> list = bsd.getAlreadyReadBooks(2);
        assertEquals(1, list.size());

        assertTrue(book2.equals(list.get(0)));

    }

    @Test
    public void test1getAllBooksInBookShelfTest() throws SQLException {
        BookShelfDao bsd = new BookShelfDao("testLibrary");
        List<Book> list = bsd.getAllBooksInBookShelf(1);
        assertEquals(0, list.size());
    }

    private void addBook3() throws SQLException {
        PreparedStatement pst6 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst6.setInt(1,1);
        pst6.setInt(2, 2);
        pst6.setInt(3, SharedConstants.ALREADY_READ);
        pst6.execute();
    }

    private void addUser1() throws SQLException {
        PreparedStatement pst = c.prepareStatement("insert into users(first_name, last_name, email," +
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
        PreparedStatement pst2 = c.prepareStatement("insert into users(first_name, last_name, email," +
                "username, password_hash)" +
                " values(?, ?, ?, ?, ?);");
        pst2.setString(1,"Eleniko");
        pst2.setString(2, "Tsiramua");
        pst2.setString(3, "TsTs@gmail.com");
        pst2.setString(4,"TsTs123");
        pst2.setString(5, "ts789");
        pst2.execute();
    }

    private void addBook2() throws SQLException {
        PreparedStatement pst5 = c.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        pst5.setString(1,"Gocha manvelidze");
        pst5.execute();
        PreparedStatement pst3 = c.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");

        pst3.setString(1,"Mglebi");
        pst3.setString(2, "Am cxovrebashi mgeli unda iyo da mgelze ufro mgeli");
        pst3.setInt(3, 1999);
        pst3.setInt(4,2);
        pst3.setInt(5, 0);
        pst3.setInt(6, 17);
        pst3.execute();

        PreparedStatement pst6 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst6.setInt(1,2);
        pst6.setInt(2, 2);
        pst6.setInt(3, SharedConstants.ALREADY_READ);
        pst6.execute();

    }


    private void addBook1() throws SQLException {
        PreparedStatement pst1 = c.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        pst1.setString(1,"J.K.Rowling");
        pst1.execute();

        PreparedStatement pst4 = c.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");
        pst4.setString(1,"Harry Potter");
        pst4.setString(2, "oboli bichis tavgadasavali romelis jadokari agmochndeba");
        pst4.setInt(3, 1980);
        pst4.setInt(4,1);
        pst4.setInt(5, 0);
        pst4.setInt(6, 14);
        pst4.execute();




        PreparedStatement pst6 = c.prepareStatement("insert into book_shelf(user_id, book_id, already_read)" +
                " values(?, ?, ?);");
        pst6.setInt(1,1);
        pst6.setInt(2, 1);
        pst6.setInt(3, SharedConstants.MARKED_FOR_FUTURE);
        pst6.execute();
    }

}


