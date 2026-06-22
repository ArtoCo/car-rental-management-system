/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MUL;

import ADMIN.Inventory;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CarInventorySaver {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static class CarEntry {

        String panelName;
        Object carName;
        Object carType;
        Object plateNumber;
        Object fuel;
        Object seats;
        Object dailyRent;
        Object modelYear;
        Object ratings;
        Object transmission;
        JLabel imageLabel;

        public CarEntry(
                String panelName,
                Object carName,
                Object carType,
                Object plateNumber,
                Object fuel,
                Object seats,
                Object dailyRent,
                Object modelYear,
                Object ratings,
                Object transmission,
                JLabel imageLabel
        ) {
            this.panelName = panelName;
            this.carName = carName;
            this.carType = carType;
            this.plateNumber = plateNumber;
            this.fuel = fuel;
            this.seats = seats;
            this.dailyRent = dailyRent;
            this.modelYear = modelYear;
            this.ratings = ratings;
            this.transmission = transmission;
            this.imageLabel = imageLabel;
        }
    }

    public static CarEntry car(
            String panelName,
            Object carName,
            Object carType,
            Object plateNumber,
            Object fuel,
            Object seats,
            Object dailyRent,
            Object modelYear,
            Object ratings,
            Object transmission,
            JLabel imageLabel
    ) {
        return new CarEntry(
                panelName,
                carName,
                carType,
                plateNumber,
                fuel,
                seats,
                dailyRent,
                modelYear,
                ratings,
                transmission,
                imageLabel
        );
    }

    public static void saveToInventory(JFrame parent, List<CarEntry> cars, boolean openInventoryAfterSave) {
        int savedCount = 0;

        try {
            for (CarEntry car : cars) {

                String carName = readText(car.carName);
                String carType = readText(car.carType);
                String plateNumber = readText(car.plateNumber);
                String fuel = readText(car.fuel);
                String seats = readText(car.seats);
                String dailyRent = readText(car.dailyRent);
                String modelYear = readText(car.modelYear);
                String ratings = readText(car.ratings);
                String transmission = readText(car.transmission);
                byte[] imageBytes = getImageBytesFromLabel(car.imageLabel);

                trace(
                        car.panelName,
                        carName,
                        carType,
                        plateNumber,
                        fuel,
                        seats,
                        dailyRent,
                        modelYear,
                        ratings,
                        transmission,
                        imageBytes
                );

                if (carName.isEmpty()) {
                    System.out.println("SKIPPED " + car.panelName + ": CARS_NAME is empty.");
                    continue;
                }

                saveOrUpdate(
                        carName,
                        carType,
                        plateNumber,
                        fuel,
                        seats,
                        dailyRent,
                        modelYear,
                        ratings,
                        transmission,
                        imageBytes
                );

                savedCount++;
            }

            JOptionPane.showMessageDialog(
                    parent,
                    savedCount + " car data saved to cars_info.",
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (openInventoryAfterSave) {
                Inventory inventory = new Inventory();
                inventory.setVisible(true);
                parent.dispose();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    parent,
                    "Failed to save car data:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void saveAlphabeticallyToInventory(List<CarEntry> cars) {
        List<CarEntry> sortedCars = new ArrayList<>(cars);
        sortedCars.sort(Comparator.comparing(
                car -> readText(car.carName),
                String.CASE_INSENSITIVE_ORDER
        ));

        for (CarEntry car : sortedCars) {
            try {
                String carName = readText(car.carName);
                String carType = readText(car.carType);
                String plateNumber = readText(car.plateNumber);
                String fuel = readText(car.fuel);
                String seats = readText(car.seats);
                String dailyRent = readText(car.dailyRent);
                String modelYear = readText(car.modelYear);
                String ratings = readText(car.ratings);
                String transmission = readText(car.transmission);
                byte[] imageBytes = getImageBytesFromLabel(car.imageLabel);

                if (carName.isEmpty() || plateNumber.isEmpty()) {
                    System.out.println("SKIPPED " + car.panelName
                            + ": CARS_NAME or CARS_PLATENUMBER is empty.");
                    continue;
                }

                saveOrUpdate(
                        carName,
                        carType,
                        plateNumber,
                        fuel,
                        seats,
                        dailyRent,
                        modelYear,
                        ratings,
                        transmission,
                        imageBytes
                );
            } catch (Exception e) {
                System.err.println("Failed to sync " + car.panelName + " to cars_info.");
                e.printStackTrace();
            }
        }
    }

    private static void saveOrUpdate(
            String carName,
            String carType,
            String plateNumber,
            String fuel,
            String seats,
            String dailyRent,
            String modelYear,
            String ratings,
            String transmission,
            byte[] imageBytes
    ) throws SQLException {

        String checkSql =
                "SELECT CARS_NAME FROM cars_info " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        String insertSql =
                "INSERT INTO cars_info " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateSql =
                "UPDATE cars_info SET " +
                "CARS_TYPE = ?, " +
                "CARS_PLATENUMBER = ?, " +
                "CARS_FUEL = ?, " +
                "CARS_SEATS = ?, " +
                "CARS_DAILYRENT = ?, " +
                "CARS_MODEL = ?, " +
                "CARS_RATINGS = ?, " +
                "CARS_IMAGE = COALESCE(?, CARS_IMAGE), " +
                "CAR_TRANSMISSION = ? " +
                "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            if (isDeletedCar(conn, carName, plateNumber)) {
                System.out.println("SKIPPED deleted car in cars_info sync: " + carName);
                return;
            }

            checkPs.setString(1, plateNumber);

            boolean exists = false;
            String existingCarName = "";
            int matchingRows = 0;

            try (ResultSet rs = checkPs.executeQuery()) {
                while (rs.next()) {
                    matchingRows++;
                    if (matchingRows == 1) {
                        existingCarName = rs.getString("CARS_NAME");
                    }
                }
            }

            if (matchingRows > 1) {
                throw new SQLException(
                        "Duplicate plate in cars_info: " + plateNumber
                        + " has " + matchingRows + " rows. No row was updated."
                );
            }

            exists = matchingRows == 1;

            if (exists) {
                if (existingCarName != null && !existingCarName.trim().equalsIgnoreCase(carName)) {
                    throw new SQLException(
                            "Plate collision in cars_info: " + plateNumber
                            + " belongs to " + existingCarName
                            + ", not " + carName
                    );
                }

                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, carType);
                    ps.setString(2, plateNumber);
                    ps.setString(3, fuel);
                    ps.setString(4, seats);
                    ps.setString(5, dailyRent);
                    ps.setString(6, modelYear);
                    ps.setString(7, ratings);

                    if (imageBytes != null) {
                        ps.setBytes(8, imageBytes);
                    } else {
                        ps.setNull(8, Types.BLOB);
                    }

                    ps.setString(9, transmission);
                    ps.setString(10, plateNumber);

                    ps.executeUpdate();
                    System.out.println("UPDATED cars_info: " + carName);
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, carName);
                    ps.setString(2, carType);
                    ps.setString(3, plateNumber);
                    ps.setString(4, fuel);
                    ps.setString(5, seats);
                    ps.setString(6, dailyRent);
                    ps.setString(7, modelYear);
                    ps.setString(8, ratings);

                    if (imageBytes != null) {
                        ps.setBytes(9, imageBytes);
                    } else {
                        ps.setNull(9, Types.BLOB);
                    }

                    ps.setString(10, transmission);

                    ps.executeUpdate();
                    System.out.println("INSERTED cars_info: " + carName);
                }
            }
        }
    }

    private static boolean isDeletedCar(Connection conn, String carName, String plateNumber)
            throws SQLException {
        String sql =
                "SELECT 1 FROM cars_deleted "
                + "WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) "
                + "OR ((CARS_PLATENUMBER IS NULL OR TRIM(CARS_PLATENUMBER) = '') "
                + "AND UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?))) LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plateNumber);
            ps.setString(2, carName);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
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

        if (component instanceof JComboBox) {
            Object selected = ((JComboBox<?>) component).getSelectedItem();
            return selected == null ? "" : selected.toString().trim();
        }

        return component.toString().trim();
    }

    private static byte[] getImageBytesFromLabel(JLabel label) throws IOException {
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

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return baos.toByteArray();
    }

    private static void trace(
            String panelName,
            String carName,
            String carType,
            String plateNumber,
            String fuel,
            String seats,
            String dailyRent,
            String modelYear,
            String ratings,
            String transmission,
            byte[] imageBytes
    ) {
        System.out.println("====================================");
        System.out.println("TRACE PANEL: " + panelName);
        System.out.println("CARS_NAME        = " + carName);
        System.out.println("CARS_TYPE        = " + carType);
        System.out.println("CARS_PLATENUMBER = " + plateNumber);
        System.out.println("CARS_FUEL        = " + fuel);
        System.out.println("CARS_SEATS       = " + seats);
        System.out.println("CARS_DAILYRENT   = " + dailyRent);
        System.out.println("CARS_MODEL       = " + modelYear);
        System.out.println("CARS_RATINGS     = " + ratings);
        System.out.println("CAR_TRANSMISSION = " + transmission);
        System.out.println("CARS_IMAGE       = " + (imageBytes == null ? "NULL" : imageBytes.length + " bytes"));
        System.out.println("====================================");
    }
}
