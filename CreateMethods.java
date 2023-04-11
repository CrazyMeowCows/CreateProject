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

    public static class Cloud{
        private Image image;
        private int x;
        private int y;

        public Cloud(Image image, int x, int y){
            this.image = image;
            this.x = x;
            this.y = y;
        }

        public void moveX () {
            x++;
        }

        public Image image(){
            return image;
        }
        public int x(){
            return x;
        }
        public int y(){
            return y;
        }
    }
}
