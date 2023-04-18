package CreatePackage;

//Imports--------------------------------------------------------------------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

import CreatePackage.CreateMethods.Vector2;

//sub https://www.freepik.com/free-vector/military-submarine-white-background_26353593.htm#query=military%20submarine&position=0&from_view=keyword&track=ais
//cloud https://creazilla.com/nodes/3159387-cloud-clipart
//torpedo https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fthumbnails%2F010%2F882%2F949%2Fsmall%2Fflat-torpedo-sea-marine-weapon-technology-sign-isolated-white-icon-naval-nautical-submarine-bomb-vector.jpg&tbnid=isE6AQCn7FRdMM&vet=12ahUKEwiEqfjNy7P-AhWZKd4AHaJhAuoQMygEegUIARDVAQ..i&imgrefurl=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Ftorpedo&docid=cd68Qo3dwlGDEM&w=200&h=200&q=torpedo%20clipart&ved=2ahUKEwiEqfjNy7P-AhWZKd4AHaJhAuoQMygEegUIARDVAQ&safe=active&ssui=on


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
    static final Image wave = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/waveTile2.png");
    static final int waveWidth = 180;
    static final int waveHeight = 180;

    static final Image sub = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/FreePikSub.png");
    static final int subWidth = 805 / 2;
    static final int subHeight = 310 / 2;
    static Vector2 subPos = new Vector2(width/2, height-300);
    static int subDir = -1;

    static final Image torp = CreateMethods.imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/torpedoSprite.png");
    static final int torpWidth = 200 / 2;
    static final int torpHeight = 200 / 2;
    static int reload = 0;

    static ArrayList<String> keysPressed = new ArrayList<String>();
    static ArrayList<Vector2> torps = new ArrayList<Vector2>();

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

    //Main------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("CreateProject"); //TODO: Make actual name
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(new CreateProject());
        frame.add(panel);

        // frame.setIconImage(imageURL("https://raw.githubusercontent.com/CrazyMeowCows/CreateProject/main/CreazillaCloud.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  

        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                gameLogic();
                frame.repaint();
            }
        });
        timer.start();  

        for (int i = 0; i < cloudPos.length; i++) {
            cloudPos[i] = new Vector2(Math.random()*width, Math.random()*180);
        }
    }

//Drawing
    static void gameLogic () {
        if (keysPressed.contains("W") && subPos.y > 320+subHeight/2) {
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
        if (keysPressed.contains("Space") && reload <= 0) {
            torps.add(new Vector2(subPos.x + 70 * subDir, subPos.y));
            reload = 50*2;
        } 
        reload = Math.max(reload-1, 0); 
    }

    static class DrawingManager extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            setBackground(new Color(136, 203, 220)); //Fill background with skyblue

            drawClouds(g2d);
            drawWater(g2d);

            Iterator<Vector2> itr = torps.iterator();            
            while(itr.hasNext()){
                Vector2 vector2 = itr.next();
                if (vector2.y < 330) {
                    itr.remove();
                }
                g2d.drawImage(torp, (int)vector2.x-torpWidth/2, (int)vector2.y-torpHeight/2, torpWidth, torpHeight, null);
                vector2.y -= 5;
            }

            drawSub(g2d);
        }
    }

    static void drawSub (Graphics2D g2d) {
        g2d.translate(subPos.x, subPos.y);
        if (subDir == 1) {
            g2d.scale( -1, 1 );
        }
        g2d.drawImage(sub, -subWidth/2, -subHeight/2, subWidth, subHeight, null);
        g2d.scale( 1, 1 );
        g2d.translate(0, 0);
}

    static void drawWater (Graphics2D g2d) {
        waveOffset += 0.25;
        if (waveOffset >= waveWidth) {waveOffset = 0;}

        for (int x = -waveWidth; x < width; x += waveWidth) {
            for (int y = 250; y < height; y += waveHeight) {
                g2d.drawImage(wave, x+(int)waveOffset, y, waveWidth, waveHeight, null);
            }
        }
        g2d.setColor(new Color(0, 0, 200));
        g2d.fillRect(0, 400, width, height);
    }

    static void drawClouds (Graphics2D g2d) {
        for (int i = 0; i < cloudPos.length; i++) {
            cloudPos[i].x++;
            if (cloudPos[i].x > width) {
                cloudPos[i] = new Vector2(-Math.random()*width-cloudWidth, Math.random()*180);
            }
            g2d.drawImage(cloud, (int)cloudPos[i].x, (int)cloudPos[i].y, cloudWidth, cloudHeight, null);
        }
    }
}
