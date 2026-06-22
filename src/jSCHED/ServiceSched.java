/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jSCHED;

import customers.DatabaseConnection;
import customers.ModelCustomer1;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ArtoCO
 */
public class ServiceSched {
    
    // ✅ Retrieve all customers from database
    public List<ModelSched> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        List<ModelSched> list = new ArrayList<>();
        
        try {
            con = DatabaseConnection.getConnection();  // Safe connection
            p = con.prepareStatement("SELECT * FROM check_ins");
            r = p.executeQuery();

            while (r.next()) {
                int Ids = r.getInt("id");
                String firstname = r.getString("firstname");
                String lastname = r.getString("lastname");
                String phone = r.getString("phoneNO");
                String carsname = r.getString("carsNAME");
                String checkin = r.getString("check_ins_TIME");
                String paytype = r.getString("Payments");
                String totalPayment = r.getString("Total_payments");   

                list.add(new ModelSched(Ids, firstname, lastname, phone, carsname, checkin, paytype, totalPayment));
            }

        } finally {
            DatabaseConnection.getInstance().close(r, p, con); // Safely close
        }
        return list;
    }
    
    // ✅ Insert new customer into the database
    public void create(ModelSched data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;

        try {
            con = DatabaseConnection.getConnection();  // Safe connection
            p = con.prepareStatement(
                "INSERT INTO check_ins (firstname, lastname, phoneNO, carsNAME, check_ins_TIME, Payments, Total_payments) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            p.setString(1, data.getfirstname());
            p.setString(2, data.getlastname());
            p.setString(3, data.getphoneNO());
            p.setString(4, data.getcarsNAME());
            p.setString(5, data.getcheckins());
            p.setString(6, data.getPayments());
            p.setString(7, data.getTotal_payments()); // new line


            p.executeUpdate(); // Use executeUpdate for INSERT
        } finally {
            DatabaseConnection.getInstance().close(p, con); // Safely close
        }
    }
    
    
    public List<ModelSched> search(String search) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;
    List<ModelSched> list = new ArrayList<>();

    try {
        con = DatabaseConnection.getInstance().createConnection();
        String sql = "SELECT * FROM check_ins WHERE " +
                     "CAST(id AS CHAR) LIKE ? OR " +
                     "firstname LIKE ? OR " +
                     "lastname LIKE ? OR " +
                     "phoneNO LIKE ? OR " +
                     "carsNAME LIKE ? OR " +
                     "check_ins_TIME LIKE ? OR " +
                     "Payments LIKE ? OR " +
                     "Total_payments LIKE ?";

        p = con.prepareStatement(sql);

        for (int i = 1; i <= 8; i++) {
            p.setString(i, "%" + search + "%");
        }

        r = p.executeQuery();

        while (r.next()) {
            list.add(new ModelSched(
                r.getInt("id"),
                r.getString("firstname"),
                r.getString("lastname"),
                r.getString("phoneNO"),
                r.getString("carsNAME"),
                r.getString("check_ins_TIME"),              
                r.getString("Payments"),
                r.getString("Total_payments")
            ));
        }

    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }

    return list;
}

    
    
public ModelSched findByPhoneNumber(String phoneNumber) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement("SELECT * FROM check_ins WHERE phoneNO = ?");
        p.setString(1, phoneNumber);
        r = p.executeQuery();

        if (r.next()) {
            return new ModelSched(
                r.getInt("id"),
                r.getString("firstname"),
                r.getString("lastname"),
                r.getString("phoneNO"),
                r.getString("carsNAME"),
                r.getString("check_ins_TIME"),
                r.getString("Payments"),
                r.getString("Total_payments")

            );
        }
        return null;
    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }
}

    public ModelSched findByIdAndPhoneNumber(int id, String phoneNumber) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement p = con.prepareStatement(
                     "SELECT * FROM check_ins WHERE id = ? AND phoneNO = ?")) {
            p.setInt(1, id);
            p.setString(2, phoneNumber);

            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) {
                    return null;
                }

                ModelSched result = new ModelSched(
                    r.getInt("id"),
                    r.getString("firstname"),
                    r.getString("lastname"),
                    r.getString("phoneNO"),
                    r.getString("carsNAME"),
                    r.getString("check_ins_TIME"),
                    r.getString("Payments"),
                    r.getString("Total_payments")
                );

                if (r.next()) {
                    throw new SQLException("Multiple check-in records matched ID and phone number.");
                }
                return result;
            }
        }
    }
    
