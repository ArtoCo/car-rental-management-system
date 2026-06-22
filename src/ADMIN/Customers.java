/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import java.util.List;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import customer.ServiceCustomer;
import customers.CheckBoxTableHeaderRenderer;
import customers.ModelCustomer1;
import customers.TableHeaderAlignment;
import employees.ModelEmployee;
import employees.ServiceEmployee;
import jSCHED.ModelSched;
import jSCHED.ServiceSched;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import popup.CUSTOMER_EDIT;
import popup.Create;
import popup.PanelSlide;
import popup.ProfileTableRenderer;
import popup.ROUNDPANEL;
import raven.modal.Toast;

import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;

/**
 * 
 * @author ArtoCO 
 */
public class Customers extends javax.swing.JFrame {
    

    private ServiceCustomer service = new ServiceCustomer();
 
    private Map<Integer, ModelEmployee> employeeMap = new HashMap<>();
    public static Customers instance;
    List<Integer> customerIdsToEdit = new ArrayList<>();
    private SwingWorker<List<ModelCustomer1>, Void> customerLoadWorker;
    private SwingWorker<List<ModelCustomer1>, Void> customerSearchWorker;
    private Timer customerSearchTimer;



    /**
     * Creates new form Customers
     */
    public Customers() {
        initComponents();
        init();
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                loadbookedrented();
            }
        });
        
                
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

       

    jTextField1.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        searchTable();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        searchTable();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        searchTable();
    }

    private void searchTable() {
        scheduleCustomerSearch();
    }
});
      
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
}
    private void init() {
    GlassPanePopup.install(this);
    Notifications.getInstance().setJFrame(this);
    loadbookedrented();

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
    jScrollPane4.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
            + "trackArc:999;"
            + "trackInsets:3,3,3,3;"
            + "thumbInsets:3,3,3,3;"
            + "background:$Table.backgound;");

    // Label font style
    jLabel12.putClientProperty(FlatClientProperties.STYLE, "font:bold +5;");

    // Search placeholder
    jTextField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
}


    
    // Connection to the database in MySQL
    public class DatabaseConnection {
        
    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = config.AppConfig.getDatabaseUrl(); // Ensure this URL is correct
            String user = config.AppConfig.getDatabaseUser(); // Ensure this username is correct
            String password = config.AppConfig.getDatabasePassword(); // Ensure this password is correct

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
    


    // Custom button class with gradient
    public class CustomButton extends JButton {
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
                Color color1 = Color.decode("#dd1818"); // Red color
                Color color2 = Color.decode("#333333"); // Dark gray color
                GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
                g2.setPaint(gradient);
            } else {
                g2.setColor(getBackground());
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
            super.paintComponent(g2);
            g2.dispose();
        }
    }  
    

private void loadbookedrented() {
    if (customerLoadWorker != null && !customerLoadWorker.isDone()) {
        customerLoadWorker.cancel(true);
    }

    customerLoadWorker = new SwingWorker<List<ModelCustomer1>, Void>() {
        @Override
        protected List<ModelCustomer1> doInBackground() throws Exception {
            return service.getbookedrented();
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                return;
            }
            try {
                populateBookedRentedTable(get());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Customers.this, "Error loading FULL PAYMENT data:\n" + ex.getMessage());
            }
        }
    };
    customerLoadWorker.execute();
}

private void populateBookedRentedTable(List<ModelCustomer1> list) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // clear old rows

    SimpleDateFormat sdf = new SimpleDateFormat("MMMM-d-yyyy"); // JULY/7/2025 format

    for (ModelCustomer1 c : list) {
        Object[] row = new Object[]{
            false, // checkbox default
            c.getCustomerId(),
            c.getFirstname1(),
            c.getLastname1(),
            c.getAge1(),
            c.getPhoneNumber1(),
            c.getPrimaryAddress1(),
            c.getTransactionDate().toString(),  // Format for display
            c.getPaymentType(),
            c.getTotalPayment(), // CARS NAME
            sdf.format(c.geInDate()),
            c.getPickup_time(),
            c.getInLocation(),
            sdf.format(c.getOutDate()),
            c.getReturned_time(),
            c.getOutLocation(),
            c.getCarType(),
            c.getCarName(),
            c.getStatus()
        };
        model.addRow(row);
    }
}

