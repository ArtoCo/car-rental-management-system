/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;
import customers.AppSession3;
import popup.AppSession;
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

/**
 *
 * @author ArtoCO
 */
public class RECEIPT extends javax.swing.JPanel {

    /**
     * Creates new form RECEIPT
     */
    public RECEIPT() {
        initComponents();
        
    loadPaymentData();

AppSession3.addPaymentListener(evt -> {
    if (AppSession3.PROP_PAYMENT_DATA.equals(evt.getPropertyName())) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            loadPaymentData();
        });
    }
});


        // Set the values in labels
        CustomerInfo info = AppSession2.getCustomerInfo();
        if (info == null) {
            info = AppSession.getCustomerInfo();
        }
        if (info != null) {
        jLabel134.setText(info.getFullName());
        jLabel135.setText(info.getAddress());
        jLabel136.setText(info.getPhoneNumber());
        jLabel142.setText(info.getFullcarinfo());
        jLabel141.setText(info.getcarPlate());
        
        String transDate = info.gettransDate();
        jLabel180.setText(transDate != null ? transDate : "No date set");
        
        
        String formattedDate = info.getFormattedDate();
        jLabel140.setText(formattedDate != null ? formattedDate : "No date set");
        
        String formattedDateOut = info.getFormattedOutDate();
        jLabel176.setText(formattedDateOut != null ? formattedDateOut : "No date set");
        
        jLabel149.setText(info.getRentPay());
        jLabel150.setText(info.getRentInsurance());
        jLabel153.setText(info.getVat());
        jLabel155.setText(info.getDriver());
        jLabel159.setText(info.getFullpay());
        jLabel161.setText(info.getPartialpay());
        }
        
        if (info != null) {
    jLabel134.setText(info.getFullName());
    jLabel135.setText(info.getAddress());
    jLabel136.setText(info.getPhoneNumber());
    jLabel142.setText(info.getFullcarinfo());
    jLabel141.setText(info.getcarPlate());
    jLabel180.setText(info.gettransDate());
    jLabel140.setText(info.getFormattedDate());
    jLabel176.setText(info.getFormattedOutDate());
    jLabel149.setText(info.getRentPay());
    jLabel150.setText(info.getRentInsurance());
    jLabel153.setText(info.getVat());
    jLabel155.setText(info.getDriver());
    jLabel159.setText(info.getFullpay());
    jLabel161.setText(info.getPartialpay());
    } else {
    jLabel134.setText("N/A");
    jLabel135.setText("N/A");
    jLabel136.setText("N/A");
    jLabel142.setText("N/A");
    jLabel141.setText("N/A");
    jLabel180.setText("N/A");
    jLabel140.setText("N/A");
    jLabel176.setText("N/A");
    jLabel149.setText("N/A");
    jLabel150.setText("N/A");
    jLabel153.setText("N/A");
    jLabel155.setText("N/A");
    jLabel159.setText("N/A");
    jLabel161.setText("N/A");
    }
}        
    public void printPanel(JPanel panel) {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setJobName("Print Agreement Form");

    job.setPrintable(new Printable() {
        public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) g;
            
            // Calculate scale to fit the panel on the page
            double scaleX = pf.getImageableWidth() / panel.getWidth();
            double scaleY = pf.getImageableHeight() / panel.getHeight();
            double scale = Math.min(scaleX, scaleY);

            // Calculate centering positions
            double centerX = (pf.getImageableWidth() - (panel.getWidth() * scale)) / 2;
            double centerY = (pf.getImageableHeight() - (panel.getHeight() * scale)) / 2;

            // Translate and scale
            g2d.translate(pf.getImageableX() + centerX, pf.getImageableY() + centerY);
            g2d.scale(scale, scale);

            panel.printAll(g2d);
            return Printable.PAGE_EXISTS;
        }
    });

    boolean doPrint = job.printDialog();
    if (doPrint) {
        try {
            job.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(panel, "Print Failed: " + ex.getMessage());
        }
    }
}
    
    private void setComponentColors(Component comp, Color fg, Color bg) {
    comp.setForeground(fg);
    comp.setBackground(bg);

    if (comp instanceof Container) {
        for (Component child : ((Container) comp).getComponents()) {
            setComponentColors(child, fg, bg);
        }
    }
}
    
    private void setComponentFontsBold(Component comp) {
    if (comp instanceof JLabel || comp instanceof JTextComponent) {
        Font font = comp.getFont();
        comp.setFont(font.deriveFont(Font.BOLD));
    }

    if (comp instanceof Container) {
        for (Component child : ((Container) comp).getComponents()) {
            setComponentFontsBold(child);
        }
    }
}

