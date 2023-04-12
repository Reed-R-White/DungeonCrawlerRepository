package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * 
 * @author Reed White
 */
public class DungeonPlayer {
	
	/* The maximum movement distance for the player */
	private static final int MAXMOVEMENT = 3;
	/* The amount of damage done to an enemy by the player */
	private static final int PLAYERDAMAGE = 5;
	/* The health of the player */
	private int playerHealth = 10;
	/* The x-coordinate of the player */
	private int playerX;
	/* The y-coordinate of the player */
	private int playerY;
	/* The new position of the player */
	private Point newPosition;
	/* The current position of the player */
	private Point currentPosition;
	/* The avatar of the player */
	private Image playerAvatar;
	/* The JFrame that the game is running in */
	private JFrame homeFrame;

	/**
	 * Constructs a DungeonPlayer object
	 * @param playerJFrame The JFrame that the game is displayed in
	 */
	public DungeonPlayer(JFrame playerJFrame) {
		homeFrame = playerJFrame;
		newPosition = new Point(playerX, playerY);
		
		homeFrame.addMouseMotionListener(new MouseMotionAdapter() {
	        /*
	         * Updates the new position of the player based on mouse movement
	         */
	        public void mouseMoved(MouseEvent e) {
	        	newPosition = new Point(e.getX(), e.getY());
	        }
	    });
		
		ActionListener movementPerSecond = new ActionListener() {
			/*
			 * Moves the player a maximum distance of MAXMOVEMENT each second
			 */
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

	/**
	 * Calculates the index of the player's current location within the game grid
	 * @param x The x-coordinate of the player
	 * @param y The y-coordinate of the player
	 */
	public void getGridLocation(int x, int y) {
    	int numberOfBoxes = 20;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }
	
	/**
	 * Gets the x-coordinate of the player's position.
	 * @return the x-coordinate of the player's position
	 */
	public int getX() {
		return playerX;
	}
	
	/**
	 * Gets the y-coordinate of the player's position.
	 * @return the y-coordinate of the player's position
	 */
	public int getY() {
		return playerY;
	}
	
	/*
	 * Draws the player's image on screen
	 */
	public void drawPlayer() {
		// This code is adapted from an online example to fix image flicker.
		// FIND SRC
		
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
	
	/**
	 * Rotates a rectangle around a pivot point by a given angle.
	 * @param rectangle   an array of four points representing the corners of the rectangle
	 * @param pivot   the point around which to rotate the rectangle
	 * @param angle   the angle in radians by which to rotate the rectangle
	 */
	public void rotateRectangle(Point[] rectangle, Point pivot, double angle) {
		// WARNING: CHAT GPT CODE PLZ USE CAREFULLY
		// add context
	    double cos = Math.cos(angle);
	    double sin = Math.sin(angle);
	    for (Point p : rectangle) {
	        double dx = p.x - pivot.x;
	        double dy = p.y - pivot.y;
	        p.x = pivot.x + (int) (dx * cos - dy * sin);
	        p.y = pivot.y + (int) (dx * sin + dy * cos);
	    }
	}
	
	/**
	* This method executes an attack action from the player towards the given point.
	* The attack is executed by creating a rectangle projecting from the player towards
	* the given point and checking whether a passed in point exists within this rectangle.
	* If an intersection is found, the game object will receive {@link DungeonPlayer.PLAYERDAMAGE} points of damage.
	*
	* @param point   the point towards which the player should execute the attack
	* @return true if an attack was executed, false otherwise
	*/
	public boolean attack(Point point) {
	    // Get the direction from the player to the mouse
	    double deltaX = newPosition.getX() - playerX;
	    double deltaY = newPosition.getY() - playerY;
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
	    
	    Point bottomLeft = new Point((int) rectX, (int) rectY);
	    Point topLeft = new Point((int) rectX, (int) (rectY + rectHeight));
	    Point bottomRight = new Point((int) (rectX + rectWidth), (int) rectY);
	    Point topRight = new Point((int) (rectX + rectWidth), (int) (rectY + rectHeight));
	    Point[] recPoints = new Point[] {bottomLeft, topLeft, bottomRight, topRight};
	    rotateRectangle(recPoints,new Point(playerX, playerY), angle);
	    
	    int[] xCords = new int[] {(int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(), (int) bottomRight.getX()}; 
	    int[] yCords = new int[] {(int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(), (int) bottomRight.getY()};
	    
	    Polygon hitbox = new Polygon(xCords, yCords, 4);
	    
	    
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.draw(hitbox);
	    return hitbox.contains(point);
	    // Check if the point is in the rectangle
	}
	
}
