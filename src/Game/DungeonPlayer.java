
package Game;

import java.awt.Container;
import java.awt.Point;
import java.awt.Polygon;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * The class managing the player, including their health, attacks, and movement.
 * 
 * @author Reed White, Charlie Said, Ryan O'Valley
 */
public class DungeonPlayer {

	public int MAXMOVEMENT = 3;
	private final int INVINCIBILITYTIME = 50;
	private int slashDamage;
	private int stabDamage;
	private int playerHealth;
	private int playerX, playerY;
	private Point newPosition;

	public int PLAYERSIZE = 32;
	private int invincibilityCounter;

	private int xMouseOffsetToContentPaneFromJFrame;
	private int yMouseOffsetToContentPaneFromJFrame;

	private GameObject swordDrawBox;
	private JLabel playerAvatar;
	private JLabel healthBar;
	private JFrame homeFrame;
	private BufferedImage swordStab;
	private BufferedImage swordSlash;

	/**
	 * Constructor for the DungeonPlayer class.
	 * 
	 * The DungeonPlayer class represents the player of a game. It allows the player
	 * to move their avatar in response to mouse movements and updates the player's
	 * position on a timer. The class also handles the player's health and damage
	 * taken, and renders the player's avatar on the game screen.
	 *
	 * @param playerJFrame the JFrame containing the player
	 */
	public DungeonPlayer(JFrame playerJFrame, int X, int Y) {
		homeFrame = playerJFrame;

		playerX = X;
		playerY = Y;
		slashDamage = 20;
		stabDamage = 30;
		newPosition = new Point(playerX, playerY);
		invincibilityCounter = 0;

		// Draw the player for the first time
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerIdle.png");
		playerAvatar.setIcon(playerSprite);
		playerAvatar.setBounds(playerX, playerY, PLAYERSIZE, PLAYERSIZE);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);

		// Add the health bar
		playerHealth = 100;
		healthBar = new JLabel("" + playerHealth);
		playerJFrame.add(healthBar);

		swordStab = null;
		swordSlash = null;

		try {
			swordStab = ImageIO.read(new File("src/Game/swordStab.png"));
			swordSlash = ImageIO.read(new File("src/Game/shortSword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		swordDrawBox = new GameObject(swordStab, 110, 70, 0);
		homeFrame.add(swordDrawBox);
		swordDrawBox.setVisible(false);
		swordDrawBox.setBounds(playerX - 100, playerY - 100, 200, 200);

		// Calculate the offset required for the player sprite.
		Container gameContentPane = homeFrame.getContentPane();
		int borderWidth = (homeFrame.getWidth() - gameContentPane.getWidth()) / 2;
		xMouseOffsetToContentPaneFromJFrame = borderWidth;
		yMouseOffsetToContentPaneFromJFrame = homeFrame.getHeight() - gameContentPane.getHeight() - borderWidth;

	}

	/**
	 * Move the player by a specified amount along the x axis
	 * 
	 * @param dx The movement along the x axis
	 */
	public void movePlayerX(int dx) {
		// update player's position
		playerX += (int) dx;
		drawPlayer();
	}

	/**
	 * Move the player by a specified amount along the y axis
	 * 
	 * @param dy The movement along the y axis
	 */
	public void movePlayerY(int dy) {
		// update player's position
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

	public void toggleSwordVisual(Boolean toggleOption) {
		swordDrawBox.setVisible(toggleOption);
	}

	private void doSwordStabVisual(int initialAngle, int visiblityDuration) {
		swordDrawBox.setImage(swordStab);
		swordDrawBox.setAngle(initialAngle - 10);
		swordDrawBox.setVisible(true);

		final Timer stabTimer = new Timer(10, null);
		ActionListener stabAction = new ActionListener() {
			int visiblityCounter = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (visiblityCounter >= visiblityDuration) {
					swordDrawBox.setVisible(false);
					stabTimer.stop();
				}
				visiblityCounter += 1;
			}
		};
		stabTimer.addActionListener(stabAction);
		stabTimer.start();
	}

	// method that performs the sword slash animation
	private void doSwordSweepVisual(int initialAngle, int increment, int angelOfSlash) {

		int numOfIncrements = angelOfSlash / increment;
		swordDrawBox.setImage(swordSlash);
		swordDrawBox.setAngle(initialAngle - 20);
		swordDrawBox.setSlashing(true);
		swordDrawBox.setVisible(true);

		final Timer sweeptimer = new Timer(10, null);
		ActionListener sweepAction = new ActionListener() {
			int currentIncrement = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentIncrement >= numOfIncrements) {
					swordDrawBox.setVisible(false);
					swordDrawBox.setSlashing(false);
					sweeptimer.stop();
					return;
				}
				swordDrawBox.setAngle(initialAngle - 20 + (increment * currentIncrement));
				currentIncrement += 1;
			}
		};
		sweeptimer.addActionListener(sweepAction);
		sweeptimer.start();
	}

