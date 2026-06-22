/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import ADMIN.Customers;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import customer.ServiceCustomer;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.DefaultTableCellRenderer;
import popup.AppSession;
import popup.BOOKCAR;
import popup.CHECKINS;
import popup.CHECKOUTS;
import popup.CheckInDateStore;
import popup.CheckOutDateStore;
import popup.ROUNDPANEL;
import popup.RoundedBorder;
import popup.SCHEDULES;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;


/**
 *
 * @author artoc
 */
public class Schedule extends javax.swing.JFrame {

    private final Set<JButton> selectedButtons = new HashSet<>();
    private final Map<JButton, String> buttonNotes = new HashMap<>();
    private boolean isDragging = false;
    private JButton viewButton;
    private Set<LocalDate> greenDotDates = new HashSet<>();
    private static final Set<LocalDate> checkInDates = new HashSet<>();
    // At the class level (top of your calendar form)
    private LocalDate selectedDateForView = null;

    /**
     * Creates new form Schedules
     */
    public Schedule() {
        
        
        CheckInDateStore.loadCheckInDatesFromDB();  // Load the latest green-dot dates
        CheckOutDateStore.loadCheckOutDatesFromDB();
        
         UIManager.put("Spinner.background", Color.WHITE);
         UIManager.put("Spinner.foreground", Color.BLACK);
         UIManager.put("Spinner.editorBackground", Color.WHITE);
         UIManager.put("Spinner.editorForeground", Color.BLACK);
         UIManager.put("Spinner.border", BorderFactory.createLineBorder(Color.GRAY));
         
         // Force default list renderer to white (for year popup list)
         UIManager.put("List.background", Color.WHITE);
         UIManager.put("List.foreground", Color.BLACK);
         UIManager.put("Spinner.foreground", Color.BLACK);
         UIManager.put("Spinner.background", Color.WHITE);


        initComponents();
        customizeJCalendar1();
        GlassPanePopup.install(this);  // "this" must be a JFrame
        
                 
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
                
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

    }   
        
    public class CustomButton extends JButton {
    // For rounded corners
    private int arcWidth = 15;
    private int arcHeight = 15;
    private boolean isGradient = false;

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
    }

    public void setGradient(boolean isGradient) {
        this.isGradient = isGradient;
        repaint(); // Repaint the button to reflect the change
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        if (isGradient) {
            // Use gradient colors
            Color color1 = Color.decode("#dd1818"); // Red color
            Color color2 = Color.decode("#333333"); // Dark gray color
            GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
            g2.setPaint(gradient);
        } else {
            // Use the default color
            g2.setColor(getBackground());
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the background
        g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        // Draw the text
        super.paintComponent(g2);
        g2.dispose();
    }

    // Optionally, you can override paintBorder to customize the border
}
    
