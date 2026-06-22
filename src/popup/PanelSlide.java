/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import popup.AppSession;
import ADMIN.Customers;
import ADMIN.Dashboard;
import CAR_BRANDS.BMW;
import CAR_BRANDS.MITSUBISHI;
import popup.ROUNDPANEL;
import customer.ServiceCustomer;
import customers.ModelCustomer1;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.util.Date;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import customers.DatabaseConnection;
import employees.ModelProfile;
import jSCHED.ModelSched;
import jSCHED.ServiceSched;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.picturebox.DefaultPictureBoxRender;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jnafilechooser.api.JnaFileChooser;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import popup.HoverEffect;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.Types;



/**
 *
 * @author ArtoCO
 */

public class PanelSlide extends javax.swing.JPanel {
    private boolean hasLicenseFrontImage;
    private boolean hasLicenseBackImage;
    
private Integer existingCustomerIdForBooking = null;
private boolean updateExistingCustomerMode = false;
    

    public PanelSlide(int step) {
        
        initComponents();
        installInputValidationFilters();

        
        new HoverEffect(jPanel13);
        
        
        pictureBox1.setPictureBoxRender(new DefaultPictureBoxRender(){
            
            @Override
            public Shape render(Rectangle rectangle){
                return createRound(rectangle, UIScale.scale(10));
            }
        });
        pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        jPanel3.putClientProperty(FlatClientProperties.STYLE, ""
            + "border:0,0,0,0,$Component.borderColor,,10;"
            + "background:$TextArea.background");
        
        pictureBox2.setPictureBoxRender(new DefaultPictureBoxRender(){
            
            @Override
            public Shape render(Rectangle rectangle){
                return createRound(rectangle, UIScale.scale(10));
            }
        });
        pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        jPanel4.putClientProperty(FlatClientProperties.STYLE, ""
            + "border:0,0,0,0,$Component.borderColor,,10;"
            + "background:$TextArea.background");
        
      

        lb.setText("Step" + step);
        datePicker1.setCloseAfterSelected(true);
        datePicker1.setEditor(jFormatted);
        datePicker1.setSelectedDate(LocalDate.now());
        datePicker2.setCloseAfterSelected(true);
        datePicker2.setEditor(jFormatted1);
        
        
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info != null) {
            jTextField30.setText(info.getcarname());
            jTextField35.setText(info.getcartype());
            jTextField36.setText(info.getrates());
            jTextField33.setText(info.getmodel());
            jTextField37.setText(info.getseats());
            jTextField39.setText(info.getdiesel());
            jTextField6.setText(info.getdaily());
            jLab.setText(info.getcarPlate());
            jLab1.setText(info.gettransmission_type());
        } else {
        jTextField30.setText("N/A");
        jTextField35.setText("N/A");
        jTextField36.setText("N/A");
        jTextField33.setText("N/A");
        jTextField37.setText("N/A");
        jTextField39.setText("N/A");
        jTextField6.setText("N/A");
        jLab.setText("N/A");
        jLab1.setText("N/A");
        }

        
        Icon transferredIcon = AppSession.getTransferIcon();

        if (transferredIcon != null) {
        jLabel00.setIcon(transferredIcon); // ✅ Set the image
        }
        autoFillExistingCustomerForBooking();
        addDailyPriceRealtimeSync();


    }
    
private Customers customerFrame;

// Constructor that receives the Customers frame
public PanelSlide(Customers customerFrame) {
    this.customerFrame = customerFrame;
    initComponents();
    installInputValidationFilters();
    
   
}

    public PanelSlide() {
        this(1);
    
    }

private Integer getSelectedCustomerIdForSafeUpdate() {
    Integer customerId = AppSession.getSelectedCustomerIdForBooking();
    return customerId != null && customerId > 0 ? customerId : null;
}


    public void loadData(ServiceCustomer service, ModelCustomer1 data) {
    if (data != null) {
        jTextField1.setText(data.getFirstname1());
        jTextField5.setText(data.getLastname1());
        jTextField2.setText(String.valueOf(data.getAge1()));
        jTextField8.setText(data.getPhoneNumber1());
        jTextField10.setText(data.getPrimaryAddress1());

        if (data.getTransactionDate() != null) {
            LocalDate selectedDate = data.getTransactionDate().toLocalDate();
            datePicker1.setSelectedDate(selectedDate);  // ✅ Set the selected date
        }
    }
}

