package Game;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class creates an Obstacle, an object that the player can detect
 * collision with.
 */
public class Obstacle {


	protected final JFrame gameJFrame;
	protected JLabel[] ObstacleObject = new JLabel[3];
	//protected final JLabel[] ObstacleObject;
	private static final int OBJECT_SIZE = 40;
    private Rotation currentOrientation;

    private float posX1,posY1,posX2,posY2,posX3,posY3,posX4,posY4,posX5,posY5;



	/**
	 * Object Constructor
	 * 
	 * @param gameJFrame A JFrame that the Obstacle should exist in.
	 * @param startingX  Initial X for the Obstacle.
	 * @param startingY  Initial X for the Obstacle.
	 * @param rotation   What sort of Obstacle this is. It will be either
	 *                   Horizontal, Vertical, or one of 4 elbow shapes.
	 */
	public Obstacle(JFrame gameJFrame, float startingX, float startingY, Rotation rotation) {

        //Assign variables
        this.gameJFrame = gameJFrame;
        posX1 = startingX;
        posY1 = startingY;
        currentOrientation = rotation;
        ObstacleObject = new JLabel[1];
        //Create objects
        

        //Config the rotation
        switch(rotation){
            case POINTING_BOTTOM_RIGHT:
            	posX2=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY2=posY1+OBJECT_SIZE;
                posX3=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY3=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX4=posX1+OBJECT_SIZE;
                posY4=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX5=posX1;
                posY5=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX1=posX1+OBJECT_SIZE+OBJECT_SIZE;
                ObstacleObject = new JLabel[5];
                break;
            case POINTING_BOTTOM_LEFT:
            	posX2=posX1;
                posY2=posY1+OBJECT_SIZE;
                posX3=posX1;
                posY3=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX4=posX1+OBJECT_SIZE;
                posY4=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX5=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY5=posY1+OBJECT_SIZE+OBJECT_SIZE;
                ObstacleObject = new JLabel[5];
                break;
            case POINTING_TOP_RIGHT:
            	posX2=posX1+OBJECT_SIZE;
                posY2=posY1;
                posX3=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY3=posY1;
                posX4=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY4=posY1+OBJECT_SIZE;
                posX5=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY5=posY1+OBJECT_SIZE+OBJECT_SIZE;
                ObstacleObject = new JLabel[5];
                break;
            case POINTING_TOP_LEFT:
                posX2=posX1+OBJECT_SIZE;
                posY2=posY1;
                posX3=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posY3=posY1;
                posX4=posX1;
                posY4=posY1+OBJECT_SIZE;
                posX5=posX1;
                posY5=posY1+OBJECT_SIZE+OBJECT_SIZE;
                ObstacleObject = new JLabel[5];
                break;
            case L_VERTICAL:
                posX1=posX1;
                posX2=posX1;
                posY2=posY1+OBJECT_SIZE;
                posX3=posX1;
                posY3=posY1+OBJECT_SIZE*2;
                ObstacleObject = new JLabel[3];
                break;
            case R_VERTICAL:
                posX1=posX1+OBJECT_SIZE+OBJECT_SIZE;
                posX2=posX1;
                posY2=posY1+OBJECT_SIZE;
                posX3=posX1;
                posY3=posY1+OBJECT_SIZE*2;
                ObstacleObject = new JLabel[3];
                break;
            case T_HORIZONTAL:
                posY1=posY1;
                posX2=posX1+OBJECT_SIZE;
                posY2=posY1;
                posX3=posX2+OBJECT_SIZE;
                posY3=posY1;
                ObstacleObject = new JLabel[3];
                break;
            case B_HORIZONTAL:
                posY1=posY1+OBJECT_SIZE+OBJECT_SIZE;
                posX2=posX1+OBJECT_SIZE;
                posY2=posY1;
                posX3=posX2+OBJECT_SIZE;
                posY3=posY1;
                ObstacleObject = new JLabel[3];
                break;
            case SINGLE:
            	ObstacleObject = new JLabel[1];
        }
    }


