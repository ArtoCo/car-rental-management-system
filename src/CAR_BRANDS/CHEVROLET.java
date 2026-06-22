/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CAR_BRANDS;

import ADMIN.Customers;
import popup.BOOKCAR;
import ADMIN.Dashboard;
import ADMIN.RentANDbookings;
import static CAR_BRANDS.MITSUBISHI.bmwRef;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import customer.ServiceCustomer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import popup.AppSession;
import popup.CustomerInfo;
import popup.FULLPAY;
import popup.GCASH_CONFIRM;
import popup.INVOICES_FULL;
import popup.PanelSlide;
import popup.ROUNDPANEL;
import popup.SORTING;
import popup.WrapLayout;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.GlassPaneChild;
import raven.popup.component.SimplePopupBorder;
import rojerusan.RSMaterialButtonRectangle;
import popup.ADD;
import popup.HoverEffect;
import popup.ModelDeletedCar;
import popup.ServiceCars;
import java.sql.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Set;
import MUL.CarInventorySaver;
import java.util.Arrays;
import MUL.CarRepairStatusMarker;
import MUL.RepairStatusRefreshable;
import MUL.CarStatusSecurity;
import MUL.InventoryDeleteRestoreSync;
import MUL.CarSecurityGuard;
import MUL.BookRentRepairBlocker;
import java.util.Arrays;
import MUL.AddedCarInventorySync;
import MUL.BrandDeleteRestoreManager;
import MUL.CarRepairStatusMarker;

/**
 *
 * @author ArtoCO
 */
public class CHEVROLET extends javax.swing.JFrame implements RepairStatusRefreshable {
    private final boolean rentalAgentMode = RentANDbookings.isRentalAgentModeActive();

    private void applyRentalAgentAccess() {
        if (!rentalAgentMode) {
            return;
        }
        editEnabled = false;
        hideEmptyAddPanel(jPanel22, jTextField90);
        hideEmptyAddPanel(jPanel21, jTextField88);
        hideEmptyAddPanel(jPanel23, jTextField97);
        hideEmptyAddPanel(jPanel24, jTextField110);
        rSMaterialButtonRectangle15.setVisible(false);
        rSMaterialButtonRectangle16.setVisible(false);
    }

    private void hideEmptyAddPanel(JPanel panel, JTextField carNameField) {
        if (carNameField.getText().trim().isEmpty()) {
            setBaseVisible(panel, false);
            panel.setVisible(false);
            jPanel3.repaint();
        }
    }
    
private PanelSlide panelSlide;
    
private int wrongAttempts = 0;

private JPanel selectedPanel = null;
private boolean editEnabled = false;

private final Map<JPanel, Integer> attemptMap = new HashMap<>();
private final Map<JPanel, Integer> lockoutMinutes = new HashMap<>();
private final Map<JPanel, Long> lockoutEndTime = new HashMap<>();
private final Map<JPanel, JProgressBar> progressBars = new HashMap<>();
private final Map<JPanel, Timer> progressTimers = new HashMap<>();
private final Set<JPanel> lockedPanels = new HashSet<>();
private final List<JPanel> deletedPanels = new ArrayList<>();
private final List<Integer> deletedIndexes = new ArrayList<>();
private final Map<JPanel, Dimension> originalSizes = new HashMap<>();
private final List<ModelDeletedCar> deletedCars = new ArrayList<>();
public static CHEVROLET bmwRef; // static reference
private static final java.awt.Color ORANGE = new java.awt.Color(255, 153, 0);
private static final java.awt.Color GREEN  = new java.awt.Color(0, 255, 51);
private static final java.awt.Color RED    = new java.awt.Color(204, 0, 0);
private final Map<String, JLabel> carDotLabels = new HashMap<>();

// ✅ Keeps the ORIGINAL order of panels inside jPanel3
private final Map<JPanel, Integer> originalIndexMap = new HashMap<>();

// ✅ Links a deleted JPanel to its car info (so restore can show names + remove from DB)
private final Map<JPanel, ModelDeletedCar> deletedCarByPanel = new HashMap<>();
// ✅ Keeps the original panel order (so ALL returns to normal)
private final List<JPanel> masterPanelOrder = new ArrayList<>();

// ✅ keep current filter so after restore/delete/add we can re-apply
private String currentTransmissionFilter = "ALL";

private Timer countdownTimer;

private final Set<String> allowedTransmissionTypes = new LinkedHashSet<>();
private boolean transmissionLocked = false;
private boolean changingTransmission = false;
private boolean blockingPopup = false;

//ADD
private boolean bodyTypeLocked = false;
private static final String BODY_TYPE_DEFAULT = "CAR BODY TYPES";
private String currentBodyTypeFilter = "CAR BODY TYPES";

//ADD THIS 
private static final String BRAND_NAME = "CHEVROLET";


public CHEVROLET() {
        initComponents();
        InventoryDeleteRestoreSync.recordBrandOpenOrRefresh("CHEVROLET");
        syncExistingCarsToInventory();
        jPanel3.setLayout(new GridLayout(3, 0, 8, 10));
    setBackground(new Color(0, 0, 0, 0));

    loadCarDataFromDatabase();
    loadCarDataFromDatabase1();
    loadCarDataFromDatabase2();
    loadCarDataFromDatabase3();

    initCarDotLabels();
    jComboBox2.addActionListener(evt -> handleTransmissionSelection());
    jComboBox3.addActionListener(evt -> handleBodyTypeSelection());
    

    // ✅ Capture panels BEFORE any filter removes/reorders them
    captureOriginalOrder();
    buildMasterPanelOrder();

    // ✅ Set base visibility after car data is loaded
    initBaseVisibilityForAllPanels();

    // ✅ Remove deleted cars after original order is already captured
    Set<String> deletedNames = loadDeletedCarNamesFromDatabase();
    checkAndRemoveIfDeleted(jPanel13, jTextField30, deletedNames);
    checkAndRemoveIfDeleted(jPanel14, jTextField38, deletedNames);
    checkAndRemoveIfDeleted(jPanel16, jTextField54, deletedNames);
    checkAndRemoveIfDeleted(jPanel17, jTextField62, deletedNames);
    checkAndRemoveIfDeleted(jPanel18, jTextField70, deletedNames);
    checkAndRemoveIfDeleted(jPanel6,  jTextField21, deletedNames);
    checkAndRemoveIfDeleted(jPanel19, jTextField78, deletedNames);
    checkAndRemoveIfDeleted(jPanel20, jTextField86, deletedNames);

    currentTransmissionFilter = String.valueOf(jComboBox2.getSelectedItem());
    currentBodyTypeFilter = String.valueOf(jComboBox3.getSelectedItem());

    // ✅ Apply filters only after masterPanelOrder is ready
    applyCombinedFilters();


    lockTransmissionComboBehavior();

    bmwRef = this;
    refreshBMWColors();
    //ADD
    refreshRepairDotColors();
    CarStatusSecurity.startRealtimeRepairDotRefresh(this, this::refreshRepairDotColors);
    installBookRentRepairBlocker();

    updateCarColor("CHEVROLET CAMARO", jLabel61);
    updateCarColor("CHEVROLET CRUZE", jLabel28);
    updateCarColor("CHEVROLET TAHOE", jLabel30);
    updateCarColor("CHEVROLET TRACKER",  jLabel106);
    updateCarColor("CHEVROLET SPARK", jLabel70);
    updateCarColor("CHEVROLET COLORADO", jLabel69);
    updateCarColor("CHEVROLET CORVETTE",  jLabel29);
    updateCarColor("CHEVROLET TRAX",  jLabel62);
    
    
    new HoverEffect(jPanel13);
    new HoverEffect(jPanel18);
    new HoverEffect(jPanel6);
    new HoverEffect(jPanel14);
    new HoverEffect(jPanel16);
    new HoverEffect(jPanel17);
    new HoverEffect(jPanel19);
    new HoverEffect(jPanel20);
    new HoverEffect(jPanel22);
    new HoverEffect(jPanel21);
    new HoverEffect(jPanel23);
    new HoverEffect(jPanel24);

    new HoverEffect(jPanel2);
    new HoverEffect(jPanel5);
    new HoverEffect(jPanel7);
    new HoverEffect(jPanel8);

    int radius = 20;
    setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

    jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            saveDeletedCarsToDatabase();
            new RentANDbookings(rentalAgentMode).setVisible(true);
            dispose();
        }
    });

    GlassPanePopup.install(this);

    setupPanelSizes();
    setupPanelClicks();

    UIManager.put("ProgressBar.background", Color.LIGHT_GRAY);
    UIManager.put("ProgressBar.foreground", new Color(255, 69, 0));
    UIManager.put("ProgressBar.selectionBackground", Color.WHITE);
    UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
    applyRentalAgentAccess();

}

//ADD RED
@Override
public void refreshRepairDotColors() {

    CarStatusSecurity.markRepairDots(
            this,
            Arrays.asList(

                    // =========================
                    // REGULAR / DEFAULT PANELS
                    // =========================

                    CarStatusSecurity.car(
                            "jPanel13",
                            jTextField30,   // CAR NAME
                            jLabel9,        // PLATE NUMBER
                            jLabel28        // DOT LABEL
                    ),

                    CarStatusSecurity.car(
                            "jPanel18",
                            jTextField70,
                            jLabel17,
                            jLabel30
                    ),

                    CarStatusSecurity.car(
                            "jPanel6",
                            jTextField21,
                            jLabel13,
                            jLabel106
                    ),

                    CarStatusSecurity.car(
                            "jPanel14",
                            jTextField38,
                            jLabel12,
                            jLabel61
                    ),

                    CarStatusSecurity.car(
                            "jPanel16",
                            jTextField54,
                            jLabel10,
                            jLabel62
                    ),

                    CarStatusSecurity.car(
                            "jPanel17",
                            jTextField62,
                            jLabel18,
                            jLabel29
                    ),

                    CarStatusSecurity.car(
                            "jPanel19",
                            jTextField78,
                            jLabel16,
                            jLabel69
                    ),

                    CarStatusSecurity.car(
                            "jPanel20",
                            jTextField86,
                            jLabel15,
                            jLabel70
                    ),

                    // =========================
                    // ADDED CAR SLOT PANELS
                    // =========================

                    CarStatusSecurity.car(
                            "jPanel22",
                            jTextField90,   // ADDED CAR NAME
                            jLabel21,       // PLATE NUMBER
                            jLabel41        // DOT LABEL
                    ),

                    CarStatusSecurity.car(
                            "jPanel21",
                            jTextField88,   // ADDED CAR NAME
                            jLabel20,       // PLATE NUMBER
                            jLabel43        // DOT LABEL
                    ),

                    CarStatusSecurity.car(
                            "jPanel23",
                            jTextField97,   // ADDED CAR NAME
                            jLabel35,       // PLATE NUMBER
                            jLabel45        // DOT LABEL
                    ),

                    CarStatusSecurity.car(
                            "jPanel24",
                            jTextField110,  // ADDED CAR NAME
                            jLabel36,       // PLATE NUMBER
                            jLabel81        // DOT LABEL
                    )
            )
    );
}

private void installBookRentRepairBlocker() {

    BookRentRepairBlocker.install(
            this,
            Arrays.asList(

                    // EXISTING / REGULAR CARS
                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle14,
                            jTextField30,
                            jLabel9
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle5,
                            jTextField70,
                            jLabel17
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle8,
                            jTextField21,
                            jLabel13
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle4,
                            jTextField38,
                            jLabel12
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle6,
                            jTextField54,
                            jLabel10
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle3,
                            jTextField62,
                            jLabel18
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle7,
                            jTextField78,
                            jLabel16
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle9, // change if your jPanel20 button name is different
                            jTextField86,
                            jLabel15
                    ),

                    // ADDED CAR SLOTS
                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle11,
                            jTextField90,
                            jLabel21
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle10,
                            jTextField88,
                            jLabel20
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle12,
                            jTextField97,
                            jLabel35
                    ),

                    BookRentRepairBlocker.protect(
                            rSMaterialButtonRectangle13,
                            jTextField110,
                            jLabel36
                    )
            )
    );
}

//DEF
private void attemptPasswordAndDelete(JPanel panel, boolean permanent) {

    long now = System.currentTimeMillis();
    long lockedUntil = lockoutEndTime.getOrDefault(panel, 0L);

    if (now < lockedUntil) {
        showProgressCountdownIfLocked(panel);
        return;
    }

    while (attemptMap.getOrDefault(panel, 3) > 0) {

        String input = promptAdminPasswordWithToggle();
        if (input == null) return; // cancel

if (config.AppConfig.verifyDeletePassword(input)) {

            String status = getActiveStatusIfLocked(panel);

            if (status != null) {
                JOptionPane.showMessageDialog(
                    this,
                    "THIS UNIT IS CURRENTLY " + status + ".\nYOU CANNOT DELETE IT.",
                    "DELETE NOT ALLOWED",
                    JOptionPane.WARNING_MESSAGE
                );

                selectedPanel = null;
                editEnabled = false;
                return;
            }

    // ✅ car_added SLOT delete/restore logic
    if (isCarAddedSlotPanel(panel)) {

        String carName = getCarNameFromPanel(panel);
        ModelDeletedCar fallback = extractCarInfoFromPanel(panel); // used only if DB full-copy fails
        if (!InventoryDeleteRestoreSync.deleteCar(
                this, getPlateNumberFromPanel(panel), "CHEVROLET", true, permanent)) {
            return;
        }

        if (permanent) {
            panel.putClientProperty("PERMA_DELETED", Boolean.TRUE);
        }
        resetCarAddedSlotToPlus(panel);

        // reset states (keep your structure)
        attemptMap.put(panel, 3);
        lockoutMinutes.put(panel, 1);
        lockoutEndTime.put(panel, 0L);
        if (progressBars.containsKey(panel)) progressBars.get(panel).setVisible(false);
        lockedPanels.remove(panel);

        selectedPanel = null;
        editEnabled = false;
        return;
    }

    // ✅ HARD-CODED PANELS: keep your existing restore system
    boolean addedInventoryCar = panel == jPanel22 || panel == jPanel21 || panel == jPanel23 || panel == jPanel24;
    if (!InventoryDeleteRestoreSync.deleteCar(
            this, getPlateNumberFromPanel(panel), "CHEVROLET", addedInventoryCar, permanent)) {
        return;
    }

    markPanelDeleted(panel, true);
    saveDeletedCarsToDatabase();

    if (permanent) {
        removeFromRestoreLists(panel);
    }

    jPanel3.remove(panel);
    jPanel3.revalidate();
    jPanel3.repaint();

    attemptMap.put(panel, 3);
    lockoutMinutes.put(panel, 1);
    lockoutEndTime.put(panel, 0L);

    if (progressBars.containsKey(panel)) progressBars.get(panel).setVisible(false);
    lockedPanels.remove(panel);

    selectedPanel = null;
    editEnabled = false;
    return;
    }    else {
            int remaining = attemptMap.getOrDefault(panel, 3) - 1;
            attemptMap.put(panel, remaining);
            JOptionPane.showMessageDialog(this, "Wrong password. Attempts left: " + remaining);
        }
    }

    int minutes = lockoutMinutes.getOrDefault(panel, 1);
    long newLockEnd = System.currentTimeMillis() + minutes * 60 * 1000L;

    lockoutEndTime.put(panel, newLockEnd);
    lockoutMinutes.put(panel, minutes + 1);
    attemptMap.put(panel, 3);

    lockedPanels.add(panel);
    showProgressCountdownIfLocked(panel);
}

// ✅ Reset the slot to "+" design so checkCarDataAndSetPanelVisibility still works
private void resetCarAddedSlotToPlus(JPanel panel) {

    if (panel == jPanel22) {
        jTextField90.setText(""); jTextField46.setText(""); jTextField93.setText("");
        jTextField106.setText(""); jTextField107.setText(""); jTextField15.setText("");
        jTextField64.setText(""); jLabel21.setText(""); jLabel117.setIcon(null);
        SEMI8.setText("");

        jPanel2.setVisible(true);
        jLabel113.setVisible(false); jLabel111.setVisible(false); jLabel39.setVisible(false);
        rSMaterialButtonRectangle11.setVisible(false);
        jLabel41.setVisible(false); jLabel40.setVisible(false); jLabel47.setVisible(false);
    }

    else if (panel == jPanel21) {
        jTextField88.setText(""); jTextField47.setText(""); jTextField94.setText("");
        jTextField116.setText(""); jTextField115.setText(""); jTextField14.setText("");
        jTextField68.setText(""); jLabel20.setText(""); jLabel109.setIcon(null);
        SEMI9.setText("");

        jPanel5.setVisible(true);
        jLabel115.setVisible(false); jLabel114.setVisible(false); jLabel44.setVisible(false);
        rSMaterialButtonRectangle10.setVisible(false);
        jLabel43.setVisible(false); jLabel42.setVisible(false); jLabel49.setVisible(false);
    }

    else if (panel == jPanel23) {
        jTextField97.setText(""); jTextField48.setText(""); jTextField104.setText("");
        jTextField117.setText(""); jTextField118.setText(""); jTextField16.setText("");
        jTextField69.setText(""); jLabel35.setText(""); jLabel125.setIcon(null);
        SEMI10.setText("");

        jPanel7.setVisible(true);
        jLabel146.setVisible(false); jLabel143.setVisible(false); jLabel46.setVisible(false);
        rSMaterialButtonRectangle12.setVisible(false);
        jLabel45.setVisible(false); jLabel50.setVisible(false); jLabel51.setVisible(false);
    }

    else if (panel == jPanel24) {
        jTextField110.setText(""); jTextField49.setText(""); jTextField105.setText("");
        jTextField119.setText(""); jTextField120.setText(""); jTextField17.setText("");
        jTextField72.setText(""); jLabel36.setText(""); jLabel133.setIcon(null);
        SEMI11.setText("");

        jPanel8.setVisible(true);
        jLabel95.setVisible(false); jLabel87.setVisible(false); jLabel89.setVisible(false);
        rSMaterialButtonRectangle13.setVisible(false);
        jLabel81.setVisible(false); jLabel108.setVisible(false); jLabel52.setVisible(false);
    }
    applyTransmissionFilter(currentTransmissionFilter);

    // ✅ keep your chain visibility logic
    checkCarDataAndSetPanelVisibility();
    checkCarDataAndSetPanelVisibility1();
    checkCarDataAndSetPanelVisibility2();

    refreshBMWColors();
    revalidate();
    repaint();
}

