/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class ROUNDPANEL extends JPanel {
    private final int radius;
    private final Color borderColor;
    private final int borderThickness;

    public ROUNDPANEL(int radius, Color borderColor, int borderThickness) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); // Allow transparent corners
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate area with padding for stroke
        int width = getWidth();
        int height = getHeight();
        int arc = radius * 2;

        // Draw smooth rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, arc, arc);

        // Draw border if needed
        if (borderThickness > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));

            // Use half-thickness padding to keep border inside bounds
            g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                    width - borderThickness, height - borderThickness,
                    arc, arc);
        }

        g2.dispose();
        super.paintComponent(g); // Paint children
    }
}
