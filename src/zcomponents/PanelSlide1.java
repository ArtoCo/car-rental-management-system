package zcomponents;

import customers.DatabaseConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import zEFFECT.ModelUser;
import ADMIN.RentANDbookings;

public class PanelSlide1 extends javax.swing.JLayeredPane {

    public void setFram(JFrame fram) {
        this.fram = fram;
    }

    private final Animator animator;
    private float animate = 1f;
    private boolean slideLeft;
    private final PanelLoginUser login;
    private final PanelLoading1 loading;
    private Thread th;
    private MigLayout layout;
    private JFrame fram;
    private RentANDbookings loadedRentBookings;

    public PanelSlide1() {
        initComponents();
        setPreferredSize(new Dimension(350, 450));
        layout = new MigLayout("inset 0", "[fill]", "[fill]");
        setLayout(layout);
        login = new PanelLoginUser();
        loading = new PanelLoading1();
        loading.setVisible(false);
        Color color = new Color(27, 26, 26);
        setBackground(color);
        loading.setBackground(color);
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (slideLeft) {
                    loading.setVisible(true);
                } else {
                    login.setVisible(true);
                }
            }

            @Override
            public void timingEvent(float fraction) {
                double width = getWidth();
                animate = fraction;
                float a = easeOutQuint(fraction);
                int x = (int) (a * width);
                loading.setAnimate(slideLeft, fraction);
                layout.setComponentConstraints(loading, "pos " + x + " 0 100% 100%");
                revalidate();
                repaint();
            }

            @Override
            public void end() {
                if (slideLeft) {
                    login.setVisible(false);
                } else {
                    loading.setVisible(false);
                    loading.reset();
                }
            }
        };
        animator = new Animator(2000, target);
        animator.setResolution(0);
        add(loading, " pos 0 0 0 0, w 0!");
        add(login);
        
        // PanelSlide
login.addEventLogin(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (animator.isRunning()) {
            return;
        }

        if (!login.checkUser()) {
            return;
        }

        if (!login.isLoginValid()) {
            javax.swing.JOptionPane.showMessageDialog(PanelSlide1.this,
                "Incorrect Username or Password!",
                "Login Failed",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;   // stop here, no slide
        }

        showSlide(true);
        doLoginAnimation();
    }
        });


    }

    public void showSlide(boolean show) {
        slideLeft = show;
        animator.start();
    }
    
// PanelSlide
private void doLoginAnimation() {
    th = new Thread(() -> {
        try {
            loadedRentBookings = new RentANDbookings(true);
            loadedRentBookings.pack();
            loadedRentBookings.setLocationRelativeTo(null);

            javax.swing.SwingUtilities.invokeLater(() -> {
                // Send preloaded RentANDbookings1 to PanelLoading1
                loading.setFrameToOpen(loadedRentBookings);
            });

            Thread.sleep(4000);

            javax.swing.SwingUtilities.invokeLater(() -> {
                loading.doneLogin(
                    new ModelUser(
                        1,
                        "USER",
                        new ImageIcon(getClass().getResource("/zicons/user.png"))
                    )
                );
            });

        } catch (Exception e) {
            e.printStackTrace();

            javax.swing.SwingUtilities.invokeLater(() -> {
                javax.swing.JOptionPane.showMessageDialog(
                    PanelSlide1.this,
                    "Failed to load Rent and Bookings: " + e.getMessage(),
                    "Loading Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            });
        }
    });

    th.start();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (slideLeft == false) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            float x = easeOutQuint(animate) * width;
            float y = 0;
            int centerY = height / 2;
            Path2D.Float p = new Path2D.Float();
            p.moveTo(x, y);
            p.lineTo(x, height);
            p.curveTo(x, height, easeOutBounce(animate) * width, centerY, x, y);
            g2.setColor(getBackground());
            g2.fill(p);
            g2.dispose();
        }
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

    private float easeOutQuint(float x) {
        double v = 1 - Math.pow(1 - x, 5);
        if (slideLeft) {
            return (float) (1f - v);
        } else {
            return (float) v;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
