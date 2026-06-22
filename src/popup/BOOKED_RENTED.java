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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author ArtoCO
 */
public class BOOKED_RENTED extends javax.swing.JPanel {

    /**
     * Creates new form BOOKED_RENTED
     */
    public BOOKED_RENTED() {
        initComponents();
        loadbookedrented1();
        init();
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        initSearchFeature();
        
    }
    
    private void init() {
        
         loadbookedrented1();

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
    
    
    private void loadbookedrented1() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // clear old rows

    ServiceCustomer service = new ServiceCustomer();
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM-d-yyyy"); // JULY/7/2025 format

    try {
        List<ModelCustomer1> list = service.getbookedrented1(); 

        for (ModelCustomer1 c : list) {
            Object[] row = new Object[]{
                false, // checkbox default
                c.getCustomerId(),               // ID
                c.getFirstname1(),               // FIRSTNAME
                c.getLastname1(),                // LASTNAME
                c.getPhoneNumber1(),             // PHONE NUMBER
                c.getCarType() + " " + c.getCarName(), // CARS NAME
                sdf.format(c.geInDate()),        // CHECK-INS DATE
                sdf.format(c.getOutDate()),      // CHECK-OUT DATE
                c.getStatus()
            };
            model.addRow(row);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading FULL PAYMENT data:\n" + ex.getMessage());
    }
}
    
    
    
    
    private void initSearchFeature() {
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
            String keyword = jTextField1.getText().trim();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);  // clear previous rows
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM-d-yyyy");

            try {
                List<ModelCustomer1> list;
                if (keyword.isEmpty()) {
                    // 🔁 If search box is empty, reload all BOOKED/RENTED customers
                    list = new ServiceCustomer().getbookedrented1();
                } else {
                    // 🔍 Filter via search logic
                    list = new ServiceCustomer().search(keyword);
                }

                for (ModelCustomer1 c : list) {
                    model.addRow(new Object[]{
                        false,
                        c.getCustomerId(),
                        c.getFirstname1(),
                        c.getLastname1(),
                        c.getPhoneNumber1(),
                        c.getCarType() + " " + c.getCarName(),
                        (c.geInDate() != null ? sdf.format(c.geInDate()) : ""),
                        (c.getOutDate() != null ? sdf.format(c.getOutDate()) : ""),
                        c.getStatus()
                    });
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Search failed: " + ex.getMessage());
            }
        }
    });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "#", "FIRSTNAME", "LASTNAME", "PHONE NUMBER", "CARS NAME", "PICKUP DATES", "RETURN DATES", "STATUS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(200);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 1140, 510));

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
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 10, 240, 25));

        jLabel5.setBackground(new java.awt.Color(27, 26, 26));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 170, 30));

        jLabel4.setBackground(new java.awt.Color(27, 26, 26));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 130, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
