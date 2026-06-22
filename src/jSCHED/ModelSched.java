/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jSCHED;

import ADMIN.Customers;
import customers.ModelCustomer1;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author ArtoCO
 */
public class ModelSched {

    private int id;
    private String firstname;
    private String lastname;
    private String phoneNO;
    private String carsNAME;
    private String checkins;
    private String Payments;
    private String Total_payments;
    
    // Constructor
    public ModelSched(int id, String firstname, String lastname, String phoneNO, String carsNAME, String checkins, String Payments, String Total_payments) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNO = phoneNO;
        this.carsNAME = carsNAME;
        this.checkins = checkins;
        this.Payments = Payments;
        this.Total_payments = Total_payments;
  
}
    
// Getters and Setters
    public int getid() {
        return id;
    }

    public void setid(int Ids) {
        this.id = Ids;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstnames) {
        this.firstname = firstnames;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastnames) {
        this.lastname = lastnames;
    }

    public String getphoneNO() {
        return phoneNO;
    }

    public void setphoneNO(String ages) {
        this.phoneNO = ages;
    }

    public String getcarsNAME() {
        return carsNAME;
    }

    public void setcarsNAME(String carsNAMES) {
        this.carsNAME = carsNAMES;
    }

    public String getcheckins() {
        return checkins;
    }

    public void setcheckins(String checkinss) {
        this.checkins = checkinss;
    }

    public String getPayments() {
        return Payments;
    }

    public void setPayments(String Paymentss) {
        this.Payments = Paymentss;
    }
    
    public String getTotal_payments() {
        return Total_payments;
    }

    public void setTotal_payments(String Total_paymentss) {
        this.Total_payments = Total_paymentss;
    }
    
    // Convert to JTable row
    public Object[] toTableRow(int rowNum) {
    DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    NumberFormat pesoFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));  // ₱ currency format

    return new Object[] {
        false,                     
        id,                      
        firstname,                     
        lastname,                      
        phoneNO,                      
        carsNAME,                   
        checkins,               
        Payments, 
        Total_payments
    };
}
    // Custom string representation
    @Override
    public String toString() {
        return firstname;
    }
    
    
    @Deprecated
    public static List<ModelSched> getAllCustomersDirectly() throws SQLException {
        List<ModelSched> list = new ArrayList<>();
        Connection conn = Customers.getConnection(); // Use DatabaseConnection.getConnection() instead
        String sql = "SELECT * FROM check_ins";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int Ids = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String phone = rs.getString("phoneNO");
                String carsname = rs.getString("carsNAME");
                String checkin = rs.getString("check-ins-TIME");
                String paytype = rs.getString("Payments");
                String totalPayment = rs.getString("Total_payments");
 
            list.add(new ModelSched(Ids, firstname, lastname, phone, carsname, checkin, paytype, totalPayment));
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }

}