private void loadPaymentData() {
    AppSession3.PaymentData data = AppSession3.getPaymentData();

    if (data != null && data.hasIcon()) {
        jLabel6.setIcon(data.getIcon());
        jLabel6.setText("");
        jLabel6.setToolTipText(data.getMethodName());

        // Optional: show the selected payment name beside the title
        jLabel163.setText("PAYMENT METHOD: " + data.getMethodName().replace("PAY USING ", ""));
    } else {
        jLabel6.setIcon(null);
        jLabel6.setText("N/A");
        jLabel6.setToolTipText(null);
        jLabel163.setText("PAYMENT METHOD");
    }

    jLabel6.revalidate();
    jLabel6.repaint();
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
        jLabel152 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
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
        jLabel173 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jLabel178 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        rSMaterialButtonRectangle4.setBackground(new java.awt.Color(102, 102, 102));
        rSMaterialButtonRectangle4.setBorder(null);
        rSMaterialButtonRectangle4.setText("PRINT");
        rSMaterialButtonRectangle4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
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
        jLabel143.setText("DESCRIPTION");
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
        jLabel146.setText("Fuel Charges                          :");
        jLabel146.setToolTipText("");
        jLabel146.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("Total Insurance Charges       :");
        jLabel147.setToolTipText("");
        jLabel147.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Total Base Rent Pay               :");
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
        jLabel150.setToolTipText("");
        jLabel150.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("-");
        jLabel151.setToolTipText("");
        jLabel151.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel152.setForeground(new java.awt.Color(255, 255, 255));
        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText("Vat (12%)                                :");
        jLabel152.setToolTipText("");
        jLabel152.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextField46.setEditable(false);
        jTextField46.setBackground(new java.awt.Color(37, 37, 37));
        jTextField46.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jTextField46.setForeground(new java.awt.Color(255, 255, 255));
        jTextField46.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField46.setText("₱5,000");
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

        jLabel153.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(255, 255, 255));
        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setToolTipText("");
        jLabel153.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel154.setForeground(new java.awt.Color(255, 255, 255));
        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText("Additional Driver Fee            :");
        jLabel154.setToolTipText("");
        jLabel154.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel157.setForeground(new java.awt.Color(255, 255, 255));
        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setText("Discount / Promo                 :");
        jLabel157.setToolTipText("");
        jLabel157.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(37, 37, 37));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jTextField8.setText("______________________________________________________");
        jTextField8.setBorder(null);

        jLabel158.setForeground(new java.awt.Color(255, 255, 255));
        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("Grand Total Amount            :");
        jLabel158.setToolTipText("");
        jLabel158.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel159.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel159.setForeground(new java.awt.Color(255, 255, 255));
        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setToolTipText("");
        jLabel159.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel160.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(255, 255, 255));
        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("Partial Total Amount:");
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

        jLabel173.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel173.setForeground(new java.awt.Color(255, 255, 255));
        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setToolTipText("");
        jLabel173.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

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

        jTextField48.setEditable(false);
        jTextField48.setBackground(new java.awt.Color(37, 37, 37));
        jTextField48.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jTextField48.setForeground(new java.awt.Color(255, 255, 255));
        jTextField48.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField48.setText("₱2000");
        jTextField48.setBorder(null);
        jTextField48.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField48jTextField42FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField48jTextField42FocusLost(evt);
            }
        });
        jTextField48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField48jTextField42ActionPerformed(evt);
            }
        });

        jLabel178.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(255, 255, 255));
        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setToolTipText("");
        jLabel178.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel180.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel180.setForeground(new java.awt.Color(255, 255, 255));

        jLabel155.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(255, 255, 255));

        jLabel163.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(255, 255, 255));
        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("PAYMENT METHOD:");
        jLabel163.setToolTipText("");
        jLabel163.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel172.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel172.setForeground(new java.awt.Color(255, 255, 255));
        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setToolTipText("");
        jLabel172.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

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
                                    .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel133)
                            .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
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
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel168, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel166, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel167, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jLabel171, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel169, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jLabel170, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(381, 381, 381)
                                .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(34, 34, 34))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel164)
                .addGap(12, 12, 12)
                .addComponent(jLabel180, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel158, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel159, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(1, 1, 1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel145)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel154, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel148, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel147, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel146, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel152, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel155, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(172, 172, 172)
                                        .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel157, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(33, 33, 33))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel163, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel160, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel161, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGap(18, 18, 18)
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel154, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel155, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel157)
                    .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel158, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel159, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel161, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel163, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jLabel171)
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel180, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rSMaterialButtonRectangle4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSMaterialButtonRectangle4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField46jTextField40FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46jTextField40FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40FocusGained

    private void jTextField46jTextField40FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46jTextField40FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40FocusLost

    private void jTextField46jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46jTextField40ActionPerformed

    private void jTextField48jTextField42FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField48jTextField42FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48jTextField42FocusGained

    private void jTextField48jTextField42FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField48jTextField42FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48jTextField42FocusLost

    private void jTextField48jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField48jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48jTextField42ActionPerformed

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed
        // TODO add your handling code here:
        Color originalBg = jPanel3.getBackground();
