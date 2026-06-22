/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import JEFORE_RENTBOOKED.RENT_BOOKED;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import customer.ServiceCustomer;
import java.awt.BorderLayout;
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
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javaswingdev.chart.ModelChart;
import javax.swing.UIManager;
import customers.DatabaseConnection;
import java.awt.Container;
import java.awt.Rectangle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import popup.AppSession;
import popup.AppStorage;
import popup.BOOKED_RENTED;
import popup.HoverEffect;
import popup.ROUNDPANEL;
import popup.RoundedBorder;
import popup.TABLE_PAIDS;
import popup.TABLE_PAIDS1;
import popup.TABLE_PARTIAL;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import popup.ServiceCars;
import MUL.InventoryMetrics;


/**
 *
 * @author artoc
 */

public class Dashboard extends javax.swing.JFrame {
    /**
     *
     */
    
    private Timer carCountTimer;
    private boolean carCountRefreshRunning;
    private boolean auxiliaryRefreshRunning;
    private int lastAvailValue = -1;
    private int lastAvailMax   = -1;
    private Inventory inv;
    private javax.swing.JComboBox<Integer> salesYearCombo;
    private boolean positionSalesChartOnCurrentMonth = true;
    private boolean duePopupLoading;
    
    
    private java.awt.Color getDefaultColor(javax.swing.JButton btn) {
    return btn.getBackground();
    
    
}

    public Dashboard() {
        initComponents();
        GlassPanePopup.install(this); 
        scroll();  
        loadBookedCount();
        loadRentCount();
        loadReturnedCount();
        loadPendingCount();
        updateDeletedCarsCount();
        new javax.swing.Timer(1000, e -> updateDeletedCarsCount()).start();
        refreshAuxiliaryCountsAsync();        // load immediately
        startDueBadgeTimer(); 
        startRealtimeCarCount();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                refreshDashboard();
            }
        });



     
        configureSalesChartScrolling();
        
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));


        loadPaymentDataToBarChart();  // Load and update data into the bar chart
        loadFullPaymentCount();
        loadPartialPaymentCount();
        
        new HoverEffect(jPanel9);
        new HoverEffect(jPanel15);
        new HoverEffect(jPanel14);
        new HoverEffect(jPanel13);
        new HoverEffect(jPanel16);
        
 
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        progress5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        progress3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        progress6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        badgeButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Get the value from jLabel14
int value = Integer.parseInt(jLabel14.getText().trim());
value = Math.min(value, 100);
progress2.setValue(value);
progress2.setBackground(new Color(0, 255, 51));
progress2.start();  // Only once at startup

// Store last values to avoid repeat
final int[] lastValue2 = {-1};
final int[] lastValue3 = {-1};
final int[] lastValue4 = {-1};
final int[] lastValue5 = {-1};

Timer timerss = new Timer(500, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        refreshAvailableProgress2();
    }
});
timerss.start();


// Timer for progress3 (jLabel21 + jLabel24)
Timer timer2 = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int val1 = Integer.parseInt(jLabel21.getText().trim());
            int val2 = Integer.parseInt(jLabel24.getText().trim());
            int total = val1 + val2;

            total = Math.min(total, 100);  // Clamp to max 100%

            if (total != lastValue3[0]) {
                progress3.setValue(total);
                progress3.setBackground(new Color(255, 153, 0));
                progress3.start();  // Animate only if total changed
                lastValue3[0] = total;
            }
        } catch (NumberFormatException ex) {
            // Optional: ignore or log invalid input
        }
    }
});
timer2.start();


Timer timer3 = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int val = Integer.parseInt(jLabel25.getText().trim());
            val = Math.min(val, 100);  // clamp to max 100

            if (val != lastValue4[0]) {
                progress6.setValue(val);
                progress6.setBackground(new Color(0, 102, 255));  // Blue for FULL
                progress6.start();
                lastValue4[0] = val;
            }
        } catch (NumberFormatException ex) {
            // fallback or ignore
        }
    }
});
timer3.start();


