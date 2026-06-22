/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceCarAdded {

    public static void saveCarInfo(ModelCarAdded car) {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;

        try {
            // Use your actual MySQL username and password here
            conn = DriverManager.getConnection(config.AppConfig.getDatabaseUrl(), config.AppConfig.getDatabaseUser(), config.AppConfig.getDatabasePassword());

            String checkSql = "SELECT COUNT(*) FROM car_added WHERE CARS_NAME = ?";
            String insertSql = "INSERT INTO cars_added (CARS_NAME, CARS_MODEL, CARS_RATINGS, CARS_DAILYRENT, CARS_PLATENUMBER, CARS_TYPE, CAR_SEATS, CAR_FUEL_TYPE, ADDED_DATE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Check if already exists
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, car.getCARS_NAME());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (count == 0) { // Insert only if not a duplicate
                insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, car.getCARS_NAME());
                insertStmt.setString(2, car.getCARS_MODEL());
                insertStmt.setString(3, car.getCARS_RATINGS());
                insertStmt.setString(4, car.getCARS_DAILYRENT());
                insertStmt.setString(5, car.getCARS_PLATENUMBER());
                insertStmt.setString(6, car.getCARS_TYPE());
                insertStmt.setString(7, car.getCAR_SEATS());
                insertStmt.setString(8, car.getCAR_FUEL_TYPE());
                insertStmt.setString(9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                insertStmt.executeUpdate();
                insertStmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
