import Constants.SharedConstants;
import Dao.AuthorDao;
import Dao.CDB;
import Dao.Connector;
import Model.Author;
import Model.Book;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoTest extends TestCase {
    private String dbName;
    private Connection connection;
    private Book book1;
    private Book book2;
    private Author author;

    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;

    }


    public void testGetAuthor() throws SQLException {
        initBooks();
        addAuthor();
        addBook1();
        AuthorDao authorDao = new AuthorDao(Connector.getConnection("testLibrary"));
        Author authorTest = authorDao.getAuthor(1);
        assertEquals(author.getAuthor_name(),authorTest.getAuthor_name());
    }

    public void testGetBooksByAuthor() throws SQLException {
        initBooks();
        addAuthor();
        addBook1();
        addBook2();
        AuthorDao authorDao = new AuthorDao(Connector.getConnection("testLibrary"));
        List<Book> result = authorDao.getBooksByAuthor(1);
        assertEquals(2,result.size());
        assertEquals(book1,result.get(0));
        assertEquals(book2,result.get(1));
    }

    public void testAddAuthor() throws SQLException {
        AuthorDao authorDao = new AuthorDao(Connector.getConnection("testLibrary"));
        assertEquals(true,authorDao.addAuthor("J.K. Rowling"));
        PreparedStatement statement = connection.prepareStatement("select * from authors;");
        ResultSet rs = statement.executeQuery();
        rs.next();
        assertEquals("J.K. Rowling",rs.getString("author_name"));
    }
    public void testGetAuthorByName() throws SQLException {
        addAuthor();
        AuthorDao authorDao = new AuthorDao(Connector.getConnection("testLibrary"));
        assertEquals(author.getAuthor_name(),authorDao.getAuthorByName("J.K. Rowling").getAuthor_name());
        assertEquals(null,authorDao.getAuthorByName("no author"));
    }

    private void addAuthor() throws SQLException {
        author = new Author();
        author.setAuthor_name("J.K. Rowling");
        PreparedStatement statement = connection.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        statement.setString(1,author.getAuthor_name());
        statement.executeUpdate();

    }
    private void initBooks(){
        book1 = new Book();
        book1.setBook_name("Harry Potter");
        book1.setAuthor_id(1);
        book1.setBook_description("oboli bichis tavgadasavali romelis jadokari agmochndeba");
        book1.setRelease_year(1980);
        book1.setBook_rating(0);
        book1.setBook_photo("photo");
        book1.setAvailable_count(5);
        book2 = new Book();
        book2.setBook_name("Harry Potter 2");
        book2.setAuthor_id(1);
        book2.setBook_description("upro saintereso tavgadasavali");
        book2.setRelease_year(1999);
        book2.setBook_rating(0);
        book2.setBook_photo("photo");
        book2.setAvailable_count(2);
    }


    private void addBook1() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");
        statement.setString(1,book1.getBook_name());
        statement.setString(2,book1.getBook_description());
        statement.setInt(3,book1.getRelease_year());
        statement.setInt(4,1);
        statement.setDouble(5,0);
        statement.setInt(6,book1.getAvailable_count());

        statement.executeUpdate();
    }
    private void addBook2() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into books(book_name, book_description, release_year," +
                "author_id, book_rating, available_count)" +
                " values(?, ?, ?, ?, ?, ?);");
        statement.setString(1,book2.getBook_name());
        statement.setString(2,book2.getBook_description());
        statement.setInt(3,book2.getRelease_year());
        statement.setInt(4,1);
        statement.setDouble(5,0);
        statement.setInt(6,book2.getAvailable_count());

        statement.executeUpdate();
    }
}
