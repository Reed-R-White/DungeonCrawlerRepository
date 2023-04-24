package Game;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RotatedImageLabel extends JLabel {
    private ImageIcon imageIcon;
    private double degrees;

    public RotatedImageLabel(ImageIcon imageIcon, double degrees) {
        this.imageIcon = imageIcon;
        this.degrees = degrees;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        int x = (getWidth() - imageIcon.getIconWidth()) / 2;
        int y = (getHeight() - imageIcon.getIconHeight()) / 2;
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees), x + imageIcon.getIconWidth() / 2, y + imageIcon.getIconHeight() / 2);
        g2d.setTransform(transform);
        imageIcon.paintIcon(this, g2d, x, y);
        g2d.dispose();
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
        repaint();
    }
}
