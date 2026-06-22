/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employees;

/**
 *
 * @author ArtoCO
 */
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import net.coobird.thumbnailator.geometry.Positions;

public class ModelEmployee {

    private int employeeId;
    private int age;
    private String phoneNumber;
    private String Primary_Address;
    private String firstname;
    private String lastname;
    private Date date;
    private double salary;
    private ModelProfile profile;
    private ModelPositions position;  
    
public ModelEmployee(int employeeId, String firstname, String lastname, int age, String phoneNumber, String Primary_Address, Date date, double salary, ModelProfile profile, ModelPositions position) {
        this.employeeId = employeeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.Primary_Address = Primary_Address;
        this.date = date;
        this.salary = salary;
        this.profile = profile;
        this.position = position;
       
    }
    // Getter and Setter methods
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrimary_Address() {
        return Primary_Address;
    }

    public void setPrimary_Addresss(String Primary_Address) {
        this.Primary_Address = Primary_Address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ModelProfile getProfile() {
        return profile;
    }

    public void setProfile(ModelProfile profile) {
        this.profile = profile;
    }

    public ModelPositions getPosition() {
        return position;
    }

    public void setPosition(ModelPositions position) {
        this.position = position;
    }
    

    // Converts the employee object to a row for displaying in a table
   public Object[] toTableRow(int rowNum) {
    DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    NumberFormat nf = new DecimalFormat("₱#,##0.##");
    return new Object[]{
        false,
        rowNum,
        this,
       
        age,                            // Age
        phoneNumber,                    // Phone number
        Primary_Address,                 // Primary address
        date == null ? "" : df.format(date),  // Date formatted
        nf.format(salary),              // Salary formatted
        position != null ? position.getPositionsName() : ""  // Position name
    };
}

    // Custom string representation
    @Override
    public String toString() {
        return firstname;
        
    }
    
}
