/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package popup;

/**
 *
 * @author ArtoCO
 */
import java.io.*;

public class AppStorage {
    private static final String FILE_NAME = "label14.txt";

    public static void saveLabel14Count(int count) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(String.valueOf(count));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadLabel14Count() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
        String line = reader.readLine();
        int value = Integer.parseInt(line);
        return Math.max(0, value); // prevent negative or invalid
    } catch (Exception e) {
        return 96; // ⬅ Fallback to 96 on any failure
    }
}

}

