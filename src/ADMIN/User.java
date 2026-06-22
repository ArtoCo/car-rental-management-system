/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ADMIN;

import ADMIN.Customers;
import popup.Create;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import customers.CheckBoxTableHeaderRenderer;
import employees.DatabaseConnection;
import employees.ModelEmployee;
import popup.ProfileTableRenderer;
import employees.ServiceEmployee;
import customers.TableHeaderAlignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import static java.nio.file.Files.list;
import java.text.MessageFormat;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import popup.ROUNDPANEL;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;


/**
 *
 * @author ArtoCO
 */
public class User extends javax.swing.JFrame {
    private ServiceEmployee service=new ServiceEmployee();
    private Map<Integer, ModelEmployee> employeeMap = new HashMap<>();
    private List<ModelEmployee> deletedEmployees = new ArrayList<>();
    
    /**
     * Creates new form User
     */
    public User() {
        initComponents();
        init();
        
        setBackground(new Color(0, 0, 0, 0)); // Make frame background transparent (important)

        int radius = 20; // or any corner radius you want
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
     
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hoverButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
  
    }
     private void init (){
         GlassPanePopup.install(this);
         Notifications.getInstance().setJFrame(this);
         
         
         jTable1.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                 + "height:30;"
                 + "hoverBackground:null;"
                 + "separatorColor:$TableHeader.background;"
                 + "font:bold;");
         jTable1.getTableHeader().putClientProperty("pressedBackground", new Color(51,51,51));
         
