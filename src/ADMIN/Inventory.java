/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import ADMIN.Customers;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import customer.ServiceCustomer;
import customers.CheckBoxTableHeaderRenderer;
import customers.ModelCustomer1;
import customers.TableHeaderAlignment;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javaswingdev.chart.ModelChart;
import raven.popup.GlassPanePopup;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.sql.Connection;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import popup.BOOKED_RENTED;
import popup.LabelNumberAnimator;
import popup.TABLE_PAIDS;
import popup.TABLE_PARTIAL;
import raven.popup.DefaultOption;
import raven.popup.component.SimplePopupBorder;
import ADMIN.RentANDbookings;
import MUL.CarRepairStatusMarker;
import popup.BOOKCAR;
import popup.REPAIR;
import popup.ROUNDPANEL;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import MUL.CarSecurityGuard;
import MUL.InventoryMetrics;


/**
 *
 * @author ArtoCO
 */
public class Inventory extends javax.swing.JFrame {

    /**
     * Creates new form Inventory
     */
    public Inventory() {
        initComponents();
                
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        init();
        installNumericInventorySorting();
        enableSingleCheckboxSelection();
        loadInventoryTableFromDatabase();
        startRealtimeInventoryMetrics();

        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)


        GlassPanePopup.install(this);

                
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
    @Override public void insertUpdate(DocumentEvent e) { searchAndLoadInventoryTable(); }
    @Override public void removeUpdate(DocumentEvent e) { searchAndLoadInventoryTable(); }
    @Override public void changedUpdate(DocumentEvent e) { searchAndLoadInventoryTable(); }
});
        
    }

private boolean inventoryMetricsRefreshRunning = false;
private int lastRealtimeInventoryTotal = -1;

private void startRealtimeInventoryMetrics() {
    Timer timer = new Timer(1000, e -> {
        if (!isDisplayable()) {
            ((Timer) e.getSource()).stop();
            return;
        }
        refreshInventoryMetricsAsync();
    });
    timer.setInitialDelay(1000);
    timer.start();
}

private void refreshInventoryMetricsAsync() {
    if (inventoryMetricsRefreshRunning) return;

    inventoryMetricsRefreshRunning = true;

    new SwingWorker<InventoryMetrics.Snapshot, Void>() {
        @Override
        protected InventoryMetrics.Snapshot doInBackground() throws Exception {
            return InventoryMetrics.load();
        }

        @Override
        protected void done() {
            try {
                InventoryMetrics.Snapshot snapshot = get();
                MUL.UiCountAnimator.animate(jLabel21, snapshot.total, 350);
                MUL.UiCountAnimator.animate(jLabel22, snapshot.available, 350);
                MUL.UiCountAnimator.animate(jLabel23, snapshot.rented, 350);
                MUL.UiCountAnimator.animate(jLabel24, snapshot.booked, 350);
                MUL.UiCountAnimator.animate(jLabel26, snapshot.repair, 350);

                if (lastRealtimeInventoryTotal != snapshot.total) {
                    lastRealtimeInventoryTotal = snapshot.total;
                    searchAndLoadInventoryTable();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                inventoryMetricsRefreshRunning = false;
            }
        }
    }.execute();
}

private void refreshInventoryAfterRepairStatusChange() {
    if (jTextField1.getText().trim().isEmpty()) {
        loadInventoryTableFromDatabase();
    } else {
        searchAndLoadInventoryTable();
    }
    refreshInventoryMetricsAsync();
    CarRepairStatusMarker.refreshOpenBrandFrames();
}

private void installNumericInventorySorting() {
    javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter =
            new javax.swing.table.TableRowSorter<>(jTable1.getModel());
    java.util.Comparator<Object> numericComparator =
            (left, right) -> Double.compare(parseNumericCell(left), parseNumericCell(right));
    sorter.setComparator(1, numericComparator);
    sorter.setComparator(6, numericComparator);
    sorter.setComparator(7, numericComparator);
    sorter.setComparator(8, numericComparator);
    sorter.setComparator(9, numericComparator);
    jTable1.setRowSorter(sorter);
}

private double parseNumericCell(Object value) {
    if (value == null) return 0.0;
    String cleaned = value.toString().replaceAll("[^0-9.-]", "");
    if (cleaned.isEmpty() || "-".equals(cleaned) || ".".equals(cleaned)) return 0.0;
    try {
        return Double.parseDouble(cleaned);
    } catch (NumberFormatException ex) {
        return 0.0;
    }
}
    
private String getSingleCheckedCarName() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    int count = 0;
    String carName = null;

    for (int i = 0; i < model.getRowCount(); i++) {
        if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
            count++;
            Object v = model.getValueAt(i, 2); // CAR NAME column
            carName = (v == null) ? null : v.toString().trim();
        }
    }

    if (count == 0) {
        JOptionPane.showMessageDialog(this, "Please select 1 car first.");
        return null;
    }

    if (count > 1) {
        JOptionPane.showMessageDialog(this, "Please select only 1 car.");
        return null;
    }

    return carName;
}

