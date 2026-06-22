/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employees;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author ARTO
 */
public class ServiceEmployee {
    
    public List<ModelEmployee> getAll() throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;
    try {
        con = DatabaseConnection.getInstance().createConnection();
        // Corrected SQL query
        p = con.prepareStatement("SELECT * FROM employee ");
        r = p.executeQuery();
        List<ModelEmployee> list = new ArrayList<>();
        while (r.next()) {
            int employeeId = r.getInt("employee_id");
            String FIRSTNAME = r.getString("employee_firstname");
            String LASTNAME = r.getString("employee_lastname");
            int AGE = r.getInt("Age");
            String PHONE_NUMBER = r.getString("Phone_Number");
            String Primary_Address = r.getString("Primary_Address");
            Date DATE = r.getDate("Date");
            double salary = r.getDouble("Salary");
            ModelProfile profile = new ModelProfile(r.getBytes("profile"));
            String PositionsName = r.getString("positions_name");
       
            list.add(new ModelEmployee(employeeId, FIRSTNAME, LASTNAME, AGE, PHONE_NUMBER, Primary_Address, DATE, salary, profile, new ModelPositions(PositionsName)));
        }
        return list;
    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }
}
   public List<ModelEmployee> search(String search) throws SQLException {
    Connection con = null;
    PreparedStatement p = null;
    ResultSet r = null;
    try {
        con = DatabaseConnection.getInstance().createConnection();
        // Corrected SQL query to use SELECT instead of UPDATE
        String sql = "SELECT employee_id, employee_firstname, employee_lastname, Age, Phone_Number, Primary_Address, Date, Salary, profile, positions_name " +
                     "FROM employee " +
                     "WHERE employee_firstname LIKE ? " +
                     "OR employee_lastname LIKE ? " +
                     "OR Age LIKE ?" +
                     "OR Phone_Number LIKE ? " +
                     "OR Primary_Address LIKE ? " +
                     "OR positions_name LIKE ?";
        p = con.prepareStatement(sql);
        p.setString(1, "%" + search + "%");
        p.setString(2, "%" + search + "%");
        p.setString(3, "%" + search + "%");
        p.setString(4, "%" + search + "%");
        p.setString(5, "%" + search + "%");
        p.setString(6, "%" + search + "%");
        r = p.executeQuery();
        
        List<ModelEmployee> list = new ArrayList<>();
        while (r.next()) {
            int employeeId = r.getInt("employee_id");
            String FIRSTNAME = r.getString("employee_firstname");
            String LASTNAME = r.getString("employee_lastname");
            int AGE = r.getInt("Age");
            String PHONE_NUMBER = r.getString("Phone_Number");
            String Primary_Address = r.getString("Primary_Address");
            Date DATE = r.getDate("Date");
            double salary = r.getDouble("Salary");
            ModelProfile profile = new ModelProfile(r.getBytes("profile"));
            String PositionsName = r.getString("positions_name");
            
            list.add(new ModelEmployee(employeeId, FIRSTNAME, LASTNAME, AGE, PHONE_NUMBER, Primary_Address, DATE, salary, profile, new ModelPositions(PositionsName)));
        }
        return list;
    } finally {
        DatabaseConnection.getInstance().close(r, p, con);
    }
}

    public void create(ModelEmployee data) throws SQLException, IOException {
    Connection con = null;
    PreparedStatement p = null;
    try {
        con = DatabaseConnection.getInstance().createConnection();
        p = con.prepareStatement("INSERT INTO employee (employee_firstname, employee_lastname, Age, Phone_Number, Primary_Address, Date, Salary, positions_name, profile) VALUES (?,?,?,?,?,?,?,?,?)");
        
        p.setString(1, data.getFirstname());
        p.setString(2, data.getLastname());
        p.setInt(3, data.getAge());
        p.setString(4, data.getPhoneNumber());
        p.setString(5, data.getPrimary_Address());
        p.setDate(6, data.getDate());
        p.setDouble(7, data.getSalary());

        // Handle potential null values
        if (data.getPosition()!= null) {
            p.setString(8, data.getPosition().getPositionsName());
        } else {
            throw new SQLException("Position cannot be null");
        }
        if (data.getProfile() != null && data.getProfile().getPath() != null) {
        p.setBytes(9, getByteImage(data.getProfile().getPath()));
        } else {
        p.setBytes(9, null);
        }

        
        p.execute();
    } finally {
        DatabaseConnection.getInstance().close(p, con);
    }
}
    public void edit(ModelEmployee data) throws SQLException, IOException {
    Connection con = null;
    PreparedStatement p = null;
    try {
        // Check if the profile needs to be updated
        boolean isEditProfile = data.getProfile() != null && data.getProfile().getPath() != null;

        // Define the base SQL query and adjust it based on whether profile needs to be updated
        String sql = isEditProfile
                ? "UPDATE employee SET employee_firstname=?, employee_lastname=?, Age=?, Phone_Number=?, Primary_Address=?, Date=?, Salary=?, positions_name=?, profile=? WHERE employee_id=? LIMIT 1"
                : "UPDATE employee SET employee_firstname=?, employee_lastname=?, Age=?, Phone_Number=?, Primary_Address=?, Date=?, Salary=?, positions_name=? WHERE employee_id=? LIMIT 1";
        
        // Establish the connection
        con = DatabaseConnection.getInstance().createConnection();
        p = con.prepareStatement(sql);

        // Set the parameters for the SQL query (same for both queries up to position_name)
        p.setString(1, data.getFirstname());
        p.setString(2, data.getLastname());
        p.setInt(3, data.getAge());
        p.setString(4, data.getPhoneNumber());
        p.setString(5, data.getPrimary_Address());
        p.setDate(6, data.getDate());
        p.setDouble(7, data.getSalary());
        p.setString(8, data.getPosition().getPositionsName());
        
        // Handle profile image only if it needs to be updated
        if (isEditProfile) {
            p.setBytes(9, getByteImage(data.getProfile().getPath())); // Set the new image bytes
            p.setInt(10, data.getEmployeeId()); // Set employee_id as the 10th parameter
        } else {
            p.setInt(9, data.getEmployeeId()); // Set employee_id as the 9th parameter when profile isn't updated
        }
        
        // Execute the update query
        p.executeUpdate();
    } finally {
        DatabaseConnection.getInstance().close(p, con);
    }
}
    

    public void delete(int id) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("delete from employee where employee_id=? limit 1");
            p.setInt(1, id);
            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public ServicePositions getServicePositions() {
        if (servicePositions == null) {
            servicePositions = new ServicePositions();
        }
        return servicePositions;
    }

    private byte[] getByteImage(File file) throws IOException {
    if (file == null || !file.exists()) {
        return null; // Avoid processing if the file is null or doesn't exist
    }

    BufferedImage image = Thumbnails.of(file)
         .width(500)
         .outputQuality(0.7f)
         .asBufferedImage();
    ByteArrayOutputStream out = null;
    try {
        out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    } finally {
        if (out != null) {
            out.close();
        }
    }
}


    private ServicePositions servicePositions;
}