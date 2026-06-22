/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class HoverEffect {

    private final Color hoverColor = new Color(40, 40, 40);
    private final Color normalColor;

    public HoverEffect(JPanel panel) {
        this.normalColor = panel.getBackground();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setPanelColor(panel, hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setPanelColor(panel, normalColor);
            }
        });
    }

    private void setPanelColor(JPanel panel, Color color) {
        panel.setBackground(color);
        updateChildComponents(panel, color);
    }

    private void updateChildComponents(Container container, Color color) {
        for (Component comp : container.getComponents()) {
            // Exclude buttons from hover effect
            if (!(comp instanceof JButton)) {
                if (comp instanceof JComponent) {
                    comp.setBackground(color);
                }
                if (comp instanceof Container) {
                    updateChildComponents((Container) comp, color);
                }
            }
        }
    }
}
