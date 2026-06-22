/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MUL;

/**
 *
 * @author artoc
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JLabel;

public class BookRentRepairBlocker {

    public static class Entry {
        AbstractButton button;
        Object carNameField;
        Object plateField;

        public Entry(AbstractButton button, Object carNameField, Object plateField) {
            this.button = button;
            this.carNameField = carNameField;
            this.plateField = plateField;
        }
    }

    public static Entry protect(AbstractButton button, Object carNameField, Object plateField) {
        return new Entry(button, carNameField, plateField);
    }

    public static void install(Component parent, List<Entry> entries) {
        for (Entry entry : entries) {
            protectButton(parent, entry.button, entry.carNameField, entry.plateField);
        }
    }

    private static void protectButton(
            Component parent,
            AbstractButton button,
            Object carNameField,
            Object plateField
    ) {
        if (button == null) return;

        // Prevent installing twice
        Object alreadyInstalled = button.getClientProperty("REPAIR_BLOCKER_INSTALLED");
        if (Boolean.TRUE.equals(alreadyInstalled)) {
            return;
        }

        // Get the existing NetBeans ActionListeners
        ActionListener[] oldListeners = button.getActionListeners();

        // Remove existing ActionListeners
        for (ActionListener listener : oldListeners) {
            button.removeActionListener(listener);
        }

        // Add protected wrapper
        button.addActionListener((ActionEvent evt) -> {

            JLabel statusDot = findStatusDot(button.getParent());
            boolean allowed = CarStatusSecurity.canRentOrBook(
                    parent, carNameField, plateField, statusDot);

            if (!allowed) {
                System.out.println("BOOK/RENT BLOCKED: car is not available.");
                return;
            }

            // Continue original button code
            for (ActionListener listener : oldListeners) {
                listener.actionPerformed(evt);
            }
        });

        button.putClientProperty("REPAIR_BLOCKER_INSTALLED", true);
    }

    private static JLabel findStatusDot(Component component) {
        if (!(component instanceof Container)) {
            return null;
        }

        for (Component child : ((Container) component).getComponents()) {
            if (child instanceof JLabel) {
                JLabel label = (JLabel) child;
                if (CarStatusSecurity.isBlockedDotColor(label.getForeground())) {
                    return label;
                }
            }
        }
        return null;
    }
}