// ✅ Permanent delete (Delete Permanently): remove from car_added only, and ensure NOT restorable
private void permanentDeleteCarAdded(String carName) throws SQLException {
    if (carName == null || carName.trim().isEmpty()) return;
    carName = carName.trim();

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String delAdded = "DELETE FROM car_added WHERE CARS_NAME=? LIMIT 1";

    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(delAdded)) {
        ps.setString(1, carName);
        ps.executeUpdate();
    }

    // remove from cars_deleted too (so it won't appear in RESTORE)
    deleteFromCarsDeletedByName(carName);
}

// ✅ Remove from cars_deleted (used when permanent delete happens or cleanup)
private void deleteFromCarsDeletedByName(String carName) {
    if (carName == null || carName.trim().isEmpty()) return;

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String sql = "DELETE FROM cars_deleted WHERE CARS_NAME=? LIMIT 1";
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, carName.trim());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// ✅ list only the "deleted added cars" (not hardcoded) from cars_deleted
private List<String> loadDeletedAddedCarNamesFromDB() {
    List<String> list = new ArrayList<>();
    Set<String> hardcoded = getHardcodedCarNames();

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String sql = "SELECT CARS_NAME FROM cars_deleted "
            + "WHERE CAR_BRAND = 'CHEVROLET' ORDER BY DELETED_DATE DESC";
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String name = rs.getString("CARS_NAME");
            if (name == null) continue;
            name = name.trim();
            if (name.isEmpty()) continue;
            if (!InventoryDeleteRestoreSync.canShowInRestore(name, null, "CHEVROLET")) continue;

            // skip hardcoded cars (they restore as panels)
            if (hardcoded.contains(name)) continue;

            // skip already restored into car_added
            if (existsInCarAdded(name)) continue;

            list.add(name);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

// ✅ names of your hardcoded BMW cards (so we don't restore them into car_added)
private Set<String> getHardcodedCarNames() {
    Set<String> s = new HashSet<>();
    // hardcoded panels textfields
    s.add(jTextField30.getText().trim());
    s.add(jTextField70.getText().trim());
    s.add(jTextField21.getText().trim());
    s.add(jTextField38.getText().trim());
    s.add(jTextField54.getText().trim());
    s.add(jTextField62.getText().trim());
    s.add(jTextField78.getText().trim());
    s.add(jTextField86.getText().trim());
    s.removeIf(x -> x == null || x.isEmpty());
    return s;
}

private boolean existsInCarAdded(String carName) {
    if (carName == null || carName.trim().isEmpty()) return false;

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String sql = "SELECT 1 FROM car_added WHERE CARS_NAME=? LIMIT 1";
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, carName.trim());
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// ✅ Identify the 4 "NEW ADDED" slots that come from car_added
private boolean isCarAddedSlotPanel(JPanel panel) {
    return panel == jPanel22 || panel == jPanel21 || panel == jPanel23 || panel == jPanel24;
}

private void softDeleteCarAddedToCarsDeleted(String carName, ModelDeletedCar fallbackCar) throws SQLException {
    if (carName == null || carName.trim().isEmpty()) throw new SQLException("Empty car name.");
    carName = carName.trim();

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String deleteFromAdded = "DELETE FROM car_added WHERE CARS_NAME=? LIMIT 1";

    // ✅ FULL COPY (recommended)
    String insertFull =
        "INSERT INTO cars_deleted " +
        "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL_TYPE, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, DELETED_DATE) " +
        "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION, ? " +
        "FROM car_added WHERE CARS_NAME=? LIMIT 1";

    // ✅ MINIMAL COPY (fallback if cars_deleted doesn't have full columns)
    String insertMini =
        "INSERT INTO cars_deleted (CARS_NAME, CARS_TYPE, CARS_RATINGS, CARS_FUEL_TYPE, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, DELETED_DATE) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(url, user, pass)) {
        conn.setAutoCommit(false);
        try {
            boolean inserted = false;

            // try FULL insert
            try (PreparedStatement ps = conn.prepareStatement(insertFull)) {
                ps.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                ps.setString(2, carName);
                int rows = ps.executeUpdate();
                inserted = (rows > 0);
            } catch (SQLException fullFail) {
                // fallback MINI insert using UI ModelDeletedCar
                if (fallbackCar == null) throw fullFail;

                try (PreparedStatement ps = conn.prepareStatement(insertMini)) {
                    ps.setString(1, fallbackCar.getName());
                    ps.setString(2, fallbackCar.getType());
                    ps.setString(3, fallbackCar.getRating());
                    ps.setString(4, fallbackCar.getFuelType());
                    ps.setString(5, fallbackCar.getSeats());
                    ps.setString(6, fallbackCar.getDailyRent());
                    ps.setString(7, fallbackCar.getModel());
                    ps.setString(8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    ps.executeUpdate();
                    inserted = true;
                }
            }

            if (!inserted) throw new SQLException("Failed to archive deleted car.");

            // delete from car_added
            try (PreparedStatement ps = conn.prepareStatement(deleteFromAdded)) {
                ps.setString(1, carName);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}

// ✅ Get car name from any panel
private String getCarNameFromPanel(JPanel panel) {
    if (panel == jPanel13) return jTextField30.getText();
    if (panel == jPanel18) return jTextField70.getText();
    if (panel == jPanel6)  return jTextField21.getText();
    if (panel == jPanel14) return jTextField38.getText();
    if (panel == jPanel16) return jTextField54.getText();
    if (panel == jPanel17) return jTextField62.getText();
    if (panel == jPanel19) return jTextField78.getText();
    if (panel == jPanel20) return jTextField86.getText();

    // car_added slots
    if (panel == jPanel22) return jTextField90.getText();
    if (panel == jPanel21) return jTextField88.getText();
    if (panel == jPanel23) return jTextField97.getText();
    if (panel == jPanel24) return jTextField110.getText();

    return null;
}

private String getPlateNumberFromPanel(JPanel panel) {
    if (panel == jPanel13) return jLabel9.getText();
    if (panel == jPanel18) return jLabel17.getText();
    if (panel == jPanel6)  return jLabel13.getText();
    if (panel == jPanel14) return jLabel12.getText();
    if (panel == jPanel16) return jLabel10.getText();
    if (panel == jPanel17) return jLabel18.getText();
    if (panel == jPanel19) return jLabel16.getText();
    if (panel == jPanel20) return jLabel15.getText();

    // car_added slots
    if (panel == jPanel22) return jLabel21.getText();
    if (panel == jPanel21) return jLabel20.getText();
    if (panel == jPanel23) return jLabel35.getText();
    if (panel == jPanel24) return jLabel36.getText();

    return "";
}

private boolean isSameRGB(Color a, Color b) {
    if (a == null || b == null) return false;

    return a.getRed() == b.getRed()
            && a.getGreen() == b.getGreen()
            && a.getBlue() == b.getBlue();
}

// ✅ BLOCK delete if ORANGE (color system) OR if DB says RENTED/BOOKED
private String getActiveStatusIfLocked(JPanel panel) {
    String carName = getCarNameFromPanel(panel);
    String plateNumber = getPlateNumberFromPanel(panel);

    if (carName == null || carName.trim().isEmpty()) {
        return null;
    }

    carName = carName.trim();
    plateNumber = plateNumber == null ? "" : plateNumber.trim();

    JLabel dot = findDotLabelByCarName(carName);

    if (dot != null) {
        Color color = dot.getForeground();

        if (isSameRGB(color, ORANGE)) {
            return "BOOKED / RENTED";
        }

        if (isSameRGB(color, RED) || isSameRGB(color, Color.RED)) {
            return "REPAIR";
        }
    }

    try {
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null && !activeStatus.trim().isEmpty()) {
            return activeStatus.trim().toUpperCase();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "STATUS CHECK ERROR";
    }

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    String sql =
        "SELECT CAR_STATUS FROM cars_info " +
        "WHERE UPPER(TRIM(CARS_NAME)) = UPPER(TRIM(?)) " +
        "  AND UPPER(TRIM(CARS_PLATENUMBER)) = UPPER(TRIM(?)) " +
        "LIMIT 1";

    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, carName);
        ps.setString(2, plateNumber);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String status = rs.getString("CAR_STATUS");

                if (status != null) {
                    status = status.trim().toUpperCase();

                    if (status.equals("BOOKED")
                            || status.equals("RENTED")
                            || status.equals("REPAIR")
                            || status.equals("UNDER REPAIR")
                            || status.equals("IN SERVICE")
                            || status.equals("MAINTENANCE")
                            || status.contains("REPAIR")
                            || MUL.CarStatusSecurity.isRepairStatus(status)) {
                        return status;
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "STATUS CHECK ERROR";
    }

    return null;
}

private void restoreCarAddedFromCarsDeleted(String carName) throws SQLException {
    if (carName == null || carName.trim().isEmpty()) return;
    carName = carName.trim();

    if (existsInCarAdded(carName)) {
        // already restored; just cleanup cars_deleted to avoid duplicate restore entry
        deleteFromCarsDeletedByName(carName);
        return;
    }

    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String pass = config.AppConfig.getDatabasePassword();

    // FULL restore (if cars_deleted has those columns)
    String insertFull =
        "INSERT INTO car_added " +
        "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
        "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL_TYPE, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION " +
        "FROM cars_deleted WHERE CARS_NAME=? LIMIT 1";

    // MINIMAL restore fallback
    String insertMini =
        "INSERT INTO car_added (CARS_NAME, CARS_TYPE, CARS_FUEL, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS) " +
        "SELECT CARS_NAME, CARS_TYPE, CARS_FUEL_TYPE, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS " +
        "FROM cars_deleted WHERE CARS_NAME=? LIMIT 1";

    String delDeleted = "DELETE FROM cars_deleted WHERE CARS_NAME=? LIMIT 1";

    try (Connection conn = DriverManager.getConnection(url, user, pass)) {
        conn.setAutoCommit(false);
        try {
            boolean ok = false;
            try (PreparedStatement ps = conn.prepareStatement(insertFull)) {
                ps.setString(1, carName);
                ok = ps.executeUpdate() > 0;
            } catch (SQLException fullFail) {
                try (PreparedStatement ps = conn.prepareStatement(insertMini)) {
                    ps.setString(1, carName);
                    ok = ps.executeUpdate() > 0;
                }
            }

            if (!ok) throw new SQLException("Restore failed: no row found in cars_deleted.");

            try (PreparedStatement ps = conn.prepareStatement(delDeleted)) {
                ps.setString(1, carName);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
 

private void refreshMitsubishiPage() {
    InventoryDeleteRestoreSync.recordBrandOpenOrRefresh("CHEVROLET");
    try {
        // Reload added cars from database
        loadCarDataFromDatabase();
        loadCarDataFromDatabase1();
        loadCarDataFromDatabase2();
        loadCarDataFromDatabase3();

        // Reload car dot mappings
        initCarDotLabels();

        // Refresh car availability colors
        refreshBMWColors(); 
        // You can rename this later to refreshMitsubishiColors(),
        // but it works if this is your existing Mitsubishi color refresh method.

        // Re-check deleted cars
        Set<String> deletedNames = loadDeletedCarNamesFromDatabase();

        checkAndRemoveIfDeleted(jPanel13, jTextField30, deletedNames);
        checkAndRemoveIfDeleted(jPanel14, jTextField38, deletedNames);
        checkAndRemoveIfDeleted(jPanel16, jTextField54, deletedNames);
        checkAndRemoveIfDeleted(jPanel17, jTextField62, deletedNames);
        checkAndRemoveIfDeleted(jPanel18, jTextField70, deletedNames);
        checkAndRemoveIfDeleted(jPanel6,  jTextField21, deletedNames);
        checkAndRemoveIfDeleted(jPanel19, jTextField78, deletedNames);
        checkAndRemoveIfDeleted(jPanel20, jTextField86, deletedNames);

        // Recalculate visible panels
        initBaseVisibilityForAllPanels();

        // Keep current selected filters
        currentTransmissionFilter = String.valueOf(jComboBox2.getSelectedItem());
        currentBodyTypeFilter = String.valueOf(jComboBox3.getSelectedItem());

        // Apply transmission/body filters again
        applyCombinedFilters();

        jPanel3.revalidate();
        jPanel3.repaint();
        revalidate();
        repaint();

        JOptionPane.showMessageDialog(
                this,
                "Chevrolet page refreshed successfully!",
                "Refresh",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
                this,
                "Refresh failed:\n" + e.getMessage(),
                "Refresh Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


private void checkAndRemoveIfDeleted(JPanel panel, JTextField nameField, Set<String> deletedNames) {
    if (deletedNames.contains(nameField.getText())) {
        markPanelDeleted(panel, false);   // already in DB, don’t re-save later
        jPanel3.remove(panel);
    }
}

private void initCarDotLabels() {
    carDotLabels.put("CHEVROLET CAMARO", jLabel61);
    carDotLabels.put("CHEVROLET CRUZE", jLabel28);
    carDotLabels.put("CHEVROLET TAHOE", jLabel30);
    carDotLabels.put("CHEVROLET TRACKER",  jLabel106);
    carDotLabels.put("CHEVROLET SPARK", jLabel70);
    carDotLabels.put("CHEVROLET COLORADO", jLabel69);
    carDotLabels.put("CHEVROLET CORVETTE",  jLabel29);
    carDotLabels.put("CHEVROLET TRAX",  jLabel62);
}
    
public void setCarDotOrangeNow(String carName) {
    if (carName == null) return;
    carName = carName.trim();

    javax.swing.JLabel dot = carDotLabels.get(carName);
    if (dot == null) return;

    javax.swing.SwingUtilities.invokeLater(() -> {
        dot.setForeground(ORANGE);
        dot.repaint();
    });
}

private void lockTransmissionComboBehavior() {
    jComboBox2.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
            if (transmissionLocked) {
                blockingPopup = true;

                SwingUtilities.invokeLater(() -> {
                    jComboBox2.hidePopup();
                    JOptionPane.showMessageDialog(
                        CHEVROLET.this,
                        "Cannot select another car. This is the allowed unit to rent/book.",
                        "Transmission Locked",
                        JOptionPane.WARNING_MESSAGE
                    );
                    blockingPopup = false;
                });
            }
        }

        @Override
        public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
        }
    });

    jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (transmissionLocked) {
                e.consume();
                JOptionPane.showMessageDialog(
                    CHEVROLET.this,
                    "Cannot select another car. This is the allowed unit to rent/book.",
                    "Transmission Locked",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    });
}

//ADD
private JTextField getBodyTypeFieldByPanel(JPanel panel) {
    if (panel == jPanel13) return jTextField35;   // MIRAGE - HATCHBACK
    if (panel == jPanel18) return jTextField75;   // MONTERO SPORT - SUV
    if (panel == jPanel6)  return jTextField28;   // OUTLANDER - SUV
    if (panel == jPanel14) return jTextField43;   // MIRAGE G4 - SEDAN
    if (panel == jPanel20) return jTextField91;   // LANCER EX - LUXURY SEDAN
    if (panel == jPanel19) return jTextField83;   // XPANDER - MPV
    if (panel == jPanel17) return jTextField67;   // PAJERO - SUV
    if (panel == jPanel16) return jTextField59;   // TRITON - PICKUP

    // Added car slots
    if (panel == jPanel22) return jTextField46;
    if (panel == jPanel21) return jTextField47;
    if (panel == jPanel23) return jTextField48;
    if (panel == jPanel24) return jTextField49;

    return null;
}
//ADD
private String getPanelBodyType(JPanel panel) {
    JTextField field = getBodyTypeFieldByPanel(panel);

    if (field == null) {
        return "";
    }

    return normalizeBodyType(field.getText());
}
//ADD
private String normalizeBodyType(String value) {
    if (value == null) {
        return "ALL";
    }

    String body = value.trim().toUpperCase();

    if (body.isEmpty()) {
        return "ALL";
    }

    body = body.replace("-", " ");
    body = body.replace("_", " ");
    body = body.replaceAll("\\s+", " ");

    // Treat default text as ALL
    if (body.equals("CAR BODY TYPES") || body.equals("ALL")) {
        return "ALL";
    }

    if (body.equals("STATION WAGON")) {
        return "STATIONWAGON";
    }

    if (body.equals("CONVERTIBLE")) {
        return "CONVERTIBLES";
    }

    return body;
}
//ADD
private boolean bodyMatches(String panelBodyType, String wantedBodyType) {
    panelBodyType = normalizeBodyType(panelBodyType);
    wantedBodyType = normalizeBodyType(wantedBodyType);

    if ("ALL".equals(wantedBodyType)) {
        return true;
    }

    if (panelBodyType.equals(wantedBodyType)) {
        return true;
    }

    // LUXURY should show LUXURY SEDAN
    if ("LUXURY".equals(wantedBodyType)) {
        return panelBodyType.contains("LUXURY");
    }

    // SEDAN should show SEDAN and LUXURY SEDAN
    if ("SEDAN".equals(wantedBodyType)) {
        return panelBodyType.equals("SEDAN") || panelBodyType.contains("SEDAN");
    }

    // CONVERTIBLES support
    if ("CONVERTIBLES".equals(wantedBodyType)) {
        return panelBodyType.equals("CONVERTIBLE") || panelBodyType.equals("CONVERTIBLES");
    }

    return false;
}
//REPLACE
public void setLockedTransmissionType(String transmissionType) {
    allowedTransmissionTypes.clear();

    String normalized = normalizeTransmission(transmissionType);

    if ("SEMI-AUTO".equalsIgnoreCase(normalized)) {
        allowedTransmissionTypes.add("SEMI-AUTO");
        allowedTransmissionTypes.add("AUTOMATIC");
    } else if ("AUTOMATIC".equalsIgnoreCase(normalized)) {
        allowedTransmissionTypes.add("AUTOMATIC");
    } else if ("MANUAL".equalsIgnoreCase(normalized)) {
        allowedTransmissionTypes.add("MANUAL");
    }

    transmissionLocked = !allowedTransmissionTypes.isEmpty();
    
    // ✅ Existing customer mode: lock body type filter
bodyTypeLocked = transmissionLocked;

if (bodyTypeLocked) {
    currentBodyTypeFilter = BODY_TYPE_DEFAULT;
    jComboBox3.setSelectedItem(BODY_TYPE_DEFAULT);
    jComboBox3.setEnabled(false); // locked, user cannot change body type
}

    changingTransmission = true;
    try {
        // default visible choice
        if (allowedTransmissionTypes.contains("SEMI-AUTO")) {
            jComboBox2.setSelectedItem("SEMI-AUTO");
            applyTransmissionFilter("SEMI-AUTO");
        } else if (allowedTransmissionTypes.contains("AUTOMATIC")) {
            jComboBox2.setSelectedItem("AUTOMATIC");
            applyTransmissionFilter("AUTOMATIC");
        } else if (allowedTransmissionTypes.contains("MANUAL")) {
            jComboBox2.setSelectedItem("MANUAL");
            applyTransmissionFilter("MANUAL");
        }
    } finally {
        changingTransmission = false;
    }
}


private void handleTransmissionSelection() {
    if (changingTransmission) {
        return;
    }

    Object selectedObj = jComboBox2.getSelectedItem();
    String selected = normalizeTransmission(selectedObj == null ? "" : selectedObj.toString());

    if (transmissionLocked) {
        if (!allowedTransmissionTypes.contains(selected)) {
            changingTransmission = true;

            try {
                if (allowedTransmissionTypes.contains("SEMI-AUTO")) {
                    jComboBox2.setSelectedItem("SEMI-AUTO");
                    selected = "SEMI-AUTO";
                } else if (allowedTransmissionTypes.contains("AUTOMATIC")) {
                    jComboBox2.setSelectedItem("AUTOMATIC");
                    selected = "AUTOMATIC";
                } else if (allowedTransmissionTypes.contains("MANUAL")) {
                    jComboBox2.setSelectedItem("MANUAL");
                    selected = "MANUAL";
                }
            } finally {
                changingTransmission = false;
            }

            JOptionPane.showMessageDialog(
                this,
                "This customer can only use the allowed transmission type.",
                "Transmission Locked",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    applyTransmissionFilter(selected);
}

//REPLACE
private void applyTransmissionFilter(String selected) {
    currentTransmissionFilter = selected == null ? "ALL" : selected;
    applyCombinedFilters();
}
//ADD
private void handleBodyTypeSelection() {
    Object selectedObj = jComboBox3.getSelectedItem();
    currentBodyTypeFilter = selectedObj == null ? "CAR BODY TYPES" : selectedObj.toString();

    applyCombinedFilters();
}
//ADD
private void applyCombinedFilters() {
    String wantedTransmission = normalizeTransmission(currentTransmissionFilter);
    String wantedBodyType = normalizeBodyType(currentBodyTypeFilter);

    List<JPanel> showFirst = new ArrayList<>();
    List<JPanel> keepHidden = new ArrayList<>();

    for (JPanel p : masterPanelOrder) {

        if (Boolean.TRUE.equals(p.getClientProperty("PERMA_DELETED"))) continue;
        if (deletedPanels.contains(p)) continue;

        boolean baseVisible = getBaseVisible(p);

        String panelTransmission = getPanelTransmission(p);
        String panelBodyType = getPanelBodyType(p);

        boolean transmissionMatch;

        if (transmissionLocked) {
            transmissionMatch = allowedTransmissionTypes.contains(panelTransmission);
        } else if ("ALL".equals(wantedTransmission)) {
            transmissionMatch = true;
        } else if ("SEMI-AUTO".equals(wantedTransmission)) {
            transmissionMatch =
                    "SEMI-AUTO".equals(panelTransmission)
                    || "AUTOMATIC".equals(panelTransmission);
        } else {
            transmissionMatch = wantedTransmission.equals(panelTransmission);
        }

        boolean bodyTypeMatch = bodyMatches(panelBodyType, wantedBodyType);

        boolean shouldShow = baseVisible && transmissionMatch && bodyTypeMatch;

        p.setVisible(shouldShow);

        if (shouldShow) {
            showFirst.add(p);
        } else {
            keepHidden.add(p);
        }
    }

    jPanel3.removeAll();

    for (JPanel p : showFirst) {
        jPanel3.add(p);
    }

    for (JPanel p : keepHidden) {
        jPanel3.add(p);
    }

    jPanel3.revalidate();
    jPanel3.repaint();
}

private String normalizeTransmission(String value) {
    if (value == null) {
        return "";
    }

    String t = value.trim();

    if (t.equalsIgnoreCase("SEMI AUTO") || t.equalsIgnoreCase("SEMI-AUTO")) {
        return "SEMI-AUTO";
    }
    if (t.equalsIgnoreCase("MANUAL")) {
        return "MANUAL";
    }
    if (t.equalsIgnoreCase("AUTOMATIC")) {
        return "AUTOMATIC";
    }
    if (t.equalsIgnoreCase("ALL")) {
        return "ALL";
    }

    return t.toUpperCase();
}

public void setCarDotGreenNow(String carName) {
    if (carName == null) return;
    carName = carName.trim();

    javax.swing.JLabel dot = carDotLabels.get(carName);
    if (dot == null) return;

    javax.swing.SwingUtilities.invokeLater(() -> {
        dot.setForeground(GREEN);
        dot.repaint();
    });
}

private void setBaseVisible(JPanel p, boolean v) {
    p.putClientProperty("BASE_VISIBLE", v);
}

// ✅ DO NOT fall back to p.isVisible() (that causes the empty bug)
private boolean getBaseVisible(JPanel p) {
    Object o = p.getClientProperty("BASE_VISIBLE");
    return (o instanceof Boolean) ? (Boolean) o : true;   // default true
}

private void buildMasterPanelOrder() {
    masterPanelOrder.clear();

    // originalIndexMap already has the correct original order
    List<Map.Entry<JPanel, Integer>> entries = new ArrayList<>(originalIndexMap.entrySet());
    entries.sort((a, b) -> Integer.compare(a.getValue(), b.getValue()));

    for (Map.Entry<JPanel, Integer> e : entries) {
        masterPanelOrder.add(e.getKey());
    }
}

private void initBaseVisibilityForAllPanels() {
    // hardcoded panels are always base-visible (unless deleted/perma)
    setBaseVisible(jPanel13, true);
    setBaseVisible(jPanel18, true);
    setBaseVisible(jPanel6,  true);
    setBaseVisible(jPanel14, true);
    setBaseVisible(jPanel16, true);
    setBaseVisible(jPanel17, true);
    setBaseVisible(jPanel19, true);
    setBaseVisible(jPanel20, true);

    // car_added slots follow your own logic (checkCarData...)
    // so call the checks first, then store their result:
    checkCarDataAndSetPanelVisibility();
    checkCarDataAndSetPanelVisibility1();
    checkCarDataAndSetPanelVisibility2();

    setBaseVisible(jPanel21, jPanel21.isVisible());
    setBaseVisible(jPanel23, jPanel23.isVisible());
    setBaseVisible(jPanel24, jPanel24.isVisible());

    // slot0 itself (jPanel22) always exists as a card, so base-visible true
    setBaseVisible(jPanel22, true);
}

private JLabel getTransmissionLabelByPanel(JPanel panel) {
    if (panel == jPanel13) return SEMI;
    if (panel == jPanel18) return SEMI1;
    if (panel == jPanel6)  return SEMI2;
    if (panel == jPanel14) return SEMI3;
    if (panel == jPanel20) return SEMI4;
    if (panel == jPanel19) return SEMI5;
    if (panel == jPanel17) return SEMI6;
    if (panel == jPanel16) return SEMI7;

    // car_added slots
    if (panel == jPanel22) return SEMI8;
    if (panel == jPanel21) return SEMI9;
    if (panel == jPanel23) return SEMI10;
    if (panel == jPanel24) return SEMI11;

    return null;
}

private String getPanelTransmission(JPanel panel) {
    JLabel t = getTransmissionLabelByPanel(panel);
    if (t == null) return "";
    return normalizeTransmission(t.getText());
}

private void restoreAllPanelsToGrid() {
    jPanel3.removeAll();

    for (JPanel p : masterPanelOrder) {
        // ✅ If hardcoded deleted (soft delete), it’s in deletedPanels -> don’t add back
        if (deletedPanels.contains(p)) continue;

        // ✅ If hardcoded PERMANENT delete, mark it once so filter doesn't bring it back
        Object perma = p.getClientProperty("PERMA_DELETED");
        if (perma instanceof Boolean && (Boolean) perma) continue;

        jPanel3.add(p);
    }

    jPanel3.revalidate();
    jPanel3.repaint();
}

private Set<String> loadDeletedCarNamesFromDatabase() {
    Set<String> deleted = new HashSet<>();
    try (Connection conn = DriverManager.getConnection(config.AppConfig.getDatabaseUrl(), config.AppConfig.getDatabaseUser(), config.AppConfig.getDatabasePassword());
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT CARS_NAME FROM cars_deleted WHERE CAR_BRAND = 'CHEVROLET'")) {

        while (rs.next()) {
            deleted.add(rs.getString("CARS_NAME"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return deleted;
}

    
 
    private void reflowGridLayout() {
    jPanel3.revalidate();
    jPanel3.repaint();
}

    public void copyComponents(JPanel source, JPanel target) {
    target.removeAll(); // clear the target panel
    for (Component comp : source.getComponents()) {
        target.add(comp); // re-add components (shallow copy)
    }
    target.revalidate();
    target.repaint();
}

    
private void updateCarColor(String carName, javax.swing.JLabel label) {
    if (label == null) return;
    if (carName == null) return;

    carName = carName.trim();
    if (carName.isEmpty()) return;

    try {
        ServiceCustomer service = new ServiceCustomer();
        boolean active = service.isCarActive(carName);   // RENTED/BOOKED = ORANGE
        label.setForeground(active ? ORANGE : GREEN);    // RETURNED/none = GREEN
    } catch (Exception e) {
        e.printStackTrace();
    }
}


// ✅ CALL THIS FROM OTHER CLASSES
public void refreshBMWColors() {
    updateCarColor("CHEVROLET CAMARO", jLabel61);
    updateCarColor("CHEVROLET CRUZE", jLabel28);
    updateCarColor("CHEVROLET TAHOE", jLabel30);
    updateCarColor("CHEVROLET TRACKER",  jLabel106);
    updateCarColor("CHEVROLET SPARK", jLabel70);
    updateCarColor("CHEVROLET COLORADO", jLabel69);
    updateCarColor("CHEVROLET CORVETTE",  jLabel29);
    updateCarColor("CHEVROLET TRAX",  jLabel62);

    // ✅ NEW CARS (car_added slots)
    updateCarColor(jTextField90.getText(),  jLabel41);  // panel22 slot
    updateCarColor(jTextField88.getText(),  jLabel43);  // panel21 slot
    updateCarColor(jTextField97.getText(),  jLabel45);  // panel23 slot
    updateCarColor(jTextField110.getText(), jLabel81);  // panel24 slot

    revalidate();
    repaint();
}
    

    
    private ModelDeletedCar extractCarInfoFromPanel(JPanel panel) {
    if (panel == jPanel13) {
        return new ModelDeletedCar(
            jTextField30.getText(), jTextField35.getText(),
            jTextField34.getText(), jTextField33.getText(),
            jTextField32.getText(), jTextField6.getText(),
            jTextField31.getText()
        );
    } else if (panel == jPanel18) {
        return new ModelDeletedCar(
            jTextField70.getText(), jTextField75.getText(),
            jTextField71.getText(), jTextField81.getText(),
            jTextField74.getText(), jTextField11.getText(),
            jTextField42.getText()
        );
    } else if (panel == jPanel6) {
        return new ModelDeletedCar(
            jTextField21.getText(), jTextField28.getText(),
            jTextField22.getText(), jTextField98.getText(),
            jTextField58.getText(), jTextField9.getText(),
            jTextField56.getText()
        );
    } else if (panel == jPanel14) {
        return new ModelDeletedCar(
            jTextField38.getText(), jTextField43.getText(),
            jTextField39.getText(), jTextField41.getText(),
            jTextField40.getText(), jTextField7.getText(),
            jTextField36.getText()
        );
    } else if (panel == jPanel20) {
        return new ModelDeletedCar(
            jTextField86.getText(), jTextField91.getText(),
            jTextField87.getText(), jTextField102.getText(),
            jTextField103.getText(), jTextField13.getText(),
            jTextField57.getText()
        );
    } else if (panel == jPanel19) {
        return new ModelDeletedCar(
            jTextField78.getText(), jTextField83.getText(),
            jTextField79.getText(), jTextField100.getText(),
            jTextField60.getText(), jTextField12.getText(),
            jTextField44.getText()
        );
    } else if (panel == jPanel17) {
        return new ModelDeletedCar(
            jTextField62.getText(), jTextField67.getText(),
            jTextField63.getText(), jTextField80.getText(),
            jTextField73.getText(), jTextField10.getText(),
            jTextField37.getText()
        );
    } else if (panel == jPanel16) {
        return new ModelDeletedCar(
            jTextField54.getText(), jTextField59.getText(),
            jTextField55.getText(), jTextField95.getText(),
            jTextField96.getText(), jTextField8.getText(),
            jTextField45.getText()
        );
        
    //THIS FOR PANEL22
    } else if (panel == jPanel22) {
        return new ModelDeletedCar(
            jTextField90.getText(), jTextField46.getText(),
            jTextField93.getText(), jTextField106.getText(),
            jTextField107.getText(), jTextField15.getText(),
            jTextField64.getText() 
        );
        
    //THIS FOR PANEL21
    } else if (panel == jPanel21) {
        return new ModelDeletedCar(
            jTextField88.getText(), jTextField47.getText(),
            jTextField94.getText(), jTextField116.getText(),
            jTextField115.getText(), jTextField14.getText(),
            jTextField68.getText() 
        );
        
    }
    return null;
}

    private void saveDeletedCarsToDatabase() {
    Connection conn = null;
    PreparedStatement checkStmt = null;
    PreparedStatement insertStmt = null;

    try {
        conn = DriverManager.getConnection(config.AppConfig.getDatabaseUrl(), config.AppConfig.getDatabaseUser(), config.AppConfig.getDatabasePassword());

        String checkSql = "SELECT COUNT(*) FROM cars_deleted WHERE CARS_NAME = ?";
        String insertSql = "INSERT INTO cars_deleted (CARS_NAME, CARS_TYPE, CARS_RATINGS, CARS_FUEL_TYPE, CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, DELETED_DATE) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        for (ModelDeletedCar car : deletedCars) {
            // Check if this car was already saved
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, car.getName());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (count == 0) { // Not a duplicate
                insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, car.getName());
                insertStmt.setString(2, car.getType());
                insertStmt.setString(3, car.getRating());
                insertStmt.setString(4, car.getFuelType());
                insertStmt.setString(5, car.getSeats());
                insertStmt.setString(6, car.getDailyRent());
                insertStmt.setString(7, car.getModel());
                insertStmt.setString(8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                insertStmt.executeUpdate();
                insertStmt.close();
            }
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (checkStmt != null) checkStmt.close();
            if (insertStmt != null) insertStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
    private void removeFromRestoreLists(JPanel panel) {
    int idx = deletedPanels.indexOf(panel);
    if (idx >= 0) {
        deletedPanels.remove(idx);
        if (idx < deletedIndexes.size()) deletedIndexes.remove(idx);
    }
}



    private void setupPanelSizes() {
    originalSizes.put(jPanel13, jPanel13.getPreferredSize());
    originalSizes.put(jPanel14, jPanel14.getPreferredSize());
    originalSizes.put(jPanel16, jPanel16.getPreferredSize());
    originalSizes.put(jPanel18, jPanel18.getPreferredSize());
    originalSizes.put(jPanel6, jPanel6.getPreferredSize());
    originalSizes.put(jPanel17, jPanel17.getPreferredSize());
    originalSizes.put(jPanel19, jPanel19.getPreferredSize());
    originalSizes.put(jPanel20, jPanel20.getPreferredSize());
    originalSizes.put(jPanel21, jPanel21.getPreferredSize());
    originalSizes.put(jPanel22, jPanel22.getPreferredSize());
    originalSizes.put(jPanel23, jPanel23.getPreferredSize());
    originalSizes.put(jPanel24, jPanel24.getPreferredSize());

    // Apply to all
    for (JPanel panel : originalSizes.keySet()) {
        Dimension size = originalSizes.get(panel);
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);
    }
}

    private void setupPanelClicks() {
    registerPanel(jPanel13);
    registerPanel(jPanel18);
    registerPanel(jPanel6);
    registerPanel(jPanel14);
    registerPanel(jPanel16);
    registerPanel(jPanel17);
    registerPanel(jPanel19);
    registerPanel(jPanel20);
    registerPanel(jPanel22);
    registerPanel(jPanel21);
    registerPanel(jPanel23);
    registerPanel(jPanel24);
}
    
    
    private void registerPanel(JPanel panel) {
    attemptMap.put(panel, 3);
    lockoutMinutes.put(panel, 1);
    lockoutEndTime.put(panel, 0L);

    panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Setup modern progress bar (hidden initially)
    JProgressBar progressBar = new JProgressBar();
    progressBar.setVisible(false);
    progressBar.setMaximum(100);
    progressBar.setValue(100);
    progressBar.setForeground(new Color(255, 69, 0)); // Modern red-orange
    progressBar.setBorderPainted(false);
    progressBar.setPreferredSize(new Dimension(panel.getWidth(), 4)); // Thin line
    progressBars.put(panel, progressBar);

    panel.setLayout(new BorderLayout());
    panel.add(progressBar, BorderLayout.SOUTH);

    panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rentalAgentMode) {
                return;
            }
            // 🔒 Skip click if user clicked a label that should be ignored
            if ((panel == jPanel22 && e.getSource() == jLabel1) ||
                (panel == jPanel21 && e.getSource() == jLabel2) ||
                (panel == jPanel23 && e.getSource() == jLabel3) ||
                (panel == jPanel24 && e.getSource() == jLabel4)) {
                return; // Do nothing
            }

            if (!editEnabled) {
                JOptionPane.showMessageDialog(null, "Press the Edit button first to enable deleting.");
                return;
            }

            String carName = getCarNameFromPanel(panel);

            if (carName == null || carName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No car to delete in this panel.",
                    "Delete Not Allowed",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String status = getActiveStatusIfLocked(panel);

            if (status != null) {
                JOptionPane.showMessageDialog(
                    null,
                    "THIS UNIT IS CURRENTLY " + status + ".\nYOU CANNOT DELETE IT.",
                    "DELETE NOT ALLOWED",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (lockedPanels.contains(panel)) {
                JOptionPane.showMessageDialog(null, "This panel is locked. Please wait for the countdown to finish.");
                return;
            }

            selectedPanel = panel;
            showProgressCountdownIfLocked(panel);

        int choice = JOptionPane.showOptionDialog(
        null,
        "Choose delete type:",
        "Delete Panel",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.WARNING_MESSAGE,
        null,
        new Object[]{"Delete", "Delete Permanently", "Cancel"},
        "Delete"
);

if (choice == 0) {
    attemptPasswordAndDelete(panel, false); // normal delete (RESTORE allowed)
} else if (choice == 1) {
    attemptPasswordAndDelete(panel, true);  // permanent delete (NO restore)
}
// else cancel -> do nothing


        }
    });

    // Optional: consume the label click so it doesn’t bubble up
    if (panel == jPanel22) jLabel1.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) { e.consume(); }
    });
    if (panel == jPanel21) jLabel2.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) { e.consume(); }
    });
    if (panel == jPanel23) jLabel3.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) { e.consume(); }
    });
    if (panel == jPanel24) jLabel4.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) { e.consume(); }
    });
}
 
    private String promptAdminPasswordWithToggle() {
    JPasswordField pf = new JPasswordField(15);
    pf.setEchoChar('*'); // ✅ asterisk masking

    JButton toggleBtn = new JButton("Show");
    toggleBtn.addActionListener(e -> {
        if (pf.getEchoChar() == (char) 0) {
            pf.setEchoChar('*');
            toggleBtn.setText("Show");
        } else {
            pf.setEchoChar((char) 0); // ✅ show characters
            toggleBtn.setText("Hide");
        }
    });

    JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 5));
    row.add(new JLabel("Admin password:"));
    row.add(pf);
    row.add(toggleBtn);

    int result = JOptionPane.showConfirmDialog(
            this,
            row,
            "Enter Admin Password",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) return null;

    char[] pass = pf.getPassword();
    return (pass == null) ? null : new String(pass);
}



    //RESTORES
    private void captureOriginalOrder() {
    Component[] comps = jPanel3.getComponents();
    for (int i = 0; i < comps.length; i++) {
        if (comps[i] instanceof JPanel) {
            originalIndexMap.put((JPanel) comps[i], i);
        }
    }
}

