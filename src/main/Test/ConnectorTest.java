import Dao.Connector;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorTest {
    @Test
    public void testConnector() throws SQLException {
        Connection c = Connector.getConnection("library");
        Statement stm = c.createStatement();
        stm.execute("use library;");

        Statement select = c.createStatement();
        ResultSet rs = select.executeQuery("select * from authors" +
                " where author_name = 'Fyodor Dostoevsky';");
        String res = "";
        while(rs.next()){
            res += rs.getString(2);
        }
        Assert.assertEquals("Fyodor Dostoevsky",res);
    }

    @Test
    public void testTwoInstances() throws SQLException {
        Connection c1 = Connector.getConnection("library");
        Connection c2 = Connector.getConnection("library");
        Assert.assertEquals(c1, c2);
    }
}
