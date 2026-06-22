/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author artoc
 */
public class REPAIR extends javax.swing.JPanel {

private final Set<String> selectedRepairItems = new LinkedHashSet<>();
private final Set<String> currentRepairItems = new LinkedHashSet<>();
private final Set<String> repairedItems = new LinkedHashSet<>();
private final List<RepairToggle> repairToggles = new ArrayList<>();
private boolean editExistingRepairMode = false;
private static final Color REPAIR_RED = new Color(255, 0, 0);
private static final Color REPAIRED_GREEN = new Color(0, 255, 51);

    /**
     * Creates new form REPAIR
     */
    public REPAIR() {
        initComponents();
        
    setupToggle(jLabel15, "ENGINE", jLabel32);
    setupToggle(jLabel19, "FRONT BUMPER", jLabel33);
    setupToggle(jLabel20, "REAR BUMPER", jLabel37);

    setupToggle(jLabel14, "TIRES", jLabel44, jLabel35, jLabel43, jLabel45);
    setupToggle(jLabel27, "DOORS", jLabel41, jLabel36);

    setupToggle(jLabel16, "BRAKES", jLabel34);

    setupToggle(jLabel17, "WINDOWS", jLabel40, jLabel39, jLabel42, jLabel38);

    setupToggle(jLabel21, "SIGNAL LIGHTS", jLabel46, jLabel30);

    }

    
private void setupToggle(JLabel trigger, String itemName, JLabel... targets) {

    for (JLabel t : targets) {
        if (t != null) t.setVisible(false);
    }

    final Color original = trigger.getForeground();
    trigger.setCursor(new Cursor(Cursor.HAND_CURSOR));
    RepairToggle repairToggle = new RepairToggle(trigger, itemName, original, targets);
    repairToggles.add(repairToggle);

    trigger.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (editExistingRepairMode) {
                if (!currentRepairItems.contains(itemName)) {
                    return;
                }

                if (repairedItems.contains(itemName)) {
                    repairedItems.remove(itemName);
                    repairToggle.setMarkedForRepair();
                } else {
                    repairedItems.add(itemName);
                    repairToggle.setMarkedRepaired();
                }
                return;
            }

            repairToggle.toggleForNewRepair();
        }
    });
}

public void setRepairEditMode(String currentStatus) {
    editExistingRepairMode = true;
    selectedRepairItems.clear();
    repairedItems.clear();
    currentRepairItems.clear();
    currentRepairItems.addAll(parseRepairItems(currentStatus));

    for (RepairToggle toggle : repairToggles) {
        if (currentRepairItems.contains(toggle.itemName)) {
            toggle.setMarkedForRepair();
        } else {
            toggle.reset();
        }
    }
}

public boolean hasEditableRepairItems() {
    return !currentRepairItems.isEmpty();
}

public boolean hasRepairedSelection() {
    return !repairedItems.isEmpty();
}

public String getRemainingRepairItemsAsOrString() {
    Set<String> remaining = new LinkedHashSet<>(currentRepairItems);
    remaining.removeAll(repairedItems);
    return String.join(" || ", remaining);
}

public static Set<String> parseRepairItems(String status) {
    Set<String> result = new LinkedHashSet<>();
    if (status == null || status.trim().isEmpty()) {
        return result;
    }

    String upperStatus = normalize(status);
    String[] knownItems = {
        "ENGINE",
        "FRONT BUMPER",
        "REAR BUMPER",
        "TIRES",
        "DOORS",
        "BRAKES",
        "WINDOWS",
        "SIGNAL LIGHTS"
    };

    for (String part : knownItems) {
        if (upperStatus.contains(part)) {
            result.add(part);
        }
    }

    return result;
}
    

public String getSelectedRepairItemsAsOrString() {
    return String.join(" || ", selectedRepairItems);
} 

private static String normalize(String value) {
    return value == null ? "" : value.trim().toUpperCase();
}

private class RepairToggle {
    private final JLabel trigger;
    private final String itemName;
    private final Color originalForeground;
    private final JLabel[] targets;
    private boolean selected;

    RepairToggle(JLabel trigger, String itemName, Color originalForeground, JLabel... targets) {
        this.trigger = trigger;
        this.itemName = itemName;
        this.originalForeground = originalForeground;
        this.targets = targets;
    }

    void toggleForNewRepair() {
        selected = !selected;
        setTargetsVisible(selected);
        trigger.setForeground(selected ? REPAIR_RED : originalForeground);

        if (selected) {
            selectedRepairItems.add(itemName);
        } else {
            selectedRepairItems.remove(itemName);
        }
    }

    void setMarkedForRepair() {
        selected = true;
        setTargetsVisible(true);
        setTargetsForeground(REPAIR_RED);
        setTriggerForeground(REPAIR_RED);
    }

    void setMarkedRepaired() {
        selected = true;
        setTargetsVisible(true);
        setTargetsForeground(REPAIRED_GREEN);
        setTriggerForeground(REPAIRED_GREEN);
    }

    void reset() {
        selected = false;
        setTargetsVisible(false);
        setTargetsForeground(REPAIR_RED);
        setTriggerForeground(originalForeground);
    }

    private void setTargetsVisible(boolean visible) {
        for (JLabel target : targets) {
            if (target != null) {
                target.setVisible(visible);
            }
        }
    }

    private void setTargetsForeground(Color color) {
        for (JLabel target : targets) {
            if (target != null) {
                target.setForeground(color);
                target.repaint();
            }
        }
    }

    private void setTriggerForeground(Color color) {
        trigger.setForeground(color);
        trigger.repaint();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel31 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 0));
        jLabel31.setText(".");
        add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -50, 30, 80));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("FRONT BUMPER");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 140, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("REAR BUMPER");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 462, 140, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ENGINE");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 60, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("DOORS");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 60, 34));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("WINDOWS");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 70, 34));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("TIRES");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 50, 60));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setText(".");
        add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 30, 80));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 0));
        jLabel32.setText(".");
        add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 30, 80));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText(".");
        add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 20, 80));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 0));
        jLabel39.setText(".");
        add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, 80));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 0));
        jLabel35.setText(".");
        add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 20, 80));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 0));
        jLabel37.setText(".");
        add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, -1, 80));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("IN REPAIR");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 80, 40));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 0));
        jLabel41.setText(".");
        add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, -1, 80));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 0, 0));
        jLabel40.setText(".");
        add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, 80));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 0, 0));
        jLabel43.setText(".");
        add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, -1, 80));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 0, 0));
        jLabel44.setText(".");
        add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 20, 80));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 0, 0));
        jLabel45.setText(".");
        add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 320, -1, 80));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 0));
        jLabel30.setText(".");
        add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, -1, 80));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 0, 0));
        jLabel46.setText(".");
        add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 20, 80));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 0));
        jLabel36.setText(".");
        add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, 80));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 0));
        jLabel38.setText(".");
        add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, -1, 80));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 0, 0));
        jLabel42.setText(".");
        add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, 80));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\MAZDA\\Car anatomy in glowing wireframe (4).png")); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 240, 420));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("BRAKES");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 70, 34));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("SIGNAL LIGHTS");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 80, 34));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 90)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 255, 51));
        jLabel47.setText(".");
        add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -20, 30, 80));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("REPAIRED");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 80, 40));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    // End of variables declaration//GEN-END:variables
}
