package Game;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * This class creates an obstacle out of 2 or 3 squares to put into a game.
 * 
 * @param gameJFrame A JFrame that the Obstacle should exist in.
 * @param startingX Initial X for the Obstacle.
 * @param startingY Initial X for the Obstacle.
 * @param rotation What sort of Obstacle this is.  It will be either Horizontal, Vertical, or one of 4 elbow shapes.
 */ 
public class Obstacle {
    
    protected final JFrame gameJFrame;
    protected final JLabel[] ObstacleObject = new JLabel[3];

    private float posX1,posY1,posX2,posY2,posX3,posY3;

    public Obstacle(JFrame gameJFrame,float startingX,float startingY,Rotation rotation){

        //Assign variables
        this.gameJFrame = gameJFrame;
        posX1 = startingX;
        posY1 = startingY;
        
        //Create objects
        ObstacleObject[0] = new JLabel();
        ObstacleObject[1] = new JLabel();
        ObstacleObject[2] = new JLabel();

        //Config the rotation
        switch(rotation){
            case POINTING_BOTTOM_RIGHT:
                posX1=posX1+50;
                posX2=posX1;
                posY2=posY1+50;
                posX3=posX1-50;
                posY3=posY1+50;
                break;
            case POINTING_BOTTOM_LEFT:
                posY2=posY1+50;
                posX2=posX1;
                posX3=posX1+50;
                posY3=posY2;
                break;
            case POINTING_TOP_RIGHT:
                posY2=posY1;
                posX2=posX1+50;
                posX3=posX2;
                posY3=posY2+50;
                break;
            case POINTING_TOP_LEFT:
                posY2=posY1;
                posX2=posX1+50;
                posX3=posX1;
                posY3=posY1+50;
                break;
            case VERTICAL:
                posX1=posX1+25;
                posX2=posX1;
                posY2=posY1+50;
                posX3=posX1;
                posY3=posY1;
                break;
            case HORIZONTAL:
                posY1=posY1+25;
                posX2=posX1+50;
                posY2=posY1;
                posX3=posX1;
                posY3=posY1;
                break;
        }
    }

    /**
     * Put the object into the game.
     */
    public void draw(){   

        
        System.out.println("X3 = "+posX3+", Y3 = "+posY3);

        ObstacleObject[0].setBounds((int)posX1, (int)posY1, 50, 50);
        ObstacleObject[1].setBounds((int)posX2, (int)posY2, 50, 50);
        ObstacleObject[2].setBounds((int)posX3, (int)posY3, 50, 50);

        
        //System.out.println("x of obj 3 = "+ObstacleObject[2].getX()+", y of obj 3 = "+ObstacleObject[2].getY());
        //System.out.println("width of obj 3 = "+ObstacleObject[2].getWidth()+", height of obj 3 = "+ObstacleObject[2].getHeight());


        //retrieve and resize the image
        ImageIcon sprite = new ImageIcon("src/Game/cobblestone_texture.jpeg");
        Image tempImage = sprite.getImage();
        tempImage = tempImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        sprite = new ImageIcon(tempImage);

        //Draw the images
        for(JLabel obj : ObstacleObject){
            
            obj.setIcon(sprite);
            obj.setVisible(true);
            gameJFrame.add(obj);
        }

        
        gameJFrame.repaint();        

    }

    /**
     * Checks if the given coordinates are within the area of the Obstacle.
     * 
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @return boolean true if the coordinates are within the Obstacle, false otherwise.
     */
    public boolean checkCollision(float x,float y){

        if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight())) || (x >= posX2 && x <= (posX2 + ObstacleObject[1].getWidth()) && y >= posY2 && y <= (posY2 + ObstacleObject[1].getHeight())) || (x >= posX3 && x <= (posX3 + ObstacleObject[2].getWidth()) && y >= posY3 && y <= (posY3 + ObstacleObject[2].getHeight()))){
            return true;
        }
        return false;
    }

}