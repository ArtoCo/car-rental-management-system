/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

import javax.swing.Icon;

/**
 *
 * @author ArtoCO
 */
public class CustomerInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String carname;
    private String cartype;
    private String carPlate;
    private String transDate;
    private String rates;
    private String model;
    private String seats;
    private String diesel;
    private String daily;
    
    private String formattedDate;
    private String formattedDateOut;
    private String Rentpay;
    private String RentInsurance;
    private String Vat;
    private String Driver;
    private String Fullpay;
    private String Partialpay;
    private String transmission_type;

    public CustomerInfo(String firstName, String lastName, String address, String phoneNumber, String carname, String cartype, String carPlate, String transDate, String rates, String model, String seats, String diesel, String daily, 
            String formattedDate, 
            String formattedDateOut, String Rentpay, String RentInsurance, String Vat, String Driver, String Fullpay, 
            String Partialpay, String transmission_type) {
        
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.carname = carname;
        this.cartype = cartype;
        this.carPlate = carPlate;
        this.transDate = transDate;
        this.rates = rates;
        this.model = model;
        this.seats = seats;
        this.diesel = diesel;
        this.daily = daily;
        
        
        this.formattedDate = formattedDate;
        this.formattedDateOut = formattedDateOut;
        this.Rentpay = Rentpay;
        this.RentInsurance = RentInsurance;
        this.Vat = Vat;
        this.Driver = Driver;
        this.Fullpay = Fullpay;
        this.Partialpay = Partialpay;
        this.transmission_type = transmission_type;
    }
    //CUSTOMER AND CAR INFO RECEIPT

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String Address) {
    this.address = Address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getFullcarinfo(){
        return carname + " " + cartype;
    }
    
    public void setcartype(String cartype) {
    this.cartype = cartype;
    }

    public String getcartype() {
    return cartype;
    }
    
    public void setcarname(String carname) {
    this.carname = carname;
    }

    public String getcarname() {
    return carname;
    }
    
    public String gettransDate() {
        return transDate;
    }

    public void settransDate(String transDate) {
        this.transDate = transDate;
    }
    
    
    public String getcarPlate() {
        return carPlate;
    }

    public void setcarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
    
    public String getrates() {
        return rates;
    }

    public void setrates(String rates) {
        this.rates = rates;
    }
    
    public String getmodel() {
        return model;
    }

    public void setmodel(String model) {
        this.model = model;
    }
    
    public String getseats() {
        return seats;
    }

    public void setseats(String seats) {
        this.seats = seats;
    }
    
    public String getdiesel() {
        return diesel;
    }

    public void setdiesel(String diesel) {
        this.diesel = diesel;
    }
    
    public String getdaily() {
        return daily;
    }

    public void setdaily(String daily) {
        this.daily = daily;
    }


    //RECEIPT DATES
    
    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    
    public String getFormattedOutDate() {
        return formattedDateOut;
    }

    public void setFormattedOutDate(String formattedDateOut) {
        this.formattedDateOut = formattedDateOut;
    }
    
    //PAYMENTS   
    public String getRentPay() {
    return Rentpay;
    }

    public void setRentPay(String Rentpay) {
    this.Rentpay = Rentpay;
    }
    
    public String getRentInsurance() {
    return RentInsurance;
    }

    public void setRentInsurance(String RentInsurance) {
    this.RentInsurance = RentInsurance;
    }
    
    public String getVat() {
    return Vat;
    }

    public void setVat(String Vat) {
    this.Vat = Vat;
    }
    
    public String getDriver() {
    return Driver;
    }

    public void setDriver(String Driver) {
    this.Driver = Driver;
    }
    
    public String getFullpay() {
    return Fullpay;
    }

    public void setFullpay(String Fullpay) {
    this.Fullpay = Fullpay;
    }
    
    public String getPartialpay() {
    return Partialpay;
    }

    public void setPartialpay(String Partialpay) {
    this.Partialpay = Partialpay;
    }
    
    public String gettransmission_type(){
    return transmission_type;
    }
    
    public void settransmission_type(String transmission_type){
    this.transmission_type = transmission_type;
    }
    
    

}
