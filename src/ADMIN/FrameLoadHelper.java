package ADMIN;

import java.awt.Cursor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

final class FrameLoadHelper {

    private static final AtomicBoolean LOADING = new AtomicBoolean(false);

    private FrameLoadHelper() {
    }

    static void fadeSwitchAsync(JFrame currentFrame, Supplier<? extends JFrame> nextFrameSupplier) {
        loadAndSwitch(currentFrame, nextFrameSupplier, false);
    }

    static void fadeSwitchCenteredAsync(JFrame currentFrame, Supplier<? extends JFrame> nextFrameSupplier) {
        loadAndSwitch(currentFrame, nextFrameSupplier, true);
    }

    private static void loadAndSwitch(JFrame currentFrame, Supplier<? extends JFrame> nextFrameSupplier, boolean centered) {
        if (!LOADING.compareAndSet(false, true)) {
            return;
        }

        Cursor oldCursor = currentFrame.getCursor();
        currentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<JFrame, Void>() {
            @Override
            protected JFrame doInBackground() {
                return nextFrameSupplier.get();
            }

            @Override
            protected void done() {
                try {
                    JFrame nextFrame = get();
                    if (centered) {
                        test.FadeTransition.fadeSwitchCentered(currentFrame, nextFrame);
                    } else {
                        test.FadeTransition.fadeSwitch(currentFrame, nextFrame);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(currentFrame,
                            "Unable to open panel:\n" + ex.getMessage(),
                            "Loading Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    currentFrame.setCursor(oldCursor);
                    LOADING.set(false);
                }
            }
        }.execute();
    }
}