private void autoFillExistingCustomerForBooking() {
    if (!AppSession.isExistingCustomerBookingMode()) {
        return;
    }

    try {
        existingCustomerIdForBooking = AppSession.getSelectedCustomerIdForBooking();
        updateExistingCustomerMode = true;

        ServiceCustomer service = new ServiceCustomer();
        ModelCustomer1 data = service.findById(existingCustomerIdForBooking);

        if (data == null) {
            JOptionPane.showMessageDialog(this, "Selected customer was not found.");
            return;
        }

        // Customer fields only
        jTextField1.setText(data.getFirstname1() == null ? "" : data.getFirstname1());
        jTextField5.setText(data.getLastname1() == null ? "" : data.getLastname1());
        jTextField2.setText(String.valueOf(data.getAge1()));
        jTextField8.setText(data.getPhoneNumber1() == null ? "" : data.getPhoneNumber1());
        jTextField10.setText(data.getPrimaryAddress1() == null ? "" : data.getPrimaryAddress1());

        if (data.getexpire_date() != null) {
    java.time.LocalDate expire = data.getexpire_date().toLocalDate();

    datePicker2.setSelectedDate(expire);

    // ✅ Force text display in jFormatted1
    jFormatted1.setText(expire.toString());
}

        // License fields
        jTextField9.setText(data.getlicense_number() == null ? "" : data.getlicense_number());

        if (data.getexpire_date() != null) {
    java.time.LocalDate expire = data.getexpire_date().toLocalDate();

    datePicker2.setSelectedDate(expire);

    // ✅ Force text display in jFormatted1
    jFormatted1.setText(expire.toString());
}

        jTextField11.setText(data.getagency_code() == null ? "" : data.getagency_code());

        setSelectedCheckBoxesFromText(
                data.getdl_codes(),
                jCheckBox21, jCheckBox22, jCheckBox23,
                jCheckBox25, jCheckBox26, jCheckBox27, jCheckBox28
        );

        setSelectedCheckBoxesFromText(
                data.getvehicle_category(),
                jCheckBox8, jCheckBox9, jCheckBox10, jCheckBox11
        );

        setSelectedCheckBoxesFromText(
                data.getconditions(),
                jCheckBox12, jCheckBox13, jCheckBox14, jCheckBox15, jCheckBox16
        );

        loadPanelSlideLicenseImages(existingCustomerIdForBooking);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Failed to autofill customer:\n" + e.getMessage(),
                "Autofill Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

private void setSelectedCheckBoxesFromText(String savedText, javax.swing.JCheckBox... boxes) {
    for (javax.swing.JCheckBox cb : boxes) {
        cb.setSelected(false);
    }

    if (savedText == null || savedText.trim().isEmpty()) {
        return;
    }

    String[] parts = savedText.split("\\s*\\|\\|\\s*");

    for (String part : parts) {
        String cleanPart = part.trim();

        for (javax.swing.JCheckBox cb : boxes) {
            if (cb.getText() != null && cb.getText().trim().equalsIgnoreCase(cleanPart)) {
                cb.setSelected(true);
            }
        }
    }
}

private void loadPanelSlideLicenseImages(int customerId) {
    String sql = "SELECT driving_license, driving_back FROM customer WHERE customer_Id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                byte[] front = rs.getBytes("driving_license");
                byte[] back = rs.getBytes("driving_back");

                if (front != null && front.length > 0) {
                    pictureBox1.setImage(new ImageIcon(front));
                    hasLicenseFrontImage = true;
                } else {
                    pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
                    hasLicenseFrontImage = false;
                }

                if (back != null && back.length > 0) {
                    pictureBox2.setImage(new ImageIcon(back));
                    hasLicenseBackImage = true;
                } else {
                    pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
                    hasLicenseBackImage = false;
                }

                pictureBox1.revalidate();
                pictureBox1.repaint();

                pictureBox2.revalidate();
                pictureBox2.repaint();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        hasLicenseFrontImage = false;
        hasLicenseBackImage = false;
        pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
    }
}
    
public ModelCustomer1 getData() {
    String firstname = jTextField1.getText().trim();
    String lastname  = jTextField5.getText().trim();
    int Age          = Integer.parseInt(jTextField2.getText().trim());

    String PHONE_NUMBER   = InputRules.digitsOnly(jTextField8.getText());
    jTextField8.setText(PHONE_NUMBER);
    String primaryAddress = jTextField10.getText().trim();   // ✅ use Contact Address field (not jTextField6)

    String trans = jLab1.getText().trim();
    String plate = jLab.getText().trim();

    java.sql.Date transactionDate = java.sql.Date.valueOf(LocalDate.now());

    // ✅ New fields
    String licenseNumber = jTextField9.getText().trim();
    java.sql.Date expireDate = datePicker2.isDateSelected()
            ? java.sql.Date.valueOf(datePicker2.getSelectedDate())
            : null;
    String agencyCode = jTextField11.getText().trim();

    // ✅ Multi-select checkbox strings (use the helper joinSelectedWithOr(...) I gave you earlier)
    String dlCodes = joinSelectedWithOr(
            jCheckBox21, jCheckBox22, jCheckBox23, jCheckBox25, jCheckBox26, jCheckBox27, jCheckBox28
    );

    String vehicleCategory = joinSelectedWithOr(
            jCheckBox8, jCheckBox9, jCheckBox10, jCheckBox11
    );

    String conditions = joinSelectedWithOr(
            jCheckBox12, jCheckBox13, jCheckBox14, jCheckBox15, jCheckBox16
    );

    // ⚠️ Images are BLOBs (byte[]) and your Model currently uses String for driving_license/driving_back,
    // so we keep them as null here and save blobs using UPDATE (like in the SAVE button code).
    String drivingLicense = null;
    String drivingBack = null;

    // Pass null for fields not handled in PanelSlide (paymentType, etc.)
    return new ModelCustomer1(
            0,
            firstname, lastname, Age,
            PHONE_NUMBER, primaryAddress,
            transactionDate,
            null, 0.0,                 // paymentType, totalPayment
            null, null, null,           // InDate, Pickup_time, checkInLocation
            null, null, null,           // CoutDate, Returned_time, checkOutLocation
            null, null, null,           // CarType, CarName, Status
            drivingLicense, drivingBack,
            trans, plate,
            licenseNumber, expireDate,
            agencyCode, dlCodes, vehicleCategory, conditions
    );
}

    
 
    private INVOICES invoicesForm; // Add this as a field in your PanelSlide class

    public PanelSlide(INVOICES invoicesForm) {
    this.invoicesForm = invoicesForm;
    initComponents();
    installInputValidationFilters();

}


    private boolean validateRequiredFields() {
    if (isEmpty(jTextField1, "First Name")) return false;
    if (isEmpty(jTextField5, "Last Name")) return false;
    if (isEmpty(jTextField2, "Age")) return false;
    if (isEmpty(jTextField8, "Phone Number")) return false;
    if (isEmpty(jFormatted, "Transaction Date")) return false;
    if (isEmpty(jTextField10, "Primary Address / Zip Code")) return false;

    
    return true;
}

private boolean isEmpty(javax.swing.JTextField field, String name) {
    if (field.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                name + " cannot be empty!",
                "Missing Field",
                JOptionPane.WARNING_MESSAGE);
        field.requestFocus();
        return true;
    }
    return false;
}


