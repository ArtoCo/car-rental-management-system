package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class HoverButton extends JButton {

    private boolean hover = false;
    private boolean press = false;

    private int radius = 40;

    // Target colors (what you want at full hover/press)
    private Color hoverColor = new Color(255, 255, 255, 30);
    private Color pressColor = new Color(255, 255, 255, 50);

    // Smooth animation state (alpha 0..1)
    private float anim = 0f;            // current hover intensity
    private float targetAnim = 0f;      // target hover intensity
    private final Timer timer;

    // Animation tuning
    private int fps = 60;
    private float speed = 0.18f;        // higher = faster transition (0.10 - 0.25 good)

    public HoverButton() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorder(new EmptyBorder(7, 12, 7, 12));

        int delay = Math.max(1, 1000 / fps);
        timer = new Timer(delay, e -> stepAnimation());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                targetAnim = 1f;
                startAnim();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                press = false;
                targetAnim = 0f;
                startAnim();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    press = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
                repaint();
            }
        });
    }

    private void startAnim() {
        if (!timer.isRunning()) timer.start();
    }

    private void stepAnimation() {
        // Exponential smoothing towards targetAnim (smooth + stable)
        anim += (targetAnim - anim) * speed;

        // Stop timer when close enough (saves CPU)
        if (Math.abs(targetAnim - anim) < 0.01f) {
            anim = targetAnim;
            timer.stop();
        }
        repaint();
    }

    public void setHoverColor(Color c) {
        hoverColor = c;
        repaint();
    }

    public void setPressColor(Color c) {
        pressColor = c;
        repaint();
    }

    public void setRadius(int r) {
        radius = r;
        repaint();
    }

    /** Optional: control how fast hover fades (0.10..0.25 typical) */
    public void setAnimationSpeed(float s) {
        speed = Math.max(0.02f, Math.min(0.5f, s));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // If pressed, show press immediately; else use smooth hover alpha
        Color base = press ? pressColor : hoverColor;
        float alpha = press ? 1f : anim;

        if (alpha > 0f) {
            // Apply smooth alpha to the base color (keeps RGB, animates transparency)
            int a = Math.min(255, Math.max(0, Math.round(base.getAlpha() * alpha)));
            Color bg = new Color(base.getRed(), base.getGreen(), base.getBlue(), a);

            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, radius, radius));
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
