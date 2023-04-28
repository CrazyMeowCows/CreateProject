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

    static class GIF {              
        public Image img;
        public Vector2 pos;
        public int w;
        public int h;
        public int length;
        public int inc;

        public GIF(Image img, Vector2 pos, int w, int h, int length) {
            this.img = img;
            this.pos = pos;
            this.w = w;
            this.h = h;
            this.length = length;
            this.inc = 0;
        }
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
