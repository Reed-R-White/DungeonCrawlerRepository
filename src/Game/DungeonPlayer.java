/**
 * The DungeonPlayer class represents the player of a game. 
 * It allows the player to move their avatar in response to mouse movements 
 * and updates the player's position on a timer. The class also handles the player's health 
 * and damage taken, and renders the player's avatar on the game screen.
*/

package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;

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

	private int playerDamage;

	/* The health of the player */
	private int playerHealth;
	/* The x-coordinate of the player */
	private int playerX;
	/* The y-coordinate of the player */
	private int playerY;
	/* The new position of the player */
	private Point newPosition;
	/* The current position of the player */

	private Point currentPosition;
	public int PLAYERSIZE = 32;
	
	private int xMouseOffsetToContentPaneFromJFrame;
	private int yMouseOffsetToContentPaneFromJFrame;
	
	private JLabel playerAvatar;
	private JLabel healthBar;
	private JFrame homeFrame;
	private int invincibilityCounter;

	/**
	 * <<<<<<< HEAD Constructor for the DungeonPlayer class.
	 *
	 * @param playerJFrame the JFrame containing the player
	 */
	public DungeonPlayer(JFrame playerJFrame) {
		homeFrame = playerJFrame;

		playerX = playerJFrame.getWidth() / 2;
		playerY = playerJFrame.getHeight() / 2;

		
		playerDamage = 10;
		

		newPosition = new Point(playerX, playerY);
		
		playerHealth = 100;
		healthBar = new JLabel("" + playerHealth);
		invincibilityCounter = 0;

		// Draw the player for the first time
		playerAvatar = new JLabel();
		
		
		playerAvatar.setBounds(playerX, playerY, PLAYERSIZE, PLAYERSIZE);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);

		playerJFrame.add(healthBar);

	    
		Container gameContentPane = homeFrame.getContentPane();

        // Event mouse position is given relative to JFrame, where
        // dolphin's image in JLabel is given relative to ContentPane,
        // so adjust for the border ( / 2  since border is on either side)
        int borderWidth = (homeFrame.getWidth() - gameContentPane.getWidth()) / 2;
        // assume side border = bottom border; ignore title bar
        xMouseOffsetToContentPaneFromJFrame = borderWidth;
        yMouseOffsetToContentPaneFromJFrame = homeFrame.getHeight() - gameContentPane.getHeight() - borderWidth;

	}

	/**
	 * Move the player by a specified amount in both x and y
	 * 
	 * @param dx The movement along the x axis
	 * @param dy The movement along the y axis
	 */
	public void movePlayer(int dx, int dy) {

		// update player's position
		playerX += (int) dx;
		playerY += (int) dy;

		drawPlayer();
	}

	/**
	 * Set the target location for the player to move towards
	 * 
	 * @param x The x coordinate of the target location
	 * @param y The y coordinate of the target location
	 */
	public void setNewTarget(Point p) {
		newPosition.setLocation(p.getX(), p.getY());
	}

	/**
	 * Getter for NewPosition
	 * 
	 * @return The current target location for the player to move to.
	 */
	public Point getNewTarget() {
		return newPosition;
	}

	/**
	 * Getter for PlayerX
	 * 
	 * @return playerX
	 */
	public int getX() {
		return playerX;
	}

	/**
	 * Getter for PlayerY
	 * 
	 * @return playerY
	 */
	public int getY() {
		return playerY;
	}

	public int getHealth() {
		return playerHealth;
	}


	public void playerTakeDamage(int damageAmount) {
		playerHealth -= damageAmount;
	}
	
	public int getDamage() {
		return playerDamage;
	}
	
	public void takeDamage(int damageAmount) {

		playerHealth -= damageAmount;
	}

	/**
	 * Redraw the player at the current player X and Y.
	 */
	public void drawPlayer() {
		playerAvatar.setBounds(playerX, playerY, PLAYERSIZE, PLAYERSIZE);
		healthBar.setText("" + playerHealth);
		healthBar.setBounds(playerX + 8, playerY - 25, PLAYERSIZE, PLAYERSIZE);
		homeFrame.repaint();

	}

	/**
	 * This method reduces the player's invincibility counter by 1 if it is greater
	 * than 0.
	 */
	public void reduceInvincibility() {
		if (invincibilityCounter > 0) {
			invincibilityCounter -= 1;
		}

	}

	/**
	 * This method returns the value of the player's invincibility counter.
	 * 
	 * @return an integer representing the player's invincibility counter
	 */
	public int getInvincibility() {
		return invincibilityCounter;

	}

	/**
	 * This method reduces the player's health by the specified amount and sets the
	 * invincibility counter to 500.
	 * 
	 * @param attack an integer representing the amount of damage taken by the
	 *               player
	 */
	public void enemyTakeDamage(int attack) {
		playerHealth -= attack;
		invincibilityCounter = 500;
	}

	public int getMaxMovement() {
		return MAXMOVEMENT;
	}

	/**
	 * Rotates a rectangle around a pivot point by a given angle.
	 * 
	 * @param rectangle an array of four points representing the corners of the
	 *                  rectangle
	 * @param pivot     the point around which to rotate the rectangle
	 * @param angle     the angle in radians by which to rotate the rectangle
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
	 * This method executes an attack action from the player towards the given
	 * point. The attack is executed by creating a rectangle projecting from the
	 * player towards the given point and checking whether a passed in point exists
	 * within this rectangle. If an intersection is found, the game object will
	 * receive {@link DungeonPlayer.PLAYERDAMAGE} points of damage.
	 *
	 * @param point the point towards which the player should execute the attack
	 * @return true if an attack was executed, false otherwise
	 */
	public boolean attack(Point point) {
		// Get the direction from the player to the mouse
		double deltaX = newPosition.getX() - playerX;
		double deltaY = newPosition.getY() - playerY;

		// Calculate the rectangle emanating from the player in the direction of the
		// mouse
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
	    
	    for (int i = 0; i < xCords.length; i++) {
	    	xCords[i] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
	    }
	    
	    for (int i = 0; i < yCords.length; i++) {
	    	yCords[i] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
	    }
	    
	    
	    
	    Polygon hitbox = new Polygon(xCords, yCords, 4);
	    System.out.println("///attacking from player X:"+ playerX + " Y: "+playerY+ " \n /// Rectangle at X:"+rectX+" Y: "+rectY);
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
	    
	    Point sweepRotationPoint = new Point((playerX + rotationPointAdjustmentX),
				(playerY + rotationPointAdjustmentY));

	    // Calculate the rectangle emanating from the player in the direction of the
		// mouse
		double rectWidth = 15;
		double rectHeight = 15;
		double rectX = playerX - 30;
		double rectY = (playerY - 30 - rectHeight / 2.0);

		// Rotate the rectangle to match the angle of the mouse in relation to the
		// player
		double angle = Math.atan2(deltaY, deltaX);

		Point bottomLeft = new Point((int) rectX, (int) rectY);
		Point topLeft = new Point((int) rectX, (int) (rectY + rectHeight));
		Point bottomRight = new Point((int) (rectX + rectWidth), (int) rectY);
		Point topRight = new Point((int) (rectX + rectWidth), (int) (rectY + rectHeight));
		Point[] recPoints = new Point[] { bottomLeft, topLeft, bottomRight, topRight };
		rotateRectangle(recPoints, sweepRotationPoint, (angle + ROTATIONCONSTANT * 5));

		int[] xCords = new int[] { (int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(),
				(int) bottomRight.getX() };
		int[] yCords = new int[] { (int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(),
				(int) bottomRight.getY() };

		for (int i = 0; i < xCords.length; i++) {
	    	xCords[i] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
	    }
	    
	    for (int i = 0; i < yCords.length; i++) {
	    	yCords[i] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
	    }
	    
	    Polygon hitbox = new Polygon(xCords, yCords, 4);
	    if (hitbox.contains(point)) {
	    	return true;
	    }
	    
	    g2d.draw(hitbox);
	    
	    for (int i = 0; i < 5; i++) {
	    	rotateRectangle(recPoints, sweepRotationPoint, ROTATIONCONSTANT);
		    
		    xCords = new int[] {(int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(), (int) bottomRight.getX()}; 
		    yCords = new int[] {(int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(), (int) bottomRight.getY()};
		    
		    for (int n = 0; n < xCords.length; n++) {
		    	xCords[n] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
		    }
		    
		    for (int n = 0; n < yCords.length; n++) {
		    	yCords[n] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE/2;
		    }
		    
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
