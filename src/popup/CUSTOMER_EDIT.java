/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import ADMIN.Customers;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import customer.ServiceCustomer;
import customers.ModelCustomer1;
import jSCHED.ModelSched;
import jSCHED.ServiceSched;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import raven.popup.GlassPanePopup;
import raven.progressindicator.MapCustom;
import raven.progressindicator.MapCustom1;
import raven.toast.Notifications;
import customers.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.nio.file.Files;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.nio.file.Files;



/**
 *
 * @author ArtoCO
 */
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import jnafilechooser.api.JnaFileChooser;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;


public class CUSTOMER_EDIT extends javax.swing.JPanel {

    private MapCustom editPickupMap;
    private MapCustom editReturnMap;
    private ModelCustomer1 originalCustomer;
    private final ServiceCustomer service = new ServiceCustomer();
    private Customers callbackForm;
    private int editingId;
    private double basePayment = 0; // Original DB value — always constant
    
private File selectedFrontFile;
private File selectedBackFile;
private byte[] currentFrontBytes;
private byte[] currentBackBytes;

private byte[] licenseFrontBytes;
private byte[] licenseBackBytes;
private boolean deleteFront = false;
private boolean deleteBack  = false;

public byte[] getLicenseFrontBytes() { return licenseFrontBytes; }
public byte[] getLicenseBackBytes()  { return licenseBackBytes; }
public boolean isDeleteFront() { return deleteFront; }
public boolean isDeleteBack()  { return deleteBack; }

private final Icon PLACEHOLDER_ICON = new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg");

  
    public CUSTOMER_EDIT() {
        initComponents();
        installInputValidationFilters();
        

timePicker1.setEditor(jFormattedTextField4);   // PICKUP TIME field
timePicker2.setEditor(jFormattedTextField5);   // RETURN TIME field

       
    jComboBox1.addActionListener(e -> {
        int selectedIndex = jComboBox1.getSelectedIndex();

        double newAmount = basePayment;  // always calculate from base

        if (selectedIndex == 0) { // FULL PAYMENT
            newAmount = basePayment * 2;
        } else if (selectedIndex == 1) { // PARTIAL PAYMENT
            newAmount = basePayment;
        }

        jTextField13.setText(formatCurrency(newAmount));
    });

        // Date pickers
        datePicker1.setCloseAfterSelected(true);
        datePicker1.setEditor(jFormattedTextField1);

        datePicker2.setCloseAfterSelected(true);
        datePicker2.setEditor(jFormattedTextField2);

        datePicker3.setCloseAfterSelected(true);
        datePicker3.setEditor(jFormattedTextField3);

        // Map location for check-in
        editPickupMap = new MapCustom();
        editPickupMap.setRouteRole(MapCustom.RouteRole.EDIT_PICKUP);
        editPickupMap.setPreferredSize(new java.awt.Dimension(555, 308));
        editPickupMap.setMaximumSize(new java.awt.Dimension(555, 308));
        editPickupMap.setMinimumSize(new java.awt.Dimension(555, 308));
        editPickupMap.setEvent(location -> jTextField5.setText(location));
        editPickupMap.init();
        mapCustom1.setLayout(new java.awt.BorderLayout());
        mapCustom1.add(editPickupMap, java.awt.BorderLayout.CENTER);

        // Map location for check-out
        editReturnMap = new MapCustom();
        editReturnMap.setRouteRole(MapCustom.RouteRole.EDIT_RETURN);
        editReturnMap.setPreferredSize(new java.awt.Dimension(555, 308));
        editReturnMap.setMaximumSize(new java.awt.Dimension(555, 308));
        editReturnMap.setMinimumSize(new java.awt.Dimension(555, 308));
        editReturnMap.setEvent(location -> jTextField10.setText(location));
        editReturnMap.init();
        mapCustom2.setLayout(new java.awt.BorderLayout());
        mapCustom2.add(editReturnMap, java.awt.BorderLayout.CENTER);
    }
    
