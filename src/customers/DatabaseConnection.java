/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customers;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private BasicDataSource dataSource;

    private String username = config.AppConfig.getDatabaseUser();
    private String password = config.AppConfig.getDatabasePassword();

    // Singleton pattern
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // ✅ Constructor initializes the connection pool
    private DatabaseConnection() {
        try {
            connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Setup the datasource connection
    public void connectToDatabase() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(
        config.AppConfig.getDatabaseUrlWithOptions(
                "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC")
        );

        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    // ✅ Always safe to call
    public Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // ✅ Auto-closing multiple resources
    public void close(AutoCloseable... close) throws SQLException {
        try {
            for (AutoCloseable c : close) {
                if (c != null) {
                    c.close();
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error on closing", e);
        }
    }

    // ✅ Static helper method for safe connection
    public static Connection getConnection() {
        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            return db.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
