/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;


import customer.ServiceCustomer;
import customers.ModelCustomer1;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;
import ADMIN.Dashboard;
import customers.AppSession3;

/**
 *
 * @author ArtoCO
 */
public class FULL_PAYPAL extends javax.swing.JPanel {
    
    

    /**
     * Creates new form FULL_PAYPAL
     */
    public FULL_PAYPAL() {
        initComponents();
        loadFromSession(); 

}
    
private void loadFromSession() {
    Integer id = AppSession.getCustomerId();
    if (id == null) return;

    // ✅ IMPORTANT for SAVE button
    this.customerId = id;

    // basic customer info
    jLabel174.setText(new SimpleDateFormat("MMMM dd, yyyy  hh:mm a").format(new java.util.Date()));
    jLabel134.setText(AppSession.getCustomerName());
    jLabel135.setText(AppSession.getCustomerAddress());
    jLabel136.setText(AppSession.getCustomerPhone());

    // car info
    jLabel142.setText(AppSession.getCustomerCarNameType());
    jLabel141.setText(AppSession.getplateno());

    // dates
    jLabel140.setText(AppSession.getCheckInDate());
    jLabel176.setText(AppSession.getCheckOutate()); // your existing getter

    // amounts
    DecimalFormat money = new DecimalFormat("#,##0.00");

    // ✅ RAW values from session (these represent DB values captured earlier)
    dbTotalPaymentRaw = 0;
    try { dbTotalPaymentRaw = Double.parseDouble(AppSession.getOriginalBalance()); } catch (Exception e) {}

    dbDueAmountRaw = 0;
    try { dbDueAmountRaw = Double.parseDouble(AppSession.getDueAmount()); } catch (Exception e) {}

    paymentStatusRaw = AppSession.getPaymentStatus() == null ? "" : AppSession.getPaymentStatus().trim();

    // ✅ Remaining Balance display:
    // FULL PAYMENT => show 0, else show total_payment (originalRaw)
    double remainingDisplay = isFullPayment(paymentStatusRaw) ? 0 : dbTotalPaymentRaw;
    jLabel149.setText(money.format(remainingDisplay));

    // penalty per hour (UI only)
    jLabel150.setText("100");

    // due hours + due amount
    jTextField46.setText(String.valueOf(AppSession.getDueHours()));
    jTextField46.setEditable(false);
    jLabel155.setText(money.format(dbDueAmountRaw));

    // ✅ Grand Total display rule:
    // if remainingDisplay is 0 => grand total = due only
    // else => grand total = remaining + due
    double grandTotalDisplay = (remainingDisplay <= 0) ? dbDueAmountRaw : (remainingDisplay + dbDueAmountRaw);
    jLabel161.setText(money.format(grandTotalDisplay));

    // ✅ icon
    if (AppSession.getTransferIcon() != null) {
        jLabel1.setIcon(AppSession.getTransferIcon());
        jLabel1.revalidate();
        jLabel1.repaint();
    }
}


private Integer customerId;
private javax.swing.Icon paymentIcon;
private Runnable afterPaidCallback;

public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
    loadCustomer(); // auto-load when set
}

public void setPaymentIcon(javax.swing.Icon icon) {
    this.paymentIcon = icon;
    jLabel1.setIcon(icon); // jLabel1 gets icon from PAYMENT jLabel7
}

public void setAfterPaidCallback(Runnable r) {
    this.afterPaidCallback = r;
}