    private String formatCurrency(double amount) {
    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
    return "₱ " + df.format(amount);
}


private double parseCurrency(String text) {
    return Double.parseDouble(text.replaceAll("[₱,]", ""));
}

public byte[] getFrontImageBytesForSave() throws Exception {
    if (selectedFrontFile != null) {
        return Files.readAllBytes(selectedFrontFile.toPath());
    }
    return currentFrontBytes;   // can be null if deleted
}

public byte[] getBackImageBytesForSave() throws Exception {
    if (selectedBackFile != null) {
        return Files.readAllBytes(selectedBackFile.toPath());
    }
    return currentBackBytes;    // can be null if deleted
}


private java.sql.Date parseSqlDateOrNull(String text) {
    String t = (text == null) ? "" : text.trim();
    if (t.isEmpty()) return null;
    return java.sql.Date.valueOf(LocalDate.parse(t, dtf));   // dtf = dd/MM/yyyy
}

private String normalizeMoney(String text) {
    return text == null ? "" : text.replaceAll("[^0-9.]", "").trim();
}

private boolean showValidationWarning(String message) {
    JOptionPane.showMessageDialog(
            this,
            message,
            "Invalid Customer Details",
            JOptionPane.WARNING_MESSAGE
    );
    return false;
}

private boolean validateDateField(String label, String value) {
    String text = value == null ? "" : value.trim();
    if (text.isEmpty()) {
        return showValidationWarning(label + " is required. Use dd/MM/yyyy.");
    }
    try {
        LocalDate.parse(text, dtf);
        return true;
    } catch (Exception ex) {
        return showValidationWarning(label + " must be a valid date in dd/MM/yyyy format.");
    }
}

private boolean validateIntegerField(String label, String value) {
    String text = value == null ? "" : value.trim();
    if (text.isEmpty()) {
        return showValidationWarning(label + " is required.");
    }
    try {
        if (Integer.parseInt(text) < 0) {
            return showValidationWarning(label + " cannot be negative.");
        }
        return true;
    } catch (NumberFormatException ex) {
        return showValidationWarning(label + " must be a whole number.");
    }
}

private boolean validateMoneyField(String label, String value) {
    String text = normalizeMoney(value);
    if (text.isEmpty()) {
        return showValidationWarning(label + " is required.");
    }
    try {
        if (Double.parseDouble(text) < 0) {
            return showValidationWarning(label + " cannot be negative.");
        }
        return true;
    } catch (NumberFormatException ex) {
        return showValidationWarning(label + " must be a valid amount.");
    }
}

public boolean validateBeforeSave() {
    if (!validateDateField("Transaction date", jFormattedTextField3.getText())) return false;
    if (!validateDateField("Pickup date", jFormattedTextField1.getText())) return false;
    if (!validateDateField("Return date", jFormattedTextField2.getText())) return false;
    if (!validateDateField("License expire date", jFormattedTextField6.getText())) return false;
    if (!validateIntegerField("Age", jTextField3.getText())) return false;
    String phone = InputRules.digitsOnly(jTextField4.getText());
    jTextField4.setText(phone);
    if (!InputRules.isValidPhilippinePhone(phone)) {
        return showValidationWarning("Phone Number must be 11 digits and start with 09.");
    }
    String plate = InputRules.normalizePlate(jTextField11.getText());
    jTextField11.setText(plate);
    if (!InputRules.isValidPlate(plate)) {
        return showValidationWarning("Plate number can only contain letters, numbers, spaces, and hyphen (-).");
    }
    if (jComboBox1.getSelectedItem() == null
            || jComboBox1.getSelectedItem().toString().trim().isEmpty()) {
        return showValidationWarning("Payment type is required.");
    }
    if (!validateMoneyField("Total payment", jTextField13.getText())) return false;
    return true;
}

private void installInputValidationFilters() {
    InputRules.installPhilippinePhoneFilter(jTextField4);
    InputRules.installPlateFilter(jTextField11);
}

private String formatDateOrEmpty(java.sql.Date d) {
    if (d == null) return "";
    return d.toLocalDate().format(dtf);
}

private byte[] readBytesOrNull(File f) throws IOException {
    if (f == null) return null;
    return Files.readAllBytes(f.toPath());
}


private void loadLicenseImagesFromDb(int customerId) {
    String sql = "SELECT driving_license, driving_back FROM customer WHERE customer_id = ?";

    try (Connection con = customers.DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                licenseFrontBytes = rs.getBytes("driving_license");
                licenseBackBytes  = rs.getBytes("driving_back");

                // reset delete flags when loading
                deleteFront = false;
                deleteBack  = false;

                if (licenseFrontBytes != null && licenseFrontBytes.length > 0) {
                    pictureBox1.setImage(new ImageIcon(licenseFrontBytes));
                } else {
                    pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
                }

                if (licenseBackBytes != null && licenseBackBytes.length > 0) {
                    pictureBox2.setImage(new ImageIcon(licenseBackBytes));
                } else {
                    pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        // fallback placeholder if error
        pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
    }
}


public String getTransmissionType() {
    return jTextField16.getText() == null ? "" : jTextField16.getText().trim();
}


public void setEditData(ModelCustomer1 data) {
    this.originalCustomer = data;
    this.editingId = data.getCustomerId();

    // Dates (existing)
    jFormattedTextField3.setText(formatDateOrEmpty(data.getTransactionDate()));
    jFormattedTextField2.setText(formatDateOrEmpty(data.getOutDate()));
    jFormattedTextField1.setText(formatDateOrEmpty(data.geInDate()));

    // Customer (existing)
    jTextField1.setText(data.getFirstname1());
    jTextField2.setText(data.getLastname1());
    jTextField3.setText(String.valueOf(data.getAge1()));
    jTextField4.setText(data.getPhoneNumber1());
    jTextField7.setText(data.getPrimaryAddress1());

    // Locations (existing)
    jTextField10.setText(data.getOutLocation());
    jTextField5.setText(data.getInLocation());
    BookingRouteState.clear();
    editReturnMap.setSavedRouteLocations(data.getInLocation(), data.getOutLocation(), editPickupMap);

    // Vehicle info (existing)
    jTextField9.setText(data.getCarType());
    jTextField8.setText(data.getCarName());
    setStatusComboValue(data.getStatus());
    jComboBox1.setSelectedItem(data.getPaymentType());

    // Payments (existing logic)
    if (data.getPaymentType() != null && data.getPaymentType().equalsIgnoreCase("FULL PAYMENT")) {
        basePayment = data.getTotalPayment() / 2;
        jTextField13.setText(formatCurrency(basePayment * 2));
    } else {
        basePayment = data.getTotalPayment();
        jTextField13.setText(formatCurrency(basePayment));
    }

    // Times (existing)
    jFormattedTextField4.setText(data.getPickup_time() == null ? "" : data.getPickup_time());
    jFormattedTextField5.setText(data.getReturned_time() == null ? "" : data.getReturned_time());

    // ✅ NEW FIELDS (load/display)
    jTextField11.setText(data.getplate_number() == null ? "" : data.getplate_number());           // plate number
    jFormattedTextField6.setText(formatDateOrEmpty(data.getexpire_date()));                        // expire date
    jTextField14.setText(data.getagency_code() == null ? "" : data.getagency_code());             // agency
    jTextField15.setText(data.getlicense_number() == null ? "" : data.getlicense_number());       // license no.
    jTextField16.setText(data.gettransmission_type() == null ? "" : data.gettransmission_type()); // transmission
    jTextField17.setText(data.getdl_codes() == null ? "" : data.getdl_codes());                   // dl codes
    jTextField18.setText(data.getvehicle_category() == null ? "" : data.getvehicle_category());   // vehicle category
    jTextField19.setText(data.getconditions() == null ? "" : data.getconditions());               // conditions

    // ✅ LOAD/DISPLAY BLOB IMAGES directly from DB
    loadLicenseImagesFromDb(editingId);
}


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

public ModelCustomer1 getData() {
    String pickupTime = jFormattedTextField4.getText().trim();
    if (pickupTime.isEmpty()) pickupTime = null;

    String returnTime = jFormattedTextField5.getText().trim();
    if (returnTime.isEmpty()) returnTime = null;

    java.sql.Date transactionDate = parseSqlDateOrNull(jFormattedTextField3.getText());
    java.sql.Date inDate          = parseSqlDateOrNull(jFormattedTextField1.getText());
    java.sql.Date outDate         = parseSqlDateOrNull(jFormattedTextField2.getText());

    // ✅ NEW fields
    String plateNumber     = InputRules.normalizePlate(jTextField11.getText());
    jTextField11.setText(plateNumber);
    java.sql.Date expireDt = parseSqlDateOrNull(jFormattedTextField6.getText());
    String agencyCode      = jTextField14.getText().trim();
    String licenseNumber   = jTextField15.getText().trim();
    String transmission    = jTextField16.getText().trim();
    String dlCodes         = jTextField17.getText().trim();
    String vehicleCategory = jTextField18.getText().trim();
    String conditions      = jTextField19.getText().trim();

    // keep model strings (your DB images are handled separately as BLOB)
    String frontStr = (originalCustomer != null) ? originalCustomer.getdriving_license() : null;
    String backStr  = (originalCustomer != null) ? originalCustomer.getdriving_back() : null;

    String selectedStatus = jComboBox2.getSelectedItem() == null
        ? "BOOKED"
        : jComboBox2.getSelectedItem().toString().trim().toUpperCase();
    
    return new ModelCustomer1(
        editingId,
        jTextField1.getText(),
        jTextField2.getText(),
        Integer.parseInt(jTextField3.getText()),
        InputRules.digitsOnly(jTextField4.getText()),
        jTextField7.getText(),
        transactionDate,
        jComboBox1.getSelectedItem().toString(),
        Double.parseDouble(normalizeMoney(jTextField13.getText())),

        inDate,
        pickupTime,
        jTextField5.getText(),

        outDate,
        returnTime,
        jTextField10.getText(),

        jTextField9.getText(),
        jTextField8.getText(),
        selectedStatus,

        frontStr,
        backStr,

        transmission,
        plateNumber,
        licenseNumber,
        expireDt,
        agencyCode,
        dlCodes,
        vehicleCategory,
        conditions
    );
}

public void commitMapSelectionsAfterUpdate() {
    editPickupMap.commitSavedSelection();
    editReturnMap.commitSavedSelection();
}


public void reloadLicenseImages() {
    loadLicenseImagesFromDb(editingId);
}

    public CUSTOMER_EDIT(Customers callbackForm) {
        this();
        this.callbackForm = callbackForm;
    }

    private String getCurrentTime12HrFormat() {
        java.time.LocalTime now = java.time.LocalTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("h:mm a");
        return now.format(formatter);
    }

    
private void setStatusComboValue(String status) {
    if (status == null || status.trim().isEmpty()) {
        jComboBox2.setSelectedItem("BOOKED");
        return;
    }

    String cleanStatus = status.trim().toUpperCase();

    for (int i = 0; i < jComboBox2.getItemCount(); i++) {
        String item = jComboBox2.getItemAt(i).toString();

        if (item.equalsIgnoreCase(cleanStatus)) {
            jComboBox2.setSelectedItem(item);
            return;
        }
    }

    // If database has a status not yet in the combo box, add it safely
    jComboBox2.addItem(cleanStatus);
    jComboBox2.setSelectedItem(cleanStatus);
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
        datePicker3 = new raven.datetime.component.date.DatePicker();
        timePicker1 = new raven.datetime.component.time.TimePicker();
        timePicker2 = new raven.datetime.component.time.TimePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        mapCustom1 = new raven.progressindicator.MapCustom();
        jTextField10 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        mapCustom2 = new raven.progressindicator.MapCustom();
        jLabel30 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        pictureBox1 = new javaswingdev.picturebox.PictureBox();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        pictureBox2 = new javaswingdev.picturebox.PictureBox();
        jToolBar2 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(622, 600));
        setPreferredSize(new java.awt.Dimension(622, 614));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField3.setBackground(new java.awt.Color(27, 26, 26));
        jTextField3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 132, 221, 27));

        jLabel144.setBackground(new java.awt.Color(102, 102, 102));
        jLabel144.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("CUSTOMER INFORMATION");
        jLabel144.setToolTipText("");
        jLabel144.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel144.setOpaque(true);
        jPanel2.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("FIRSTNAME");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 256, 20));

        jTextField7.setBackground(new java.awt.Color(27, 26, 26));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jTextField7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 197, 570, 27));

        jTextField1.setBackground(new java.awt.Color(27, 26, 26));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 66, 221, 27));

        jTextField5.setBackground(new java.awt.Color(27, 26, 26));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2010, 570, 27));

        jTextField2.setBackground(new java.awt.Color(27, 26, 26));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 66, 221, 27));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("PICKUP LOCATION");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1990, 221, 20));

        jTextField8.setBackground(new java.awt.Color(27, 26, 26));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jTextField8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 221, 27));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("VEHICLE NAME");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 221, 20));

        jTextField4.setBackground(new java.awt.Color(27, 26, 26));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 132, 221, 27));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("PHONE NUMBER");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 106, 221, 20));

        jTextField9.setBackground(new java.awt.Color(27, 26, 26));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(255, 255, 255));
        jTextField9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 221, 27));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("PRIMARY ADDRESS");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 171, 221, 20));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("VEHICLE TYPE");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 186, 20));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("LASTNAME");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 221, 20));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("AGE");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 106, 221, 20));

        jLabel146.setBackground(new java.awt.Color(102, 102, 102));
        jLabel146.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel146.setText("RENTAL PERIOD AND LOCATION");
        jLabel146.setToolTipText("");
        jLabel146.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel146.setOpaque(true);
        jPanel2.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1620, 610, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("PICKUP DATE");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2050, 221, 20));

        mapCustom1.setPreferredSize(new java.awt.Dimension(589, 308));

        javax.swing.GroupLayout mapCustom1Layout = new javax.swing.GroupLayout(mapCustom1);
        mapCustom1.setLayout(mapCustom1Layout);
        mapCustom1Layout.setHorizontalGroup(
            mapCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        mapCustom1Layout.setVerticalGroup(
            mapCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

        jPanel2.add(mapCustom1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1670, 580, -1));

        jTextField10.setBackground(new java.awt.Color(27, 26, 26));
        jTextField10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 255, 255));
        jTextField10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2485, 570, 27));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("RETURN DATE");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2524, 221, 20));

        mapCustom2.setPreferredSize(new java.awt.Dimension(900, 405));

        javax.swing.GroupLayout mapCustom2Layout = new javax.swing.GroupLayout(mapCustom2);
        mapCustom2.setLayout(mapCustom2Layout);
        mapCustom2Layout.setHorizontalGroup(
            mapCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        mapCustom2Layout.setVerticalGroup(
            mapCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

        jPanel2.add(mapCustom2, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 2140, 580, 308));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("RETURN LOCATION");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2459, 221, 20));

        jLabel147.setBackground(new java.awt.Color(102, 102, 102));
        jLabel147.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel147.setText("PAYMENTS");
        jLabel147.setToolTipText("");
        jLabel147.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel147.setOpaque(true);
        jPanel2.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 2620, 610, -1));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("PAYMENT TYPE");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2660, 221, 20));

        jTextField13.setBackground(new java.awt.Color(27, 26, 26));
        jTextField13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(255, 255, 255));
        jTextField13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2690, 221, 27));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("TOTAL PAYMENT");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2660, 221, 20));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("TRANSACTION DATE");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2730, 221, 20));

        jFormattedTextField1.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField1.setText(" ");
        jFormattedTextField1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2080, 221, 26));

        jFormattedTextField2.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField2.setText(" ");
        jFormattedTextField2.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2550, 221, 26));

        jFormattedTextField3.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField3.setText(" ");
        jFormattedTextField3.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField3ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2760, 222, 26));

        jComboBox1.setBackground(new java.awt.Color(27, 26, 26));
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FULL PAYMENT", "PARTIAL PAYMENT" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2690, 221, 27));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("STATUS");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2730, 221, 20));

        jComboBox2.setBackground(new java.awt.Color(27, 26, 26));
        jComboBox2.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BOOKED", "RENTED", "RETURNED", "CANCELLED" }));
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 2760, 221, 27));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("PICKUP TIME");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2050, 221, 20));

        jFormattedTextField4.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField4.setText(" ");
        jFormattedTextField4.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField4ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2080, 221, 26));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("RETURN TIME");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2524, 200, 20));

        jFormattedTextField5.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField5.setText(" ");
        jFormattedTextField5.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField5ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 2550, 221, 26));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("PLATE NUMBER");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 221, 20));

        jTextField11.setBackground(new java.awt.Color(27, 26, 26));
        jTextField11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 221, 27));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("EXPIRE DATE");
        jPanel2.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 177, 20));

        jFormattedTextField6.setBackground(new java.awt.Color(27, 26, 26));
        jFormattedTextField6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jFormattedTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField6.setText(" ");
        jFormattedTextField6.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField6ActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 330, 221, 26));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("AGENCY CODE");
        jPanel2.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 221, 20));

        jTextField14.setBackground(new java.awt.Color(27, 26, 26));
        jTextField14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jTextField14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 221, 27));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("LICENSE NUMBER");
        jPanel2.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 221, 20));

        jTextField15.setBackground(new java.awt.Color(27, 26, 26));
        jTextField15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jTextField15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 400, 221, 27));

        jTextField16.setBackground(new java.awt.Color(27, 26, 26));
        jTextField16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jTextField16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 570, 27));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("TRANSMISSION TYPE");
        jPanel2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 221, 20));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("DL CODES");
        jPanel2.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 221, 20));

        jTextField17.setBackground(new java.awt.Color(27, 26, 26));
        jTextField17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 570, 27));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("VEHICLE CATEGORY");
        jPanel2.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 221, 20));

        jTextField18.setBackground(new java.awt.Color(27, 26, 26));
        jTextField18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 570, 27));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("CONDITIONS");
        jPanel2.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 221, 20));

        jTextField19.setBackground(new java.awt.Color(27, 26, 26));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel2.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 570, 27));

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
                .addContainerGap(465, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pictureBox1Layout.setVerticalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 315, Short.MAX_VALUE))
        );

        jPanel2.add(pictureBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 760, 570, -1));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("LICENSE ID FRONT");
        jPanel2.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 570, 20));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("LICENSE ID BACK");
        jPanel2.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 1140, 570, 20));

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
            .addGroup(pictureBox2Layout.createSequentialGroup()
                .addContainerGap(465, Short.MAX_VALUE)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pictureBox2Layout.setVerticalGroup(
            pictureBox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox2Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        jPanel2.add(pictureBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1170, 570, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 2842, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 630));
    }// </editor-fold>//GEN-END:initComponents

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jFormattedTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField3ActionPerformed

    private void jFormattedTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField4ActionPerformed

    private void jFormattedTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField5ActionPerformed

    private void jFormattedTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // add pic
    JnaFileChooser ch = new JnaFileChooser();
    ch.addFilter("image","jpg", "jpeg", "png");

    boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
    if (act) {
        try {
            File file = ch.getSelectedFile();
            licenseFrontBytes = Files.readAllBytes(file.toPath());   // ✅ bytes for DB
            deleteFront = false;                                     // ✅ not deleting
            pictureBox1.setImage(new ImageIcon(licenseFrontBytes));  // ✅ show preview
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load image");
        }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    licenseFrontBytes = null;
    deleteFront = true; // ✅ mark for DB null
    pictureBox1.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    JnaFileChooser ch = new JnaFileChooser();
    ch.addFilter("image","jpg", "jpeg", "png");

    boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
    if (act) {
        try {
            File file = ch.getSelectedFile();
            licenseBackBytes = Files.readAllBytes(file.toPath());
            deleteBack = false;
            pictureBox2.setImage(new ImageIcon(licenseBackBytes));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load image");
        }
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    licenseBackBytes = null;
    deleteBack = true;
    pictureBox2.setImage(new FlatSVGIcon("icon/add-picture-svgrepo-com (3).svg"));
        
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.datetime.component.date.DatePicker datePicker1;
    private raven.datetime.component.date.DatePicker datePicker2;
    private raven.datetime.component.date.DatePicker datePicker3;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private raven.progressindicator.MapCustom mapCustom1;
    private raven.progressindicator.MapCustom mapCustom2;
    private javaswingdev.picturebox.PictureBox pictureBox1;
    private javaswingdev.picturebox.PictureBox pictureBox2;
    private raven.datetime.component.time.TimePicker timePicker1;
    private raven.datetime.component.time.TimePicker timePicker2;
    // End of variables declaration//GEN-END:variables
}
