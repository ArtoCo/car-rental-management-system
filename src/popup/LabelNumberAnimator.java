/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class LabelNumberAnimator extends Thread {
    private final JLabel label;
    private final double targetValue;
    private final int duration; // in milliseconds
    private final Locale locale = new Locale("en", "PH");

    public LabelNumberAnimator(JLabel label, double targetValue, int duration) {
        this.label = label;
        this.targetValue = targetValue;
        this.duration = duration;
    }

    @Override
    public void run() {
        int frames = 60; // Number of frames per second
        int totalFrames = (duration / 1000) * frames;
        double increment = targetValue / totalFrames;

        double current = 0;

        for (int i = 0; i < totalFrames; i++) {
            current += increment;
            final double valueToShow = current;

            SwingUtilities.invokeLater(() -> {
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                label.setText(format.format(valueToShow));
            });

            try {
                Thread.sleep(1000 / frames);
            } catch (InterruptedException ignored) {}
        }

        // Ensure the final value is set
        SwingUtilities.invokeLater(() -> {
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            label.setText(format.format(targetValue));
        });
    }
}

