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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JLabel playerAvatar;
	private JFrame homeFrame;
	
	/**
	 * Constructor for the player.
	 * @param playerJFrame The JFrame of the game that the player should be added to.
	 */
	public DungeonPlayer(JFrame playerJFrame) {
		homeFrame = playerJFrame;
		playerX = playerJFrame.getWidth()/2;
		playerY = playerJFrame.getHeight()/2;

		newPosition = new Point(playerX, playerY);

		
		//Draw the player for the first time
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
		playerAvatar.setIcon(playerSprite);
		playerAvatar.setBounds(playerX, playerY, 50, 50);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);
	    
	}
	
	/**
	 * Check which row and column a specified point is in.
	 * @param x 
	 * @param y
	 */
	private void getLocal(int x, int y) {
    	//This variable is the number of columns.
		int numberOfBoxes = DungeonGame.gridWidth;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }

	/**
	 * Move the player by a specified amount in both x and y
	 * @param dx The movement along the x axis
	 * @param dy The movement along the y axis
	 */
   	public void movePlayer(int dx, int dy){

		// update player's position
		playerX += (int) dx;
		playerY += (int) dy;

		drawPlayer();
	}

	/**
	 * Set the target location for the player to move towards
	 * @param x The x coordinate of the target location
	 * @param y The y coordinate of the target location
	 */
	public void setNewTarget(Point p){
		newPosition.setLocation(p.getX(), p.getY());
	}
	
	/**
	 * Getter for NewPosition
	 * @return The current target location for the player to move to.
	 */
	public Point getNewTarget(){
		return newPosition;
	}

	/**
	 * Getter for PlayerX
	 * @return playerX
	 */
	public int getX(){
		return playerX;
	}

	/**
	 * Getter for PlayerY
	 * @return playerY
	 */
	public int getY(){
		return playerY;
	}

	/**
	 * Redraw the player at the current player X and Y.
	 */
	public void drawPlayer() {

		playerAvatar.setBounds(playerX-5, playerY-5, 50, 50);
		homeFrame.repaint();
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
