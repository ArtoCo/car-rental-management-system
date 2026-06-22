/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customer;

import ADMIN.Customers;
import ADMIN.Dashboard;
import customers.DatabaseConnection;
import customers.ModelCustomer1;
import employees.ModelEmployee;
import java.awt.Frame;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;



/**
 *
 * @author ARTO
 */
public class ServiceCustomer {

    // ✅ Retrieve all customers from database
public List<ModelCustomer1> getAll() throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;
    List<ModelCustomer1> list = new ArrayList<>();

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement("SELECT * FROM customer");
        r = p.executeQuery();

        while (r.next()) {
            int customerId = r.getInt("customer_Id");
            String firstname = r.getString("customer_firstname");
            String lastname = r.getString("customer_lastname");
            int age = r.getInt("Age");
            String phoneNumber = r.getString("phone_number");
            String address = r.getString("primary_address");
            Date transactionDate = r.getDate("transaction_Date");
            String paymentType = r.getString("payment_Type");
            double totalPayment = r.getDouble("total_payment");

            Date inDate = r.getDate("Pickup_Date");
            String pickup_time = r.getString("Pickup_time");
            String pickup_location = r.getString("Pickup_Location");

            Date outDate = r.getDate("Return_Date");
            String returned_time = r.getString("Returned_time");
            String return_location = r.getString("Return_Location");

            String carType = r.getString("vehicle_type");
            String carName = r.getString("vehicle_name");
            String status = r.getString("status");

            String driving_license = r.getString("driving_license");   // pictureBox1 (path/string)
            String driving_back    = r.getString("driving_back");      // pictureBox2 (path/string)

            String transmission_type = r.getString("transmission_type");
            String plate_number = r.getString("plate_number");

            String license_number = r.getString("license_number");
            Date expire_date = r.getDate("expire_date");
            String agency_code = r.getString("agency_code");
            String dl_codes = r.getString("dl_codes");                 // ✅ fixed
            String vehicle_category = r.getString("vehicle_category");
            String conditions = r.getString("conditions");             // ✅ fixed

            list.add(new ModelCustomer1(
                customerId, firstname, lastname, age, phoneNumber, address,
                transactionDate, paymentType, totalPayment,
                inDate, pickup_time, pickup_location,
                outDate, returned_time, return_location,
                carType, carName, status,
                driving_license, driving_back,
                transmission_type, plate_number,
                license_number, expire_date,
                agency_code, dl_codes, vehicle_category, conditions
            ));
        }

    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }

    return list;
}

    
    
    

    // ✅ Insert new customer into the database
    public int create(ModelCustomer1 data) throws SQLException, IOException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement(
            "INSERT INTO customer (customer_firstname, customer_lastname, Age, phone_number, primary_address, transaction_Date, payment_Type, total_payment, Return_Date, Return_Location, Pickup_Date, Pickup_Location, vehicle_type, vehicle_name, status, transmission_type, plate_number, driving_back) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS // important!
        );

        p.setString(1, data.getFirstname1());
        p.setString(2, data.getLastname1());
        p.setInt(3, data.getAge1());
        p.setString(4, data.getPhoneNumber1());
        p.setString(5, data.getPrimaryAddress1());
        p.setDate(6, data.getTransactionDate());
        p.setString(7, data.getPaymentType());
        p.setDouble(8, data.getTotalPayment());
        p.setDate(9, data.getOutDate());
        p.setString(10, data.getOutLocation());
        p.setDate(11, data.geInDate());
        p.setString(12, data.getInLocation());
        p.setString(13, data.getCarType());
        p.setString(14, data.getCarName());
        p.setString(15, data.getStatus());
        p.setString(16, data.gettransmission_type());
        p.setString(17, data.getplate_number());
        p.setString(18, data.getdriving_back());

        int affectedRows = p.executeUpdate();

        if (affectedRows > 0) {
            rs = p.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                data.setCustomerId(generatedId); // ✅ Update your model
                return generatedId;
            }
        }

    } finally {
        DatabaseConnection.getInstance().close(rs, p, con);
    }

    return -1; // failed
}


public ModelCustomer1 findById(int customerId) throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("SELECT * FROM customer WHERE customer_Id = ?");
        ps.setInt(1, customerId);
        rs = ps.executeQuery();

        if (rs.next()) {
            ModelCustomer1 m = new ModelCustomer1(
                rs.getInt("customer_Id"),
                rs.getString("customer_firstname"),
                rs.getString("customer_lastname"),
                rs.getInt("Age"),
                rs.getString("phone_number"),
                rs.getString("primary_address"),
                rs.getDate("transaction_Date"),
                rs.getString("payment_Type"),
                rs.getDouble("total_payment"),
                rs.getDate("Pickup_Date"),
                rs.getString("Pickup_time"),
                rs.getString("Pickup_Location"),
                rs.getDate("Return_Date"),
                rs.getString("Returned_time"),
                rs.getString("Return_Location"),
                rs.getString("vehicle_type"),
                rs.getString("vehicle_name"),
                rs.getString("status"),
                rs.getString("driving_license"),
                rs.getString("driving_back"),
                rs.getString("transmission_type"),
                rs.getString("plate_number"),
                rs.getString("license_number"),
                rs.getDate("expire_date"),
                rs.getString("agency_code"),
                rs.getString("dl_codes"),
                rs.getString("vehicle_category"),
                rs.getString("conditions")
            );

            // due fields (keep your logic)
            m.setDueHours(rs.getInt("due_hours"));
            m.setDueAmount(rs.getDouble("due_amount"));
            m.setTotalPay(rs.getDouble("total_pay"));

            return m;
        }

        return null;
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
}


