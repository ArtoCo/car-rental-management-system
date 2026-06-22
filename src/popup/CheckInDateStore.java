/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
// File: CheckInDateStore.java

import customers.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CheckInDateStore {
    private static final Set<LocalDate> checkInDates = new HashSet<>();

    public static void addCheckInDate(LocalDate date) {
        checkInDates.add(date);
    }

    public static void loadCheckInDatesFromDB() {
        checkInDates.clear();

        // ✅ Only load PICKUP dates for active customers (NOT RETURNED)
        String sql =
            "SELECT Pickup_Date FROM customer " +
            "WHERE Pickup_Date IS NOT NULL " +
            "AND TRIM(UPPER(status)) <> 'RETURNED'";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Date sqlDate = rs.getDate("Pickup_Date");
                if (sqlDate != null) {
                    checkInDates.add(sqlDate.toLocalDate());
                }
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Set<LocalDate> getCheckInDates() {
        return checkInDates;
    }
}



