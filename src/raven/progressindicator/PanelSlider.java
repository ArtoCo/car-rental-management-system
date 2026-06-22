package raven.progressindicator;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class PanelSlider extends JPanel {

    private Component[] sliderComponent;
    private final Component[] components = new Component[2];
    private final List<EventSliderAnimatorChanged> events = new ArrayList<>();
    private Animator animator;
    private float animate;
    private SliderType type;
    private MigLayout layout;

    private int duration = 100; // 🔧 Adjust animation speed here (ms)

    public PanelSlider() {
        setDoubleBuffered(true); // ✅ Reduce flicker
        init();
    }

    private void init() {
        layout = new MigLayout();
        setLayout(layout);

        layout.addLayoutCallback(new LayoutCallback() {
            @Override
            public void correctBounds(ComponentWrapper cw) {
                change(cw);
            }
        });

        // 🌟 SMOOTHER ANIMATION CONFIGURATION HERE
        animator = new Animator(duration, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float eased = easeInOutCubic(fraction); // 💡 Use smoother easing
                animate = eased;
                revalidate();
                runEvent(eased);
            }
        });

        animator.setAcceleration(0.3f); // 🔧 Fine-tune smooth start
        animator.setDeceleration(0.7f); // 🔧 Fine-tune smooth stop
        animator.setResolution(0); // ✅ Highest frame rate
    }

    private float easeInOutCubic(float t) {
        return t < 0.5 ? 4 * t * t * t : 1 - (float) Math.pow(-2 * t + 2, 3) / 2;
    }

    public void change(ComponentWrapper cw) {
        int width = getWidth();
        int height = getHeight();
        int x = 0, y = 0, x2 = 0, y2 = 0;

        switch (type) {
            case LEFT_TO_RIGHT:
                x = (int) -(width * (1f - animate));
                x2 = (int) (width * animate);
                break;
            case RIGHT_TO_LEFT:
                x = (int) (width - (width * animate));
                x2 = (int) -(width * animate);
                break;
            case TOP_TO_BOTTOM:
                y = (int) -(height * (1f - animate));
                y2 = (int) (height * animate);
                break;
            case BOTTOM_TO_TOP:
                y = (int) (height - (height * animate));
                y2 = (int) -(height * animate);
                break;
            default:
                x2 = width;
        }

        if (cw.getComponent() == components[0]) {
            cw.setBounds(x, y, width, height);
        }
        if (cw.getComponent() == components[1]) {
            cw.setBounds(x2, y2, width, height);
        }
    }

    public boolean isSlidAble() {
        return !animator.isRunning();
    }

    public synchronized void showSlid(Component component, SliderType type) {
        if (!animator.isRunning()) {
            this.type = type;
            if (components[1] != null) {
                remove(components[1]);
            }
            components[1] = components[0];
            components[0] = component;
            add(component, "pos 0 0");
            if (type == SliderType.NONE) {
                animate = 1;
                revalidate();
            } else {
                animator.start();
            }
        }
    }

    public void addEventSliderAnimatorChanged(EventSliderAnimatorChanged event) {
        events.add(event);
    }

    private void runEvent(float f) {
        for (EventSliderAnimatorChanged event : events) {
            event.animatorChange(type, f);
        }
    }

    public void setActive(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static enum SliderType {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP, NONE
    }

    public Component[] getSliderComponent() {
        return sliderComponent;
    }

    public void setSliderComponent(Component[] sliderComponent) {
        this.sliderComponent = sliderComponent;
    }

    // ✅ Optional: change speed from outside
    public void setAnimationDuration(int durationMillis) {
        this.duration = durationMillis;
        animator.setDuration(durationMillis);
    }
}