Timer timer4 = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int val = Integer.parseInt(jLabel26.getText().trim());
            val = Math.min(val, 100);  // clamp to max 100

            if (val != lastValue5[0]) {
                progress5.setValue(val);
                progress5.setBackground(new Color(204,0,204)); 
                progress5.start();
                lastValue5[0] = val;
            }
        } catch (NumberFormatException ex) {
            // fallback or ignore
        }
    }
});
timer4.start();

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0, 0));

        // Set up a timer to update the time every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime(); // Update the time label
            }
        });
        timer.start();
        updateDate(); // Initial update of the date   
    }

    private void configureSalesChartScrolling() {
        panelLineChart.removeAll();
        panelLineChart.setLayout(new BorderLayout());

        barChart.addLegend("FULL AMOUNT", new Color(17, 153, 142), new Color(56, 239, 125));
        barChart.addLegend("PARTIAL AMOUNT", new Color(252, 74, 26), new Color(247, 183, 51));
        barChart.addLegend("TOTAL AMOUNT", new Color(0, 159, 255), new Color(236, 47, 75));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        salesYearCombo = new javax.swing.JComboBox<>();
        for (int year = currentYear; year >= currentYear - 10; year--) {
            salesYearCombo.addItem(year);
        }
        salesYearCombo.setSelectedItem(currentYear);
        salesYearCombo.setPreferredSize(new java.awt.Dimension(75, 26));
        barChart.addHeaderComponent(salesYearCombo);

        salesYearCombo.addActionListener(e -> {
            positionSalesChartOnCurrentMonth = false;
            loadPaymentDataToBarChart();
        });

        panelLineChart.add(barChart, BorderLayout.CENTER);
    }

    private void scrollSalesChartToCurrentMonth() {
        if (!positionSalesChartOnCurrentMonth) {
            return;
        }
        positionSalesChartOnCurrentMonth = false;
        barChart.scrollToMonth(Calendar.getInstance().get(Calendar.MONTH));
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

private int getTotalDeletedCars() {
    int total = 0;

    String url = config.AppConfig.getDatabaseUrlWithOptions(
            "useSSL=false&serverTimezone=Asia/Manila");
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword(); // change if you have password

    String sql = "SELECT COUNT(*) FROM cars_deleted";

    try (Connection con = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) total = rs.getInt(1);

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return total;
}

private void updateDeletedCarsCount() {
    refreshAuxiliaryCountsAsync();
}

private void showDueAccountsPopupAsync() {
    if (duePopupLoading) {
        return;
    }

    duePopupLoading = true;
    badgeButton1.setEnabled(false);
    Cursor oldCursor = getCursor();
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    new SwingWorker<TABLE_PAIDS1, Void>() {
        @Override
        protected TABLE_PAIDS1 doInBackground() {
            return new TABLE_PAIDS1();
        }

        @Override
        protected void done() {
            try {
                TABLE_PAIDS1 sched = get();
                String actions[] = new String[]{"NEW CUSTOMER", "CLOSE"};
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
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Dashboard.this,
                        "Unable to open notification details:\n" + ex.getMessage(),
                        "Loading Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                setCursor(oldCursor);
                badgeButton1.setEnabled(true);
                duePopupLoading = false;
            }
        }
    }.execute();
}


private void restoreDefault(javax.swing.JButton btn, java.awt.Color defaultColor) {
    btn.setOpaque(true);
    btn.setContentAreaFilled(true);
    btn.setBorderPainted(true);
    btn.setBackground(defaultColor);
}

    private void loadFullPaymentCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        int fullPaymentCount = service.countFullPayments();
        jLabel25.setText(String.valueOf(fullPaymentCount));
    } catch (SQLException ex) {
        ex.printStackTrace();
        jLabel25.setText("0");  // fallback value
    }
}
    
    private void loadPartialPaymentCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        int partialPaymentCount = service.countPartialPayments();
        jLabel26.setText(String.valueOf(partialPaymentCount));
    } catch (SQLException ex) {
        ex.printStackTrace();
        jLabel26.setText("0");  // fallback value
    }
}
    
public void loadPendingCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();

        int bookedCount = service.countCustomersByStatus("BOOKED");
        int rentedCount = service.countCustomersByStatus("RENTED");
        int deletedCount = getTotalDeletedCars();

        // Base cars = 96
        // Available = 96 - booked - rented - deleted
        int pendingCount = Math.max(0, 96 - (bookedCount + rentedCount + deletedCount));

        jLabel14.setText(String.valueOf(pendingCount));

    } catch (Exception e) {
        e.printStackTrace();
        jLabel14.setText("96");
    }
}
    