private String joinSelectedWithOr(javax.swing.JCheckBox... boxes) {
    List<String> selected = new ArrayList<>();
    for (javax.swing.JCheckBox cb : boxes) {
        if (cb != null && cb.isSelected()) {
            String t = cb.getText();
            if (t != null && !t.trim().isEmpty()) selected.add(t.trim());
        }
    }
    return selected.isEmpty() ? null : String.join(" || ", selected);
}

private void installInputValidationFilters() {
    InputRules.installPhilippinePhoneFilter(jTextField8);
}

private byte[] iconToPngBytes(Icon icon) throws IOException {
    // If placeholder or no image, return null
    if (icon == null) return null;
    if (!(icon instanceof ImageIcon)) return null;   // FlatSVGIcon etc.

    Image img = ((ImageIcon) icon).getImage();
    if (img == null) return null;

    int w = img.getWidth(null);
    int h = img.getHeight(null);
    if (w <= 0 || h <= 0) return null;

    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bi.createGraphics();
    g2.drawImage(img, 0, 0, null);
    g2.dispose();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bi, "png", baos);
    return baos.toByteArray();
}

private void updateOpenBrandDotsFromDbByPhone(String phoneNumber) {
    try {
        ServiceCustomer svc = new ServiceCustomer();
        ModelCustomer1 m = svc.findByPhoneNumber(phoneNumber);
        if (m == null) return;

        String carName = m.getCarName();
        if (carName == null || carName.trim().isEmpty()) return;

        refreshAllOpenBrandDotsFromDB(carName);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

private void refreshAllOpenBrandDotsFromDB(String carName) {
    if (carName == null || carName.trim().isEmpty()) {
        return;
    }

    for (Frame frame : Frame.getFrames()) {
        if (frame == null || !frame.isDisplayable()) {
            continue;
        }
        try {
            java.lang.reflect.Method method = frame.getClass().getMethod("refreshCarDotFromDB", String.class);
            method.invoke(frame, carName);
        } catch (NoSuchMethodException ignored) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

private void addDailyPriceRealtimeSync() {
    syncDailyPriceToSession();

    jTextField6.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            syncDailyPriceToSession();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            syncDailyPriceToSession();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            syncDailyPriceToSession();
        }
    });
}

private void syncDailyPriceToSession() {
    String daily = jTextField6.getText().trim();

    if (daily.isEmpty() || daily.equalsIgnoreCase("N/A")) {
        daily = "₱ 0.00";
    }

    AppSession2.setDailyPrice(daily);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        datePicker1 = new raven.datetime.component.date.DatePicker();
        datePicker2 = new raven.datetime.component.date.DatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lb = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel13 =  new ROUNDPANEL(20, new Color(153, 153, 153), 2);
        jLabel153 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel00 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        jLabel150 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        jLab = new javax.swing.JLabel();
        jFormatted = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLab1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pictureBox1 = new javaswingdev.picturebox.PictureBox();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        pictureBox2 = new javaswingdev.picturebox.PictureBox();
        jToolBar2 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jFormatted1 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jTextField6 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jCheckBox28 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(27, 26, 26));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1063, 423));

        lb.setBackground(new java.awt.Color(27, 26, 26));
        lb.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lb.setForeground(new java.awt.Color(27, 26, 26));
        lb.setText("Step 1");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Firstname:");

        jTextField1.setBackground(new java.awt.Color(27, 26, 26));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(27, 26, 26));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField5.setBackground(new java.awt.Color(27, 26, 26));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField5FocusLost(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Email (Optional):");

        jTextField7.setBackground(new java.awt.Color(27, 26, 26));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField7FocusLost(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Phone:");

        jTextField8.setBackground(new java.awt.Color(27, 26, 26));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField8FocusLost(evt);
            }
        });
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Transaction Date:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Contact Address:");

        jTextField10.setBackground(new java.awt.Color(27, 26, 26));
        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField10FocusLost(evt);
            }
        });
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Age:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Lastname:");

        jPanel13.setBackground(new java.awt.Color(26, 27, 27));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel153.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-petrol-30.png")); // NOI18N
        jPanel13.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 40, 50));
        jPanel13.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, -1, -1));

        jTextField30.setEditable(false);
        jTextField30.setBackground(new java.awt.Color(26, 27, 27));
        jTextField30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField30.setForeground(new java.awt.Color(255, 255, 255));
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField30.setBorder(null);
        jPanel13.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jLabel00.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel13.add(jLabel00, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 150));

        jTextField33.setBackground(new java.awt.Color(26, 27, 27));
        jTextField33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField33.setForeground(new java.awt.Color(255, 255, 255));
        jTextField33.setBorder(null);
        jPanel13.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 30, 40));
        jPanel13.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 250, 30, 40));
        jPanel13.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jTextField36.setBackground(new java.awt.Color(26, 27, 27));
        jTextField36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField36.setForeground(new java.awt.Color(255, 255, 255));
        jTextField36.setBorder(null);
        jPanel13.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));
        jPanel13.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 190, 40, 60));

        jTextField37.setBackground(new java.awt.Color(26, 27, 27));
        jTextField37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField37.setForeground(new java.awt.Color(255, 255, 255));
        jTextField37.setBorder(null);
        jTextField37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField37ActionPerformed(evt);
            }
        });
        jPanel13.add(jTextField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 216, -1, 20));

        jTextField39.setBackground(new java.awt.Color(26, 27, 27));
        jTextField39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField39.setForeground(new java.awt.Color(255, 255, 255));
        jTextField39.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField39.setBorder(null);
        jPanel13.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 210, -1, 30));
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 255, 51));
        jLabel28.setText(".");
        jPanel13.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField35.setBackground(new java.awt.Color(26, 27, 27));
        jTextField35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField35.setForeground(new java.awt.Color(255, 255, 255));
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField35.setBorder(null);
        jPanel13.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 140, 20));

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle2.setBorder(null);
        rSMaterialButtonRectangle2.setText("RENTING");
        rSMaterialButtonRectangle2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rSMaterialButtonRectangle2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel13.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle3.setBorder(null);
        rSMaterialButtonRectangle3.setText("BOOKING");
        rSMaterialButtonRectangle3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rSMaterialButtonRectangle3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel13.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(191, 290, 120, 40));

        jLabel150.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-date-30.png")); // NOI18N
        jPanel13.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 250, 30, 40));

        jLabel55.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-star-48 (1).png")); // NOI18N
        jPanel13.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel152.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-seat-30.png")); // NOI18N
        jPanel13.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 190, 40, 60));

        jLabel151.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-suv-30.png")); // NOI18N
        jPanel13.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 250, 40, 40));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(103, 103, 103), 1, true));
        rSMaterialButtonRectangle1.setText("SAVE");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });

        jLab.setBackground(new java.awt.Color(27, 26, 26));
        jLab.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLab.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jFormatted.setBackground(new java.awt.Color(27, 26, 26));
        jFormatted.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jFormatted.setText(" ");
        jFormatted.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormatted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Plate Number:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Transmission Type:");

        jLab1.setBackground(new java.awt.Color(27, 26, 26));
        jLab1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLab1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Driver License Front:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("I. DL CODES:");

        jPanel3.setBackground(new java.awt.Color(27, 26, 26));
        jPanel3.setForeground(new java.awt.Color(27, 26, 26));
        jPanel3.setLayout(new java.awt.BorderLayout());

        pictureBox1.setBackground(new java.awt.Color(0, 0, 0));
        pictureBox1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        pictureBox1.setForeground(new java.awt.Color(0, 0, 0));

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        jButton2.setText("Browse");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setText("Delete");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        javax.swing.GroupLayout pictureBox1Layout = new javax.swing.GroupLayout(pictureBox1);
        pictureBox1.setLayout(pictureBox1Layout);
        pictureBox1Layout.setHorizontalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pictureBox1Layout.createSequentialGroup()
                .addContainerGap(488, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pictureBox1Layout.setVerticalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 315, Short.MAX_VALUE))
        );

        jPanel3.add(pictureBox1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(27, 26, 26));
        jPanel4.setForeground(new java.awt.Color(27, 26, 26));
        jPanel4.setLayout(new java.awt.BorderLayout());

        pictureBox2.setBackground(new java.awt.Color(0, 0, 0));
        pictureBox2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        pictureBox2.setForeground(new java.awt.Color(0, 0, 0));

        jToolBar2.setRollover(true);
        jToolBar2.setOpaque(false);

        jButton4.setText("Browse");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        jButton5.setText("Delete");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        javax.swing.GroupLayout pictureBox2Layout = new javax.swing.GroupLayout(pictureBox2);
        pictureBox2.setLayout(pictureBox2Layout);
        pictureBox2Layout.setHorizontalGroup(
            pictureBox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pictureBox2Layout.createSequentialGroup()
                .addContainerGap(488, Short.MAX_VALUE)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pictureBox2Layout.setVerticalGroup(
            pictureBox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox2Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 315, Short.MAX_VALUE))
        );

        jPanel4.add(pictureBox2, java.awt.BorderLayout.CENTER);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Driver License Back:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("VEHICLE CATEGORY");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("II. CONDITIONS: ");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("LICENSE NUMBER:");

        jTextField9.setBackground(new java.awt.Color(27, 26, 26));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField9FocusLost(evt);
            }
        });
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("EXPIRATION DATE:");

        jFormatted1.setBackground(new java.awt.Color(27, 26, 26));
        jFormatted1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jFormatted1.setText(" ");
        jFormatted1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormatted1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormatted1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("AGENCY CODE:");

        jTextField11.setBackground(new java.awt.Color(27, 26, 26));
        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField11FocusLost(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(27, 26, 26));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jCheckBox8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox8.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox8.setText("M1 (Code B)");

        jCheckBox9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox9.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox9.setText("M2 (Code B1)");

        jCheckBox10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox10.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox10.setText("N1 (Code B2)");

        jCheckBox11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox11.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox11.setText("M3 (Code D)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBox9)
                                .addComponent(jCheckBox10))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(jCheckBox11)
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(jCheckBox8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckBox9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckBox10)
                    .addContainerGap(32, Short.MAX_VALUE)))
        );

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jCheckBox12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox12.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox12.setText("Daylight driving only ");

        jCheckBox13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox13.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox13.setText("Drive customized motor vehicle only");

        jCheckBox14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox14.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox14.setText("Drive only w/ special equipment for upper/lower");

        jCheckBox15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox15.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox15.setText("Wear corrective lenses");

        jCheckBox16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox16.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox16.setText("Hearing aid required");
        jCheckBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox16)
                .addContainerGap())
        );

        jTextField6.setBackground(new java.awt.Color(30, 30, 30));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(30, 30, 30));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setBorder(null);
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField6FocusLost(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(27, 26, 26));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jCheckBox21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox21.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox21.setText("B – Up to 5000 KGS GVW / 8 Seats");

        jCheckBox22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox22.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox22.setText("B1 – Up to 5000 KGS GVW / 9 or more seats");

        jCheckBox23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox23.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox23.setText("B2 – Goods < 3500 KGS GVW");

        jCheckBox25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox25.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox25.setText("C – Goods > 3500 KGS GVW");

        jCheckBox26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox26.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox26.setText("D – Bus > 3500 KGS GVW / 9 or more seats");

        jCheckBox27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox27.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox27.setText("BE – Trailers > 3500 KGS");

        jCheckBox28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox28.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox28.setText("CE – Articulated C > 3500 KGS combined GVW");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckBox22, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addComponent(jCheckBox23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jCheckBox25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jCheckBox21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox28)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormatted)))
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLab1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormatted1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 26, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(373, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(23, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(376, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(16, 16, 16)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel10)))
                                        .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(35, 35, 35)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(35, 35, 35)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jFormatted, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLab, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLab1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jLabel16)
                            .addGap(38, 38, 38)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jFormatted1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(539, 539, 539)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(398, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(931, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jTextField7.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 460));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        // TODO add your handling code here:

 
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField5FocusGained

    private void jTextField5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField5FocusLost

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained
        // TODO add your handling code here:
        if(jTextField7.getText().equals("@hotmail or @gmail"))
        {
            jTextField7.setText("");
            jTextField7.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_jTextField7FocusGained

    private void jTextField7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        // TODO add your handling code here:
        if(jTextField7.getText().equals(""))
        {
            jTextField7.setText("@hotmail or @gmail");
            jTextField7.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jTextField7FocusLost

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField8FocusLost

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusGained
        // TODO add your handling code here:

    
    }//GEN-LAST:event_jTextField10FocusGained

    private void jTextField10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField10FocusLost

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jFormattedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedActionPerformed

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6FocusLost

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
                                                      
    if (!validateRequiredFields()) return;

    try {
        // ===============================
        // 1) BASIC FIELD VALIDATION
        // ===============================
        String first = jTextField1.getText().trim();
        String last  = jTextField5.getText().trim();
        String ageText = jTextField2.getText().trim();
        String phone = InputRules.digitsOnly(jTextField8.getText());
        jTextField8.setText(phone);
        String address = jTextField10.getText().trim();

        if (first.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name is required.");
            jTextField1.requestFocus();
            return;
        }

        if (last.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name is required.");
            jTextField5.requestFocus();
            return;
        }

        if (ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Age is required.");
            jTextField2.requestFocus();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.");
            jTextField2.requestFocus();
            return;
        }

        if (age < 18 || age > 100) {
            JOptionPane.showMessageDialog(this, "Age must be between 18 and 100.");
            jTextField2.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone Number is required.");
            jTextField8.requestFocus();
            return;
        }

        if (!InputRules.isValidPhilippinePhone(phone)) {
            JOptionPane.showMessageDialog(this, "Phone Number must be 11 digits and start with 09.");
            jTextField8.requestFocus();
            return;
        }

        LocalDate today = LocalDate.now();
        if (!datePicker1.isDateSelected()) {
            datePicker1.setSelectedDate(today);
        }

        LocalDate selectedTransactionDate = datePicker1.getSelectedDate();
        if (!today.equals(selectedTransactionDate)) {
            JOptionPane.showMessageDialog(this, "Transaction Date must be today's date.");
            datePicker1.setSelectedDate(today);
            jFormatted.requestFocus();
            return;
        }

        java.sql.Date transactionDate = java.sql.Date.valueOf(today);

        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contact Address is required.");
            jTextField10.requestFocus();
            return;
        }

        String email = jTextField7.getText().trim();
        if (!email.isEmpty()
                && !email.equalsIgnoreCase("@hotmail or @gmail")
                && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Email format is invalid.");
            jTextField7.requestFocus();
            return;
        }

        // ===============================
        // 2) CAR INFO VALIDATION
        // ===============================
        String carName = jTextField30.getText().trim();
        String carType = jTextField35.getText().trim();
        String rates   = jTextField36.getText().trim();
        String model   = jTextField33.getText().trim();
        String seats   = jTextField37.getText().trim();
        String diesel  = jTextField39.getText().trim();
        String daily   = jTextField6.getText().trim();

        String trans   = jLab1.getText().trim();
        String plateNo = jLab.getText().trim();

        if (carName.isEmpty() || carName.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Vehicle Name is missing.");
            return;
        }

        if (carType.isEmpty() || carType.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Vehicle Type is missing.");
            return;
        }

        if (rates.isEmpty() || rates.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Rates is missing.");
            return;
        }

        if (model.isEmpty() || model.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Model is missing.");
            return;
        }

        if (seats.isEmpty() || seats.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Seats is missing.");
            return;
        }

        if (diesel.isEmpty() || diesel.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Fuel Type is missing.");
            return;
        }

        if (daily.isEmpty() || daily.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Daily Rent Price is missing.");
            return;
        }

        if (plateNo.isEmpty() || plateNo.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Plate Number is missing.");
            return;
        }

        if (trans.isEmpty() || trans.equalsIgnoreCase("N/A")) {
            JOptionPane.showMessageDialog(this, "Transmission Type is missing.");
            return;
        }

        // ===============================
        // 3) LICENSE VALIDATION
        // ===============================
        String licenseNumber = jTextField9.getText().trim();

        if (licenseNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "License Number is required.");
            jTextField9.requestFocus();
            return;
        }

        if (!datePicker2.isDateSelected()) {
            JOptionPane.showMessageDialog(this, "License Expiration Date is required.");
            jFormatted1.requestFocus();
            return;
        }

        java.sql.Date expireDate = java.sql.Date.valueOf(datePicker2.getSelectedDate());
        java.time.LocalDate licenseExpireDate = expireDate.toLocalDate();

        if (!licenseExpireDate.isAfter(today)) {
            JOptionPane.showMessageDialog(this,
                    "License is expired or expires today.\nPlease select a future expiration date.",
                    "Invalid License Expiration Date",
                    JOptionPane.ERROR_MESSAGE);
            jFormatted1.requestFocus();
            return;
        }

        String agencyCode = jTextField11.getText().trim();

        if (agencyCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agency Code is required.");
            jTextField11.requestFocus();
            return;
        }

        // ===============================
        // 4) CHECKBOX VALIDATION
        // ===============================
        String dlCodes = joinSelectedWithOr(
                jCheckBox21, jCheckBox22, jCheckBox23,
                jCheckBox25, jCheckBox26, jCheckBox27, jCheckBox28
        );

        if (dlCodes == null || dlCodes.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one DL Code.");
            return;
        }

        String vehicleCategory = joinSelectedWithOr(
                jCheckBox8, jCheckBox9, jCheckBox10, jCheckBox11
        );

        if (vehicleCategory == null || vehicleCategory.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one Vehicle Category.");
            return;
        }

        String conditions = joinSelectedWithOr(
                jCheckBox12, jCheckBox13, jCheckBox14, jCheckBox15, jCheckBox16
        );

        if (conditions == null || conditions.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one Condition.");
            return;
        }

        // ===============================
        // 5) DRIVER LICENSE IMAGE VALIDATION
        // ===============================
        byte[] dlFrontBytes = iconToPngBytes(pictureBox1.getImage());
        byte[] dlBackBytes  = iconToPngBytes(pictureBox2.getImage());

        if (!hasLicenseFrontImage || dlFrontBytes == null) {
            JOptionPane.showMessageDialog(this, "Driver License Front image is required.");
            return;
        }

        if (!hasLicenseBackImage || dlBackBytes == null) {
            JOptionPane.showMessageDialog(this, "Driver License Back image is required.");
            return;
        }

        // ===============================
        // 6) CHECK IF THIS IS EXISTING CUSTOMER FLOW
        // ===============================
        boolean updateExisting = AppSession.isExistingCustomerBookingMode()
                && AppSession.getSelectedCustomerIdForBooking() != null
                && AppSession.getSelectedCustomerIdForBooking() > 0;

        Integer selectedCustomerId = AppSession.getSelectedCustomerIdForBooking();
        Integer savedCustomerId = selectedCustomerId;

        // ===============================
        // 7) DUPLICATE PHONE CHECK
        // ===============================
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPhone = con.prepareStatement(
                     updateExisting
                             ? "SELECT COUNT(*) FROM customer WHERE phone_number = ? AND customer_Id <> ?"
                             : "SELECT COUNT(*) FROM customer WHERE phone_number = ?")) {

            checkPhone.setString(1, phone);

            if (updateExisting) {
                checkPhone.setInt(2, selectedCustomerId);
            }

            try (ResultSet rs = checkPhone.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this,
                            "This phone number already exists in another customer record.",
                            "Duplicate Customer",
                            JOptionPane.WARNING_MESSAGE);
                    jTextField8.requestFocus();
                    return;
                }
            }
        }

        // ===============================
        // 8) UPDATE EXISTING OR CREATE NEW
        // ===============================
        if (updateExisting) {

            String updateSql =
                    "UPDATE customer SET " +
                    "customer_firstname = ?, " +
                    "customer_lastname = ?, " +
                    "Age = ?, " +
                    "phone_number = ?, " +
                    "primary_address = ?, " +
                    "transaction_Date = ?, " +
                    "vehicle_type = ?, " +
                    "vehicle_name = ?, " +
                    "transmission_type = ?, " +
                    "plate_number = ?, " +
                    "license_number = ?, " +
                    "expire_date = ?, " +
                    "agency_code = ?, " +
                    "dl_codes = ?, " +
                    "vehicle_category = ?, " +
                    "conditions = ?, " +
                    "driving_license = ?, " +
                    "driving_back = ? " +
                    "WHERE customer_Id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(updateSql)) {

                int i = 1;

                ps.setString(i++, first);
                ps.setString(i++, last);
                ps.setInt(i++, age);
                ps.setString(i++, phone);
                ps.setString(i++, address);
                ps.setDate(i++, transactionDate);

                // selected car from Mitsubishi or other brand frame
                ps.setString(i++, carType);
                ps.setString(i++, carName);
                ps.setString(i++, trans);
                ps.setString(i++, plateNo);

                // license info
                ps.setString(i++, licenseNumber);
                ps.setDate(i++, expireDate);
                ps.setString(i++, agencyCode);
                ps.setString(i++, dlCodes);
                ps.setString(i++, vehicleCategory);
                ps.setString(i++, conditions);

                // license images
                ps.setBytes(i++, dlFrontBytes);
                ps.setBytes(i++, dlBackBytes);

                ps.setInt(i++, selectedCustomerId);

                int updated = ps.executeUpdate();

                if (updated <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Customer was not updated.",
                            "Update Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

        } else {

            // NORMAL NEW CUSTOMER FLOW
            ModelCustomer1 customer = new ModelCustomer1(
                    0, first, last, age, phone, address,
                    transactionDate,
                    null, 0.0,
                    null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null,
                    trans, plateNo,
                    null, null, null, null, null, null
            );

            ServiceCustomer service = new ServiceCustomer();
            savedCustomerId = service.createCompleteBooking(
                    customer, carType, carName, trans, plateNo,
                    licenseNumber, expireDate, agencyCode, dlCodes,
                    vehicleCategory, conditions, dlFrontBytes, dlBackBytes);
        }

        // ===============================
        // 9) SAVE RECEIPT / SESSION INFO
        // ===============================
        String transDateStr = transactionDate.toString();

        String formattedDate = "N/A";
        String formattedDateOut = "N/A";

        String rentPay = "N/A";
        String rentInsurance = "N/A";
        String vat = "N/A";
        String driverFee = "N/A";
        String fullPay = "N/A";
        String partialPay = "N/A";

        CustomerInfo info = new CustomerInfo(
                first, last, address, phone,
                carName, carType,
                plateNo,
                transDateStr,
                rates, model, seats, diesel, daily,
                formattedDate, formattedDateOut,
                rentPay, rentInsurance, vat, driverFee, fullPay,
                partialPay,
                trans
        );

        AppSession2.setCustomerInfo(info);
        AppSession2.setCustomerId(savedCustomerId);
        AppSession2.setDailyPrice(daily);

        // ===============================
        // 10) UPDATE CAR DOT
        // ===============================
        refreshAllOpenBrandDotsFromDB(carName);

        javax.swing.Timer t = new javax.swing.Timer(120, e -> {
            JOptionPane.showMessageDialog(this,
                    updateExisting
                            ? "Existing customer updated successfully!"
                            : "Customer saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        t.setRepeats(false);
        t.start();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "System Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
        String status = "RENTED";
    String phoneNumber = jTextField8.getText().trim();

    if (phoneNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Phone number is missing. Please save/select customer first.");
        return;
    }

    try {
        ServiceCustomer service = new ServiceCustomer();
        Integer customerId = getSelectedCustomerIdForSafeUpdate();
        if (customerId != null) {
            service.updateStatusByCustomerId(customerId, status);
        } else {
            service.updateStatusByUniquePhone(phoneNumber, status);
        }

        updateOpenBrandDotsFromDbByPhone(phoneNumber);

        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof Dashboard) {
                Dashboard dash = (Dashboard) frame;
                dash.loadPendingCount();
            }
        }

        JOptionPane.showMessageDialog(this, "Status updated to RENTED!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error while updating Status: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        // TODO add your handling code here:
        String status = "BOOKED";
    String phoneNumber = jTextField8.getText().trim();

    if (phoneNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Phone number is missing. Please save/select customer first.");
        return;
    }

    try (Connection con = DatabaseConnection.getConnection()) {

        String currentStatus = null;
        Integer customerId = getSelectedCustomerIdForSafeUpdate();

        String statusSql = customerId != null
                ? "SELECT status FROM customer WHERE customer_Id = ?"
                : "SELECT status FROM customer WHERE phone_number = ?";

        try (PreparedStatement getStatus = con.prepareStatement(statusSql)) {

            if (customerId != null) {
                getStatus.setInt(1, customerId);
            } else {
                getStatus.setString(1, phoneNumber);
            }

            try (ResultSet rs = getStatus.executeQuery()) {
                if (rs.next()) {
                    currentStatus = rs.getString("status");
                }
            }
        }

        ServiceCustomer service = new ServiceCustomer();
        if (customerId != null) {
            service.updateStatusByCustomerId(customerId, status);
        } else {
            service.updateStatusByUniquePhone(phoneNumber, status);
        }

        updateOpenBrandDotsFromDbByPhone(phoneNumber);

        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof Dashboard) {
                Dashboard dash = (Dashboard) frame;
                dash.loadBookedCount();

                if (currentStatus == null || currentStatus.trim().isEmpty()) {
                    dash.loadPendingCount();
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Status updated to BOOKED!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error while updating Status: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void jTextField37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // add pic
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("image","jpg", "png");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act){
            File file = ch.getSelectedFile();
            pictureBox1.setImage(new ImageIcon(file.getAbsolutePath()));
            hasLicenseFrontImage = true;
 
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        hasLicenseFrontImage = false;

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
                JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("image","jpg", "png");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act){
            File file = ch.getSelectedFile();
            pictureBox2.setImage(new ImageIcon(file.getAbsolutePath()));
            hasLicenseBackImage = true;
 
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
            pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
            hasLicenseBackImage = false;
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9FocusGained

    private void jTextField9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9FocusLost

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jFormatted1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormatted1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormatted1ActionPerformed

    private void jTextField11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusGained

    private void jTextField11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusLost

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jCheckBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox16ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.datetime.component.date.DatePicker datePicker1;
    private raven.datetime.component.date.DatePicker datePicker2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JFormattedTextField jFormatted;
    private javax.swing.JFormattedTextField jFormatted1;
    private javax.swing.JLabel jLab;
    private javax.swing.JLabel jLab1;
    private javax.swing.JLabel jLabel00;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lb;
    private javaswingdev.picturebox.PictureBox pictureBox1;
    private javaswingdev.picturebox.PictureBox pictureBox2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    // End of variables declaration//GEN-END:variables

    public void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void addWindowListener(WindowAdapter windowAdapter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
