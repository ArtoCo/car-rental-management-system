package MUL;

import javax.swing.JLabel;
import javax.swing.Timer;

public final class UiCountAnimator {

    private static final String TIMER_KEY = "CRMS_COUNT_ANIMATION_TIMER";

    private UiCountAnimator() {
    }

    public static void animate(JLabel label, int target, int durationMs) {
        if (label == null) return;

        Object oldTimer = label.getClientProperty(TIMER_KEY);
        if (oldTimer instanceof Timer) {
            ((Timer) oldTimer).stop();
        }

        int start = parseInt(label.getText());
        if (start == target || durationMs <= 0) {
            label.setText(String.valueOf(target));
            return;
        }

        long started = System.nanoTime();
        long durationNanos = durationMs * 1_000_000L;
        Timer timer = new Timer(16, null);
        timer.setCoalesce(true);
        timer.addActionListener(e -> {
            double progress = Math.min(1.0,
                    (double) (System.nanoTime() - started) / durationNanos);
            double eased = 1.0 - Math.pow(1.0 - progress, 3);
            int value = (int) Math.round(start + (target - start) * eased);
            label.setText(String.valueOf(value));
            if (progress >= 1.0) {
                timer.stop();
                label.putClientProperty(TIMER_KEY, null);
            }
        });
        label.putClientProperty(TIMER_KEY, timer);
        timer.start();
    }

    private static int parseInt(String value) {
        try {
            return Integer.parseInt(value == null ? "0" : value.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