public void loadPaymentDataToBarChart() {
    barChart.clear();
    barChart.setTitle("SALES STATUS‎‎ ‎  ");

    barChart.setSeriesSize(8);       // Width of each bar
    barChart.setMonthSpacing(1);    // Space between months (requires setMonthSpacing support in BarChart)

    try {
        ServiceCustomer service = new ServiceCustomer();
        int selectedYear = (Integer) salesYearCombo.getSelectedItem();
        Map<String, double[]> data = service.getMonthlyPaymentSummary(selectedYear);  // returns [PARTIAL, FULL]

        for (Map.Entry<String, double[]> entry : data.entrySet()) {
            String month = entry.getKey();              // e.g., "July"
            double[] totals = entry.getValue();         // [PARTIAL, FULL]
            double full = totals[1];
            double partial = totals[0];
            double total = full + partial;

            // Match order of legends: [FULL, PARTIAL, TOTAL]
            barChart.addData(new ModelChart(month, new double[]{full, partial, total}));
        }

        barChart.blankPlotChart.setMaxValues(100000);  // Adjust to max Y value needed
        barChart.start();
        scrollSalesChartToCurrentMonth();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading bar chart: " + e.getMessage());
    }

    barChart.revalidate();
    barChart.repaint();
    panelLineChart.revalidate();
    panelLineChart.repaint();
}


  public void loadBookedCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        int bookedCount = service.countCustomersByStatus("BOOKED");
        jLabel21.setText(String.valueOf(bookedCount));
    } catch (Exception e) {
        e.printStackTrace();
        jLabel21.setText("0"); // fallback
    }
}
  
  public void loadRentCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        int rentCount = service.countCustomersByStatus("RENTED");
        jLabel24.setText(String.valueOf(rentCount));
    } catch (Exception e) {
        e.printStackTrace();
        jLabel24.setText("0");
    }
}
  
    public void loadReturnedCount() {
    try {
        ServiceCustomer service = new ServiceCustomer();
            int returnedCount = service.countCustomersByStatus("RETURNED");
        jLabel1.setText(String.valueOf(returnedCount));
    } catch (Exception e) {
        e.printStackTrace();
        jLabel1.setText("0");
    }
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


    public void done() {
    javax.swing.Timer timers = new javax.swing.Timer(200, e -> {
 
        barChart.start();
    });
    timers.setRepeats(false);
    timers.start();
}
 
    private void scroll(){

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                 + "trackArc:999;"
                 + "trackInsets:3,3,3,3;"
                 + "thumbInsets:3,3,3,3;"
                 + "background:$Table.bsckgound;");
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

private void startDueBadgeTimer() {
    new javax.swing.Timer(60_000, e -> refreshAuxiliaryCountsAsync()).start(); // every 1 minute
}

private void updateDueBadge() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        int dueCount = service.countDueAfterOneHour();

        if (dueCount <= 0) {
            badgeButton1.setText("");      // ✅ hides the badge (icon only)
            badgeButton1.setToolTipText("No due accounts");
        } else {
            // Optional: show 99+ if huge
            badgeButton1.setText(dueCount > 99 ? "99+" : String.valueOf(dueCount));
            badgeButton1.setToolTipText("Due accounts: " + dueCount);
        }

        badgeButton1.repaint();

    } catch (Exception e) {
        // If DB error, just hide badge so UI stays clean
        badgeButton1.setText("");
        badgeButton1.repaint();
        e.printStackTrace();
    }
}

private void refreshAuxiliaryCountsAsync() {
    if (auxiliaryRefreshRunning) return;
    auxiliaryRefreshRunning = true;

    new SwingWorker<int[], Void>() {
        @Override
        protected int[] doInBackground() throws Exception {
            ServiceCustomer service = new ServiceCustomer();
            return new int[]{getTotalDeletedCars(), service.countDueAfterOneHour()};
        }

        @Override
        protected void done() {
            try {
                int[] counts = get();
                MUL.UiCountAnimator.animate(jLabel8, counts[0], 350);
                int dueCount = counts[1];
                badgeButton1.setText(dueCount <= 0 ? "" : (dueCount > 99 ? "99+" : String.valueOf(dueCount)));
                badgeButton1.setToolTipText(dueCount <= 0 ? "No due accounts" : "Due accounts: " + dueCount);
                badgeButton1.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                auxiliaryRefreshRunning = false;
            }
        }
    }.execute();
}