	/**
	 * Getter for the player's health
	 * 
	 * @return player health
	 */
	public int getHealth() {
		return playerHealth;
	}

	/**
	 * Setter for the player's position.
	 * 
	 * @param pos The point to put the player at.
	 */
	public void setCurrentPosition(Point pos) {
		playerX = (int) pos.getX();
		playerY = (int) pos.getY();
	}

	/**
	 * Getter for the player's  stab attack damage output (damage dealt to enemies)
	 * 
	 * @return stabDamage
	 */
	public int getStabDamage() {
		return stabDamage;
	}
	
	/**
	 * Getter for the player's  slash  attack damage output (damage dealt to enemies)
	 * 
	 * @return slashDamage
	 */
	public int getSlashDamage() {
		return slashDamage;
	}

	/**
	 * This method reduces the player's health by the specified amount and sets the
	 * invincibility counter to 500.
	 * 
	 * @param damageAmount an integer representing the amount of damage taken by the
	 *                     player
	 */
	public void takeDamage(int damageAmount) {

		if (invincibilityCounter <= 0) {
			playerHealth = playerHealth - damageAmount;

			invincibilityCounter = INVINCIBILITYTIME;
			drawPlayer();
		}
	}

	/**
	 * Redraw the player at the current player X and Y.
	 */
	public void drawPlayer() {
		playerAvatar.setBounds(playerX, playerY, PLAYERSIZE, PLAYERSIZE);
		healthBar.setText("" + playerHealth);
		healthBar.setBounds(playerX + 8, playerY - 25, PLAYERSIZE, PLAYERSIZE);
		swordDrawBox.setBounds(playerX - 100, playerY - 100, 200, 200);
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
	 * Getter for the player's maximum movement.
	 * 
	 * @return MAXMOVEMENT
	 */
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
	 * @param enemy the enemy the method checks its attack against
	 * @return true if an attack hit, false otherwise
	 */

	public boolean attack(EnemyPlayer enemy) {

		Point enemPoint = new Point((int) enemy.getX(), (int) enemy.getY());
		Point enemBottomLeft = new Point((int) enemy.getX(), (int) enemy.getY() + enemy.ENEMYSIZE);
		Point enemTopRight = new Point((int) enemy.getX() + enemy.ENEMYSIZE, (int) enemy.getY());
		Point enemBottomRight = new Point((int) enemy.getX() + enemy.ENEMYSIZE, (int) enemy.getY() + enemy.ENEMYSIZE);

		int[] enemyXCords = new int[] { (int) enemPoint.getX(), (int) enemTopRight.getX(), (int) enemBottomRight.getX(),
				(int) enemBottomLeft.getX() };
		int[] enemyYCords = new int[] { (int) enemPoint.getY(), (int) enemTopRight.getY(), (int) enemBottomRight.getY(),
				(int) enemBottomLeft.getY() };

		for (int i = 0; i < enemyXCords.length; i++) {
			enemyXCords[i] += xMouseOffsetToContentPaneFromJFrame;
		}

		for (int i = 0; i < enemyYCords.length; i++) {
			enemyYCords[i] += yMouseOffsetToContentPaneFromJFrame;
		}

		Polygon enemhitbox = new Polygon(enemyXCords, enemyYCords, 4);
		Rectangle enemyHitbox = new Rectangle(enemhitbox.getBounds());

		// Get the direction from the player to the mouse
		double deltaX = newPosition.getX() - playerX;
		double deltaY = newPosition.getY() - playerY;

		// Calculate the rectangle emanating from the player in the direction of the
		// mouse
		double rectWidth = 90;
		double rectHeight = 10;
		double rectX = playerX;
		double rectY = playerY - rectHeight / 2.0;

		// Rotate the rectangle to match the angle of the mouse in relation to the
		// player
		double angle = Math.atan2(deltaY, deltaX);

		Point bottomLeft = new Point((int) rectX, (int) rectY);
		Point topLeft = new Point((int) rectX, (int) (rectY + rectHeight));
		Point bottomRight = new Point((int) (rectX + rectWidth), (int) rectY);
		Point topRight = new Point((int) (rectX + rectWidth), (int) (rectY + rectHeight));
		Point[] recPoints = new Point[] { bottomLeft, topLeft, bottomRight, topRight };
		rotateRectangle(recPoints, new Point(playerX, playerY), angle);

		int[] xCords = new int[] { (int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(),
				(int) bottomRight.getX() };
		int[] yCords = new int[] { (int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(),
				(int) bottomRight.getY() };

		for (int i = 0; i < xCords.length; i++) {
			xCords[i] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
		}

		for (int i = 0; i < yCords.length; i++) {
			yCords[i] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
		}

		Polygon hitbox = new Polygon(xCords, yCords, 4);
		doSwordStabVisual((int) Math.toDegrees(angle) + 90, 10);

		return hitbox.intersects(enemyHitbox);
		// Check if the point is in the rectangle

	}

	/**
	 * This method executes an attack action from the player towards the given
	 * point. The attack is executed by drawing a rectangle projecting from the
	 * player towards the given point and checking whether a given point exists
	 * within this rectangle. If an intersection is found, the game object will
	 * receive {@link DungeonPlayer.PLAYERDAMAGE} points of damage.
	 *
	 * @param enemy the enemy the method checks the attack against.
	 * @return true if an attack hit, false otherwise
	 */
	public boolean sweepAttack(EnemyPlayer enemy) {

		Point enemPoint = new Point((int) enemy.getX(), (int) enemy.getY());
		Point enemBottomLeft = new Point((int) enemy.getX(), (int) enemy.getY() + enemy.ENEMYSIZE);
		Point enemTopRight = new Point((int) enemy.getX() + enemy.ENEMYSIZE, (int) enemy.getY());
		Point enemBottomRight = new Point((int) enemy.getX() + enemy.ENEMYSIZE, (int) enemy.getY() + enemy.ENEMYSIZE);

		int[] enemyXCords = new int[] { (int) enemPoint.getX(), (int) enemTopRight.getX(), (int) enemBottomRight.getX(),
				(int) enemBottomLeft.getX() };
		int[] enemyYCords = new int[] { (int) enemPoint.getY(), (int) enemTopRight.getY(), (int) enemBottomRight.getY(),
				(int) enemBottomLeft.getY() };

		for (int i = 0; i < enemyXCords.length; i++) {
			enemyXCords[i] += xMouseOffsetToContentPaneFromJFrame;
		}

		for (int i = 0; i < enemyYCords.length; i++) {
			enemyYCords[i] += yMouseOffsetToContentPaneFromJFrame;
		}

		Polygon enemhitbox = new Polygon(enemyXCords, enemyYCords, 4);
		Rectangle enemyHitbox = new Rectangle(enemhitbox.getBounds());

		double ROTATIONCONSTANT = 0.25;

		// Get the direction from the player to the mouse

		double deltaX = newPosition.getX() - playerX;
		double deltaY = newPosition.getY() - playerY;

		int rotationPointAdjustmentX = 3 * Integer.signum((int) deltaX);
		int rotationPointAdjustmentY = 3 * Integer.signum((int) deltaY);

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
			xCords[i] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
		}

		for (int i = 0; i < yCords.length; i++) {
			yCords[i] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
		}

		Polygon hitbox = new Polygon(xCords, yCords, 4);

		enemyHitbox.intersects(enemyHitbox);
		if (hitbox.intersects(enemyHitbox)) {
			return true;
		}

		for (int i = 0; i < 5; i++) {
			rotateRectangle(recPoints, sweepRotationPoint, ROTATIONCONSTANT);

			xCords = new int[] { (int) bottomLeft.getX(), (int) topLeft.getX(), (int) topRight.getX(),
					(int) bottomRight.getX() };
			yCords = new int[] { (int) bottomLeft.getY(), (int) topLeft.getY(), (int) topRight.getY(),
					(int) bottomRight.getY() };

			for (int n = 0; n < xCords.length; n++) {
				xCords[n] += xMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
			}

			for (int n = 0; n < yCords.length; n++) {
				yCords[n] += yMouseOffsetToContentPaneFromJFrame + PLAYERSIZE / 2;
			}

			hitbox = new Polygon(xCords, yCords, 4);
			if (hitbox.intersects(enemyHitbox)) {
				return true;
			}

		}

		doSwordSweepVisual((int) Math.toDegrees(angle + (ROTATIONCONSTANT * 5)), 10, 90);

		return hitbox.intersects(enemyHitbox);
	}
}