public void deleteByPhoneNumber(String phoneNumber) throws SQLException {
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement p = con.prepareStatement("DELETE FROM check_ins WHERE phoneNO = ?")) {
        p.setString(1, phoneNumber);
        p.executeUpdate();
    }
}

    public boolean deleteByIdAndPhoneNumber(int id, String phoneNumber) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement p = con.prepareStatement(
                     "DELETE FROM check_ins WHERE id = ? AND phoneNO = ?")) {
            p.setInt(1, id);
            p.setString(2, phoneNumber);
            return p.executeUpdate() == 1;
        }
    }
    
    public void restore(ModelSched data) throws SQLException, IOException {
    Connection con = null;
    PreparedStatement p = null;

    try {
        con = DatabaseConnection.getConnection();  // Use safe connection
        p = con.prepareStatement(
            "INSERT INTO check_ins (firstname, lastname, phoneNO, carsNAME, check_ins_TIME, Payments, Total_payments) VALUES (?, ?, ?, ?, ?, ?, ?)"
        );

        p.setString(1, data.getfirstname());
        p.setString(2, data.getlastname());
        p.setString(3, data.getphoneNO());
        p.setString(4, data.getcarsNAME());
        p.setString(5, data.getcheckins());
        p.setString(6, data.getPayments());
        p.setString(7, data.getTotal_payments());

        p.executeUpdate(); // Perform INSERT
    } finally {
        DatabaseConnection.getInstance().close(p, con);
    }
}

    public void update(ModelSched data) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement(
            "UPDATE check_ins SET " +
            "firstname = ?, " +
            "lastname = ?, " +
            "phoneNO = ?, " +
            "carsNAME = ?, " +
            "check_ins_TIME = ?, " +
            "Payments = ?, " +
            "Total_payments = ? " +
            "WHERE id = ?"
        );

        p.setString(1, data.getfirstname());
        p.setString(2, data.getlastname());
        p.setString(3, data.getphoneNO());
        p.setString(4, data.getcarsNAME());
        p.setString(5, data.getcheckins());
        p.setString(6, data.getPayments());
        p.setString(7, data.getTotal_payments());
        p.setInt(8, data.getid()); // Make sure your model has the correct ID

        p.executeUpdate();

    } finally {
        DatabaseConnection.getInstance().close(p, con);
    }
}

    
    public void edit(ModelSched data) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement(
            "UPDATE check_ins SET " +
            "firstname = ?, " +
            "lastname = ?, " +
            "phoneNO = ?, " +
            "check_ins_TIME = ?, " +
            "Payments = ?, " +
            "Total_payments = ? " +
            "WHERE id = ?"
        );

        p.setString(1, data.getfirstname());
        p.setString(2, data.getlastname());
        p.setString(3, data.getphoneNO());
        p.setString(4, data.getcheckins());
        p.setString(5, data.getPayments());
        p.setString(6, data.getTotal_payments());
        p.setInt(7, data.getid());

        p.executeUpdate();

    } finally {
        DatabaseConnection.getInstance().close(p, con);
    }
}


    
    public ModelSched getById(int id) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;

    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement("SELECT * FROM check_ins WHERE id = ?");
        p.setInt(1, id);
        r = p.executeQuery();

        if (r.next()) {
            return new ModelSched(
                r.getInt("id"),
                r.getString("firstname"),
                r.getString("lastname"),
                r.getString("phoneNO"),
                r.getString("carsNAME"),
                r.getString("check_ins_TIME"),
                r.getString("Payments"),
                r.getString("Total_payments")
            );
        }

        return null;

    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }
}
    
    public int findSchedIdByPhone(String phone) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet rs = null;
    try {
        con = DatabaseConnection.getConnection();
        p = con.prepareStatement("SELECT id FROM check_ins WHERE phoneNO = ?");
        p.setString(1, phone);
        rs = p.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        throw new SQLException("No check-in record found for phone: " + phone);
    } finally {
        DatabaseConnection.getInstance().close(rs, p, con);
    }
}



}