public void edit(ModelCustomer1 data) throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;

    try {
        con = DatabaseConnection.getConnection();

        ps = con.prepareStatement(
            "UPDATE customer SET " +
            "customer_firstname = ?, customer_lastname = ?, Age = ?, phone_number = ?, primary_address = ?, " +
            "transaction_Date = ?, payment_Type = ?, total_payment = ?, " +
            "Pickup_Date = ?, Pickup_time = ?, Pickup_Location = ?, " +
            "Return_Date = ?, Returned_time = ?, Return_Location = ?, " +
            "vehicle_type = ?, vehicle_name = ?, status = ?, " +

            // ✅ NEW VEHICLE+LICENSE FIELDS
            "plate_number = ?, expire_date = ?, agency_code = ?, license_number = ?, " +
            "transmission_type = ?, dl_codes = ?, vehicle_category = ?, conditions = ? " +

            "WHERE customer_Id = ?"
        );

        int i = 1;

        ps.setString(i++, data.getFirstname1());
        ps.setString(i++, data.getLastname1());
        ps.setInt(i++, data.getAge1());
        ps.setString(i++, data.getPhoneNumber1());
        ps.setString(i++, data.getPrimaryAddress1());

        ps.setDate(i++, data.getTransactionDate());
        ps.setString(i++, data.getPaymentType());
        ps.setDouble(i++, data.getTotalPayment());

        ps.setDate(i++, data.geInDate());
        ps.setString(i++, data.getPickup_time());
        ps.setString(i++, data.getInLocation());

        ps.setDate(i++, data.getOutDate());
        ps.setString(i++, data.getReturned_time());
        ps.setString(i++, data.getOutLocation());

        ps.setString(i++, data.getCarType());
        ps.setString(i++, data.getCarName());
        ps.setString(i++, data.getStatus());

        ps.setString(i++, data.getplate_number());
        ps.setDate(i++, data.getexpire_date());
        ps.setString(i++, data.getagency_code());
        ps.setString(i++, data.getlicense_number());
        ps.setString(i++, data.gettransmission_type());
        ps.setString(i++, data.getdl_codes());
        ps.setString(i++, data.getvehicle_category());
        ps.setString(i++, data.getconditions());

        ps.setInt(i++, data.getCustomerId());

        if (ps.executeUpdate() != 1) {
            throw new SQLException("Customer was not found or could not be updated.");
        }

    } finally {
        DatabaseConnection.getInstance().close(ps, con);
    }
}




 
    public List<ModelCustomer1> search(String search) throws SQLException {

    List<ModelCustomer1> list = new ArrayList<>();

    String sql =
        "SELECT * FROM customer WHERE " +
        "CAST(customer_Id AS CHAR) LIKE ? OR " +
        "customer_firstname LIKE ? OR " +
        "customer_lastname LIKE ? OR " +
        "CAST(Age AS CHAR) LIKE ? OR " +
        "phone_number LIKE ? OR " +
        "primary_address LIKE ? OR " +
        "CAST(transaction_Date AS CHAR) LIKE ? OR " +
        "payment_Type LIKE ? OR " +
        "CAST(total_payment AS CHAR) LIKE ? OR " +
        "Return_Location LIKE ? OR " +
        "Pickup_Location LIKE ? OR " +
        "vehicle_type LIKE ? OR " +
        "vehicle_name LIKE ? OR " +
        "status LIKE ? " +
        "ORDER BY customer_Id ASC"; // ⭐ KEY FIX

    try (Connection con = DatabaseConnection.getInstance().createConnection();
         PreparedStatement p = con.prepareStatement(sql)) {

        for (int i = 1; i <= 14; i++) {
            p.setString(i, "%" + search + "%");
        }

        try (ResultSet r = p.executeQuery()) {
            while (r.next()) {
list.add(new ModelCustomer1(
    r.getInt("customer_Id"),
    r.getString("customer_firstname"),
    r.getString("customer_lastname"),
    r.getInt("Age"),
    r.getString("phone_number"),
    r.getString("primary_address"),
    r.getDate("transaction_Date"),
    r.getString("payment_Type"),
    r.getDouble("total_payment"),
    r.getDate("Pickup_Date"),
    r.getString("Pickup_time"),
    r.getString("Pickup_Location"),
    r.getDate("Return_Date"),
    r.getString("Returned_time"),
    r.getString("Return_Location"),
    r.getString("vehicle_type"),
    r.getString("vehicle_name"),
    r.getString("status"),

    // ✅ correct order after status
    r.getString("driving_license"),
    r.getString("driving_back"),
    r.getString("transmission_type"),
    r.getString("plate_number"),
    r.getString("license_number"),
    r.getDate("expire_date"),
    r.getString("agency_code"),
    r.getString("dl_codes"),
    r.getString("vehicle_category"),
    r.getString("conditions")
));

            }
        }
    }

    return list;
}

    
    
    public void incrementBookedCount() throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;

    try {
        con = DatabaseConnection.getConnection();

        // We do not need to track the count in another table.
        // Instead, this method ensures a fresh count of current 'BOOKED' entries.
        // Optional: this could be optimized via triggers or cache.

    } finally {
        DatabaseConnection.getInstance().close(ps, con);
    }
}
    
    public void incrementRentCount() throws SQLException {
    countCustomersByStatus("RENTED");
}
  
    public int countCustomersByStatus(String status) throws SQLException {
    int count = 0;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("SELECT COUNT(*) FROM customer WHERE status = ?");
        ps.setString(1, status);
        rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }

    return count;
}


    
    public Map<String, double[]> getMonthlyPaymentSummary(int year) throws SQLException {
    Map<String, double[]> monthlyTotals = new LinkedHashMap<>();

    List<String> displayMonths = Arrays.asList(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    );
    for (String month : displayMonths) {
        monthlyTotals.put(month, new double[]{0, 0});  // [PARTIAL, FULL]
    }

    String query =
    "SELECT " +
    "  DATE_FORMAT(transaction_Date, '%M') AS month, " +
    "  payment_Type, " +
    "  SUM(total_payment) AS total " +
    "FROM customer " +
    "WHERE payment_Type IN ('FULL PAYMENT', 'PARTIAL PAYMENT') " +
    "  AND YEAR(transaction_Date) = ? " +
    "GROUP BY month, payment_Type " +
    "ORDER BY MIN(transaction_Date)";


    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = prepareMonthlyPaymentQuery(ps, year)) {

        while (rs.next()) {
            String month = rs.getString("month");
            String type = rs.getString("payment_Type");
            double total = rs.getDouble("total");

            // Only update if the month is part of our July–December target range
            if (monthlyTotals.containsKey(month)) {
                if ("PARTIAL PAYMENT".equalsIgnoreCase(type)) {
                    monthlyTotals.get(month)[0] = total;
                } else if ("FULL PAYMENT".equalsIgnoreCase(type)) {
                    monthlyTotals.get(month)[1] = total;
                }
            }
        }
    }

    return monthlyTotals;
}

private ResultSet prepareMonthlyPaymentQuery(PreparedStatement ps, int year) throws SQLException {
    ps.setInt(1, year);
    return ps.executeQuery();
}
    
    
    // ✅ Count how many FULL PAYMENT in payment_Type column
public int countFullPayments() throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int count = 0;

    try {
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("SELECT COUNT(*) FROM customer WHERE payment_Type = 'FULL PAYMENT'");
        rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return count;
}

