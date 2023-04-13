package CreatePackage;

//Imports--------------------------------------------------------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import CreatePackage.CreateMethods.Vector2;

//water tile https://www.google.com/url?sa=i&url=https%3A%2F%2Fsharecg.com%2Fv%2F2987%2Ffavorite%2F6%2Ftexture%2Ftileable-water-02&psig=AOvVaw150yNy2zSuRXMn7Z7jQD5B&ust=1681307294815000&source=images&cd=vfe&ved=0CA0QjRxqFwoTCKDa7fT7of4CFQAAAAAdAAAAABAI
//sub https://www.freepik.com/free-vector/military-submarine-white-background_26353593.htm#query=military%20submarine&position=0&from_view=keyword&track=ais
//cloud https://creazilla.com/nodes/3159387-cloud-clipart


@SuppressWarnings("serial")
public class CreateProject extends JPanel {

    //Variables------------------------------------------------------------------------------------------------------------------------------------------
    static final DrawingManager panel = new DrawingManager();
    static final Font font = new Font("Serif", Font.PLAIN, 20);
    static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static final int width = gd.getDisplayMode().getWidth()-100;
    static final int height = gd.getDisplayMode().getHeight()-100;

    static double cloudOffset = 0;
    static Vector2[] cloudPos = new Vector2[10];
    static final Image cloud = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/CreazillaCloud.png");
    static final int cloudWidth = 226;
    static final int cloudHeight = 123;

    static double waveOffset = 0;
    static final Image water = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterTile.png");
    static final Image wave = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waveTile.png");
    static final int waterWidth = 180;
    static final int waterHeight = 180;

    static final Image sub = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/FreePikSub.png");
    static final int subWidth = 805 / 2;
    static final int subHeight = 310 / 2;

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

        for (int i = 0; i < cloudPos.length; i++) {
            cloudPos[i] = new Vector2(Math.random()*width, Math.random()*200);
        }
    }

//Drawing
    static class DrawingManager extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            setBackground(new Color(136, 203, 220)); //Fill background with skyblue

            drawClouds(g2d);
            drawWater(g2d);

            g2d.drawImage(sub, 100, 400, null);
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

    static void drawClouds (Graphics2D g2d) {
        for (int i = 0; i < cloudPos.length; i++) {
            cloudPos[i].x++;
            if (cloudPos[i].x > width) {
                cloudPos[i] = new Vector2(-Math.random()*width-cloudWidth, Math.random()*200);
            }
            g2d.drawImage(cloud, (int)cloudPos[i].x, (int)cloudPos[i].y, cloudWidth, cloudHeight, null);
        }
    }
}
