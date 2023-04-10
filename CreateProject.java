//Imports--------------------------------------------------------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class CreateProject extends JPanel {

    //Variables------------------------------------------------------------------------------------------------------------------------------------------
    static final DrawingManager panel = new DrawingManager();
    static final Font font = new Font("Serif", Font.PLAIN, 20);
    static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static final int width = gd.getDisplayMode().getWidth()-100;
    static final int height = gd.getDisplayMode().getHeight()-100;

    static int offset = 0;
    static final Image cloud = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/CreazillaCloud.png");
    static final int cloudWidth = 677 / 3;
    static final int cloudHeight = 369 / 3;

    static final Color skyBlue = new Color(136, 203, 220);

    //Main------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("CreateProject"); //TODO: Make actual name
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);

        // frame.setIconImage(imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/CreazillaCloud.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  

        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                frame.repaint();
            }
        });
        timer.start();  
    }

//Drawing
    static class DrawingManager extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            setBackground(skyBlue); //Fill background with sky blue

            g2d.drawImage(cloud, 50, 50, cloudWidth, cloudHeight, null);

            g2d.setColor(Color.BLUE); //Draw main body of ocean
            g2d.fillRect(0, 300, width, height);

            offset++; //Add waves on top of ocean
            if (offset >= 100) {offset = 0;}
            for (int x = -200; x < width; x += 100) {
                g2d.fillArc(x + offset, 292, 100, 16, 0, 180);
            }
        }
    }

//Methods
    public static Image imageURL(String link){  
        Image finalImage = null;
        try {
            finalImage = ImageIO.read(new URL(link));
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        return finalImage;
    } 
}
