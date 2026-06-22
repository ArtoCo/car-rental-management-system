package test;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Button extends JButton {

    private boolean mousePress;

    // Gradient colors
    private Color startColor = Color.decode("#dd1818");
    private Color endColor   = Color.decode("#333333");

    public Button() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorder(new EmptyBorder(7, 12, 7, 12));
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    mousePress = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    mousePress = false;
                    repaint();
                }
            }
        });
    }

    // Optional: allow changing gradient colors later
    public void setGradient(Color start, Color end) {
        this.startColor = start;
        this.endColor = end;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // pressed effect: darken both colors
        Color c1 = mousePress ? startColor.darker() : startColor;
        Color c2 = mousePress ? endColor.darker()   : endColor;

        // Horizontal gradient (left -> right)
        GradientPaint gp = new GradientPaint(0, 0, c1, w, 0, c2);

        // Rounded "pill" shape (same as your original)
        RoundRectangle2D rr = new RoundRectangle2D.Double(0, 0, w, h, h, h);

        g2.setPaint(gp);
        g2.fill(rr);

        // Optional subtle border for nicer look
        g2.setColor(new Color(255, 255, 255, 40));
        g2.draw(rr);

        g2.dispose();
        super.paintComponent(grphcs);
    }
}
