/**
 * The EnemyPlayer class represents an enemy in the game. 
 * It extends the Playerclass, and can either follow the player, attack the player,
 * or move in a pre-defined pattern.
 */

package Game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EnemyPlayer extends Player {
	private List<Integer> movementPatternX;
	private List<Integer> movementPatternY;
	private int movementIndex;
	Boolean follow;
	private DungeonPlayer player;
	private JLabel enemyAvatar;
	private JLabel healthBar;
	private int attack;
	JFrame homeFrame;
	Obstacle[] obstacleArr;
	private int enemyHealth;

	private final static int PLAYER_FOLLOW_DISTANCE = 200;

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
	public EnemyPlayer(JFrame gameJFrame, float startingX, float startingY, float width, float height,
			int enemyHealth, int attack,
			DungeonPlayer player, Obstacle[] obstacleArr) {
		super(gameJFrame, startingX, startingY, width, height);
		
		homeFrame = gameJFrame;
		follow = false;
		color = Color.red;
		speed = 0.5f;

		
		
		this.player = player;
		this.attack = attack;
		this.obstacleArr = obstacleArr;
		this.enemyHealth = enemyHealth;
		this.healthBar = new JLabel("" + enemyHealth);
		
		movementPatternX = new ArrayList<Integer>();
		movementPatternY = new ArrayList<Integer>();

		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			int directionX = random.nextInt(3) - 1;
			int directionY = random.nextInt(3) - 1;
			for (int j = 0; j < 40; j++) {
				movementPatternX.add(directionX);
				movementPatternY.add(directionY);
			}

		}

		follow = false;
		color = Color.red;
		posX = startingX;
		posY = startingY;

		// NOte for later: go forwards or move into upper section

		for (int i = movementPatternX.size() - 1; i >= 0; i--) {
			movementPatternX.add(movementPatternX.get(i) * -1);
		}

		for (int i = movementPatternY.size() - 1; i >= 0; i--) {
			movementPatternY.add(movementPatternY.get(i) * -1);
		}

		movementIndex = 0;
		
//		this.movementPatternX = movementPatternX;
//		this.movementPatternY = movementPatternY;

		// Draw the Enemy for the first time
		enemyAvatar = new JLabel();
		ImageIcon enemySprite = new ImageIcon("src/Game/enemySprite.png");
		enemyAvatar.setIcon(enemySprite);
		enemyAvatar.setBounds((int) posX, (int) posX, 32, 32);
		enemyAvatar.setVisible(true);
		gameJFrame.add(enemyAvatar);
		gameJFrame.add(healthBar);
		
		// Reverses the movement pattern so the enemy will move back and forth instead
		// of just looping.

	}

	public void takeDamage(int damageAmount) {
		enemyHealth -= damageAmount;
		if(enemyHealth <= 0) {
			gameJFrame.remove(enemyAvatar);
			gameJFrame.remove(healthBar);
		}
	}

	public float getX() {
		return posX;
	}

	public float getY() {
		return posY;

	}
	
	public int getEnemyHealth() {
		return enemyHealth;
	}
	
	public JLabel getJLabel() {
		return enemyAvatar;
	}

	/**
	 * Moves the enemy towards the player if it is following, or follows a movement
	 * pattern otherwise.
	 */
	public void move() {
		checkPlayer(player);

		if (follow) {

			if (posX > player.getX()) {
				// subtract speed from pos x
				if (posX - speed >= 0 && posX - speed <= DungeonGame.GAMEWINDOWSIZE - width
						&& !collidesWithObstacle(posX - speed, posY)) {
					posX -= speed;
				}

			} else if (posX < player.getX()) {
				// add speed to pos x
				if (posX + speed >= 0 && posX + speed <= DungeonGame.GAMEWINDOWSIZE - width
						&& !collidesWithObstacle(posX + speed, posY)) {
					posX += speed;
				}
			}

			if (posY > player.getY()) {
				// subtract speed from pos y
				if (posY - speed >= 0 && posY - speed <= DungeonGame.GAMEWINDOWSIZE - height
						&& !collidesWithObstacle(posX, posY - speed)) {
					posY -= speed;
				}

			} else if (posY < player.getY()) {
				// add speed to pos y
				if (posY + speed >= 0 && posY + speed <= DungeonGame.GAMEWINDOWSIZE - height
						&& !collidesWithObstacle(posX, posY + speed)) {
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
			if (posX + deltaX * speed >= 0 && posX + deltaX * speed <= DungeonGame.GAMEWINDOWSIZE - width
					&& !collidesWithObstacle(posX + deltaX * speed, posY)) {
				posX += deltaX * speed;
			}
			// set pos y
			if (posY + deltaY * speed >= 0 && posY + deltaY * speed <= DungeonGame.GAMEWINDOWSIZE - width
					&& !collidesWithObstacle(posX, posY + deltaY * speed)) {
				posY += deltaY * speed;
			}
			// increment movement index
			movementIndex += 1;
		}
		drawEnemy();
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
			follow = true; // set follow to true
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

	private boolean collidesWithObstacle(float x, float y) {
		for (int i = 0; i < obstacleArr.length; i++) {
			Obstacle obstacle = obstacleArr[i];
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					if (obstacle != null && obstacle.checkCollision(x + c, y + r)) {
						return true;
					}
				}
			}

		}
		return false;
	}

	public boolean isAlive() {
		return enemyHealth > 0;
	}

	private boolean checkPlayer() {
		return Math
				.sqrt(Math.pow(posX - player.getX(), 2) + Math.pow(posY - player.getY(), 2)) <= PLAYER_FOLLOW_DISTANCE;
	}

	public void drawEnemy() {
		enemyAvatar.setBounds((int) posX, (int) posY, 32, 32);
		healthBar.setText("" + enemyHealth);
		healthBar.setBounds((int) posX + 10, (int) posY - 25, 32, 32);
		gameJFrame.repaint();
	}
}
