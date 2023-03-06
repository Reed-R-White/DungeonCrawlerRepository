package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private int boxSize = 50;
    private int boxX = 0;
    private int boxY = 0;
    private int boxVelX = 0;
    private int boxVelY = 0;
    private int boxInertia = 3;
    private int followDist = 100;

    public DungeonGame() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        setFocusable(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int dist = (int) Point.distance(boxX, boxY, mouseX, mouseY);
                if (dist > followDist) {
                    if (boxX < mouseX) {
                        boxVelX += boxInertia;
                    } else if (boxX > mouseX) {
                        boxVelX -= boxInertia;
                    }
                    if (boxY < mouseY) {
                        boxVelY += boxInertia;
                    } else if (boxY > mouseY) {
                        boxVelY -= boxInertia;
                    }
                } else {
                    boxVelX = 0;
                    boxVelY = 0;
                }
            }
        });
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(boxX, boxY, boxSize, boxSize);
    }

    public void actionPerformed(ActionEvent e) {
        boxVelX *= 0.95;
        boxVelY *= 0.95;
        boxX += boxVelX;
        boxY += boxVelY;
        if (boxX < 0) {
            boxX = 0;
            boxVelX = -boxVelX;
        }
        if (boxY < 0) {
            boxY = 0;
            boxVelY = -boxVelY;
        }
        if (boxX + boxSize > getWidth()) {
            boxX = getWidth() - boxSize;
            boxVelX = -boxVelX;
        }
        if (boxY + boxSize > getHeight()) {
            boxY = getHeight() - boxSize;
            boxVelY = -boxVelY;
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Box Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new DungeonGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}




