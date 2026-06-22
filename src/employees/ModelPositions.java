/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employees;

/**
 *
 * @author ArtoCO
 */
public class ModelPositions {

    
    public String getPositionsName() {
        return positionsName;
    }

    public void setPositionsName(String positionsName) {
        this.positionsName = positionsName;
    }

    public ModelPositions(String positionsName) {
     
        this.positionsName = positionsName;
    }

    public ModelPositions() {
    }

    private String positionsName;

    @Override
    public String toString() {
        return positionsName;
    }
}