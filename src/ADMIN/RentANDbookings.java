/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import CAR_BODY_TYPE.STATIONWAGON;
import CAR_BODY_TYPE.PICKUP;
import CAR_BODY_TYPE.MPV;
import CAR_BODY_TYPE.MINIVAN;
import CAR_BODY_TYPE.CONVERTIBLES;
import CAR_BODY_TYPE.COUPE;
import CAR_BODY_TYPE.CROSSOVER;
import CAR_BODY_TYPE.HATCHBACK;
import CAR_BODY_TYPE.LUXURY;
import CAR_BODY_TYPE.SEDAN;
import CAR_BODY_TYPE.SUV;
import CAR_BODY_TYPE.VAN;
import CAR_BRANDS.CHEVROLET;
import CAR_BRANDS.FORD;
import CAR_BRANDS.HONDA;
import CAR_BRANDS.HYUNDAI;
import CAR_BRANDS.BMW;
import CAR_BRANDS.MERCEDES;
import CAR_BRANDS.KIA;
import CAR_BRANDS.MAZDA;
import CAR_BRANDS.MITSUBISHI;
import CAR_BRANDS.NISSAN;
import CAR_BRANDS.SUZUKI;
import CAR_BRANDS.TOYOTA;
import JEFORE_RENTBOOKED.RENT_BOOKED;
import JEFORE_RENTBOOKED.RENT_BOOKED1;
import JEFORE_RENTBOOKED.RENT_BOOKED10;
import JEFORE_RENTBOOKED.RENT_BOOKED11;
import JEFORE_RENTBOOKED.RENT_BOOKED2;
import JEFORE_RENTBOOKED.RENT_BOOKED3;
import JEFORE_RENTBOOKED.RENT_BOOKED4;
import JEFORE_RENTBOOKED.RENT_BOOKED5;
import JEFORE_RENTBOOKED.RENT_BOOKED6;
import JEFORE_RENTBOOKED.RENT_BOOKED7;
import JEFORE_RENTBOOKED.RENT_BOOKED8;
import JEFORE_RENTBOOKED.RENT_BOOKED9;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import popup.HoverEffect;
import popup.ROUNDPANEL;
import popup.TABLE_PAIDS1;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import zEFFECT.ROUNDLABEL;

/**
 *
 * @author artoc
 */
public class RentANDbookings extends javax.swing.JFrame {
    private static boolean activeRentalAgentMode;
    private final boolean rentalAgentMode;

    public static boolean isRentalAgentModeActive() {
        return activeRentalAgentMode;
    }
   
    /**
     * Creates new form bookings
     */
    public RentANDbookings() {
        this(false);
    }

    public RentANDbookings(boolean rentalAgentMode) {
        this.rentalAgentMode = rentalAgentMode;
        activeRentalAgentMode = rentalAgentMode;
        initComponents();
        
        GlassPanePopup.install(this);
        
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

 
        UIManager.put("ComboBox.selectionBackground", Color.CYAN);
     
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (rentalAgentMode) {
            applyRentalAgentNavigation();
        }
    }

    private void applyRentalAgentNavigation() {
        hoverButton2.setVisible(false);
        hoverButton3.setVisible(false);
        hoverButton4.setVisible(false);
        hoverButton5.setVisible(false);
        hoverButton6.setVisible(false);
        jLabel38.setVisible(false);
        jLabel35.setVisible(false);
        jLabel36.setVisible(false);
        jLabel49.setVisible(false);
        jLabel50.setVisible(false);

        org.netbeans.lib.awtextra.AbsoluteLayout sidebarLayout
                = (org.netbeans.lib.awtextra.AbsoluteLayout) jPanel5.getLayout();
        sidebarLayout.addLayoutComponent(
                button2,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40)
        );
        sidebarLayout.addLayoutComponent(
                jLabel37,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40)
        );
        jPanel5.revalidate();
        jPanel5.repaint();
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
 
 
private String requiredTransmissionType = "";

public void setRequiredTransmissionType(String transmissionType) {
    this.requiredTransmissionType = transmissionType == null ? "" : transmissionType.trim();
}