public int countPartialPayments() throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int count = 0;

    try {
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("SELECT COUNT(*) FROM customer WHERE payment_Type = 'PARTIAL PAYMENT'");
        rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return count;
}

 
   public ModelCustomer1 findByPhoneNumber(String phoneNumber) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement("SELECT * FROM customer WHERE phone_number = ?");
        p.setString(1, phoneNumber);
        r = p.executeQuery();

        if (r.next()) {
            return new ModelCustomer1(
                r.getInt("customer_Id"),
                r.getString("customer_firstname"),
                r.getString("customer_lastname"),
                r.getInt("Age"),
                r.getString("phone_number"),
                r.getString("primary_address"),
                r.getDate("transaction_Date"),
                r.getString("payment_Type"),
                r.getDouble("total_payment"),
                r.getDate("Pickup_Date"),
                r.getString("Pickup_time"),
                r.getString("Pickup_Location"),
                r.getDate("Return_Date"),                   
                r.getString("Returned_time"),
                r.getString("Return_Location"),
                r.getString("vehicle_type"),
                r.getString("vehicle_name"),
                r.getString("status"),
                r.getString("driving_license"),
                r.getString("driving_back"),
                r.getString("transmission_type"),
                r.getString("plate_number"),
                r.getString("license_number"),
                r.getDate("expire_date"),
                r.getString("agency_code"),
                r.getString("dl_codes"),
                r.getString("vehicle_category"),
                r.getString("conditions")

            );
        }
        return null;
    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }
}

public void deleteByPhoneNumber(String phoneNumber) throws SQLException {
    List<Integer> ids = new ArrayList<>();
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement p = con.prepareStatement(
                 "SELECT customer_Id FROM customer WHERE phone_number = ?")) {
        p.setString(1, phoneNumber);
        try (ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
    }
    if (ids.size() != 1) {
        throw new SQLException(ids.isEmpty()
                ? "No customer was found for that phone number."
                : "Multiple customers use that phone number; delete was blocked.");
    }
    archiveAndDeleteByCustomerId(ids.get(0));
}

public void archiveAndDeleteByCustomerId(int customerId) throws SQLException {
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        con.setAutoCommit(false);
        try {
            String status = null;
            try (PreparedStatement check = con.prepareStatement(
                    "SELECT status FROM customer WHERE customer_Id = ? FOR UPDATE")) {
                check.setInt(1, customerId);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        status = rs.getString("status");
                    }
                }
            }
            if (!isCustomerDeletionAllowed(status)) {
                throw new SQLException("Customer cannot be deleted while status is "
                        + normalizeStatus(status) + ". Only RETURNED or CANCELLED customers can be deleted.");
            }

            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS customer_deleted LIKE customer");
            }
            try (PreparedStatement archive = con.prepareStatement(
                    "INSERT INTO customer_deleted SELECT * FROM customer WHERE customer_Id = ?")) {
                archive.setInt(1, customerId);
                if (archive.executeUpdate() != 1) {
                    throw new SQLException("Customer was not archived; delete was cancelled.");
                }
            }
            try (PreparedStatement delete = con.prepareStatement(
                    "DELETE FROM customer WHERE customer_Id = ?")) {
                delete.setInt(1, customerId);
                if (delete.executeUpdate() != 1) {
                    throw new SQLException("Customer was not deleted.");
                }
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
}

public String getCustomerDeletionBlockedStatus(int customerId) throws SQLException {
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
                 "SELECT status FROM customer WHERE customer_Id = ?")) {
        ps.setInt(1, customerId);
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("Customer was not found.");
            }
            String status = rs.getString("status");
            return isCustomerDeletionAllowed(status) ? null : normalizeStatus(status);
        }
    }
}

public String getCustomerEditBlockedStatus(int customerId) throws SQLException {
    return getCustomerDeletionBlockedStatus(customerId);
}

private boolean isCustomerDeletionAllowed(String status) {
    String normalized = normalizeStatus(status);
    return "RETURNED".equals(normalized) || "CANCELLED".equals(normalized);
}

private String normalizeStatus(String status) {
    return status == null || status.trim().isEmpty()
            ? "PENDING"
            : status.trim().toUpperCase();
}

public List<ModelCustomer1> getDeletedCustomers() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS customer_deleted LIKE customer");
        }
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM customer_deleted ORDER BY customer_Id ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ModelCustomer1(
                        rs.getInt("customer_Id"),
                        rs.getString("customer_firstname"),
                        rs.getString("customer_lastname"),
                        rs.getInt("Age"),
                        rs.getString("phone_number"),
                        rs.getString("primary_address"),
                        rs.getDate("transaction_Date"),
                        rs.getString("payment_Type"),
                        rs.getDouble("total_payment"),
                        rs.getDate("Pickup_Date"),
                        rs.getString("Pickup_time"),
                        rs.getString("Pickup_Location"),
                        rs.getDate("Return_Date"),
                        rs.getString("Returned_time"),
                        rs.getString("Return_Location"),
                        rs.getString("vehicle_type"),
                        rs.getString("vehicle_name"),
                        rs.getString("status"),
                        rs.getString("driving_license"),
                        rs.getString("driving_back"),
                        rs.getString("transmission_type"),
                        rs.getString("plate_number"),
                        rs.getString("license_number"),
                        rs.getDate("expire_date"),
                        rs.getString("agency_code"),
                        rs.getString("dl_codes"),
                        rs.getString("vehicle_category"),
                        rs.getString("conditions")
                ));
            }
        }
    }
    return list;
}

public int restoreDeletedCustomersByIds(List<Integer> customerIds) throws SQLException {
    if (customerIds == null || customerIds.isEmpty()) {
        return 0;
    }

    int restored = 0;
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        con.setAutoCommit(false);
        try {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS customer_deleted LIKE customer");
            }
            for (Integer id : customerIds) {
                if (id == null) {
                    continue;
                }
                try (PreparedStatement activeCheck = con.prepareStatement(
                        "SELECT COUNT(*) FROM customer WHERE customer_Id = ?")) {
                    activeCheck.setInt(1, id);
                    try (ResultSet rs = activeCheck.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            throw new SQLException("Customer " + id + " already exists in active customers.");
                        }
                    }
                }
                try (PreparedStatement restore = con.prepareStatement(
                        "INSERT INTO customer SELECT * FROM customer_deleted WHERE customer_Id = ?")) {
                    restore.setInt(1, id);
                    if (restore.executeUpdate() != 1) {
                        throw new SQLException("Customer " + id + " was not found in deleted customers.");
                    }
                }
                try (PreparedStatement removeArchive = con.prepareStatement(
                        "DELETE FROM customer_deleted WHERE customer_Id = ?")) {
                    removeArchive.setInt(1, id);
                    if (removeArchive.executeUpdate() != 1) {
                        throw new SQLException("Restored customer archive could not be cleared.");
                    }
                }
                restored++;
            }
            con.commit();
            return restored;
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
}

