/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employees;

/**
 *
 * @author ArtoCO
 */
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private BasicDataSource dataSource;
    private String username = config.AppConfig.getDatabaseUser();
    private String password = config.AppConfig.getDatabasePassword();


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {

    }

    public synchronized void connectToDatabase() throws SQLException {
    if (dataSource != null) {
        return;
    }
    dataSource = new BasicDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Required
    dataSource.setUrl(config.AppConfig.getDatabaseUrlWithOptions(
            "useSSL=false&serverTimezone=UTC&zeroDateTimeBehavior=CONVERT_TO_NULL"));
    dataSource.setUsername(username);  // root
    dataSource.setPassword(password);  // 1234
}


    public Connection createConnection() throws SQLException {
        if (dataSource == null) {
            connectToDatabase();
        }
        return dataSource.getConnection();
    }

    public void close(AutoCloseable... close) throws SQLException {
        try {
            for (AutoCloseable c : close) {
                if (c != null) {
                    c.close();
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error on closing");
        }
    }
}