private void loadCustomer() {
    try {
        if (customerId == null) return;

        ServiceCustomer service = new ServiceCustomer();
        ModelCustomer1 c = service.findById(customerId);

        if (c == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Customer not found");
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat money = new DecimalFormat("#,##0.00");

        // header date/time
        jLabel174.setText(new SimpleDateFormat("MMMM dd, yyyy  hh:mm a").format(new java.util.Date()));

        // customer info
        jLabel134.setText(c.getFirstname1() + " " + c.getLastname1());
        jLabel135.setText(c.getPrimaryAddress1());
        jLabel136.setText(c.getPhoneNumber1());

        // car info
        jLabel142.setText(c.getCarType() + " - " + c.getCarName());
        jLabel141.setText(c.getplate_number());

        // dates
        jLabel140.setText(c.geInDate() == null ? "" : df.format(c.geInDate()));
        jLabel176.setText(c.getOutDate() == null ? "" : df.format(c.getOutDate()));

        // RAW values from DB
        paymentStatusRaw = c.getPaymentType() == null ? "" : c.getPaymentType().trim();
        dbTotalPaymentRaw = c.getTotalPayment();   // total_payment
        dbDueAmountRaw = c.getDueAmount();         // due_amount

        // ✅ DISPLAY remaining balance:
        // If FULL PAYMENT => display 0, else display total_payment
        double remainingDisplay = isFullPayment(paymentStatusRaw) ? 0 : dbTotalPaymentRaw;
        jLabel149.setText(money.format(remainingDisplay));

        // penalty/hour fixed
        jLabel150.setText("100");

        // due hours + due amount
        jTextField46.setText(String.valueOf(c.getDueHours()));
        jTextField46.setEditable(false);

        jLabel155.setText(money.format(dbDueAmountRaw));

        // ✅ GRAND TOTAL DISPLAY RULE YOU WANT:
        // If remainingDisplay is 0 => grand total = due only
        // Else => grand total = remaining + due
        double grandTotalDisplay = (remainingDisplay <= 0) ? dbDueAmountRaw : (remainingDisplay + dbDueAmountRaw);
        jLabel161.setText(money.format(grandTotalDisplay));

        // icon
        if (paymentIcon != null) {
            jLabel1.setIcon(paymentIcon);
        } else if (AppSession.getTransferIcon() != null) {
            jLabel1.setIcon(AppSession.getTransferIcon());
        }
        jLabel1.revalidate();
        jLabel1.repaint();

    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Failed to load payment details");
    }
}

private double dbTotalPaymentRaw = 0;   // total_payment from DB
private double dbDueAmountRaw = 0;      // due_amount from DB
private String paymentStatusRaw = "";

private boolean isFullPayment(String s) {
    return s != null && s.trim().equalsIgnoreCase("FULL PAYMENT");
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rSMaterialButtonRectangle4 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle5 = new rojerusan.RSMaterialButtonRectangle();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();

        rSMaterialButtonRectangle4.setBackground(new java.awt.Color(102, 102, 102));
        rSMaterialButtonRectangle4.setBorder(null);
        rSMaterialButtonRectangle4.setText("SAVE");
        rSMaterialButtonRectangle4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle5.setBackground(new java.awt.Color(102, 102, 102));
        rSMaterialButtonRectangle5.setBorder(null);
        rSMaterialButtonRectangle5.setText("PRINT");
        rSMaterialButtonRectangle5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        rSMaterialButtonRectangle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle5ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(37, 37, 37));
        jPanel3.setForeground(new java.awt.Color(27, 26, 26));

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 255, 255));
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("RECEIPT");

        jLabel123.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 255, 255));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("BILL FROM:");
        jLabel123.setToolTipText("");
        jLabel123.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel124.setForeground(new java.awt.Color(255, 255, 255));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("Company Name              :");
        jLabel124.setToolTipText("");
        jLabel124.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel125.setForeground(new java.awt.Color(255, 255, 255));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("Street Address, Zip code :");
        jLabel125.setToolTipText("");
        jLabel125.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("Phone Number                :");
        jLabel126.setToolTipText("");
        jLabel126.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel127.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Rent - A - Carz");
        jLabel127.setToolTipText("");
        jLabel127.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel128.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 255, 255));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("Visayan Village Tagum City, 8801");
        jLabel128.setToolTipText("");
        jLabel128.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 255, 255));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("09555270541");
        jLabel129.setToolTipText("");
        jLabel129.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("Phone Number                 :");
        jLabel130.setToolTipText("");
        jLabel130.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setText("Street Address, Zip code :");
        jLabel131.setToolTipText("");
        jLabel131.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("Customer Name               :");
        jLabel132.setToolTipText("");
        jLabel132.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel133.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 255, 255));
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("BILL TO:");
        jLabel133.setToolTipText("");
        jLabel133.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel134.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 255, 255));
        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setToolTipText("");
        jLabel134.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel135.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setToolTipText("");
        jLabel135.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel136.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(255, 255, 255));
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setToolTipText("");
        jLabel136.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel137.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 255, 255));
        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("VEHICLE INFORMATION:");
        jLabel137.setToolTipText("");
        jLabel137.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel138.setForeground(new java.awt.Color(255, 255, 255));
        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("Plate Number                  :");
        jLabel138.setToolTipText("");
        jLabel138.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("Car Name / Type            :");
        jLabel139.setToolTipText("");
        jLabel139.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel140.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setToolTipText("");
        jLabel140.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setToolTipText("");
        jLabel141.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel142.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setToolTipText("");
        jLabel142.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel143.setBackground(new java.awt.Color(102, 102, 102));
        jLabel143.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel143.setText("DUE AMOUNT");
        jLabel143.setToolTipText("");
        jLabel143.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel143.setOpaque(true);

        jLabel144.setBackground(new java.awt.Color(102, 102, 102));
        jLabel144.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("INFORMATION");
        jLabel144.setToolTipText("");
        jLabel144.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel144.setOpaque(true);

        jLabel145.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setText("PAYMENT:");
        jLabel145.setToolTipText("");
        jLabel145.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setText("Total Due Hours                      :");
        jLabel146.setToolTipText("");
        jLabel146.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("Due Penalty Per hour:             :");
        jLabel147.setToolTipText("");
        jLabel147.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Remaining Balance                 :");
        jLabel148.setToolTipText("");
        jLabel148.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel149.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setToolTipText("");
        jLabel149.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel150.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(255, 255, 255));
        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("₱100");
        jLabel150.setToolTipText("");
        jLabel150.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("-");
        jLabel151.setToolTipText("");
        jLabel151.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextField46.setEditable(false);
        jTextField46.setBackground(new java.awt.Color(37, 37, 37));
        jTextField46.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jTextField46.setForeground(new java.awt.Color(255, 255, 255));
        jTextField46.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField46.setBorder(null);
        jTextField46.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField46jTextField40FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField46jTextField40FocusLost(evt);
            }
        });
        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46jTextField40ActionPerformed(evt);
            }
        });

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(37, 37, 37));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jTextField8.setText("______________________________________________________");
        jTextField8.setBorder(null);

        jLabel160.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(255, 255, 255));
        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("Total Amount:");
        jLabel160.setToolTipText("");
        jLabel160.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel161.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(255, 255, 255));
        jLabel161.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel161.setToolTipText("");
        jLabel161.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel162.setBackground(new java.awt.Color(102, 102, 102));
        jLabel162.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(255, 255, 255));
        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel162.setText("OTHERS");
        jLabel162.setToolTipText("");
        jLabel162.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel162.setOpaque(true);

        jLabel164.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel164.setForeground(new java.awt.Color(255, 255, 255));
        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText("DATE:");
        jLabel164.setToolTipText("");
        jLabel164.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel165.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(255, 255, 255));
        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setToolTipText("");
        jLabel165.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel166.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(255, 255, 255));
        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel166.setText("NOTES:");
        jLabel166.setToolTipText("");
        jLabel166.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel167.setForeground(new java.awt.Color(255, 255, 255));
        jLabel167.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel167.setText("Thank you for choosing RENT - A - CARZ");
        jLabel167.setToolTipText("");
        jLabel167.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel168.setForeground(new java.awt.Color(255, 255, 255));
        jLabel168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel168.setText("for your car rental needs. If you have any");
        jLabel168.setToolTipText("");
        jLabel168.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel169.setForeground(new java.awt.Color(255, 255, 255));
        jLabel169.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel169.setText("questions regarding this invoice or need further");
        jLabel169.setToolTipText("");
        jLabel169.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel170.setForeground(new java.awt.Color(255, 255, 255));
        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText("assistance, please don't hesistate to contact us");
        jLabel170.setToolTipText("");
        jLabel170.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel171.setForeground(new java.awt.Color(255, 255, 255));
        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setText("at 09555270541 or rentAcarz@gmail.com.");
        jLabel171.setToolTipText("");
        jLabel171.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel176.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel176.setForeground(new java.awt.Color(255, 255, 255));
        jLabel176.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel176.setToolTipText("");
        jLabel176.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel177.setForeground(new java.awt.Color(255, 255, 255));
        jLabel177.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel177.setText("Rental Period                   :");
        jLabel177.setToolTipText("");
        jLabel177.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel178.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(255, 255, 255));
        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setToolTipText("");
        jLabel178.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel155.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(255, 255, 255));
        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel163.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(255, 255, 255));
        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("PAYMENT METHOD");
        jLabel163.setToolTipText("");
        jLabel163.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel172.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel172.setForeground(new java.awt.Color(255, 255, 255));
        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setToolTipText("");
        jLabel172.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel174.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel174.setForeground(new java.awt.Color(255, 255, 255));
        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setToolTipText("");
        jLabel174.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel158.setForeground(new java.awt.Color(255, 255, 255));
        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("Total Due Amount                  :");
        jLabel158.setToolTipText("");
        jLabel158.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(126, 126, 126))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel137)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel138, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jLabel177, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(19, 19, 19))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel176, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(38, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel133)
                            .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel132)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jLabel143, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel144, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel162, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel145)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel148, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel146, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel147, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel158, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel161, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel168, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel166, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel167, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel171, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel169, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel170, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel164)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel144)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel123)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124)
                    .addComponent(jLabel127))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel125)
                    .addComponent(jLabel128))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126)
                    .addComponent(jLabel129))
                .addGap(36, 36, 36)
                .addComponent(jLabel133)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel135, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel137)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel139)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel176, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel177))
                .addGap(0, 28, Short.MAX_VALUE)
                .addComponent(jLabel143)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel121)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel145)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel148)
                    .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel146)
                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel158))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel161, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel163, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel165)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel178)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel162)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel166)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel167)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel168)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel169)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel170)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel171))
                    .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                        .addComponent(rSMaterialButtonRectangle4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSMaterialButtonRectangle4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed

    try {
        if (customerId == null) return;

        ServiceCustomer service = new ServiceCustomer();

        // ✅ Always pull fresh values from DB when saving
        ModelCustomer1 c = service.findById(customerId);
        if (c == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Customer not found");
            return;
        }

        String status = c.getPaymentType() == null ? "" : c.getPaymentType().trim();
        double dbTotalPayment = c.getTotalPayment(); // total_payment in DB
        double dbDueAmount = c.getDueAmount();       // due_amount in DB

        // Nothing to add
        if (dbDueAmount <= 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "No due amount to add");
            return;
        }

        // ✅ This is your DISPLAY rule (same as loadCustomer)
        double remainingDisplay = isFullPayment(status) ? 0 : dbTotalPayment;
        double grandTotalDisplay = (remainingDisplay <= 0) ? dbDueAmount : (remainingDisplay + dbDueAmount);

        // ✅ IMPORTANT DB UPDATE RULE:
        // You DO NOT add "grandTotalDisplay" when remainingDisplay > 0 because that would double the base.
        // Correct new DB total = old total_payment + due_amount
        double newTotalPayment = dbTotalPayment + dbDueAmount;

        // Save + clear dues + set FULL PAYMENT + status RETURNED (your service method)
        service.settleDueAsFullPayment(customerId, newTotalPayment);

       
Notifications.getInstance().show(Notifications.Type.SUCCESS, "Payment saved successfully");

// popup/FULL_PAYPAL.java  (inside SAVE after SUCCESS)
for (java.awt.Window w : java.awt.Window.getWindows()) {
    if (w instanceof Dashboard && w.isShowing()) {
        ((Dashboard) w).refreshDashboard();
        break;
    }
}

// ✅ refresh Dashboard (the caller will define what this does)
if (afterPaidCallback != null) {
    javax.swing.SwingUtilities.invokeLater(afterPaidCallback);
}
        

        // ✅ Refresh this receipt view
        loadCustomer();

        // close popup
        try { GlassPanePopup.closePopupAll(); } catch (Exception ex) {}

    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Failed to save payment");
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed

    private void jTextField46jTextField40FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46jTextField40FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40FocusGained

    private void jTextField46jTextField40FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46jTextField40FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40FocusLost

    private void jTextField46jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40ActionPerformed

    private void rSMaterialButtonRectangle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField8;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle5;
    // End of variables declaration//GEN-END:variables
}
