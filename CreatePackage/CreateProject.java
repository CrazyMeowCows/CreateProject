package CreatePackage;

//Imports------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import CreatePackage.CreateMethods.Launcher;
import CreatePackage.CreateMethods.Vector2;

public class CreateProject extends JPanel {

    //Variable Declerations------------------------------------------------------------------------
    static final DrawingManager panel = new DrawingManager();
    static final Font font = new Font("Serif", Font.PLAIN, 20);
    static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static final int width = gd.getDisplayMode().getWidth()-100;
    static final int height = gd.getDisplayMode().getHeight()-100;
    static final int scale = 10;

    static final Image cloud = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/cloudSprite-1.png.png");
    static final int cloudWidth = 32 * scale;
    static final int cloudHeight = 14 * scale;
    static final Image wave = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waveSprite-1.png.png");
    static final Image water = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterSprite-1.png.png");
    static final int waveWidth = 32 * scale;
    static final int waveHeight = 32 * scale;
    static final Image sub = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/subSprite-1.png.png");
    static final int subWidth = 32 * scale;
    static final int subHeight = 11 *  scale;
    static final Image ship = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/shipSprite-1.png.png");
    static final int shipWidth = 48 * scale;
    static final int shipHeight = 13 *  scale;
    static final Image torp = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/torpSprite-1.png.png");
    static final int torpWidth = 2 * scale;
    static final int torpHeight = 10 * scale;
    static final Image charge = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/chargeSprite-1.png.png");
    static final int chargeWidth = 2 * scale;
    static final int chargeHeight = 2 *  scale;
    static final Image launcher = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/launcherSprite-1.png.png");
    static final int launcherWidth = 6 * scale;
    static final int launcherHeight = 4 *  scale;

    static final Image splash = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waterGIF.gif");
    static final int splashWidth = 32 * scale;
    static final int splashHeight = 32 *  scale;
    static final Image fire = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/fireGIF.gif");
    static final int fireWidth = 8 * scale;
    static final int fireHeight = 34 *  scale;
    static final Image explosion = CreateMethods.imageURL("https://github.com/CrazyMeowCows/CreateProject/blob/main/explosionGIF%20(1).gif?raw=true");
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

    static ArrayList<String> keysPressed = new ArrayList<String>();
    static ArrayList<Vector2> torps = new ArrayList<Vector2>();
    static ArrayList<Vector2> charges = new ArrayList<Vector2>();

    static Launcher[] launchers = {new Launcher(), new Launcher(), new Launcher()};
    static Vector2[] cloudPos = new Vector2[5];

    static Timer timer;


    //Key Listener Setup---------------------------------------------------------------------------
    public CreateProject() {
		KeyListener listener = new keyListener();
		addKeyListener(listener);
		setFocusable(true);
	}

    public class keyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
            if (!keysPressed.contains(KeyEvent.getKeyText(e.getKeyCode()))) {
                keysPressed.add(KeyEvent.getKeyText(e.getKeyCode()));
            }
		}

		@Override
		public void keyReleased(KeyEvent e) {
            try {
                keysPressed.remove(keysPressed.indexOf(KeyEvent.getKeyText(e.getKeyCode())));
            } catch (IndexOutOfBoundsException i) {
                System.err.println(i);
            }
		}
	}


    //Main-----------------------------------------------------------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("CreateProject");
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(new CreateProject());
        frame.add(panel);

        frame.setIconImage(sub);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  

        timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                gameLogic();
                frame.repaint();
            }
        });
        timer.start();  

        for (int i = 0; i < cloudPos.length; i++) {
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
            drawShip(g2d);
            drawWater(g2d);
            drawLaunchers(g2d);
            drawCharges(g2d);
            drawTorps(g2d);
            drawSub(g2d);

            g2d.drawImage(explosion, (int)300, (int)300, explosionWidth, explosionHeight, null);

        }
    }


    //Drawing Methods------------------------------------------------------------------------------
    static void drawLaunchers (Graphics2D g2d) {
        for (int i = 0; i < launchers.length; i++) {
            if (launchers[i].health > 0) {
                g2d.drawImage(launcher, (int)launchers[i].x-launcherWidth/2, (int)(shipPos.y - 3.5*scale), launcherWidth, launcherHeight, null);
            }
        }
    }

    static void drawSub (Graphics2D g2d) {
        g2d.translate(subPos.x, subPos.y);
        if (subDir != 1) {
            g2d.scale( -1, 1 );
        }
        g2d.drawImage(sub, -subWidth/2, -subHeight/2, subWidth, subHeight, null);
        g2d.scale( 1, 1 );
        g2d.translate(-subPos.x, -subPos.y);
    }

    static void drawShip (Graphics2D g2d) {
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

    static void drawCharges (Graphics2D g2d) {
        Iterator<Vector2> itr = charges.iterator();            
        while(itr.hasNext()){
            Vector2 vector2 = itr.next();
            if (vector2.y > height) {
                itr.remove();
            } else if (vector2.y > subPos.y-subHeight/2 && vector2.y < subPos.y+subHeight/2 && vector2.x > subPos.x-subWidth/2 && vector2.x < subPos.x+subWidth/2) {
                subHealth -= 40;
                itr.remove();
            }
            g2d.drawImage(charge, (int)vector2.x-chargeWidth/2, (int)vector2.y-chargeHeight/2, chargeWidth, chargeHeight, null);
            vector2.y += 5;
        }
    }

    static void drawTorps (Graphics2D g2d) {
        Iterator<Vector2> itr = torps.iterator();            
        while(itr.hasNext()){
            Vector2 vector2 = itr.next();
            if (vector2.y < 330) {
                itr.remove();
                for (int i = 0; i < launchers.length; i++) {
                    if (Math.abs(vector2.x - launchers[i].x) < launcherWidth/2) {
                        launchers[i].health -= 50;
                    }
                }
            }
            g2d.drawImage(torp, (int)vector2.x-torpWidth/2, (int)vector2.y-torpHeight/2, torpWidth, torpHeight, null);
            vector2.y -= 5;
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
                cloudPos[i] = new Vector2(-Math.random()*width-cloudWidth, Math.random()*150);
            }
            g2d.drawImage(cloud, (int)cloudPos[i].x, (int)cloudPos[i].y, cloudWidth, cloudHeight, null);
        }
    }
}
