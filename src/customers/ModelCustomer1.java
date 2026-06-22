/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customers;

/**
 *
 * @author ArtoCO
 */
import ADMIN.Customers;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModelCustomer1 {

    private int customerId;
    private String firstname;
    private String lastname;
    private int age;
    private String phoneNumber;
    private String primaryAddress;
    private Date transactionDate;
    private String paymentType;
    private double totalPayment;
    private Date InDate;
    private String checkInLocation;
    private Date CoutDate;
    private String checkOutLocation;
    private String CarType;
    private String CarName;
    private String Status;
    private String driving_license;
    private String driving_back;
    private String transmission_type;
    private String plate_number;
    private String Pickup_time;
    private String Returned_time;
    
    private String license_number;
    private Date expire_date;
    private String agency_code;
    private String dl_codes;
    private String vehicle_category;
    private String conditions;

    // Constructor
    public ModelCustomer1(int customerId, String firstname, String lastname, int age,
                          String phoneNumber, String primaryAddress, Date transactionDate, String paymentType, double totalPayment, Date InDate, String Pickup_time, String checkInLocation, Date CoutDate, 
                          String Returned_time, String checkOutLocation,  String CarType, String CarName, String Status, 
                          String driving_license, String driving_back, String transmission_type, String plate_number, String license_number, Date expire_date,
                          String agency_code, String dl_codes, String vehicle_category, String conditions) {
        
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.primaryAddress = primaryAddress;
        this.transactionDate = transactionDate;
        this.paymentType = paymentType;
        this.totalPayment = totalPayment;
        this.CoutDate = CoutDate;
        this.Returned_time = Returned_time;
        this.checkOutLocation = checkOutLocation;
        this.InDate = InDate;
        this.Pickup_time = Pickup_time;
        this.checkInLocation = checkInLocation;
        this.CarType = CarType;
        this.CarName = CarName;
        this.Status = Status;
        this.driving_license = driving_license;
        this.driving_back = driving_back;
        this.transmission_type = transmission_type;
        this.plate_number = plate_number;
        this.license_number = license_number;
        this.expire_date = expire_date;
        this.agency_code = agency_code;
        this.dl_codes = dl_codes;
        this.vehicle_category = vehicle_category;
        this.conditions = conditions;

    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstname1() {
        return firstname;
    }

    public void setFirstname1(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname1() {
        return lastname;
    }

    public void setLastname1(String lastname) {
        this.lastname = lastname;
    }

    public int getAge1() {
        return age;
    }

    public void setAge1(int age) {
        this.age = age;
    }

    public String getPhoneNumber1() {
        return phoneNumber;
    }

    public void setPhoneNumber1(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrimaryAddress1() {
        return primaryAddress;
    }

    public void setPrimaryAddress1(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    // Add getter and setter
    public String getPaymentType() {
    return paymentType;
    }

    public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
    }
    
    public double getTotalPayment() {
    return totalPayment;
    }
    
    public Date getOutDate() {
        return CoutDate;
    }

    public void setOutDate(Date CoutDate) {
        this.CoutDate = CoutDate;
    }
    
    public String getOutLocation() {
        return checkOutLocation;
    }

    public void setOutLocation(String checkOutLocation) {
        this.checkOutLocation = checkOutLocation;
    }
    
    public Date geInDate() {
        return InDate;
    }

    public void setInDate(Date InDate) {
        this.InDate = InDate;
    }
    
    public String getInLocation() {
        return checkInLocation;
    }

    public void setInLocation(String checkInLocation) {
        this.checkInLocation = checkInLocation;
    }
    
    public String getCarType() {
        return CarType;
    }

    public void setCarType(String CarType) {
        this.CarType = CarType;
    }
    
    public String getCarName() {
        return CarName;
    }

    public void setCarName(String CarName) {
        this.CarName = CarName;
    }
    
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public String getdriving_license(){
        return driving_license;
    }
    
    public void setdriving_license(String driving_license){
        this.driving_license = driving_license;
    }
    
    public String getdriving_back(){
        return driving_back;
    }
    
    public void setdriving_back(String driving_back){
        this.driving_back = driving_back;
    }
    
    public String gettransmission_type(){
        return transmission_type;
    }
    
    public void settransmission_type(String transmission_type){
        this.transmission_type = transmission_type;
    }
    
    public String getplate_number(){
        return plate_number;
    }
    
    public void setplate_number(String plate_number){
        this.plate_number = plate_number;
    }
    
    public String getPickup_time () {
        return Pickup_time;
    }
    
    public void setPickup_time(String Pickup_time){
        this.Pickup_time = Pickup_time;
    }
    
    public String getReturned_time () {
        return Returned_time;
    }
    
    public void setReturned_time(String Returned_time){
        this.Returned_time = Returned_time;
    }
    
private int dueHours;
private int dueDays;
private double dueAmount;
private double totalPay;

public int getDueHours() { return dueHours; }
public void setDueHours(int dueHours) { this.dueHours = dueHours; }

public int getDueDays() { return dueDays; }
public void setDueDays(int dueDays) { this.dueDays = dueDays; }

public double getDueAmount() { return dueAmount; }
public void setDueAmount(double dueAmount) { this.dueAmount = dueAmount; }

public double getTotalPay() { return totalPay; }
public void setTotalPay(double totalPay) { this.totalPay = totalPay; }


    public String getlicense_number () {
        return license_number;
    }
    
    public void setlicense_number(String license_number){
        this.license_number = license_number;
    }
    
        public Date getexpire_date() {
        return expire_date;
    }

    public void setexpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }
    
    public String getagency_code () {
        return agency_code;
    }
    
    public void setagency_code( String agency_code){
        this.agency_code = agency_code;
    }
    
    public String getdl_codes () {
        return dl_codes;
    }
    
    public void setdl_codes (String dl_codes){
        this.dl_codes = dl_codes;
    }
    
    public String getvehicle_category () {
        return vehicle_category;
    }
    
    public void setvehicle_category(String vehicle_category){
        this.vehicle_category = vehicle_category;
    }
    
    public String getconditions () {
        return conditions;
    }
    
    public void setconditions(String conditions){
        this.conditions = conditions;
    }


    
    
    // Convert to JTable row
    public Object[] toTableRow(int rowNum) {
    DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    NumberFormat pesoFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

    return new Object[] {
        false,                        // checkbox
        customerId,                   // REAL customer ID (IMPORTANT!)
        firstname,
        lastname,
        age,
        phoneNumber,
        primaryAddress,
        transactionDate == null ? "" : df.format(transactionDate),
        paymentType,
        pesoFormat.format(totalPayment),
        InDate == null ? "" : df.format(InDate),
        Pickup_time,
        checkInLocation,
        CoutDate == null ? "" : df.format(CoutDate),
        Returned_time,
        checkOutLocation,
        CarType,
        CarName,
        Status,
        driving_license,
        driving_back,
        transmission_type,
        plate_number,
        license_number,
        expire_date,
        agency_code,
        dl_codes,
        vehicle_category,
        conditions

    };
}

    // Custom string representation
    @Override
    public String toString() {
        return firstname;
    }

    // ⚠️ REMOVE THIS METHOD — violates separation of concerns!
    // Logic for getting customer list should go in ServiceCustomer.
    // However, if you must keep it here, you should refactor:

    /**
     * Deprecated: This method violates separation of concerns.
     * Please use ServiceCustomer.getAll() instead.
     */
    @Deprecated
    public static List<ModelCustomer1> getAllCustomersDirectly() throws SQLException {
        List<ModelCustomer1> list = new ArrayList<>();
        Connection conn = Customers.getConnection(); // Use DatabaseConnection.getConnection() instead
        String sql = "SELECT * FROM customer";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("customer_Id");
            String firstname = rs.getString("customer_firstname");
            String lastname = rs.getString("customer_lastname");
            int age = rs.getInt("Age");
            String phoneNumber = rs.getString("phone_number");
            String primaryAddress = rs.getString("primary_address");
            Date transactionDate = rs.getDate("transaction_Date");
            String paymentType = rs.getString("payment_Type");
            double totalPayment = rs.getDouble("total_payment");
            Date InDate = rs.getDate("check_In_Date");// 🆕 added
            String Pickup_time = rs.getString("Pickup_time");
            String checkInLocation = rs.getString("check_In_Location");
            Date CoutDate = rs.getDate("check_Out_Date");// 🆕 added
            String Returned_time = rs.getString("Returned_time");
            String checkOutLocation = rs.getString("check_Out_Location");
            String CarType = rs.getString("vehicle_type");
            String CarName = rs.getString("vehicle_name");
            String Status = rs.getString("status");
            String driving_license = rs.getString("driving_license");
            String driving_back = rs.getString("driving_back");
            String transmission_type = rs.getString ("transmission_type");
            String plate_number = rs.getString("plate_number");
            String license_number = rs.getString("license_number");
            Date expire_date = rs.getDate("expire_date");
            String agency_code = rs.getString("agency_code");
            String dl_codes = rs.getString("dl_codes");
            String vehicle_category = rs.getString("vehicle_category");
            String conditions = rs.getString("conditions");

            list.add(new ModelCustomer1(id, firstname, lastname, age, phoneNumber, primaryAddress, transactionDate, paymentType, totalPayment, InDate, Pickup_time, checkInLocation, CoutDate, Returned_time, checkOutLocation, CarType, CarName, Status, driving_license, driving_back, transmission_type, plate_number, license_number, expire_date, agency_code, dl_codes, vehicle_category, conditions));
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }
    
}