private void markPanelDeleted(JPanel panel, boolean addToSaveList) {
    if (!InventoryDeleteRestoreSync.canShowInRestore(
            getCarNameFromPanel(panel), getPlateNumberFromPanel(panel), "CHEVROLET")) {
        return;
    }
    if (deletedPanels.contains(panel)) return;

    deletedPanels.add(panel);
    deletedIndexes.add(originalIndexMap.getOrDefault(panel, Integer.MAX_VALUE));

    ModelDeletedCar car = extractCarInfoFromPanel(panel);
    if (car != null) {
        deletedCarByPanel.put(panel, car);

        if (addToSaveList) {
            deletedCars.add(car); // ✅ only add when user deletes this session
        }
    }
}

private String getDeletedDisplayName(JPanel panel) {
    ModelDeletedCar car = deletedCarByPanel.get(panel);
    if (car == null) car = extractCarInfoFromPanel(panel);

    if (car != null && car.getName() != null && !car.getName().trim().isEmpty()) {
        return car.getName();
    }
    return "Deleted Panel";
}

// ✅ compute correct insert index based on ORIGINAL order (works even if many missing)
private int computeInsertIndexByOriginalOrder(JPanel panel) {
    Integer target = originalIndexMap.get(panel);
    if (target == null) return jPanel3.getComponentCount();

    int insert = 0;
    for (Component c : jPanel3.getComponents()) {
        if (c instanceof JPanel) {
            Integer idx = originalIndexMap.get((JPanel) c);
            if (idx != null && idx < target) insert++;
        } else {
            insert++;
        }
    }
    return insert;
}

