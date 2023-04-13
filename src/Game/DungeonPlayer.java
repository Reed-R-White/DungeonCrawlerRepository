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
import javax.swing.Timer;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Reed White
 */
public class DungeonPlayer {
	
	/* The maximum movement distance for the player */
	public int MAXMOVEMENT = 3;
	/* The amount of damage done to an enemy by the player */
	private int PLAYERDAMAGE = 5;
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
		
		homeFrame.addMouseMotionListener(new MouseMotionAdapter() {
	        /*
	         * Updates the new position of the player based on mouse movement
	         */
	        public void mouseMoved(MouseEvent e) {
	        	setNewTarget(new Point(e.getX(), e.getY()));
	        }
	    });
		
		ActionListener movementPerSecond = new ActionListener() {
			/*
			 * Moves the player a maximum distance of MAXMOVEMENT each second
			 */
			public void actionPerformed(ActionEvent e) {
				currentPosition = new Point(playerX, playerY);
			};
		};
		Timer timer = new Timer(10, movementPerSecond);
		timer.start();

		
		//Draw the player for the first time
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
		ImageIcon playerIdle = new ImageIcon("src/Game/playerIdle.png");
		playerAvatar.setIcon(playerIdle);
		playerAvatar.setBounds(playerX, playerY, 32, 32);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);
	    
	}
	
	/**
	 * Check which row and column a specified point is in.
	 * Currently unused code.
	 * @param x 
	 * @param y
	 */
	/*private void getLocal(int x, int y) {
    	//This variable is the number of columns.
		int numberOfBoxes = DungeonGame.gridWidth;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }// */

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
	
	public int getHealth() {
		return playerHealth;
	}
	
	public void takeDamage(int damageAmount) {
		playerHealth -= damageAmount;
	}

	/**
	 * Redraw the player at the current player X and Y.
	 */
	public void drawPlayer() {
		playerAvatar.setBounds(playerX-25, playerY-45, 32, 32);
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

	    // Calculate the rectangle emanating from the player in the direction of the mouse
	    double rectWidth = 90;
	    double rectHeight = 10;
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
	
	public boolean sweepAttack(Point point) {
		double ROTATIONCONSTANT = 0.25;
		
		Graphics g = homeFrame.getGraphics();
	    Graphics2D g2d = (Graphics2D) g;
		
	    // Get the direction from the player to the mouse
	    
	    double deltaX = newPosition.getX() - playerX;
	    double deltaY = newPosition.getY() - playerY;
	    
	    int rotationPointAdjustmentX = 3*Integer.signum((int) deltaX);
	    int rotationPointAdjustmentY = 3*Integer.signum((int) deltaY);
	    Point sweepRotationPoint = new Point((playerX + rotationPointAdjustmentX), (playerY + rotationPointAdjustmentY));

	    // Calculate the rectangle emanating from the player in the direction of the mouse
	    double rectWidth = 15;
	    double rectHeight = 15;
	    double rectX = playerX- 30;
	    double rectY = (playerY- 30 - rectHeight / 2.0);

	    // Rotate the rectangle to match the angle of the mouse in relation to the player
	    double angle = Math.atan2(deltaY, deltaX);
	    
	    
	    Point bottomLeft = new Point((int) rectX, (int) rectY);
	    Point topLeft = new Point((int) rectX, (int) (rectY + rectHeight));
	    Point bottomRight = new Point((int) (rectX + rectWidth), (int) rectY);
	    Point topRight = new Point((int) (rectX + rectWidth), (int) (rectY + rectHeight));
	    Point[] recPoints = new Point[] {bottomLeft, topLeft, bottomRight, topRight};
	    rotateRectangle(recPoints,sweepRotationPoint, (angle+ROTATIONCONSTANT*5));
	    
	    int[] xCords = new int[] {(int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(), (int) bottomRight.getX()}; 
	    int[] yCords = new int[] {(int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(), (int) bottomRight.getY()};
	    
	    Polygon hitbox = new Polygon(xCords, yCords, 4);
	    if (hitbox.contains(point)) {
	    	return true;
	    }
	    
	    g2d.draw(hitbox);
	    
	    for (int i = 0; i < 5; i++) {
	    	rotateRectangle(recPoints, sweepRotationPoint, ROTATIONCONSTANT);
		    
		    xCords = new int[] {(int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(), (int) bottomRight.getX()}; 
		    yCords = new int[] {(int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(), (int) bottomRight.getY()};
		    
		    hitbox = new Polygon(xCords, yCords, 4);
		    if (hitbox.contains(point)) {
		    	return true;
		    }
		    
		    g2d.draw(hitbox);
	    }
	    
	    return hitbox.contains(point);
	    // Check if the point is in the rectangle
	}
	
}
