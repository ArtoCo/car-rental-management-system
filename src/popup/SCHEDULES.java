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
import employees.ModelEmployee;
import jSCHED.ModelSched;
import jSCHED.ServiceSched;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;

/**
 *
 * @author ArtoCO
 */
public class SCHEDULES extends javax.swing.JPanel {
    
    private final List<ModelSched> deletedRows = new ArrayList<>();


    /**
     * Creates new form SCHEDULES
     */
    public SCHEDULES() {
        initComponents();
        init();
        loadTableData();
        initSearchFeature();   
 
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
    
    Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime(); // Update the time label
            }
        });
        timer.start();
        updateDate(); 
}
    
    private void updateTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm : ss");
        String time = sdf.format(now.getTime());
        jLabel5.setText(time);
    }

    private void updateDate() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        String date = sdf.format(now.getTime());
        jLabel4.setText(date);
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
    
public void loadTableData() {
    try {
        ServiceSched service = new ServiceSched();
        List<ModelSched> list = service.getAll();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing data

        int rowNum = 1;
        for (ModelSched sched : list) {
            model.addRow(sched.toTableRow(rowNum++));
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load table data: " + ex.getMessage());
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
            try {
                List<ModelSched> filtered = new ServiceSched().search(keyword);  // Uses your search method
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);  // Clear previous rows

                int rowNum = 1;
                for (ModelSched data : filtered) {
                    model.addRow(data.toTableRow(rowNum++));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SCHEDULES.this, "Search failed: " + ex.getMessage());
            }
        }
    });
}

