package ADMIN;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import customers.DatabaseConnection;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.UIManager;
import popup.ROUNDPANEL;
import zcomponents.PanelSlide;
import ADMIN.Login1;

public class Login1 extends javax.swing.JFrame {

    public Login1() {
        initComponents();
        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
     
        getContentPane().setBackground(new Color(0, 0, 0));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSlide11 = new zcomponents.PanelSlide1();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSlide11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSlide11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
    FlatRobotoFont.install();
    UIManager.put("defaultFont",
        new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

    FlatMacDarkLaf.setup();                 // Dark theme
    FlatLaf.registerCustomDefaultsSource("sample.theme");

    java.awt.EventQueue.invokeLater(() -> {
        new Login1().setVisible(true);       // ONLY startup window
    });

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private zcomponents.PanelSlide1 panelSlide11;
    // End of variables declaration//GEN-END:variables
}
