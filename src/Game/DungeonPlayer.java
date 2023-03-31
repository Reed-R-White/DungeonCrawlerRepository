package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * A player object, with coordinates, movement capabilities, and a CheckLocal function.
 */
public class DungeonPlayer {
	
	public static final int MAXMOVEMENT = 7;
	private int playerX;
	private int playerY;
	private Point newPosition;
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
	
}
