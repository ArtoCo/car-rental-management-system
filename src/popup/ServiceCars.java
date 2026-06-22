package popup;

import MUL.AddedCarInventorySync;

public class ServiceCars {

    public static void addCar(ADD carForm) throws Exception {
        AddedCarInventorySync.saveAddedCarToBothTables(carForm);
    }
}