private void restoreOnePanel(JPanel panel) {
    if (panel.getParent() == jPanel3) return;

    // optional: re-apply original size
    Dimension size = originalSizes.get(panel);
    if (size != null) {
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);
    }

    int insertIndex = computeInsertIndexByOriginalOrder(panel);
    jPanel3.add(panel, insertIndex);
    jPanel3.revalidate();
    jPanel3.repaint();
}

private void removeDeletedCarTracking(JPanel panel) {
    ModelDeletedCar car = deletedCarByPanel.get(panel);
    if (car != null && car.getName() != null) {
        // ✅ remove from DB so it shows again next app open
        deleteCarFromDatabase(car.getName(), getPlateNumberFromPanel(panel));

        // ✅ prevent saving it again when you click Back (saveDeletedCarsToDatabase)
        deletedCars.removeIf(c -> c.getName() != null && c.getName().equals(car.getName()));
    }
    deletedCarByPanel.remove(panel);
}

    
    
private void showProgressCountdownIfLocked(JPanel panel) {
    long end = lockoutEndTime.get(panel);
    long now = System.currentTimeMillis();
    long duration = end - now;

    if (duration <= 0) {
        JProgressBar pb = progressBars.get(panel);
        pb.setVisible(false);
        if (progressTimers.containsKey(panel)) {
            progressTimers.get(panel).stop();
        }
        return;
    }

    JProgressBar progressBar = progressBars.get(panel);
    progressBar.setVisible(true);
    progressBar.setValue(100);

    Timer timer = new Timer(1000, null);
    final long total = duration;

    timer.addActionListener(new ActionListener() {
        long remaining = duration;

        @Override
        public void actionPerformed(ActionEvent e) {
            remaining -= 1000;
            int percent = (int) ((remaining * 100) / total);
            progressBar.setValue(percent);

            if (remaining <= 0) {
             progressBar.setVisible(false);
             lockedPanels.remove(panel);  // UNLOCK panel
             timer.stop();
            }

        }
    });

    if (progressTimers.containsKey(panel)) {
        progressTimers.get(panel).stop();
    }

    progressTimers.put(panel, timer);
    timer.start();
}
    
private void deleteCarFromDatabase(String carName, String plate) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection(config.AppConfig.getDatabaseUrl(), config.AppConfig.getDatabaseUser(), config.AppConfig.getDatabasePassword());
        String sql = "DELETE FROM cars_deleted WHERE CARS_NAME = ? "
                + "AND TRIM(CARS_PLATENUMBER) = TRIM(?) AND CAR_BRAND = 'CHEVROLET' "
                + "AND (DELETED_DATE IS NULL OR UPPER(TRIM(DELETED_DATE)) NOT LIKE 'PERMANENT%')";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, carName);
        stmt.setString(2, plate);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

// ✅ Finds the dot label for BOTH existing cars and car_added slots
private JLabel findDotLabelByCarName(String carName) {
    if (carName == null) return null;
    carName = carName.trim();
    if (carName.isEmpty()) return null;

    // 1) Existing cars (hardcoded map)
    JLabel dot = carDotLabels.get(carName);
    if (dot != null) return dot;

    // 2) New cars (dynamic slots) — match against the slot textfields
    if (carName.equalsIgnoreCase(jTextField90.getText().trim()))  return jLabel41; // slot 0
    if (carName.equalsIgnoreCase(jTextField88.getText().trim()))  return jLabel43; // slot 1
    if (carName.equalsIgnoreCase(jTextField97.getText().trim()))  return jLabel45; // slot 2
    if (carName.equalsIgnoreCase(jTextField110.getText().trim())) return jLabel81; // slot 3

    return null;
}

// ✅ Set dot color immediately (no DB query needed)
public void setCarDotActiveNow(String carName, boolean active) {
    JLabel dot = findDotLabelByCarName(carName);
    if (dot == null) return;

    java.awt.Color color = active ? ORANGE : GREEN;

    javax.swing.SwingUtilities.invokeLater(() -> {
        dot.setForeground(color);
        dot.repaint();
    });
}