public int restoreAllDeletedCustomers() throws SQLException {
    int restored = 0;
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        con.setAutoCommit(false);
        try {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS customer_deleted LIKE customer");
            }
            List<Integer> ids = new ArrayList<>();
            try (PreparedStatement select = con.prepareStatement(
                    "SELECT customer_Id FROM customer_deleted ORDER BY customer_Id");
                 ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
            for (Integer id : ids) {
                try (PreparedStatement restore = con.prepareStatement(
                        "INSERT INTO customer SELECT * FROM customer_deleted WHERE customer_Id = ?")) {
                    restore.setInt(1, id);
                    if (restore.executeUpdate() != 1) {
                        throw new SQLException("Customer " + id + " was not restored.");
                    }
                }
                try (PreparedStatement removeArchive = con.prepareStatement(
                        "DELETE FROM customer_deleted WHERE customer_Id = ?")) {
                    removeArchive.setInt(1, id);
                    if (removeArchive.executeUpdate() != 1) {
                        throw new SQLException("Restored customer archive could not be cleared.");
                    }
                }
                restored++;
            }
            con.commit();
            return restored;
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
}

public int createCompleteBooking(
        ModelCustomer1 data, String carType, String carName, String transmission,
        String plateNumber, String licenseNumber, Date expireDate, String agencyCode,
        String dlCodes, String vehicleCategory, String conditions,
        byte[] drivingLicense, byte[] drivingBack) throws SQLException {

    String sql = "INSERT INTO customer ("
            + "customer_firstname, customer_lastname, Age, phone_number, primary_address, transaction_Date, "
            + "payment_Type, total_payment, Return_Date, Return_Location, Pickup_Date, Pickup_Location, "
            + "vehicle_type, vehicle_name, status, transmission_type, plate_number, license_number, expire_date, "
            + "agency_code, dl_codes, vehicle_category, conditions, driving_license, driving_back"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setString(i++, data.getFirstname1());
            ps.setString(i++, data.getLastname1());
            ps.setInt(i++, data.getAge1());
            ps.setString(i++, data.getPhoneNumber1());
            ps.setString(i++, data.getPrimaryAddress1());
            ps.setDate(i++, data.getTransactionDate());
            ps.setString(i++, data.getPaymentType());
            ps.setDouble(i++, data.getTotalPayment());
            ps.setDate(i++, data.getOutDate());
            ps.setString(i++, data.getOutLocation());
            ps.setDate(i++, data.geInDate());
            ps.setString(i++, data.getInLocation());
            ps.setString(i++, carType);
            ps.setString(i++, carName);
            ps.setString(i++, data.getStatus());
            ps.setString(i++, transmission);
            ps.setString(i++, plateNumber);
            ps.setString(i++, licenseNumber);
            ps.setDate(i++, expireDate);
            ps.setString(i++, agencyCode);
            ps.setString(i++, dlCodes);
            ps.setString(i++, vehicleCategory);
            ps.setString(i++, conditions);
            ps.setBytes(i++, drivingLicense);
            ps.setBytes(i++, drivingBack);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Customer booking was not created.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("Customer booking ID was not generated.");
                }
                int id = keys.getInt(1);
                data.setCustomerId(id);
                con.commit();
                return id;
            }
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
}



public void updateCheckoutInfoByPhone(String phoneNumber, Date checkOutDate, String Returned_time, String checkOutLocation) throws SQLException {
    throw new SQLException("Phone-only return updates are blocked. Use verified customer identity.");
}

public void updateCheckinInfoByPhone(String phoneNumber, Date InDate, String Pickup_time, String checkInLocation) throws SQLException {
    throw new SQLException("Phone-only pickup updates are blocked. Use verified customer identity.");
}

public void updateCarInfoByPhone(String phoneNumber, String CarType, String CarName) throws SQLException {
    updateCarInfoByCustomerId(
            resolveSingleCustomerIdByPhone(phoneNumber),
            CarType,
            CarName);
}

public void updateStatusByPhone(String phoneNumber, String Status) throws SQLException {
    updateStatusByUniquePhone(phoneNumber, Status);
}

public void updatePaymentByVerifiedCustomer(int customerId, String firstName, String lastName, String phoneNumber, String paymentType, double amount) throws SQLException {
    String sql = "UPDATE customer SET payment_Type = ?, total_payment = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        conn.setAutoCommit(false);
        try {
            requireVerifiedCustomer(conn, customerId, firstName, lastName, phoneNumber);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, paymentType);
                ps.setDouble(2, amount);
                ps.setInt(3, customerId);
                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Customer payment was not updated.");
                }
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

public void updateCheckoutInfoByVerifiedCustomer(int customerId, String firstName, String lastName, String phoneNumber, Date checkOutDate, String Returned_time, String checkOutLocation) throws SQLException {
    if (checkOutDate == null) {
        throw new SQLException("Return date is required.");
    }
    String sql = "UPDATE customer SET Return_Date = ?, Returned_time = ?, Return_Location = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        conn.setAutoCommit(false);
        try {
            requireVerifiedCustomer(conn, customerId, firstName, lastName, phoneNumber);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, new java.sql.Date(checkOutDate.getTime()));
                ps.setString(2, Returned_time);
                ps.setString(3, checkOutLocation);
                ps.setInt(4, customerId);
                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Customer checkout information was not updated.");
                }
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

public void updateCheckinInfoByVerifiedCustomer(int customerId, String firstName, String lastName, String phoneNumber, Date InDate, String Pickup_time, String checkInLocation) throws SQLException {
    if (InDate == null) {
        throw new SQLException("Pickup date is required.");
    }
    String sql = "UPDATE customer SET Pickup_Date = ?, Pickup_time = ?, Pickup_Location = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        conn.setAutoCommit(false);
        try {
            requireVerifiedCustomer(conn, customerId, firstName, lastName, phoneNumber);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, new java.sql.Date(InDate.getTime()));
                ps.setString(2, Pickup_time);
                ps.setString(3, checkInLocation);
                ps.setInt(4, customerId);
                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Customer pickup information was not updated.");
                }
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

