/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zEFFECT;

/**
 *
 * @author ArtoCO
 */
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class ROUNDPANEL extends JPanel {

    private int cornerRadius;
    private Color blurColor;
    private int blurSize;

    public ROUNDPANEL(int cornerRadius, Color blurColor, int blurSize) {
        this.cornerRadius = cornerRadius;
        this.blurColor = blurColor;
        this.blurSize = blurSize;   // bigger number = stronger blur
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // ---- Outer Blurry Glow ----
        for (int i = blurSize; i > 0; i--) {
            float alpha = (float) i / (blurSize * 2f); // fade outward
            g2.setColor(new Color(
                    blurColor.getRed(),
                    blurColor.getGreen(),
                    blurColor.getBlue(),
                    (int) (alpha * 255))
            );

            g2.setStroke(new BasicStroke(i)); // increasing thickness
            g2.draw(new RoundRectangle2D.Double(
                    i / 2.0,
                    i / 2.0,
                    width - i,
                    height - i,
                    cornerRadius, cornerRadius
            ));
        }

        // ---- Fill panel inside ----
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(
                blurSize,
                blurSize,
                width - blurSize * 2,
                height - blurSize * 2,
                cornerRadius, cornerRadius
        ));

        g2.dispose();
    }
}
