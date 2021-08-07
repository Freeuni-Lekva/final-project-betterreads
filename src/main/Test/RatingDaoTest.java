import Dao.CDB;
import Dao.Connector;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
        PreparedStatement statement = connection.prepareStatement("insert into books(book_name, last_name,email,username,password_hash) values(?,?,?,?,?);");
        statement.setString(1,"Elene");
        statement.setString(2,"Tsiramua");
        statement.setString(3,"etsir19@gmail.com");
        statement.setString(4,"etsir19");
        statement.setString(5,"1234");
        statement.executeUpdate();
    }
}
