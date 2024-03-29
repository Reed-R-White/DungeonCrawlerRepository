package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class GameObject extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private int x;
	private int y;
	private double angle;
	private boolean slashing = false;

	public GameObject(BufferedImage newImage, int x, int y, double angle) {
		this.image = newImage;
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	/*
	 * Simple setter method for the image to be drawn in this objects paint method
	 * 
	 * @param newImage
	 */
	public void setImage(BufferedImage newImage) {
		this.image = newImage;
	}

	public void setSlashing(boolean newValue) {
		slashing = newValue;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawRotatedImage(this.image, this.x, this.y, this.angle);
	}

	public void setAngle(double newAngle) {
		this.angle = newAngle;
	}

	public void drawRotatedImage(BufferedImage image, int x, int y, double angle) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		AffineTransform transform = new AffineTransform();
		if (slashing) {
			transform.translate(x, y);
			transform.translate(0, +40);
		} else {
			transform.translate(x + image.getWidth() / 2, y + image.getHeight() / 2);
		}
		transform.rotate(Math.toRadians(angle));

		transform.translate(-image.getWidth(), -image.getHeight());
		g2d.drawImage(image, transform, null);

	}
}