    public void setGreenDotDates(List<LocalDate> dates) {
    greenDotDates.clear();
    greenDotDates.addAll(dates);
    jCalendar1.repaint(); // Refresh the calendar UI
}

  
    private void customizeJCalendar1() {
    Color hoverColor = new Color(51, 51, 51);   // Hover/selection color
    Color gridColor = Color.GRAY;              // Grid border color
    Color textColor = Color.WHITE;             // Date and year text
    Color sundayColor = Color.RED;             // Sunday color

    jCalendar1.setBackground(Color.DARK_GRAY);
    jCalendar1.setDecorationBackgroundColor(Color.DARK_GRAY);
    jCalendar1.setSundayForeground(sundayColor);
    jCalendar1.setWeekdayForeground(Color.LIGHT_GRAY);

    // Customize month and year controls
    for (Component comp : jCalendar1.getDayChooser().getParent().getComponents()) {
        if (comp instanceof JPanel) {
            for (Component sub : ((JPanel) comp).getComponents()) {

                // Month combo
                if (sub instanceof JComboBox) {
                    JComboBox<?> monthCombo = (JComboBox<?>) sub;
                    monthCombo.setForeground(textColor);
                    monthCombo.setBackground(Color.BLACK);
                    monthCombo.setFont(new Font("SansSerif", Font.BOLD, 14));
                }

                // Year spinner
                if (sub instanceof JSpinner) {
                    JSpinner yearSpinner = (JSpinner) sub;
                    yearSpinner.setForeground(textColor);

                    JComponent editor = yearSpinner.getEditor();
                    if (editor instanceof JSpinner.DefaultEditor) {
                        JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                        JTextField spinnerText = defaultEditor.getTextField();
                        spinnerText.setForeground(textColor);
                        spinnerText.setBackground(Color.BLACK);
                        spinnerText.setCaretColor(textColor);

                        // Keep color when focused
                        spinnerText.addFocusListener(new java.awt.event.FocusAdapter() {
                            public void focusGained(java.awt.event.FocusEvent e) {
                                spinnerText.setForeground(textColor);
                                spinnerText.setBackground(Color.BLACK);
                            }

                            public void focusLost(java.awt.event.FocusEvent e) {
                                spinnerText.setForeground(textColor);
                                spinnerText.setBackground(Color.BLACK);
                            }
                        });
                    }

                    // Fix spinner arrow buttons
                    for (Component sub2 : yearSpinner.getComponents()) {
                        if (sub2 instanceof JButton) {
                            JButton arrow = (JButton) sub2;
                            arrow.setBackground(Color.DARK_GRAY);
                            arrow.setForeground(Color.WHITE);
                            arrow.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        }
                    }
                }
            }
        }
    }

    // Customize day buttons
    JPanel dayPanel = jCalendar1.getDayChooser().getDayPanel();
    Component[] components = dayPanel.getComponents();

    for (Component comp : components) {
        if (comp instanceof JButton) {
            JButton dayButton = (JButton) comp;

            dayButton.setContentAreaFilled(false);
            dayButton.setFocusPainted(false);
            dayButton.setForeground(textColor);
            dayButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
            dayButton.setBorder(new RoundedBorder(10, gridColor));
            
            // Set hand cursor on hover
            dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            dayButton.addActionListener(e -> {
            // Get clicked button's position relative to dayPanel
            Point location = dayButton.getLocation();
            int width = dayButton.getWidth();
            int height = dayButton.getHeight();

        });
            

            // Hover effect
            dayButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    dayButton.setBackground(hoverColor);
                    dayButton.setOpaque(true);
                    dayButton.repaint();
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    dayButton.setOpaque(false);
                    dayButton.repaint();
                }
            });


            // Custom UI for rounded selection and today gradient
        dayButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = b.getWidth();
        int height = b.getHeight();

