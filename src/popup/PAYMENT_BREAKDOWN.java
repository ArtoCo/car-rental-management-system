/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package popup;

import popup.AppSession2;
import customer.ServiceCustomer;
import customers.DatabaseConnection;
import customers.ModelCustomer1;
import jSCHED.ModelSched;
import jSCHED.ServiceSched;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import javax.swing.JOptionPane;
import popup.ROUNDPANEL;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.SwingUtilities;


/**
 *
 * @author ArtoCO
 */

public class PAYMENT_BREAKDOWN extends javax.swing.JPanel {
    
    private static final java.text.DecimalFormat PESO_FORMAT =
        new java.text.DecimalFormat("₱ #,##0.00");

    
    private String phoneNumber; // store the phoneNO of the person being edited


    /**
     * Creates new form PAYMENT_BREAKDOWN
     */
    public PAYMENT_BREAKDOWN(int i) {
        initComponents();
        addRealTimeCalculation();
        addRealTimeCalculation1();// enable real-time calculation
        updateTotalAmount();
        addNumberFormatter(jTextField30);
        addNumberFormatter(jTextField38);
        addNumberFormatter(jTextField39);
        
        jTextField52.setText("₱ TEST");

              
        loadDailyPriceRealtime();
        
    // Add real-time VAT calculator to jTextField52
    jTextField52.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        calculateVAT();
    }
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        calculateVAT();
    }
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        calculateVAT();
    }
});
    
    javax.swing.JTextField[] monitoredFields = {
    jTextField52, jTextField30, jTextField38,
    jTextField39, jTextField40, jTextField41, jTextField42
};

    //GET TOTAL VALUE THEN REAL TIME UPDATE
for (javax.swing.JTextField field : monitoredFields) {
    field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            calculateGrandTotal();
        }
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            calculateGrandTotal();
        }
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            calculateGrandTotal();
        }
    });
}

    DocumentListener listener = new DocumentListener() {
    public void insertUpdate(DocumentEvent e) { updateTotalAmount(); }
    public void removeUpdate(DocumentEvent e) { updateTotalAmount(); }
    public void changedUpdate(DocumentEvent e) { updateTotalAmount(); }
    };

    jTextField30.getDocument().addDocumentListener(listener);
    jTextField38.getDocument().addDocumentListener(listener);
    jTextField39.getDocument().addDocumentListener(listener);


    }

    // Method to calculate and update the total
    private void calculateTotal() {
    try {
        String rateText = jTextField7.getText().replaceAll("[^\\d.]", "");
        String daysText = jTextField9.getText().replaceAll("[^\\d]", "");

        if (rateText.isEmpty() || daysText.isEmpty()) {
            jTextField52.setText("₱ 0.00");
            return;
        }

        double ratePerDay = Double.parseDouble(rateText);
        int numOfDays = Integer.parseInt(daysText);

        double total = ratePerDay * numOfDays;

        java.text.NumberFormat format =
            java.text.NumberFormat.getNumberInstance(java.util.Locale.US);

        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        // 🔒 FORCE PESO SIGN
        jTextField52.setText("₱ " + format.format(total));

    } catch (Exception e) {
        jTextField52.setText("₱ 0.00");
    }
}


    // Add document listeners to update total in real time
    private void addRealTimeCalculation() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotal();
            }
        };

        jTextField7.getDocument().addDocumentListener(listener);
        jTextField9.getDocument().addDocumentListener(listener);
    }
    
    
    // Method to calculate and update the total 2
    private void calculateTotal1() {
    try {
        // Remove non-numeric characters except dot (.)
        String rateText = jTextField10.getText().replaceAll("[^\\d.]", "");
        String daysText = jTextField24.getText().replaceAll("[^\\d]", "");

        if (rateText.isEmpty() || daysText.isEmpty()) {
            jTextField42.setText("₱ 0.00");
            return;
        }

        double ratePerDay = Double.parseDouble(rateText);
        int numOfDays = Integer.parseInt(daysText);

        double total = ratePerDay * numOfDays;

        // ✅ FORMAT NUMBER ONLY (NO CURRENCY INSTANCE)
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");

        // 🔒 FORCE PESO SIGN
        jTextField42.setText("₱ " + df.format(total));

    } catch (Exception e) {
        jTextField42.setText("₱ 0.00");
    }
}

    
    // Add document listeners to update total in real time
    private void addRealTimeCalculation1() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotal1();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotal1();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotal1();
            }
        };

        jTextField10.getDocument().addDocumentListener(listener);
        jTextField24.getDocument().addDocumentListener(listener);
    }

    private void transferAmountWithPeso() {
    try {
        // Remove non-numeric characters except dot (.)
        String raw = jTextField53.getText().replaceAll("[^\\d.]", "");

        if (raw.isEmpty()) {
            jTextField30.setText("₱ 0.00");
            return;
        }

        double amount = Double.parseDouble(raw);

        // Format with ₱ and comma separators
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("en", "PH"));
        jTextField30.setText(format.format(amount));

    } catch (NumberFormatException ex) {
        jTextField30.setText("₱ 0.00");
    }
    
}
private void transferAmountWithPeso1() {
        try {
        // Remove non-numeric characters except dot (.)
        String raw = jTextField54.getText().replaceAll("[^\\d.]", "");

        if (raw.isEmpty()) {
            jTextField38.setText("₱ 0.00");
            return;
        }

        double amount = Double.parseDouble(raw);

        // Format with ₱ and comma separators
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("en", "PH"));
        jTextField38.setText(format.format(amount));

    } catch (NumberFormatException ex) {
        jTextField38.setText("₱ 0.00");
    }
        
}
private void transferAmountWithPeso2() {
    try {
        // Remove non-numeric characters except dot (.)
        String raw = jTextField55.getText().replaceAll("[^\\d.]", "");

        if (raw.isEmpty()) {
            jTextField39.setText("₱ 0.00");
            return;
        }

        double amount = Double.parseDouble(raw);

        // Format with ₱ and comma separators
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("en", "PH"));
        jTextField39.setText(format.format(amount));

    } catch (NumberFormatException ex) {
        jTextField39.setText("₱ 0.00");
    }
    
}
//GET VAT %
private void calculateVAT() {
    try {
        // Get only numeric part of the input (remove ₱, commas, letters)
        String valueText = jTextField52.getText().replaceAll("[^\\d.]", "");

        if (valueText.isEmpty()) {
            jTextField41.setText("₱ 0.00");
            return;
        }

        double amount = Double.parseDouble(valueText);
        double vat = amount * 0.12;

        // ✅ FORMAT NUMBER ONLY
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");

        // 🔒 FORCE PESO SIGN
        jTextField41.setText("₱ " + df.format(vat));

    } catch (Exception ex) {
        jTextField41.setText("₱ 0.00");
    }
}

