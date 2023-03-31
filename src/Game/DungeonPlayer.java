package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class DungeonPlayer {
	
	private static final int MAXMOVEMENT = 3;
	private static final int PLAYERDAMAGE = 5;
	private int playerHealth = 10;
	private int playerX;
	private int playerY;
	private Point newPosition;
	private Point currentPosition;
	private Image playerAvatar;
	private JFrame homeFrame;
	
	public DungeonPlayer(JFrame playerJFrame) {
		homeFrame = playerJFrame;
		newPosition = new Point(playerX, playerY);
		
		homeFrame.addMouseMotionListener(new MouseMotionAdapter() {
	        public void mouseMoved(MouseEvent e) {
	        	newPosition = new Point(e.getX(), e.getY());
	        }
	    });
		
		ActionListener movementPerSecond = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 currentPosition = new Point(playerX, playerY);

	                // calculate distance between currentPosition and newPosition
	                double distance = currentPosition.distance(newPosition);

	                if (distance > 5) {
	                    // calculate the amount to move in each direction based on MAXMOVEMENT
	                    double dx = MAXMOVEMENT * (newPosition.getX() - currentPosition.getX()) / distance;
	                    double dy = MAXMOVEMENT * (newPosition.getY() - currentPosition.getY()) / distance;

	                    // update player's position
	                    playerX += (int) dx;
	                    playerY += (int) dy;
	                }
	                drawPlayer();
	            }
	        };
		
	    Timer timer = new Timer(10, movementPerSecond);
	    timer.start();
	    
	    try {
	    	playerAvatar = ImageIO.read(new File("src/Game/playerSprite.png"));
	    } catch (IOException FileNotFoundException) {
	    	System.out.println("src/Game/playerSprite.png not found resulted in failure");
	    }
	}
	
	private void getLocal(int x, int y) {
    	int numberOfBoxes = 20;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }
	
	
	public int getX() {
		return playerX;
	}
	
	public int getY() {
		return playerY;
	}
	
	public void drawPlayer() {
		// This code is adapted from an online example to fix image flicker.
		
		// Create an off-screen image buffer
	    Image offScreenImage = homeFrame.createImage(homeFrame.getWidth(), homeFrame.getHeight());
	    Graphics offScreenGraphics = offScreenImage.getGraphics();
	    
	    // Clear the previous image
	    offScreenGraphics.clearRect(0, 0, homeFrame.getWidth(), homeFrame.getHeight());
	    
	    // Draw the new image on the off-screen buffer
	    offScreenGraphics.drawImage(playerAvatar, playerX, playerY, null);
	    
	    // Paint the off-screen buffer on the screen
	    homeFrame.getGraphics().drawImage(offScreenImage, 0, 0, null);
	}
	
	public boolean attack(Point point) {
	    // Get the direction from the player to the mouse
	    double deltaX = point.getX() - playerX;
	    double deltaY = point.getY() - playerY;
	    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	    
	    // Calculate the unit vector in the direction of the mouse
	    double unitX = deltaX / distance;
	    double unitY = deltaY / distance;
	    
	    // Calculate the rectangle emanating from the player in the direction of the mouse
	    double rectWidth = 100;
	    double rectHeight = 50;
	    double rectX = playerX;
	    double rectY = playerY - rectHeight / 2.0;
	    
	    // Rotate the rectangle to match the angle of the mouse in relation to the player
	    double angle = Math.atan2(deltaY, deltaX);
	    Graphics g = homeFrame.getGraphics();
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.rotate(angle, playerX, playerY);
	    g2d.drawRect((int) rectX, (int) rectY, (int) rectWidth, (int) rectHeight);
	    g2d.rotate(-angle, playerX, playerY);
	    
	    // Check if the point is in the rectangle
	    if (point.getX() >= rectX && point.getX() <= rectX + rectWidth &&
	            point.getY() >= rectY && point.getY() <= rectY + rectHeight) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
}
