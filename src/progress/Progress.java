/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progress;

/**
 *
 * @author ArtoCO
 */
import java.awt.Font;
import javax.swing.JProgressBar;

public class Progress extends JProgressBar {

    private final ProgressCircleUI ui;

    public Progress() {
    setOpaque(false);
    setStringPainted(true);
    setFont(new Font("SansSerif", Font.BOLD, 24)); // 🔧 Make number larger
    ui = new ProgressCircleUI();
    setUI(ui);
}


    @Override
public String getString() {
    return String.valueOf(getValue());  // ✅ Just the number
}


    public void start() {
        ui.start();
    }
}