private void calculateGrandTotal() {
    double total = 0;

    // List of relevant fields
    javax.swing.JTextField[] fields = {
        jTextField52, jTextField30, jTextField38,
        jTextField39, jTextField40, jTextField41, jTextField42
    };

    // Loop and add numeric values
    for (javax.swing.JTextField field : fields) {
        try {
            String value = field.getText().replaceAll("[^\\d.]", ""); // remove ₱ and non-numeric
            if (!value.isEmpty()) {
                total += Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            // ignore invalid entries
        }
    }

    total -= 2000;

    // ✅ FORMAT NUMBER ONLY (NO CURRENCY INSTANCE)
    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");

    // Display in jTextField51
    jTextField51.setText("₱ " + df.format(total));

    // Calculate 50% and display in jTextField56
    double half = total * 0.5;
    jTextFieldZZ.setText("₱ " + df.format(half));
}


public void setPhoneNumber(String phone) {
    this.phoneNumber = phone;
}

//REAL TIME UPDATE
private void updateTotalAmount() {
    try {
        double val1 = parseAmount(jTextField30.getText());
        double val2 = parseAmount(jTextField38.getText());
        double val3 = parseAmount(jTextField39.getText());

        double total = val1 + val2 + val3;

        // Format safely with peso sign
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
        jTextField49.setText("₱ " + df.format(total));
    } catch (Exception e) {
        jTextField49.setText("₱ 0.00");
    }
}


private double parseAmount(String text) {
    // Remove PHP if present
    text = text.replace("PHP", "");

    // Remove all characters except digits and dot
    text = text.replaceAll("[^\\d.]", "");

    if (text.isEmpty()) return 0.0;

    return Double.parseDouble(text);
}


private void addNumberFormatter(javax.swing.JTextField textField) {
    NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
    format.setMinimumFractionDigits(2);
    format.setMaximumFractionDigits(2);

    textField.getDocument().addDocumentListener(new DocumentListener() {
        private void formatText() {
            SwingUtilities.invokeLater(() -> {
                String text = textField.getText().replaceAll(",", "").trim();
                if (text.isEmpty()) return;

                try {
                    // Parse and reformat the value
                    double value = Double.parseDouble(text);
                    String formatted = format.format(value);
                    textField.setText(formatted);
                } catch (NumberFormatException e) {
                    // Ignore while user is typing
                }
            });
        }

        @Override
        public void insertUpdate(DocumentEvent e) { formatText(); }
        @Override
        public void removeUpdate(DocumentEvent e) { formatText(); }
        @Override
        public void changedUpdate(DocumentEvent e) { formatText(); }
    });
}

private double parsePesoToDouble(String text) {
    if (text == null) return 0.0;
    text = text.replace("PHP", "")
               .replace("₱", "")
               .replace(",", "")
               .trim();
    if (text.isEmpty()) return 0.0;
    return Double.parseDouble(text);
}

private CustomerInfo requireCustomerInfoOrWarn() {
    CustomerInfo info = AppSession2.getCustomerInfo();
    if (info == null) {
        JOptionPane.showMessageDialog(this,
                "Customer session is missing. Please save customer first.",
                "Session Missing",
                JOptionPane.ERROR_MESSAGE);
        return null;
    }
    if (info.getPhoneNumber() == null || info.getPhoneNumber().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Phone number is missing in session.",
                "Session Missing",
                JOptionPane.ERROR_MESSAGE);
        return null;
    }
    return info;
}

