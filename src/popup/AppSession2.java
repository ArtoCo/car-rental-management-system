/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

import javax.swing.Icon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AppSession2 {

    // ===== Receipt object (FULL_RECEIPT reads this) =====
    private static CustomerInfo customerInfo;

    // (Optional) keep these too, in case you want direct getters later
    private static Integer customerId;
    private static String customerName;
    private static String customerAddress;
    private static String customerPhone;

    private static String customerCarNameType;
    private static String plateno;
    private static String trandate;

    private static String basePay;        // rent pay
    private static String rentInsurance;  // insurance
    private static String vat;
    private static String driver;
    private static String fullpay;        // grand total

    private static Icon transferIcon;
    private static String transmission_type;
    
    private static String checkInDateFormatted; // "June 28 2025" (receipt format)
    private static String checkInTime;
    private static String checkInLocation;
    
private static final PropertyChangeSupport pcs = new PropertyChangeSupport(AppSession2.class);
private static String dailyPrice;
    

    // ===== Receipt CustomerInfo =====
    public static void setCustomerInfo(CustomerInfo info) {
        customerInfo = info;
    }

    public static CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public static void setCheckInDateFormatted(String d) { checkInDateFormatted = d; }
    public static String getCheckInDateFormatted() { return checkInDateFormatted; }

    public static void setCheckInTime(String t) { checkInTime = t; }
    public static String getCheckInTime() { return checkInTime; }

    public static void setCheckInLocation(String loc) { checkInLocation = loc; }
    public static String getCheckInLocation() { return checkInLocation; }

private static String checkOutDateFormatted; // "June 28 2025"
private static String checkOutTime;
private static String checkOutLocation;

public static void setCheckOutDateFormatted(String d) { checkOutDateFormatted = d; }
public static String getCheckOutDateFormatted() { return checkOutDateFormatted; }

public static void setCheckOutTime(String t) { checkOutTime = t; }
public static String getCheckOutTime() { return checkOutTime; }

public static void setCheckOutLocation(String loc) { checkOutLocation = loc; }
public static String getCheckOutLocation() { return checkOutLocation; }


    // ===== Basic info =====
    public static void setCustomerId(Integer id) { customerId = id; }
    public static Integer getCustomerId() { return customerId; }

    public static void setCustomerName(String firstname, String lastname) {
        customerName = firstname + " " + lastname;
    }
    public static String getCustomerName() { return customerName; }

    public static void setCustomerAddress(String address) { customerAddress = address; }
    public static String getCustomerAddress() { return customerAddress; }

    public static void setCustomerPhone(String phone) { customerPhone = phone; }
    public static String getCustomerPhone() { return customerPhone; }

    // ===== Car info =====
    public static void setCustomerCarNameType(String carName, String carType) {
        customerCarNameType = carName + " " + carType;
    }
    public static String getCustomerCarNameType() { return customerCarNameType; }

    public static void setplateno(String plate) { plateno = plate; }
    public static String getplateno() { return plateno; }

    public static void settrandate(String d) { trandate = d; }
    public static String gettrandate() { return trandate; }

    public static void settransmission_type(String t) { transmission_type = t; }
    public static String getTransmission_type() { return transmission_type; }

    // ===== Receipt amounts =====
    public static void setBase(String s) { basePay = s; }
    public static String getBase() { return basePay; }

    public static void setRentInsurance(String s) { rentInsurance = s; }
    public static String getRentInsurance() { return rentInsurance; }

    public static void setvat(String s) { vat = s; }
    public static String getvat() { return vat; }

    public static void setdriver(String s) { driver = s; }
    public static String getdriver() { return driver; }

    public static void setFullpay(String s) { fullpay = s; }
    public static String getFullpay() { return fullpay; }

    // ===== Icon transfer (optional) =====
    public static void setTransferIcon(Icon icon) { transferIcon = icon; }
    public static Icon getTransferIcon() { return transferIcon; }
    
    //daily rent price
    public static void setDailyPrice(String price) {
    String old = dailyPrice;
    dailyPrice = price;
    pcs.firePropertyChange("dailyPrice", old, price);
}

public static String getDailyPrice() {
    return dailyPrice;
}

public static void addDailyPriceListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener("dailyPrice", listener);
}

    // ===== Clear =====
    public static void clear() {
        BookingRouteState.clear();
        customerInfo = null;

        customerId = null;
        customerName = null;
        customerAddress = null;
        customerPhone = null;

        customerCarNameType = null;
        plateno = null;
        trandate = null;

        basePay = null;
        rentInsurance = null;
        vat = null;
        driver = null;
        fullpay = null;

        transferIcon = null;
        transmission_type = null;
        
        checkInDateFormatted = null;
        checkInTime = null;
        checkInLocation = null;
        
        checkOutDateFormatted = null;
        checkOutTime = null;
        checkOutLocation = null;
        
        dailyPrice = null;
        

    }
}
