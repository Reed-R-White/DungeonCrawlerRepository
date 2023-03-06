package Game;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

public class EnemyPlayer extends Player {
	ArrayList<Integer> movementPatternX;
	ArrayList<Integer> movementPatternY;
	int movementIndex;
	Boolean follow;
	Player player;

	public EnemyPlayer(JFrame gameJFrame, float startingX, float startingY, float width, float height,
			ArrayList<Integer> movementPatternX, ArrayList<Integer> movementPatternY) {
		super(gameJFrame, startingX, startingY, width, height);
		follow = false;
		color = Color.red;
		speed = 0.5f;
		this.movementPatternX = movementPatternX;
		this.movementPatternY = movementPatternY;
		for (int i = movementPatternX.size() - 1; i >= 0; i--) {
			this.movementPatternX.add(movementPatternX.get(i) * -1);
		}

		for (int i = movementPatternY.size() - 1; i >= 0; i--) {
			this.movementPatternY.add(movementPatternY.get(i) * -1);
		}

		movementIndex = 0;

	}

	public void move() {
		if (follow) {
			float deltaX = 0;
			float deltaY = 0;

			if (posX > player.posX) {
				posX -= speed;
				deltaX = -1;
			} else if (posX < player.posX) {
				posX += speed;
				deltaX = 1;
			}

			if (posY > player.posY) {
				posY -= speed;
				deltaY = -1;
			} else if (posY < player.posY) {
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

	public void checkPlayer(Player player) {
		double distance = Math.sqrt(Math.pow(posX - player.posX, 2) + Math.pow(posY - player.posY, 2));

		if (distance <= 200) {
			follow = true;
			this.player = player;
		} else {
			follow = false;
			this.player = null;
		}
	}

}
