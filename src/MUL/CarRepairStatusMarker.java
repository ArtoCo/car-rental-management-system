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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.List;

public class CarRepairStatusMarker {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static final Color REPAIR_RED = new Color(204, 0, 0);

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

    public static void markRepairDots(Component parent, List<CarDotEntry> cars) {
        java.util.ArrayList<CarStatusSecurity.CarDotEntry> sharedEntries = new java.util.ArrayList<>();
        for (CarDotEntry car : cars) {
            sharedEntries.add(CarStatusSecurity.car(
                    car.panelName, car.carNameComponent, car.plateComponent, car.dotLabel));
        }
        CarStatusSecurity.markRepairDots(parent, sharedEntries);
    }

    private static String getRepairStatus(Connection conn, String carName, String plate) throws Exception {
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

        if (component instanceof JComboBox) {
            Object selected = ((JComboBox<?>) component).getSelectedItem();
            return selected == null ? "" : selected.toString().trim();
        }

        return component.toString().trim();
    }

    public static void refreshOpenBrandFrames() {
        for (Window window : Window.getWindows()) {
            if (window instanceof RepairStatusRefreshable && window.isDisplayable()) {
                ((RepairStatusRefreshable) window).refreshRepairDotColors();
            }
        }
    }
}
