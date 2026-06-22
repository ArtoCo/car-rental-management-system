package zcomponents;

import ADMIN.Dashboard;
import ADMIN.Login1;
import ADMIN.Schedule;
import ADMIN.User;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import zEFFECT.ModelUser;
import zEFFECT.Button;

public class PanelLoading extends javax.swing.JLayeredPane {

    public ModelUser getData() {
        return data;
    }

    private final Animator animator;
    private boolean slideLeft;
    private float animate;
    private boolean isMessage;
    private ModelUser data;
    private javax.swing.JFrame frameToOpen;

    public PanelLoading() {
        initComponents();
        loading.setVisible(true);
        profile.setVisible(false);
        message.setVisible(false);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (isMessage) {
                    message.setVisible(true);
                } else {
                    profile.setVisible(true);
                }
            }

            @Override
            public void timingEvent(float fraction) {
                if (isMessage) {
                    message.setAlpha(fraction);
                    loading.setAlpha(1f - fraction);
                } else {
                    profile.setAlpha(fraction);
                    loading.setAlpha(1f - fraction);
                }
                repaint();
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(0);
    }

    public void setAnimate(boolean slideLeft, float animate) {
        this.slideLeft = slideLeft;
        this.animate = animate;
    }


    public void addEventContinue(ActionListener event) {
        cmdContinue.addActionListener(event);
    }
    
    public void doneLogin(ModelUser data) {
        isMessage = false;
        this.data = data;
        pic.setIcon(data.getProfile());
        cmdContinue.setText("CONTINUE AS " + data.getUserName());
        animator.start();
    }


    public void showError(String ms) {
        lbMessage.setText(ms);
        isMessage = true;
        animator.start();
    }

    public void reset() {
        loading.setAlpha(1f);
        loading.setVisible(true);
        profile.setVisible(false);
        message.setVisible(false);
    }
    
    public void setFrameToOpen(javax.swing.JFrame frameToOpen) {
    this.frameToOpen = frameToOpen;
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profile = new zEFFECT.PanelTransparent();
        pic = new zEFFECT.ImageAvatar();
        cmdContinue = new zEFFECT.Button();
        cmdContinue1 = new zEFFECT.Button();
        loading = new zEFFECT.PanelTransparent();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        message = new zEFFECT.PanelTransparent();
        lbMessage = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        pic.setBorderSize(3);
        pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/user.png"))); // NOI18N
        pic.setOpaque(true);

        cmdContinue.setBackground(new java.awt.Color(255, 0, 0));
        cmdContinue.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cmdContinue.setForeground(new java.awt.Color(255, 255, 255));
        cmdContinue.setText("CONTINUE");
        cmdContinue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmdContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdContinueActionPerformed(evt);
            }
        });

        cmdContinue1.setBackground(new java.awt.Color(255, 0, 0));
        cmdContinue1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cmdContinue1.setForeground(new java.awt.Color(255, 255, 255));
        cmdContinue1.setText("RENTAL AGENT");
        cmdContinue1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmdContinue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdContinue1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profileLayout = new javax.swing.GroupLayout(profile);
        profile.setLayout(profileLayout);
        profileLayout.setHorizontalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileLayout.createSequentialGroup()
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdContinue1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdContinue, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addGap(0, 78, Short.MAX_VALUE)))
                .addContainerGap())
        );
        profileLayout.setVerticalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(cmdContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdContinue1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        add(profile, "card2");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\TOYOTA\\aseprite_pixel_quality_car_338x102.gif")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOADING");

        javax.swing.GroupLayout loadingLayout = new javax.swing.GroupLayout(loading);
        loading.setLayout(loadingLayout);
        loadingLayout.setHorizontalGroup(
            loadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addGroup(loadingLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        loadingLayout.setVerticalGroup(
            loadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loadingLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        add(loading, "card2");

        lbMessage.setBackground(new java.awt.Color(255, 255, 255));
        lbMessage.setForeground(new java.awt.Color(255, 0, 0));
        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMessage.setText("MESSAGE");

        javax.swing.GroupLayout messageLayout = new javax.swing.GroupLayout(message);
        message.setLayout(messageLayout);
        messageLayout.setHorizontalGroup(
            messageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );
        messageLayout.setVerticalGroup(
            messageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messageLayout.createSequentialGroup()
                .addContainerGap(167, Short.MAX_VALUE)
                .addComponent(lbMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(244, 244, 244))
        );

        add(message, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void cmdContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdContinueActionPerformed
        // TODO add your handling code here:
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.dispose();
    }

    if (frameToOpen != null) {
        frameToOpen.setLocationRelativeTo(null);
        frameToOpen.setVisible(true);
    } else {
        Dashboard dashboard = new Dashboard();
        dashboard.setLocationRelativeTo(null);
        dashboard.setVisible(true);
    }
    }//GEN-LAST:event_cmdContinueActionPerformed

    private void cmdContinue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdContinue1ActionPerformed
        // TODO add your handling code here:
    java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.dispose();
    }

    Login1 login = new Login1();
    login.setLocationRelativeTo(null);
    login.setVisible(true);

    }//GEN-LAST:event_cmdContinue1ActionPerformed

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        int width = getWidth();
        int height = getHeight();
        int x = 0;
        int y = 0;
        if (slideLeft) {
            int centerY = height / 2;
            Path2D.Float p = new Path2D.Float();
            p.moveTo(x, y);
            p.lineTo(width, y);
            p.lineTo(width, height);
            p.lineTo(x, height);
            p.curveTo(x, height, easeOutBounce(animate) * width, centerY, x, y);
            g2.fill(p);
        } else {
            g2.fillRect(x, y, width, height);
        }
        g2.dispose();
        super.paint(grphcs);
    }

    private float easeOutBounce(float x) {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        double v;
        if (x < 1 / d1) {
            v = n1 * x * x;
        } else if (x < 2 / d1) {
            v = n1 * (x -= 1.5 / d1) * x + 0.75;
        } else if (x < 2.5 / d1) {
            v = n1 * (x -= 2.25 / d1) * x + 0.9375;
        } else {
            v = n1 * (x -= 2.625 / d1) * x + 0.984375;
        }
        if (slideLeft) {
            return (float) (1f - v);
        } else {
            return (float) v;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private zEFFECT.Button cmdContinue;
    private zEFFECT.Button cmdContinue1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbMessage;
    private zEFFECT.PanelTransparent loading;
    private zEFFECT.PanelTransparent message;
    private zEFFECT.ImageAvatar pic;
    private zEFFECT.PanelTransparent profile;
    // End of variables declaration//GEN-END:variables
}
