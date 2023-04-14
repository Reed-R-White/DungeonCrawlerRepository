/**
 * The EnemyPlayer class represents an enemy in the game. 
 * It extends the Playerclass, and can either follow the player, attack the player,
 * or move in a pre-defined pattern.
 */

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EnemyPlayer extends Player {
	private List<Integer> movementPatternX;
	private List<Integer> movementPatternY;
	private int movementIndex;
	Boolean follow;
	private DungeonPlayer player;
	private JLabel enemyAvatar;
	private int attack;
	JFrame homeFrame;
	Obstacle[] obstacleArr;
	private int health;

	/**
	 * Constructor for the EnemyPlayer class. Initializes the starting position of
	 * the enemy, its size, and its movement pattern. Also loads the image for the
	 * enemy.
	 * 
	 * @param gameJFrame       The JFrame where the enemy is placed.
	 * @param startingX        The starting X position of the enemy.
	 * @param startingY        The starting Y position of the enemy.
	 * @param width            The width of the enemy.
	 * @param height           The height of the enemy.
	 * @param movementPatternX The X values of the movement pattern of the enemy.
	 * @param movementPatternY The Y values of the movement pattern of the enemy.
	 */
	public EnemyPlayer(JFrame gameJFrame, float startingX, float startingY, float width, float height, int health,
			ArrayList<Integer> movementPatternX, List<Integer> movementPatternY, int attack, DungeonPlayer player, Obstacle[] obstacleArr) {
		super(gameJFrame, startingX, startingY, width, height);
		homeFrame = gameJFrame;
		follow = false;
		color = Color.red;
		speed = 0.5f;
		
		//Draw the player for the first time
		enemyAvatar = new JLabel();
		ImageIcon enemySprite = new ImageIcon("src/Game/enemySprite.png");
		enemyAvatar.setIcon(enemySprite);
		enemyAvatar.setBounds((int)posX, (int)posX, 50, 50);
		enemyAvatar.setVisible(true);
		homeFrame.add(enemyAvatar);
		
		
		this.player = player;
		this.movementPatternX = movementPatternX;
		this.movementPatternY = movementPatternY;
		this.attack = attack;
		this.obstacleArr = obstacleArr;
		this.health = health;

		// Reverses the movement pattern so the enemy will move back and forth instead
		// of just looping.
		for (int i = movementPatternX.size() - 1; i >= 0; i--) {
			movementPatternX.add(movementPatternX.get(i) * -1);
		}

		for (int i = movementPatternY.size() - 1; i >= 0; i--) {
			movementPatternY.add(movementPatternY.get(i) * -1);
		}

		movementIndex = 0;

		// Load the enemy sprite.
		//try {
			//enemyAvatar = ImageIO.read(new File("src/Game/enemySprite.png"));
		//} catch (IOException FileNotFoundException) {
			//System.out.println(FileNotFoundException + " resulted in failure");
		//}

	}

	/**
	 * Moves the enemy towards the player if it is following, or follows a movement
	 * pattern otherwise.
	 */
	public void move() {
		// if you are following the player
		if (follow) {
			
			// if enemy pos x is greater than player pos x
			if (posX > player.getX()) {
				// subtract speed from pos x
				if (posX - speed >= 0 && posX - speed <= DungeonGame.GAMEWINDOWSIZE - width  && !collidesWithObstacle(posX - speed, posY)) {
					posX -= speed;
				}
				
			
				// if enemy pos x is less than player pos x
			} else if (posX < player.getX()) {
				// add speed to pos x
				if (posX + speed >= 0 && posX + speed <= DungeonGame.GAMEWINDOWSIZE - width && !collidesWithObstacle(posX + speed, posY)) {
					posX += speed;
				}
			}
			// if enemy pos y is greater than player pos y
			if (posY > player.getY()) {
				// subtract speed from pos y
				if (posY - speed >= 0 && posY - speed <= DungeonGame.GAMEWINDOWSIZE - height && !collidesWithObstacle(posX, posY - speed)) {
					posY -= speed;
				}
				// if enemy pos y is less than player pos y
			} else if (posY < player.getY()) {
				// add speed to pos y
				if (posY + speed >= 0 && posY + speed <= DungeonGame.GAMEWINDOWSIZE - height && !collidesWithObstacle(posX, posY + speed)) {
					posY += speed;
				}
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
			if(posX + deltaX * speed >= 0 && posX + deltaX * speed <= DungeonGame.GAMEWINDOWSIZE - width && !collidesWithObstacle(posX + deltaX * speed, posY)) {
				posX += deltaX * speed;
			}
			// set pos y
			if(posY + deltaY * speed >= 0 && posY + deltaY * speed <= DungeonGame.GAMEWINDOWSIZE - width && !collidesWithObstacle(posX, posY + deltaY * speed)) {
				posY += deltaY * speed;
			}
			// increment movement index
			movementIndex += 1;
		}

	}

	/**
	 * Set internal behavior, whether or not the enemy should follow the player
	 * based on the player's proximity. Should be called every time the player moves
	 * 
	 * @param player the player object to check proximity against
	 */
	public void checkPlayer(DungeonPlayer player) {
		// calculate distance from player
		double distance = Math.sqrt(Math.pow(posX - player.getX(), 2) + Math.pow(posY - player.getY(), 2));
		// if the distance from player is 200 or less
		if (distance <= 200) {
			// set follow to true
			follow = true;
			// this.player = player;
		} else {
			// set follow to false
			follow = false;
			// this.player = null;
		}
	}

	public void attack(DungeonPlayer player) {
		if (player.getX() >= posX && player.getX() <= posX + width && player.getY() >= posY
				&& player.getY() <= posY + height && player.getInvincibility() == 0) {

			player.takeDamage(attack);

		}
	}

	/**
	 * Draws the enemy's avatar to the screen.
	 */
	public void drawPlayer() {
		enemyAvatar.setBounds((int)posX - 5, (int)posY - 5, 50, 50);
		
		
		/*
		// This code is adapted from an online example to fix image flicker.

		// Create an off-screen image buffer
		Image offScreenImage = homeFrame.createImage(homeFrame.getWidth(), homeFrame.getHeight());
		Graphics offScreenGraphics = offScreenImage.getGraphics();

		// Clear the previous image
		offScreenGraphics.clearRect(0, 0, homeFrame.getWidth(), homeFrame.getHeight());

		// Draw the new image on the off-screen buffer
		offScreenGraphics.drawImage(enemyAvatar, (int) posX, (int) posY, null);

		// Paint the off-screen buffer on the screen
		homeFrame.getGraphics().drawImage(offScreenImage, 0, 0, null);
		
		//homeFrame.getGraphics().setColor(Color.GREEN);
		//homeFrame.getGraphics().fillRect(50, 50, 100, 50);*/
		
	}
	
	private boolean collidesWithObstacle(float x, float y) {
		for(int i = 0; i < obstacleArr.length; i++) {
			Obstacle obstacle = obstacleArr[i];
			for(int r = 0; r < height; r++) {
				for(int c = 0; c < width; c++) {
					if(obstacle != null && obstacle.checkCollision(x + c, y + r)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	public boolean isAlive() {
		return health > 0;
	}
	

}