public void updateCheckoutInfoByCustomerId(int customerId, Date checkOutDate, String Returned_time, String checkOutLocation) throws SQLException {
    if (checkOutDate == null) {
        throw new SQLException("Return date is required.");
    }
    String sql = "UPDATE customer SET Return_Date = ?, Returned_time = ?, Return_Location = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(checkOutDate.getTime()));
            ps.setString(2, Returned_time);
            ps.setString(3, checkOutLocation);
            ps.setInt(4, customerId);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Customer checkout information was not updated.");
            }
        }
    }
}

public void updateCheckinInfoByCustomerId(int customerId, Date InDate, String Pickup_time, String checkInLocation) throws SQLException {
    if (InDate == null) {
        throw new SQLException("Pickup date is required.");
    }
    String sql = "UPDATE customer SET Pickup_Date = ?, Pickup_time = ?, Pickup_Location = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(InDate.getTime()));
            ps.setString(2, Pickup_time);
            ps.setString(3, checkInLocation);
            ps.setInt(4, customerId);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Customer pickup information was not updated.");
            }
        }
    }
}

public void updateCarInfoByCustomerId(int customerId, String CarType, String CarName) throws SQLException {
    String sql = "UPDATE customer SET vehicle_type = ?, vehicle_name = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, CarType);
            ps.setString(2, CarName);
            ps.setInt(3, customerId);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Customer car information was not updated.");
            }
        }
    }
}

public void updateStatusByCustomerId(int customerId, String status) throws SQLException {
    String sql = "UPDATE customer SET status = ? WHERE customer_Id = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, customerId);
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Customer status was not updated.");
            }
        }
    }
}

private int resolveSingleCustomerIdByPhone(String phoneNumber) throws SQLException {
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
        throw new SQLException("Phone number is required.");
    }

    List<Integer> ids = new ArrayList<>();
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        try (PreparedStatement ps = con.prepareStatement(
                 "SELECT customer_Id FROM customer WHERE phone_number = ?")) {
            ps.setString(1, phoneNumber.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
        }
    }

    if (ids.size() != 1) {
        throw new SQLException(ids.isEmpty()
                ? "No customer was found for that phone number."
                : "Multiple customers use that phone number; update was blocked.");
    }

    return ids.get(0);
}

private void requireVerifiedCustomer(Connection con, int customerId, String firstName, String lastName, String phoneNumber) throws SQLException {
    if (customerId <= 0
            || firstName == null || firstName.isEmpty()
            || lastName == null || lastName.isEmpty()
            || phoneNumber == null || phoneNumber.isEmpty()) {
        throw new SQLException("Customer identity is incomplete; update was blocked.");
    }

    int matches = 0;
    try (PreparedStatement ps = con.prepareStatement(
            "SELECT customer_Id FROM customer "
            + "WHERE customer_Id = ? "
            + "AND BINARY customer_firstname = BINARY ? "
            + "AND BINARY customer_lastname = BINARY ? "
            + "AND BINARY phone_number = BINARY ? "
            + "FOR UPDATE")) {
        ps.setInt(1, customerId);
        ps.setString(2, firstName);
        ps.setString(3, lastName);
        ps.setString(4, phoneNumber);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                matches++;
            }
        }
    }

    if (matches != 1) {
        throw new SQLException("Customer identity did not match exactly one record; update was blocked.");
    }
}

public void updateStatusByUniquePhone(String phoneNumber, String status) throws SQLException {
    try (Connection con = DatabaseConnection.getConnection()) {
        if (con == null) {
            throw new SQLException("Database connection is unavailable.");
        }
        con.setAutoCommit(false);
        try {
            int matches;
            try (PreparedStatement count = con.prepareStatement(
                    "SELECT COUNT(*) FROM customer WHERE phone_number = ? FOR UPDATE")) {
                count.setString(1, phoneNumber);
                try (ResultSet rs = count.executeQuery()) {
                    rs.next();
                    matches = rs.getInt(1);
                }
            }
            if (matches != 1) {
                throw new SQLException(matches == 0
                        ? "No customer was found for that phone number."
                        : "Multiple customers use that phone number; status update was blocked.");
            }
            try (PreparedStatement update = con.prepareStatement(
                    "UPDATE customer SET status = ? WHERE phone_number = ?")) {
                update.setString(1, status);
                update.setString(2, phoneNumber);
                if (update.executeUpdate() != 1) {
                    throw new SQLException("Customer status was not updated.");
                }
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
}

public ModelCustomer1 findByCustomerId(int customerId) throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("SELECT * FROM customer WHERE customer_Id = ?");
        ps.setInt(1, customerId);
        rs = ps.executeQuery();

        if (rs.next()) {
            return new ModelCustomer1(
            rs.getInt("customer_id"),
            rs.getString("customer_firstname"),
            rs.getString("customer_lastname"),
            rs.getInt("Age"),
            rs.getString("phone_number"),
            rs.getString("primary_address"),
            rs.getDate("transaction_Date"),
            rs.getString("payment_Type"),
            rs.getDouble("total_payment"),
            rs.getDate("Pickup_Date"),
            rs.getString("Pickup_time"),
            rs.getString("Pickup_Location"),
            rs.getDate("Return_Date"),
            rs.getString("Returned_time"),
            rs.getString("Return_Location"),
            rs.getString("vehicle_type"),
            rs.getString("vehicle_name"),
            rs.getString("status"),
            rs.getString("driving_license"),
            rs.getString("driving_back"),
            rs.getString("transmission_type"),
            rs.getString("plate_number"),
            rs.getString("license_number"),
            rs.getDate("expire_date"),
            rs.getString("agency_code"),
            rs.getString("dl_codes"),
            rs.getString("vehicle_category"),
            rs.getString("conditions")
            );
        }

    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }

    return null;
}


public List<ModelCustomer1> getFullPayments() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE payment_Type = 'FULL PAYMENT' ORDER BY transaction_Date DESC";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
            rs.getInt("customer_id"),
            rs.getString("customer_firstname"),
            rs.getString("customer_lastname"),
            rs.getInt("Age"),
            rs.getString("phone_number"),
            rs.getString("primary_address"),
            rs.getDate("transaction_Date"),
            rs.getString("payment_Type"),
            rs.getDouble("total_payment"),
            rs.getDate("Pickup_Date"),
            rs.getString("Pickup_time"),
            rs.getString("Pickup_Location"),
            rs.getDate("Return_Date"),
            rs.getString("Returned_time"),
            rs.getString("Return_Location"),
            rs.getString("vehicle_type"),
            rs.getString("vehicle_name"),
            rs.getString("status"),
            rs.getString("driving_license"),
            rs.getString("driving_back"),
            rs.getString("transmission_type"),
            rs.getString("plate_number"),
            rs.getString("license_number"),
            rs.getDate("expire_date"),
            rs.getString("agency_code"),
            rs.getString("dl_codes"),
            rs.getString("vehicle_category"),
            rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return list;
}

