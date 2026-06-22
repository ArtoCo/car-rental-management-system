package test;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.Timer;

public final class FadeTransition {

    private FadeTransition() {}

    public static void fadeSwitch(JFrame from, JFrame to) {
        fadeSwitch(from, to, 250, 8, false); // duration(ms), timer delay(ms)
    }

    public static void fadeSwitchCentered(JFrame from, JFrame to) {
        fadeSwitch(from, to, 250, 8, true);
    }

    public static void fadeSwitch(JFrame from, JFrame to, int durationMs, int delayMs) {
        fadeSwitch(from, to, durationMs, delayMs, false);
    }

    private static void fadeSwitch(JFrame from, JFrame to, int durationMs, int delayMs, boolean centerTarget) {
        boolean opacitySupported = isOpacitySupported();

        // If not supported, just switch normally
        if (!opacitySupported) {
            from.dispose();
            to.setLocationRelativeTo(centerTarget ? null : from);
            to.setVisible(true);
            return;
        }

        // Prepare target frame at same location (or center if you want)
        if (to.getWidth() <= 0 || to.getHeight() <= 0) {
            to.pack();
        }
        if (centerTarget) {
            to.setLocationRelativeTo(null);
        } else {
            to.setLocation(from.getLocation());
        }
        to.setOpacity(0f);
        to.setVisible(true);

        // Fade out current frame, then fade in target
        fade(from, 1f, 0f, durationMs, delayMs, () -> {
            from.setVisible(false);
            from.dispose();
            fade(to, 0f, 1f, durationMs, delayMs, null);
        });
    }

    private static void fade(JFrame frame, float from, float to, int durationMs, int delayMs, Runnable onDone) {
        long start = System.nanoTime();
        final long dur = durationMs * 1_000_000L;

        Timer timer = new Timer(delayMs, null);
        timer.setCoalesce(true);

        timer.addActionListener(e -> {
            long now = System.nanoTime();
            double t = (double) (now - start) / dur;
            if (t >= 1.0) t = 1.0;

            // smooth curve (smoother than linear)
            double eased = smootherStep(t);

            float op = (float) (from + (to - from) * eased);
            safeSetOpacity(frame, op);

            if (t >= 1.0) {
                timer.stop();
                safeSetOpacity(frame, to);
                if (onDone != null) onDone.run();
            }
        });

        timer.start();
    }

    // 6t^5 - 15t^4 + 10t^3 (very smooth)
    private static double smootherStep(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static boolean isOpacitySupported() {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            return gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
        } catch (Exception ex) {
            return false;
        }
    }

    private static void safeSetOpacity(JFrame frame, float opacity) {
        try {
            float op = Math.max(0f, Math.min(1f, opacity));
            frame.setOpacity(op);
        } catch (Exception ignored) {
        }
    }
}