public String getRequiredTransmissionType() {
    return requiredTransmissionType;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel5 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel33 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        hoverButton2 = new test.HoverButton();
        jLabel35 = new javax.swing.JLabel();
        hoverButton3 = new test.HoverButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton4 = new test.HoverButton();
        jLabel49 = new javax.swing.JLabel();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        jLabel50 = new javax.swing.JLabel();
        hoverButton7 = new test.HoverButton();
        jLabel51 = new javax.swing.JLabel();
        jPanel2 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel1 = new ROUNDLABEL(20, new java.awt.Color(0, 204, 204), 2);
        jPanel4 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel10 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel20 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jPanel17 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField36 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel20 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField39 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel21 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField40 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel31 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField37 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jPanel33 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField38 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jPanel19 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField35 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel30 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField41 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel32 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField42 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel34 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField43 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel35 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField44 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jPanel36 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField45 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(27, 26, 26));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, 40));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel5.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("   Dashboard");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("    Schedule");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        button2.setText("      Rent and Bookings");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel5.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("   Customers");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

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

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel5.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 690));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\MAZDA\\car_brands_slider_smooth_12_1000x290.gif")); // NOI18N
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 30, 30), 1, true));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 1000, 290));

        jPanel4.setBackground(new java.awt.Color(26, 27, 27));

        jPanel10.setBackground(new java.awt.Color(27, 26, 26));

        jLabel20.setBackground(new java.awt.Color(27, 26, 26));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-mitsubishi-901.png")); // NOI18N
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jTextField34.setBackground(new java.awt.Color(27, 26, 26));
        jTextField34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField34.setForeground(new java.awt.Color(255, 255, 255));
        jTextField34.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField34.setText("MITSUBISHI");
        jTextField34.setBorder(null);
        jTextField34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField34, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(27, 26, 26));

        jTextField36.setBackground(new java.awt.Color(27, 26, 26));
        jTextField36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField36.setForeground(new java.awt.Color(255, 255, 255));
        jTextField36.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField36.setText("HONDA");
        jTextField36.setBorder(null);
        jTextField36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel22.setBackground(new java.awt.Color(27, 26, 26));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-honda-90.png")); // NOI18N
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jTextField36))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(27, 26, 26));

        jTextField39.setBackground(new java.awt.Color(27, 26, 26));
        jTextField39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField39.setForeground(new java.awt.Color(255, 255, 255));
        jTextField39.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField39.setText("HYUNDAI");
        jTextField39.setBorder(null);
        jTextField39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel26.setBackground(new java.awt.Color(27, 26, 26));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-hyundai-90.png")); // NOI18N
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField39, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(27, 26, 26));

        jTextField40.setBackground(new java.awt.Color(27, 26, 26));
        jTextField40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField40.setForeground(new java.awt.Color(255, 255, 255));
        jTextField40.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField40.setText("FORD");
        jTextField40.setBorder(null);
        jTextField40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel25.setBackground(new java.awt.Color(27, 26, 26));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-ford-100.png")); // NOI18N
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField40, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel31.setBackground(new java.awt.Color(27, 26, 26));

        jTextField37.setBackground(new java.awt.Color(27, 26, 26));
        jTextField37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField37.setForeground(new java.awt.Color(255, 255, 255));
        jTextField37.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField37.setText("SUZUKI");
        jTextField37.setBorder(null);
        jTextField37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel45.setBackground(new java.awt.Color(27, 26, 26));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-suzuki-901.png")); // NOI18N
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField37, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel33.setBackground(new java.awt.Color(27, 26, 26));

        jTextField38.setBackground(new java.awt.Color(27, 26, 26));
        jTextField38.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField38.setForeground(new java.awt.Color(255, 255, 255));
        jTextField38.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField38.setText("KIA");
        jTextField38.setBorder(null);
        jTextField38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel24.setBackground(new java.awt.Color(27, 26, 26));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-kia-90.png")); // NOI18N
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField38, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jTextField32.setBackground(new java.awt.Color(26, 27, 27));
        jTextField32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField32.setForeground(new java.awt.Color(255, 255, 255));
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField32.setText("CAR BRANDS");
        jTextField32.setBorder(null);

        jPanel19.setBackground(new java.awt.Color(27, 26, 26));

        jTextField35.setBackground(new java.awt.Color(27, 26, 26));
        jTextField35.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jTextField35.setForeground(new java.awt.Color(255, 255, 255));
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField35.setText("MERCEDES BENZ");
        jTextField35.setBorder(null);
        jTextField35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel27.setBackground(new java.awt.Color(27, 26, 26));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-mercedes-benz-100.png"))); // NOI18N
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField35))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel30.setBackground(new java.awt.Color(27, 26, 26));

        jTextField41.setBackground(new java.awt.Color(27, 26, 26));
        jTextField41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField41.setForeground(new java.awt.Color(255, 255, 255));
        jTextField41.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField41.setText("NISSAN");
        jTextField41.setBorder(null);
        jTextField41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel21.setBackground(new java.awt.Color(27, 26, 26));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-nissan-100.png"))); // NOI18N
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField41, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel32.setBackground(new java.awt.Color(27, 26, 26));

        jTextField42.setBackground(new java.awt.Color(27, 26, 26));
        jTextField42.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField42.setForeground(new java.awt.Color(255, 255, 255));
        jTextField42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField42.setText("BMW");
        jTextField42.setBorder(null);
        jTextField42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel32.setBackground(new java.awt.Color(27, 26, 26));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-bmw-87.png")); // NOI18N
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField42, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel34.setBackground(new java.awt.Color(27, 26, 26));

        jTextField43.setBackground(new java.awt.Color(27, 26, 26));
        jTextField43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField43.setForeground(new java.awt.Color(255, 255, 255));
        jTextField43.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField43.setText("MAZDA");
        jTextField43.setBorder(null);
        jTextField43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel23.setBackground(new java.awt.Color(27, 26, 26));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-mazda-90.png")); // NOI18N
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField43, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel35.setBackground(new java.awt.Color(27, 26, 26));

        jTextField44.setBackground(new java.awt.Color(27, 26, 26));
        jTextField44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField44.setForeground(new java.awt.Color(255, 255, 255));
        jTextField44.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField44.setText("TOYOTA");
        jTextField44.setBorder(null);
        jTextField44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel46.setBackground(new java.awt.Color(27, 26, 26));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-toyota-90.png")); // NOI18N
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField44, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel36.setBackground(new java.awt.Color(27, 26, 26));

        jTextField45.setBackground(new java.awt.Color(27, 26, 26));
        jTextField45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField45.setForeground(new java.awt.Color(255, 255, 255));
        jTextField45.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField45.setText("CHEVROLET");
        jTextField45.setBorder(null);
        jTextField45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel47.setBackground(new java.awt.Color(27, 26, 26));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BRANDS CAR ICON\\icons8-chevrolet-100.png")); // NOI18N
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField45, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton4ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Customers::new);
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

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED8 sched = new RENT_BOOKED8();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED3 sched = new RENT_BOOKED3();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED10 sched = new RENT_BOOKED10();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED5 sched = new RENT_BOOKED5();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED4 sched = new RENT_BOOKED4();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED2 sched = new RENT_BOOKED2();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED7 sched = new RENT_BOOKED7();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED9 sched = new RENT_BOOKED9();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED sched = new RENT_BOOKED();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED6 sched = new RENT_BOOKED6();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED11 sched = new RENT_BOOKED11();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        // TODO add your handling code here:
    RENT_BOOKED1 sched = new RENT_BOOKED1();

    String actions[] = new String[]{""};
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            dialog.closePopup();
        }),
        option
    );
    }//GEN-LAST:event_jLabel47MouseClicked

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
            new RentANDbookings().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    // End of variables declaration//GEN-END:variables
}