	/**
	 * Put the object into the game.
	 */
	public void draw() {

    	switch(currentOrientation){
	        case POINTING_BOTTOM_RIGHT:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	ObstacleObject[3] = new JLabel();
	        	ObstacleObject[4] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[3].setBounds((int)posX4, (int)posY4, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[4].setBounds((int)posX5, (int)posY5, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case POINTING_BOTTOM_LEFT:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	ObstacleObject[3] = new JLabel();
	        	ObstacleObject[4] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[3].setBounds((int)posX4, (int)posY4, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[4].setBounds((int)posX5, (int)posY5, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case POINTING_TOP_RIGHT:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	ObstacleObject[3] = new JLabel();
	        	ObstacleObject[4] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[3].setBounds((int)posX4, (int)posY4, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[4].setBounds((int)posX5, (int)posY5, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case POINTING_TOP_LEFT:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	ObstacleObject[3] = new JLabel();
	        	ObstacleObject[4] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[3].setBounds((int)posX4, (int)posY4, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[4].setBounds((int)posX5, (int)posY5, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case L_VERTICAL:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case R_VERTICAL:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	            
	        case T_HORIZONTAL:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	        
	        case B_HORIZONTAL:
	        	ObstacleObject[0] = new JLabel();
	        	ObstacleObject[1] = new JLabel();
	        	ObstacleObject[2] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[1].setBounds((int)posX2, (int)posY2, OBJECT_SIZE, OBJECT_SIZE);
	            ObstacleObject[2].setBounds((int)posX3, (int)posY3, OBJECT_SIZE, OBJECT_SIZE);
	            break;
	           
	        case SINGLE:
	        	ObstacleObject[0] = new JLabel();
	        	
	        	ObstacleObject[0].setBounds((int)posX1, (int)posY1, OBJECT_SIZE, OBJECT_SIZE);
	        	break;
	            
	    }
        
        System.out.println("X3 = "+posX3+", Y3 = "+posY3);

        //retrieve and resize the image
        ImageIcon sprite = new ImageIcon("src/Game/cobblestone_texture.jpeg");
        Image tempImage = sprite.getImage();
        tempImage = tempImage.getScaledInstance(OBJECT_SIZE, OBJECT_SIZE, java.awt.Image.SCALE_SMOOTH);
        sprite = new ImageIcon(tempImage);


		// Draw the images
		for (JLabel obj : ObstacleObject) {

			obj.setIcon(sprite);
			obj.setVisible(true);
			gameJFrame.add(obj);
		}
		
		gameJFrame.repaint();
    }
    
    public JLabel[] getObjects() {
    	return ObstacleObject;
    }

	/**
	 * Checks if the given coordinates are within the area of the Obstacle.
	 * 
	 * @param x The X-coordinate to check.
	 * @param y The Y-coordinate to check.
	 * @return boolean true if the coordinates are within the Obstacle, false
	 *         otherwise.
	 */
	public boolean checkCollision(float x, float y) {

        if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight()))){
            return true;
        }
        
        if (ObstacleObject.length == 3) {
        	if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight())) || (x >= posX2 && x <= (posX2 + ObstacleObject[1].getWidth()) && y >= posY2 && y <= (posY2 + ObstacleObject[1].getHeight())) || (x >= posX3 && x <= (posX3 + ObstacleObject[2].getWidth()) && y >= posY3 && y <= (posY3 + ObstacleObject[2].getHeight()))){
                return true;
            }
        }
        else if (ObstacleObject.length == 5) {
        	if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight())) 
        			|| (x >= posX2 && x <= (posX2 + ObstacleObject[1].getWidth()) && y >= posY2 && y <= (posY2 + ObstacleObject[1].getHeight())) 
        			|| (x >= posX3 && x <= (posX3 + ObstacleObject[2].getWidth()) && y >= posY3 && y <= (posY3 + ObstacleObject[2].getHeight()))
        			|| (x >= posX4 && x <= (posX4 + ObstacleObject[3].getWidth()) && y >= posY4 && y <= (posY4 + ObstacleObject[3].getHeight()))
        			|| (x >= posX5 && x <= (posX5 + ObstacleObject[4].getWidth()) && y >= posY5 && y <= (posY5 + ObstacleObject[4].getHeight()))
        			){
                return true;
            }
        }
        
        return false;
    }

}