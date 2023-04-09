/**
 * The DungeonPlayer class represents the player of a game. 
 * It allows the player to move their avatar in response to mouse movements 
 * and updates the player's position on a timer. The class also handles the player's health 
 * and damage taken, and renders the player's avatar on the game screen.
*/

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
 * A player object, with coordinates, movement capabilities, and a CheckLocal
 * function.
 */
public class DungeonPlayer {

	public static final int MAXMOVEMENT = 7;
	public int playerX;
	public int playerY;
	public int health;

	private Point newPosition;
	private Point currentPosition;
	private JLabel playerAvatar;
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
		newPosition = new Point(playerX, playerY);
		health = 100;
		invincibilityCounter = 0;

		// Add a MouseMotionListener to the homeFrame to track the player's movement
		homeFrame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				newPosition.setLocation(e.getX(), e.getY());
			}
		});

		// Create a Timer to handle the player's movement
		ActionListener movementPerSecond = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPosition = new Point(playerX, playerY);

				// Calculate the distance between currentPosition and newPosition
				double distance = currentPosition.distance(newPosition);

				// If the distance is greater than 5 pixels, move the player
				if (distance > 5) {
					// Calculate the amount to move in each direction based on MAXMOVEMENT
					double dx = MAXMOVEMENT * (newPosition.getX() - currentPosition.getX()) / distance;
					double dy = MAXMOVEMENT * (newPosition.getY() - currentPosition.getY()) / distance;

					// Update the player's position
					playerX += (int) dx;
					playerY += (int) dy;
				}
				drawPlayer();
			}
		};

		Timer timer = new Timer(10, movementPerSecond);
		timer.start();

		// Load the player's avatar from a file
		//playerAvatar = ImageIO.read(new File("src/Game/playerSprite.png"));
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
		playerAvatar.setIcon(playerSprite);
		playerAvatar.setBounds(playerX, playerY, 50, 50);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);
	}

	/**
	 * Calculates the player's position in the game grid.
	 *
	 * @param x the x-coordinate of the player's position
	 * @param y the y-coordinate of the player's position
	 */
	private void getLocal(int x, int y) {
		int numberOfBoxes = 20;
		int boxLength = homeFrame.getWidth() / numberOfBoxes;
		int xIndex = x / boxLength;
		int yIndex = y / boxLength;
		System.out.println("The player is in Column: " + xIndex + " Row: " + yIndex);
	}

	/**
	 * This method handles the user mouse movement event, which updates the player's
	 * coordinates on the screen based on the location of the mouse pointer and the
	 * panel location on the screen.
	 * 
	 * @param e an ActionEvent object representing the mouse movement event
	 */
	public void actionPerformed(ActionEvent e) {
		Point mouseSpot = MouseInfo.getPointerInfo().getLocation();
		Point panelLocation = homeFrame.getLocationOnScreen();
		playerX = (int) (mouseSpot.getX() - panelLocation.getX());
		playerY = (int) (mouseSpot.getY() - panelLocation.getY());
	}

	/**
	 * This method draws the player's avatar on an off-screen image buffer to
	 * prevent image flickering. The off-screen buffer is then painted on the
	 * screen. ======= Constructor for the player.
	 * 
	 * @param playerJFrame The JFrame of the game that the player should be added
	 *                     to.
	 */
//	public DungeonPlayer(JFrame playerJFrame) {
//		homeFrame = playerJFrame;
//		playerX = playerJFrame.getWidth()/2;
//		playerY = playerJFrame.getHeight()/2;
//
//		newPosition = new Point(playerX, playerY);
//
//		
//		//Draw the player for the first time
//		playerAvatar = new JLabel();
//		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
//		playerAvatar.setIcon(playerSprite);
//		playerAvatar.setBounds(playerX, playerY, 50, 50);
//		playerAvatar.setVisible(true);
//		playerJFrame.add(playerAvatar);
//	    
//	}
// xxxx
	/**
	 * Check which row and column a specified point is in.
	 * 
	 * @param x
	 * @param y
	 */

//	private void getLocal(int x, int y) {
//    	//This variable is the number of columns.
//		int numberOfBoxes = DungeonGame.gridWidth;
//    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
//    	int xIndex = x/boxLength;
//    	int yIndex = y/boxLength;
//    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
//   }
// xxxx
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

	/**
	 * Redraw the player at the current player X and Y. >>>>>>> main
	 */
	public void drawPlayer() {

		playerAvatar.setBounds(playerX - 5, playerY - 5, 50, 50);
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
	public void takeDamage(int attack) {
		health -= attack;
		invincibilityCounter = 500;
	}

	public int getMaxMovement() {
		return MAXMOVEMENT;
	}

}
