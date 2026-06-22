/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
public class ModelCarAdded {
    private String CARS_NAME;
    private String CARS_MODEL;
    private String CARS_RATINGS;
    private String CARS_DAILYRENT;
    private String CARS_PLATENUMBER;
    private String CARS_TYPE;
    private String CAR_SEATS;
    private String CAR_FUEL;
    private String ADDED_DATE;

    // Constructor
    public ModelCarAdded(String CARS_NAME, String CARS_MODEL, String CARS_RATINGS, String CARS_DAILYRENT,
                         String CARS_PLATENUMBER, String CARS_TYPE, String CAR_SEATS,
                         String CAR_FUEL, String ADDED_DATE) {
        this.CARS_NAME = CARS_NAME;
        this.CARS_MODEL = CARS_MODEL;
        this.CARS_RATINGS = CARS_RATINGS;
        this.CARS_DAILYRENT = CARS_DAILYRENT;
        this.CARS_PLATENUMBER = CARS_PLATENUMBER;
        this.CARS_TYPE = CARS_TYPE;
        this.CAR_SEATS = CAR_SEATS;
        this.CAR_FUEL = CAR_FUEL;
        this.ADDED_DATE = ADDED_DATE;
    }

    // Getters
    public String getCARS_NAME() { return CARS_NAME; }
    public String getCARS_MODEL() { return CARS_MODEL; }
    public String getCARS_RATINGS() { return CARS_RATINGS; }
    public String getCARS_DAILYRENT() { return CARS_DAILYRENT; }
    public String getCARS_PLATENUMBER() { return CARS_PLATENUMBER; }
    public String getCARS_TYPE() { return CARS_TYPE; }
    public String getCAR_SEATS() { return CAR_SEATS; }
    public String getCAR_FUEL_TYPE() { return CAR_FUEL; }
    public String getADDED_DATE() { return ADDED_DATE; }
}