        try {
            int buttonDay = Integer.parseInt(b.getText());
            Calendar calendarInView = jCalendar1.getCalendar();
            int viewMonth = calendarInView.get(Calendar.MONTH);
            int viewYear = calendarInView.get(Calendar.YEAR);

            LocalDate buttonDate = LocalDate.of(viewYear, viewMonth + 1, buttonDay);
            LocalDate today = LocalDate.now();

            // 🌈 Apply gradient background if date is today
            if (buttonDate.equals(today)) {
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0x23, 0x07, 0x4D),  // #23074d
                    width, height, new Color(0xCC, 0x53, 0x33) // #cc5333
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, width, height, 10, 10);
            }

            // ✅ Green dot - check-in
            Set<LocalDate> checkInDates = CheckInDateStore.getCheckInDates();
            if (checkInDates.contains(buttonDate)) {
                int dotSize = 10;
                int leftX = 5;
                int bottomY = height - dotSize - 4;
                g2.setColor(Color.GREEN);
                g2.fillOval(leftX, bottomY, dotSize, dotSize);
            }

            // 🟠 Orange dot - check-out
            Set<LocalDate> checkOutDates = CheckOutDateStore.getCheckOutDates();
            if (checkOutDates.contains(buttonDate)) {
                int dotSize = 10;
                int rightX = width - dotSize - 5;
                int bottomY = height - dotSize - 4;
                g2.setColor(new Color(255, 153, 0)); // Orange
                g2.fillOval(rightX, bottomY, dotSize, dotSize);
            }

        } catch (Exception ex) {
            // Ignore invalid day buttons
        }

        // 🖌 Draw text on top
        super.paint(g2, c);
        g2.dispose();
    }
});



        }
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

        jPanel2 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel1 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel5 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton2 = new test.HoverButton();
        jLabel36 = new javax.swing.JLabel();
        hoverButton3 = new test.HoverButton();
        hoverButton4 = new test.HoverButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        hoverButton7 = new test.HoverButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        buttonAction4 = new employees.ButtonAction();
        buttonAction5 = new employees.ButtonAction();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1360, 712));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1360, 712));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setMinimumSize(new java.awt.Dimension(1350, 701));
        jPanel2.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setPreferredSize(new java.awt.Dimension(987, 705));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCalendar1.setBackground(new java.awt.Color(0, 150, 225));
        jCalendar1.setForeground(new java.awt.Color(255, 255, 255));
        jCalendar1.setSundayForeground(new java.awt.Color(255, 255, 255));
        jCalendar1.setTodayButtonVisible(true);
        jPanel1.add(jCalendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 960, 630));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, -1, 660));

        jPanel5.setBackground(new java.awt.Color(27, 26, 26));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 670, 30, 40));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel5.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        button2.setText("    Schedule");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel5.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("   Dashboard");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("   Customers");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("      Rent and Bookings");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BMW\\icons8-employee-35.png")); // NOI18N
        jPanel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 30, 40));

        hoverButton5.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton5.setText("   Employees");
        hoverButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton5ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 270, 40));

        hoverButton6.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton6.setText("     Inventory");
        hoverButton6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 270, 40));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-inventory-35.png")); // NOI18N
        jPanel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 10, 330, 691));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Schedule Calendar");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, -1, 40));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 255, 51));
        jLabel24.setText(".");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, -20, 70, 50));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("PICKUP CARS");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 100, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 153, 0));
        jLabel14.setText(".");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, -20, 70, 50));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("RETURNED CARS");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 120, 20));

        buttonAction4.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction4.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction4.setText("VIEW RETURNED");
        buttonAction4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction4ActionPerformed(evt);
            }
        });
        jPanel2.add(buttonAction4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 10, 160, 20));

        buttonAction5.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction5.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction5.setText("VIEW PICKUPS");
        buttonAction5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction5ActionPerformed(evt);
            }
        });
        jPanel2.add(buttonAction5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, 140, 20));

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed

         CHECKOUTS sched = new CHECKOUTS();

String actions[] = new String[]{"CLOSE"};
DefaultOption option = new DefaultOption() {
    @Override
    public boolean closeWhenClickOutside() {
        return true;
    }
};

GlassPanePopup.showPopup(
    new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
        dialog.closePopup(); // just closes when "Close" is clicked
    }),

    option
);
    }//GEN-LAST:event_buttonAction4ActionPerformed

    private void buttonAction5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction5ActionPerformed
        // TODO add your handling code here:
        CHECKINS sched = new CHECKINS();

String actions[] = new String[]{"CLOSE"};
DefaultOption option = new DefaultOption() {
    @Override
    public boolean closeWhenClickOutside() {
        return true;
    }
};

GlassPanePopup.showPopup(
    new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
        dialog.closePopup(); // just closes when "Close" is clicked
    }),

    option
);
    }//GEN-LAST:event_buttonAction5ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton2ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Dashboard::new);
    }//GEN-LAST:event_hoverButton2ActionPerformed

    private void hoverButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton3ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Customers::new);
    }//GEN-LAST:event_hoverButton3ActionPerformed

    private void hoverButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton4ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, RentANDbookings::new);
    }//GEN-LAST:event_hoverButton4ActionPerformed

    private void hoverButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton5ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, User::new);
    }//GEN-LAST:event_hoverButton5ActionPerformed

    private void hoverButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton6ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Inventory::new);
    }//GEN-LAST:event_hoverButton6ActionPerformed

    private void hoverButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton7ActionPerformed
        // TODO add your handling code here:
        Login pi = new Login();
        test.FadeTransition.fadeSwitchCentered(this, pi);
    }//GEN-LAST:event_hoverButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    FlatRobotoFont.install();
    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
    FlatMacDarkLaf.setup(); // Change to the dark theme
    FlatLaf.registerCustomDefaultsSource("sample.theme");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new Schedule().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private test.Button button2;
    private employees.ButtonAction buttonAction4;
    private employees.ButtonAction buttonAction5;
    private test.HoverButton hoverButton2;
    private test.HoverButton hoverButton3;
    private test.HoverButton hoverButton4;
    private test.HoverButton hoverButton5;
    private test.HoverButton hoverButton6;
    private test.HoverButton hoverButton7;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