public List<ModelCustomer1> getPartialPayments() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE payment_Type = 'PARTIAL PAYMENT' ORDER BY transaction_Date DESC";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
            rs.getInt("customer_id"),
            rs.getString("customer_firstname"),
            rs.getString("customer_lastname"),
            rs.getInt("Age"),
            rs.getString("phone_number"),
            rs.getString("primary_address"),
            rs.getDate("transaction_Date"),
            rs.getString("payment_Type"),
            rs.getDouble("total_payment"),
            rs.getDate("Pickup_Date"),
            rs.getString("Pickup_time"),
            rs.getString("Pickup_Location"),
            rs.getDate("Return_Date"),
            rs.getString("Returned_time"),
            rs.getString("Return_Location"),
            rs.getString("vehicle_type"),
            rs.getString("vehicle_name"),
            rs.getString("status"),
            rs.getString("driving_license"),
            rs.getString("driving_back"),
            rs.getString("transmission_type"),
            rs.getString("plate_number"),
            rs.getString("license_number"),
            rs.getDate("expire_date"),
            rs.getString("agency_code"),
            rs.getString("dl_codes"),
            rs.getString("vehicle_category"),
            rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return list;
}

public List<ModelCustomer1> getTotalPayments() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE payment_Type IN ('FULL PAYMENT', 'PARTIAL PAYMENT') ORDER BY transaction_Date DESC";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
            rs.getInt("customer_id"),
            rs.getString("customer_firstname"),
            rs.getString("customer_lastname"),
            rs.getInt("Age"),
            rs.getString("phone_number"),
            rs.getString("primary_address"),
            rs.getDate("transaction_Date"),
            rs.getString("payment_Type"),
            rs.getDouble("total_payment"),
            rs.getDate("Pickup_Date"),
            rs.getString("Pickup_time"),
            rs.getString("Pickup_Location"),
            rs.getDate("Return_Date"),
            rs.getString("Returned_time"),
            rs.getString("Return_Location"),
            rs.getString("vehicle_type"),
            rs.getString("vehicle_name"),
            rs.getString("status"),
            rs.getString("driving_license"),
            rs.getString("driving_back"),
            rs.getString("transmission_type"),
            rs.getString("plate_number"),
            rs.getString("license_number"),
            rs.getDate("expire_date"),
            rs.getString("agency_code"),
            rs.getString("dl_codes"),
            rs.getString("vehicle_category"),
            rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return list;
}

public List<ModelCustomer1> getbookedrented() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM customer " +
                     "WHERE status IN ('RENTED','BOOKED','RETURNED','PAID','CANCELLED') " +
                     "ORDER BY customer_Id ASC";

        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
                rs.getInt("customer_id"),
                rs.getString("customer_firstname"),
                rs.getString("customer_lastname"),
                rs.getInt("Age"),
                rs.getString("phone_number"),
                rs.getString("primary_address"),
                rs.getDate("transaction_Date"),
                rs.getString("payment_Type"),
                rs.getDouble("total_payment"),
                rs.getDate("Pickup_Date"),
                rs.getString("Pickup_time"),
                rs.getString("Pickup_Location"),
                rs.getDate("Return_Date"),
                rs.getString("Returned_time"),
                rs.getString("Return_Location"),
                rs.getString("vehicle_type"),
                rs.getString("vehicle_name"),
                rs.getString("status"),
                rs.getString("driving_license"),
                rs.getString("driving_back"),
                rs.getString("transmission_type"),
                rs.getString("plate_number"),
                rs.getString("license_number"),
                rs.getDate("expire_date"),
                rs.getString("agency_code"),
                rs.getString("dl_codes"),
                rs.getString("vehicle_category"),
                rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }

    return list;
}

public List<ModelCustomer1> getbookedrented1() throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();

        // ✅ Include returned/paid so it stays visible in Customers table
        String sql = "SELECT * FROM customer " +
                     "WHERE status IN ('RENTED','BOOKED') " +
                     "ORDER BY transaction_Date ASC";

        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
                rs.getInt("customer_id"),
                rs.getString("customer_firstname"),
                rs.getString("customer_lastname"),
                rs.getInt("Age"),
                rs.getString("phone_number"),
                rs.getString("primary_address"),
                rs.getDate("transaction_Date"),
                rs.getString("payment_Type"),
                rs.getDouble("total_payment"),
                rs.getDate("Pickup_Date"),
                rs.getString("Pickup_time"),
                rs.getString("Pickup_Location"),
                rs.getDate("Return_Date"),
                rs.getString("Returned_time"),
                rs.getString("Return_Location"),
                rs.getString("vehicle_type"),
                rs.getString("vehicle_name"),
                rs.getString("status"),
                rs.getString("driving_license"),
                rs.getString("driving_back"),
                rs.getString("transmission_type"),
                rs.getString("plate_number"),
                rs.getString("license_number"),
                rs.getDate("expire_date"),
                rs.getString("agency_code"),
                rs.getString("dl_codes"),
                rs.getString("vehicle_category"),
                rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }
    return list;
}



