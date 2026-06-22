/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customer;

import customer.ServiceCustomer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class CarDotColorManager {

    private static final Color ORANGE = new Color(255, 153, 0);
    private static final Color GREEN = new Color(0, 255, 51);

    private static final Map<String, List<JLabel>> carDots = new HashMap<>();

    private CarDotColorManager() {
        // Utility class only
    }

    public static void register(String carName, JLabel dotLabel) {
        if (carName == null || dotLabel == null) {
            return;
        }

        String key = normalize(carName);

        if (key.isEmpty()) {
            return;
        }

        carDots.putIfAbsent(key, new ArrayList<>());

        if (!carDots.get(key).contains(dotLabel)) {
            carDots.get(key).add(dotLabel);
        }
    }

    public static void unregister(JLabel dotLabel) {
        if (dotLabel == null) {
            return;
        }

        for (List<JLabel> labels : carDots.values()) {
            labels.remove(dotLabel);
        }
    }

    public static void setActiveNow(String carName, boolean active) {
        String key = normalize(carName);

        if (key.isEmpty()) {
            return;
        }

        List<JLabel> labels = carDots.get(key);

        if (labels == null || labels.isEmpty()) {
            return;
        }

        Color color = active ? ORANGE : GREEN;

        SwingUtilities.invokeLater(() -> {
            for (JLabel label : labels) {
                label.setForeground(color);
                label.repaint();
            }
        });
    }

    public static void refreshFromDatabase(String carName) {
        String key = normalize(carName);

        if (key.isEmpty()) {
            return;
        }

        try {
            ServiceCustomer service = new ServiceCustomer();
            boolean active = service.isCarActive(carName);

            setActiveNow(carName, active);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshAllFromDatabase() {
        List<String> names = new ArrayList<>(carDots.keySet());

        for (String carName : names) {
            refreshFromDatabase(carName);
        }
    }

    private static String normalize(String carName) {
        if (carName == null) {
            return "";
        }

        return carName.trim().toUpperCase();
    }
}