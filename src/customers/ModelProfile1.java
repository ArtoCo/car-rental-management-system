/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customers;

/**
 *
 * @author ArtoCO
 */
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ModelProfile1 {
    
    public Icon getIcon() {
        return icon;
    }
    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    public File getPath() {
        return path;
    }
    public void setPath(File path) {
        this.path = path;
    }
    public ModelProfile1(File path) {
        this.path = path;
    }
    public ModelProfile1(byte[] bytes) {
        if (bytes != null) {
            icon = new ImageIcon(bytes);
        }
    }
    public ModelProfile1(Icon icon) {
        this.icon = icon;
    }
    
    private Icon icon;
    private File path;
}