public List<ModelCustomer1> searchFullPartialPayments(String keyword) throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE payment_Type = 'FULL PAYMENT' AND (" +
                     "CAST(customer_Id AS CHAR) LIKE ? OR " +
                     "customer_firstname LIKE ? OR " +
                     "customer_lastname LIKE ? OR " +
                     "phone_number LIKE ? OR " +
                     "vehicle_type LIKE ? OR " +
                     "vehicle_name LIKE ? OR " +
                     "Pickup_Location LIKE ? OR " +
                     "Return_Location LIKE ?)";
        ps = con.prepareStatement(sql);

        for (int i = 1; i <= 8; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new ModelCustomer1(
            rs.getInt("customer_id"),
            rs.getString("customer_firstname"),
            rs.getString("customer_lastname"),
            rs.getInt("Age"),
            rs.getString("phone_number"),
            rs.getString("primary_address"),
            rs.getDate("transaction_Date"),
            rs.getString("payment_Type"),
            rs.getDouble("total_payment"),
            rs.getDate("Pickup_Date"),
            rs.getString("Pickup_time"),
            rs.getString("Pickup_Location"),
            rs.getDate("Return_Date"),
            rs.getString("Returned_time"),
            rs.getString("Return_Location"),
            rs.getString("vehicle_type"),
            rs.getString("vehicle_name"),
            rs.getString("status"),
            rs.getString("driving_license"),
            rs.getString("driving_back"),
            rs.getString("transmission_type"),
            rs.getString("plate_number"),
            rs.getString("license_number"),
            rs.getDate("expire_date"),
            rs.getString("agency_code"),
            rs.getString("dl_codes"),
            rs.getString("vehicle_category"),
            rs.getString("conditions")
            ));
        }
    } finally {
        DatabaseConnection.getInstance().close(rs, ps, con);
    }

    return list;
}

public void deleteById(int customerId) throws SQLException {
    archiveAndDeleteByCustomerId(customerId);
}

public List<ModelCustomer1> getDueAfterOneHour() throws SQLException {

    // 1) realtime compute + save
    refreshDueRealtime();

    // 2) show only accounts >= 1 hour late (your existing rule)
    List<ModelCustomer1> list = new ArrayList<>();

    String sql =
        "SELECT *, FLOOR(IFNULL(due_hours,0) / 24) AS due_days " +
        "FROM customer " +
        "WHERE IFNULL(due_hours,0) >= 1 " +
        "  AND status IN ('RENTED','BOOKED') " +
        "ORDER BY Return_Date ASC";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            ModelCustomer1 m = new ModelCustomer1(
                rs.getInt("customer_id"),
                rs.getString("customer_firstname"),
                rs.getString("customer_lastname"),
                rs.getInt("Age"),
                rs.getString("phone_number"),
                rs.getString("primary_address"),
                rs.getDate("transaction_Date"),
                rs.getString("payment_Type"),
                rs.getDouble("total_payment"),
                rs.getDate("Pickup_Date"),
                rs.getString("Pickup_time"),
                rs.getString("Pickup_Location"),
                rs.getDate("Return_Date"),
                rs.getString("Returned_time"),
                rs.getString("Return_Location"),
                rs.getString("vehicle_type"),
                rs.getString("vehicle_name"),
                rs.getString("status"),
                rs.getString("driving_license"),
                rs.getString("driving_back"),
                rs.getString("transmission_type"),
                rs.getString("plate_number"),
                rs.getString("license_number"),
                rs.getDate("expire_date"),
                rs.getString("agency_code"),
                rs.getString("dl_codes"),
                rs.getString("vehicle_category"),
                rs.getString("conditions")
            );

            // new fields from DB
            m.setDueHours(rs.getInt("due_hours"));
            m.setDueDays(rs.getInt("due_days"));
            m.setDueAmount(rs.getDouble("due_amount"));
            m.setTotalPay(rs.getDouble("total_pay"));

            list.add(m);
        }
    }

    return list;
}


public int countDueAfterOneHour() throws SQLException {
    int count = 0;

    String sql =
        "SELECT COUNT(*) " +
        "FROM customer " +
        "WHERE Return_Date IS NOT NULL " +
        "  AND Returned_time IS NOT NULL " +
        "  AND Returned_time <> '' " +
        "  AND status IN ('RENTED', 'BOOKED') " +
        "  AND NOW() >= DATE_ADD( " +
        "        STR_TO_DATE( " +
        "          CONCAT(Return_Date, ' ', Returned_time), " +
        "          CASE " +
        "            WHEN Returned_time REGEXP '(AM|PM|am|pm)' THEN '%Y-%m-%d %h:%i %p' " +
        "            WHEN Returned_time LIKE '%:%:%' THEN '%Y-%m-%d %H:%i:%s' " +
        "            ELSE '%Y-%m-%d %H:%i' " +
        "          END " +
        "        ), " +
        "        INTERVAL 1 HOUR " +
        "      )";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    }

    return count;
}

private static final String DUE_DT_EXPR =
        "STR_TO_DATE(CONCAT(Return_Date, ' ', Returned_time), " +
        "  CASE " +
        "    WHEN Returned_time REGEXP '(AM|PM|am|pm)' THEN '%Y-%m-%d %h:%i %p' " +
        "    WHEN Returned_time LIKE '%:%:%' THEN '%Y-%m-%d %H:%i:%s' " +
        "    ELSE '%Y-%m-%d %H:%i' " +
        "  END" +
        ")";

public void refreshDueRealtime() throws SQLException {

    // dh = hours late (0 if not late yet, 1 when 1 hour late, 2 when 2 hours late, etc.)
    // due_amount = dh * 100
    // total_pay:
    //   FULL PAYMENT    -> only due penalty
    //   PARTIAL PAYMENT -> total_payment + due penalty  (based on your message)
    String sql =
        "UPDATE customer c " +
        "JOIN ( " +
        "   SELECT customer_id, " +
        "          GREATEST(0, TIMESTAMPDIFF(HOUR, due_dt, NOW())) AS dh " +
        "   FROM ( " +
        "       SELECT customer_id, " + DUE_DT_EXPR + " AS due_dt " +
        "       FROM customer " +
        "       WHERE Return_Date IS NOT NULL " +
        "         AND Returned_time IS NOT NULL " +
        "         AND Returned_time <> '' " +
        "         AND status IN ('RENTED','BOOKED') " +
        "   ) x " +
        "   WHERE due_dt IS NOT NULL " +
        ") d ON d.customer_id = c.customer_id " +
        "SET c.due_hours  = d.dh, " +
        "    c.due_amount = d.dh * 100, " +
        "    c.total_pay  = CASE " +
        "        WHEN c.payment_Type = 'FULL PAYMENT' THEN (d.dh * 100) " +
        "        WHEN c.payment_Type = 'PARTIAL PAYMENT' THEN (IFNULL(c.total_payment,0) + (d.dh * 100)) " +
        "        ELSE (d.dh * 100) " +
        "    END";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.executeUpdate();
    }
}

