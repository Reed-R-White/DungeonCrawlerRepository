package Game;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JFrame;

import java.util.Random;

public class TestGame extends JFrame {

	public static void main(String[] args) {
		new TestGame();

	}

	public TestGame() {
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		Player player1 = new Player(this, 700, 700, 10, 10);
		Random random = new Random();
		ArrayList<Integer> movementPatternX = new ArrayList<Integer>();
		ArrayList<Integer> movementPatternY = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			int directionX = random.nextInt(3) - 1;
			int directionY = random.nextInt(3) - 1;
			for (int j = 0; j < 40; j++) {
				movementPatternX.add(directionX);
				movementPatternY.add(directionY);
			}

		}

		EnemyPlayer enemy1 = new EnemyPlayer(this, 200, 200, 10, 10, movementPatternX, movementPatternY);

		Grid grid1 = new Grid(player1, enemy1);
		grid1.addMouseListener(grid1);
		add(grid1);
		setVisible(true);
		while (true) {
			player1.move();
			enemy1.move();
			enemy1.checkPlayer(player1);
			grid1.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

}