private void startRealtimeCarCount() {
    carCountTimer = new Timer(1000, e -> refreshCarCountAsync()); // every 1 second
    carCountTimer.setInitialDelay(0);
    carCountTimer.start();
}

private void refreshCarCountAsync() {
    if (carCountRefreshRunning) return;
    carCountRefreshRunning = true;

    new SwingWorker<InventoryMetrics.Snapshot, Void>() {
        @Override
        protected InventoryMetrics.Snapshot doInBackground() {
            try {
                return InventoryMetrics.load();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void done() {
            try {
                InventoryMetrics.Snapshot snapshot = get();
                if (snapshot == null) return;

                MUL.UiCountAnimator.animate(jLabel14, snapshot.available, 350);
                MUL.UiCountAnimator.animate(jLabel21, snapshot.booked, 350);
                MUL.UiCountAnimator.animate(jLabel24, snapshot.rented, 350);
                MUL.UiCountAnimator.animate(jLabel11, snapshot.added, 350);
                progress2.setMaximum(Math.max(1, snapshot.total));
                progress2.setValue(snapshot.available);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                carCountRefreshRunning = false;
            }
        }
    }.execute();
}

// ADMIN/Dashboard.java
public void refreshDashboard() {
    // refresh counters
    loadBookedCount();
    loadRentCount();
    loadReturnedCount();
    loadPendingCount();

    // refresh payments + chart
    loadFullPaymentCount();
    loadPartialPaymentCount();

    // refresh other UI parts you already have
    updateDeletedCarsCount();
    refreshAuxiliaryCountsAsync();
    refreshCarCountAsync();
    
    revalidate();
    repaint();
}


// Dashboard.java


private int toInt(javax.swing.JLabel lbl) {
    try { return Integer.parseInt(lbl.getText().trim()); }
    catch (Exception e) { return 0; }
}

private void refreshAvailableProgress2() {
    int available = Math.max(0, toInt(jLabel14));

    if (available != lastAvailValue) {
        progress2.setValue(Math.min(available, progress2.getMaximum()));
        progress2.start();
        lastAvailValue = available;
    }
}
    /**
     * @param args the command line arguments
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressCircleUI1 = new progress.ProgressCircleUI();
        jPanel1 =  new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel5 = new ROUNDPANEL(25, new Color(0, 204, 204), 2);
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        panelLineChart = new ROUNDPANEL(10, new Color(0, 0, 0), 0);
        barChart = new javaswingdev.chart.BarChart();
        panelBarChart = new ROUNDPANEL(10, new Color(0, 0, 0), 0);
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        progress3 = new progress.Progress();
        progress2 = new progress.Progress();
        panelBarChart1 = new ROUNDPANEL(10, new Color(0, 0, 0), 0);
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        progress5 = new progress.Progress();
        progress6 = new progress.Progress();
        jPanel6 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel6 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel12 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel16 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton2 = new test.HoverButton();
        hoverButton3 = new test.HoverButton();
        hoverButton4 = new test.HoverButton();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        hoverButton7 = new test.HoverButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new ROUNDPANEL(25, new Color(0, 204, 204), 2);
        badgeButton1 = new raven.notibutton.BadgeButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(27, 26, 26));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-username-42.png"))); // NOI18N
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 60));

        jLabel4.setBackground(new java.awt.Color(27, 26, 26));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 230, 30));

        jLabel5.setBackground(new java.awt.Color(27, 26, 26));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 230, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Hello! Admin");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 7, 290, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 890, 60));

        jPanel11.setBackground(new java.awt.Color(27, 26, 26));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLineChart.setBackground(new java.awt.Color(51, 51, 51));
        panelLineChart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelLineChart.add(barChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 410));

        jPanel11.add(panelLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 490, 430));

        panelBarChart.setBackground(new java.awt.Color(51, 51, 51));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("AVAILABLE");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("BOOKED/RENTED");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("VEHICLE STATUS");

        progress3.setMaximum(96);
        progress3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progress3MouseClicked(evt);
            }
        });

        progress2.setMaximum(96);

        javax.swing.GroupLayout panelBarChartLayout = new javax.swing.GroupLayout(panelBarChart);
        panelBarChart.setLayout(panelBarChartLayout);
        panelBarChartLayout.setHorizontalGroup(
            panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarChartLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
            .addGroup(panelBarChartLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progress2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progress3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        panelBarChartLayout.setVerticalGroup(
            panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarChartLayout.createSequentialGroup()
                .addComponent(jLabel18)
                .addGap(12, 12, 12)
                .addGroup(panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progress3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progress2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel11.add(panelBarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 210));

        panelBarChart1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("PAID ACCOUNTS");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("PARTIAL PAY ACCOUNT");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("ACCOUNT STATUS");

        progress5.setMaximum(96);
        progress5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progress5MouseClicked(evt);
            }
        });

        progress6.setMaximum(96);
        progress6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progress6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBarChart1Layout = new javax.swing.GroupLayout(panelBarChart1);
        panelBarChart1.setLayout(panelBarChart1Layout);
        panelBarChart1Layout.setHorizontalGroup(
            panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarChart1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBarChart1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progress6, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progress5, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        panelBarChart1Layout.setVerticalGroup(
            panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarChart1Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addGap(12, 12, 12)
                .addGroup(panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progress5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progress6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBarChart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel11.add(panelBarChart1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 480, 210));

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 1000, 451));

        jPanel6.setBackground(new java.awt.Color(27, 26, 26));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(27, 26, 26));
        jScrollPane1.setBorder(null);

        jPanel8.setBackground(new java.awt.Color(26, 27, 27));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("BOOKINGS");

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
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Status");

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ON RENT");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("0");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RETURNED CARS");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("0");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DELETED CARS");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(51, 51, 51));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("NEW CARS ADDED");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("0");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        jScrollPane1.setViewportView(jPanel8);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 980, 140));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 1000, 160));

        jPanel2.setBackground(new java.awt.Color(27, 26, 26));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 670, 30, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel2.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-inventory-35.png")); // NOI18N
        jPanel2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 40));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BMW\\icons8-employee-35.png")); // NOI18N
        jPanel2.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 30, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        button2.setText("   Dashboard");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel2.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("    Schedule");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("   Customers");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("      Rent and Bookings");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

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
        hoverButton6.setText("    Inventory");
        hoverButton6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 270, 40));

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 691));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("0");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 187, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("0");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 187, 39));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("96");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 182, -1, 50));

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        badgeButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-notification-35.png"))); // NOI18N
        badgeButton1.setText("0");
        badgeButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                badgeButton1MouseClicked(evt);
            }
        });
        badgeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                badgeButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(badgeButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(10, Short.MAX_VALUE)
                    .addComponent(badgeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 10, -1, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 0, 1360, 712));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void progress6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progress4MouseClicked
        // TODO add your handling code here:
        TABLE_PAIDS sched = new TABLE_PAIDS();

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
    }//GEN-LAST:event_progress4MouseClicked

    private void progress5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progress5MouseClicked
        // TODO add your handling code here:
         TABLE_PARTIAL sched = new TABLE_PARTIAL();

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
    }//GEN-LAST:event_progress5MouseClicked

    private void progress3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progress3MouseClicked
        // TODO add your handling code here:
    BOOKED_RENTED sched = new BOOKED_RENTED();

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
    }//GEN-LAST:event_progress3MouseClicked

    private void progress4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progress6MouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_progress6MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked

    private void badgeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_badgeButton1ActionPerformed
        // TODO add your handling code here:
        showDueAccountsPopupAsync();
    }//GEN-LAST:event_badgeButton1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Dashboard::new);
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton2ActionPerformed
        // TODO add your handling code here:
    FrameLoadHelper.fadeSwitchAsync(this, Schedule::new);
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

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void badgeButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_badgeButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_badgeButton1MouseClicked

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
            new Dashboard().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.notibutton.BadgeButton badgeButton1;
    private javaswingdev.chart.BarChart barChart;
    private test.Button button2;
    private test.HoverButton hoverButton2;
    private test.HoverButton hoverButton3;
    private test.HoverButton hoverButton4;
    private test.HoverButton hoverButton5;
    private test.HoverButton hoverButton6;
    private test.HoverButton hoverButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelBarChart;
    private javax.swing.JPanel panelBarChart1;
    private javax.swing.JPanel panelLineChart;
    private progress.Progress progress2;
    private progress.Progress progress3;
    private progress.Progress progress5;
    private progress.Progress progress6;
    private progress.ProgressCircleUI progressCircleUI1;
    // End of variables declaration//GEN-END:variables
}