public void updateFromDashboard(String value21, String value24, int progressVal) {
    jLabel21.setText(value21);
    jLabel23.setText(value24);
    jLabel22.setText(String.valueOf(progressVal));
}

public class DatabaseConnection {

    }

public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = config.AppConfig.getDatabaseUrl();
            String user = config.AppConfig.getDatabaseUser();
            String password = config.AppConfig.getDatabasePassword();

            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection established successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    
    private void init() {

    // Header styling
    jTable1.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
            + "height:30;"
            + "hoverBackground:null;"
            + "separatorColor:$TableHeader.background;"
            + "font:bold;");
    jTable1.getTableHeader().putClientProperty("pressedBackground", new Color(51, 51, 51));

    // Table row styling
    jTable1.putClientProperty(FlatClientProperties.STYLE, ""
            + "rowHeight:40;"
            + "showHorizontalLines:true;"
            + "intercellSpacing:0,1;"
            + "cellFocusColor:$TableHeader.hoverBackground;"
            + "selectionBackground:$TableHeader.hoverBackground;"
            + "selectionForeground:$Table.foreground;");

    // ✅ Checkbox column renderer (leave as is)
    jTable1.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(jTable1, 0));

    // ✅ Align all headers
    jTable1.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(jTable1));

    // ❌ Removed: Profile image renderer (column 2)
    // jTable1.getColumnModel().getColumn(2).setCellRenderer(new ProfileTableRenderer(jTable1));

    // Scroll styling
    jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
            + "trackArc:999;"
            + "trackInsets:3,3,3,3;"
            + "thumbInsets:3,3,3,3;"
            + "background:$Table.backgound;");

    // Search placeholder
    jTextField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
}
    
    
private void loadInventoryTableFromDatabase() {

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    String sql = "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                 "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CAR_TRANSMISSION, CAR_STATUS " +
                 "FROM cars_info ORDER BY CARS_NAME ASC, CARS_PLATENUMBER ASC";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        int rowNum = 1;

        while (rs.next()) {

            Object[] row = new Object[] {
                false,
                rowNum++,
                rs.getString("CARS_NAME"),
                rs.getString("CARS_TYPE"),
                rs.getString("CARS_PLATENUMBER"),
                rs.getString("CARS_FUEL"),
                rs.getString("CARS_SEATS"),
                rs.getString("CARS_DAILYRENT"),
                rs.getString("CARS_MODEL"),
                rs.getString("CARS_RATINGS"),
                rs.getString("CAR_TRANSMISSION"),
                rs.getString("CAR_STATUS")
            };

            model.addRow(row);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load inventory: " + ex.getMessage());
    }
}