         jTable1.putClientProperty(FlatClientProperties.STYLE, ""
                 + "rowHeight:70;"
                 + "showHorizontalLines:true;"
                 + "intercellSpacing:0,1;"
                 + "cellFocusColor:$TableHeader.hoverBackground;"
                 + "selectionBackground:$TableHeader.hoverBackground;"
                 + "selectionForeground:"
                 + "$Table.foreground;");
         jTable1.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(jTable1, 0));
         jTable1.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(jTable1));
         jTable1.getColumnModel().getColumn(2).setCellRenderer(new ProfileTableRenderer(jTable1));
       
         jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                 + "trackArc:999;"
                 + "trackInsets:3,3,3,3;"
                 + "thumbInsets:3,3,3,3;"
                 + "background:$Table.backgound;");
         jLabel12.putClientProperty(FlatClientProperties.STYLE, ""
                 + "font:bold +5;");
         
         jTextField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
  
    loadInitialDataAsync();
         
                 
     }

    private void loadInitialDataAsync() {
        new SwingWorker<List<ModelEmployee>, Void>() {
            @Override
            protected List<ModelEmployee> doInBackground() throws Exception {
                DatabaseConnection.getInstance().connectToDatabase();
                return service.getAll();
            }

            @Override
            protected void done() {
                try {
                    populateEmployeeTable(get());
                } catch (Exception e) {
                    e.printStackTrace();
                    Notifications.getInstance().show(
                            Notifications.Type.ERROR, "Failed to load employee data");
                }
            }
        }.execute();
    }

    private void populateEmployeeTable(List<ModelEmployee> employees) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (jTable1.isEditing()) {
            jTable1.getCellEditor().stopCellEditing();
        }
        model.setRowCount(0);
        for (ModelEmployee employee : employees) {
            model.addRow(employee.toTableRow(jTable1.getRowCount() + 1));
        }
    }

    public void exportJTableToPDF(JTable table, String filePath) {
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage(PDRectangle.LETTER);
        document.addPage(page);

        // Load custom font
        PDType0Font font = PDType0Font.load(document, new File("C:/Users/ArtoCO/Documents/NetBeansProjects/CRMS/src/fonts/segoeui.ttf"));

        float fontSize = 9;
        float leading = 14;

        float margin = 10; // align table to the left
        float pageWidth = page.getMediaBox().getWidth();
        float yStart = page.getMediaBox().getHeight() - 50; // start near top

        // Adjust these widths to match your 9 table columns
        float[] colWidths = {35, 25, 35, 35, 80, 100, 100, 50, 100};
        int numCols = table.getColumnCount();

        if (colWidths.length != numCols) {
            throw new IllegalArgumentException("Column width count does not match table column count.");
        }

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Centered Title
        String title = "EMPLOYEES TABLE INFORMATION";
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleX = (pageWidth - titleWidth) / 2;

        contentStream.beginText();
        contentStream.setFont(font, fontSize + 2);
        contentStream.newLineAtOffset(titleX, yStart);
        contentStream.showText(title);
        contentStream.endText();

        float yPosition = yStart - 2 * leading; // space after title

        // Draw Header Row
        contentStream.setFont(font, fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yPosition);
        for (int i = 0; i < numCols; i++) {
            String text = table.getColumnName(i);
            contentStream.showText(String.format("%-" + (int)(colWidths[i]/5) + "s", text));
            contentStream.newLineAtOffset(colWidths[i], 0);
        }
        contentStream.endText();

        yPosition -= leading;

        // Draw Table Rows
        for (int row = 0; row < table.getRowCount(); row++) {
            if (yPosition < 50) { // new page if needed
                contentStream.close();
                page = new PDPage(PDRectangle.LETTER);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = yStart;

                // Reprint header on new page
                contentStream.setFont(font, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                for (int i = 0; i < numCols; i++) {
                    String text = table.getColumnName(i);
                    contentStream.showText(String.format("%-" + (int)(colWidths[i]/5) + "s", text));
                    contentStream.newLineAtOffset(colWidths[i], 0);
                }
                contentStream.endText();
                yPosition -= leading;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            for (int col = 0; col < numCols; col++) {
                Object value = table.getValueAt(row, col);
                String text = value != null ? value.toString() : "";
                contentStream.showText(String.format("%-" + (int)(colWidths[col]/5) + "s", text));
                contentStream.newLineAtOffset(colWidths[col], 0);
            }
            contentStream.endText();
            yPosition -= leading;
        }

        contentStream.close();
        document.save(filePath);
        JOptionPane.showMessageDialog(null, "Exported to PDF successfully!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to export to PDF: " + e.getMessage());
    }
}



private float getColumnOffset(float[] colWidths, int index) {
    float offset = 0;
    for (int i = 0; i < index; i++) {
        offset += colWidths[i];
    }
    return offset;
}

private void drawCell(PDPageContentStream contentStream, PDFont font, String text, float x, float y, float width, float height) throws IOException {
    contentStream.beginText();
    contentStream.newLineAtOffset(x + 2, y - height + 6);  // adjust baseline and padding
    contentStream.showText(fitText(font, text, width - 4)); // fit text
    contentStream.endText();
}

private String fitText(PDFont font, String text, float maxWidth) throws IOException {
    StringBuilder sb = new StringBuilder();
    float width = 0;
    for (char c : text.toCharArray()) {
        float charWidth = font.getStringWidth(String.valueOf(c)) / 1000 * 10; // font size 10
        if (width + charWidth > maxWidth) break;
        sb.append(c);
        width += charWidth;
    }
    return sb.toString();
}


    private void loadData(){
        try{
           populateEmployeeTable(service.getAll());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void searchData(String search){
        try{
           DefaultTableModel model= (DefaultTableModel)jTable1.getModel();
           if(jTable1.isEditing()){
              jTable1.getCellEditor().stopCellEditing();
           }
           model.setRowCount(0);
           List<ModelEmployee>list=service.search(search);
           for(ModelEmployee d:list){
               model.addRow(d.toTableRow(jTable1.getRowCount() + 1));
           }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class CustomButton extends JButton {
    // For rounded corners
    private int arcWidth = 15;
    private int arcHeight = 15;
    private boolean isGradient = false;

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
    }

    public void setGradient(boolean isGradient) {
        this.isGradient = isGradient;
        repaint(); // Repaint the button to reflect the change
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        if (isGradient) {
            // Use gradient colors
            Color color1 = Color.decode("#dd1818"); // Red color
            Color color2 = Color.decode("#333333"); // Dark gray color
            GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
            g2.setPaint(gradient);
        } else {
            // Use the default color
            g2.setColor(getBackground());
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the background
        g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        // Draw the text
        super.paintComponent(g2);
        g2.dispose();
    }
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jPanel14 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        hoverButton2 = new test.HoverButton();
        jLabel38 = new javax.swing.JLabel();
        hoverButton3 = new test.HoverButton();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        hoverButton4 = new test.HoverButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        button2 = new test.Button();
        hoverButton5 = new test.HoverButton();
        hoverButton6 = new test.HoverButton();
        jLabel50 = new javax.swing.JLabel();
        hoverButton7 = new test.HoverButton();
        jLabel51 = new javax.swing.JLabel();
        jPanel3 = new ROUNDPANEL(20, new Color(0, 204, 204), 2);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        buttonAction1 = new employees.ButtonAction();
        buttonAction2 = new employees.ButtonAction();
        buttonAction3 = new employees.ButtonAction();
        buttonAction4 = new employees.ButtonAction();
        buttonAction5 = new employees.ButtonAction();

        jTextField1.setBackground(new java.awt.Color(27, 26, 26));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1360, 712));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(1350, 701));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(27, 26, 26));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel14.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 670, 30, 40));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/Yellow and Black Modern Minimalist Car Rental Service Logo (100 x 100 px) (2).png"))); // NOI18N
        jPanel14.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 330, 170));

        hoverButton2.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton2.setText("   Dashboard");
        hoverButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton2ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, 40));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-dashboard-35 (1).png")); // NOI18N
        jPanel14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 30, 40));

        hoverButton3.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton3.setText("    Schedule");
        hoverButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 270, 40));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-schedule-39.png")); // NOI18N
        jPanel14.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 30, 40));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-customers-35.png")); // NOI18N
        jPanel14.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 30, 40));

        hoverButton4.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton4.setText("   Customers");
        hoverButton4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton4ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 270, 40));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-rental-35.png")); // NOI18N
        jPanel14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 30, 40));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\BMW\\icons8-employee-35.png")); // NOI18N
        jPanel14.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 30, 40));

        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("   Employees");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel14.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 270, 40));

        hoverButton5.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton5.setText("      Rent and Bookings");
        hoverButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton5ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 270, 40));

        hoverButton6.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton6.setText("     Inventory");
        hoverButton6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 270, 40));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-inventory-35.png")); // NOI18N
        jPanel14.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 40));

        hoverButton7.setForeground(new java.awt.Color(255, 255, 255));
        hoverButton7.setText("     Sign Out");
        hoverButton7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        hoverButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoverButton7ActionPerformed(evt);
            }
        });
        jPanel14.add(hoverButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 270, 40));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-sign-out-35.png")); // NOI18N
        jPanel14.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 30, 40));

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 10, 330, 691));

        jPanel3.setBackground(new java.awt.Color(27, 26, 26));

        jTable1.setBackground(new java.awt.Color(27, 26, 26));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECT", "#", "NAME", "AGE", "PHONE NUMBER", "PRIMARY ADDRESS", "DATE", "SALARY", "POSITION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(4).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Search Employee");

        buttonAction1.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction1.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction1.setText("PRINT");
        buttonAction1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction1ActionPerformed(evt);
            }
        });

        buttonAction2.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction2.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction2.setText("RETRIVE");
        buttonAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction2ActionPerformed(evt);
            }
        });

        buttonAction3.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction3.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction3.setText("DELETE");
        buttonAction3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction3ActionPerformed(evt);
            }
        });

        buttonAction4.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction4.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction4.setText("EDIT");
        buttonAction4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction4ActionPerformed(evt);
            }
        });

        buttonAction5.setBackground(new java.awt.Color(51, 51, 51));
        buttonAction5.setForeground(new java.awt.Color(255, 255, 255));
        buttonAction5.setText("ADD");
        buttonAction5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(538, 538, 538)
                        .addComponent(buttonAction5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonAction4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 1000, 690));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction5ActionPerformed
        // CREATE WITH UPDATE
    Create create=new Create();
    create.loadData(service, null);
    DefaultOption option = new DefaultOption(){
        @Override
        
        public boolean closeWhenClickOutside(){
            return true;
            
        }
    };
    String actions[] = new String[]{"Cancel", "Save"};

