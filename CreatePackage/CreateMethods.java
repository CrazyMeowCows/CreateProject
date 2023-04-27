package CreatePackage;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;

public class CreateMethods {
    public static Image imageURL(String link){  
        Image finalImage = null;
        try {
            finalImage = new ImageIcon(new URL(link)).getImage();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        return finalImage;
    } 

    static class Vector2 {              
        public double x;
        public double y;

        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Launcher {              
        public double x;
        public double health;

        public Launcher() {
            this.x = 0;
            this.health = 100;
        }
    }
}
