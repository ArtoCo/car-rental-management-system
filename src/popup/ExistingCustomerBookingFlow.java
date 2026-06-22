/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

import customer.ServiceCustomer;
import customers.ModelCustomer1;
import java.awt.Window;
import java.util.ArrayList;
import java.util.function.Function;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import raven.popup.GlassPanePopup;
import customers.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExistingCustomerBookingFlow {

    public static void openBrandFromSelectedCustomer(
            JPanel parentPanel,
            JTable table,
            ServiceCustomer service,
            Function<String, JFrame> brandFrameFactory
    ) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<Integer> selectedRows = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, 0);
            if (checked != null && checked) {
                selectedRows.add(i);
            }
        }

        if (selectedRows.isEmpty()) {
            JOptionPane.showMessageDialog(parentPanel, "Please select one customer first.");
            return;
        }

        if (selectedRows.size() > 1) {
            JOptionPane.showMessageDialog(parentPanel, "Please select only one customer.");
            return;
        }

        //FOR VERIFCATION
    try {
    int row = selectedRows.get(0);
    int customerId = ((Number) model.getValueAt(row, 1)).intValue();

    ModelCustomer1 data = service.findById(customerId);

    if (data == null) {
        JOptionPane.showMessageDialog(parentPanel, "Customer not found.");
        return;
    }

    // ✅ 1. LICENSE CHECK FIRST
    if (data.getexpire_date() == null) {
        JOptionPane.showMessageDialog(
                parentPanel,
                "This customer has no license expiration date.",
                "License Required",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    java.time.LocalDate today = java.time.LocalDate.now();
    java.time.LocalDate expireDate = data.getexpire_date().toLocalDate();

    if (expireDate.isBefore(today)) {
    JOptionPane.showMessageDialog(
            parentPanel,
            "CUSTOMER'S LICENSE IS EXPIRED.",
            "EXPIRED LICENSE",
            JOptionPane.WARNING_MESSAGE
    );
    return;
}

    // ✅ 2. STATUS CHECK SECOND
    String currentStatus = getCustomerStatusById(customerId);

if (isBlockedStatus(currentStatus)) {
    JOptionPane.showMessageDialog(
            parentPanel,
            "THIS CUSTOMER IS NOT ALLOWED TO RENT/BOOK ANOTHER CAR "
            + "UNTIL THEIR STATUS IS RETURNED OR CANCELLED.",
            "BOOKING NOT ALLOWED",
            JOptionPane.WARNING_MESSAGE
    );
    return;
}

    // ✅ 3. TRANSMISSION CHECK AFTER LICENSE + STATUS
    String transmissionType = data.gettransmission_type() == null
            ? ""
            : data.gettransmission_type().trim();

    if (transmissionType.isEmpty()) {
        JOptionPane.showMessageDialog(parentPanel, "This customer has no transmission type.");
        return;
    }

    AppSession.startExistingCustomerBooking(customerId);

    AppSession.setCustomerId(customerId);
    AppSession2.setCustomerId(customerId);
    AppSession.setCustomerName(data.getFirstname1(), data.getLastname1());
    AppSession.setCustomerAddress(data.getPrimaryAddress1());
    AppSession.setCustomerPhone(data.getPhoneNumber1());
    AppSession.settransmission_type(transmissionType);

    JFrame brandFrame = brandFrameFactory.apply(transmissionType);
    brandFrame.pack();
    brandFrame.setLocationRelativeTo(null);

    Window owner = SwingUtilities.getWindowAncestor(parentPanel);

    GlassPanePopup.closePopupLast();

    if (owner != null) {
        owner.dispose();
    }

    brandFrame.setVisible(true);

} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(parentPanel,
            "Failed to open brand frame:\n" + e.getMessage());
}
    }
    
private static String getCustomerStatusById(int customerId) throws Exception {
    String sql = "SELECT status FROM customer WHERE customer_Id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("status");
            }
        }
    }

    return null;
}

private static boolean isBlockedStatus(String status) {
    if (status == null) {
        return false;
    }

    String s = status.trim().toUpperCase();

    return s.equals("RENTED")
            || s.equals("RENT")
            || s.equals("BOOKED");
}

}