GlassPanePopup.showPopup(new SimplePopupBorder(create, "Create Employee", actions, (dialog, index) -> {
    if (index == 1) { 
    try {
             service.create(create.getData());
             dialog.closePopup();
             Notifications.getInstance().show(Notifications.Type.SUCCESS, "SUCCESSFULLY ADDED"); 
             loadData();
             
    } catch (Exception e) {
        e.printStackTrace();
    }
    }else {
       dialog.closePopup();
    }
}), option);
    }//GEN-LAST:event_buttonAction5ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        searchData(jTextField1.getText().trim());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed
    // EDIT THEN UPDATE  
    List<ModelEmployee> list = getSelectedData();
    if (!list.isEmpty()) {
        if (list.size() == 1) {
            ModelEmployee data = list.get(0);
            Create create= new Create();
            create.loadData(service, data);
            DefaultOption option = new DefaultOption(){
        @Override
        
        public boolean closeWhenClickOutside(){
            return true;
            
        }
    };
    String actions[] = new String[]{"Cancel", "Update"};
    GlassPanePopup.showPopup(new SimplePopupBorder(create, "Edit Employee ["+ data.getFirstname()+ "]", actions, (dialog, index) -> {
    if (index == 1) { 
        //edit
    try {
             ModelEmployee dataEdit=create.getData();
             dataEdit.setEmployeeId(data.getEmployeeId());
             service.edit(dataEdit);
             dialog.closePopup();
             Notifications.getInstance().show(Notifications.Type.SUCCESS, "UPDATED"); 
    } catch (Exception e) {
        e.printStackTrace();
    }
    loadData();
    }else {
       dialog.closePopup();
    }
}), option);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Choose Only One Employee To Edit");
        }
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Choose an Employee To Edit");
    }
    
    }//GEN-LAST:event_buttonAction4ActionPerformed
  
    private void buttonAction3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction3ActionPerformed
        // DELETE LIST THEN UPDATE
    List<ModelEmployee> list = getSelectedData();
    if (!list.isEmpty()) {
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };
        String actions[] = new String[]{"Cancel", "Delete"};
        JLabel label = new JLabel("         ARE YOU SURE TO DELETE " + list.size() + " EMPLOYEE(S)?");
        label.setBounds(0, 25, 0, 30);
        GlassPanePopup.showPopup(new SimplePopupBorder(label, "Confirm Delete", actions, (dialog, index) -> {
            if (index == 1) { 
                try {
                    for (ModelEmployee d : list) {
                        // Store deleted employee in the temporary list
                        deletedEmployees.add(d);
                        service.delete(d.getEmployeeId());
                    }
                    dialog.closePopup();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "DELETED"); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadData();
            } else {
                dialog.closePopup();
            }
        }), option);
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Please Select an Employee To Delete");
    }//GEN-LAST:event_buttonAction3ActionPerformed
 }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed
        // TODO add your handling code here:
        if (!deletedEmployees.isEmpty()) {
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };
        String[] actions = new String[]{"Cancel", "Restore"};
        JLabel label = new JLabel("         RESTORE " + deletedEmployees.size() + " DELETED EMPLOYEE(S)?");

        GlassPanePopup.showPopup(new SimplePopupBorder(label, "Restore Deleted", actions, (dialog, index) -> {
            if (index == 1) {
                try {
                    for (ModelEmployee emp : deletedEmployees) {
                        // Re-insert employee into database
                        service.create(emp);
                    }
                    deletedEmployees.clear();  // Clear the temp list after restore
                    loadData();  // Refresh JTable1
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "RESTORED");
                } catch (Exception e) {
                    e.printStackTrace();
                    Notifications.getInstance().show(Notifications.Type.ERROR, "FAILED TO RESTORE");
                }
                dialog.closePopup();
            } else {
                dialog.closePopup();
            }
        }), option);
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, "No deleted employees to restore.");
    }
        
    }//GEN-LAST:event_buttonAction2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void buttonAction1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction1ActionPerformed
        // TODO add your handling code here:
    exportJTableToPDF(jTable1, "EmployeeList.pdf");
    
    }//GEN-LAST:event_buttonAction1ActionPerformed

    private void hoverButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton2ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Dashboard::new);
    }//GEN-LAST:event_hoverButton2ActionPerformed

    private void hoverButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton3ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Schedule::new);
    }//GEN-LAST:event_hoverButton3ActionPerformed

    private void hoverButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton4ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Customers::new);
    }//GEN-LAST:event_hoverButton4ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed

    private void hoverButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton5ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, RentANDbookings::new);
    }//GEN-LAST:event_hoverButton5ActionPerformed

    private void hoverButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton6ActionPerformed
        // TODO add your handling code here:
        FrameLoadHelper.fadeSwitchAsync(this, Inventory::new);
    }//GEN-LAST:event_hoverButton6ActionPerformed

    private void hoverButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoverButton7ActionPerformed
        // TODO add your handling code here:
        Login pi = new Login();
        test.FadeTransition.fadeSwitchCentered(this, pi);
    }//GEN-LAST:event_hoverButton7ActionPerformed

    private List<ModelEmployee>getSelectedData(){
    List<ModelEmployee> list = new ArrayList<>();
    for( int i = 0; i < jTable1.getRowCount(); i++){
        if ((boolean) jTable1.getValueAt(i, 0)){
            ModelEmployee data = (ModelEmployee) jTable1.getValueAt(i, 2);
            list.add(data);
        }
        
    }
    return list;
    
}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    FlatRobotoFont.install();
    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
    FlatMacDarkLaf.setup(); // Change to the dark theme
    FlatLaf.registerCustomDefaultsSource("sample.theme");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new User().setVisible(true);
        }
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private test.Button button2;
    private employees.ButtonAction buttonAction1;
    private employees.ButtonAction buttonAction2;
    private employees.ButtonAction buttonAction3;
    private employees.ButtonAction buttonAction4;
    private employees.ButtonAction buttonAction5;
    private test.HoverButton hoverButton2;
    private test.HoverButton hoverButton3;
    private test.HoverButton hoverButton4;
    private test.HoverButton hoverButton5;
    private test.HoverButton hoverButton6;
    private test.HoverButton hoverButton7;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
}