public List<ModelCustomer1> searchDueAfterOneHour(String keyword) throws SQLException {

    refreshDueRealtime();

    List<ModelCustomer1> list = new ArrayList<>();

    String sql =
        "SELECT *, FLOOR(IFNULL(due_hours,0) / 24) AS due_days " +
        "FROM customer " +
        "WHERE IFNULL(due_hours,0) >= 1 " +
        "  AND status IN ('RENTED','BOOKED') " +
        "  AND ( " +
        "       CAST(customer_id AS CHAR) LIKE ? OR " +
        "       customer_firstname LIKE ? OR " +
        "       customer_lastname LIKE ? OR " +
        "       phone_number LIKE ? OR " +
        "       vehicle_name LIKE ? OR " +
        "       payment_Type LIKE ? " +
        "  ) " +
        "ORDER BY Return_Date ASC";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        for (int i = 1; i <= 6; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

ModelCustomer1 m = new ModelCustomer1(
    rs.getInt("customer_id"),
    rs.getString("customer_firstname"),
    rs.getString("customer_lastname"),
    rs.getInt("Age"),
    rs.getString("phone_number"),
    rs.getString("primary_address"),
    rs.getDate("transaction_Date"),
    rs.getString("payment_Type"),
    rs.getDouble("total_payment"),
    rs.getDate("Pickup_Date"),
    rs.getString("Pickup_time"),
    rs.getString("Pickup_Location"),
    rs.getDate("Return_Date"),
    rs.getString("Returned_time"),
    rs.getString("Return_Location"),
    rs.getString("vehicle_type"),
    rs.getString("vehicle_name"),
    rs.getString("status"),

    // ✅ correct order
    rs.getString("driving_license"),
    rs.getString("driving_back"),
    rs.getString("transmission_type"),
    rs.getString("plate_number"),
    rs.getString("license_number"),
    rs.getDate("expire_date"),
    rs.getString("agency_code"),
    rs.getString("dl_codes"),
    rs.getString("vehicle_category"),
    rs.getString("conditions")
);


                m.setDueHours(rs.getInt("due_hours"));
                m.setDueDays(rs.getInt("due_days"));
                m.setDueAmount(rs.getDouble("due_amount"));
                m.setTotalPay(rs.getDouble("total_pay"));

                list.add(m);
            }
        }
    }

    return list;
}

public void settleDueAsFullPayment(int customerId, double newTotalPayment) throws SQLException {
    String sql =
        "UPDATE customer " +
        "SET total_payment = ?, " +
        "    due_hours = 0, " +
        "    due_amount = 0, " +
        "    total_pay = 0, " +
        "    payment_Type = 'FULL PAYMENT', " +
        "    status = 'RETURNED' " +   // IMPORTANT: must NOT be RENTED/BOOKED
        "WHERE customer_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setDouble(1, newTotalPayment);
        ps.setInt(2, customerId);
        ps.executeUpdate();
    }
}

public boolean isCarActive(String carName) throws SQLException {
    String sql = "SELECT COUNT(*) FROM customer " +
                 "WHERE vehicle_name = ? AND status IN ('RENTED','BOOKED')";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, carName);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}

// Returns "RENTED" or "BOOKED" if the car is currently not available.
// Returns null if available (no active record found).
public String getActiveStatusByCarName(String carName) throws SQLException {
    String sql =
        "SELECT status " +
        "FROM customer " +
        "WHERE vehicle_name = ? AND status IN ('RENTED','BOOKED') " +
        "ORDER BY transaction_Date DESC, customer_id DESC " +
        "LIMIT 1";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, carName);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("status"); // RENTED or BOOKED
            }
        }
    }
    return null; // available (GREEN)
}

public void updateLicenseImages(int customerId, byte[] front, byte[] back, boolean deleteFront, boolean deleteBack) throws SQLException {
    String sql =
        "UPDATE customer SET " +
        "driving_license = CASE WHEN ? THEN NULL ELSE COALESCE(?, driving_license) END, " +
        "driving_back    = CASE WHEN ? THEN NULL ELSE COALESCE(?, driving_back) END " +
        "WHERE customer_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setBoolean(1, deleteFront);
        if (front != null && front.length > 0) ps.setBytes(2, front);
        else ps.setNull(2, Types.BLOB);

        ps.setBoolean(3, deleteBack);
        if (back != null && back.length > 0) ps.setBytes(4, back);
        else ps.setNull(4, Types.BLOB);

        ps.setInt(5, customerId);
        ps.executeUpdate();
    }
}

// ✅ COUNT how many cars exist in car_added table
public int countCarsAdded() throws SQLException {
    String sql = "SELECT COUNT(*) FROM car_added";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1);
        }
    }

    return 0;
}

public List<ModelCustomer1> searchLicenseData(String keyword) throws SQLException {
    List<ModelCustomer1> list = new ArrayList<>();

    String sql =
        "SELECT * FROM customer WHERE " +
        "customer_firstname LIKE ? OR " +
        "customer_lastname LIKE ? OR " +
        "CAST(Age AS CHAR) LIKE ? OR " +
        "phone_number LIKE ? OR " +
        "primary_address LIKE ? OR " +
        "vehicle_name LIKE ? OR " +
        "vehicle_type LIKE ? OR " +
        "transmission_type LIKE ? OR " +
        "CAST(expire_date AS CHAR) LIKE ? OR " +
        "agency_code LIKE ? OR " +
        "dl_codes LIKE ? OR " +
        "vehicle_category LIKE ? OR " +
        "conditions LIKE ? OR " +
        "CAST(transaction_Date AS CHAR) LIKE ? " +
        "ORDER BY customer_Id ASC";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        for (int i = 1; i <= 14; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ModelCustomer1(
                    rs.getInt("customer_Id"),
                    rs.getString("customer_firstname"),
                    rs.getString("customer_lastname"),
                    rs.getInt("Age"),
                    rs.getString("phone_number"),
                    rs.getString("primary_address"),
                    rs.getDate("transaction_Date"),
                    rs.getString("payment_Type"),
                    rs.getDouble("total_payment"),
                    rs.getDate("Pickup_Date"),
                    rs.getString("Pickup_time"),
                    rs.getString("Pickup_Location"),
                    rs.getDate("Return_Date"),
                    rs.getString("Returned_time"),
                    rs.getString("Return_Location"),
                    rs.getString("vehicle_type"),
                    rs.getString("vehicle_name"),
                    rs.getString("status"),
                    rs.getString("driving_license"),
                    rs.getString("driving_back"),
                    rs.getString("transmission_type"),
                    rs.getString("plate_number"),
                    rs.getString("license_number"),
                    rs.getDate("expire_date"),
                    rs.getString("agency_code"),
                    rs.getString("dl_codes"),
                    rs.getString("vehicle_category"),
                    rs.getString("conditions")
                ));
            }
        }
    }

    return list;
}

}
