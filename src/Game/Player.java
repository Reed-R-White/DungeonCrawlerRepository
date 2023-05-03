package Game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Player {

	protected final JFrame gameJFrame;
	protected final JLabel playerSprite;

	float posX, posY;
	float destX, destY;
	float speed = 1;
	float width;
	float height;
	Color color;

	public Player(JFrame gameJFrame, float startingX, float startingY, float width, float height) {

		this.gameJFrame = gameJFrame;
		posX = startingX;
		destX = startingX;
		posY = startingY;
		destY = startingY;
		this.width = width;
		this.height = height;
		color = color.blue;

		playerSprite = new JLabel();
		gameJFrame.getContentPane().add(playerSprite);

	}

	public void draw(Graphics g) {

//        final ImageIcon sprite = new ImageIcon("");
//        playerSprite.setIcon(sprite);
//        playerSprite.setBounds((int)posX, (int)posY, sprite.getIconWidth(), sprite.getIconHeight());
//        playerSprite.setVisible(true);
		g.setColor(color);
		g.fillRect((int) posX, (int) posY, (int) width, (int) height);

	}

	public void move() {
		float deltaX = 0;
		float deltaY = 0;

		if (posX > destX) {
			posX -= speed;
			deltaX = -1;
		} else if (posX < destX) {
			posX += speed;
			deltaX = 1;
		}

		if (posY > destY) {
			posY -= speed;
			deltaY = -1;
		} else if (posY < destY) {
			posY += speed;
			deltaY = 1;
		}

	}

	public void changeDestination(float destX, float destY) {
		this.destX = destX;
		this.destY = destY;

	}

}