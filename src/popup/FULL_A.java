/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import popup.AppSession2;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author ArtoCO
 */
public class FULL_A extends javax.swing.JPanel {

    /**
     * Creates new form FULL_A
     */
    public FULL_A() {
        initComponents();
        
     // ✅ Make sure UI is created first, then load session values
    SwingUtilities.invokeLater(this::refreshFromSession);
    }
    
    public final void refreshFromSession() {
    CustomerInfo info = AppSession2.getCustomerInfo();

    if (info != null) {
        // Full name
        setTF(jTextField115, info.getFullName());
        setTF(jTextField137, info.getFullName());

        // Address
        setTF(jTextField118, info.getAddress());

        // Vehicle info
        setTF(jTextField122, info.getFullcarinfo());
        setTF(jTextField125, info.getcarPlate());

        // Transaction date
        String transDate = info.gettransDate();
        setLabel(jLabel2, transDate);
        setTF(jTextField127, transDate);

        // Rental period dates
        setTF(jTextField128, info.getFormattedDate());
        setTF(jTextField131, info.getFormattedOutDate());

        // Total payment (full payment)
        String fullPay = info.getFullpay();
        jTextField134.setText((fullPay == null || fullPay.trim().isEmpty()) ? "₱0.00" : fullPay);

    } else {
        // If session is null, mark all fields as N/A
        setTF(jTextField115, "N/A");
        setTF(jTextField137, "N/A");
        setTF(jTextField118, "N/A");
        setTF(jTextField122, "N/A");
        setTF(jTextField125, "N/A");
        setTF(jTextField128, "N/A");
        setTF(jTextField131, "N/A");
        setTF(jTextField134, "N/A");
        setLabel(jLabel2, "N/A");
        setTF(jTextField127, "N/A");
    }
}

private void setTF(javax.swing.JTextField tf, String value) {
    tf.setText((value == null || value.trim().isEmpty()) ? "N/A" : value);
}

private void setLabel(javax.swing.JLabel lbl, String value) {
    lbl.setText((value == null || value.trim().isEmpty()) ? "N/A" : value);
}

    
    private void printPanelFitWidthMultiPage(JPanel panel) {
    // Ensure correct size/layout
    panel.doLayout();
    if (panel.getWidth() <= 0 || panel.getHeight() <= 0) {
        panel.setSize(panel.getPreferredSize());
    }

    PrinterJob job = PrinterJob.getPrinterJob();
    job.setJobName("Print Customer Form");

    // Optional: reduce margins a bit (bigger content)
    PageFormat pf = job.defaultPage();
    Paper paper = pf.getPaper();
    double margin = 18; // points (~0.25 inch). Try 10 for smaller margins
    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
    pf.setPaper(paper);

    job.setPrintable(new Printable() {
        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {

            Graphics2D g2d = (Graphics2D) g;

            // ✅ Scale ONLY to fit width (keeps text large)
            double scale = pageFormat.getImageableWidth() / panel.getWidth();

            // Height of one printable page in PANEL coordinates
            double pageHeightInPanelCoords = pageFormat.getImageableHeight() / scale;

            int totalPages = (int) Math.ceil(panel.getHeight() / pageHeightInPanelCoords);

            if (pageIndex >= totalPages) {
                return NO_SUCH_PAGE;
            }

            // Move to printable area
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Apply scaling
            g2d.scale(scale, scale);

            // Move panel up for the current page slice
            g2d.translate(0, -pageIndex * pageHeightInPanelCoords);

            // Print the panel (it will be clipped by the printer page)
            panel.printAll(g2d);

            return PAGE_EXISTS;
        }
    }, pf);

    if (job.printDialog()) {
        try {
            job.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printing Error: " + ex.getMessage());
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField108 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField111 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jTextField112 = new javax.swing.JTextField();
        jTextField109 = new javax.swing.JTextField();
        jTextField113 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField114 = new javax.swing.JTextField();
        jTextField115 = new javax.swing.JTextField();
        jTextField116 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField117 = new javax.swing.JTextField();
        jTextField118 = new javax.swing.JTextField();
        jTextField119 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField120 = new javax.swing.JTextField();
        jTextField125 = new javax.swing.JTextField();
        jTextField122 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jTextField128 = new javax.swing.JTextField();
        jLabel151 = new javax.swing.JLabel();
        jTextField131 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField134 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jTextField86 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField88 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jTextField93 = new javax.swing.JTextField();
        jTextField94 = new javax.swing.JTextField();
        jTextField95 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jTextField97 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jTextField99 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jTextField101 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        jTextField104 = new javax.swing.JTextField();
        jTextField105 = new javax.swing.JTextField();
        jTextField106 = new javax.swing.JTextField();
        jTextField137 = new javax.swing.JTextField();
        jTextField136 = new javax.swing.JTextField();
        jTextField138 = new javax.swing.JTextField();
        jTextField107 = new javax.swing.JTextField();
        jTextField110 = new javax.swing.JTextField();
        jTextField121 = new javax.swing.JTextField();
        jTextField123 = new javax.swing.JTextField();
        jTextField124 = new javax.swing.JTextField();
        jTextField126 = new javax.swing.JTextField();
        jTextField127 = new javax.swing.JTextField();
        jTextField70 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();

        jPanel2.setMinimumSize(new java.awt.Dimension(771, 1640));
        jPanel2.setPreferredSize(new java.awt.Dimension(765, 1640));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(26, 27, 27));
        jPanel3.setMinimumSize(new java.awt.Dimension(765, 1953));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField30.setEditable(false);
        jTextField30.setBackground(new java.awt.Color(26, 27, 27));
        jTextField30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField30.setForeground(new java.awt.Color(255, 255, 255));
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField30.setText("CAR RENTAL AGREEMENT");
        jTextField30.setBorder(null);
        jPanel3.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 765, 30));

