package Dao;

import Constants.DataBaseConstants;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
    private static Connection connection;
    private Connector(String dbName) throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(DataBaseConstants.DATA_BASE_URL + dbName);
        dataSource.setUsername(DataBaseConstants.DATA_BASE_USERNAME);
        dataSource.setPassword(DataBaseConstants.DATA_BASE_PASSWORD);
        connection = dataSource.getConnection();
    }

    public static Connection getConnection(String dbName) throws SQLException {
        if(connection == null){
            new Connector(dbName);
        }
        return connection;
    }
}
