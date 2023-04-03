package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class EnemyPlayer extends Player {
	private List<Integer> movementPatternX;
	private List<Integer> movementPatternY;
	private int movementIndex;
	private int boolean follow;
	private int DungeonPlayer player;
	private Image enemyAvatar;
	JFrame homeFrame;
	
	/**
	 * Constructor for the EnemyPlayer class. Initializes the starting position of the enemy, its size, and its movement pattern. 
	 * Also loads the image for the enemy. 
	 * 
	 * @param gameJFrame The JFrame where the enemy is placed.
	 * @param startingX The starting X position of the enemy.
	 * @param startingY The starting Y position of the enemy.
	 * @param width The width of the enemy.
	 * @param height The height of the enemy.
	 * @param movementPatternX The X values of the movement pattern of the enemy.
	 * @param movementPatternY The Y values of the movement pattern of the enemy.
	 */
	public EnemyPlayer(JFrame gameJFrame, float startingX, float startingY, float width, float height,
	        List<Integer> movementPatternX, List<Integer> movementPatternY) {
	    super(gameJFrame, startingX, startingY, width, height);
	    homeFrame = gameJFrame;
	    follow = false;
	    color = Color.red;
	    speed = 0.5f;
	    this.movementPatternX = movementPatternX;
	    this.movementPatternY = movementPatternY;
	
	    // Reverses the movement pattern so the enemy will move back and forth instead of just looping.
	    for (int i = movementPatternX.size() - 1; i >= 0; i--) {
	        movementPatternX.add(movementPatternX.get(i) * -1);
	    }
	
	    for (int i = movementPatternY.size() - 1; i >= 0; i--) {
	        movementPatternY.add(movementPatternY.get(i) * -1);
	    }
	
	    movementIndex = 0;
	
	    // Load the enemy sprite.
	    try {
	        enemyAvatar = ImageIO.read(new File("src/Game/enemySprite.png"));
	    } catch (IOException FileNotFoundException) {
	        System.out.println(FileNotFoundException + " resulted in failure");
	    }
	    
	}
	
	/**
	 * Moves the enemy towards the player if it is following, or follows a movement pattern otherwise.
	 */
	public void move() {
		// if you are following the player
		if (follow) {
			// set initial x and y to 0
			float deltaX = 0;
			float deltaY = 0;
			// if enemy pos x is greater than player pos x
			if (posX > DungeonPlayer.playerX) {
				// subtract speed from pos x
				posX -= speed;
				// subtract 1 from delta x
				deltaX = -1;
			// if enemy pos x is less than player pos x 
			} else if (posX < player.playerX) {
				// add speed to pos x
				posX += speed;
				// add 1 to delta x
				deltaX = 1;
			}
			// if enemy pos y is greater than player pos y
			if (posY > player.playerY) {
				// subtract speed from pos y
				posY -= speed;
				// subtract 1 from delta y
				deltaY = -1;
			// if enemy pos y is less than player pos y
			} else if (posY < player.playerY) {
				// add speed to pos y
				posY += speed;
				// set delta y to 1
				deltaY = 1;
			}

		} else {
			// if you go out of bounds 
			if (movementIndex >= movementPatternX.size()) {
				// set movement index to 0
				movementIndex = 0;
			}
			// set delta x to index value
			int deltaX = movementPatternX.get((int) movementIndex);
			// set delta y to index value
			int deltaY = movementPatternY.get((int) movementIndex);
			// set pos x 
			posX += deltaX * speed;
			// set pos y
			posY += deltaY * speed;
			// increment movement index 
			movementIndex += 1;
		}

	}

	/**
	 * Set internal behavior, whether or not the enemy should follow the player based on the player's proximity.
	 * Should be called every time the player moves
	 * @param player the player object to check proximity against
	 */
	public void checkPlayer(DungeonPlayer player) {
		// calculate distance from player
		double distance = Math.sqrt(Math.pow(posX - player.playerX, 2) + Math.pow(posY - player.playerY, 2));
		// if the distance from player is 200 or less
		if (distance <= 200) {
			// set follow to true
			follow = true;
			//this.player = player;
		} else {
			// set follow to false
			follow = false;
			//this.player = null;
		}
	}
	
	/**
	 * Draws the enemy's avatar to the screen.
	 */
	public void drawPlayer() {
		// This code is adapted from an online example to fix image flicker.
		
		// Create an off-screen image buffer
	    Image offScreenImage = homeFrame.createImage(homeFrame.getWidth(), homeFrame.getHeight());
	    Graphics offScreenGraphics = offScreenImage.getGraphics();
	    
	    // Clear the previous image
	    offScreenGraphics.clearRect(0, 0, homeFrame.getWidth(), homeFrame.getHeight());
	    
	    // Draw the new image on the off-screen buffer
	    offScreenGraphics.drawImage(enemyAvatar, (int)posX, (int)posY, null);
	    
	    // Paint the off-screen buffer on the screen
	    homeFrame.getGraphics().drawImage(offScreenImage, 0, 0, null);
	}

}
	

