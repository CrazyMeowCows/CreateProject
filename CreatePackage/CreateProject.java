package CreatePackage;

//Imports------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class CreateProject extends JPanel {
    //Variable Declerations------------------------------------------------------------------------
    static final DrawingManager panel = new DrawingManager();
    static final Font font = new Font("Serif", Font.PLAIN, 20);
    static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static final int width = gd.getDisplayMode().getWidth()-100;
    static final int height = gd.getDisplayMode().getHeight()-100;
    static final int scale = 10;

    //All pixel art images were created by me, so no credits needed.
    static final Image cloud = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/cloudSprite-1.png.png");
    static final int cloudWidth = 32 * scale;
    static final int cloudHeight = 14 * scale;
    static final Image wave = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waveSprite-1.png.png");
    static final Image water = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterSprite-1.png.png");
    static final int waveWidth = 32 * scale;
    static final int waveHeight = 32 * scale;
    static final Image sub = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/subSprite-1.png.png");
    static final int subWidth = 32 * scale;
    static final int subHeight = 11 *  scale;
    static final Image ship = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/shipSprite-1.png.png");
    static final int shipWidth = 48 * scale;
    static final int shipHeight = 13 *  scale;
    static final Image torp = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/torpSprite-1.png.png");
    static final int torpWidth = 2 * scale;
    static final int torpHeight = 10 * scale;
    static final Image charge = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/chargeSprite-1.png.png");
    static final int chargeWidth = 2 * scale;
    static final int chargeHeight = 2 *  scale;
    static final Image launcher = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/launcherSprite-1.png.png");
    static final int launcherWidth = 6 * scale;
    static final int launcherHeight = 4 *  scale;

    //Gifs were used after ensuring they were free to download and use non-commerically
    static final Image splash = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterGIF.gif"); //SOURCE: https://giphy.com/stickers/water-splash-splashing-uRN8krEG25j6BrNg5a
    static final int splashWidth = 32 * scale;
    static final int splashHeight = 32 *  scale;
    static final Image fire = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/flameGIF.gif"); //SOURCE: https://no.pinterest.com/pin/163748136435573274/
    static final int fireWidth = 8 * scale;
    static final int fireHeight = 34 *  scale;
    static final Image explosion = imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/explosionGIF.gif"); //SOURCE: https://gifer.com/en/3iCN
    static final int explosionWidth = 20 * scale;
    static final int explosionHeight = 20 *  scale;

    static double cloudOffset = 0;
    static double waveOffset = 0;
    
    static int subHealth = 100;
    static int subReload = 0;
    static int subDir = -1;
    static int shipReload = 0;
    static int shipDir = 1;

    static Vector2 shipPos = new Vector2(width/2, 275);
    static Vector2 subPos = new Vector2(width/2, height-300);
    
    static Launcher[] launchers = {new Launcher(), new Launcher(), new Launcher()};
    static Vector2[] cloudPos = new Vector2[7];

    static Timer timer;

    static ArrayList<Vector2> torps = new ArrayList<Vector2>();
    static ArrayList<Vector2> charges = new ArrayList<Vector2>();
    static ArrayList<GIF> gifs = new ArrayList<GIF>();
    static ArrayList<String> keysPressed = new ArrayList<String>();

    //Key Listener Setup---------------------------------------------------------------------------
    public CreateProject() {
		KeyListener listener = new keyListener(); //Create a keylistener instance to add to the jframe
		addKeyListener(listener);
		setFocusable(true);
	}

    public class keyListener implements KeyListener { //create a keylistener class
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) { //When a key is pressed, add it to the list of pressed keys if it is not already on there
            if (!keysPressed.contains(KeyEvent.getKeyText(e.getKeyCode()))) {
                keysPressed.add(KeyEvent.getKeyText(e.getKeyCode()));
                System.out.println(keysPressed);
            }
		}

		@Override
		public void keyReleased(KeyEvent e) { //When a key is released, remove it from the list of pressed keys
            try {
                keysPressed.remove(keysPressed.indexOf(KeyEvent.getKeyText(e.getKeyCode())));
                System.out.println(keysPressed);
            } catch (IndexOutOfBoundsException i) {
                System.err.println("keyReleased err: " + i);
            }
		}
	}

    //Misc Methods---------------------------------------------------------------------------------
    public static Image imageURL(String link){ //Get image from URL
        Image finalImage = null;
        try {
            finalImage = new ImageIcon(new URL(link)).getImage();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        return finalImage;
    } 


    //Main-----------------------------------------------------------------------------------------
    public static void main(String[] args) { 
        JFrame frame = new JFrame("CreateProject"); //Basic JFrame setup
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(new CreateProject());
        frame.add(panel);

        frame.setIconImage(sub);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  

        timer = new Timer(20, new ActionListener() { //Create a timer that runs every 20 ms to run the game loop
            public void actionPerformed(ActionEvent evt) {
                gameLogic();
                frame.repaint();
            }
        });
        timer.start();  

        for (int i = 0; i < cloudPos.length; i++) { //Initialize random array of clouds
            cloudPos[i] = new Vector2(Math.random()*width, Math.random()*150);
        }
    }


    //Game Logic-----------------------------------------------------------------------------------
    static void gameLogic () {
        if (keysPressed.contains("W") && subPos.y > 320+subHeight/2) { //Move based off of player input
            subPos.y -= 5;
        }
        if (keysPressed.contains("S") & subPos.y < height-subHeight/2) {
            subPos.y += 5;
        }
        if (keysPressed.contains("A") && subPos.x > subWidth/2) {
            subPos.x -= 5;
            subDir = -1;
        }
        if (keysPressed.contains("D") && subPos.x < width-subWidth/2) {
            subPos.x += 5;
            subDir = 1;
        }
        if (keysPressed.contains("Space") && subReload <= 0) {
            torps.add(new Vector2(subPos.x + 5*scale*subDir, subPos.y - 1*scale));
            subReload = 50*2;
        } 
        subReload = Math.max(subReload-1, 0); 

        if (shipPos.x > width-shipWidth/2 || shipPos.x < shipWidth/2) { //Move ship back and forth and bounce off walls
            shipDir *= -1;
        }
        shipPos.x += 2*shipDir;

        launchers[0].x = shipPos.x - 18*scale*shipDir; //Set location of each launcher
        launchers[1].x = shipPos.x - 2*scale*shipDir;
        launchers[2].x = shipPos.x + 16*scale*shipDir;

        shipReload++; //Drop charges every 100 game ticks if the launcher is still active
        if (shipReload > 100) {
            for (int i = 0; i < launchers.length; i++) {
                if (launchers[i].health > 0) {
                    charges.add(new Vector2(launchers[i].x, shipPos.y - 2.5*scale));
                }
            }
            shipReload = 0;
        }

        if (subHealth < 0) { //Stop game if sub dies
            timer.stop();
        }
    }


    //Drawing Manager------------------------------------------------------------------------------
    static class DrawingManager extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            setBackground(new Color(136, 203, 220)); //Fill background with skyblue

            drawClouds(g2d); //Call drawing functions for each of the game elements
            drawLaunchers(g2d);
            drawShip(g2d);
            drawWater(g2d);
            drawCharges(g2d);
            drawTorps(g2d, torps);
            drawSub(g2d);
            drawGIFS(g2d);
        }
    }


    //Drawing Methods------------------------------------------------------------------------------
    static void drawLaunchers (Graphics2D g2d) { //Draw depth charge launchers mounted on the ship
        for (int i = 0; i < launchers.length; i++) {
            if (launchers[i].health > 0) {
                g2d.drawImage(launcher, (int)launchers[i].x-launcherWidth/2, (int)(shipPos.y - 3.5*scale), launcherWidth, launcherHeight, null);
            } else {
                g2d.drawImage(fire, (int)launchers[i].x-launcherWidth/2-1*scale, (int)(shipPos.y + 1.5*scale - fireHeight), fireWidth, fireHeight, null);
            }
        }
    }

    static void drawSub (Graphics2D g2d) { //Draw player controlled submarine
        g2d.translate(subPos.x, subPos.y);
        if (subDir != 1) {
            g2d.scale( -1, 1 );
            g2d.drawImage(sub, -subWidth/2, -subHeight/2, subWidth, subHeight, null);
            g2d.scale( -1, 1 );
        } else {
            g2d.drawImage(sub, -subWidth/2, -subHeight/2, subWidth, subHeight, null);
        }
        g2d.translate(-subPos.x, -subPos.y);
    }

    static void drawShip (Graphics2D g2d) { //Draw the AI surface ship
        g2d.translate(shipPos.x, shipPos.y);
        if (shipDir != 1) {
            g2d.scale( -1, 1 );
            g2d.drawImage(ship, -shipWidth/2, -shipHeight/2, shipWidth, shipHeight, null);
            g2d.scale( -1, 1 );
        } else {
            g2d.drawImage(ship, -shipWidth/2, -shipHeight/2, shipWidth, shipHeight, null);
        }
        g2d.translate(-shipPos.x, -shipPos.y);
    }

    static void drawGIFS (Graphics2D g2d) { //Draw any gifs currently queued in gifs
        Iterator<GIF> itr = gifs.iterator();            
        while(itr.hasNext()){
            GIF gif = itr.next();
            if (gif.inc < gif.length) {
                g2d.drawImage(gif.img, (int)gif.pos.x - gif.w/2, (int)gif.pos.y - gif.h/2, gif.w, gif.h, null);
                gif.inc++;
            } else {
                itr.remove();
            }
        }
    }

    static void drawCharges (Graphics2D g2d) { //Draw depth charges launched by ship
        Iterator<Vector2> itr = charges.iterator();            
        while(itr.hasNext()){ //iterate through all depth charges in the list
            Vector2 vector2 = itr.next();
            if (vector2.y > height) { //If charge is off bottom of the screen, it can be removed
                itr.remove();
            } else if (vector2.y > subPos.y-subHeight/2 && vector2.y < subPos.y+subHeight/2 && vector2.x > subPos.x-subWidth/2 && vector2.x < subPos.x+subWidth/2) { //check if charge hit sub
                subHealth -= 40;
                itr.remove();
                gifs.add(new GIF(explosion, vector2, explosionWidth, explosionHeight, 30));
            }
            g2d.drawImage(charge, (int)vector2.x-chargeWidth/2, (int)vector2.y-chargeHeight/2, chargeWidth, chargeHeight, null); //Draw depth charge
            vector2.y += 5; //Move depth charge down every program cycle
        }
    }

    static void drawTorps (Graphics2D g2d, ArrayList<Vector2> torps) { //Draw torpedos launched by sub
        Iterator<Vector2> itr = torps.iterator();            
        while(itr.hasNext()){ //iterate through all torps
            Vector2 vector2 = itr.next();
            if (vector2.y < 330) { //check if torp hit the surface
                itr.remove();
                boolean hit = false;
                for (int i = 0; i < launchers.length; i++) { //check if the torp hit any of the targets on the ship
                    if (Math.abs(vector2.x - launchers[i].x) < launcherWidth/2) {
                        launchers[i].health -= 50;
                        hit = true;
                    }
                }
                if (!hit) { //if it missed all targets, play a splah gif
                    gifs.add(new GIF(splash, vector2, splashWidth, splashHeight, 20));
                }
            }
            g2d.drawImage(torp, (int)vector2.x-torpWidth/2, (int)vector2.y-torpHeight/2, torpWidth, torpHeight, null); //draw torpedo
            vector2.y -= 5; //move torpedo up every program cycle
        }
    }

    static void drawWater (Graphics2D g2d) {
        waveOffset += 0.25;
        if (waveOffset >= waveWidth) {waveOffset = 0;}

        for (int x = -waveWidth; x < width; x += waveWidth) {
            for (int y = 250; y < height; y += waveHeight) {
                g2d.drawImage(wave, x+(int)waveOffset, y, waveWidth, waveHeight, null);
            }
        }
        for (int x = -waveWidth; x < width; x += waveWidth) {
            for (int y = 250+waveHeight; y < height; y += waveHeight) {
                g2d.drawImage(water, x+(int)waveOffset, y, waveWidth, waveHeight, null);
            }
        }
    }

    static void drawClouds (Graphics2D g2d) {
        for (int i = 0; i < cloudPos.length; i++) {
            cloudPos[i].x++;
            if (cloudPos[i].x > width) {
                cloudPos[i] = new Vector2(-cloudWidth, Math.random()*150);
            }
            g2d.drawImage(cloud, (int)cloudPos[i].x, (int)cloudPos[i].y, cloudWidth, cloudHeight, null);
        }
    }


    //Class definitions----------------------------------------------------------------------------
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
