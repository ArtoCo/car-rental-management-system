package zcomponents;

import ADMIN.Dashboard;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
public class PanelLoginUser extends javax.swing.JPanel {
    
    private char defaultEchoChar;
    private boolean passwordVisible = false;


    public PanelLoginUser() {
        initComponents();
        setOpaque(false);
        initValidation(); 
        defaultEchoChar = txtPass.getEchoChar();
        jLabel3.setVisible(false);
        jLabel4.setVisible(true);

    }
    
    private void initValidation() {

    // Username field
    txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            if (!txtUser.getText().trim().isEmpty()) {
                txtUser.setHelperText("");
            }
        }
    });

    // Password field
    txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            if (txtPass.getPassword().length > 0) {
                txtPass.setHelperText("");
            }
        }
    });
}

    public boolean isLoginValid() {
    String user = txtUser.getText().trim();
    return config.AppConfig.isUserLogin(user, txtPass.getPassword());
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmd = new zEFFECT.Button();
        txtUser = new zEFFECT.TextField();
        txtPass = new zEFFECT.PasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(350, 450));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(27, 26, 26));
        jLabel1.setFont(new java.awt.Font("sansserif", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 13, 350, 180));

        cmd.setBackground(new java.awt.Color(255, 0, 0));
        cmd.setForeground(new java.awt.Color(255, 255, 255));
        cmd.setText("LOGIN");
        cmd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdActionPerformed(evt);
            }
        });
        add(cmd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 289, 45));

        txtUser.setBackground(new java.awt.Color(0, 0, 0));
        txtUser.setLabelText("User Name");
        add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 201, 255, -1));

        txtPass.setBackground(new java.awt.Color(0, 0, 0));
        txtPass.setLabelText("Password");
        add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 255, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_user_20px_1.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 219, 22, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_invisible_20px_1.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 260, -1, 59));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_20px_1.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 260, -1, 59));
    }// </editor-fold>//GEN-END:initComponents

    private void cmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdActionPerformed
 
    }//GEN-LAST:event_cmdActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        if (!passwordVisible) {
            txtPass.setEchoChar((char) 0); // reveal password
            passwordVisible = true;

            jLabel4.setVisible(false); // eye off
            jLabel3.setVisible(true);  // eye on
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        if (passwordVisible) {
            txtPass.setEchoChar(defaultEchoChar); // back to *
            passwordVisible = false;

            jLabel3.setVisible(false); // eye on
            jLabel4.setVisible(true);  // eye off
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    public void addEventLogin(ActionListener event) {
        cmd.addActionListener(event);
    }

    public boolean checkUser() {
    boolean action = true;

    // Username check
    if (txtUser.getText().trim().isEmpty()) {
        txtUser.setHelperText("Please input user name");
        action = false;
    } else {
        txtUser.setHelperText(""); // ✅ CLEAR message
    }

    // Password check
    if (String.valueOf(txtPass.getPassword()).trim().isEmpty()) {
        txtPass.setHelperText("Please input password");
        action = false;
    } else {
        txtPass.setHelperText(""); // ✅ CLEAR message
    }

    return action;
}


    public String getUserName() {
        return txtUser.getText().trim();
    }

    public String getPassword() {
        return String.valueOf(txtPass.getPassword());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private zEFFECT.Button cmd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private zEFFECT.PasswordField txtPass;
    private zEFFECT.TextField txtUser;
    // End of variables declaration//GEN-END:variables
}
