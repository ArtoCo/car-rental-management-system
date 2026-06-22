package popup;

import customers.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CheckOutDateStore {
    private static final Set<LocalDate> checkOutDates = new HashSet<>();

    // ✅ Add a date to memory
    public static void addCheckOutDate(LocalDate date) {
        checkOutDates.add(date);
    }

    // ✅ Load check-out dates from database (EXCLUDE RETURNED)
    public static void loadCheckOutDatesFromDB() {
        checkOutDates.clear();

        String sql =
            "SELECT Return_Date FROM customer " +
            "WHERE Return_Date IS NOT NULL " +
            "AND TRIM(UPPER(status)) <> 'RETURNED'";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Date sqlDate = rs.getDate("Return_Date");
                if (sqlDate != null) {
                    checkOutDates.add(sqlDate.toLocalDate());
                }
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ✅ Retrieve all loaded check-out dates
    public static Set<LocalDate> getCheckOutDates() {
        return checkOutDates;
    }

    // (Optional) clear stored dates manually
    public static void clear() {
        checkOutDates.clear();
    }
}
