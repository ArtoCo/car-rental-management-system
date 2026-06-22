/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MUL;

/**
 *
 * @author artoc
 */

import customer.ServiceCustomer;
import java.awt.Component;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CarSecurityGuard {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static boolean canMarkAsRepair(Component parent, Object carNameObj, Object plateObj) {
        return CarStatusSecurity.canMarkAsRepair(parent, carNameObj, plateObj);
    }

    public static boolean canRentOrBook(Component parent, Object carNameObj, Object plateObj) {
        return CarStatusSecurity.canRentOrBook(parent, carNameObj, plateObj);
    }

    public static boolean isBookedOrRented(String carName) {
        if (carName == null || carName.trim().isEmpty()) {
            return false;
        }

        try {
            ServiceCustomer service = new ServiceCustomer();
            return service.isCarActive(carName.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isUnderRepair(String carName, String plate) {
        String status = getRepairStatus(carName, plate);
        return CarStatusSecurity.isRepairStatus(status);
    }

    public static String getRepairStatus(String carName, String plate) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            if (carName != null && !carName.trim().isEmpty()
                    && plate != null && !plate.trim().isEmpty()) {
                String sql =
                        "SELECT CAR_STATUS FROM cars_info "
                        + "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) "
                        + "AND TRIM(CARS_PLATENUMBER) = TRIM(?) LIMIT 1";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, carName.trim());
                    ps.setString(2, plate.trim());

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
                    ps.setString(1, carName.trim());

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
                    ps.setString(1, plate.trim());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("CAR_STATUS");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String findCarNameByPlate(String plate) {
        if (plate == null || plate.trim().isEmpty()) {
            return "";
        }

        String sql = "SELECT CARS_NAME FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?) LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plate.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CARS_NAME");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String readText(Object component) {
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