private void searchAndLoadInventoryTable() {
    String keyword = jTextField1.getText().trim();

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    String baseSql =
        "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
        "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CAR_TRANSMISSION, CAR_STATUS " +
        "FROM cars_info";

    String searchSql =
        baseSql +
        " WHERE CARS_NAME LIKE ? OR CARS_TYPE LIKE ? OR CARS_PLATENUMBER LIKE ? OR " +
        "CARS_FUEL LIKE ? OR CARS_SEATS LIKE ? OR CARS_DAILYRENT LIKE ? OR " +
        "CARS_MODEL LIKE ? OR CARS_RATINGS LIKE ? OR CAR_TRANSMISSION LIKE ? OR " +
        "CAR_STATUS LIKE ?";

    boolean isSearching = !keyword.isEmpty();
    String sql = (isSearching ? searchSql : baseSql)
            + " ORDER BY CARS_NAME ASC, CARS_PLATENUMBER ASC";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if (isSearching) {
            String k = "%" + keyword + "%";

            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ps.setString(4, k);
            ps.setString(5, k);
            ps.setString(6, k);
            ps.setString(7, k);
            ps.setString(8, k);
            ps.setString(9, k);
            ps.setString(10, k);
        }

        try (ResultSet rs = ps.executeQuery()) {
            int rowNum = 1;

            while (rs.next()) {
                model.addRow(new Object[]{
                    false,
                    rowNum++,
                    rs.getString("CARS_NAME"),
                    rs.getString("CARS_TYPE"),
                    rs.getString("CARS_PLATENUMBER"),
                    rs.getString("CARS_FUEL"),
                    rs.getString("CARS_SEATS"),
                    rs.getString("CARS_DAILYRENT"),
                    rs.getString("CARS_MODEL"),
                    rs.getString("CARS_RATINGS"),
                    rs.getString("CAR_TRANSMISSION"),
                    rs.getString("CAR_STATUS")
                });
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Search failed: " + e.getMessage());
    }
}
    

    private void updateTime() {
        Calendar now = Calendar.getInstance();
        // 🔧 Use 'hh' for 12-hour format and 'a' for AM/PM
        SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss a");
        String time = sdf.format(now.getTime());
        jLabel5.setText(time);
    }

    private void updateDate() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        String date = sdf.format(now.getTime());
        jLabel4.setText(date);
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

    }
    

private boolean adjustingCheckbox = false;

private void enableSingleCheckboxSelection() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    model.addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            if (adjustingCheckbox) return;
            if (e.getType() != TableModelEvent.UPDATE) return;

            int row = e.getFirstRow();
            int col = e.getColumn();

            // checkbox column is 0
            if (col != 0 || row < 0) return;

            Object v = model.getValueAt(row, 0);
            if (!Boolean.TRUE.equals(v)) return;

            // if user checked one row, uncheck all others
            adjustingCheckbox = true;
            try {
                for (int r = 0; r < model.getRowCount(); r++) {
                    if (r != row) model.setValueAt(false, r, 0);
                }
            } finally {
                adjustingCheckbox = false;
            }
        }
    });
}

private String getSingleCheckedPlateNumber() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    int count = 0;
    String plate = null;

    for (int i = 0; i < model.getRowCount(); i++) {
        if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
            count++;
            Object v = model.getValueAt(i, 4); // PLATE NUMBER column
            plate = (v == null) ? null : v.toString().trim();
        }
    }

    if (count == 0) {
        JOptionPane.showMessageDialog(this, "Please select 1 car first.");
        return null;
    }
    if (count > 1) {
        JOptionPane.showMessageDialog(this, "Please select only 1 car.");
        return null;
    }
    if (plate == null || plate.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Selected row has no plate number.");
        return null;
    }
    return plate;
}

private boolean updateCarStatus(String carName, String plateNumber, String status) {
    String sql =
            "UPDATE cars_info SET CAR_STATUS=? "
            + "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) "
            + "AND TRIM(CARS_PLATENUMBER) = TRIM(?)";

    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (status == null || status.trim().isEmpty()) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, status.trim());
            }
            ps.setString(2, carName);
            ps.setString(3, plateNumber);

            int updated = ps.executeUpdate();
            if (updated != 1) {
                conn.rollback();
                JOptionPane.showMessageDialog(
                        this,
                        "Repair status was not saved because the selected car was not uniquely identified."
                );
                return false;
            }

            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }

        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to save car status: " + e.getMessage());
        return false;
    }
}