private void scheduleCustomerSearch() {
    if (customerSearchTimer == null) {
        customerSearchTimer = new Timer(300, e -> searchCustomersAsync());
        customerSearchTimer.setRepeats(false);
    }
    customerSearchTimer.restart();
}

private void searchCustomersAsync() {
    final String keyword = jTextField1.getText().trim();

    if (customerSearchWorker != null && !customerSearchWorker.isDone()) {
        customerSearchWorker.cancel(true);
    }

    customerSearchWorker = new SwingWorker<List<ModelCustomer1>, Void>() {
        @Override
        protected List<ModelCustomer1> doInBackground() throws Exception {
            return new ServiceCustomer().search(keyword);
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                return;
            }
            try {
                populateSearchTable(get());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Customers.this, "Error searching customer data:\n" + ex.getMessage());
            }
        }
    };
    customerSearchWorker.execute();
}

private void populateSearchTable(List<ModelCustomer1> filtered) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    int rowNum = 1;
    for (ModelCustomer1 data : filtered) {
        model.addRow(data.toTableRow(rowNum++));
    }
}


private void addToTable(ModelCustomer1 customer, int rowNum) {  
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    SimpleDateFormat sdf = new SimpleDateFormat("MMMM-d-yyyy");

    model.addRow(new Object[]{
        false,
        customer.getCustomerId(),  // ✅ this should now be the correct ID from DB
        customer.getFirstname1(),
        customer.getLastname1(),
        customer.getAge1(),
        customer.getPhoneNumber1(),
        customer.getPrimaryAddress1(),
        customer.getTransactionDate().toString(),
        customer.getPaymentType(),
        customer.getTotalPayment(),
        sdf.format(customer.geInDate()),
        customer.getPickup_time(),
        customer.getInLocation(),
        sdf.format(customer.getOutDate()),
        customer.getReturned_time(),
        customer.getOutLocation(),
        customer.getCarType(),
        customer.getCarName(),
        customer.getStatus()

    });
}

private String normalizeCustomerStatus(String status) {
    return status == null ? "" : status.trim().toUpperCase();
}

private boolean isChangingToCancelled(ModelCustomer1 oldData, ModelCustomer1 newData) {
    return !"CANCELLED".equals(normalizeCustomerStatus(oldData.getStatus()))
            && "CANCELLED".equals(normalizeCustomerStatus(newData.getStatus()));
}

