/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progress;

/**
 *
 * @author ArtoCO
 */
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ProgressCircleUI extends BasicProgressBarUI {

    private Animator animator;
    private float animate;

    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    public void start() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    @Override
    public void installUI(JComponent jc) {
        super.installUI(jc);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                setAnimate(fraction);
                progressBar.repaint();
            }
        };
        animator = new Animator(2000, target);
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area area = new Area(createCircle(c, 0, 0, 360));
        area.subtract(new Area(createCircle(c, 15, 0, 360)));
        g2.setColor(new Color(216, 216, 216, 50));
        g2.fill(area);
        g2.setComposite(AlphaComposite.SrcIn);
        int r = (int) (progressBar.getPercentComplete() * 360);
        g2.setPaint(new GradientPaint(0, 0, progressBar.getBackground(), 0, c.getHeight(), progressBar.getForeground()));
        Area area1 = new Area(createCircle(c, 0, 90, -r * animate));
        area1.subtract(new Area(createCircle(c, 15, 0, 360)));
        g2.fill(area1);
        if (progressBar.isStringPainted()) {
            paintString(g2, c);
        }
        g2.dispose();
    }

    private Shape createCircle(Component c, int s, int start, double angle) {
        int width = c.getWidth();
        int height = c.getHeight();
        int size = Math.min(width, height) - s;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        return new Arc2D.Double(x, y, size, size, start, angle, Arc2D.PIE);
    }

    private void paintString(Graphics g, JComponent c) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.WHITE);  // Set the string color

    // 🔧 Set a custom large font here
    g2.setFont(new Font("SansSerif", Font.BOLD, 24));  // Adjust size as needed

    Insets insets = progressBar.getInsets();
    int width = c.getWidth();
    int height = c.getHeight();
    FontMetrics fontMetrics = g2.getFontMetrics();  // use updated font
    String progressString = progressBar.getString();
    int stringWidth = fontMetrics.stringWidth(progressString);
    int stringHeight = fontMetrics.getAscent();

    int x = (width - stringWidth) / 2;
    int y = (height + stringHeight) / 2 - insets.bottom;

    g2.drawString(progressString, x, y);
}

}