        jTextField31.setBackground(new java.awt.Color(26, 27, 27));
        jTextField31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField31.setForeground(new java.awt.Color(255, 255, 255));
        jTextField31.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField31.setText("This Car Rental Agreement ( the \"Agreement\" ) is made and entered on");
        jTextField31.setBorder(null);
        jPanel3.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 56, 399, -1));

        jTextField108.setBackground(new java.awt.Color(26, 27, 27));
        jTextField108.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField108.setForeground(new java.awt.Color(255, 255, 255));
        jTextField108.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField108.setText("___________________________________________________");
        jTextField108.setBorder(null);
        jPanel3.add(jTextField108, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 56, 21, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 56, 114, 16));

        jTextField111.setBackground(new java.awt.Color(26, 27, 27));
        jTextField111.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField111.setForeground(new java.awt.Color(255, 255, 255));
        jTextField111.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField111.setText("________________________________________________");
        jTextField111.setBorder(null);
        jPanel3.add(jTextField111, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 56, 22, -1));

        jTextField51.setBackground(new java.awt.Color(26, 27, 27));
        jTextField51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField51.setForeground(new java.awt.Color(255, 255, 255));
        jTextField51.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField51.setText("(the \"Effective Date\") ");
        jTextField51.setBorder(null);
        jPanel3.add(jTextField51, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 56, -1, -1));

        jTextField52.setBackground(new java.awt.Color(26, 27, 27));
        jTextField52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField52.setForeground(new java.awt.Color(255, 255, 255));
        jTextField52.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField52.setText("by");
        jTextField52.setBorder(null);
        jPanel3.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 78, 19, -1));

        jTextField112.setBackground(new java.awt.Color(26, 27, 27));
        jTextField112.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField112.setForeground(new java.awt.Color(255, 255, 255));
        jTextField112.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField112.setText("RENT - A - CARZ");
        jTextField112.setBorder(null);
        jPanel3.add(jTextField112, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 78, -1, -1));

        jTextField109.setBackground(new java.awt.Color(26, 27, 27));
        jTextField109.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField109.setForeground(new java.awt.Color(255, 255, 255));
        jTextField109.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField109.setText("___________________________________________________");
        jTextField109.setBorder(null);
        jPanel3.add(jTextField109, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 78, 37, -1));

        jTextField113.setBackground(new java.awt.Color(26, 27, 27));
        jTextField113.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField113.setForeground(new java.awt.Color(255, 255, 255));
        jTextField113.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField113.setText("___________________________________________________");
        jTextField113.setBorder(null);
        jPanel3.add(jTextField113, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 78, 29, -1));

        jTextField54.setBackground(new java.awt.Color(26, 27, 27));
        jTextField54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField54.setForeground(new java.awt.Color(255, 255, 255));
        jTextField54.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField54.setText("(the \"Lessor\") with residence address at");
        jTextField54.setBorder(null);
        jPanel3.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 78, -1, -1));

        jTextField55.setBackground(new java.awt.Color(26, 27, 27));
        jTextField55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField55.setForeground(new java.awt.Color(255, 255, 255));
        jTextField55.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField55.setText("_______Visayan Village Tagum City, 8801________");
        jTextField55.setBorder(null);
        jPanel3.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 78, 259, -1));

        jTextField56.setBackground(new java.awt.Color(26, 27, 27));
        jTextField56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField56.setForeground(new java.awt.Color(255, 255, 255));
        jTextField56.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField56.setText("and");
        jTextField56.setBorder(null);
        jPanel3.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 78, 27, -1));

        jTextField114.setBackground(new java.awt.Color(26, 27, 27));
        jTextField114.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField114.setForeground(new java.awt.Color(255, 255, 255));
        jTextField114.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField114.setText("___________________________________________________");
        jTextField114.setBorder(null);
        jPanel3.add(jTextField114, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 101, 13, -1));

        jTextField115.setBackground(new java.awt.Color(26, 27, 27));
        jTextField115.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField115.setForeground(new java.awt.Color(255, 255, 255));
        jTextField115.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField115.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel3.add(jTextField115, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 100, 119, -1));

        jTextField116.setBackground(new java.awt.Color(26, 27, 27));
        jTextField116.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField116.setForeground(new java.awt.Color(255, 255, 255));
        jTextField116.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField116.setText("___________________________________________________");
        jTextField116.setBorder(null);
        jPanel3.add(jTextField116, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 101, 13, -1));

        jTextField58.setBackground(new java.awt.Color(26, 27, 27));
        jTextField58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField58.setForeground(new java.awt.Color(255, 255, 255));
        jTextField58.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField58.setText("(the \"Lessee\") with residence address at");
        jTextField58.setBorder(null);
        jPanel3.add(jTextField58, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 101, -1, -1));

        jTextField117.setBackground(new java.awt.Color(26, 27, 27));
        jTextField117.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField117.setForeground(new java.awt.Color(255, 255, 255));
        jTextField117.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField117.setText("___________________________________________________");
        jTextField117.setBorder(null);
        jPanel3.add(jTextField117, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 101, 13, -1));

        jTextField118.setBackground(new java.awt.Color(26, 27, 27));
        jTextField118.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField118.setForeground(new java.awt.Color(255, 255, 255));
        jTextField118.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField118.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel3.add(jTextField118, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 100, 246, -1));

        jTextField119.setBackground(new java.awt.Color(26, 27, 27));
        jTextField119.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField119.setForeground(new java.awt.Color(255, 255, 255));
        jTextField119.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField119.setText("___________________________________________________");
        jTextField119.setBorder(null);
        jPanel3.add(jTextField119, new org.netbeans.lib.awtextra.AbsoluteConstraints(713, 101, 13, -1));

        jTextField60.setBackground(new java.awt.Color(26, 27, 27));
        jTextField60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField60.setForeground(new java.awt.Color(255, 255, 255));
        jTextField60.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField60.setText("Subject to the terms and conditions stated below the parties agree as follows:");
        jTextField60.setBorder(null);
        jPanel3.add(jTextField60, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 159, -1, -1));

        jTextField61.setBackground(new java.awt.Color(102, 102, 102));
        jTextField61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField61.setForeground(new java.awt.Color(255, 255, 255));
        jTextField61.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField61.setText("A. RENTED VEHICLE");
        jTextField61.setBorder(null);
        jPanel3.add(jTextField61, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 191, 765, -1));

        jTextField62.setBackground(new java.awt.Color(26, 27, 27));
        jTextField62.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField62.setForeground(new java.awt.Color(255, 255, 255));
        jTextField62.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField62.setText("DESCRIPTION:");
        jTextField62.setBorder(null);
        jPanel3.add(jTextField62, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 239, -1, -1));

        jTextField120.setBackground(new java.awt.Color(26, 27, 27));
        jTextField120.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField120.setForeground(new java.awt.Color(255, 255, 255));
        jTextField120.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField120.setText("Plate NO:");
        jTextField120.setBorder(null);
        jPanel3.add(jTextField120, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 263, -1, -1));

        jTextField125.setBackground(new java.awt.Color(26, 27, 27));
        jTextField125.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField125.setForeground(new java.awt.Color(255, 255, 255));
        jTextField125.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField125.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField125, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 262, 197, -1));

        jTextField122.setBackground(new java.awt.Color(26, 27, 27));
        jTextField122.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField122.setForeground(new java.awt.Color(255, 255, 255));
        jTextField122.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField122.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField122, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 238, 197, -1));

        jTextField64.setBackground(new java.awt.Color(102, 102, 102));
        jTextField64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField64.setForeground(new java.awt.Color(255, 255, 255));
        jTextField64.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField64.setText("B. RENTAL PERIOD");
        jTextField64.setBorder(null);
        jPanel3.add(jTextField64, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 298, 765, -1));

        jTextField65.setBackground(new java.awt.Color(26, 27, 27));
        jTextField65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField65.setForeground(new java.awt.Color(255, 255, 255));
        jTextField65.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField65.setText("Start time/date: End time/date:");
        jTextField65.setBorder(null);
        jPanel3.add(jTextField65, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 352, 188, -1));

        jTextField128.setBackground(new java.awt.Color(26, 27, 27));
        jTextField128.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField128.setForeground(new java.awt.Color(255, 255, 255));
        jTextField128.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField128.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField128, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 351, 97, -1));

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("-");
        jLabel151.setToolTipText("");
        jLabel151.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel3.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 345, -1, -1));

        jTextField131.setBackground(new java.awt.Color(26, 27, 27));
        jTextField131.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField131.setForeground(new java.awt.Color(255, 255, 255));
        jTextField131.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField131.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField131, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 351, 97, -1));

        jTextField66.setBackground(new java.awt.Color(26, 27, 27));
        jTextField66.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField66.setForeground(new java.awt.Color(255, 255, 255));
        jTextField66.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField66.setText("Penalty Per Hour Rate For Late Return Of Unit (ALL TYPE OF CARS):");
        jTextField66.setBorder(null);
        jPanel3.add(jTextField66, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 376, -1, -1));

        jTextField67.setBackground(new java.awt.Color(26, 27, 27));
        jTextField67.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField67.setForeground(new java.awt.Color(255, 255, 255));
        jTextField67.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField67.setText("_____₱ 100 _____");
        jTextField67.setBorder(null);
        jPanel3.add(jTextField67, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 376, -1, -1));

        jTextField68.setBackground(new java.awt.Color(102, 102, 102));
        jTextField68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField68.setForeground(new java.awt.Color(255, 255, 255));
        jTextField68.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField68.setText("C. RENTAL PAYMENT");
        jTextField68.setBorder(null);
        jPanel3.add(jTextField68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 493, 765, -1));

        jTextField69.setBackground(new java.awt.Color(26, 27, 27));
        jTextField69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField69.setForeground(new java.awt.Color(255, 255, 255));
        jTextField69.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField69.setText("The total rental fee is PHP:");
        jTextField69.setBorder(null);
        jPanel3.add(jTextField69, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 541, 152, -1));

        jTextField134.setBackground(new java.awt.Color(26, 27, 27));
        jTextField134.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField134.setForeground(new java.awt.Color(255, 255, 255));
        jTextField134.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField134.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField134, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 540, 117, -1));

        jTextField71.setBackground(new java.awt.Color(26, 27, 27));
        jTextField71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField71.setForeground(new java.awt.Color(255, 255, 255));
        jTextField71.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField71.setText("50 % payable upon delivery of the vehicle and 50% upon return");
        jTextField71.setBorder(null);
        jPanel3.add(jTextField71, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 541, 363, -1));

        jTextField72.setBackground(new java.awt.Color(102, 102, 102));
        jTextField72.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField72.setForeground(new java.awt.Color(255, 255, 255));
        jTextField72.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField72.setText("D. AUTHORIZED DRIVERS");
        jTextField72.setBorder(null);
        jPanel3.add(jTextField72, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 765, -1));

        jTextField78.setBackground(new java.awt.Color(26, 27, 27));
        jTextField78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField78.setForeground(new java.awt.Color(255, 255, 255));
        jTextField78.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField78.setText("The Lessee agrees that its qualified and authorized drivers shall not engage in any behavior that may endanger themselves,");
        jTextField78.setBorder(null);
        jPanel3.add(jTextField78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 750, -1));

        jTextField79.setBackground(new java.awt.Color(26, 27, 27));
        jTextField79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField79.setForeground(new java.awt.Color(255, 255, 255));
        jTextField79.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField79.setText("If the authorized drivers is under in the influence of alcohol and an accident occur THE COMPANY HAVE NO RESPONSIBILITY.");
        jTextField79.setBorder(null);
        jPanel3.add(jTextField79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, 750, -1));

        jTextField80.setBackground(new java.awt.Color(102, 102, 102));
        jTextField80.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField80.setForeground(new java.awt.Color(255, 255, 255));
        jTextField80.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField80.setText("E. RESPONSIBILITY FOR LOSS OF OR DAMAGE TO THE VEHICLE");
        jTextField80.setBorder(null);
        jPanel3.add(jTextField80, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 770, 765, -1));

        jTextField86.setBackground(new java.awt.Color(26, 27, 27));
        jTextField86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField86.setForeground(new java.awt.Color(255, 255, 255));
        jTextField86.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField86.setText("LESSEE is responsible for returning the vehicle with as much fuel in the tank as when received. If the LESSEE returned the");
        jTextField86.setBorder(null);
        jPanel3.add(jTextField86, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 810, 685, -1));

        jTextField87.setBackground(new java.awt.Color(26, 27, 27));
        jTextField87.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField87.setForeground(new java.awt.Color(255, 255, 255));
        jTextField87.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField87.setText("vehicle with less fuel than we supplied it with, a fuel charge applies. LESSOR determines how much fuel is needed by the");
        jTextField87.setBorder(null);
        jPanel3.add(jTextField87, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 832, -1, -1));

        jTextField88.setBackground(new java.awt.Color(26, 27, 27));
        jTextField88.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField88.setForeground(new java.awt.Color(255, 255, 255));
        jTextField88.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField88.setText("refilling of the tank. AT THE TIME OF RETURN, LESSEE WILL NOT RECEIVE CREDIT FOR ANY FUEL REMAINING IN THE VEHICLE.");
        jTextField88.setBorder(null);
        jPanel3.add(jTextField88, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 854, 750, -1));

        jTextField89.setBackground(new java.awt.Color(26, 27, 27));
        jTextField89.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField89.setForeground(new java.awt.Color(255, 255, 255));
        jTextField89.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField89.setText("A total deposit of ₱5,000 shall be collected as a fuel charge for renting luxury vehicles and ₱2000 for non luxury vehicles. Upon");
        jTextField89.setBorder(null);
        jPanel3.add(jTextField89, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 876, 750, -1));

        jTextField90.setBackground(new java.awt.Color(26, 27, 27));
        jTextField90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField90.setForeground(new java.awt.Color(255, 255, 255));
        jTextField90.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField90.setText("return of the vehicle, if the actual cost of refueling to a full tank is less than ₱5,000, the remaining balance will be refunded to ");
        jTextField90.setBorder(null);
        jPanel3.add(jTextField90, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 898, 750, -1));

        jTextField91.setBackground(new java.awt.Color(26, 27, 27));
        jTextField91.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField91.setForeground(new java.awt.Color(255, 255, 255));
        jTextField91.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField91.setText("Lessee.");
        jTextField91.setBorder(null);
        jPanel3.add(jTextField91, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 920, 228, -1));

        jTextField92.setBackground(new java.awt.Color(102, 102, 102));
        jTextField92.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField92.setForeground(new java.awt.Color(255, 255, 255));
        jTextField92.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField92.setText("G. LIABILITY.");
        jTextField92.setBorder(null);
        jPanel3.add(jTextField92, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 954, 765, -1));

        jTextField93.setBackground(new java.awt.Color(26, 27, 27));
        jTextField93.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField93.setForeground(new java.awt.Color(255, 255, 255));
        jTextField93.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField93.setText("If the rented vehicle has been lost/car nabbed or in any case that the renter can't return the vehicle then the renter is the one");
        jTextField93.setBorder(null);
        jPanel3.add(jTextField93, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1001, 750, -1));

        jTextField94.setBackground(new java.awt.Color(26, 27, 27));
        jTextField94.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField94.setForeground(new java.awt.Color(255, 255, 255));
        jTextField94.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField94.setText("responsible for the whole amount of the rented vehicle, it's based on the selling price of the vehicle at the time of the lost or");
        jTextField94.setBorder(null);
        jPanel3.add(jTextField94, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1023, 750, -1));

        jTextField95.setBackground(new java.awt.Color(26, 27, 27));
        jTextField95.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField95.setForeground(new java.awt.Color(255, 255, 255));
        jTextField95.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField95.setText("accident. Total wreck-if the rented vehicle has been damaged severely that it can't be fixed, the renter is responsible in paying");
        jTextField95.setBorder(null);
        jPanel3.add(jTextField95, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1045, 750, -1));

        jTextField96.setBackground(new java.awt.Color(26, 27, 27));
        jTextField96.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField96.setForeground(new java.awt.Color(255, 255, 255));
        jTextField96.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField96.setText("the whole amount of the rented vehicle based on the selling price on the time of the accident/police report date.");
        jTextField96.setBorder(null);
        jPanel3.add(jTextField96, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1067, 750, -1));

        jTextField97.setBackground(new java.awt.Color(102, 102, 102));
        jTextField97.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField97.setForeground(new java.awt.Color(255, 255, 255));
        jTextField97.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField97.setText("H. CANCELATIONS OF BOOKINGS");
        jTextField97.setBorder(null);
        jPanel3.add(jTextField97, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1101, 765, -1));

        jTextField98.setBackground(new java.awt.Color(26, 27, 27));
        jTextField98.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField98.setForeground(new java.awt.Color(255, 255, 255));
        jTextField98.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField98.setText("If the customer cancels the booking within 24 hours of the rental transaction, a full refund will be granted. However, if the");
        jTextField98.setBorder(null);
        jPanel3.add(jTextField98, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1148, 750, -1));

        jTextField99.setBackground(new java.awt.Color(26, 27, 27));
        jTextField99.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField99.setForeground(new java.awt.Color(255, 255, 255));
        jTextField99.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField99.setText("cancellation occurs after 24 hours from the time of booking, a cancellation fee will apply, but the remaining amount will still be ");
        jTextField99.setBorder(null);
        jPanel3.add(jTextField99, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1170, 750, -1));

        jTextField100.setBackground(new java.awt.Color(26, 27, 27));
        jTextField100.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField100.setForeground(new java.awt.Color(255, 255, 255));
        jTextField100.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField100.setText("refunded.");
        jTextField100.setBorder(null);
        jPanel3.add(jTextField100, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1192, 228, -1));

        jTextField101.setBackground(new java.awt.Color(102, 102, 102));
        jTextField101.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField101.setForeground(new java.awt.Color(255, 255, 255));
        jTextField101.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField101.setText("I. TERMS");
        jTextField101.setBorder(null);
        jPanel3.add(jTextField101, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1226, 765, -1));

        jTextField102.setBackground(new java.awt.Color(26, 27, 27));
        jTextField102.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField102.setForeground(new java.awt.Color(255, 255, 255));
        jTextField102.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField102.setText("The term of this contract commences from the date of the DELIVERY AND ACCEPTANCE of the \"vehicle\" to the LESSEE");
        jTextField102.setBorder(null);
        jPanel3.add(jTextField102, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1273, 750, -1));

        jTextField103.setBackground(new java.awt.Color(26, 27, 27));
        jTextField103.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField103.setForeground(new java.awt.Color(255, 255, 255));
        jTextField103.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField103.setText("And ends on the date specified. Such Delivery and Acceptance Date for all purpose be all conclusively presumed to be the actual,");
        jTextField103.setBorder(null);
        jPanel3.add(jTextField103, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1295, 714, -1));

        jTextField104.setBackground(new java.awt.Color(26, 27, 27));
        jTextField104.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField104.setForeground(new java.awt.Color(255, 255, 255));
        jTextField104.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField104.setText("true and correct date of Delivery and Acceptance by the LESSEE. In case of termination/cancellation of this contract agreement");
        jTextField104.setBorder(null);
        jPanel3.add(jTextField104, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1317, -1, -1));

        jTextField105.setBackground(new java.awt.Color(26, 27, 27));
        jTextField105.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField105.setForeground(new java.awt.Color(255, 255, 255));
        jTextField105.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField105.setText("by the LESSEE before agreed end date of this contract, the LESSOR has all the rights to forfeit any amount unused or paid by the");
        jTextField105.setBorder(null);
        jPanel3.add(jTextField105, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1339, 714, -1));

        jTextField106.setBackground(new java.awt.Color(26, 27, 27));
        jTextField106.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField106.setForeground(new java.awt.Color(255, 255, 255));
        jTextField106.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField106.setText("LESSEE this will all fall as penalty and damage to the LESSOR by the LESSEE.");
        jTextField106.setBorder(null);
        jPanel3.add(jTextField106, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1361, -1, -1));

        jTextField137.setBackground(new java.awt.Color(26, 27, 27));
        jTextField137.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField137.setForeground(new java.awt.Color(255, 255, 255));
        jTextField137.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField137.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        jPanel3.add(jTextField137, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 1467, 201, -1));

        jTextField136.setBackground(new java.awt.Color(26, 27, 27));
        jTextField136.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField136.setForeground(new java.awt.Color(255, 255, 255));
        jTextField136.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField136.setText("______________________________________________________");
        jTextField136.setBorder(null);
        jPanel3.add(jTextField136, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1468, 31, -1));

        jTextField138.setBackground(new java.awt.Color(26, 27, 27));
        jTextField138.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField138.setForeground(new java.awt.Color(255, 255, 255));
        jTextField138.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField138.setText("___________________________________________________");
        jTextField138.setBorder(null);
        jPanel3.add(jTextField138, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 1468, 24, -1));

        jTextField107.setBackground(new java.awt.Color(26, 27, 27));
        jTextField107.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField107.setForeground(new java.awt.Color(255, 255, 255));
        jTextField107.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField107.setText("LESSEE");
        jTextField107.setBorder(null);
        jPanel3.add(jTextField107, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1491, 271, -1));

        jTextField110.setBackground(new java.awt.Color(26, 27, 27));
        jTextField110.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField110.setForeground(new java.awt.Color(255, 255, 255));
        jTextField110.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField110.setText("signature over printed name");
        jTextField110.setBorder(null);
        jPanel3.add(jTextField110, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1517, 271, -1));

        jTextField121.setBackground(new java.awt.Color(26, 27, 27));
        jTextField121.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField121.setForeground(new java.awt.Color(255, 255, 255));
        jTextField121.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField121.setText("__________________RENT - A - CARZ_____________________");
        jTextField121.setBorder(null);
        jPanel3.add(jTextField121, new org.netbeans.lib.awtextra.AbsoluteConstraints(462, 1467, 273, -1));

        jTextField123.setBackground(new java.awt.Color(26, 27, 27));
        jTextField123.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField123.setForeground(new java.awt.Color(255, 255, 255));
        jTextField123.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField123.setText("LESSOR");
        jTextField123.setBorder(null);
        jPanel3.add(jTextField123, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 1489, 286, -1));

        jTextField124.setBackground(new java.awt.Color(26, 27, 27));
        jTextField124.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField124.setForeground(new java.awt.Color(255, 255, 255));
        jTextField124.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField124.setText("signature over printed name");
        jTextField124.setBorder(null);
        jPanel3.add(jTextField124, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 1515, 286, -1));

        jTextField126.setBackground(new java.awt.Color(26, 27, 27));
        jTextField126.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField126.setForeground(new java.awt.Color(255, 255, 255));
        jTextField126.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField126.setText("DATE:");
        jTextField126.setBorder(null);
        jPanel3.add(jTextField126, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 1605, 41, -1));

        jTextField127.setBackground(new java.awt.Color(26, 27, 27));
        jTextField127.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField127.setForeground(new java.awt.Color(255, 255, 255));
        jTextField127.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField127.setBorder(null);
        jPanel3.add(jTextField127, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 1605, 245, -1));

        jTextField70.setBackground(new java.awt.Color(26, 27, 27));
        jTextField70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField70.setForeground(new java.awt.Color(255, 255, 255));
        jTextField70.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField70.setText("If the vehicle is not returned on the due date/time without at least 24 hours’ prior notice, the rental company will call the customer");
        jTextField70.setBorder(null);
        jPanel3.add(jTextField70, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 410, -1, 17));

        jTextField75.setBackground(new java.awt.Color(26, 27, 27));
        jTextField75.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField75.setForeground(new java.awt.Color(255, 255, 255));
        jTextField75.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField75.setText("every 12 hours, and if there is still no contact after 24 hours, the company may record the incident and file a report");
        jTextField75.setBorder(null);
        jPanel3.add(jTextField75, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 433, -1, 17));

        jTextField84.setBackground(new java.awt.Color(26, 27, 27));
        jTextField84.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField84.setForeground(new java.awt.Color(255, 255, 255));
        jTextField84.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField84.setText("passengers, or third parties. In such cases, THE COMPANY HAVE NO RESPONSIBILITY for any resulting damages or liabilities.");
        jTextField84.setBorder(null);
        jPanel3.add(jTextField84, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 750, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, 1640));

        jScrollPane1.setViewportView(jPanel2);

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(102, 102, 102));
        rSMaterialButtonRectangle1.setBorder(null);
        rSMaterialButtonRectangle1.setText("PRINT AGREEMENT");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
