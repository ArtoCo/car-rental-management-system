package MUL;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import popup.ADD;
import popup.InputRules;

public class AddedCarInventorySync {

    private static final String URL = config.AppConfig.getDatabaseUrl();
    private static final String USER = config.AppConfig.getDatabaseUser();
    private static final String PASSWORD = config.AppConfig.getDatabasePassword();

    public static void saveAddedCarToBothTables(ADD addForm) throws Exception {
        String carName = safe(addForm.getCarName());
        String carType = safe(addForm.getCarType());
        String plateNumber = InputRules.normalizePlate(addForm.getPlateNumber());
        String fuel = safe(addForm.getFuelType());
        String seats = safe(addForm.getCarSeats());
        String dailyRent = safe(addForm.getCarRent());
        String model = safe(addForm.getCarModel());
        String ratings = safe(addForm.getCarRatings());
        String transmission = safe(addForm.gettransmissiontype());
        String imagePath = safe(addForm.getSelectedImagePath());
        String carBrand = safe(addForm.getCarBrand());

        validateRequiredFields(carName, plateNumber, carBrand);
        trace(carName, carType, plateNumber, fuel, seats, dailyRent, model, ratings, transmission, imagePath);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            try {
                if (plateExists(conn, "cars_info", plateNumber)
                        || plateExists(conn, "car_added", plateNumber)) {
                    throw new Exception("Plate number already exists. No car was saved: " + plateNumber);
                }

                insertCarAdded(conn, carBrand, carName, carType, plateNumber, fuel,
                        seats, dailyRent, model, ratings, transmission, imagePath);
                insertNewCar(conn, carName, carType, plateNumber, fuel, seats,
                        dailyRent, model, ratings, transmission, imagePath);
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public static void saveAddedCarToCarsInfo(ADD addForm) throws Exception {

        String carName = safe(addForm.getCarName());
        String carType = safe(addForm.getCarType());
        String plateNumber = InputRules.normalizePlate(addForm.getPlateNumber());
        String fuel = safe(addForm.getFuelType());
        String seats = safe(addForm.getCarSeats());
        String dailyRent = safe(addForm.getCarRent());
        String model = safe(addForm.getCarModel());
        String ratings = safe(addForm.getCarRatings());
        String transmission = safe(addForm.gettransmissiontype());
        String imagePath = safe(addForm.getSelectedImagePath());

        trace(carName, carType, plateNumber, fuel, seats, dailyRent, model, ratings, transmission, imagePath);

        validateRequiredFields(carName, plateNumber, safe(addForm.getCarBrand()));

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (plateExists(conn, "cars_info", plateNumber)) {
                throw new Exception("Plate number already exists in cars_info: " + plateNumber);
            }

            insertNewCar(conn, carName, carType, plateNumber, fuel, seats,
                    dailyRent, model, ratings, transmission, imagePath);
        }
    }

    private static void validateRequiredFields(String carName, String plateNumber, String carBrand) throws Exception {
        if (carName.isEmpty()) {
            throw new Exception("Car name is empty. No car was saved.");
        }
        if (plateNumber.isEmpty()) {
            throw new Exception("Plate number is empty. No car was saved.");
        }
        if (!InputRules.isValidPlate(plateNumber)) {
            throw new Exception("Plate number can only contain letters, numbers, spaces, and hyphen (-).");
        }
        if (carBrand.isEmpty()) {
            throw new Exception("Car brand is empty. No car was saved.");
        }
    }

    private static boolean plateExists(Connection conn, String tableName, String plateNumber) throws Exception {
        if (!"cars_info".equals(tableName) && !"car_added".equals(tableName)) {
            throw new IllegalArgumentException("Unsupported inventory table: " + tableName);
        }

        String sql = "SELECT COUNT(*) FROM " + tableName
                + " WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plateNumber);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private static void insertCarAdded(
            Connection conn,
            String carBrand,
            String carName,
            String carType,
            String plateNumber,
            String fuel,
            String seats,
            String dailyRent,
            String model,
            String ratings,
            String transmission,
            String imagePath
    ) throws Exception {
        String addedDate = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH))
                .toUpperCase();

        String sql =
                "INSERT INTO car_added " +
                "(CAR_BRAND, CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, " +
                "CARS_SEATS, CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, ADDED_DATE, " +
                "CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carBrand);
            ps.setString(2, carName);
            ps.setString(3, carType);
            ps.setString(4, plateNumber);
            ps.setString(5, fuel);
            ps.setString(6, seats);
            ps.setString(7, dailyRent);
            ps.setString(8, model);
            ps.setString(9, ratings);
            ps.setString(10, addedDate);
            setImage(ps, 11, imagePath);
            ps.setString(12, transmission);
            ps.executeUpdate();
        }
    }

    private static void insertNewCar(
            Connection conn,
            String carName,
            String carType,
            String plateNumber,
            String fuel,
            String seats,
            String dailyRent,
            String model,
            String ratings,
            String transmission,
            String imagePath
    ) throws Exception {

        String sql =
                "INSERT INTO cars_info " +
                "(CARS_NAME, CARS_TYPE, CARS_PLATENUMBER, CARS_FUEL, CARS_SEATS, " +
                "CARS_DAILYRENT, CARS_MODEL, CARS_RATINGS, CARS_IMAGE, CAR_TRANSMISSION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carName);
            ps.setString(2, carType);
            ps.setString(3, plateNumber);
            ps.setString(4, fuel);
            ps.setString(5, seats);
            ps.setString(6, dailyRent);
            ps.setString(7, model);
            ps.setString(8, ratings);
            setImage(ps, 9, imagePath);
            ps.setString(10, transmission);

            int rows = ps.executeUpdate();
            System.out.println("INSERTED new added car to cars_info: " + carName + " | rows=" + rows);
        }
    }

    private static void setImage(PreparedStatement ps, int index, String imagePath) throws Exception {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            ps.setNull(index, Types.BLOB);
            return;
        }

        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            ps.setNull(index, Types.BLOB);
            return;
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        ps.setBytes(index, imageBytes);
    }

    public static void deleteAddedCarFromCarsInfoByPlate(String plateNumber) throws Exception {

        plateNumber = safe(plateNumber);

        if (plateNumber.isEmpty()) {
            throw new Exception("Plate number is empty. Cannot delete from cars_info.");
        }

        String sql = "DELETE FROM cars_info WHERE TRIM(CARS_PLATENUMBER) = TRIM(?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plateNumber);

            int deleted = ps.executeUpdate();

            System.out.println("Deleted from cars_info by plate: " + plateNumber);
            System.out.println("Rows deleted: " + deleted);
        }
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private static void trace(
            String carName,
            String carType,
            String plateNumber,
            String fuel,
            String seats,
            String dailyRent,
            String model,
            String ratings,
            String transmission,
            String imagePath
    ) {
        System.out.println("====================================");
        System.out.println("TRACE ADDED CAR TO cars_info");
        System.out.println("CARS_NAME        = " + carName);
        System.out.println("CARS_TYPE        = " + carType);
        System.out.println("CARS_PLATENUMBER = " + plateNumber);
        System.out.println("CARS_FUEL        = " + fuel);
        System.out.println("CARS_SEATS       = " + seats);
        System.out.println("CARS_DAILYRENT   = " + dailyRent);
        System.out.println("CARS_MODEL       = " + model);
        System.out.println("CARS_RATINGS     = " + ratings);
        System.out.println("CAR_TRANSMISSION = " + transmission);
        System.out.println("IMAGE PATH       = " + imagePath);
        System.out.println("====================================");
    }
}
