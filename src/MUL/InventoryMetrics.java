package MUL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public final class InventoryMetrics {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    private InventoryMetrics() {
    }

    public static final class Snapshot {
        public final int total;
        public final int available;
        public final int rented;
        public final int booked;
        public final int repair;
        public final int added;

        private Snapshot(int total, int available, int rented, int booked, int repair, int added) {
            this.total = total;
            this.available = available;
            this.rented = rented;
            this.booked = booked;
            this.repair = repair;
            this.added = added;
        }
    }

    public static Snapshot load() throws Exception {
        int total = 0;
        int repair = 0;
        int available = 0;
        int added = 0;
        Set<String> bookedCars = new HashSet<>();
        Set<String> rentedCars = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String activeSql =
                    "SELECT vehicle_name, status FROM customer "
                    + "WHERE status IN ('BOOKED','RENTED')";

            try (PreparedStatement ps = conn.prepareStatement(activeSql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = normalize(rs.getString("vehicle_name"));
                    String status = normalize(rs.getString("status"));
                    if ("BOOKED".equals(status)) bookedCars.add(name);
                    if ("RENTED".equals(status)) rentedCars.add(name);
                }
            }

            String inventorySql = "SELECT CARS_NAME, CAR_STATUS FROM cars_info";

            try (PreparedStatement ps = conn.prepareStatement(inventorySql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    total++;
                    String name = normalize(rs.getString("CARS_NAME"));
                    boolean underRepair = CarStatusSecurity.isRepairStatus(rs.getString("CAR_STATUS"));

                    if (underRepair) {
                        repair++;
                    } else if (!bookedCars.contains(name) && !rentedCars.contains(name)) {
                        available++;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM car_added");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    added = rs.getInt(1);
                }
            }
        }

        return new Snapshot(total, available, rentedCars.size(), bookedCars.size(), repair, added);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }
}
