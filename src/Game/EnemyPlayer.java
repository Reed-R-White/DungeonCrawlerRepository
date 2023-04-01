package Game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class EnemyPlayer extends Player {
	private final static int PLAYER_FOLLOW_DISTANCE = 200;
	private List<Integer> movementPatternX;
	private List<Integer> movementPatternY;
	private int health = 100;
	private int movementIndex;
	private boolean follow;
	private DungeonPlayer  player;

	public EnemyPlayer(JFrame gameJFrame, float startingX, float startingY, float width, float height, DungeonPlayer player1) {
		super(gameJFrame, startingX, startingY, width, height);
		Random random = new Random();
		
		movementPatternX = new ArrayList<Integer>();
		movementPatternY = new ArrayList<Integer>();

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
		speed = (float) 0.50;
		player = player1;
		
		//NOte for later: go forwards or move into upper section
		for (int i = movementPatternX.size() - 1; i >= 0; i--) {
			this.movementPatternX.add(movementPatternX.get(i) * -1);
		}

		for (int i = movementPatternY.size() - 1; i >= 0; i--) {
			this.movementPatternY.add(movementPatternY.get(i) * -1);
		}

		movementIndex = 0;

		ActionListener movementPerSecond = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				follow = checkPlayer();
				move();
				draw(gameJFrame.getGraphics());
	        }
	    };
		
	    Timer timer = new Timer(10, movementPerSecond);
	    timer.start();
		
	}
	
	public void takeDamage(int damageAmount) {
		health -= damageAmount;
	}
	
	public float getX() {
		return posX;
	}
	
	public float getY() {
		return posY;
	}

	public void move() {
		if (follow) {
			int playerX = player.getX();
			int playerY = player.getY();
			float deltaX = 0;
			float deltaY = 0;

			if (posX > playerX) {
				posX -= speed;
				deltaX = -1;
			} else if (posX < playerX) {
				posX += speed;
				deltaX = 1;
			}

			if (posY > playerY) {
				posY -= speed;
				deltaY = -1;
			} else if (posY < playerY) {
				posY += speed;
				deltaY = 1;
			}

		} else {
			if (movementIndex >= movementPatternX.size()) {
				movementIndex = 0;
			}
			int deltaX = movementPatternX.get((int) movementIndex);
			int deltaY = movementPatternY.get((int) movementIndex);
			posX += deltaX * speed;
			posY += deltaY * speed;
			movementIndex += 1;
		}

	}

	private boolean checkPlayer() {
		return Math.sqrt(Math.pow(posX - player.getX(), 2) + Math.pow(posY - player.getY(), 2)) <= PLAYER_FOLLOW_DISTANCE;
	}
}
