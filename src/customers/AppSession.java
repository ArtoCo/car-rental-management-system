/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customers;

import javax.swing.Icon;
import popup.CustomerInfo;

/**
 *
 * @author ArtoCO
 */
public class AppSession {

    // =========================
    // BASIC CUSTOMER INFO
    // =========================
    private static Integer customerId;              // ✅ added (needed for FULL_PAYPAL save)
    private static String customerName;
    private static String customerAddress;
    private static String customerPhone;

    // =========================
    // CAR / RENT INFO
    // =========================
    private static String customerCarNameType;
    private static String plateno;
    private static String checkInDate;
    private static String checkOutDate;

    // =========================
    // PAYMENT / DUE INFO (store as String for easy display)
    // =========================
    private static String originalBalance;          // ✅ added (old remaining balance / total_payment)
    private static int dueHours;                    // ✅ added
    private static String dueAmount;                // ✅ added

    // =========================
    // OTHER EXISTING FIELDS
    // =========================
    private static String textFromPanelSlide;
    private static boolean bmwLabelOpaque;
    private static String carModel;
    private static String model;

    private static String basePay;
    private static String totalPayment;
    private static String vat;
    private static String driver;
    private static String totalamount;
    private static String totalparamount;
    private static String trandate;

    private static String invoiceDate;
    private static String invoiceCarName;

    // for receipt
    private static CustomerInfo customerInfo;

    // icon transfer
    private static Icon transferIcon;

    // transmission type
    private static String transmission_type;

    // =========================
    // NEW: CUSTOMER ID
    // =========================
    public static void setCustomerId(Integer id) {
        customerId = id;
    }

    public static Integer getCustomerId() {
        return customerId;
    }

    // =========================
    // CUSTOMER NAME / ADDRESS / PHONE
    // =========================
    public static void setCustomerName(String firstname, String lastname) {
        customerName = firstname + " " + lastname;
    }

    public static String getCustomerName() {
        return customerName;
    }

    public static void setCustomerAddress(String address) {
        customerAddress = address;
    }

    public static String getCustomerAddress() {
        return customerAddress;
    }

    public static void setCustomerPhone(String phone) {
        customerPhone = phone;
    }

    public static String getCustomerPhone() {
        return customerPhone;
    }

    // =========================
    // CAR NAME / TYPE
    // =========================
    public static void setCustomerCarNameType(String carName, String carType) {
        customerCarNameType = carName + " " + carType;
    }

    public static String getCustomerCarNameType() {
        return customerCarNameType;
    }

    // =========================
    // CHECK IN / OUT DATE
    // =========================
    public static void setCheckInDate(String date) {
        checkInDate = date;
    }

    public static String getCheckInDate() {
        return checkInDate;
    }

    public static void setCheckOutDate(String date) {
        checkOutDate = date;
    }

    // ✅ keep your old typo getter for compatibility
    public static String getCheckOutate() {
        return checkOutDate;
    }

    // ✅ add correct getter (safe to use in new code)
    public static String getCheckOutDate() {
        return checkOutDate;
    }

    // =========================
    // PAYMENT / DUE (NEW)
    // =========================
    public static void setOriginalBalance(String amount) {
        originalBalance = amount;
    }

    public static String getOriginalBalance() {
        return originalBalance;
    }

    public static void setDueHours(int hours) {
        dueHours = hours;
    }

    public static int getDueHours() {
        return dueHours;
    }

    public static void setDueAmount(String amount) {
        dueAmount = amount;
    }

    public static String getDueAmount() {
        return dueAmount;
    }

    // =========================
    // YOUR EXISTING METHODS (UNCHANGED)
    // =========================
    public static void setBase(String Base) {
        basePay = Base;
    }

    public static String getBase() {
        return basePay;
    }

    public static void setTotalPayment(String total) {
        totalPayment = total;
    }

    public static String getTotalPayment() {
        return totalPayment;
    }

    public static void setvat(String vats) {
        vat = vats;
    }

    public static String getvat() {
        return vat;
    }

    public static void setdriver(String drivers) {
        driver = drivers;
    }

    public static String getdriver() {
        return driver;
    }

    public static void settotalamount(String totalamounts) {
        totalamount = totalamounts;
    }

    public static String gettotalamount() {
        return totalamount;
    }

    public static void settotalparamount(String totalparamounts) {
        totalparamount = totalparamounts;
    }

    public static String gettotalparamount() {
        return totalparamount;
    }

    public static void setplateno(String platenos) {
        plateno = platenos;
    }

    public static String getplateno() {
        return plateno;
    }

    public static void settrandate(String trandates) {
        trandate = trandates;
    }

    public static String gettrandate() {
        return trandate;
    }

    public static void setInvoiceDate(String date) {
        invoiceDate = date;
    }

    public static String getInvoiceDate() {
        return invoiceDate;
    }

    public static void setInvoiceCarName(String carName) {
        invoiceCarName = carName;
    }

    public static String getInvoiceCarName() {
        return invoiceCarName;
    }

    public static void setCustomerInfo(CustomerInfo info) {
        customerInfo = info;
    }

    public static CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public static void setTransferIcon(Icon icon) {
        transferIcon = icon;
    }

    public static Icon getTransferIcon() {
        return transferIcon;
    }

    public static void settransmission_type(String Transmission_type) {
        transmission_type = Transmission_type;
    }

    public static String getTransmission_type() {
        return transmission_type;
    }

    // =========================
    // SIMPLE CLEAR (TEMP SESSION)
    // =========================
    public static void clearPaymentSession() {
        customerId = null;
        customerName = null;
        customerAddress = null;
        customerPhone = null;

        customerCarNameType = null;
        plateno = null;
        checkInDate = null;
        checkOutDate = null;

        originalBalance = null;
        dueHours = 0;
        dueAmount = null;

        transferIcon = null;
        customerInfo = null;
    }
    

}