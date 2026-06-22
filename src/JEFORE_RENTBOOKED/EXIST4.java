/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package JEFORE_RENTBOOKED;

import ADMIN.Customers;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import customer.ServiceCustomer;
import customers.CheckBoxTableHeaderRenderer;
import customers.ModelCustomer1;
import customers.TableHeaderAlignment;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;
import CAR_BRANDS.BMW;
import CAR_BRANDS.CHEVROLET;
import CAR_BRANDS.FORD;
import CAR_BRANDS.HONDA;
import CAR_BRANDS.HYUNDAI;
import CAR_BRANDS.MITSUBISHI;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import popup.ExistingCustomerBookingFlow;
import raven.popup.DefaultOption;
import raven.popup.component.SimplePopupBorder;

/**
 *
 * @author artoc
 */
public class EXIST4 extends javax.swing.JPanel {
    
    private ServiceCustomer service = new ServiceCustomer();

    /**
     * Creates new form EXIST1
     */
    public EXIST4() {
        initComponents();
        init();
        initLicenseFrame();
        loadLicenseData();
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

private void initLicenseFrame() {
    jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));

    jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            searchLicenseTable();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            searchLicenseTable();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            searchLicenseTable();
        }
    });
}

private void loadLicenseData() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM-d-yyyy");

    try {
        List<ModelCustomer1> list = service.getAll();

        for (ModelCustomer1 c : list) {
            model.addRow(new Object[] {
                false, // SELECT checkbox
                c.getCustomerId(), // #
                safe(c.getFirstname1()),
                safe(c.getLastname1()),
                c.getAge1(),
                safe(c.getPhoneNumber1()),
                safe(c.getPrimaryAddress1()),
                safe(c.getCarName()),
                safe(c.getCarType()),
                safe(c.gettransmission_type()),
                getLicenseStatus(c.getexpire_date()),
                c.getexpire_date() == null ? "" : sdf.format(c.getexpire_date()),
                safe(c.getagency_code()),
                safe(c.getdl_codes()),
                safe(c.getvehicle_category()),
                safe(c.getconditions()),
                c.getTransactionDate() == null ? "" : sdf.format(c.getTransactionDate())
            });
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error loading data:\n" + ex.getMessage());
    }
}

private void searchLicenseTable() {
    String keyword = jTextField1.getText().trim();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM-d-yyyy");

    try {
        List<ModelCustomer1> list;

        if (keyword.isEmpty()) {
            list = service.getAll();
        } else {
            list = service.searchLicenseData(keyword);
        }

        for (ModelCustomer1 c : list) {
            model.addRow(new Object[] {
                false, // SELECT checkbox
                c.getCustomerId(), // #
                safe(c.getFirstname1()),
                safe(c.getLastname1()),
                c.getAge1(),
                safe(c.getPhoneNumber1()),
                safe(c.getPrimaryAddress1()),
                safe(c.getCarName()),
                safe(c.getCarType()),
                safe(c.gettransmission_type()),
                getLicenseStatus(c.getexpire_date()),
                c.getexpire_date() == null ? "" : sdf.format(c.getexpire_date()),
                safe(c.getagency_code()),
                safe(c.getdl_codes()),
                safe(c.getvehicle_category()),
                safe(c.getconditions()),
                c.getTransactionDate() == null ? "" : sdf.format(c.getTransactionDate())
            });
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Search error:\n" + ex.getMessage());
    }
}

private String getLicenseStatus(java.sql.Date expireDate) {
    if (expireDate == null) {
        return "EXPIRE";
    }

    java.time.LocalDate today = java.time.LocalDate.now();
    java.time.LocalDate exp = expireDate.toLocalDate();

    return exp.isBefore(today) ? "EXPIRE" : "ACTIVE";
}

private String safe(String value) {
    return value == null ? "" : value;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        rSMaterialButtonRectangle14 = new rojerusan.RSMaterialButtonRectangle();

        setMinimumSize(new java.awt.Dimension(1052, 593));
        setPreferredSize(new java.awt.Dimension(1052, 593));

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

        jPanel4.setBackground(new java.awt.Color(27, 26, 26));

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECT", "#", "FIRST NAME", "LAST NAME", "AGE", "PHONE NUMBER", "PRIMARY ADDRESS", "VEHICLE NAME", "VEHICLE TYPE", "TRANSMISSION TYPE", "LICENSE STATUS", "LICENSE EXPIRE DATE", "AGENCY CODE", "DL CODES", "VEHICLE CATEGORY", "CONDITIONS", "TRANSACTION DATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setMinWidth(70);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(1).setMinWidth(70);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(2).setMinWidth(160);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(160);
            jTable1.getColumnModel().getColumn(3).setMinWidth(160);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(160);
            jTable1.getColumnModel().getColumn(4).setMinWidth(70);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(5).setMinWidth(150);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(6).setMinWidth(250);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(250);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(250);
            jTable1.getColumnModel().getColumn(7).setMinWidth(160);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(160);
            jTable1.getColumnModel().getColumn(8).setMinWidth(160);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(160);
            jTable1.getColumnModel().getColumn(9).setMinWidth(160);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(160);
            jTable1.getColumnModel().getColumn(10).setMinWidth(150);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(11).setMinWidth(170);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(12).setMinWidth(115);
            jTable1.getColumnModel().getColumn(12).setPreferredWidth(115);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(115);
            jTable1.getColumnModel().getColumn(13).setMinWidth(650);
            jTable1.getColumnModel().getColumn(13).setPreferredWidth(650);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(650);
            jTable1.getColumnModel().getColumn(14).setMinWidth(450);
            jTable1.getColumnModel().getColumn(14).setPreferredWidth(450);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(450);
            jTable1.getColumnModel().getColumn(15).setMinWidth(300);
            jTable1.getColumnModel().getColumn(15).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(300);
            jTable1.getColumnModel().getColumn(16).setMinWidth(150);
            jTable1.getColumnModel().getColumn(16).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(16).setMaxWidth(150);
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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("RECENT CUSTOMERS");

        rSMaterialButtonRectangle14.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle14.setBorder(null);
        rSMaterialButtonRectangle14.setText("RENT/BOOKED");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rSMaterialButtonRectangle14, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle14, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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
        
            ExistingCustomerBookingFlow.openBrandFromSelectedCustomer(
            this,
            jTable1,
            service,
            transmissionType -> {
                HYUNDAI hyun = new HYUNDAI();
                hyun.setLockedTransmissionType(transmissionType);
                return hyun;
            }
    );
    }//GEN-LAST:event_rSMaterialButtonRectangle14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle14;
    // End of variables declaration//GEN-END:variables
}
