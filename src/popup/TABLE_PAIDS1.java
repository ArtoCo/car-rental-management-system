/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import com.formdev.flatlaf.FlatClientProperties;
import customer.ServiceCustomer;
import customers.CheckBoxTableHeaderRenderer;
import customers.ModelCustomer1;
import customers.TableHeaderAlignment;
import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;

/**
 *
 * @author ArtoCO
 */
public class TABLE_PAIDS1 extends javax.swing.JPanel {
    private final List<ModelCustomer1> deletedRows = new ArrayList<>();
    private boolean dueRefreshRunning;



    /**
     * Creates new form TABLE_PAIDS
     */
    public TABLE_PAIDS1() {
        initComponents();
        init();
        loadDueAccountsAsync();
        startAutoRefresh();
        
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        
                
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

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


private void loadDueAccountsAsync() {
    if (dueRefreshRunning) {
        return;
    }
    dueRefreshRunning = true;

    new SwingWorker<List<ModelCustomer1>, Void>() {
        @Override
        protected List<ModelCustomer1> doInBackground() throws Exception {
            return new ServiceCustomer().getDueAfterOneHour();
        }

        @Override
        protected void done() {
            try {
                populateDueAccountsTable(get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dueRefreshRunning = false;
            }
        }
    }.execute();
}

private void populateDueAccountsTable(List<ModelCustomer1> dueList) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    for (ModelCustomer1 c : dueList) {
        Object pickupDate = (c.geInDate() == null) ? "" : df.format(c.geInDate());
        Object returnDate = (c.getOutDate() == null) ? "" : df.format(c.getOutDate());
        String paymentStatus = c.getPaymentType() == null ? "" : c.getPaymentType().toString().trim();
        Object displayBalance = paymentStatus.equalsIgnoreCase("FULL PAYMENT") ? 0 : c.getTotalPayment();

        model.addRow(new Object[]{
            false, c.getCustomerId(), c.getFirstname1(), c.getLastname1(), c.getPhoneNumber1(),
            c.getCarName(), pickupDate, c.getPickup_time(), returnDate, c.getReturned_time(),
            paymentStatus, displayBalance, c.getDueHours(), c.getDueAmount(), c.getTotalPay()
        });
    }
}

private void loadDueAccountsToTable() {
    try {
        ServiceCustomer service = new ServiceCustomer();
        List<ModelCustomer1> dueList = service.getDueAfterOneHour(); // ✅ FIX

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (ModelCustomer1 c : dueList) {
    Object pickupDate = (c.geInDate() == null) ? "" : df.format(c.geInDate());
    Object returnDate  = (c.getOutDate() == null) ? "" : df.format(c.getOutDate());

    String paymentStatus = c.getPaymentType() == null ? "" : c.getPaymentType().toString().trim();
    Object displayBalance = paymentStatus.equalsIgnoreCase("FULL PAYMENT") ? 0 : c.getTotalPayment();

    model.addRow(new Object[]{
        false,
        c.getCustomerId(),
        c.getFirstname1(),
        c.getLastname1(),
        c.getPhoneNumber1(),
        c.getCarName(),
        pickupDate,
        c.getPickup_time(),
        returnDate,
        c.getReturned_time(),
        paymentStatus,        // PAYMENT STATUS
        displayBalance,       // ✅ REMAINING BALANCE shows 0 if FULL PAYMENT
        c.getDueHours(),
        c.getDueAmount(),
        c.getTotalPay()
    });
}

    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void startAutoRefresh() {
    new javax.swing.Timer(60_000, e -> loadDueAccountsAsync()).start();
}

private void searchAndLoadTable() {
    try {
        String keyword = jTextField1.getText().trim();

        ServiceCustomer service = new ServiceCustomer();
        List<ModelCustomer1> list;

        if (keyword.isEmpty()) {
            // show only overdue accounts (>= 1 hour late)
            list = service.getDueAfterOneHour();
        } else {
            // search ONLY within overdue accounts (>= 1 hour late)
            list = service.searchDueAfterOneHour(keyword);
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (ModelCustomer1 c : list) {
    Object pickupDate = (c.geInDate() == null) ? "" : df.format(c.geInDate());
    Object returnDate = (c.getOutDate() == null) ? "" : df.format(c.getOutDate());

    String paymentStatus = c.getPaymentType() == null ? "" : c.getPaymentType().toString().trim();
    Object displayBalance = paymentStatus.equalsIgnoreCase("FULL PAYMENT") ? 0 : c.getTotalPayment();

    model.addRow(new Object[]{
        false,
        c.getCustomerId(),
        c.getFirstname1(),
        c.getLastname1(),
        c.getPhoneNumber1(),
        c.getCarName(),
        pickupDate,
        c.getPickup_time(),
        returnDate,
        c.getReturned_time(),
        paymentStatus,        // PAYMENT STATUS
        displayBalance,       // ✅ REMAINING BALANCE shows 0 if FULL PAYMENT
        c.getDueHours(),
        c.getDueAmount(),
        c.getTotalPay()
    });
}

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private Integer getSingleCheckedCustomerId() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    Integer foundId = null;
    int count = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {   // checkbox column 0
            count++;

            Object v = model.getValueAt(i, 1);               // customer id column 1 (#)
            int id;
            if (v instanceof Number) {
                id = ((Number) v).intValue();
            } else {
                id = Integer.parseInt(v.toString());
            }
            foundId = id;
        }
    }

    if (count == 0) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a customer first");
        return null;
    }
    if (count > 1) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select only one customer");
        return null;
    }
    return foundId;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        rSMaterialButtonRectangle14 = new rojerusan.RSMaterialButtonRectangle();

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "#", "FIRSTNAME", "LASTNAME", "PHONE NUMBER", "CARS NAME", "PICKUP DATE", "PICKUP TIME", "RETURN DATE", "RETURNED TIME", "PAYMENT STATUS", "REMAINING BALANCE", "DUE HOURS", "DUE AMOUNTS", "TOTAL AMOUNT TO PAY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(110);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(180);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2362, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 2362, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jScrollPane2.setViewportView(jPanel2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 48, 1140, 510));

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
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 12, 240, 25));

        rSMaterialButtonRectangle14.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle14.setBorder(null);
        rSMaterialButtonRectangle14.setText("PAY BALANCE");
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
        jPanel1.add(rSMaterialButtonRectangle14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 6, 120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        searchAndLoadTable();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void rSMaterialButtonRectangle14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle14MouseClicked

    private void rSMaterialButtonRectangle14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14ActionPerformed
        // TODO add your handling code here:
      
        try {
        Integer customerId = getSingleCheckedCustomerId();   // your helper (checkbox check)
        if (customerId == null) return;

        ServiceCustomer service = new ServiceCustomer();
        ModelCustomer1 data = service.findById(customerId);  // make sure findById returns the full data
        AppSession.setPaymentStatus(data.getPaymentType() == null ? "" : data.getPaymentType().toString());

        // ✅ store temp session (so next panels can display without DB)
AppSession.setCustomerId(customerId);
AppSession.setCustomerName(data.getFirstname1(), data.getLastname1());
AppSession.setCustomerAddress(data.getPrimaryAddress1());
AppSession.setCustomerPhone(data.getPhoneNumber1());

AppSession.setCustomerCarNameType(data.getCarName(), data.getCarType());
AppSession.setplateno(data.getplate_number());

// dates -> store as String
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
AppSession.setCheckInDate(data.geInDate() == null ? "" : df.format(data.geInDate()));
AppSession.setCheckOutDate(data.getOutDate() == null ? "" : df.format(data.getOutDate()));

// money/due -> store as String
AppSession.setOriginalBalance(String.valueOf(data.getTotalPayment()));
AppSession.setDueHours(data.getDueHours());
AppSession.setDueAmount(String.valueOf(data.getDueAmount()));

        if (data == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Customer not found");
            return;
        }

        PAYMENT payment = new PAYMENT();
        payment.setSelectedCustomerId(customerId);

        // refresh table after successful save
        payment.setAfterPaidCallback(() -> {
            loadDueAccountsToTable();
            searchAndLoadTable();
        });

        String actions[] = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(payment, "CHOOSE PAYMENT METHOD", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Failed to open payment window");
    }

     

    }//GEN-LAST:event_rSMaterialButtonRectangle14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle14;
    // End of variables declaration//GEN-END:variables
}
