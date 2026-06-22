/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MUL;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BrandDeleteRestoreSync {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static class Row {
        public JPanel panel;
        public String panelName;
        public Object carName;
        public Object carType;
        public Object plateNumber;
        public Object fuel;
        public Object seats;
        public Object dailyRent;
        public Object model;
        public Object ratings;
        public Object transmission;
        public JLabel imageLabel;
        public boolean addedSlot;

        public Row(
                JPanel panel,
                String panelName,
                Object carName,
                Object carType,
                Object plateNumber,
                Object fuel,
                Object seats,
                Object dailyRent,
                Object model,
                Object ratings,
                Object transmission,
                JLabel imageLabel,
                boolean addedSlot
        ) {
            this.panel = panel;
            this.panelName = panelName;
            this.carName = carName;
            this.carType = carType;
            this.plateNumber = plateNumber;
            this.fuel = fuel;
            this.seats = seats;
            this.dailyRent = dailyRent;
            this.model = model;
            this.ratings = ratings;
            this.transmission = transmission;
            this.imageLabel = imageLabel;
            this.addedSlot = addedSlot;
        }
    }

    private static class DeletedRow {
        String carName;
        String carType;
        String plateNumber;
        String fuel;
        String seats;
        String dailyRent;
        String model;
        String ratings;
        String deletedDate;
        byte[] imageBytes;
        String transmission;
        String brand;
    }

    public static Row row(
            JPanel panel,
            String panelName,
            Object carName,
            Object carType,
            Object plateNumber,
            Object fuel,
            Object seats,
            Object dailyRent,
            Object model,
            Object ratings,
            Object transmission,
            JLabel imageLabel,
            boolean addedSlot
    ) {
        return new Row(
                panel,
                panelName,
                carName,
                carType,
                plateNumber,
                fuel,
                seats,
                dailyRent,
                model,
                ratings,
                transmission,
                imageLabel,
                addedSlot
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    public static boolean softDelete(JFrame parent, Row row, String brandName) {
        return delete(parent, row, brandName, false);
    }

    public static boolean permanentDelete(JFrame parent, Row row, String brandName) {
        return delete(parent, row, brandName, true);
    }

    private static boolean delete(JFrame parent, Row row, String brandName, boolean permanent) {
        try {
            if (row == null) {
                JOptionPane.showMessageDialog(parent, "No row mapping found.");
                return false;
            }

            String carName = readText(row.carName);
            String plate = readText(row.plateNumber);

            if (carName.isEmpty() || plate.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "This panel has no car data to delete.");
                return false;
            }

            return InventoryDeleteRestoreSync.deleteCar(
                    parent, plate, brandName, row.addedSlot, permanent);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    parent,
                    "Delete sync failed:\n" + e.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private static void saveToCarsDeleted(Row row, String brandName, boolean permanent) throws Exception {
        String carName = readText(row.carName);
        String carType = readText(row.carType);
        String plate = readText(row.plateNumber);
        String fuel = readText(row.fuel);
        String seats = readText(row.seats);
        String dailyRent = readText(row.dailyRent);
        String model = readText(row.model);
        String ratings = readText(row.ratings);
        String transmission = readText(row.transmission);
        byte[] imageBytes = getImageBytesFromLabel(row.imageLabel);

        String deletedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (permanent) {
            deletedDate = "PERMANENT | " + deletedDate;
        }

        String deleteOldSql =
                "DELETE FROM cars_deleted " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        String insertSql =
                "INSERT INTO cars_deleted " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, DELETED_DATE, CARS_IMAGE, " +
                "CAR_TRANSMISSION, CAR_BRAND) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteOldSql)) {
                ps.setString(1, plate);
                ps.setString(2, brandName);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, carName);
                ps.setString(2, carType);
                ps.setString(3, plate);
                ps.setString(4, fuel);
                ps.setString(5, seats);
                ps.setString(6, dailyRent);
                ps.setString(7, model);
                ps.setString(8, ratings);
                ps.setString(9, deletedDate);

                if (imageBytes != null) {
                    ps.setBytes(10, imageBytes);
                } else {
                    ps.setNull(10, Types.BLOB);
                }

                ps.setString(11, transmission);
                ps.setString(12, brandName);

                ps.executeUpdate();
            }
        }
    }

    public static void deleteFromCarsInfoByPlate(String plateNumber) throws SQLException {
        String sql = "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plateNumber.trim());

            int rows = ps.executeUpdate();

            System.out.println("DELETED FROM cars_info: " + plateNumber + " | rows=" + rows);
        }
    }

    private static void deleteFromCarAddedByPlateOrName(
            String plateNumber,
            String carName,
            String brandName
    ) throws SQLException {

        String sql =
                "DELETE FROM car_added " +
                "WHERE CAR_BRAND = ? " +
                "AND (TRIM(CARS_PLATENUMBER) = TRIM(?) OR UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?))) " +
                "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);
            ps.setString(2, plateNumber.trim());
            ps.setString(3, carName.trim());

            int rows = ps.executeUpdate();

            System.out.println("DELETED FROM car_added: " + carName + " | rows=" + rows);
        }
    }

    // =========================================================
    // RESTORE REGULAR PANEL TO cars_info
    // =========================================================

    public static boolean restoreRegularPanelToCarsInfo(JFrame parent, Row row, String brandName) {
        try {
            if (row == null) {
                JOptionPane.showMessageDialog(parent, "No row mapping found for restore.");
                return false;
            }

            savePanelRowToCarsInfo(row);
            deleteFromCarsDeletedByPlateOrName(
                    readText(row.plateNumber),
                    readText(row.carName),
                    brandName
            );

            System.out.println("RESTORED REGULAR TO cars_info: " + readText(row.carName));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    parent,
                    "Restore to cars_info failed:\n" + e.getMessage(),
                    "Restore Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private static void savePanelRowToCarsInfo(Row row) throws Exception {
        String carName = readText(row.carName);
        String carType = readText(row.carType);
        String plate = readText(row.plateNumber);
        String fuel = readText(row.fuel);
        String seats = readText(row.seats);
        String dailyRent = readText(row.dailyRent);
        String model = readText(row.model);
        String ratings = readText(row.ratings);
        String transmission = readText(row.transmission);
        byte[] imageBytes = getImageBytesFromLabel(row.imageLabel);

        String deleteSql =
                "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        String insertSql =
                "INSERT INTO cars_info " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, plate);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, carName);
                ps.setString(2, carType);
                ps.setString(3, plate);
                ps.setString(4, fuel);
                ps.setString(5, seats);
                ps.setString(6, dailyRent);
                ps.setString(7, model);
                ps.setString(8, ratings);

                if (imageBytes != null) {
                    ps.setBytes(9, imageBytes);
                } else {
                    ps.setNull(9, Types.BLOB);
                }

                ps.setString(10, transmission);

                ps.executeUpdate();
            }
        }
    }

    // =========================================================
    // RESTORE ADDED CAR: cars_deleted -> car_added + cars_info
    // =========================================================

    public static List<String> loadRestorableAddedCarNames(
            String brandName,
            Set<String> hardcodedCarNames
    ) {
        List<String> names = new ArrayList<>();

        String sql =
                "SELECT CARS_NAME FROM cars_deleted " +
                "WHERE CAR_BRAND = ? " +
                "AND (DELETED_DATE IS NULL OR DELETED_DATE NOT LIKE 'PERMANENT%')";

        Set<String> hardcoded = new HashSet<>();

        if (hardcodedCarNames != null) {
            for (String name : hardcodedCarNames) {
                if (name != null) {
                    hardcoded.add(name.trim().toUpperCase());
                }
            }
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("CARS_NAME");

                    if (name == null || name.trim().isEmpty()) {
                        continue;
                    }

                    if (!hardcoded.contains(name.trim().toUpperCase())) {
                        names.add(name.trim());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names;
    }

    public static boolean restoreAddedCarByName(JFrame parent, String carName, String brandName) {
        try {
            DeletedRow row = loadDeletedRowByName(carName, brandName);

            if (row == null) {
                JOptionPane.showMessageDialog(parent, "Deleted car data not found: " + carName);
                return false;
            }

            saveDeletedRowToCarsInfo(row);
            saveDeletedRowToCarAdded(row, brandName);
            deleteFromCarsDeletedByPlateOrName(row.plateNumber, row.carName, brandName);

            System.out.println("RESTORED ADDED CAR: " + row.carName);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    parent,
                    "Restore added car failed:\n" + e.getMessage(),
                    "Restore Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private static DeletedRow loadDeletedRowByName(String carName, String brandName) throws SQLException {
        String sql =
                "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, DELETED_DATE, CARS_IMAGE, " +
                "CAR_TRANSMISSION, CAR_BRAND " +
                "FROM cars_deleted " +
                "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) " +
                "AND CAR_BRAND = ? " +
                "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, carName);
            ps.setString(2, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DeletedRow row = new DeletedRow();

                    row.carName = rs.getString("CARS_NAME");
                    row.carType = rs.getString("CARS_TYPE");
                    row.plateNumber = rs.getString("CARS_PLATENUMBER");
                    row.fuel = rs.getString("CARS_FUEL");
                    row.seats = rs.getString("CARS_SEATS");
                    row.dailyRent = rs.getString("CARS_DAILYRENT");
                    row.model = rs.getString("CARS_MODEL");
                    row.ratings = rs.getString("CARS_RATINGS");
                    row.deletedDate = rs.getString("DELETED_DATE");
                    row.imageBytes = rs.getBytes("CARS_IMAGE");
                    row.transmission = rs.getString("CAR_TRANSMISSION");
                    row.brand = rs.getString("CAR_BRAND");

                    return row;
                }
            }
        }

        return null;
    }

    private static void saveDeletedRowToCarsInfo(DeletedRow row) throws SQLException {
        String deleteSql =
                "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        String insertSql =
                "INSERT INTO cars_info " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, row.plateNumber);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, row.carName);
                ps.setString(2, row.carType);
                ps.setString(3, row.plateNumber);
                ps.setString(4, row.fuel);
                ps.setString(5, row.seats);
                ps.setString(6, row.dailyRent);
                ps.setString(7, row.model);
                ps.setString(8, row.ratings);

                if (row.imageBytes != null) {
                    ps.setBytes(9, row.imageBytes);
                } else {
                    ps.setNull(9, Types.BLOB);
                }

                ps.setString(10, row.transmission);

                ps.executeUpdate();
            }
        }
    }

    private static void saveDeletedRowToCarAdded(DeletedRow row, String brandName) throws SQLException {
        String deleteSql =
                "DELETE FROM car_added " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        String insertSql =
                "INSERT INTO car_added " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, CAR_BRAND) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, row.plateNumber);
                ps.setString(2, brandName);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, row.carName);
                ps.setString(2, row.carType);
                ps.setString(3, row.plateNumber);
                ps.setString(4, row.fuel);
                ps.setString(5, row.seats);
                ps.setString(6, row.dailyRent);
                ps.setString(7, row.model);
                ps.setString(8, row.ratings);

                if (row.imageBytes != null) {
                    ps.setBytes(9, row.imageBytes);
                } else {
                    ps.setNull(9, Types.BLOB);
                }

                ps.setString(10, row.transmission);
                ps.setString(11, brandName);

                ps.executeUpdate();
            }
        }
    }

    public static void deleteFromCarsDeletedByPlateOrName(
            String plateNumber,
            String carName,
            String brandName
    ) throws SQLException {

        String sql =
                "DELETE FROM cars_deleted " +
                "WHERE CAR_BRAND = ? " +
                "AND (TRIM(CARS_PLATENUMBER) = TRIM(?) OR UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?))) " +
                "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);
            ps.setString(2, plateNumber == null ? "" : plateNumber.trim());
            ps.setString(3, carName == null ? "" : carName.trim());

            ps.executeUpdate();
        }
    }

    public static Set<String> loadDeletedCarNames(String brandName) {
        Set<String> deleted = new HashSet<>();

        String sql = "SELECT CARS_NAME FROM cars_deleted WHERE CAR_BRAND = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("CARS_NAME");

                    if (name != null && !name.trim().isEmpty()) {
                        deleted.add(name.trim());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    // =========================================================
    // COMPONENT READERS
    // =========================================================

    private static String readText(Object component) {
        if (component == null) {
            return "";
        }

        if (component instanceof JTextField) {
            return ((JTextField) component).getText().trim();
        }

        if (component instanceof JLabel) {
            return ((JLabel) component).getText().trim();
        }

        return component.toString().trim();
    }

    private static byte[] getImageBytesFromLabel(JLabel label) throws Exception {
        if (label == null) {
            return null;
        }

        Icon icon = label.getIcon();

        if (!(icon instanceof ImageIcon)) {
            return null;
        }

        Image image = ((ImageIcon) icon).getImage();

        if (image == null) {
            return null;
        }

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (width <= 0 || height <= 0) {
            return null;
        }

        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = buffered.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffered, "png", baos);

        return baos.toByteArray();
    }
}