private void updatePaymentInDatabase(String paymentType, double amount, int customerId, CustomerInfo info) throws Exception {
    ServiceCustomer service = new ServiceCustomer();
    service.updatePaymentByVerifiedCustomer(
            customerId,
            info.getFirstName(),
            info.getLastName(),
            info.getPhoneNumber(),
            paymentType,
            amount
    );
}

private void saveReceiptPaymentFieldsToSession(CustomerInfo info) {
    // These are what FULL_RECEIPT displays
    info.setRentPay(jTextField52.getText().trim());       // base rental fee total
    info.setRentInsurance(jTextField49.getText().trim()); // total insurance charges
    info.setVat(jTextField41.getText().trim());
    info.setDriver(jTextField42.getText().trim());
    info.setFullpay(jTextField51.getText().trim());
    info.setPartialpay(jTextFieldZZ.getText().trim());

    AppSession2.setCustomerInfo(info); // store back
}

/** One function for both FULL and PARTIAL payment buttons */
private void handlePayment(boolean fullPayment) {

    // 1) show popup (your existing UI)
    if (fullPayment) {
        PAYMENT1_1 bookcar = new PAYMENT1_1();
        String actions[] = new String[]{"",""};
        DefaultOption option = new DefaultOption() {
            @Override public boolean closeWhenClickOutside() { return true; }
        };
        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "PAYMENT METHOD", actions, (dialog, index) -> dialog.closePopup()),
            option
        );
    } else {
        PAYMENT_PARTIAL bookcar = new PAYMENT_PARTIAL();
        String actions[] = new String[]{"",""};
        DefaultOption option = new DefaultOption() {
            @Override public boolean closeWhenClickOutside() { return true; }
        };
        GlassPanePopup.showPopup(
            new SimplePopupBorder(bookcar, "PAYMENT METHOD", actions, (dialog, index) -> dialog.closePopup()),
            option
        );
    }

    // 2) session must exist
    CustomerInfo info = requireCustomerInfoOrWarn();
    if (info == null) return;

    Integer customerId = AppSession2.getCustomerId();
    if (customerId == null) {
        customerId = AppSession.getSelectedCustomerIdForBooking();
    }
    if (customerId == null) {
        customerId = AppSession.getCustomerId();
    }
    if (customerId == null) {
        JOptionPane.showMessageDialog(this, "Customer ID missing in session.", "Session Missing", JOptionPane.ERROR_MESSAGE);
        return;
    }
    AppSession2.setCustomerId(customerId);

    // 3) payment type + amount
    String paymentType = fullPayment ? jTextField57.getText().trim() : jTextFieldZ.getText().trim();
    String amountText  = fullPayment ? jTextField51.getText().trim() : jTextFieldZZ.getText().trim();

    try {
        double amount = parsePesoToDouble(amountText);

        // 4) update DB
        updatePaymentInDatabase(paymentType, amount, customerId, info);

        // 5) update receipt fields in CustomerInfo (session)
        saveReceiptPaymentFieldsToSession(info);

        JOptionPane.showMessageDialog(this, "Payment details updated successfully.");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid payment amount. Please enter a valid number.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error while updating payment: " + e.getMessage(),
                "System Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private void loadDailyPriceRealtime() {
    String daily = AppSession2.getDailyPrice();

    if (daily == null || daily.trim().isEmpty() || daily.equalsIgnoreCase("N/A")) {
        daily = "₱ 0.00";
    }

    jTextField7.setText(daily);
    calculateTotal();

    AppSession2.addDailyPriceListener(evt -> {
        javax.swing.SwingUtilities.invokeLater(() -> {
            String value = String.valueOf(evt.getNewValue());

            if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
                value = "₱ 0.00";
            }

            jTextField7.setText(value);
            calculateTotal();
        });
    });
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField34 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jPanel4 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField52 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField16 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel5 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField33 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jPanel6 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField35 = new javax.swing.JTextField();
        jPanel7 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextField45 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jPanel8 =  new ROUNDPANEL(20, new Color(0, 0, 0), 0);
        jTextFieldZZ = new javax.swing.JTextField();
        jTextFieldZ = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField57 = new javax.swing.JTextField();
        rSMaterialButtonRectangle4 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();

        setBackground(new java.awt.Color(27, 26, 26));
        setPreferredSize(new java.awt.Dimension(1063, 427));

        jPanel2.setBackground(new java.awt.Color(27, 26, 26));
        jPanel2.setPreferredSize(new java.awt.Dimension(1063, 423));

        jPanel3.setBackground(new java.awt.Color(27, 26, 26));

        jPanel1.setBackground(new java.awt.Color(27, 26, 26));
        jPanel1.setPreferredSize(new java.awt.Dimension(1063, 423));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField34.setBackground(new java.awt.Color(51, 51, 51));
        jTextField34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField34.setForeground(new java.awt.Color(255, 255, 255));
        jTextField34.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField34.setText("VAT (12%)");
        jTextField34.setBorder(null);
        jPanel1.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 79, -1));

        jTextField21.setBackground(new java.awt.Color(51, 51, 51));
        jTextField21.setForeground(new java.awt.Color(255, 255, 255));
        jTextField21.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField21.setText("Base pay only (Mandatory)");
        jTextField21.setBorder(null);
        jTextField21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField21FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField21FocusLost(evt);
            }
        });
        jTextField21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField21ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 308, 260, 22));

        jTextField10.setBackground(new java.awt.Color(51, 51, 51));
        jTextField10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 255, 255));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setText("₱300 DAILY");
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
        jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 378, 111, 28));

        jTextField23.setBackground(new java.awt.Color(51, 51, 51));
        jTextField23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField23.setForeground(new java.awt.Color(255, 255, 255));
        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23.setText("X");
        jTextField23.setBorder(null);
        jTextField23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField23FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField23FocusLost(evt);
            }
        });
        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 378, 20, 30));

        jTextField24.setBackground(new java.awt.Color(51, 51, 51));
        jTextField24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField24.setForeground(new java.awt.Color(255, 255, 255));
        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField24.setText("Number of Days");
        jTextField24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jTextField24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField24FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField24FocusLost(evt);
            }
        });
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 378, 111, 28));

        jTextField42.setBackground(new java.awt.Color(51, 51, 51));
        jTextField42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField42.setForeground(new java.awt.Color(255, 255, 255));
        jTextField42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField42.setBorder(null);
        jTextField42.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField42FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField42FocusLost(evt);
            }
        });
        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField42, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, 111, 28));

        jTextField22.setBackground(new java.awt.Color(51, 51, 51));
        jTextField22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField22.setForeground(new java.awt.Color(255, 255, 255));
        jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField22.setText("=");
        jTextField22.setBorder(null);
        jTextField22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField22FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField22FocusLost(evt);
            }
        });
        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 378, 20, 30));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField52.setBackground(new java.awt.Color(51, 51, 51));
        jTextField52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField52.setForeground(new java.awt.Color(255, 255, 255));
        jTextField52.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField52.setBorder(null);
        jTextField52.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField52FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField52FocusLost(evt);
            }
        });
        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 120, 28));

        jTextField17.setBackground(new java.awt.Color(51, 51, 51));
        jTextField17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField17.setText("=");
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
        jPanel4.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 20, -1));

        jTextField32.setEditable(false);
        jTextField32.setBackground(new java.awt.Color(51, 51, 51));
        jTextField32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField32.setForeground(new java.awt.Color(255, 255, 255));
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField32.setText("BASE RENTAL FEE");
        jTextField32.setBorder(null);
        jTextField32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField32ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 20));

        jTextField7.setBackground(new java.awt.Color(51, 51, 51));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setToolTipText("");
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
        jPanel4.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 111, 28));

        jTextField8.setBackground(new java.awt.Color(51, 51, 51));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("X");
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
        jPanel4.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 20, 30));

        jTextField9.setBackground(new java.awt.Color(51, 51, 51));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(255, 255, 255));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setText("Number of Days");
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
        jPanel4.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 111, 28));

        jTextField31.setBackground(new java.awt.Color(51, 51, 51));
        jTextField31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField31.setForeground(new java.awt.Color(255, 255, 255));
        jTextField31.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField31.setText("INSURANCE CHARGES (Optional)");
        jTextField31.setBorder(null);
        jPanel4.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 340, 24));

        jTextField13.setBackground(new java.awt.Color(51, 51, 51));
        jTextField13.setForeground(new java.awt.Color(255, 255, 255));
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField13.setText("Theft Protection");
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
        jPanel4.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 160, -1));

        jTextField12.setBackground(new java.awt.Color(51, 51, 51));
        jTextField12.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 255, 255));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField12.setText("Personal Accident Insurance (PAI)");
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
        jPanel4.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, -1, 20));

        jTextField11.setBackground(new java.awt.Color(51, 51, 51));
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField11.setText("Collision Damage Waiver (CDW)");
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
        jPanel4.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 190, 22));

        jTextField55.setBackground(new java.awt.Color(51, 51, 51));
        jTextField55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField55.setForeground(new java.awt.Color(255, 255, 255));
        jTextField55.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField55.setText("₱300 DAILY");
        jTextField55.setBorder(null);
        jTextField55.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField55FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField55FocusLost(evt);
            }
        });
        jTextField55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField55ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        jTextField54.setBackground(new java.awt.Color(51, 51, 51));
        jTextField54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField54.setForeground(new java.awt.Color(255, 255, 255));
        jTextField54.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField54.setText("₱500 DAILY");
        jTextField54.setBorder(null);
        jTextField54.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField54FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField54FocusLost(evt);
            }
        });
        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 136, -1, 20));

        jTextField53.setBackground(new java.awt.Color(51, 51, 51));
        jTextField53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField53.setForeground(new java.awt.Color(255, 255, 255));
        jTextField53.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField53.setText("₱500 DAILY");
        jTextField53.setBorder(null);
        jTextField53.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField53FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField53FocusLost(evt);
            }
        });
        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField53, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 69, 22));

        jTextField56.setBackground(new java.awt.Color(51, 51, 51));
        jTextField56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField56.setForeground(new java.awt.Color(255, 255, 255));
        jTextField56.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField56.setText("TOTAL CHARGES");
        jTextField56.setBorder(null);
        jTextField56.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField56FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField56FocusLost(evt);
            }
        });
        jTextField56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField56ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 100, 20));

        jTextField49.setBackground(new java.awt.Color(51, 51, 51));
        jTextField49.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jTextField49.setForeground(new java.awt.Color(255, 255, 255));
        jTextField49.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField49.setBorder(null);
        jTextField49.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField49FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField49FocusLost(evt);
            }
        });
        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField49, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 90, 20));

        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, -1, -1));

        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 138, -1, -1));

        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, -1, 22));

        jTextField16.setBackground(new java.awt.Color(51, 51, 51));
        jTextField16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField16.setText("=");
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
        jPanel4.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 26, 15));

        jTextField15.setBackground(new java.awt.Color(51, 51, 51));
        jTextField15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField15.setText("=");
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
        jPanel4.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 26, 30));

        jTextField14.setBackground(new java.awt.Color(51, 51, 51));
        jTextField14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField14.setText("=");
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
        jPanel4.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 112, 26, 15));

        jTextField39.setBackground(new java.awt.Color(51, 51, 51));
        jTextField39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField39.setForeground(new java.awt.Color(255, 255, 255));
        jTextField39.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField39.setBorder(null);
        jTextField39.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField39FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField39FocusLost(evt);
            }
        });
        jTextField39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField39ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 160, 80, -1));

        jTextField38.setBackground(new java.awt.Color(51, 51, 51));
        jTextField38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField38.setForeground(new java.awt.Color(255, 255, 255));
        jTextField38.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField38.setBorder(null);
        jTextField38.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField38FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField38FocusLost(evt);
            }
        });
        jTextField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField38ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 140, 80, -1));

        jTextField30.setBackground(new java.awt.Color(51, 51, 51));
        jTextField30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField30.setForeground(new java.awt.Color(255, 255, 255));
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField30.setBorder(null);
        jTextField30.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField30FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField30FocusLost(evt);
            }
        });
        jTextField30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField30ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 110, 80, 20));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(51, 51, 51));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setText("______________________________________________________");
        jTextField4.setBorder(null);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 120, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 410, 220));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField33.setBackground(new java.awt.Color(51, 51, 51));
        jTextField33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField33.setForeground(new java.awt.Color(255, 255, 255));
        jTextField33.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField33.setText("Fuel Charges ");
        jTextField33.setBorder(null);
        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 110, 14));

        jTextField19.setBackground(new java.awt.Color(51, 51, 51));
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField19.setText("Full tank (Prepaid Option)");
        jTextField19.setBorder(null);
        jTextField19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField19FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField19FocusLost(evt);
            }
        });
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, -1, 22));

        jTextField18.setBackground(new java.awt.Color(51, 51, 51));
        jTextField18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField18.setText("=");
        jTextField18.setBorder(null);
        jTextField18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField18FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField18FocusLost(evt);
            }
        });
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 26, -1));

        jTextField40.setBackground(new java.awt.Color(51, 51, 51));
        jTextField40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField40.setForeground(new java.awt.Color(255, 255, 255));
        jTextField40.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField40.setText("₱5,000");
        jTextField40.setBorder(null);
        jTextField40.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField40FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField40FocusLost(evt);
            }
        });
        jTextField40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField40ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 24, 76, 20));

        jTextField20.setBackground(new java.awt.Color(51, 51, 51));
        jTextField20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField20.setForeground(new java.awt.Color(255, 255, 255));
        jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField20.setText("=");
        jTextField20.setBorder(null);
        jTextField20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField20FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField20FocusLost(evt);
            }
        });
        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 26, 15));

        jTextField41.setBackground(new java.awt.Color(51, 51, 51));
        jTextField41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField41.setForeground(new java.awt.Color(255, 255, 255));
        jTextField41.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField41.setBorder(null);
        jTextField41.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField41FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField41FocusLost(evt);
            }
        });
        jTextField41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField41ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField41, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 76, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 410, 100));

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField35.setBackground(new java.awt.Color(51, 51, 51));
        jTextField35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField35.setForeground(new java.awt.Color(255, 255, 255));
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField35.setText("Additional Driver");
        jTextField35.setBorder(null);
        jPanel6.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 410, 63));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField45.setEditable(false);
        jTextField45.setBackground(new java.awt.Color(51, 51, 51));
        jTextField45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField45.setForeground(new java.awt.Color(255, 255, 255));
        jTextField45.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField45.setText("Discounts/Promo and Extras");
        jTextField45.setBorder(null);
        jTextField45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField45ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, -1));

        jTextField48.setBackground(new java.awt.Color(51, 51, 51));
        jTextField48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField48.setForeground(new java.awt.Color(255, 255, 255));
        jTextField48.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField48.setText("₱2,000");
        jTextField48.setBorder(null);
        jTextField48.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField48FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField48FocusLost(evt);
            }
        });
        jTextField48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField48ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField48, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 80, 20));

        jTextField47.setBackground(new java.awt.Color(51, 51, 51));
        jTextField47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField47.setForeground(new java.awt.Color(255, 255, 255));
        jTextField47.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField47.setText("=");
        jTextField47.setBorder(null);
        jTextField47.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField47FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField47FocusLost(evt);
            }
        });
        jTextField47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField47ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 30, 40));

        jTextField61.setBackground(new java.awt.Color(51, 51, 51));
        jTextField61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField61.setForeground(new java.awt.Color(255, 255, 255));
        jTextField61.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField61.setText("FREE");
        jTextField61.setBorder(null);
        jTextField61.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField61FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField61FocusLost(evt);
            }
        });
        jTextField61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField61ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField61, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 80, -1));

        jTextField62.setBackground(new java.awt.Color(51, 51, 51));
        jTextField62.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField62.setForeground(new java.awt.Color(255, 255, 255));
        jTextField62.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField62.setText("=");
        jTextField62.setBorder(null);
        jTextField62.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField62FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField62FocusLost(evt);
            }
        });
        jTextField62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField62ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField62, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 30, 15));

        jTextField58.setBackground(new java.awt.Color(51, 51, 51));
        jTextField58.setForeground(new java.awt.Color(255, 255, 255));
        jTextField58.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField58.setText("Unit Return Location Delivery");
        jTextField58.setBorder(null);
        jTextField58.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField58FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField58FocusLost(evt);
            }
        });
        jTextField58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField58ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, -1));

        jTextField46.setBackground(new java.awt.Color(51, 51, 51));
        jTextField46.setForeground(new java.awt.Color(255, 255, 255));
        jTextField46.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField46.setText("Loyalty/Promo Code (FIX)");
        jTextField46.setBorder(null);
        jTextField46.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField46FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField46FocusLost(evt);
            }
        });
        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 22));

        jTextField29.setBackground(new java.awt.Color(51, 51, 51));
        jTextField29.setForeground(new java.awt.Color(255, 255, 255));
        jTextField29.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField29.setText("GPS (FREE FOR ALL TYPE OF CARS)");
        jTextField29.setBorder(null);
        jTextField29.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField29FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField29FocusLost(evt);
            }
        });
        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jTextField25.setBackground(new java.awt.Color(51, 51, 51));
        jTextField25.setForeground(new java.awt.Color(255, 255, 255));
        jTextField25.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField25.setText("Unit Pickup Location Delivery");
        jTextField25.setBorder(null);
        jTextField25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField25FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField25FocusLost(evt);
            }
        });
        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 170, -1));

        jTextField36.setBackground(new java.awt.Color(51, 51, 51));
        jTextField36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField36.setForeground(new java.awt.Color(255, 255, 255));
        jTextField36.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField36.setText("=");
        jTextField36.setBorder(null);
        jTextField36.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField36FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField36FocusLost(evt);
            }
        });
        jTextField36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField36ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 30, 15));

        jTextField50.setBackground(new java.awt.Color(51, 51, 51));
        jTextField50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField50.setForeground(new java.awt.Color(255, 255, 255));
        jTextField50.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField50.setText("FREE");
        jTextField50.setBorder(null);
        jTextField50.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField50FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField50FocusLost(evt);
            }
        });
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField50, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        jTextField43.setBackground(new java.awt.Color(51, 51, 51));
        jTextField43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField43.setForeground(new java.awt.Color(255, 255, 255));
        jTextField43.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField43.setText("FREE");
        jTextField43.setBorder(null);
        jTextField43.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField43FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField43FocusLost(evt);
            }
        });
        jTextField43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField43ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField43, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 60, -1));

        jTextField26.setBackground(new java.awt.Color(51, 51, 51));
        jTextField26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField26.setForeground(new java.awt.Color(255, 255, 255));
        jTextField26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField26.setText("=");
        jTextField26.setBorder(null);
        jTextField26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField26FocusLost(evt);
            }
        });
        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 30, 15));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 350, 170));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldZZ.setEditable(false);
        jTextFieldZZ.setBackground(new java.awt.Color(51, 51, 51));
        jTextFieldZZ.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextFieldZZ.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldZZ.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldZZ.setBorder(null);
        jPanel8.add(jTextFieldZZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 330, 40));

        jTextFieldZ.setBackground(new java.awt.Color(51, 51, 51));
        jTextFieldZ.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextFieldZ.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldZ.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldZ.setText("PARTIAL PAYMENT");
        jTextFieldZ.setBorder(null);
        jPanel8.add(jTextFieldZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 170, 40));

        jTextField60.setBackground(new java.awt.Color(51, 51, 51));
        jTextField60.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField60.setForeground(new java.awt.Color(255, 255, 255));
        jTextField60.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField60.setText("TOTAL (50%)");
        jTextField60.setBorder(null);
        jTextField60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField60ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField60, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 80, 160, 40));

        jTextField51.setEditable(false);
        jTextField51.setBackground(new java.awt.Color(51, 51, 51));
        jTextField51.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jTextField51.setForeground(new java.awt.Color(255, 255, 255));
        jTextField51.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField51.setBorder(null);
        jTextField51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField51ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 330, 40));

        jTextField59.setBackground(new java.awt.Color(51, 51, 51));
        jTextField59.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField59.setForeground(new java.awt.Color(255, 255, 255));
        jTextField59.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField59.setText("TOTAL");
        jTextField59.setBorder(null);
        jTextField59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField59ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField59, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 140, 30));

        jTextField57.setBackground(new java.awt.Color(51, 51, 51));
        jTextField57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField57.setForeground(new java.awt.Color(255, 255, 255));
        jTextField57.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField57.setText("FULL PAYMENT");
        jTextField57.setBorder(null);
        jTextField57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField57ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 30));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 350, 173));

        rSMaterialButtonRectangle4.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle4.setBorder(null);
        rSMaterialButtonRectangle4.setText("PAY IN FULL");
        rSMaterialButtonRectangle4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 370, 115, 39));

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(51, 51, 51));
        rSMaterialButtonRectangle3.setBorder(null);
        rSMaterialButtonRectangle3.setText("PAY PARTIALLY");
        rSMaterialButtonRectangle3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 370, 115, 39));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50ActionPerformed

    private void jTextField50FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField50FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50FocusLost

    private void jTextField50FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField50FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50FocusGained

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jTextField29FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField29FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29FocusLost

    private void jTextField29FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField29FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29FocusGained

    private void jTextField36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36ActionPerformed

    private void jTextField36FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField36FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36FocusLost

    private void jTextField36FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField36FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36FocusGained

    private void jTextField56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56ActionPerformed

    private void jTextField56FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField56FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56FocusLost

    private void jTextField56FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField56FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56FocusGained

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49ActionPerformed

    private void jTextField49FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField49FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49FocusLost

    private void jTextField49FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField49FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49FocusGained

    private void jTextField59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField59ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField59ActionPerformed

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed
        // TODO add your handling code here:
        handlePayment(true);
       
    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        // TODO add your handling code here:
        handlePayment(false);
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void jTextField60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField60ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField60ActionPerformed

    private void jTextField57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField57ActionPerformed

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField55FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField55FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55FocusLost

    private void jTextField55FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField55FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55FocusGained

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField17FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17FocusLost

    private void jTextField17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField17FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17FocusGained

    private void jTextField54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54ActionPerformed

    private void jTextField54FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField54FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54FocusLost

    private void jTextField54FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField54FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54FocusGained

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jTextField53FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField53FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53FocusLost

    private void jTextField53FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField53FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53FocusGained

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jTextField52FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField52FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52FocusLost

    private void jTextField52FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField52FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52FocusGained

    private void jTextField51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField51ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48ActionPerformed

    private void jTextField48FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField48FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48FocusLost

    private void jTextField48FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField48FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48FocusGained

    private void jTextField47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47ActionPerformed

    private void jTextField47FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField47FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47FocusLost

    private void jTextField47FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField47FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47FocusGained

    private void jTextField46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46ActionPerformed

    private void jTextField46FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46FocusLost

    private void jTextField46FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField46FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46FocusGained

    private void jTextField45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField45ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jTextField22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField22FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22FocusLost

    private void jTextField22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField22FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22FocusGained

    private void jTextField43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43ActionPerformed

    private void jTextField43FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField43FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43FocusLost

    private void jTextField43FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField43FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43FocusGained

    private void jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42ActionPerformed

    private void jTextField42FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField42FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42FocusLost

    private void jTextField42FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField42FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42FocusGained

    private void jTextField41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41ActionPerformed

    private void jTextField41FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField41FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41FocusLost

    private void jTextField41FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField41FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41FocusGained

    private void jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40ActionPerformed

    private void jTextField40FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField40FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40FocusLost

    private void jTextField40FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField40FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40FocusGained

    private void jTextField39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39ActionPerformed

    private void jTextField39FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField39FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39FocusLost

    private void jTextField39FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField39FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39FocusGained

    private void jTextField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38ActionPerformed

    private void jTextField38FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField38FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38FocusLost

    private void jTextField38FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField38FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38FocusGained

    private void jTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30ActionPerformed

    private void jTextField30FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField30FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30FocusLost

    private void jTextField30FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField30FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30FocusGained

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26ActionPerformed

    private void jTextField26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField26FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26FocusLost

    private void jTextField26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField26FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26FocusGained

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void jTextField25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField25FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25FocusLost

    private void jTextField25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField25FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25FocusGained

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jTextField24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField24FocusLost
        // TODO add your handling code here:
        if(jTextField24.getText().equals(""))
        {
            jTextField24.setText("Number of Days");
            jTextField24.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jTextField24FocusLost

    private void jTextField24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField24FocusGained
        // TODO add your handling code here:
        if(jTextField24.getText().equals("Number of Days"))
        {
            jTextField24.setText("");
            jTextField24.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_jTextField24FocusGained

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField23FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23FocusLost

    private void jTextField23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField23FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23FocusGained

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10FocusLost

    private void jTextField10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10FocusGained

    private void jTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21ActionPerformed

    private void jTextField21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField21FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21FocusLost

    private void jTextField21FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField21FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21FocusGained

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jTextField20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20FocusLost

    private void jTextField20FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20FocusGained

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField19FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19FocusLost

    private void jTextField19FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField19FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19FocusGained

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField18FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18FocusLost

    private void jTextField18FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField18FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18FocusGained

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField16FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16FocusLost

    private void jTextField16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField16FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16FocusGained

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField15FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15FocusLost

    private void jTextField15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField15FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15FocusGained

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14FocusLost

    private void jTextField14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField14FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14FocusGained

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox5.isSelected()) {
            transferAmountWithPeso2();
        } else {
            jTextField39.setText("₱ 0.00"); // or leave empty if preferred
        }
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox4.isSelected()) {
            transferAmountWithPeso1();
        } else {
            jTextField38.setText("₱ 0.00"); // or leave empty if preferred
        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox3.isSelected()) {
            transferAmountWithPeso();
        } else {
            jTextField30.setText("₱ 0.00"); // or leave empty if preferred
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField13FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13FocusLost

    private void jTextField13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField13FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13FocusGained

    private void jTextField32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusLost

    private void jTextField12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusGained

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusLost

    private void jTextField11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11FocusGained

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusLost
        // TODO add your handling code here:
        if(jTextField9.getText().equals(""))
        {
            jTextField9.setText("Number of Days");
            jTextField9.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jTextField9FocusLost

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        // TODO add your handling code here:
        if(jTextField9.getText().equals("Number of Days"))
        {
            jTextField9.setText("");
            jTextField9.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_jTextField9FocusGained

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8FocusLost

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7FocusLost

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained

    }//GEN-LAST:event_jTextField7FocusGained

    private void jTextField58FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField58FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58FocusGained

    private void jTextField58FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField58FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58FocusLost

    private void jTextField58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58ActionPerformed

    private void jTextField61FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField61FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61FocusGained

    private void jTextField61FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField61FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61FocusLost

    private void jTextField61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61ActionPerformed

    private void jTextField62FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField62FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField62FocusGained

    private void jTextField62FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField62FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField62FocusLost

    private void jTextField62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField62ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextFieldZ;
    private javax.swing.JTextField jTextFieldZZ;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    // End of variables declaration//GEN-END:variables
}