// ✅ If you prefer: update dot from DB (uses your ServiceCustomer.isCarActive)
public void refreshCarDotFromDB(String carName) {
    if (carName == null) return;
    carName = carName.trim();
    if (carName.isEmpty()) return;

    try {
        ServiceCustomer service = new ServiceCustomer();
        boolean active = service.isCarActive(carName);
        setCarDotActiveNow(carName, active);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadCarDataFromDatabase() {
    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String password = config.AppConfig.getDatabasePassword();

    String sql = "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                 "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION " +
                 "FROM car_added WHERE CAR_BRAND = 'CHEVROLET' ORDER BY CARS_PLATENUMBER ASC LIMIT 1";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {


            String carName = rs.getString("CARS_NAME");

            if (carName != null && !carName.trim().isEmpty()) {
                jTextField90.setText(carName);
                updateCarColor(carName, jLabel41);
                jTextField46.setText(rs.getString("CARS_TYPE"));
                jLabel21.setText(rs.getString("CARS_PLATENUMBER"));
                jTextField106.setText(rs.getString("CARS_FUEL"));
                jTextField107.setText(rs.getString("CARS_SEATS"));
                jTextField15.setText(rs.getString("CARS_DAILYRENT"));
                jTextField64.setText(rs.getString("CARS_MODEL"));
                jTextField93.setText(rs.getString("CARS_RATINGS"));
                SEMI8.setText(rs.getString("CAR_TRANSMISSION"));

                byte[] imageBytes = rs.getBytes("CARS_IMAGE");
                if (imageBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage().getScaledInstance(
                        jLabel117.getWidth(), jLabel117.getHeight(),
                        Image.SCALE_SMOOTH
                    );
                    jLabel117.setIcon(new ImageIcon(image));
                } else {
                    jLabel117.setIcon(null);
                }

               

                jPanel2.setVisible(false);
                jLabel113.setVisible(true);
                jLabel111.setVisible(true);
                jLabel39.setVisible(true);
                rSMaterialButtonRectangle11.setVisible(true);
                jLabel41.setVisible(true);
                jLabel40.setVisible(true);
                jLabel47.setVisible(true);

            } else {
                jPanel2.setVisible(true);
                jLabel113.setVisible(false);
                jLabel111.setVisible(false);
                jLabel39.setVisible(false);
                rSMaterialButtonRectangle11.setVisible(false);
                jLabel41.setVisible(false);
                jLabel40.setVisible(false);
                jLabel47.setVisible(false);
            }
                           
                // after filling jTextField90, etc.
                checkCarDataAndSetPanelVisibility();

        } else {
            jPanel2.setVisible(true);
            jLabel113.setVisible(false);
            jLabel111.setVisible(false);
            jLabel39.setVisible(false);
            rSMaterialButtonRectangle11.setVisible(false);
            jLabel41.setVisible(false);
            jLabel40.setVisible(false);
            jLabel47.setVisible(false);
            
            checkCarDataAndSetPanelVisibility();

        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

private void checkCarDataAndSetPanelVisibility() {
    String carName = jTextField90.getText().trim();

    if (carName.isEmpty()) {
        // No data in jTextField90 → hide all panels
        jPanel21.setVisible(false);
    } else {
        // Data exists → show only jPanel21, hide the rest
        jPanel21.setVisible(true);
    }
}


private void loadCarDataFromDatabase1() {
    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String password = config.AppConfig.getDatabasePassword();

    // ✅ Get only the second row
    String sql = "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                 "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION " +
                 "FROM car_added WHERE CAR_BRAND = 'CHEVROLET' ORDER BY CARS_PLATENUMBER ASC LIMIT 1 OFFSET 1";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            String carName = rs.getString("CARS_NAME");

            if (carName != null && !carName.trim().isEmpty()) {
                jTextField88.setText(carName);
                updateCarColor(carName, jLabel43);
                jTextField47.setText(rs.getString("CARS_TYPE"));
                jLabel20.setText(rs.getString("CARS_PLATENUMBER"));
                jTextField116.setText(rs.getString("CARS_FUEL"));
                jTextField115.setText(rs.getString("CARS_SEATS"));
                jTextField14.setText(rs.getString("CARS_DAILYRENT"));
                jTextField68.setText(rs.getString("CARS_MODEL"));
                jTextField94.setText(rs.getString("CARS_RATINGS"));
                SEMI9.setText(rs.getString("CAR_TRANSMISSION"));

                byte[] imageBytes = rs.getBytes("CARS_IMAGE");
                if (imageBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage().getScaledInstance(
                        jLabel109.getWidth(), jLabel109.getHeight(),
                        Image.SCALE_SMOOTH
                    );
                    jLabel109.setIcon(new ImageIcon(image));
                } else {
                    jLabel109.setIcon(null);
                }

                jPanel5.setVisible(false);
                jLabel115.setVisible(true);
                jLabel114.setVisible(true);
                jLabel44.setVisible(true);
                rSMaterialButtonRectangle10.setVisible(true);
                jLabel43.setVisible(true);
                jLabel42.setVisible(true);
                jLabel49.setVisible(true);

            } else {
                jPanel5.setVisible(true);
                jLabel115.setVisible(false);
                jLabel114.setVisible(false);
                jLabel44.setVisible(false);
                rSMaterialButtonRectangle10.setVisible(false);
                jLabel43.setVisible(false);
                jLabel42.setVisible(false);
                jLabel49.setVisible(false);
            }

            // ✅ Panel visibility check
            checkCarDataAndSetPanelVisibility1();

        } else {
            jPanel5.setVisible(true);
            jLabel115.setVisible(false);
            jLabel114.setVisible(false);
            jLabel44.setVisible(false);
            rSMaterialButtonRectangle10.setVisible(false);
            jLabel43.setVisible(false);
            jLabel42.setVisible(false);
            jLabel49.setVisible(false);

            checkCarDataAndSetPanelVisibility1();
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


private void checkCarDataAndSetPanelVisibility1() {
    String carName = jTextField88.getText().trim();

    if (carName.isEmpty()) {
        // No data in jTextField88 → hide all panels

        jPanel23.setVisible(false);

    } else {
        // Data exists → show only jPanel23, hide the rest

        jPanel23.setVisible(true);

    }
}

private void loadCarDataFromDatabase2() {
    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String password = config.AppConfig.getDatabasePassword();

    // ✅ Fetch the 3rd row only
    String sql = "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                 "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION " +
                 "FROM car_added WHERE CAR_BRAND = 'CHEVROLET' ORDER BY CARS_PLATENUMBER ASC LIMIT 1 OFFSET 2";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            String carName = rs.getString("CARS_NAME");

            if (carName != null && !carName.trim().isEmpty()) {
                jTextField97.setText(carName);
                updateCarColor(carName, jLabel45);
                jTextField48.setText(rs.getString("CARS_TYPE"));
                jLabel35.setText(rs.getString("CARS_PLATENUMBER"));
                jTextField117.setText(rs.getString("CARS_FUEL"));
                jTextField118.setText(rs.getString("CARS_SEATS"));
                jTextField16.setText(rs.getString("CARS_DAILYRENT"));
                jTextField69.setText(rs.getString("CARS_MODEL"));
                jTextField104.setText(rs.getString("CARS_RATINGS"));
                SEMI10.setText(rs.getString("CAR_TRANSMISSION"));

                byte[] imageBytes = rs.getBytes("CARS_IMAGE");
                if (imageBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage().getScaledInstance(
                        jLabel125.getWidth(), jLabel125.getHeight(),
                        Image.SCALE_SMOOTH
                    );
                    jLabel125.setIcon(new ImageIcon(image));
                } else {
                    jLabel125.setIcon(null);
                }

                jPanel7.setVisible(false);
                jLabel146.setVisible(true);
                jLabel143.setVisible(true);
                jLabel46.setVisible(true);
                rSMaterialButtonRectangle12.setVisible(true);
                jLabel45.setVisible(true);
                jLabel50.setVisible(true);
                jLabel51.setVisible(true);

            } else {
                jPanel7.setVisible(true);
                jLabel146.setVisible(false);
                jLabel143.setVisible(false);
                jLabel46.setVisible(false);
                rSMaterialButtonRectangle12.setVisible(false);
                jLabel45.setVisible(false);
                jLabel50.setVisible(false);
                jLabel51.setVisible(false);
            }

            // ✅ Panel visibility check
            checkCarDataAndSetPanelVisibility2();

        } else {
            jPanel7.setVisible(true);
            jLabel146.setVisible(false);
            jLabel143.setVisible(false);
            jLabel46.setVisible(false);
            rSMaterialButtonRectangle12.setVisible(false);
            jLabel45.setVisible(false);
            jLabel50.setVisible(false);
            jLabel51.setVisible(false);

            checkCarDataAndSetPanelVisibility2();
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


private void checkCarDataAndSetPanelVisibility2() {
    String carName = jTextField97.getText().trim();

    if (carName.isEmpty()) {
        // No data in jTextField97 → hide all panels


        jPanel24.setVisible(false);
    } else {
        // Data exists → show only jPanel23, hide the rest

        jPanel24.setVisible(true);
    }
}

private void loadCarDataFromDatabase3() {
    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String password = config.AppConfig.getDatabasePassword();

    // ✅ Fetch the 3rd row only
    String sql = "SELECT CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                 "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION " +
                 "FROM car_added WHERE CAR_BRAND = 'CHEVROLET' ORDER BY CARS_PLATENUMBER ASC LIMIT 1 OFFSET 3";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            String carName = rs.getString("CARS_NAME");

            if (carName != null && !carName.trim().isEmpty()) {
                jTextField110.setText(carName);
                updateCarColor(carName, jLabel81);
                jTextField49.setText(rs.getString("CARS_TYPE"));
                jLabel36.setText(rs.getString("CARS_PLATENUMBER"));
                jTextField119.setText(rs.getString("CARS_FUEL"));
                jTextField120.setText(rs.getString("CARS_SEATS"));
                jTextField17.setText(rs.getString("CARS_DAILYRENT"));
                jTextField72.setText(rs.getString("CARS_MODEL"));
                jTextField105.setText(rs.getString("CARS_RATINGS"));
                SEMI11.setText(rs.getString("CAR_TRANSMISSION"));

                byte[] imageBytes = rs.getBytes("CARS_IMAGE");
                if (imageBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage().getScaledInstance(
                        jLabel133.getWidth(), jLabel133.getHeight(),
                        Image.SCALE_SMOOTH
                    );
                    jLabel133.setIcon(new ImageIcon(image));
                } else {
                    jLabel133.setIcon(null);
                }

                jPanel8.setVisible(false);
                jLabel95.setVisible(true);
                jLabel87.setVisible(true);
                jLabel89.setVisible(true);
                rSMaterialButtonRectangle13.setVisible(true);
                jLabel81.setVisible(true);
                jLabel108.setVisible(true);
                jLabel52.setVisible(true);

            } else {
                jPanel8.setVisible(true);
                jLabel95.setVisible(false);
                jLabel87.setVisible(false);
                jLabel89.setVisible(false);
                rSMaterialButtonRectangle13.setVisible(false);
                jLabel81.setVisible(false);
                jLabel108.setVisible(false);
                jLabel52.setVisible(false);
            }


        } else {
            jPanel8.setVisible(true);
            jLabel95.setVisible(false);
            jLabel87.setVisible(false);
            jLabel89.setVisible(false);
            rSMaterialButtonRectangle13.setVisible(false);
            jLabel81.setVisible(false);
            jLabel108.setVisible(false);
            jLabel52.setVisible(false);

        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


private void syncExistingCarsToInventory() {
    CarInventorySaver.saveAlphabeticallyToInventory(java.util.Arrays.asList(
        CarInventorySaver.car("jPanel13", jTextField30, jTextField35, jLabel9,
                jTextField33, jTextField32, jTextField6, jTextField31,
                jTextField34, SEMI, jLabel32),
        CarInventorySaver.car("jPanel14", jTextField38, jTextField43, jLabel12,
                jTextField41, jTextField40, jTextField7, jTextField36,
                jTextField39, SEMI3, jLabel38),
        CarInventorySaver.car("jPanel16", jTextField54, jTextField59, jLabel10,
                jTextField95, jTextField96, jTextField8, jTextField45,
                jTextField55, SEMI7, jLabel58),
        CarInventorySaver.car("jPanel6", jTextField21, jTextField28, jLabel13,
                jTextField98, jTextField58, jTextField9, jTextField56,
                jTextField22, SEMI2, jLabel26),
        CarInventorySaver.car("jPanel17", jTextField62, jTextField67, jLabel18,
                jTextField80, jTextField73, jTextField10, jTextField37,
                jTextField63, SEMI6, jLabel66),
        CarInventorySaver.car("jPanel18", jTextField70, jTextField75, jLabel17,
                jTextField81, jTextField74, jTextField11, jTextField42,
                jTextField71, SEMI1, jLabel74),
        CarInventorySaver.car("jPanel19", jTextField78, jTextField83, jLabel16,
                jTextField100, jTextField60, jTextField12, jTextField44,
                jTextField79, SEMI5, jLabel82),
        CarInventorySaver.car("jPanel20", jTextField86, jTextField91, jLabel15,
                jTextField102, jTextField103, jTextField13, jTextField57,
                jTextField87, SEMI4, jLabel90)
    ));
}

private void saveCarInfoToDatabase() {
    String url = config.AppConfig.getDatabaseUrl();
    String user = config.AppConfig.getDatabaseUser();
    String password = config.AppConfig.getDatabasePassword();

    String sql = "INSERT INTO cars_info " +
            "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
            " CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 1) GET VALUES FROM YOUR FIELDS
    String carName = jTextField86.getText().trim();
    String carType = jTextField91.getText().trim();
    String plateNumber = jLabel15.getText().trim();     // you used JLabel for plate
    String fuel = jTextField102.getText().trim();
    String seats = jTextField103.getText().trim();
    String dailyRent = jTextField13.getText().trim();
    String model = jTextField57.getText().trim();
    String ratings = jTextField87.getText().trim();
    String transmission = SEMI4.getText().trim();       // whatever SEMI8 is in your UI

    // Optional basic validation (adjust as you want)
    if (carName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Car name is empty.");
        return;
    }

    // 2) GET IMAGE BYTES FROM jLabel117 ICON
    byte[] imageBytes = null;
    try {
        imageBytes = getImageBytesFromLabel(jLabel90);
    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to read image from label.");
        return;
    }

    // 3) SAVE TO DATABASE
    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, carName);
        stmt.setString(2, carType);
        stmt.setString(3, plateNumber);
        stmt.setString(4, fuel);
        stmt.setString(5, seats);
        stmt.setString(6, dailyRent);
        stmt.setString(7, model);
        stmt.setString(8, ratings);

        if (imageBytes != null) {
            stmt.setBytes(9, imageBytes);
        } else {
            stmt.setNull(9, Types.BLOB);
        }

        stmt.setString(10, transmission);

        int rows = stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Saved to car_info! Rows inserted: " + rows);

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
}

/**
 * Converts the icon in a JLabel to byte[] (PNG).
 * Returns null if there is no icon.
 */
private byte[] getImageBytesFromLabel(JLabel label) throws IOException {
    Icon icon = label.getIcon();
    if (!(icon instanceof ImageIcon)) return null;

    Image image = ((ImageIcon) icon).getImage();
    if (image == null) return null;

    int w = image.getWidth(null);
    int h = image.getHeight(null);
    if (w <= 0 || h <= 0) return null;

    BufferedImage buffered = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = buffered.createGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(buffered, "png", baos);
    return baos.toByteArray();
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel32 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        rSMaterialButtonRectangle14 = new rojerusan.RSMaterialButtonRectangle();
        jLabel9 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        SEMI = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel18 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel74 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        rSMaterialButtonRectangle5 = new rojerusan.RSMaterialButtonRectangle();
        jTextField11 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        SEMI1 = new javax.swing.JLabel();
        jPanel6 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel26 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        rSMaterialButtonRectangle8 = new rojerusan.RSMaterialButtonRectangle();
        jTextField9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        SEMI2 = new javax.swing.JLabel();
        jPanel14 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel38 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        rSMaterialButtonRectangle4 = new rojerusan.RSMaterialButtonRectangle();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        SEMI3 = new javax.swing.JLabel();
        jPanel16 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel58 = new javax.swing.JLabel();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField95 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        rSMaterialButtonRectangle6 = new rojerusan.RSMaterialButtonRectangle();
        jTextField8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        SEMI7 = new javax.swing.JLabel();
        jPanel17 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel66 = new javax.swing.JLabel();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField73 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        jTextField10 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        SEMI6 = new javax.swing.JLabel();
        jPanel19 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jLabel82 = new javax.swing.JLabel();
        jTextField78 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        rSMaterialButtonRectangle7 = new rojerusan.RSMaterialButtonRectangle();
        jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        SEMI5 = new javax.swing.JLabel();
        jPanel20 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jTextField86 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        rSMaterialButtonRectangle9 = new rojerusan.RSMaterialButtonRectangle();
        jTextField13 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        SEMI4 = new javax.swing.JLabel();
        jPanel22 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jPanel2 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel1 = new javax.swing.JLabel();
        jLabel04 = new javax.swing.JLabel();
        jTextField107 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField93 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jTextField106 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        rSMaterialButtonRectangle11 = new rojerusan.RSMaterialButtonRectangle();
        jTextField15 = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        SEMI8 = new javax.swing.JLabel();
        jPanel21 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jPanel5 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel2 = new javax.swing.JLabel();
        jTextField88 = new javax.swing.JTextField();
        rSMaterialButtonRectangle10 = new rojerusan.RSMaterialButtonRectangle();
        jTextField14 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jTextField68 = new javax.swing.JTextField();
        jTextField94 = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jTextField115 = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        jTextField116 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        SEMI9 = new javax.swing.JLabel();
        jPanel23 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jPanel7 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel3 = new javax.swing.JLabel();
        jTextField97 = new javax.swing.JTextField();
        rSMaterialButtonRectangle12 = new rojerusan.RSMaterialButtonRectangle();
        jTextField16 = new javax.swing.JTextField();
        jLabel125 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField117 = new javax.swing.JTextField();
        jTextField104 = new javax.swing.JTextField();
        jTextField118 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        SEMI10 = new javax.swing.JLabel();
        jPanel24 =  new ROUNDPANEL(20, new Color(0, 0, 0), 2);
        jPanel8 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jLabel4 = new javax.swing.JLabel();
        jTextField110 = new javax.swing.JTextField();
        rSMaterialButtonRectangle13 = new rojerusan.RSMaterialButtonRectangle();
        jLabel133 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jTextField72 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField119 = new javax.swing.JTextField();
        jTextField105 = new javax.swing.JTextField();
        jTextField120 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        SEMI11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rSMaterialButtonRectangle15 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle16 = new rojerusan.RSMaterialButtonRectangle();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1347, 749));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new java.awt.GridLayout(3, 0, 8, 10));

        jPanel13.setBackground(new java.awt.Color(26, 27, 27));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (27).png"))); // NOI18N
        jPanel13.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 160));

        jTextField30.setEditable(false);
        jTextField30.setBackground(new java.awt.Color(26, 27, 27));
        jTextField30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField30.setForeground(new java.awt.Color(255, 255, 255));
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField30.setText("CHEVROLET CRUZE");
        jTextField30.setBorder(null);
        jPanel13.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jTextField31.setBackground(new java.awt.Color(26, 27, 27));
        jTextField31.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField31.setForeground(new java.awt.Color(255, 255, 255));
        jTextField31.setText("2018");
        jTextField31.setBorder(null);
        jPanel13.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 210, 30, 30));

        jTextField32.setBackground(new java.awt.Color(26, 27, 27));
        jTextField32.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField32.setForeground(new java.awt.Color(255, 255, 255));
        jTextField32.setText("5 SEATS");
        jTextField32.setBorder(null);
        jPanel13.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        jTextField33.setBackground(new java.awt.Color(26, 27, 27));
        jTextField33.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField33.setForeground(new java.awt.Color(255, 255, 255));
        jTextField33.setText("GASOLINE");
        jTextField33.setBorder(null);
        jPanel13.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jTextField35.setBackground(new java.awt.Color(26, 27, 27));
        jTextField35.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField35.setForeground(new java.awt.Color(255, 255, 255));
        jTextField35.setText("HATCHBACK");
        jTextField35.setBorder(null);
        jPanel13.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 130, 40));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 255, 51));
        jLabel28.setText(".");
        jPanel13.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField34.setBackground(new java.awt.Color(26, 27, 27));
        jTextField34.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField34.setForeground(new java.awt.Color(255, 255, 255));
        jTextField34.setText("4.4");
        jTextField34.setBorder(null);
        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });
        jPanel13.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        rSMaterialButtonRectangle14.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle14.setBorder(null);
        rSMaterialButtonRectangle14.setText("BOOK / RENT");
        rSMaterialButtonRectangle14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle14MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle14ActionPerformed(evt);
            }
        });
        jPanel13.add(rSMaterialButtonRectangle14, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jLabel9.setBackground(new java.awt.Color(26, 27, 27));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(26, 27, 27));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("CHEV 5981 ");
        jPanel13.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel13.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel13.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel13.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 40, 40));

        jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel13.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 30, 60));

        jLabel153.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel13.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jTextField6.setBackground(new java.awt.Color(27, 26, 26));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField6.setText("₱2000 DAILY");
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
        jPanel13.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        SEMI.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI.setForeground(new java.awt.Color(255, 255, 255));
        SEMI.setText("MANUAL");
        jPanel13.add(SEMI, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel13.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        jPanel3.add(jPanel13);

        jPanel18.setBackground(new java.awt.Color(26, 27, 27));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com.png"))); // NOI18N
        jPanel18.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 160));

        jTextField70.setBackground(new java.awt.Color(26, 27, 27));
        jTextField70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField70.setForeground(new java.awt.Color(255, 255, 255));
        jTextField70.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField70.setText("CHEVROLET TAHOE");
        jTextField70.setBorder(null);
        jPanel18.add(jTextField70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField71.setBackground(new java.awt.Color(26, 27, 27));
        jTextField71.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField71.setForeground(new java.awt.Color(255, 255, 255));
        jTextField71.setText("4.5");
        jTextField71.setBorder(null);
        jPanel18.add(jTextField71, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField75.setBackground(new java.awt.Color(26, 27, 27));
        jTextField75.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField75.setForeground(new java.awt.Color(255, 255, 255));
        jTextField75.setText("SUV");
        jTextField75.setBorder(null);
        jPanel18.add(jTextField75, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 90, 40));

        jTextField81.setBackground(new java.awt.Color(26, 27, 27));
        jTextField81.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField81.setForeground(new java.awt.Color(255, 255, 255));
        jTextField81.setText("DIESEL");
        jTextField81.setBorder(null);
        jPanel18.add(jTextField81, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 255, 51));
        jLabel30.setText(".");
        jPanel18.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField42.setBackground(new java.awt.Color(26, 27, 27));
        jTextField42.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField42.setForeground(new java.awt.Color(255, 255, 255));
        jTextField42.setText("2018");
        jTextField42.setBorder(null);
        jPanel18.add(jTextField42, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField74.setBackground(new java.awt.Color(26, 27, 27));
        jTextField74.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField74.setForeground(new java.awt.Color(255, 255, 255));
        jTextField74.setText("8 SEATS");
        jTextField74.setBorder(null);
        jPanel18.add(jTextField74, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        rSMaterialButtonRectangle5.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle5.setBorder(null);
        rSMaterialButtonRectangle5.setText("BOOK / RENT");
        rSMaterialButtonRectangle5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle5MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle5ActionPerformed(evt);
            }
        });
        jPanel18.add(rSMaterialButtonRectangle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField11.setBackground(new java.awt.Color(27, 26, 26));
        jTextField11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField11.setText("₱10000 DAILY");
        jTextField11.setBorder(null);
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
        jPanel18.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel17.setBackground(new java.awt.Color(26, 27, 27));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(26, 27, 27));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("CHLT 3434  ");
        jPanel18.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel18.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel18.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel18.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel159.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel18.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel18.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel161.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel18.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel18.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI1.setForeground(new java.awt.Color(255, 255, 255));
        SEMI1.setText("AUTOMATIC");
        jPanel18.add(SEMI1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel18);

        jPanel6.setBackground(new java.awt.Color(26, 27, 27));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (2).png"))); // NOI18N
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 160));

        jTextField21.setBackground(new java.awt.Color(26, 27, 27));
        jTextField21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField21.setForeground(new java.awt.Color(255, 255, 255));
        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField21.setText("CHEVROLET TRACKER");
        jTextField21.setBorder(null);
        jPanel6.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField22.setBackground(new java.awt.Color(26, 27, 27));
        jTextField22.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField22.setForeground(new java.awt.Color(255, 255, 255));
        jTextField22.setText("4.5");
        jTextField22.setBorder(null);
        jPanel6.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField28.setBackground(new java.awt.Color(26, 27, 27));
        jTextField28.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField28.setForeground(new java.awt.Color(255, 255, 255));
        jTextField28.setText("SEDAN");
        jTextField28.setBorder(null);
        jPanel6.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 110, 40));

        jTextField98.setBackground(new java.awt.Color(26, 27, 27));
        jTextField98.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField98.setForeground(new java.awt.Color(255, 255, 255));
        jTextField98.setText("GASOLINE");
        jTextField98.setBorder(null);
        jPanel6.add(jTextField98, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(0, 255, 51));
        jLabel106.setText(".");
        jPanel6.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField56.setBackground(new java.awt.Color(26, 27, 27));
        jTextField56.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField56.setForeground(new java.awt.Color(255, 255, 255));
        jTextField56.setText("2017");
        jTextField56.setBorder(null);
        jPanel6.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField58.setBackground(new java.awt.Color(26, 27, 27));
        jTextField58.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField58.setForeground(new java.awt.Color(255, 255, 255));
        jTextField58.setText("5 SEATS");
        jTextField58.setBorder(null);
        jPanel6.add(jTextField58, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        rSMaterialButtonRectangle8.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle8.setBorder(null);
        rSMaterialButtonRectangle8.setText("BOOK / RENT");
        rSMaterialButtonRectangle8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle8ActionPerformed(evt);
            }
        });
        jPanel6.add(rSMaterialButtonRectangle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 120, 40));

        jTextField9.setBackground(new java.awt.Color(27, 26, 26));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(255, 255, 255));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setText("₱6000 DAILY");
        jTextField9.setBorder(null);
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
        jPanel6.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel13.setBackground(new java.awt.Color(26, 27, 27));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(26, 27, 27));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("CHEV 3325");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel6.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel6.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel6.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel6.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel164.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel6.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel165.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel6.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI2.setForeground(new java.awt.Color(255, 255, 255));
        SEMI2.setText("AUTOMATIC");
        jPanel6.add(SEMI2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel6);

        jPanel14.setBackground(new java.awt.Color(26, 27, 27));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (5).png"))); // NOI18N
        jPanel14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 150));

        jTextField38.setBackground(new java.awt.Color(26, 27, 27));
        jTextField38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField38.setForeground(new java.awt.Color(255, 255, 255));
        jTextField38.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField38.setText("CHEVROLET CAMARO");
        jTextField38.setBorder(null);
        jPanel14.add(jTextField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField39.setBackground(new java.awt.Color(26, 27, 27));
        jTextField39.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField39.setForeground(new java.awt.Color(255, 255, 255));
        jTextField39.setText("5.0");
        jTextField39.setBorder(null);
        jPanel14.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField40.setBackground(new java.awt.Color(26, 27, 27));
        jTextField40.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField40.setForeground(new java.awt.Color(255, 255, 255));
        jTextField40.setText("4 SEATS");
        jTextField40.setBorder(null);
        jPanel14.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        jTextField41.setBackground(new java.awt.Color(26, 27, 27));
        jTextField41.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField41.setForeground(new java.awt.Color(255, 255, 255));
        jTextField41.setText("GASOLINE");
        jTextField41.setBorder(null);
        jPanel14.add(jTextField41, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jTextField43.setBackground(new java.awt.Color(26, 27, 27));
        jTextField43.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField43.setForeground(new java.awt.Color(255, 255, 255));
        jTextField43.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField43.setText("LUXURY COUPE");
        jTextField43.setBorder(null);
        jPanel14.add(jTextField43, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, 20));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 255, 51));
        jLabel61.setText(".");
        jPanel14.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField36.setBackground(new java.awt.Color(26, 27, 27));
        jTextField36.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField36.setForeground(new java.awt.Color(255, 255, 255));
        jTextField36.setText("2016");
        jTextField36.setBorder(null);
        jPanel14.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        rSMaterialButtonRectangle4.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle4.setBorder(null);
        rSMaterialButtonRectangle4.setText("BOOK / RENT");
        rSMaterialButtonRectangle4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
            }
        });
        jPanel14.add(rSMaterialButtonRectangle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField7.setBackground(new java.awt.Color(27, 26, 26));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField7.setText("₱18000 DAILY");
        jTextField7.setBorder(null);
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
        jPanel14.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel12.setBackground(new java.awt.Color(26, 27, 27));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(26, 27, 27));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("CHEVPH");
        jPanel14.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel14.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel14.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel14.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel179.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel14.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel14.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel14.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel14.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI3.setForeground(new java.awt.Color(255, 255, 255));
        SEMI3.setText("SEMI-AUTO ");
        jPanel14.add(SEMI3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel14);

        jPanel16.setBackground(new java.awt.Color(26, 27, 27));
        jPanel16.setMinimumSize(new java.awt.Dimension(330, 330));
        jPanel16.setPreferredSize(new java.awt.Dimension(330, 330));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (15).png"))); // NOI18N
        jPanel16.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 150));

        jTextField54.setBackground(new java.awt.Color(26, 27, 27));
        jTextField54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField54.setForeground(new java.awt.Color(255, 255, 255));
        jTextField54.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField54.setText("CHEVROLET TRAX");
        jTextField54.setBorder(null);
        jPanel16.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField55.setBackground(new java.awt.Color(26, 27, 27));
        jTextField55.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField55.setForeground(new java.awt.Color(255, 255, 255));
        jTextField55.setText("4.0");
        jTextField55.setBorder(null);
        jPanel16.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField59.setBackground(new java.awt.Color(26, 27, 27));
        jTextField59.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField59.setForeground(new java.awt.Color(255, 255, 255));
        jTextField59.setText("SUV");
        jTextField59.setBorder(null);
        jPanel16.add(jTextField59, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 120, 40));

        jTextField95.setBackground(new java.awt.Color(26, 27, 27));
        jTextField95.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField95.setForeground(new java.awt.Color(255, 255, 255));
        jTextField95.setText("GASOLINE");
        jTextField95.setBorder(null);
        jTextField95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField95ActionPerformed(evt);
            }
        });
        jPanel16.add(jTextField95, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jTextField96.setBackground(new java.awt.Color(26, 27, 27));
        jTextField96.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField96.setForeground(new java.awt.Color(255, 255, 255));
        jTextField96.setText("5 SEATS");
        jTextField96.setBorder(null);
        jPanel16.add(jTextField96, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 255, 51));
        jLabel62.setText(".");
        jPanel16.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField45.setBackground(new java.awt.Color(26, 27, 27));
        jTextField45.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField45.setForeground(new java.awt.Color(255, 255, 255));
        jTextField45.setText("2016");
        jTextField45.setBorder(null);
        jPanel16.add(jTextField45, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        rSMaterialButtonRectangle6.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle6.setBorder(null);
        rSMaterialButtonRectangle6.setText("BOOK / RENT");
        rSMaterialButtonRectangle6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle6ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField8.setBackground(new java.awt.Color(27, 26, 26));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField8.setText("₱10000 DAILY");
        jTextField8.setBorder(null);
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
        jPanel16.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel10.setBackground(new java.awt.Color(26, 27, 27));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(26, 27, 27));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("CHEV 4123");
        jPanel16.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel16.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel16.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel16.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel16.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel170.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel16.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel16.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI7.setForeground(new java.awt.Color(255, 255, 255));
        SEMI7.setText("SEMI-AUTO ");
        jPanel16.add(SEMI7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(26, 27, 27));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (13).png"))); // NOI18N
        jPanel17.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -10, 290, 190));

        jTextField62.setBackground(new java.awt.Color(26, 27, 27));
        jTextField62.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField62.setForeground(new java.awt.Color(255, 255, 255));
        jTextField62.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField62.setText("CHEVROLET CORVETTE");
        jTextField62.setBorder(null);
        jPanel17.add(jTextField62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField63.setBackground(new java.awt.Color(26, 27, 27));
        jTextField63.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField63.setForeground(new java.awt.Color(255, 255, 255));
        jTextField63.setText("5.0");
        jTextField63.setBorder(null);
        jPanel17.add(jTextField63, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField67.setBackground(new java.awt.Color(26, 27, 27));
        jTextField67.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField67.setForeground(new java.awt.Color(255, 255, 255));
        jTextField67.setText("LUXURY COUPE");
        jTextField67.setBorder(null);
        jPanel17.add(jTextField67, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 130, 40));

        jTextField73.setBackground(new java.awt.Color(26, 27, 27));
        jTextField73.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField73.setForeground(new java.awt.Color(255, 255, 255));
        jTextField73.setText("2 SEATS");
        jTextField73.setBorder(null);
        jPanel17.add(jTextField73, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        jTextField80.setBackground(new java.awt.Color(26, 27, 27));
        jTextField80.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField80.setForeground(new java.awt.Color(255, 255, 255));
        jTextField80.setText("GASOLINE");
        jTextField80.setBorder(null);
        jPanel17.add(jTextField80, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 255, 51));
        jLabel29.setText(".");
        jPanel17.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField37.setBackground(new java.awt.Color(26, 27, 27));
        jTextField37.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField37.setForeground(new java.awt.Color(255, 255, 255));
        jTextField37.setText("2016");
        jTextField37.setBorder(null);
        jPanel17.add(jTextField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle3.setBorder(null);
        rSMaterialButtonRectangle3.setText("BOOK / RENT");
        rSMaterialButtonRectangle3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle3MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel17.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField10.setBackground(new java.awt.Color(27, 26, 26));
        jTextField10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 255, 255));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField10.setText("₱10000 DAILY");
        jTextField10.setBorder(null);
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
        jPanel17.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel18.setBackground(new java.awt.Color(26, 27, 27));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(26, 27, 27));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("CHEV 2234");
        jPanel17.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel17.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel17.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel17.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel17.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel17.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel157.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel17.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel17.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI6.setForeground(new java.awt.Color(255, 255, 255));
        SEMI6.setText("SEMI-AUTO ");
        jPanel17.add(SEMI6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel17);

        jPanel19.setBackground(new java.awt.Color(26, 27, 27));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (10).png"))); // NOI18N
        jPanel19.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 280, 180));

        jTextField78.setBackground(new java.awt.Color(26, 27, 27));
        jTextField78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField78.setForeground(new java.awt.Color(255, 255, 255));
        jTextField78.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField78.setText("CHEVROLET COLORADO");
        jTextField78.setBorder(null);
        jPanel19.add(jTextField78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField79.setBackground(new java.awt.Color(26, 27, 27));
        jTextField79.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField79.setForeground(new java.awt.Color(255, 255, 255));
        jTextField79.setText("4.5");
        jTextField79.setBorder(null);
        jPanel19.add(jTextField79, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField83.setBackground(new java.awt.Color(26, 27, 27));
        jTextField83.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField83.setForeground(new java.awt.Color(255, 255, 255));
        jTextField83.setText("PICKUP");
        jTextField83.setBorder(null);
        jPanel19.add(jTextField83, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 140, 40));

        jTextField100.setBackground(new java.awt.Color(26, 27, 27));
        jTextField100.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField100.setForeground(new java.awt.Color(255, 255, 255));
        jTextField100.setText("DIESEL");
        jTextField100.setBorder(null);
        jPanel19.add(jTextField100, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 255, 51));
        jLabel69.setText(".");
        jPanel19.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField44.setBackground(new java.awt.Color(26, 27, 27));
        jTextField44.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField44.setForeground(new java.awt.Color(255, 255, 255));
        jTextField44.setText("2017");
        jTextField44.setBorder(null);
        jPanel19.add(jTextField44, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField60.setBackground(new java.awt.Color(26, 27, 27));
        jTextField60.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField60.setForeground(new java.awt.Color(255, 255, 255));
        jTextField60.setText("5 SEATS");
        jTextField60.setBorder(null);
        jPanel19.add(jTextField60, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        rSMaterialButtonRectangle7.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle7.setBorder(null);
        rSMaterialButtonRectangle7.setText("BOOK / RENT");
        rSMaterialButtonRectangle7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle7MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle7ActionPerformed(evt);
            }
        });
        jPanel19.add(rSMaterialButtonRectangle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField12.setBackground(new java.awt.Color(27, 26, 26));
        jTextField12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 255, 255));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField12.setText("₱6000 DAILY");
        jTextField12.setBorder(null);
        jTextField12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField12FocusLost(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jPanel19.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel16.setBackground(new java.awt.Color(26, 27, 27));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(26, 27, 27));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("CHEV 3982");
        jPanel19.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel19.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel166.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel19.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel19.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel171.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel19.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel172.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel19.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel173.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel19.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel19.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI5.setForeground(new java.awt.Color(255, 255, 255));
        SEMI5.setText("SEMI-AUTO ");
        jPanel19.add(SEMI5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(26, 27, 27));
        jPanel20.setPreferredSize(new java.awt.Dimension(320, 330));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField86.setBackground(new java.awt.Color(26, 27, 27));
        jTextField86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField86.setForeground(new java.awt.Color(255, 255, 255));
        jTextField86.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField86.setText("CHEVROLET SPARK");
        jTextField86.setBorder(null);
        jPanel20.add(jTextField86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField87.setBackground(new java.awt.Color(26, 27, 27));
        jTextField87.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField87.setForeground(new java.awt.Color(255, 255, 255));
        jTextField87.setText("4.6");
        jTextField87.setBorder(null);
        jPanel20.add(jTextField87, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField91.setBackground(new java.awt.Color(26, 27, 27));
        jTextField91.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField91.setForeground(new java.awt.Color(255, 255, 255));
        jTextField91.setText("HATCHBACK");
        jTextField91.setBorder(null);
        jPanel20.add(jTextField91, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 130, 40));

        jTextField102.setBackground(new java.awt.Color(26, 27, 27));
        jTextField102.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField102.setForeground(new java.awt.Color(255, 255, 255));
        jTextField102.setText("GASOLINE");
        jTextField102.setBorder(null);
        jPanel20.add(jTextField102, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 60, 30));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 255, 51));
        jLabel70.setText(".");
        jPanel20.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField57.setBackground(new java.awt.Color(26, 27, 27));
        jTextField57.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField57.setForeground(new java.awt.Color(255, 255, 255));
        jTextField57.setText("2018");
        jTextField57.setBorder(null);
        jPanel20.add(jTextField57, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField103.setBackground(new java.awt.Color(26, 27, 27));
        jTextField103.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField103.setForeground(new java.awt.Color(255, 255, 255));
        jTextField103.setText("5 SEATS");
        jTextField103.setBorder(null);
        jPanel20.add(jTextField103, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 50, 30));

        rSMaterialButtonRectangle9.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle9.setBorder(null);
        rSMaterialButtonRectangle9.setText("BOOK / RENT");
        rSMaterialButtonRectangle9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle9MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle9ActionPerformed(evt);
            }
        });
        jPanel20.add(rSMaterialButtonRectangle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField13.setBackground(new java.awt.Color(27, 26, 26));
        jTextField13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(255, 255, 255));
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField13.setText("₱6000 DAILY");
        jTextField13.setBorder(null);
        jTextField13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField13FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField13FocusLost(evt);
            }
        });
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jPanel20.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHEVROLET_IMAGE/pngwing.com (6).png"))); // NOI18N
        jPanel20.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 180));

        jLabel15.setBackground(new java.awt.Color(26, 27, 27));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(26, 27, 27));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("CHEV 4456");
        jPanel20.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel20.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel174.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel20.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel20.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel175.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel20.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 40, 60));

        jLabel176.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel20.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel20.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel20.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI4.setForeground(new java.awt.Color(255, 255, 255));
        SEMI4.setText("SEMI-AUTO ");
        jPanel20.add(SEMI4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel20);

        jPanel22.setBackground(new java.awt.Color(26, 27, 27));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("+");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 270, 230));

        jLabel04.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-petrol-30.png")); // NOI18N
        jPanel22.add(jLabel04, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jTextField107.setBackground(new java.awt.Color(26, 27, 27));
        jTextField107.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField107.setForeground(new java.awt.Color(255, 255, 255));
        jTextField107.setBorder(null);
        jPanel22.add(jTextField107, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, 30));

        jTextField90.setBackground(new java.awt.Color(26, 27, 27));
        jTextField90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField90.setForeground(new java.awt.Color(255, 255, 255));
        jTextField90.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField90.setBorder(null);
        jPanel22.add(jTextField90, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        jTextField93.setBackground(new java.awt.Color(26, 27, 27));
        jTextField93.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField93.setForeground(new java.awt.Color(255, 255, 255));
        jTextField93.setBorder(null);
        jPanel22.add(jTextField93, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jLabel111.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-date-30.png")); // NOI18N
        jPanel22.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jTextField106.setBackground(new java.awt.Color(26, 27, 27));
        jTextField106.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField106.setForeground(new java.awt.Color(255, 255, 255));
        jTextField106.setBorder(null);
        jPanel22.add(jTextField106, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 70, 30));

        jTextField64.setBackground(new java.awt.Color(26, 27, 27));
        jTextField64.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField64.setForeground(new java.awt.Color(255, 255, 255));
        jTextField64.setBorder(null);
        jPanel22.add(jTextField64, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        rSMaterialButtonRectangle11.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle11.setBorder(null);
        rSMaterialButtonRectangle11.setText("BOOK / RENT");
        rSMaterialButtonRectangle11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle11MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle11ActionPerformed(evt);
            }
        });
        jPanel22.add(rSMaterialButtonRectangle11, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField15.setBackground(new java.awt.Color(27, 26, 26));
        jTextField15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField15.setBorder(null);
        jTextField15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField15FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField15FocusLost(evt);
            }
        });
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jPanel22.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel22.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 170));

        jLabel21.setBackground(new java.awt.Color(26, 27, 27));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(26, 27, 27));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("CHEV 4456");
        jPanel22.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel39.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-star-48 (1).png")); // NOI18N
        jPanel22.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel112.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-car-seat-30.png")); // NOI18N
        jPanel22.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 60, 60));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 255, 51));
        jLabel41.setText(".");
        jPanel22.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField46.setBackground(new java.awt.Color(26, 27, 27));
        jTextField46.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField46.setForeground(new java.awt.Color(255, 255, 255));
        jTextField46.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField46.setBorder(null);
        jPanel22.add(jTextField46, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, 20));

        jLabel113.setIcon(new javax.swing.ImageIcon("C:\\Users\\artoc\\OneDrive\\Desktop\\CARSZ\\icons8-suv-30.png")); // NOI18N
        jPanel22.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel22.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel22.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel22.add(SEMI8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel22);

        jPanel21.setBackground(new java.awt.Color(26, 27, 27));
        jPanel21.setPreferredSize(new java.awt.Dimension(320, 330));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("+");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel21.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 270, 230));

        jTextField88.setBackground(new java.awt.Color(26, 27, 27));
        jTextField88.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField88.setForeground(new java.awt.Color(255, 255, 255));
        jTextField88.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField88.setBorder(null);
        jPanel21.add(jTextField88, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        rSMaterialButtonRectangle10.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle10.setBorder(null);
        rSMaterialButtonRectangle10.setText("BOOK / RENT");
        rSMaterialButtonRectangle10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle10MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle10ActionPerformed(evt);
            }
        });
        jPanel21.add(rSMaterialButtonRectangle10, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField14.setBackground(new java.awt.Color(27, 26, 26));
        jTextField14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jTextField14.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField14.setBorder(null);
        jTextField14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField14FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField14FocusLost(evt);
            }
        });
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel21.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 170));

        jLabel20.setBackground(new java.awt.Color(26, 27, 27));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(26, 27, 27));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("CHEV 4456");
        jPanel21.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 255, 51));
        jLabel43.setText(".");
        jPanel21.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel21.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel21.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel145.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel21.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jTextField68.setBackground(new java.awt.Color(26, 27, 27));
        jTextField68.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField68.setForeground(new java.awt.Color(255, 255, 255));
        jTextField68.setBorder(null);
        jTextField68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField68ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField68, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField94.setBackground(new java.awt.Color(26, 27, 27));
        jTextField94.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField94.setForeground(new java.awt.Color(255, 255, 255));
        jTextField94.setBorder(null);
        jPanel21.add(jTextField94, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jLabel115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel21.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jTextField47.setBackground(new java.awt.Color(26, 27, 27));
        jTextField47.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField47.setForeground(new java.awt.Color(255, 255, 255));
        jTextField47.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField47.setBorder(null);
        jPanel21.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, 20));

        jTextField115.setBackground(new java.awt.Color(26, 27, 27));
        jTextField115.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField115.setForeground(new java.awt.Color(255, 255, 255));
        jTextField115.setBorder(null);
        jPanel21.add(jTextField115, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, 30));

        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel21.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 60, 60));

        jTextField116.setBackground(new java.awt.Color(26, 27, 27));
        jTextField116.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField116.setForeground(new java.awt.Color(255, 255, 255));
        jTextField116.setBorder(null);
        jPanel21.add(jTextField116, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 70, 30));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel21.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel21.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI9.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel21.add(SEMI9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel21);

        jPanel23.setBackground(new java.awt.Color(26, 27, 27));
        jPanel23.setPreferredSize(new java.awt.Dimension(320, 330));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("+");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel23.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 270, 230));

        jTextField97.setBackground(new java.awt.Color(26, 27, 27));
        jTextField97.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField97.setForeground(new java.awt.Color(255, 255, 255));
        jTextField97.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField97.setBorder(null);
        jPanel23.add(jTextField97, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        rSMaterialButtonRectangle12.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle12.setBorder(null);
        rSMaterialButtonRectangle12.setText("BOOK / RENT");
        rSMaterialButtonRectangle12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle12MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle12ActionPerformed(evt);
            }
        });
        jPanel23.add(rSMaterialButtonRectangle12, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jTextField16.setBackground(new java.awt.Color(27, 26, 26));
        jTextField16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField16.setBorder(null);
        jTextField16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField16FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField16FocusLost(evt);
            }
        });
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jPanel23.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel23.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 170));

        jLabel35.setBackground(new java.awt.Color(26, 27, 27));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(26, 27, 27));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("CHEV 4456");
        jPanel23.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel23.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel23.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 255, 51));
        jLabel45.setText(".");
        jPanel23.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel23.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel23.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 60, 60));

        jLabel148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel23.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 30, 50));

        jTextField48.setBackground(new java.awt.Color(26, 27, 27));
        jTextField48.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField48.setForeground(new java.awt.Color(255, 255, 255));
        jTextField48.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField48.setBorder(null);
        jPanel23.add(jTextField48, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, 20));

        jTextField69.setBackground(new java.awt.Color(26, 27, 27));
        jTextField69.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField69.setForeground(new java.awt.Color(255, 255, 255));
        jTextField69.setBorder(null);
        jPanel23.add(jTextField69, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField117.setBackground(new java.awt.Color(26, 27, 27));
        jTextField117.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField117.setForeground(new java.awt.Color(255, 255, 255));
        jTextField117.setBorder(null);
        jPanel23.add(jTextField117, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 70, 30));

        jTextField104.setBackground(new java.awt.Color(26, 27, 27));
        jTextField104.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField104.setForeground(new java.awt.Color(255, 255, 255));
        jTextField104.setBorder(null);
        jPanel23.add(jTextField104, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField118.setBackground(new java.awt.Color(26, 27, 27));
        jTextField118.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField118.setForeground(new java.awt.Color(255, 255, 255));
        jTextField118.setBorder(null);
        jPanel23.add(jTextField118, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, 30));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel23.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel23.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI10.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(SEMI10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel23);

        jPanel24.setBackground(new java.awt.Color(26, 27, 27));
        jPanel24.setPreferredSize(new java.awt.Dimension(320, 330));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("+");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel24.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 270, 230));

        jTextField110.setBackground(new java.awt.Color(26, 27, 27));
        jTextField110.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField110.setForeground(new java.awt.Color(255, 255, 255));
        jTextField110.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField110.setBorder(null);
        jPanel24.add(jTextField110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 30));

        rSMaterialButtonRectangle13.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle13.setBorder(null);
        rSMaterialButtonRectangle13.setText("BOOK / RENT");
        rSMaterialButtonRectangle13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle13MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle13ActionPerformed(evt);
            }
        });
        jPanel24.add(rSMaterialButtonRectangle13, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 290, 120, 40));

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel24.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 170));

        jLabel36.setBackground(new java.awt.Color(26, 27, 27));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(26, 27, 27));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("CHEV 4456");
        jPanel24.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jTextField17.setBackground(new java.awt.Color(27, 26, 26));
        jTextField17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField17.setBorder(null);
        jTextField17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField17FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField17FocusLost(evt);
            }
        });
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jPanel24.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-suv-30.png"))); // NOI18N
        jPanel24.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 250, 30, 40));

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-date-30.png"))); // NOI18N
        jPanel24.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, 30, 40));

        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-star-48 (1).png"))); // NOI18N
        jPanel24.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 200, 40, 50));

        jLabel97.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-car-seat-30.png"))); // NOI18N
        jPanel24.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 190, 50, 60));

        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-petrol-30.png"))); // NOI18N
        jPanel24.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 50, 50));

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 255, 51));
        jLabel81.setText(".");
        jPanel24.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, -45, 30, 70));

        jTextField72.setBackground(new java.awt.Color(26, 27, 27));
        jTextField72.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField72.setForeground(new java.awt.Color(255, 255, 255));
        jTextField72.setBorder(null);
        jPanel24.add(jTextField72, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 206, 30, 40));

        jTextField49.setBackground(new java.awt.Color(26, 27, 27));
        jTextField49.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField49.setForeground(new java.awt.Color(255, 255, 255));
        jTextField49.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField49.setBorder(null);
        jPanel24.add(jTextField49, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, 20));

        jTextField119.setBackground(new java.awt.Color(26, 27, 27));
        jTextField119.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField119.setForeground(new java.awt.Color(255, 255, 255));
        jTextField119.setBorder(null);
        jPanel24.add(jTextField119, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 210, 70, 30));

        jTextField105.setBackground(new java.awt.Color(26, 27, 27));
        jTextField105.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField105.setForeground(new java.awt.Color(255, 255, 255));
        jTextField105.setBorder(null);
        jPanel24.add(jTextField105, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 30, 30));

        jTextField120.setBackground(new java.awt.Color(26, 27, 27));
        jTextField120.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTextField120.setForeground(new java.awt.Color(255, 255, 255));
        jTextField120.setBorder(null);
        jPanel24.add(jTextField120, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, 30));

        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-50.png"))); // NOI18N
        jPanel24.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 50, 50));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-gear-stick-30.png"))); // NOI18N
        jPanel24.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 250, 40, 40));

        SEMI11.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        SEMI11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel24.add(SEMI11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 40));

        jPanel3.add(jPanel24);

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 1030));

        jScrollPane1.setViewportView(jPanel4);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1340, 630));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zicons/icons8-back-button-45.png"))); // NOI18N
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 10, 50, 50));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("RENTED/BOOKED");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 100, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 153, 0));
        jLabel14.setText(".");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, -10, 70, 70));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 255, 51));
        jLabel24.setText(".");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, -10, 70, 70));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("AVAILABLE");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 100, 40));

        rSMaterialButtonRectangle15.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle15.setBorder(null);
        rSMaterialButtonRectangle15.setText("RESTORE");
        rSMaterialButtonRectangle15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle15ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 20, 120, 40));

        rSMaterialButtonRectangle16.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle16.setBorder(null);
        rSMaterialButtonRectangle16.setText("DELETE");
        rSMaterialButtonRectangle16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rSMaterialButtonRectangle16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle16ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, 120, 40));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(204, 0, 0));
        jLabel53.setText(".");
        jPanel1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 70, 50));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("IN SERVICE / REPAIR");
        jPanel1.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 120, 60));

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CAR_LOGO/icons8-chevrolet-100.png"))); // NOI18N
        jLabel75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel75MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 6, 90, 65));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("CHEVROLET");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 16, 110, 40));

        jComboBox2.setBackground(new java.awt.Color(27, 26, 26));
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "SEMI-AUTO", "MANUAL", "AUTOMATIC" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 25, 130, 30));

        jComboBox3.setBackground(new java.awt.Color(27, 26, 26));
        jComboBox3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAR BODY TYPES", "CONVERTIBLES", "COUPE", "CROSSOVER", "HATCHBACK", "LUXURY", "MINIVAN", "MPV", "PICKUP", "SEDAN", "STATIONWAGON", "SUV", "VAN" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 25, 160, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1359, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField95ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField95ActionPerformed

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked

    }//GEN-LAST:event_jLabel19MouseClicked

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed
        // TODO add your handling code here:
    try {
        String carName = jTextField38.getText().trim();

        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Icon icon = jLabel38.getIcon();
        AppSession.setTransferIcon(icon);

        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField38.getText(), jTextField43.getText(), jLabel12.getText(), "",
                jTextField39.getText(), jTextField36.getText(), jTextField40.getText(),
                jTextField41.getText(), jTextField7.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField38.getText());
            info.setcartype(jTextField43.getText());
            info.setrates(jTextField39.getText());
            info.setmodel(jTextField36.getText());
            info.setseats(jTextField40.getText());
            info.setdiesel(jTextField41.getText());
            info.setdaily(jTextField7.getText());
            info.setcarPlate(jLabel12.getText());
            info.settransmission_type(SEMI3.getText());
        }

        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed

    private void rSMaterialButtonRectangle6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle6ActionPerformed
        // TODO add your handling code here:
    try {
        String carName = jTextField54.getText().trim();

        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Icon icon = jLabel58.getIcon();
        AppSession.setTransferIcon(icon);

        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField54.getText(), jTextField59.getText(), jLabel10.getText(), "",
                jTextField55.getText(), jTextField45.getText(), jTextField96.getText(),
                jTextField95.getText(), jTextField8.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField54.getText());
            info.setcartype(jTextField59.getText());
            info.setrates(jTextField55.getText());
            info.setmodel(jTextField45.getText());
            info.setseats(jTextField96.getText());
            info.setdiesel(jTextField95.getText());
            info.setdaily(jTextField8.getText());
            info.setcarPlate(jLabel10.getText());
            info.settransmission_type(SEMI7.getText());
        }

        BOOKCAR bookcar = new BOOKCAR();
        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() { return true; }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> dialog.closePopup()),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_rSMaterialButtonRectangle6ActionPerformed

    private void rSMaterialButtonRectangle8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle8ActionPerformed
        // TODO add your handling code here:
    try {
        String carName = jTextField21.getText().trim();

        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Icon icon = jLabel26.getIcon();
        AppSession.setTransferIcon(icon);

        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField21.getText(), jTextField28.getText(), jLabel13.getText(), "",
                jTextField22.getText(), jTextField56.getText(), jTextField58.getText(),
                jTextField98.getText(), jTextField9.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField21.getText());
            info.setcartype(jTextField28.getText());
            info.setrates(jTextField22.getText());
            info.setmodel(jTextField56.getText());
            info.setseats(jTextField58.getText());
            info.setdiesel(jTextField98.getText());
            info.setdaily(jTextField9.getText());
            info.setcarPlate(jLabel13.getText());
            info.settransmission_type(SEMI2.getText());
        }

        BOOKCAR bookcar = new BOOKCAR();
        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() { return true; }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> dialog.closePopup()),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    }//GEN-LAST:event_rSMaterialButtonRectangle8ActionPerformed

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6FocusLost

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7FocusGained

    private void jTextField7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        // TODO add your handling code here:
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

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9FocusLost

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9FocusGained

    private void rSMaterialButtonRectangle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle3MouseClicked
    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        // TODO add your handling code here:
    try {
        // Step 1: Get the car name
        String carName = jTextField62.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent popup from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel66.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField62.getText(), jTextField67.getText(), jLabel18.getText(), "",
                jTextField63.getText(), jTextField37.getText(), jTextField73.getText(),
                jTextField80.getText(), jTextField10.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField62.getText());
            info.setcartype(jTextField67.getText());
            info.setrates(jTextField63.getText());
            info.setmodel(jTextField37.getText());
            info.setseats(jTextField73.getText());
            info.setdiesel(jTextField80.getText());
            info.setdaily(jTextField10.getText());
            info.setcarPlate(jLabel18.getText());
            info.settransmission_type(SEMI6.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void rSMaterialButtonRectangle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle5MouseClicked

    private void rSMaterialButtonRectangle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5ActionPerformed
        // TODO add your handling code here:

    try {
        // Step 1: Get the car name
        String carName = jTextField70.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel74.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField70.getText(), jTextField75.getText(), jLabel17.getText(), "",
                jTextField71.getText(), jTextField42.getText(), jTextField74.getText(),
                jTextField81.getText(), jTextField11.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField70.getText());
            info.setcartype(jTextField75.getText());
            info.setrates(jTextField71.getText());
            info.setmodel(jTextField42.getText());
            info.setseats(jTextField74.getText());
            info.setdiesel(jTextField81.getText());
            info.setdaily(jTextField11.getText());
            info.setcarPlate(jLabel17.getText());
            info.settransmission_type(SEMI1.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_rSMaterialButtonRectangle5ActionPerformed

    private void rSMaterialButtonRectangle7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle7MouseClicked

    private void rSMaterialButtonRectangle7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle7ActionPerformed
        // TODO add your handling code here:
    try {
        // Step 1: Get the car name
        String carName = jTextField78.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel82.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField78.getText(), jTextField83.getText(), jLabel16.getText(), "",
                jTextField79.getText(), jTextField44.getText(), jTextField60.getText(),
                jTextField100.getText(), jTextField12.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField78.getText());
            info.setcartype(jTextField83.getText());
            info.setrates(jTextField79.getText());
            info.setmodel(jTextField44.getText());
            info.setseats(jTextField60.getText());
            info.setdiesel(jTextField100.getText());
            info.setdaily(jTextField12.getText());
            info.setcarPlate(jLabel16.getText());
            info.settransmission_type(SEMI5.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle7ActionPerformed

    private void rSMaterialButtonRectangle9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle9MouseClicked

    private void rSMaterialButtonRectangle9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle9ActionPerformed

    try {
        // Step 1: Get the car name
        String carName = jTextField86.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel90.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField86.getText(), jTextField91.getText(), jLabel15.getText(), "",
                jTextField87.getText(), jTextField57.getText(), jTextField103.getText(),
                jTextField102.getText(), jTextField13.getText(),   // ✅ fixed (you used jTextField9 before)
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField86.getText());
            info.setcartype(jTextField91.getText());
            info.setrates(jTextField87.getText());
            info.setmodel(jTextField57.getText());
            info.setseats(jTextField103.getText());
            info.setdiesel(jTextField102.getText());
            info.setdaily(jTextField13.getText());          // ✅ keep consistent
            info.setcarPlate(jLabel15.getText());
            info.settransmission_type(SEMI4.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
     
    }//GEN-LAST:event_rSMaterialButtonRectangle9ActionPerformed

    private void jTextField10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10FocusGained

    private void jTextField10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10FocusLost

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusGained

    private void jTextField11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusLost

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusGained

    private void jTextField12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusLost

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField13FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13FocusGained

    private void jTextField13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField13FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13FocusLost

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void rSMaterialButtonRectangle10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle10MouseClicked

    private void rSMaterialButtonRectangle10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle10ActionPerformed
        // TODO add your handling code here:
            try {
        // Step 1: Get the car name
        String carName = jTextField88.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel109.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField88.getText(), jTextField47.getText(), jLabel20.getText(), "",
                jTextField94.getText(), jTextField68.getText(), jTextField115.getText(),
                jTextField116.getText(), jTextField14.getText(),
                null, null, null, null, null, null, null, null, SEMI9.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField88.getText());
            info.setcartype(jTextField47.getText());
            info.setrates(jTextField94.getText());
            info.setmodel(jTextField68.getText());
            info.setseats(jTextField115.getText());
            info.setdiesel(jTextField116.getText());
            info.setdaily(jTextField14.getText());
            info.setcarPlate(jLabel20.getText());
            info.settransmission_type(SEMI9.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle10ActionPerformed

    private void jTextField14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14FocusGained

    private void jTextField14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14FocusLost

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField15FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15FocusGained

    private void jTextField15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField15FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15FocusLost

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void rSMaterialButtonRectangle12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle12MouseClicked

    private void rSMaterialButtonRectangle12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle12ActionPerformed
        // TODO add your handling code here:
        try {
        // Step 1: Get the car name
        String carName = jTextField97.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel125.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField97.getText(), jTextField48.getText(), jLabel35.getText(), "",
                jTextField104.getText(), jTextField69.getText(), jTextField118.getText(),
                jTextField117.getText(), jTextField16.getText(),
                null, null, null, null, null, null, null, null, SEMI10.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField97.getText());
            info.setcartype(jTextField48.getText());
            info.setrates(jTextField104.getText());
            info.setmodel(jTextField69.getText());
            info.setseats(jTextField118.getText());
            info.setdiesel(jTextField117.getText());
            info.setdaily(jTextField16.getText());
            info.setcarPlate(jLabel35.getText());
            info.settransmission_type(SEMI10.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle12ActionPerformed

    private void jTextField16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField16FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16FocusGained

    private void jTextField16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField16FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16FocusLost

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed

        editEnabled = true;
        JOptionPane.showMessageDialog(this, "DELETE PANEL MODE ENABLED. Click a panel to delete.");
    }//GEN-LAST:event_buttonAction4ActionPerformed

    private void buttonAction5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction5ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_buttonAction5ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

    ADD sched = new ADD();
    sched.setCarBrand("CHEVROLET");
    String[] actions = { "CLOSE", "SAVE" };

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            if (index == 0) {
                dialog.closePopup();
                return;
            } 
//ADD
else if (index == 1) {
    try {
        AddedCarInventorySync.saveAddedCarToBothTables(sched);

        JOptionPane.showMessageDialog(null, "SUCCESS!");

        loadCarDataFromDatabase1();
        CarRepairStatusMarker.refreshOpenBrandFrames();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving car: " + e.getMessage());
    }

    dialog.closePopup();
}
        }),
        option
    );
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
    ADD sched = new ADD();
    sched.setCarBrand("CHEVROLET");
    String[] actions = { "CLOSE", "SAVE" };

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            if (index == 0) {
                dialog.closePopup();
                return;
            } 
            
//ADD
else if (index == 1) {
    try {
        AddedCarInventorySync.saveAddedCarToBothTables(sched);

        JOptionPane.showMessageDialog(null, "SUCCESS!");

        loadCarDataFromDatabase2();
        CarRepairStatusMarker.refreshOpenBrandFrames();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving car: " + e.getMessage());
    }

    dialog.closePopup();
}
            
        }),
        option
    );

    }//GEN-LAST:event_jLabel3MouseClicked

    private void rSMaterialButtonRectangle14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle14MouseClicked

    private void rSMaterialButtonRectangle14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle14ActionPerformed
        // TODO add your handling code here:
        
        
    try {
        // Step 1: Get the car name
        String carName = jTextField30.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel32.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField30.getText(), jTextField35.getText(), jLabel9.getText(), "",
                jTextField34.getText(), jTextField31.getText(), jTextField32.getText(),
                jTextField33.getText(), jTextField6.getText(),
                null, null, null, null, null, null, null, null, SEMI.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField30.getText());
            info.setcartype(jTextField35.getText());
            info.setrates(jTextField34.getText());
            info.setmodel(jTextField31.getText());
            info.setseats(jTextField32.getText());
            info.setdiesel(jTextField33.getText());
            info.setdaily(jTextField6.getText());
            info.setcarPlate(jLabel9.getText());
            info.settransmission_type(SEMI.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle14ActionPerformed

    private void rSMaterialButtonRectangle11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle11ActionPerformed
        // TODO add your handling code here:
    try {
        // Step 1: Get the car name
        String carName = jTextField90.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel117.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField90.getText(), jTextField46.getText(), jLabel21.getText(), "",
                jTextField93.getText(), jTextField64.getText(), jTextField107.getText(),
                jTextField106.getText(), jTextField15.getText(),
                null, null, null, null, null, null, null, null, SEMI8.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField90.getText());
            info.setcartype(jTextField46.getText());
            info.setrates(jTextField93.getText());
            info.setmodel(jTextField64.getText());
            info.setseats(jTextField107.getText());
            info.setdiesel(jTextField106.getText());
            info.setdaily(jTextField15.getText());
            info.setcarPlate(jLabel21.getText());
            info.settransmission_type(SEMI8.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle11ActionPerformed

    private void rSMaterialButtonRectangle11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle11MouseClicked

    private void buttonAction6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAction6ActionPerformed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
    ADD sched = new ADD();
    sched.setCarBrand("CHEVROLET");
    String[] actions = { "CLOSE", "SAVE" };

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            if (index == 0) {
                dialog.closePopup();
                return;
            } 
            
            //ADD
 else if (index == 1) {
    try {
        AddedCarInventorySync.saveAddedCarToBothTables(sched);

        JOptionPane.showMessageDialog(null, "SUCCESS!");

        loadCarDataFromDatabase();
        CarRepairStatusMarker.refreshOpenBrandFrames();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving car: " + e.getMessage());
    }

    dialog.closePopup();
}
            
        }),
        option
    );
    }//GEN-LAST:event_jLabel1MouseClicked

    private void rSMaterialButtonRectangle13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle13ActionPerformed
        // TODO add your handling code here:
        try {
        // Step 1: Get the car name
        String carName = jTextField110.getText().trim();

        // Step 2: Status-aware check (blocks only if RENTED/BOOKED)
        ServiceCustomer service = new ServiceCustomer();
        String activeStatus = service.getActiveStatusByCarName(carName);

        if (activeStatus != null) {
            JOptionPane.showMessageDialog(
                this,
                "THIS UNIT IS CURRENTLY " + activeStatus + ". PLEASE CHOOSE ANOTHER CAR.",
                "NOT AVAILABLE",
                JOptionPane.WARNING_MESSAGE
            );
            return; // 🔴 Prevent PanelSlide from opening
        }

        // Step 4: Transfer the icon
        Icon icon = jLabel133.getIcon();
        AppSession.setTransferIcon(icon);

        // Step 5: Transfer customer info
        CustomerInfo info = AppSession.getCustomerInfo();
        if (info == null) {
            info = new CustomerInfo(
                "", "", "", "", jTextField110.getText(), jTextField49.getText(), jLabel36.getText(), "",
                jTextField105.getText(), jTextField72.getText(), jTextField120.getText(),
                jTextField119.getText(), jTextField17.getText(),
                null, null, null, null, null, null, null, null, SEMI11.getText()
            );
            AppSession.setCustomerInfo(info);
        } else {
            info.setcarname(jTextField110.getText());
            info.setcartype(jTextField49.getText());
            info.setrates(jTextField105.getText());
            info.setmodel(jTextField72.getText());
            info.setseats(jTextField120.getText());
            info.setdiesel(jTextField119.getText());
            info.setdaily(jTextField17.getText());
            info.setcarPlate(jLabel36.getText());
            info.settransmission_type(SEMI11.getText());
        }

        // Step 6: Open the BOOKCAR → PanelSlide
        BOOKCAR bookcar = new BOOKCAR();

        String[] actions = new String[]{"CLOSE"};
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };

        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "BOOK/RENT THIS CAR", actions, (dialog, index) -> {
                dialog.closePopup();
            }),
            option
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                                      "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle13ActionPerformed

    private void rSMaterialButtonRectangle13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle13MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
    ADD sched = new ADD();
    sched.setCarBrand("CHEVROLET");
    String[] actions = { "CLOSE", "SAVE" };

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(
        new SimplePopupBorder(sched, "", actions, (dialog, index) -> {
            if (index == 0) {
                dialog.closePopup();
                return;
            } 

//ADD
else if (index == 1) {
    try {
        AddedCarInventorySync.saveAddedCarToBothTables(sched);

        JOptionPane.showMessageDialog(null, "SUCCESS!");

        loadCarDataFromDatabase3();
        CarRepairStatusMarker.refreshOpenBrandFrames();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving car: " + e.getMessage());
    }

    dialog.closePopup();
}
            
            
        }),
        option
    );
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jTextField17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField17FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17FocusGained

    private void jTextField17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField17FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17FocusLost

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void rSMaterialButtonRectangle15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle15ActionPerformed
        if (rentalAgentMode) return;
        // TODO add your handling code here:
       
            // load added-deleted cars from DB
    List<String> addedDeleted = loadDeletedAddedCarNamesFromDB();

    if (deletedPanels.isEmpty() && addedDeleted.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No deleted cars to restore.");
        return;
    }

    DefaultListModel<String> model = new DefaultListModel<>();
    List<Object> targets = new ArrayList<>();

    // 1) HARD-CODED deleted panels (existing behavior)
    for (JPanel p : deletedPanels) {
        model.addElement("[PANEL] " + getDeletedDisplayName(p));
        targets.add(p);
    }

    // 2) NEW-ADDED deleted cars (car_added) from DB
    for (String name : addedDeleted) {
        model.addElement("[ADDED] " + name);
        targets.add(name); // store name, restore by DB
    }

    JList<String> list = new JList<>(model);
    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.setVisibleRowCount(Math.min(12, model.size()));

    int result = JOptionPane.showConfirmDialog(
            this,
            new JScrollPane(list),
            "Select item(s) to restore",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );
    if (result != JOptionPane.OK_OPTION) return;

    int[] selected = list.getSelectedIndices();
    if (selected.length == 0) return;

    // split restore targets
    List<JPanel> panelsToRestore = new ArrayList<>();
    List<String> addedCarsToRestore = new ArrayList<>();

    for (int idx : selected) {
        Object t = targets.get(idx);
        if (t instanceof JPanel) panelsToRestore.add((JPanel) t);
        else if (t instanceof String) addedCarsToRestore.add((String) t);
    }

    // ✅ restore panels in original order (existing)
    panelsToRestore.sort((a, b) -> Integer.compare(
            originalIndexMap.getOrDefault(a, Integer.MAX_VALUE),
            originalIndexMap.getOrDefault(b, Integer.MAX_VALUE)
    ));

    int restoredPanels = 0;
    for (JPanel p : panelsToRestore) {
        boolean addedInventoryCar = p == jPanel22 || p == jPanel21 || p == jPanel23 || p == jPanel24;
        if (!InventoryDeleteRestoreSync.restoreCar(
                this, getPlateNumberFromPanel(p), "CHEVROLET", addedInventoryCar)) {
            continue;
        }
        restoreOnePanel(p);
        removeDeletedCarTracking(p);  // removes from cars_deleted DB
        removeFromRestoreLists(p);    // removes from restore list memory
        restoredPanels++;
    }

    // ✅ restore added cars back into car_added from cars_deleted
    int restoredAdded = 0;
    for (String name : addedCarsToRestore) {
        try {
            if (!InventoryDeleteRestoreSync.restoreAddedCarByName(this, name, "CHEVROLET")) {
                continue;
            }
            restoredAdded++;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to restore: " + name + "\n" + ex.getMessage(),
                    "RESTORE ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // refresh the 4 slots after restore
    if (restoredAdded > 0) {
        loadCarDataFromDatabase();
        loadCarDataFromDatabase1();
        loadCarDataFromDatabase2();
        loadCarDataFromDatabase3();
        refreshBMWColors();
    }

    JOptionPane.showMessageDialog(this,
            "Restored Panels: " + restoredPanels + "\nRestored Added Cars: " + restoredAdded);
    }//GEN-LAST:event_rSMaterialButtonRectangle15ActionPerformed

    private void rSMaterialButtonRectangle16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle16ActionPerformed
        if (rentalAgentMode) return;
        // TODO add your handling code here:
                editEnabled = true;
        JOptionPane.showMessageDialog(this, "DELETE PANEL MODE ENABLED. Click a panel to delete.");
    }//GEN-LAST:event_rSMaterialButtonRectangle16ActionPerformed

    private void jTextField68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField68ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField68ActionPerformed

    private void jLabel75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel75MouseClicked
        // TODO add your handling code here:
        refreshMitsubishiPage();
    }//GEN-LAST:event_jLabel75MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        handleBodyTypeSelection();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    //saveCarInfoToDatabase();
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BMW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BMW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BMW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BMW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

    FlatRobotoFont.install();
    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
    FlatMacDarkLaf.setup(); // Change to the dark theme
    FlatLaf.registerCustomDefaultsSource("sample.theme");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new CHEVROLET().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SEMI;
    private javax.swing.JLabel SEMI1;
    private javax.swing.JLabel SEMI10;
    private javax.swing.JLabel SEMI11;
    private javax.swing.JLabel SEMI2;
    private javax.swing.JLabel SEMI3;
    private javax.swing.JLabel SEMI4;
    private javax.swing.JLabel SEMI5;
    private javax.swing.JLabel SEMI6;
    private javax.swing.JLabel SEMI7;
    private javax.swing.JLabel SEMI8;
    private javax.swing.JLabel SEMI9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel04;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField105;
    private javax.swing.JTextField jTextField106;
    private javax.swing.JTextField jTextField107;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField110;
    private javax.swing.JTextField jTextField115;
    private javax.swing.JTextField jTextField116;
    private javax.swing.JTextField jTextField117;
    private javax.swing.JTextField jTextField118;
    private javax.swing.JTextField jTextField119;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField120;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle10;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle11;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle12;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle13;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle14;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle15;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle16;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle5;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle6;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle7;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle8;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle9;
    // End of variables declaration//GEN-END:variables
}