private List<ModelSched> getSelectedData() {
    List<ModelSched> list = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    for (int i = 0; i < model.getRowCount(); i++) {
        Object checkValue = model.getValueAt(i, 0); // checkbox column
        boolean isChecked = checkValue instanceof Boolean && (Boolean) checkValue;

        if (isChecked) {
            try {
                int id = Integer.parseInt(model.getValueAt(i, 1).toString()); // ID is column 1
                ModelSched sched = new ServiceSched().getById(id);
                if (sched != null) {
                    list.add(sched);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    return list;
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
        buttonAction4 = new employees.ButtonAction();
        buttonAction2 = new employees.ButtonAction();
        buttonAction3 = new employees.ButtonAction();

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "#", "FIRSTNAME", "LASTNAME", "PHONE NUMBER", "CARS NAME", "CHECK-INS TIME", "PAYMENTS", "TOTAL PAYMENTS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, true, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(250);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(250);
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

        buttonAction4.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction4.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction4.setText("EDIT");
        buttonAction4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction4ActionPerformed(evt);
            }
        });
        jPanel1.add(buttonAction4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        buttonAction2.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction2.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction2.setText("RETRIVE");
        buttonAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction2ActionPerformed(evt);
            }
        });
        jPanel1.add(buttonAction2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        buttonAction3.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction3.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction3.setText("DELETE");
        buttonAction3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction3ActionPerformed(evt);
            }
        });
        jPanel1.add(buttonAction3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

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

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed


     // EDIT THEN UPDATE schedule data
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    ServiceSched service = new ServiceSched();

    List<Integer> selectedRows = new ArrayList<>();
    List<String> phoneNumbersToEdit = new ArrayList<>();

    // ✅ Step 1: Collect checked rows (checkbox in column 0, phone number in column 4)
    for (int i = 0; i < model.getRowCount(); i++) {
        Boolean checked = (Boolean) model.getValueAt(i, 0); // Checkbox
        if (checked != null && checked) {
            selectedRows.add(i);
            phoneNumbersToEdit.add((String) model.getValueAt(i, 4)); // phoneNO column
        }
    }

    // ✅ Step 2: Ensure only one selected
    if (selectedRows.isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select a row to edit");
        return;
    }

    if (selectedRows.size() > 1) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select only one row to edit");
        return;
    }

    try {
        String phoneNumber = phoneNumbersToEdit.get(0);
        ModelSched data = service.findByPhoneNumber(phoneNumber);

        if (data != null) {
            // ✅ Open edit form with data
            SCHED_E editForm = new SCHED_E();
            editForm.setEditData(data); // Pre-fill form fields

            DefaultOption option = new DefaultOption() {
                @Override
                public boolean closeWhenClickOutside() {
                    return true;
                }
            };

            String[] actions = {"Cancel", "Update"};

            GlassPanePopup.showPopup(new SimplePopupBorder(editForm, "Edit Schedule [" + data.getfirstname() + "]", actions, (dialog, index) -> {
                if (index == 1) {
                    try {
                        ModelSched updated = editForm.getData();
                        updated.setid(data.getid()); // Keep ID the same
                        service.edit(updated);       // Update DB
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Schedule Updated");
                        dialog.closePopup();
                        loadTableData();             // Reload table
                    } catch (Exception e) {
                        e.printStackTrace();
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Update Failed");
                    }
                } else {
                    dialog.closePopup();
                }
            }), option);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_buttonAction4ActionPerformed

    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed

        int restoredCount = 0;
    ServiceSched service = new ServiceSched();

    for (int i = deletedRows.size() - 1; i >= 0; i--) {
        ModelSched sched = deletedRows.get(i);
        try {
            service.restore(sched);  // Replace: service.create(sched);
            deletedRows.remove(i);
            restoredCount++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    if (restoredCount > 0) {
        loadTableData();
        Notifications.getInstance().show(
            Notifications.Type.SUCCESS, "Restored " + restoredCount + " item(s)");
    } else {
        Notifications.getInstance().show(
            Notifications.Type.WARNING, "No item to restore");
    }
    

    }//GEN-LAST:event_buttonAction2ActionPerformed

    private void buttonAction3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction3ActionPerformed

    int deletedCount = 0;
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    ServiceSched service = new ServiceSched();

    // Lists to collect phone numbers and their row indices
    List<Integer> selectedRows = new ArrayList<>();
    List<Integer> idsToDelete = new ArrayList<>();
    List<String> phoneNumbersToDelete = new ArrayList<>();

    // ✅ Step 1: Collect checked rows
    for (int i = 0; i < model.getRowCount(); i++) {
        Boolean checked = (Boolean) model.getValueAt(i, 0); // column 0 = checkbox
        if (checked != null && checked) {
            selectedRows.add(i);
            idsToDelete.add(Integer.parseInt(model.getValueAt(i, 1).toString()));
            phoneNumbersToDelete.add((String) model.getValueAt(i, 4)); // column 4 = phoneNO
        }
    }

    // ✅ Step 2: Confirm deletion
    if (selectedRows.isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please select to delete");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete selected item(s)?", "Confirm Delete",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // ✅ Step 3: Loop in reverse to avoid index shifting
        for (int i = selectedRows.size() - 1; i >= 0; i--) {
            int rowIndex = selectedRows.get(i);
            int id = idsToDelete.get(i);
            String phoneNumber = phoneNumbersToDelete.get(i);

            try {
                ModelSched deleted = service.findByIdAndPhoneNumber(id, phoneNumber);
                if (deleted != null && service.deleteByIdAndPhoneNumber(id, phoneNumber)) {
                    deletedRows.add(deleted); // Save for restore
                    model.removeRow(rowIndex); // Delete from JTable
                    deletedCount++;
                } else {
                    Notifications.getInstance().show(
                        Notifications.Type.ERROR,
                        "Delete blocked: record ID and phone number did not match exactly one schedule");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Notifications.getInstance().show(Notifications.Type.SUCCESS,
            "Deleted " + deletedCount + " item(s)");

    }
    }//GEN-LAST:event_buttonAction3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private employees.ButtonAction buttonAction2;
    private employees.ButtonAction buttonAction3;
    private employees.ButtonAction buttonAction4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
