import Dao.Connector;
import Dao.UserDao;
import Model.User;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.*;

public class UserDaoTest extends TestCase {
    private String dbName;
    private Connection connection;

    protected void setUp() throws SQLException {

        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;
    }

    @Test
    public void testCreateUser() {
        UserDao dao = null;
        try {
            dao = new UserDao(Connector.getConnection(dbName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User user = new User();
        user.setFirst_name("test_name");
        user.setLast_name("test_lastname");
        user.setEmail("test_mail");
        user.setUsername("test_username");
        user.setPassword_hash("12");


        assertTrue(dao.create(user));
        try {
            Connection connection = Connector.getConnection(dbName);
            Statement stm = connection.createStatement();
            stm.execute("use " + dbName);
            PreparedStatement statement = connection.prepareStatement("select * from users where username = ?;");
            statement.setString(1,"test_username");
            ResultSet resultSet = statement.executeQuery();

            User newUser = createUser(resultSet);
            assertEquals(user,newUser);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void testGetUserByMail() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        statement.setString(1,"Elene");
        statement.setString(2,"Tsiramua");
        statement.setString(3,"etsir19@gmail.com");
        statement.setString(4,"etsir19");
        statement.setString(5,"1234");
        statement.executeUpdate();

        String mail = "etsir19@gmail.com";
        UserDao dao = null;
        try {
            dao = new UserDao(Connector.getConnection(dbName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User user = dao.getUserByMail(mail);
        assertEquals(mail,user.getEmail());
        assertEquals( "Elene",user.getFirst_name());
        assertEquals("Tsiramua",user.getLast_name());
        assertEquals("etsir19",user.getUsername());
    }

    public void testGetUserByUsername() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("use " + dbName);
        PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        statement.setString(1,"Jiji");
        statement.setString(2,"Jijelava");
        statement.setString(3,"tjije19@gmail.com");
        statement.setString(4,"tjije19");
        statement.setString(5,"4321");
        statement.executeUpdate();

        String mail = "tjije19@gmail.com";
        UserDao dao = null;
        try {
            dao = new UserDao(Connector.getConnection(dbName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User user = dao.getUserByMail(mail);
        assertEquals(mail,user.getEmail());
        assertEquals( "Jiji",user.getFirst_name());
        assertEquals("Jijelava",user.getLast_name());
        assertEquals("tjije19",user.getUsername());
    }

    public void testGetUserById() throws SQLException {
        User newUser = new User();
        newUser.setFirst_name("nini");
        newUser.setLast_name("kh");
        newUser.setEmail("ninikh@");
        newUser.setUsername("ninikh");
        newUser.setPassword_hash("12");

        UserDao dao = null;
        try {
            dao = new UserDao(Connector.getConnection(dbName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dao.create(newUser);
        int user_id = dao.getUserByUsername(newUser.getUsername()).getUser_id();

        User userById = dao.getUserById(user_id);
        assertEquals(newUser,userById);
    }

    public void testContainsUserByMail(){
        User newUser = new User();
        newUser.setFirst_name("1");
        newUser.setLast_name("1");
        newUser.setEmail("1@");
        newUser.setUsername("1");
        newUser.setPassword_hash("1");
        UserDao dao = null;
        try {
            dao = new UserDao(Connector.getConnection(dbName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dao.create(newUser);
        assertTrue(dao.containsUserByMail(newUser.getEmail()));
        assertFalse(dao.containsUserByUserName("2@"));
    }

    private User createUser(ResultSet resultSet) {
        try {
            User user = new User();
            resultSet.next();
            user.setUser_id(resultSet.getInt(1));
            user.setFirst_name(resultSet.getString(2));
            user.setLast_name(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setUsername(resultSet.getString(5));
            user.setPassword_hash(resultSet.getString(6));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
