/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
public class ModelDeletedCar {
    private String name, type, rating, fuelType, seats, dailyRent, model;

    public ModelDeletedCar(String name, String type, String rating, String fuelType, String seats, String dailyRent, String model) {
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.fuelType = fuelType;
        this.seats = seats;
        this.dailyRent = dailyRent;
        this.model = model;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getRating() { return rating; }
    public String getFuelType() { return fuelType; }
    public String getSeats() { return seats; }
    public String getDailyRent() { return dailyRent; }
    public String getModel() { return model; }
}

