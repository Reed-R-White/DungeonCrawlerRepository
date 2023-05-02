//Fully commented.

/**
 * The EnemyPlayer class represents an enemy in the game. 
 * It extends the Playerclass, and can either follow the player, attack the player,
 * or move in a pre-defined pattern.
 */

package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


import java.awt.Color;


/**
 * The enemy class extends player, and contains similar capabilites as the user-controlled player.
 * The main difference is that, unless in follow mode, the enemy will follow a simple pre-defined movement pattern.
 * 
 * @authors Ryan O'Valley, Reed White, Charlie Said
 */
public class EnemyPlayer extends Player {
	private List<Integer> movementPatternX;
	private List<Integer> movementPatternY;
	private int movementIndex;
	private Boolean follow;
	private int boredom;
	private DungeonPlayer player;
	private JLabel enemyAvatar;
	private JLabel healthBar;
	private int damage;
	private final float ENEMYSPEED = (float) 1;
	JFrame homeFrame;
	Obstacle[] obstacleArr;
	private int enemyHealth;
	public final int ENEMYSIZE = 32;
	
	Clip monsterClip;
	float deltaX = 0, deltaY = 0;


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
			int enemyHealth, int damage,
			DungeonPlayer player, Obstacle[] obstacleArr) {
		super(gameJFrame, startingX, startingY, width, height);
		
		homeFrame = gameJFrame;
		follow = false;
		color = Color.red;

		
//		try {
//			AudioInputStream aud = AudioSystem.getAudioInputStream(this.getClass().getResource("monster.wav"));
//			this.monsterClip = AudioSystem.getClip();
//			this.monsterClip.open(aud);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		this.player = player;
		this.damage = damage;
		this.obstacleArr = obstacleArr;
		this.enemyHealth = enemyHealth;
		
		
		//Set up the random movement patterns
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
		for (int i = movementPatternX.size() - 1; i >= 0; i--) {
			movementPatternX.add(movementPatternX.get(i) * -1);
		}
		for (int i = movementPatternY.size() - 1; i >= 0; i--) {
			movementPatternY.add(movementPatternY.get(i) * -1);
		}
		movementIndex = 0;

		// Draw the Enemy for the first time
		enemyAvatar = new JLabel();
		ImageIcon enemySprite = new ImageIcon("src/Game/Ork.png");
		enemyAvatar.setIcon(enemySprite);
		enemyAvatar.setBounds((int) posX, (int) posX, ENEMYSIZE, ENEMYSIZE);
		enemyAvatar.setVisible(true);
		gameJFrame.add(enemyAvatar);
		healthBar = new JLabel("" + enemyHealth);
		gameJFrame.add(healthBar);
	}

	/**
	 * The enemy's health is reduced by an amount equal to the given ammount
	 * 
	 * @param damageAmount The amount to reduce the enemy's health by
	 */
	public void takeDamage(int damageAmount) {
		enemyHealth -= damageAmount;
		healthBar.setText(""+enemyHealth);

		//Check if the enemy is dead.  If it is, remove it.
		if(enemyHealth <= 0) {
			gameJFrame.remove(enemyAvatar);
			gameJFrame.remove(healthBar);
		}
	}

	/**
	 * Getter for the enemy's X-coordinate
	 * 
	 * @return the enemy's X-coordinate
	 */
	public float getX() {
		return posX;
	}

	/**
	 * Getter for the enemy's Y-coordinate
	 * 
	 * @return the enemy's Y-coordinate
	 */
	public float getY() {
		return posY;
	}

	/**
	 * Getter for the enemy's delta along the X axis.
	 * The delta is set by the determineDeltas method, but defaults to 0.
	 * The delta is how much the enemy plans to move, not how much it has or is moving.
	 * 
	 * @return the enemy's delta along the X axis
	 */
	public float getDeltaX(){
		return deltaX;
	}

	/**
	 * Setter for the enemy's deltaX.
	 * DeltaX is the amount by which the enemy will move along the X axis when moveX is called.
	 * 
	 * @param deltaX the new value to set deltaX to
	 */
	public void setDeltaX(float deltaX){
		this.deltaX = deltaX;
	}

	/**
	 * Getter for the enemy's delta along the Y axis.
	 * The delta is set by the setDeltas method, but defaults to 0.
	 * The delta is how much the enemy plans to move, not how much it has or is moving.
	 * 
	 * @return the enemy's delta along the Y axis
	 */
	public float getDeltaY(){
		return deltaY;
	}

	/**
	 * Setter for the enemy's deltaY.
	 * DeltaX is the amount by which the enemy will move along the X axis when moveY is called.
	 * 
	 * @param deltaX the new value to set deltaX to
	 */
	public void setDeltaY(float deltaY){
		this.deltaY = deltaY;
	}

	/**
	 * Getter for the enemy's size (which is both its width and height)
	 * 
	 * @return enemy size
	 */
	public int getEnemySize(){
		return ENEMYSIZE;
	}
	
	/**
	 * Getter for the enemy's health.  Note that this is its current health, not its maximum health
	 * 
	 * @return enemy current health
	 */
	public int getEnemyHealth() {
		return enemyHealth;
	}

	/**
	 * Getter for the enemy's JLabel
	 * 
	 * @return enemy avatar JLabel
	 */
	public JLabel getJLabel() {
		return enemyAvatar;
	}

	/**
	 * Determines where the enemy ought to move to next.  
	 * If in following mode, the enemy will move towards the player.
	 * Otherwise, the enemy will follow its predetermined course.
	 */
	public void determineDeltas() {

		//Calculate deltas if the enemy is near enough to follow the player
		if (follow) {
			if (posX > player.getX()){
				deltaX = -1*ENEMYSPEED;
			} else if (posX < player.getX()){
				deltaX = ENEMYSPEED;
			} else if (posX == player.getX()){
				deltaX = 0;
			}
			if (posY > player.getY()){
				deltaY = -1*ENEMYSPEED;
			} else if (posY < player.getY()){
				deltaY = ENEMYSPEED;
			} else if (posY == player.getY()){
				deltaY = 0;
			}

		} //Otherwise, follow the wandering pattern
		else {
			// if you go out of bounds in the random-movement array,
			if (movementIndex >= movementPatternX.size()) {
				// set movement index to 0
				movementIndex = 0;
			}
			
			//Set the deltas to the next int in the movement pattern.
			deltaX = movementPatternX.get((int) movementIndex)*ENEMYSPEED;
			deltaY = movementPatternY.get((int) movementIndex)*ENEMYSPEED;
			
			// increment movement index
			movementIndex += 1;
		}
	}

	/**
	 * Moves the enemy by its deltas along the X axis, then redraws it
	 */
	public void moveX(){
		posX += deltaX;
		
		drawEnemy();
	}

	/**
	 * Moves the enemy by its deltas along the Y axis, then redraws it
	 */
	public void moveY(){
		posY += deltaY;
		
		drawEnemy();
	}

	/**
	 * Method to increase the enemy's boredom by 1.
	 */
	public void incrementBoredom(){
		boredom++;
	}

	/**
	 * Method to reduce the enemy's boredom by 1.
	 * Boredom cannot go below 0.  If boredom would be reduced below 0, the method does nothing.
	 */
	public void decrementBoredom(){
		if (boredom >0){
			boredom--;
		}
	}

	/**
	 * Getter for boredom
	 * 
	 * @return The enemy's current boredom.
	 */
	public int getBoredom(){
		return boredom;
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
		if (distance <= PLAYER_FOLLOW_DISTANCE) {
			follow = true;
		} else {
			follow = false;
		}

		if (boredom > 500){
			follow = false;
		}

		if (boredom > 3000){
			boredom = 0;
		}
	}

	/**
	 * Attempt to damage the player
	 * 
	 * @param player the player to deal damage to
	 */
	public void attack(DungeonPlayer player) {

		if (enemyHealth > 0){
			//monsterClip.start();
			player.takeDamage(damage);
		}
	}

	/**
	 * Checks if a certain x,y point collides with an Obstacle, by checking each object in the Obstacle.
	 * 
	 * @param x the x-coord of the point to check.
	 * @param y the y-coord of the point to check.
	 * @return true if there was a collision, false otherwise.
	 */
	public boolean collidesWithObstacle(float x, float y) {
		for (int i = 0; i < obstacleArr.length; i++) {
			Obstacle obstacle = obstacleArr[i];
			if (obstacle != null && obstacle.checkCollision(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the enemy is still alive (if its health is greater than 0).
	 * 
	 * @return true if alive, false if not.
	 */
	public boolean isAlive() {
		return enemyHealth > 0;
	}

	/**
	 * Puts the enemy in the game at its X and Y coordinates
	 */
	public void drawEnemy() {
		enemyAvatar.setBounds((int) posX, (int) posY, ENEMYSIZE, ENEMYSIZE);
		healthBar.setText("" + enemyHealth);
		healthBar.setBounds((int) posX + 10, (int) posY - 25, 32, 32);
	}
}
