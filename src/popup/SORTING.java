/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author ArtoCO
 */
public class SORTING extends javax.swing.JPanel {

private JPanel selectedPanel = null;
private boolean editEnabled = false;

private final Map<JPanel, Integer> attemptMap = new HashMap<>();
private final Map<JPanel, Integer> lockoutMinutes = new HashMap<>();
private final Map<JPanel, Long> lockoutEndTime = new HashMap<>();
private final Map<JPanel, JLabel> countdownLabels = new HashMap<>();
private final Map<JPanel, JProgressBar> progressBars = new HashMap<>();
private final Map<JPanel, Timer> progressTimers = new HashMap<>();



private Timer countdownTimer;




    /**
     * Creates new form SORTING
     */
    public SORTING() {
        initComponents();
        setupPanelClicks(); 
        
        
UIManager.put("ProgressBar.background", Color.LIGHT_GRAY);
UIManager.put("ProgressBar.foreground", new Color(255, 69, 0));  // Custom fill
UIManager.put("ProgressBar.selectionBackground", Color.WHITE);
UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

    }
    
    private void setupPanelClicks() {
    registerPanel(jPanel3);
    registerPanel(jPanel4);
    registerPanel(jPanel5);
}

private void registerPanel(JPanel panel) {
    attemptMap.put(panel, 3);
    lockoutMinutes.put(panel, 1);
    lockoutEndTime.put(panel, 0L);

    panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Setup modern progress bar (hidden initially)
    JProgressBar progressBar = new JProgressBar();
    progressBar.setVisible(false);
    progressBar.setMaximum(100);
    progressBar.setValue(100);
    progressBar.setForeground(new Color(255, 69, 0)); // Modern red-orange
    progressBar.setBorderPainted(false);
    progressBar.setPreferredSize(new Dimension(panel.getWidth(), 4)); // Thin line
    progressBars.put(panel, progressBar);

    panel.setLayout(new BorderLayout());
    panel.add(progressBar, BorderLayout.SOUTH);

    panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!editEnabled) {
                JOptionPane.showMessageDialog(null, "Press the Edit button first to enable deleting.");
                return;
            }

            selectedPanel = panel;
            showProgressCountdownIfLocked(panel);

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this panel?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                attemptPasswordAndDelete(panel);
            }
        }
    });
}


private void attemptPasswordAndDelete(JPanel panel) {
    long now = System.currentTimeMillis();
    long lockedUntil = lockoutEndTime.get(panel);

    if (now < lockedUntil) {
        showProgressCountdownIfLocked(panel);
        return;
    }

    int attemptsLeft = attemptMap.get(panel);

    while (attemptsLeft > 0) {
        String input = JOptionPane.showInputDialog(this, "Enter password to delete:");

        if (input == null) {
            return; // Cancel pressed
        }

        if (config.AppConfig.verifyDeletePassword(input)) {
            jPanel2.remove(panel);
            jPanel2.revalidate();
            jPanel2.repaint();

            // Reset
            attemptMap.put(panel, 3);
            lockoutMinutes.put(panel, 1);
            countdownLabels.get(panel).setVisible(false);
            editEnabled = false;
            selectedPanel = null;
            return;
        } else {
            attemptsLeft--;
            attemptMap.put(panel, attemptsLeft);
            JOptionPane.showMessageDialog(this, "Wrong password. Attempts left: " + attemptsLeft);
        }
    }

    // Lockout
    int minutes = lockoutMinutes.get(panel);
    long newLockEnd = System.currentTimeMillis() + minutes * 60 * 1000L;
    lockoutEndTime.put(panel, newLockEnd);
    lockoutMinutes.put(panel, minutes + 1);
    attemptMap.put(panel, 3);
    showProgressCountdownIfLocked(panel);
}

private void showProgressCountdownIfLocked(JPanel panel) {
    long end = lockoutEndTime.get(panel);
    long now = System.currentTimeMillis();
    long duration = end - now;

    if (duration <= 0) {
        JProgressBar pb = progressBars.get(panel);
        pb.setVisible(false);
        if (progressTimers.containsKey(panel)) {
            progressTimers.get(panel).stop();
        }
        return;
    }

    JProgressBar progressBar = progressBars.get(panel);
    progressBar.setVisible(true);
    progressBar.setValue(100);

    Timer timer = new Timer(1000, null);
    final long total = duration;

    timer.addActionListener(new ActionListener() {
        long remaining = duration;

        @Override
        public void actionPerformed(ActionEvent e) {
            remaining -= 1000;
            int percent = (int) ((remaining * 100) / total);
            progressBar.setValue(percent);

            if (remaining <= 0) {
                progressBar.setVisible(false);
                timer.stop();
            }
        }
    });

    if (progressTimers.containsKey(panel)) {
        progressTimers.get(panel).stop();
    }

    progressTimers.put(panel, timer);
    timer.start();
}





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        buttonAction4 = new employees.ButtonAction();

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel3);

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4);

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel5);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1120, 480));

        buttonAction4.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction4.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction4.setText("EDIT");
        buttonAction4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction4ActionPerformed(evt);
            }
        });
        jPanel1.add(buttonAction4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed

    }//GEN-LAST:event_buttonAction4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private employees.ButtonAction buttonAction4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
