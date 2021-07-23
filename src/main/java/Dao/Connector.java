package Dao;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
    private static Connection connection;
    private Connector(String dbName) throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/library");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        connection = dataSource.getConnection();
    }

    public static Connection getConnection(String dbName) throws SQLException {
        if(connection == null){
            new Connector(dbName);
        }
        return connection;
    }
}
