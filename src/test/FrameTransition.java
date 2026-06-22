package test;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.Timer;

public final class FrameTransition {

    private FrameTransition() {}

    // Public API (simple)
    public static void minimizeSwitch(JFrame from, JFrame to) {
        minimizeSwitch(from, to, 360, 8, 0.10);  // duration, delay, minScale
    }

    // Advanced tuning
    public static void minimizeSwitch(JFrame from, JFrame to, int durationMs, int delayMs, double minScale) {
        if (to.getWidth() <= 0 || to.getHeight() <= 0) {
            to.pack();
        }

        Rectangle start = from.getBounds();
        int cx = start.x + start.width / 2;
        int cy = start.y + start.height / 2;

        Rectangle minimized = scaledAroundCenter(start, cx, cy, minScale);

        boolean opacitySupported = isOpacitySupported();

        // Prepare "to" frame at minimized center
        to.setBounds(minimized);
        if (opacitySupported) safeSetOpacity(to, 0f);
        to.setVisible(true);

        // Animate OUT (shrink + fade)
        animate(from, start, minimized,
                opacitySupported ? 1f : -1f,
                opacitySupported ? 0f : -1f,
                durationMs, delayMs,
                () -> {
                    from.setVisible(false);
                    from.dispose();

                    // Animate IN (grow + fade + tiny overshoot at end)
                    animateWithOvershoot(to, minimized, start,
                            opacitySupported ? 0f : -1f,
                            opacitySupported ? 1f : -1f,
                            durationMs + 80, delayMs,
                            null);
                });
    }

    // -------- Core animation (ultra smooth) --------

    private static void animate(JFrame f,
                                Rectangle fromB, Rectangle toB,
                                float fromOp, float toOp,
                                int durationMs, int delayMs,
                                Runnable onDone) {

        long start = System.nanoTime();
        final long dur = durationMs * 1_000_000L;

        Timer timer = new Timer(delayMs, null);
        timer.setCoalesce(true);

        timer.addActionListener(e -> {
            long now = System.nanoTime();
            double t = (double) (now - start) / dur;
            if (t >= 1.0) t = 1.0;

            // ✅ Quintic smooth (super smooth)
            double eased = smootherStep(t);

            int x = lerp(fromB.x, toB.x, eased);
            int y = lerp(fromB.y, toB.y, eased);
            int w = lerp(fromB.width, toB.width, eased);
            int h = lerp(fromB.height, toB.height, eased);
            f.setBounds(x, y, w, h);

            // opacity with smoother curve
            if (fromOp >= 0f && toOp >= 0f) {
                float op = (float) (fromOp + (toOp - fromOp) * eased);
                safeSetOpacity(f, op);
            }

            if (t >= 1.0) {
                timer.stop();
                f.setBounds(toB);
                if (fromOp >= 0f && toOp >= 0f) safeSetOpacity(f, toOp);
                if (onDone != null) onDone.run();
            }
        });

        timer.start();
    }

    // Open animation with a tiny overshoot (feels premium)
    private static void animateWithOvershoot(JFrame f,
                                             Rectangle fromB, Rectangle toB,
                                             float fromOp, float toOp,
                                             int durationMs, int delayMs,
                                             Runnable onDone) {

        // Overshoot target (slightly bigger) then settle
        Rectangle over = new Rectangle(
                toB.x - (int) (toB.width * 0.01),
                toB.y - (int) (toB.height * 0.01),
                (int) (toB.width * 1.02),
                (int) (toB.height * 1.02)
        );

        int firstPhase = (int) (durationMs * 0.78);   // grow to overshoot
        int secondPhase = durationMs - firstPhase;    // settle to final

        animate(f, fromB, over, fromOp, toOp, firstPhase, delayMs, () -> {
            animate(f, over, toB, toOp, toOp, secondPhase, delayMs, onDone);
        });
    }

    // -------- Helpers --------

    // Quintic smootherstep: 6t^5 - 15t^4 + 10t^3
    private static double smootherStep(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static Rectangle scaledAroundCenter(Rectangle base, int cx, int cy, double s) {
        int w = (int) Math.max(1, Math.round(base.width * s));
        int h = (int) Math.max(1, Math.round(base.height * s));
        int x = cx - w / 2;
        int y = cy - h / 2;
        return new Rectangle(x, y, w, h);
    }

    private static int lerp(int a, int b, double t) {
        return (int) Math.round(a + (b - a) * t);
    }

    private static boolean isOpacitySupported() {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            return gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
        } catch (Exception ex) {
            return false;
        }
    }

    private static void safeSetOpacity(JFrame f, float opacity) {
        try {
            float op = Math.max(0f, Math.min(1f, opacity));
            f.setOpacity(op);
        } catch (Exception ignored) {}
    }
}
