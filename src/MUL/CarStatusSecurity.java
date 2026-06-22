/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MUL;

/**
 *
 * @author artoc
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.sql.*;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarStatusSecurity {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static final Color REPAIR_RED = new Color(204, 0, 0);
    public static final Color BOOKED_ORANGE = new Color(255, 153, 0);
    public static final Color AVAILABLE_GREEN = new Color(0, 255, 51);

    public static class CarDotEntry {
        String panelName;
        Object carNameComponent;
        Object plateComponent;
        JLabel dotLabel;

        public CarDotEntry(String panelName, Object carNameComponent, Object plateComponent, JLabel dotLabel) {
            this.panelName = panelName;
            this.carNameComponent = carNameComponent;
            this.plateComponent = plateComponent;
            this.dotLabel = dotLabel;
        }
    }

    public static CarDotEntry car(String panelName, Object carNameComponent, Object plateComponent, JLabel dotLabel) {
        return new CarDotEntry(panelName, carNameComponent, plateComponent, dotLabel);
    }

    public static void startRealtimeRepairDotRefresh(Component parent, Runnable refreshAction) {
        AtomicBoolean running = new AtomicBoolean(false);

        Timer timer = new Timer(1000, e -> {
            if (parent instanceof Window && !((Window) parent).isDisplayable()) {
                ((Timer) e.getSource()).stop();
                return;
            }

            if (!running.compareAndSet(false, true)) {
                return;
            }

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    refreshAction.run();
                    return null;
                }

                @Override
                protected void done() {
                    running.set(false);
                }
            }.execute();
        });

        timer.setInitialDelay(1000);
        timer.start();
    }

    // SECURITY 1:
    // If car is booked/rented, DO NOT turn it red.
    // Orange booked/rented has higher priority than repair red.
    public static void markRepairDots(Component parent, List<CarDotEntry> cars) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            for (CarDotEntry car : cars) {
                String carName = readText(car.carNameComponent);
                String plate = readText(car.plateComponent);

                if (carName.isEmpty() && plate.isEmpty()) {
                    continue;
                }

                boolean bookedOrRented = isBookedOrRented(conn, carName, plate);
                boolean underRepair = isRepairStatus(getCarStatus(conn, carName, plate));

            System.out.println("====================================");
            System.out.println("SECURITY DOT CHECK");
            System.out.println("PANEL          : " + car.panelName);
            System.out.println("CAR NAME       : " + carName);
            System.out.println("PLATE          : " + plate);
            System.out.println("BOOKED/RENTED  : " + bookedOrRented);
            System.out.println("UNDER REPAIR   : " + underRepair);
            System.out.println("====================================");

                if (car.dotLabel != null) {
                    Color targetColor = bookedOrRented
                            ? BOOKED_ORANGE
                            : (underRepair ? REPAIR_RED : AVAILABLE_GREEN);

                    SwingUtilities.invokeLater(() -> {
                        car.dotLabel.setForeground(targetColor);
                        car.dotLabel.repaint();
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // SECURITY 2:
    // Call this before opening BOOKCAR / rent popup.
    // If car is red/under repair, renting is blocked.
    public static boolean canRentOrBook(Component parent, Object carNameComponent, Object plateComponent) {
        return canRentOrBook(parent, carNameComponent, plateComponent, null);
    }

    public static boolean canRentOrBook(
            Component parent,
            Object carNameComponent,
            Object plateComponent,
            JLabel statusDot
    ) {
        String carName = readText(carNameComponent);
        String plate = readText(plateComponent);

        if (carName.isEmpty() || plate.isEmpty()) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car slot is incomplete and cannot be booked or rented.",
                    "Car Data Missing",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        if (statusDot != null && isSameRgb(statusDot.getForeground(), REPAIR_RED)) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car is currently under repair/service.\nYou cannot rent or book this car.",
                    "Car Under Repair",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        if (statusDot != null && isSameRgb(statusDot.getForeground(), BOOKED_ORANGE)) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car is already booked/rented.",
                    "Car Not Available",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        final String status;
        final boolean bookedOrRented;
        try {
            status = getCarStatusOrThrow(carName, plate);
            bookedOrRented = isBookedOrRentedOrThrow(carName, plate);
        } catch (Exception ex) {
            showSecurityCheckFailed(parent, ex);
            return false;
        }

        if (isRepairStatus(status)) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car is currently under repair/service.\nYou cannot rent or book this car.",
                    "Car Under Repair",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        if (bookedOrRented) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car is already booked/rented.",
                    "Car Not Available",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return true;
    }

    public static boolean isBlockedDotColor(Color color) {
        return isSameRgb(color, REPAIR_RED) || isSameRgb(color, BOOKED_ORANGE);
    }

    private static boolean isSameRgb(Color first, Color second) {
        return first != null && second != null
                && first.getRed() == second.getRed()
                && first.getGreen() == second.getGreen()
                && first.getBlue() == second.getBlue();
    }

    // SECURITY 3:
    // Use this in Inventory before saving repair.
    // If car is currently booked/rented, do not allow setting repair.
    public static boolean canMarkAsRepair(Component parent, Object carNameComponent, Object plateComponent) {
        String carName = readText(carNameComponent);
        String plate = readText(plateComponent);

        final boolean bookedOrRented;
        try {
            bookedOrRented = isBookedOrRentedOrThrow(carName, plate);
        } catch (Exception ex) {
            showSecurityCheckFailed(parent, ex);
            return false;
        }

        if (bookedOrRented) {
            JOptionPane.showMessageDialog(
                    parent,
                    "This car is currently booked/rented.\nYou cannot mark it as repair until it is returned.",
                    "Booked/Rented Car",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return true;
    }

    public static boolean isBookedOrRented(String carName) {
        if (carName == null || carName.trim().isEmpty()) {
            return false;
        }

        try {
            return isBookedOrRentedOrThrow(carName);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isUnderRepair(String carName, String plate) {
        String status = getCarStatus(carName, plate);

        if (status == null) {
            return false;
        }

        String s = status.trim();

        if (s.isEmpty()) {
            return false;
        }

        String upper = s.toUpperCase();

        return isRepairStatus(upper);
    }

    public static boolean isRepairStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }

        String upper = status.trim().toUpperCase();

        // Inventory stores the selected repair-item description in CAR_STATUS.
        // Exclude known non-repair states; any other non-empty value is repair detail.
        return !upper.equals("NONE")
                && !upper.equals("OK")
                && !upper.equals("AVAILABLE")
                && !upper.equals("RETURNED")
                && !upper.equals("BOOKED")
                && !upper.equals("RENTED")
                && !upper.equals("CANCELLED")
                && !upper.equals("CANCELED")
                && !upper.equals("DELETED");
    }

    public static String getCarStatus(String carName, String plate) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return getCarStatus(conn, carName, plate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getCarStatusOrThrow(String carName, String plate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return getCarStatus(conn, carName, plate);
        }
    }

    private static boolean isBookedOrRentedOrThrow(String carName) throws SQLException {
        return isBookedOrRentedOrThrow(carName, "");
    }

    private static boolean isBookedOrRentedOrThrow(String carName, String plate) throws SQLException {
        if (carName == null || carName.trim().isEmpty()) {
            return false;
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return isBookedOrRented(conn, carName, plate);
        }
    }

    private static void showSecurityCheckFailed(Component parent, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                parent,
                "The car status could not be verified.\nThe operation was blocked to protect the booking records.",
                "Status Check Failed",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private static String getCarStatus(Connection conn, String carName, String plate)
            throws SQLException {
        if (carName != null && !carName.trim().isEmpty()
                && plate != null && !plate.trim().isEmpty()) {
            String sql =
                    "SELECT CAR_STATUS FROM cars_info "
                    + "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) "
                    + "AND TRIM(CARS_PLATENUMBER) = TRIM(?) LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, carName);
                ps.setString(2, plate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("CAR_STATUS");
                    }
                }
            }
        }

        if (carName != null && !carName.trim().isEmpty()) {
            String sql = "SELECT CAR_STATUS FROM cars_info WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, carName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("CAR_STATUS");
                    }
                }
            }
        }

        if (plate != null && !plate.trim().isEmpty()) {
            String sql = "SELECT CAR_STATUS FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, plate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("CAR_STATUS");
                    }
                }
            }
        }

        return null;
    }

    private static boolean isBookedOrRented(Connection conn, String carName, String plate) throws SQLException {
        if (carName == null || carName.trim().isEmpty()) {
            return false;
        }

        String sql;
        boolean hasPlate = plate != null && !plate.trim().isEmpty();

        if (hasPlate) {
            sql =
                    "SELECT 1 FROM customer "
                    + "WHERE TRIM(plate_number) = TRIM(?) "
                    + "AND status IN ('RENTED','BOOKED') LIMIT 1";
        } else {
            sql =
                    "SELECT 1 FROM customer "
                    + "WHERE UPPER(TRIM(vehicle_name)) = UPPER(TRIM(?)) "
                    + "AND status IN ('RENTED','BOOKED') LIMIT 1";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hasPlate ? plate : carName);
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
}