Color originalBg = jPanel3.getBackground();
Color originalFg = jPanel3.getForeground();

// Save original label backgrounds
Color bg144 = jTextField61.getBackground();
Color bg143 = jTextField64.getBackground();
Color bg162 = jTextField68.getBackground();
Color bg163 = jTextField72.getBackground();
Color bg164 = jTextField80.getBackground();
Color bg165 = jTextField92.getBackground();
Color bg166 = jTextField97.getBackground();
Color bg167 = jTextField101.getBackground();

try {
// Set print-friendly colors
jPanel3.setBackground(Color.WHITE);
setComponentColors(jPanel3, Color.BLACK, Color.WHITE);
setComponentFontsBold(jPanel3); // Optional

// Set specific labels to gray
Color printGray = new Color(200, 200, 200); // Light gray
jTextField61.setOpaque(true);
jTextField64.setOpaque(true);
jTextField68.setOpaque(true);
jTextField72.setOpaque(true);
jTextField80.setOpaque(true);
jTextField92.setOpaque(true);
jTextField97.setOpaque(true);
jTextField101.setOpaque(true);
jTextField61.setBackground(printGray);
jTextField64.setBackground(printGray);
jTextField68.setBackground(printGray);
jTextField72.setBackground(printGray);
jTextField80.setBackground(printGray);
jTextField92.setBackground(printGray);
jTextField97.setBackground(printGray);
jTextField101.setBackground(printGray);

printPanelFitWidthMultiPage(jPanel3);

} finally {
// Restore original colors
jPanel3.setBackground(originalBg);
setComponentColors(jPanel3, originalFg, originalBg);

// Restore original label backgrounds
jTextField61.setBackground(bg144);
jTextField64.setBackground(bg143);
jTextField68.setBackground(bg162);
jTextField72.setBackground(bg163);
jTextField80.setBackground(bg164);
jTextField92.setBackground(bg165);
jTextField97.setBackground(bg166);
jTextField101.setBackground(bg167);
}

    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField101;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField105;
    private javax.swing.JTextField jTextField106;
    private javax.swing.JTextField jTextField107;
    private javax.swing.JTextField jTextField108;
    private javax.swing.JTextField jTextField109;
    private javax.swing.JTextField jTextField110;
    private javax.swing.JTextField jTextField111;
    private javax.swing.JTextField jTextField112;
    private javax.swing.JTextField jTextField113;
    private javax.swing.JTextField jTextField114;
    private javax.swing.JTextField jTextField115;
    private javax.swing.JTextField jTextField116;
    private javax.swing.JTextField jTextField117;
    private javax.swing.JTextField jTextField118;
    private javax.swing.JTextField jTextField119;
    private javax.swing.JTextField jTextField120;
    private javax.swing.JTextField jTextField121;
    private javax.swing.JTextField jTextField122;
    private javax.swing.JTextField jTextField123;
    private javax.swing.JTextField jTextField124;
    private javax.swing.JTextField jTextField125;
    private javax.swing.JTextField jTextField126;
    private javax.swing.JTextField jTextField127;
    private javax.swing.JTextField jTextField128;
    private javax.swing.JTextField jTextField131;
    private javax.swing.JTextField jTextField134;
    private javax.swing.JTextField jTextField136;
    private javax.swing.JTextField jTextField137;
    private javax.swing.JTextField jTextField138;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private javax.swing.JTextField jTextField99;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    // End of variables declaration//GEN-END:variables
}
