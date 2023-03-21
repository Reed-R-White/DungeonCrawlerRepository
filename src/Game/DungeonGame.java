package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame {
    private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    public int[][] obstacleCoords = new int[6][6];
    public Obstacle[] obstacleArr = new Obstacle[obstacleCoords.length*obstacleCoords[0].length];
    public static final int gridWidth = 20;

    private JFrame gameWindow;
    
    private DungeonPlayer player1;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        player1 = new DungeonPlayer(gameWindow);

        //Create coords for the obstacles
        //outer loop controls X, inner loop controls Y
        for(int i=0;i<obstacleCoords.length;i++){
            for(int j=0;j<obstacleCoords[i].length;j++){
                obstacleCoords[i][j]=j*100;
            }
        }
        
        
        
    }

    public static void main(String[] args) {
        new DungeonGame();
    }
}




