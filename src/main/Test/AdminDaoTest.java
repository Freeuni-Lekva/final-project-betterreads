import Dao.AdminDao;
import Dao.CDB;
import Dao.Connector;
import Model.Book;
import Model.User;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoTest extends TestCase {
    private String dbName;
    private Connection connection;
    private User user;
    private User user2;
    private Book book1;

    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;
    }
    public void testIsAdmin() throws SQLException {
        initUser();
        PreparedStatement statement = connection.prepareStatement("insert into admins(user_id) values(?);");
        statement.setInt(1,1);
        statement.executeUpdate();
        AdminDao adminDao = new AdminDao(Connector.getConnection("testLibrary"));
        assertEquals(true,adminDao.isAdmin(user));
        assertEquals(false,adminDao.isAdmin(user2));


    }
    public void testAddBook() throws SQLException {
        initAuthorAndGenres();
        PreparedStatement statement1 = connection.prepareStatement("select * from books;");
        ResultSet resultSet = statement1.executeQuery();
        assertEquals(false,resultSet.next());
        AdminDao adminDao = new AdminDao(Connector.getConnection("testLibrary"));
        String[] genres = {"Fantasy"};
        adminDao.addBook(book1,genres);
        PreparedStatement statement2 = connection.prepareStatement("select  * from books;");
        resultSet = statement2.executeQuery();
        assertEquals(true,resultSet.next());
    }
    private void initUser() throws SQLException {
        user = new User();
        user.setUsername("username");
        user.setFirst_name("name");
        user.setLast_name("lastName");
        user.setEmail("user@gmail.com");
        user.setPassword_hash("1234567");
        user.setUser_id(1);
        PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        statement.setString(1,user.getFirst_name());
        statement.setString(2,user.getLast_name());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getUsername());
        statement.setString(5, user.getPassword_hash());
        statement.executeUpdate();

        user2 = new User();
        user2.setUsername("username2");
        user2.setFirst_name("name2");
        user2.setLast_name("lastName2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword_hash("12345678");
        user2.setUser_id(2);
        PreparedStatement statement2 = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        statement2.setString(1,user2.getFirst_name());
        statement2.setString(2,user2.getLast_name());
        statement2.setString(3, user2.getEmail());
        statement2.setString(4, user2.getUsername());
        statement2.setString(5, user2.getPassword_hash());
        statement2.executeUpdate();
    }
    private void initAuthorAndGenres() throws SQLException {
        book1 = new Book();
        book1.setBook_name("Harry Potter");
        book1.setAuthor_id(1);
        book1.setBook_description("oboli bichis tavgadasavali romelis jadokari agmochndeba");
        book1.setRelease_year(1980);
        book1.setBook_rating(0);
        book1.setBook_photo("photo");
        book1.setAvailable_count(5);
        PreparedStatement st = connection.prepareStatement("insert into authors(author_name)" +
                " values(?);");
        st.setString(1,"J.K.Rowling");
        st.executeUpdate();

        PreparedStatement stm = connection.prepareStatement("insert into genres(genre_name)" +
                " values(?);");
        stm.setString(1,"Fantasy");
        stm.executeUpdate();
    }
}
