package CreatePackage;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class CreateMethods {
    public static Image imageURL(String link){  
        Image finalImage = null;
        try {
            finalImage = ImageIO.read(new URL(link));
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
}
