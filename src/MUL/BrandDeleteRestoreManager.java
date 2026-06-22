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
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class BrandDeleteRestoreManager {

    private static final String DB_URL = config.AppConfig.getDatabaseUrl();
    private static final String DB_USER = config.AppConfig.getDatabaseUser();
    private static final String DB_PASS = config.AppConfig.getDatabasePassword();

    public static class Entry {
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

        public Entry(
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

    private static class DeletedCarData {
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

    public static Entry entry(
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
        return new Entry(
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

    public static boolean deletePanel(
            JFrame parent,
            JPanel panel,
            List<Entry> entries,
            String brandName,
            boolean permanent
    ) {
        try {
            Entry entry = findEntryByPanel(panel, entries);

            if (entry == null) {
                JOptionPane.showMessageDialog(parent, "No panel mapping found.");
                return false;
            }

            String carName = readText(entry.carName);
            String plate = readText(entry.plateNumber);

            if (carName.isEmpty() || plate.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "This panel has no car data to delete.");
                return false;
            }

            return InventoryDeleteRestoreSync.deleteCar(
                    parent, plate, brandName, entry.addedSlot, permanent);

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

    public static void showRestoreDialog(
            JFrame parent,
            JPanel gridPanel,
            List<Entry> entries,
            String brandName,
            Consumer<JPanel> restoreRegularPanelAction,
            Runnable refreshAfterRestore
    ) {
        try {
            List<DeletedCarData> deletedCars = loadRestorableDeletedCars(brandName);

            if (deletedCars.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No deleted cars to restore.");
                return;
            }

            DefaultListModel<String> model = new DefaultListModel<>();

            for (DeletedCarData car : deletedCars) {
                model.addElement(car.carName + " | " + car.plateNumber);
            }

            JList<String> list = new JList<>(model);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setVisibleRowCount(Math.min(10, model.size()));

            int result = JOptionPane.showConfirmDialog(
                    parent,
                    new JScrollPane(list),
                    "Select car(s) to restore",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            int[] selectedIndexes = list.getSelectedIndices();

            if (selectedIndexes.length == 0) {
                return;
            }

            int restoredCount = 0;

            for (int index : selectedIndexes) {
                DeletedCarData car = deletedCars.get(index);

                restoreDeletedCar(
                        car,
                        entries,
                        brandName,
                        restoreRegularPanelAction
                );

                restoredCount++;
            }

            if (refreshAfterRestore != null) {
                refreshAfterRestore.run();
            }

            JOptionPane.showMessageDialog(
                    parent,
                    "Restored " + restoredCount + " car(s).",
                    "Restore Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    parent,
                    "Restore failed:\n" + e.getMessage(),
                    "Restore Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void hideDeletedPanelsOnOpen(
            JPanel gridPanel,
            List<Entry> entries,
            String brandName
    ) {
        try {
            Set<String> deletedNames = loadDeletedNames(brandName);
            Set<String> deletedPlates = loadDeletedPlates(brandName);

            for (Entry entry : entries) {
                String carName = readText(entry.carName).toUpperCase();
                String plate = readText(entry.plateNumber).toUpperCase();

                boolean deleted =
                        (!carName.isEmpty() && deletedNames.contains(carName))
                        || (!plate.isEmpty() && deletedPlates.contains(plate));

                if (deleted) {
                    gridPanel.remove(entry.panel);
                    entry.panel.setVisible(false);
                    System.out.println("HIDDEN DELETED PANEL: " + entry.panelName);
                }
            }

            gridPanel.revalidate();
            gridPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAddedPanel(JPanel panel, List<Entry> entries) {
        Entry entry = findEntryByPanel(panel, entries);
        return entry != null && entry.addedSlot;
    }

    private static void saveToCarsDeleted(Entry entry, String brandName, boolean permanent) throws Exception {
        String carName = readText(entry.carName);
        String carType = readText(entry.carType);
        String plate = readText(entry.plateNumber);
        String fuel = readText(entry.fuel);
        String seats = readText(entry.seats);
        String dailyRent = readText(entry.dailyRent);
        String model = readText(entry.model);
        String ratings = readText(entry.ratings);
        String transmission = readText(entry.transmission);
        byte[] imageBytes = getImageBytesFromLabel(entry.imageLabel);

        String deletedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (permanent) {
            deletedDate = "PERMANENT | " + deletedDate;
        }

        String deleteOldSql =
                "DELETE FROM cars_deleted WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        String insertSql =
                "INSERT INTO cars_deleted " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, DELETED_DATE, CARS_IMAGE, " +
                "CAR_TRANSMISSION, CAR_BRAND) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

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

        System.out.println("SAVED TO cars_deleted: " + carName + " | " + plate);
    }

    private static void deleteFromCarsInfoByPlate(String plateNumber) throws SQLException {
        String sql = "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plateNumber.trim());

            int deleted = ps.executeUpdate();

            System.out.println("DELETED FROM cars_info: " + plateNumber + " | rows=" + deleted);
        }
    }

    private static void deleteFromCarAddedByPlate(String plateNumber, String brandName) throws SQLException {
        String sql =
                "DELETE FROM car_added " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plateNumber.trim());
            ps.setString(2, brandName);

            int deleted = ps.executeUpdate();

            System.out.println("DELETED FROM car_added: " + plateNumber + " | rows=" + deleted);
        }
    }

    private static List<DeletedCarData> loadRestorableDeletedCars(String brandName) throws SQLException {
        List<DeletedCarData> list = new ArrayList<>();

        String sql =
                "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, DELETED_DATE, CARS_IMAGE, " +
                "CAR_TRANSMISSION, CAR_BRAND " +
                "FROM cars_deleted " +
                "WHERE CAR_BRAND = ? " +
                "AND (DELETED_DATE IS NULL OR DELETED_DATE NOT LIKE 'PERMANENT%')";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DeletedCarData car = new DeletedCarData();

                    car.carName = rs.getString("CARS_NAME");
                    car.carType = rs.getString("CARS_TYPE");
                    car.plateNumber = rs.getString("CARS_PLATENUMBER");
                    car.fuel = rs.getString("CARS_FUEL");
                    car.seats = rs.getString("CARS_SEATS");
                    car.dailyRent = rs.getString("CARS_DAILYRENT");
                    car.model = rs.getString("CARS_MODEL");
                    car.ratings = rs.getString("CARS_RATINGS");
                    car.deletedDate = rs.getString("DELETED_DATE");
                    car.imageBytes = rs.getBytes("CARS_IMAGE");
                    car.transmission = rs.getString("CAR_TRANSMISSION");
                    car.brand = rs.getString("CAR_BRAND");

                    list.add(car);
                }
            }
        }

        return list;
    }

    private static void restoreDeletedCar(
            DeletedCarData car,
            List<Entry> entries,
            String brandName,
            Consumer<JPanel> restoreRegularPanelAction
    ) throws SQLException {
        saveDeletedCarToCarsInfo(car);

        Entry regularEntry = findRegularEntryByCar(car, entries);

        if (regularEntry != null) {
            if (restoreRegularPanelAction != null) {
                restoreRegularPanelAction.accept(regularEntry.panel);
            }
        } else {
            saveDeletedCarToCarAdded(car, brandName);
        }

        deleteFromCarsDeletedByPlate(car.plateNumber, brandName);

        System.out.println("RESTORED: " + car.carName + " | " + car.plateNumber);
    }

    private static void saveDeletedCarToCarsInfo(DeletedCarData car) throws SQLException {
        String deleteSql =
                "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        String insertSql =
                "INSERT INTO cars_info " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, car.plateNumber);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, car.carName);
                ps.setString(2, car.carType);
                ps.setString(3, car.plateNumber);
                ps.setString(4, car.fuel);
                ps.setString(5, car.seats);
                ps.setString(6, car.dailyRent);
                ps.setString(7, car.model);
                ps.setString(8, car.ratings);

                if (car.imageBytes != null) {
                    ps.setBytes(9, car.imageBytes);
                } else {
                    ps.setNull(9, Types.BLOB);
                }

                ps.setString(10, car.transmission);

                ps.executeUpdate();
            }
        }
    }

    private static void saveDeletedCarToCarAdded(DeletedCarData car, String brandName) throws SQLException {
        String deleteSql =
                "DELETE FROM car_added WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        String insertSql =
                "INSERT INTO car_added " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, CAR_BRAND) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, car.plateNumber);
                ps.setString(2, brandName);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, car.carName);
                ps.setString(2, car.carType);
                ps.setString(3, car.plateNumber);
                ps.setString(4, car.fuel);
                ps.setString(5, car.seats);
                ps.setString(6, car.dailyRent);
                ps.setString(7, car.model);
                ps.setString(8, car.ratings);

                if (car.imageBytes != null) {
                    ps.setBytes(9, car.imageBytes);
                } else {
                    ps.setNull(9, Types.BLOB);
                }

                ps.setString(10, car.transmission);
                ps.setString(11, brandName);

                ps.executeUpdate();
            }
        }
    }

    private static void deleteFromCarsDeletedByPlate(String plateNumber, String brandName) throws SQLException {
        String sql =
                "DELETE FROM cars_deleted " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plateNumber);
            ps.setString(2, brandName);

            ps.executeUpdate();
        }
    }

    private static Set<String> loadDeletedNames(String brandName) throws SQLException {
        Set<String> set = new HashSet<>();

        String sql = "SELECT CARS_NAME FROM cars_deleted WHERE CAR_BRAND = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String value = rs.getString("CARS_NAME");

                    if (value != null && !value.trim().isEmpty()) {
                        set.add(value.trim().toUpperCase());
                    }
                }
            }
        }

        return set;
    }

    private static Set<String> loadDeletedPlates(String brandName) throws SQLException {
        Set<String> set = new HashSet<>();

        String sql = "SELECT CARS_PLATENUMBER FROM cars_deleted WHERE CAR_BRAND = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brandName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String value = rs.getString("CARS_PLATENUMBER");

                    if (value != null && !value.trim().isEmpty()) {
                        set.add(value.trim().toUpperCase());
                    }
                }
            }
        }

        return set;
    }

    private static Entry findEntryByPanel(JPanel panel, List<Entry> entries) {
        for (Entry entry : entries) {
            if (entry.panel == panel) {
                return entry;
            }
        }
        return null;
    }

    private static Entry findRegularEntryByCar(DeletedCarData car, List<Entry> entries) {
        for (Entry entry : entries) {
            if (entry.addedSlot) {
                continue;
            }

            String entryName = readText(entry.carName);
            String entryPlate = readText(entry.plateNumber);

            boolean sameName =
                    car.carName != null
                    && !car.carName.trim().isEmpty()
                    && car.carName.trim().equalsIgnoreCase(entryName);

            boolean samePlate =
                    car.plateNumber != null
                    && !car.plateNumber.trim().isEmpty()
                    && car.plateNumber.trim().equalsIgnoreCase(entryPlate);

            if (sameName || samePlate) {
                return entry;
            }
        }

        return null;
    }

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