private boolean handleBookingCancellationReason(ModelCustomer1 oldData, ModelCustomer1 newData) {
    if (!isChangingToCancelled(oldData, newData)) {
        return true;
    }

    String oldStatus = normalizeCustomerStatus(oldData.getStatus());
    if ("RENTED".equals(oldStatus)) {
        JOptionPane.showMessageDialog(this,
                "The car is rented.\nCannot cancel.",
                "Cancellation Not Allowed",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    if (!"BOOKED".equals(oldStatus)) {
        JOptionPane.showMessageDialog(this,
                "Only booked customers can be cancelled.",
                "Cancellation Not Allowed",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    String[] cancellationReasons = new String[]{
        "Change of travel plans",
        "Found another vehicle/service",
        "Wrong booking date selected",
        "Wrong pickup or return location",
        "Customer no longer needs the car",
        "Customer requested to cancel",
        "Payment not completed",
        "Customer did not respond",
        "Emergency or personal reason",
        "Duplicate booking",
        "Booking made by mistake",
        "Schedule conflict",
        "OTHERS"
    };

    Object selectedReason = JOptionPane.showInputDialog(
            this,
            "Select cancellation reason:",
            "Booking Cancellation",
            JOptionPane.QUESTION_MESSAGE,
            null,
            cancellationReasons,
            cancellationReasons[0]);

    if (selectedReason == null) {
        return false;
    }

    String reason = selectedReason.toString().trim();
    if ("OTHERS".equalsIgnoreCase(reason)) {
        reason = JOptionPane.showInputDialog(
                this,
                "Enter cancellation reason:",
                "Other Cancellation Reason",
                JOptionPane.QUESTION_MESSAGE);

        if (reason == null) {
            return false;
        }

        reason = reason.trim();
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cancellation reason is required.",
                    "Reason Required",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    String existingConditions = newData.getconditions() == null
            ? ""
            : newData.getconditions().trim();
    String cancellationNote = "Cancellation reason: " + reason;
    newData.setconditions(existingConditions.isEmpty()
            ? cancellationNote
            : existingConditions + " | " + cancellationNote);

    return true;
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel2 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        hoverButton2 = new test.HoverButton();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton3 = new test.HoverButton();
        hoverButton4 = new test.HoverButton();
        jLabel49 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        jLabel50 = new javax.swing.JLabel();
        hoverButton7 = new test.HoverButton();
        jLabel51 = new javax.swing.JLabel();
        jPanel3 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel18 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        rSMaterialButtonRectangle14 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle15 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle16 = new rojerusan.RSMaterialButtonRectangle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1360, 712));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(1350, 701));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(27, 26, 26));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 670, 30, 40));

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("   Dashboard");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        button2.setText("   Customers");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel2.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("    Schedule");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("      Rent and Bookings");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BMW\\icons8-employee-35.png")); // NOI18N
        jPanel2.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 30, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        hoverButton5.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton5.setText("   Employees");
        hoverButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 270, 40));

        hoverButton6.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton6.setText("     Inventory");
        hoverButton6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 270, 40));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-inventory-35.png")); // NOI18N
        jPanel2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 40));

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel2.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 10, 330, 691));

        jPanel3.setBackground(new java.awt.Color(27, 26, 26));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("CUSTOMERS SUMMARY PROFILE AND INVOICES");

        jTextField1.setBackground(new java.awt.Color(27, 26, 26));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Search Customer");

        jPanel4.setBackground(new java.awt.Color(27, 26, 26));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECT", "#", "FIRST NAME", "LAST NAME", "AGE", "PHONE NUMBER", "PRIMARY ADDRESS", "TRANSACTION DATE", "PAYMENT TYPE", "TOTAL PAYMENT", "PICKUP DATE", "PICKUP TIME", "PICKUP LOCATION", "RETURNED DATE", "RETURNED TIME", "RETURNED LOCATION", "VEHICLE TYPE", "VEHICLE NAME", "STATUS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(250);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(250);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(12).setPreferredWidth(650);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(650);
            jTable1.getColumnModel().getColumn(13).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(14).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(15).setPreferredWidth(650);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(650);
            jTable1.getColumnModel().getColumn(16).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(16).setMaxWidth(130);
            jTable1.getColumnModel().getColumn(17).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(17).setMaxWidth(130);
            jTable1.getColumnModel().getColumn(18).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(18).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 3400, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel4);

        rSMaterialButtonRectangle14.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle14.setBorder(null);
        rSMaterialButtonRectangle14.setText("EDIT");
        rSMaterialButtonRectangle14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle14MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle14ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle15.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle15.setBorder(null);
        rSMaterialButtonRectangle15.setText("DELETE");
        rSMaterialButtonRectangle15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle15MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle15ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle16.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle16.setBorder(null);
        rSMaterialButtonRectangle16.setText("RESTORE");
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(0, 650, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rSMaterialButtonRectangle14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rSMaterialButtonRectangle15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rSMaterialButtonRectangle16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rSMaterialButtonRectangle14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rSMaterialButtonRectangle15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rSMaterialButtonRectangle16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 10, 987, 690));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed

    
    }//GEN-LAST:event_buttonAction4ActionPerformed

    private void buttonAction3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction3ActionPerformed
        // DELETE LIST THEN UPDATE
    // DELETE LIST THEN UPDATE
    
    }//GEN-LAST:event_buttonAction3ActionPerformed
    
    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed

       
    }//GEN-LAST:event_buttonAction2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1KeyReleased

    private void rSMaterialButtonRectangle14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle14MouseClicked

    private void rSMaterialButtonRectangle14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14ActionPerformed
        // TODO add your handling code here:
        //EDIT                                                          
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    ServiceCustomer service = new ServiceCustomer();

    List<Integer> selectedRows = new ArrayList<>();
    List<Integer> customerIdsToEdit = new ArrayList<>();

    // collect checked rows using customer_id
    for (int i = 0; i < model.getRowCount(); i++) {
        Boolean checked = (Boolean) model.getValueAt(i, 0);
        if (checked != null && checked) {
            selectedRows.add(i);
            customerIdsToEdit.add(((Number) model.getValueAt(i, 1)).intValue());
        }
    }

    if (selectedRows.isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a row to edit");
        return;
    }

    if (selectedRows.size() > 1) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select only one row to edit");
        return;
    }

    try {
        int customerId = customerIdsToEdit.get(0);
        ModelCustomer1 data = service.findById(customerId);

        if (data == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Customer not found");
            return;
        }

        CUSTOMER_EDIT editForm = new CUSTOMER_EDIT();
        editForm.setEditData(data);

        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        String status = data.getStatus() == null ? "" : data.getStatus().trim();
        boolean showRent = status.equalsIgnoreCase("RETURNED");

        String[] actions = showRent
                ? new String[]{"Rent", "Cancel", "Update"}
                : new String[]{"Cancel", "Update"};

        GlassPanePopup.showPopup(
            new SimplePopupBorder(
                editForm,
                "Edit Customer [" + data.getFirstname1() + "]",
                actions,
                (dialog, index) -> {

                    // FIX: popup can return -1 when closed/dismissed
                    if (index < 0 || index >= actions.length) {
                        dialog.closePopup();
                        return;
                    }

                    String action = actions[index];

                    switch (action) {
                        case "Rent":
                            try {
                                // DO NOT SAVE anything here
                                String transmissionType = editForm.getTransmissionType();

                                dialog.closePopup();

                                RentANDbookings pi = new RentANDbookings();
                                pi.setRequiredTransmissionType(transmissionType);

                                // optional: pass customer too if you need later
                                // pi.setSelectedCustomer(data);

                                test.FadeTransition.fadeSwitch(this, pi);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Notifications.getInstance().show(
                                    Notifications.Type.ERROR,
                                    "Failed to open Rent and Bookings"
                                );
                            }
                            break;

                        case "Cancel":
                            dialog.closePopup();
                            break;

                        case "Update":
                            try {
                                if (!editForm.validateBeforeSave()) {
                                    return;
                                }
                                ModelCustomer1 updated = editForm.getData();
                                updated.setCustomerId(data.getCustomerId());

                                if (!handleBookingCancellationReason(data, updated)) {
                                    return;
                                }

                                service.edit(updated);
                                editForm.commitMapSelectionsAfterUpdate();

                                service.updateLicenseImages(
                                    updated.getCustomerId(),
                                    editForm.getLicenseFrontBytes(),
                                    editForm.getLicenseBackBytes(),
                                    editForm.isDeleteFront(),
                                    editForm.isDeleteBack()
                                );

                                editForm.reloadLicenseImages();

                                Notifications.getInstance().show(
                                    Notifications.Type.SUCCESS,
                                    "Customer updated"
                                );

                                dialog.closePopup();
                                loadbookedrented();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Notifications.getInstance().show(
                                    Notifications.Type.ERROR,
                                    "Update failed"
                                );
                            }
                            break;
                    }
                }
            ),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Failed to open edit form");
    }


    }//GEN-LAST:event_rSMaterialButtonRectangle14ActionPerformed

    private void rSMaterialButtonRectangle15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle15MouseClicked

    private void rSMaterialButtonRectangle15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle15ActionPerformed
        // TODO add your handling code here:
        //DELETE
        
    int deletedCount = 0;
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    List<Integer> rowsToDelete = new ArrayList<>();
    List<Integer> customerIdsToDelete = new ArrayList<>();

    // Step 1: Collect all checked rows
    for (int i = 0; i < model.getRowCount(); i++) {
        boolean checked = Boolean.TRUE.equals(model.getValueAt(i, 0));
        if (checked) {
            rowsToDelete.add(i);
            customerIdsToDelete.add(((Number) model.getValueAt(i, 1)).intValue());
        }
    }

    // Step 2: Show confirmation once
    if (!rowsToDelete.isEmpty()) {
        for (Integer customerId : customerIdsToDelete) {
            try {
                String blockedStatus = service.getCustomerDeletionBlockedStatus(customerId);
                if (blockedStatus != null) {
                    JOptionPane.showMessageDialog(this,
                            "Customer cannot be deleted while status is " + blockedStatus
                            + ".\nOnly RETURNED or CANCELLED customers can be deleted.",
                            "Delete Not Allowed",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Customer status could not be verified. Delete was blocked.\n" + ex.getMessage(),
                        "Delete Check Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int option = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete selected item(s)?", "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Step 3: Delete in reverse order to avoid index shifting
            for (int i = rowsToDelete.size() - 1; i >= 0; i--) {
                int rowIndex = rowsToDelete.get(i);
                int customerId = customerIdsToDelete.get(i);
                try {
                    service.archiveAndDeleteByCustomerId(customerId);
                    model.removeRow(rowIndex);
                    deletedCount++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            Notifications.getInstance().show(
                Notifications.Type.SUCCESS, "Deleted " + deletedCount + " item(s)");
        }
    } else {
        Notifications.getInstance().show(
            Notifications.Type.WARNING, "Please select to delete");
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle15ActionPerformed

    private void rSMaterialButtonRectangle16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle16MouseClicked

    private void rSMaterialButtonRectangle16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle16ActionPerformed
    try {
        List<ModelCustomer1> deletedCustomers = service.getDeletedCustomers();
        if (deletedCustomers.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "No item to restore");
            return;
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ModelCustomer1 customer : deletedCustomers) {
            listModel.addElement(
                    customer.getCustomerId() + " | "
                    + customer.getFirstname1() + " " + customer.getLastname1()
                    + " | " + customer.getPhoneNumber1()
                    + " | " + customer.getplate_number()
                    + " | " + customer.getStatus()
            );
        }

        JList<String> restoreList = new JList<>(listModel);
        restoreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        restoreList.setVisibleRowCount(Math.min(10, listModel.size()));

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(restoreList),
                "Select customer(s) to restore",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        int[] selectedIndexes = restoreList.getSelectedIndices();
        if (selectedIndexes.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select customer(s) to restore");
            return;
        }

        List<Integer> selectedIds = new ArrayList<>();
        for (int index : selectedIndexes) {
            selectedIds.add(deletedCustomers.get(index).getCustomerId());
        }

        int restoredCount = service.restoreDeletedCustomersByIds(selectedIds);
        loadbookedrented();

        if (restoredCount > 0) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Restored " + restoredCount + " item(s)");
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "No item to restore");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Restore failed: " + ex.getMessage());
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle16ActionPerformed

    private void hoverButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton2ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Dashboard::new);
    }//GEN-LAST:event_hoverButton2ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton3ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Schedule::new);
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
            new Customers().setVisible(true);
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle14;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle15;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle16;
    // End of variables declaration//GEN-END:variables
}
