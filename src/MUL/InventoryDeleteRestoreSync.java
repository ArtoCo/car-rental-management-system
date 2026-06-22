package MUL;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public final class InventoryDeleteRestoreSync {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();
    private static final int REQUIRED_RESTORE_OPENS = 10;
    private static volatile boolean restoreCounterReady;
    private static volatile boolean restoreCounterChecked;

    private InventoryDeleteRestoreSync() {
    }

    public static boolean deleteCar(
            Component parent,
            String plate,
            String brand,
            boolean addedCar,
            boolean permanent
    ) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            ensureRestoreCounterColumn(conn);
            conn.setAutoCommit(false);

            try {
                CarRow row = loadSingleCarsInfoRow(conn, plate);
                if (row == null) {
                    throw new SQLException("Car was not found in inventory: " + plate);
                }

                archiveDeletedCar(conn, row, brand, permanent);

                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM cars_info WHERE `#` = ?")) {
                    ps.setInt(1, row.id);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Inventory row changed before deletion. Nothing was deleted.");
                    }
                }

                if (addedCar) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM car_added "
                            + "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?")) {
                        ps.setString(1, plate);
                        ps.setString(2, brand);
                        ps.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(parent, "Delete was cancelled safely", ex);
            return false;
        }
    }

    public static boolean restoreCar(
            Component parent,
            String plate,
            String brand,
            boolean addedCar
    ) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
            conn.setAutoCommit(false);

            try {
                DeletedRow row = loadSingleDeletedRowByPlate(conn, plate, brand);
                if (row == null) {
                    throw new SQLException("Deleted car data was not found: " + plate);
                }
                if (isPermanent(row.deletedDate) && !hasRestoreCounter) {
                    throw new SQLException(
                            "Restore counter column is missing. Normal restore still works, "
                            + "but permanently deleted cars cannot be restored safely.");
                }
                if (isPermanent(row.deletedDate) && row.restoreOpenCount < REQUIRED_RESTORE_OPENS) {
                    throw new SQLException(
                            "This permanently deleted car can be restored after " + REQUIRED_RESTORE_OPENS
                            + " brand page opens or refreshes.");
                }
                if (countByPlate(conn, "cars_info", plate) > 0) {
                    throw new SQLException("Inventory already contains plate: " + plate);
                }

                insertCarsInfo(conn, row);

                if (addedCar) {
                    if (countByPlate(conn, "car_added", plate) > 0) {
                        throw new SQLException("Added-car list already contains plate: " + plate);
                    }
                    insertCarAdded(conn, row, brand);
                }

                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM cars_deleted WHERE `#` = ?")) {
                    ps.setInt(1, row.id);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Deleted-car row changed before restore.");
                    }
                }

                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(parent, "Restore was cancelled safely", ex);
            return false;
        }
    }

    public static boolean restoreAddedCarByName(Component parent, String carName, String brand) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
            String sql = "SELECT CARS_PLATENUMBER FROM cars_deleted "
                    + "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) AND CAR_BRAND = ? "
                    + (hasRestoreCounter
                    ? "AND ((DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%') "
                    + "OR COALESCE(RESTORE_OPEN_COUNT, 0) >= ?)"
                    : "AND (DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%')");
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, carName);
                ps.setString(2, brand);
                if (hasRestoreCounter) {
                    ps.setInt(3, REQUIRED_RESTORE_OPENS);
                }

                String plate = null;
                int rows = 0;
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rows++;
                        plate = rs.getString("CARS_PLATENUMBER");
                    }
                }

                if (rows != 1 || plate == null || plate.trim().isEmpty()) {
                    throw new SQLException("Expected one restore-eligible added car for " + carName + ", found " + rows);
                }

                return restoreCar(parent, plate, brand, true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(parent, "Restore was cancelled safely", ex);
            return false;
        }
    }

    public static int showRestoreAddedCarsDialog(
            Component parent,
            String brand,
            List<String> hardcodedPlates
    ) {
        try {
            List<DeletedRow> candidates = loadRestorableAddedCars(brand, hardcodedPlates);
            if (candidates.isEmpty()) {
                return 0;
            }

            DefaultListModel<String> model = new DefaultListModel<>();
            for (DeletedRow row : candidates) {
                model.addElement(row.name + " | " + row.plate);
            }

            JList<String> list = new JList<>(model);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setVisibleRowCount(Math.min(10, model.size()));

            int result = JOptionPane.showConfirmDialog(
                    parent,
                    new JScrollPane(list),
                    "Select added car(s) to restore",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return 0;
            }

            int restored = 0;
            for (int index : list.getSelectedIndices()) {
                DeletedRow row = candidates.get(index);
                if (restoreCar(parent, row.plate, brand, true)) {
                    restored++;
                }
            }
            return restored;
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(parent, "Restore added cars was cancelled safely", ex);
            return 0;
        }
    }

    public static void recordBrandOpenOrRefresh(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return;
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (!ensureRestoreCounterColumn(conn)) {
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cars_deleted "
                    + "SET RESTORE_OPEN_COUNT = LEAST(COALESCE(RESTORE_OPEN_COUNT, 0) + 1, ?) "
                    + "WHERE CAR_BRAND = ? "
                    + "AND UPPER(TRIM(DELETED_DATE)) LIKE 'PERMANENT%'")) {
                ps.setInt(1, REQUIRED_RESTORE_OPENS);
                ps.setString(2, brand);
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean canShowInRestore(String carName, String plate, String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return false;
        }

        boolean usePlate = plate != null && !plate.trim().isEmpty();
        boolean useName = carName != null && !carName.trim().isEmpty();
        if (!usePlate && !useName) {
            return false;
        }

        String identity = usePlate
                ? "TRIM(CARS_PLATENUMBER) = TRIM(?)"
                : "UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?))";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
            String sql = "SELECT COUNT(*) FROM cars_deleted WHERE CAR_BRAND = ? AND " + identity
                    + (hasRestoreCounter
                    ? " AND ((DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%')"
                    + " OR COALESCE(RESTORE_OPEN_COUNT, 0) >= ?)"
                    : " AND (DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%')");
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, brand);
                ps.setString(2, usePlate ? plate : carName);
                if (hasRestoreCounter) {
                    ps.setInt(3, REQUIRED_RESTORE_OPENS);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) == 1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static List<DeletedRow> loadRestorableAddedCars(
            String brand,
            List<String> hardcodedPlates
    ) throws SQLException {
        Set<String> builtIn = new HashSet<>();
        if (hardcodedPlates != null) {
            for (String plate : hardcodedPlates) {
                if (plate != null && !plate.trim().isEmpty()) {
                    builtIn.add(plate.trim().toUpperCase());
                }
            }
        }

        List<DeletedRow> rows = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
            String sql =
                    "SELECT `#`, CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL_TYPE, CARS_SEATS, "
                    + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, DELETED_DATE"
                    + (hasRestoreCounter ? ", RESTORE_OPEN_COUNT " : " ")
                    + "FROM cars_deleted WHERE CAR_BRAND = ? "
                    + (hasRestoreCounter
                    ? "AND ((DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%') "
                    + "OR COALESCE(RESTORE_OPEN_COUNT, 0) >= ?) "
                    : "AND (DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%') ")
                    + "ORDER BY CARS_NAME, CARS_PLATENUMBER";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, brand);
                if (hasRestoreCounter) {
                    ps.setInt(2, REQUIRED_RESTORE_OPENS);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        DeletedRow row = readDeletedRow(rs);
                        String plate = row.plate == null ? "" : row.plate.trim().toUpperCase();
                        if (!plate.isEmpty() && !builtIn.contains(plate)) {
                            rows.add(row);
                        }
                    }
                }
            }
        }
        return rows;
    }

    private static CarRow loadSingleCarsInfoRow(Connection conn, String plate) throws SQLException {
        String sql =
                "SELECT `#`, CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, "
                + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION "
                + "FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            try (ResultSet rs = ps.executeQuery()) {
                CarRow row = null;
                int count = 0;
                while (rs.next()) {
                    count++;
                    row = readCarRow(rs);
                }
                if (count > 1) {
                    throw new SQLException("Duplicate inventory plate found: " + plate + ". No row was deleted.");
                }
                return row;
            }
        }
    }

    private static DeletedRow loadSingleDeletedRowByPlate(
            Connection conn, String plate, String brand
    ) throws SQLException {
        boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
        String sql =
                "SELECT `#`, CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL_TYPE, CARS_SEATS, "
                + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, DELETED_DATE"
                + (hasRestoreCounter ? ", RESTORE_OPEN_COUNT " : " ")
                + "FROM cars_deleted WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            ps.setString(2, brand);
            try (ResultSet rs = ps.executeQuery()) {
                DeletedRow row = null;
                int count = 0;
                while (rs.next()) {
                    count++;
                    row = readDeletedRow(rs);
                }
                if (count > 1) {
                    throw new SQLException("Duplicate deleted-car plate found: " + plate);
                }
                return row;
            }
        }
    }

    private static void archiveDeletedCar(
            Connection conn, CarRow row, String brand, boolean permanent
    ) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM cars_deleted WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?")) {
            ps.setString(1, row.plate);
            ps.setString(2, brand);
            ps.executeUpdate();
        }

        boolean hasRestoreCounter = ensureRestoreCounterColumn(conn);
        String sql =
                "INSERT INTO cars_deleted "
                + "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL_TYPE, CARS_SEATS, "
                + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, DELETED_DATE, CARS_IMAGE, "
                + "CAR_TRANSMISSION, CAR_BRAND"
                + (hasRestoreCounter ? ", RESTORE_OPEN_COUNT" : "")
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
                + (hasRestoreCounter ? ", ?" : "")
                + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, row.name);
            ps.setString(2, row.type);
            ps.setString(3, row.plate);
            ps.setString(4, row.fuel);
            ps.setString(5, row.seats);
            ps.setString(6, row.dailyRent);
            ps.setString(7, row.model);
            ps.setString(8, row.ratings);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ps.setString(9, permanent ? "PERMANENT | " + date : date);
            setBytes(ps, 10, row.image);
            ps.setString(11, row.transmission);
            ps.setString(12, brand);
            if (hasRestoreCounter) {
                ps.setInt(13, 0);
            }
            ps.executeUpdate();
        }
    }

    private static void insertCarsInfo(Connection conn, DeletedRow row) throws SQLException {
        String sql =
                "INSERT INTO cars_info "
                + "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, "
                + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bindCommon(ps, row);
            ps.executeUpdate();
        }
    }

    private static void insertCarAdded(Connection conn, DeletedRow row, String brand) throws SQLException {
        String sql =
                "INSERT INTO car_added "
                + "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, "
                + "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, ADDED_DATE, CARS_IMAGE, "
                + "CAR_TRANSMISSION, CAR_BRAND) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, row.name);
            ps.setString(2, row.type);
            ps.setString(3, row.plate);
            ps.setString(4, row.fuel);
            ps.setString(5, row.seats);
            ps.setString(6, row.dailyRent);
            ps.setString(7, row.model);
            ps.setString(8, row.ratings);
            ps.setString(9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            setBytes(ps, 10, row.image);
            ps.setString(11, row.transmission);
            ps.setString(12, brand);
            ps.executeUpdate();
        }
    }

    private static void bindCommon(PreparedStatement ps, DeletedRow row) throws SQLException {
        ps.setString(1, row.name);
        ps.setString(2, row.type);
        ps.setString(3, row.plate);
        ps.setString(4, row.fuel);
        ps.setString(5, row.seats);
        ps.setString(6, row.dailyRent);
        ps.setString(7, row.model);
        ps.setString(8, row.ratings);
        setBytes(ps, 9, row.image);
        ps.setString(10, row.transmission);
    }

    private static int countByPlate(Connection conn, String table, String plate) throws SQLException {
        if (!"cars_info".equals(table) && !"car_added".equals(table)) {
            throw new IllegalArgumentException("Unsupported table: " + table);
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM " + table + " WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)")) {
            ps.setString(1, plate);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private static CarRow readCarRow(ResultSet rs) throws SQLException {
        CarRow row = new CarRow();
        row.id = rs.getInt("#");
        row.name = rs.getString("CARS_NAME");
        row.type = rs.getString("CARS_TYPE");
        row.plate = rs.getString("CARS_PLATENUMBER");
        row.fuel = rs.getString("CARS_FUEL");
        row.seats = rs.getString("CARS_SEATS");
        row.dailyRent = rs.getString("CARS_DAILYRENT");
        row.model = rs.getString("CARS_MODEL");
        row.ratings = rs.getString("CARS_RATINGS");
        row.image = rs.getBytes("CARS_IMAGE");
        row.transmission = rs.getString("CAR_TRANSMISSION");
        return row;
    }

    private static DeletedRow readDeletedRow(ResultSet rs) throws SQLException {
        DeletedRow row = new DeletedRow();
        row.id = rs.getInt("#");
        row.name = rs.getString("CARS_NAME");
        row.type = rs.getString("CARS_TYPE");
        row.plate = rs.getString("CARS_PLATENUMBER");
        row.fuel = rs.getString("CARS_FUEL_TYPE");
        row.seats = rs.getString("CARS_SEATS");
        row.dailyRent = rs.getString("CARS_DAILYRENT");
        row.model = rs.getString("CARS_MODEL");
        row.ratings = rs.getString("CARS_RATINGS");
        row.image = rs.getBytes("CARS_IMAGE");
        row.transmission = rs.getString("CAR_TRANSMISSION");
        row.deletedDate = rs.getString("DELETED_DATE");
        try {
            row.restoreOpenCount = rs.getInt("RESTORE_OPEN_COUNT");
        } catch (SQLException ignored) {
            row.restoreOpenCount = 0;
        }
        return row;
    }

    private static synchronized boolean ensureRestoreCounterColumn(Connection conn) {
        if (restoreCounterChecked) {
            return restoreCounterReady;
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT RESTORE_OPEN_COUNT FROM cars_deleted WHERE 1 = 0")) {
            ps.executeQuery();
            restoreCounterReady = true;
        } catch (SQLException missingColumn) {
            restoreCounterReady = false;
        }
        restoreCounterChecked = true;
        return restoreCounterReady;
    }

    private static boolean isPermanent(String deletedDate) {
        return deletedDate != null && deletedDate.trim().toUpperCase().startsWith("PERMANENT");
    }

    private static void setBytes(PreparedStatement ps, int index, byte[] bytes) throws SQLException {
        if (bytes == null) {
            ps.setNull(index, Types.BLOB);
        } else {
            ps.setBytes(index, bytes);
        }
    }

    private static void showError(Component parent, String title, Exception ex) {
        JOptionPane.showMessageDialog(
                parent,
                title + ":\n" + ex.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    private static class CarRow {
        int id;
        String name;
        String type;
        String plate;
        String fuel;
        String seats;
        String dailyRent;
        String model;
        String ratings;
        byte[] image;
        String transmission;
    }

    private static class DeletedRow extends CarRow {
        String deletedDate;
        int restoreOpenCount;
    }
}