Color originalFg = jPanel3.getForeground();

// Save original label backgrounds
Color bg144 = jLabel144.getBackground();
Color bg143 = jLabel143.getBackground();
Color bg162 = jLabel162.getBackground();

// Set print-friendly colors
jPanel3.setBackground(Color.WHITE);
setComponentColors(jPanel3, Color.BLACK, Color.WHITE);
setComponentFontsBold(jPanel3); // Optional

// Set specific labels to gray
Color printGray = new Color(200, 200, 200); // Light gray
jLabel144.setOpaque(true);
jLabel143.setOpaque(true);
jLabel162.setOpaque(true);
jLabel144.setBackground(printGray);
jLabel143.setBackground(printGray);
jLabel162.setBackground(printGray);

PrinterJob job = PrinterJob.getPrinterJob();
job.setJobName("Print RECEIPT Panel");

job.setPrintable(new Printable() {
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        double scaleX = pf.getImageableWidth() / jPanel3.getWidth();
        double scaleY = pf.getImageableHeight() / jPanel3.getHeight();
        double scale = Math.min(scaleX, scaleY);

        double x = pf.getImageableX() + (pf.getImageableWidth() - (jPanel3.getWidth() * scale)) / 2;
        double y = pf.getImageableY() + (pf.getImageableHeight() - (jPanel3.getHeight() * scale)) / 2;

        g2d.translate(x, y);
        g2d.scale(scale, scale);

        jPanel3.printAll(g2d);
        return Printable.PAGE_EXISTS;
    }
});

if (job.printDialog()) {
    try {
        job.print();
    } catch (PrinterException ex) {
        JOptionPane.showMessageDialog(null, "Printing Error: " + ex.getMessage());
    }
}

// Restore original colors
jPanel3.setBackground(originalBg);
setComponentColors(jPanel3, originalFg, originalBg);

// Restore original label backgrounds
jLabel144.setBackground(bg144);
jLabel143.setBackground(bg143);
jLabel162.setBackground(bg162);
 

    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
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
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField8;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    // End of variables declaration//GEN-END:variables
}
