/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zEFFECT;

/**
 *
 * @author artoc
 */
import java.awt.*;
import javax.swing.*;

public class ROUNDLABEL extends JLabel {

    private int radius;
    private Color borderColor;
    private int borderSize;

    public ROUNDLABEL(int radius, Color borderColor, int borderSize) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderSize = borderSize;

        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public ROUNDLABEL(String text, int radius, Color borderColor, int borderSize) {
        super(text);
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderSize = borderSize;

        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color of label
        g2.setColor(getBackground());
        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radius,
                radius
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (borderSize > 0) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderSize));

            int half = borderSize / 2;

            g2.drawRoundRect(
                    half,
                    half,
                    getWidth() - borderSize,
                    getHeight() - borderSize,
                    radius,
                    radius
            );

            g2.dispose();
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        repaint();
    }
}