private String getCarStatusFromDatabase(String carName, String plateNumber) {
    String sql =
            "SELECT CAR_STATUS FROM cars_info "
            + "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) "
            + "AND TRIM(CARS_PLATENUMBER) = TRIM(?) LIMIT 1";

    try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, carName);
        ps.setString(2, plateNumber);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("CAR_STATUS");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to read car repair status: " + e.getMessage());
    }

    return null;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressCircleUI1 = new progress.ProgressCircleUI();
        progress1 = new progress.Progress();
        jPanel1 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel14 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel30 = new javax.swing.JLabel();
        hoverButton2 = new test.HoverButton();
        hoverButton3 = new test.HoverButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        hoverButton4 = new test.HoverButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        jLabel51 = new javax.swing.JLabel();
        hoverButton7 = new test.HoverButton();
        jPanel5 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel9 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel10 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel10 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel12 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel15 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        rSMaterialButtonRectangle16 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle17 = new rojerusan.RSMaterialButtonRectangle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(27, 26, 26));
        jPanel14.setPreferredSize(new java.awt.Dimension(330, 710));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel14.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("   Dashboard");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("    Schedule");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel14.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel14.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("   Customers");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BMW\\icons8-employee-35.png")); // NOI18N
        jPanel14.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 30, 40));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-inventory-35.png")); // NOI18N
        jPanel14.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 40));

        button2.setText("     Inventory");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel14.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 270, 40));

        hoverButton5.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton5.setText("      Rent and Bookings");
        hoverButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton5ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

        hoverButton6.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton6.setText("   Employees");
        hoverButton6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 270, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel14.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 691));

        jPanel5.setBackground(new java.awt.Color(27, 26, 26));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 60));

        jLabel4.setBackground(new java.awt.Color(27, 26, 26));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 230, 30));

        jLabel5.setBackground(new java.awt.Color(27, 26, 26));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, 230, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-username-42.png"))); // NOI18N
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 60));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Inventory");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 7, 290, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 1000, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "#", "CAR NAME", "CAR TYPE", "PLATE NUMBER", "FUEL TYPE", "CAR SEATS", "DAILY RENT", "CAR MODEL YEAR", "CAR RATINGS", "TRANSMISSION TYPE", "CAR STATUS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(11).setMinWidth(400);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(400);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(400);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 2097, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 4265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 190, 970, 425));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TOTAL CARS");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("0");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 43, -1, -1));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("AVAILBLE CARS");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("0");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 43, -1, -1));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RENTED CARS");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("0");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 43, -1, -1));

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("BOOKED CARS");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("0");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 43, -1, -1));

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("IN SERVICE / REPAIR");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("0");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(798, 43, -1, -1));

        jTextField1.setBackground(new java.awt.Color(50, 50, 50));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(null);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 250, 30));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Cars Information");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1, 196, 40));

        rSMaterialButtonRectangle16.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle16.setBorder(null);
        rSMaterialButtonRectangle16.setText("REPAIR");
        rSMaterialButtonRectangle16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle16MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle16ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonRectangle16, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 145, 120, 40));

        rSMaterialButtonRectangle17.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle17.setBorder(null);
        rSMaterialButtonRectangle17.setText("EDIT REPAIR");
        rSMaterialButtonRectangle17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle17MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle17ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonRectangle17, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 145, 120, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 1000, 620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void hoverButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton2ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Dashboard::new);
    }//GEN-LAST:event_hoverButton2ActionPerformed

    private void hoverButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton3ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Schedule::new);
    }//GEN-LAST:event_hoverButton3ActionPerformed

    private void hoverButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton4ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Customers::new);
    }//GEN-LAST:event_hoverButton4ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton5ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, RentANDbookings::new);
    }//GEN-LAST:event_hoverButton5ActionPerformed

    private void hoverButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton6ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, User::new);
    }//GEN-LAST:event_hoverButton6ActionPerformed

    private void hoverButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton7ActionPerformed
        // TODO add your handling code here:
        Login pi = new Login();
        test.FadeTransition.fadeSwitchCentered(this, pi);
    }//GEN-LAST:event_hoverButton7ActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        //searchAndLoadTable();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void rSMaterialButtonRectangle16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle16MouseClicked

    private void rSMaterialButtonRectangle16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle16ActionPerformed

    String plate = getSingleCheckedPlateNumber();
    if (plate == null) return;

    String carName = getSingleCheckedCarName();
    if (carName == null) return;

    if (!REPAIR.parseRepairItems(getCarStatusFromDatabase(carName, plate)).isEmpty()) {
        JOptionPane.showMessageDialog(this, "This car already has repair parts. Use EDIT REPAIR.");
        return;
    }

    // SECURITY: do not allow repair if booked/rented
    if (!CarSecurityGuard.canMarkAsRepair(this, carName, plate)) {
        return;
    }

    REPAIR repairPanel = new REPAIR();

    String[] actions = new String[]{"CLOSE", "SAVE"};

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(repairPanel, "VEHICLE SERVICE/REPAIR", actions, (dialog, index) -> {

            // only SAVE can continue
            if (index != 1) {
                dialog.closePopup();
                return;
            }

            String issues = repairPanel.getSelectedRepairItemsAsOrString();

            if (issues == null || issues.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "SELECT PART TO REPAIR");
                return;
            }

            // SECURITY again before saving
            if (!CarSecurityGuard.canMarkAsRepair(this, carName, plate)) {
                return;
            }

            if (!updateCarStatus(carName, plate, issues)) {
                return;
            }
            refreshInventoryAfterRepairStatusChange();

            dialog.closePopup();
        }),
        option
    );

    }//GEN-LAST:event_rSMaterialButtonRectangle16ActionPerformed

    private void rSMaterialButtonRectangle17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle17MouseClicked

    private void rSMaterialButtonRectangle17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle17ActionPerformed
        String plate = getSingleCheckedPlateNumber();
        if (plate == null) return;

        String carName = getSingleCheckedCarName();
        if (carName == null) return;

        String currentStatus = getCarStatusFromDatabase(carName, plate);
        Set<String> existingParts = REPAIR.parseRepairItems(currentStatus);
        if (existingParts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This car has no saved repair parts to edit.");
            return;
        }

        REPAIR repairPanel = new REPAIR();
        repairPanel.setRepairEditMode(currentStatus);

        if (!repairPanel.hasEditableRepairItems()) {
            JOptionPane.showMessageDialog(this, "This car has no saved repair parts to edit.");
            return;
        }

        String[] actions = new String[]{"CLOSE", "SAVE"};

        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(repairPanel, "EDIT VEHICLE SERVICE/REPAIR", actions, (dialog, index) -> {
                if (index != 1) {
                    dialog.closePopup();
                    return;
                }

                if (!repairPanel.hasRepairedSelection()) {
                    JOptionPane.showMessageDialog(this, "SELECT RED PARTS THAT ARE NOW REPAIRED.");
                    return;
                }

                Set<String> latestParts = REPAIR.parseRepairItems(
                        getCarStatusFromDatabase(carName, plate));
                if (!latestParts.equals(existingParts)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Repair data changed while this window was open. Close it and open EDIT REPAIR again."
                    );
                    return;
                }

                String remainingIssues = repairPanel.getRemainingRepairItemsAsOrString();
                if (!updateCarStatus(carName, plate, remainingIssues)) {
                    return;
                }
                refreshInventoryAfterRepairStatusChange();

                if (remainingIssues == null || remainingIssues.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Repair status cleared. The car is available again.");
                } else {
                    JOptionPane.showMessageDialog(this, "Repaired parts removed. Remaining repair parts stayed red.");
                }

                dialog.closePopup();
            }),
            option
        );
    }//GEN-LAST:event_rSMaterialButtonRectangle17ActionPerformed

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
                new Inventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private test.Button button2;
    private test.HoverButton hoverButton2;
    private test.HoverButton hoverButton3;
    private test.HoverButton hoverButton4;
    private test.HoverButton hoverButton5;
    private test.HoverButton hoverButton6;
    private test.HoverButton hoverButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private progress.Progress progress1;
    private progress.ProgressCircleUI progressCircleUI1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle16;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle17;
    // End of variables declaration//GEN-END:variables
}
