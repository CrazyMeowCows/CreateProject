//Imports--------------------------------------------------------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

//water tile https://www.google.com/url?sa=i&url=https%3A%2F%2Fsharecg.com%2Fv%2F2987%2Ffavorite%2F6%2Ftexture%2Ftileable-water-02&psig=AOvVaw150yNy2zSuRXMn7Z7jQD5B&ust=1681307294815000&source=images&cd=vfe&ved=0CA0QjRxqFwoTCKDa7fT7of4CFQAAAAAdAAAAABAI

@SuppressWarnings("serial")
public class CreateProject extends JPanel {

    //Variables------------------------------------------------------------------------------------------------------------------------------------------
    static final DrawingManager panel = new DrawingManager();
    static final Font font = new Font("Serif", Font.PLAIN, 20);
    static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static final int width = gd.getDisplayMode().getWidth()-100;
    static final int height = gd.getDisplayMode().getHeight()-100;

    static double cloudOffset = 0;
    static CreateMethods.Cloud[] clouds;
    static final Image cloud = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/CreazillaCloud.png");
    static final int cloudWidth = 226;
    static final int cloudHeight = 123;

    static double waveOffset = 0;
    static final Image water = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterTile.png");
    static final Image wave = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waveTile.png");
    static final int waterWidth = 180;
    static final int waterHeight = 180;

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

        for (int i = 0; i < 10; i++) {
            // clouds
        }
    }

//Drawing
    static class DrawingManager extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            setBackground(skyBlue); //Fill background with sky blue

            g2d.drawImage(cloud, 50, 50, cloudWidth, cloudHeight, null);


            drawWater(g2d);
        }
    }

    static void drawWater (Graphics2D g2d) {
        waveOffset += 0.25;
        if (waveOffset >= waterWidth) {waveOffset = 0;}

        for (int x = -waterWidth; x < width; x += waterWidth) {
            for (int y = 300; y < height; y += waterHeight) {
                g2d.drawImage(wave, x+(int)waveOffset, y, waterWidth, waterHeight, null);
            }
        }
        for (int x = -waterWidth; x < width; x += waterWidth) {
            for (int y = 300+waterHeight; y < height; y += waterHeight) {
                g2d.drawImage(water, x+(int)waveOffset, y, waterWidth, waterHeight, null);
            }
        }
    }
}
