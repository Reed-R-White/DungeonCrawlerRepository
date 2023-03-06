package Game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	Player player;
	EnemyPlayer enemy;

	public Grid(Player player, EnemyPlayer enemy) {
		this.player = player;
		this.enemy = enemy;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.draw(g);
		enemy.draw(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		player.changeDestination(e.getX(), e.getY());
		System.out.println(e.getX() + " " + e.getY());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
