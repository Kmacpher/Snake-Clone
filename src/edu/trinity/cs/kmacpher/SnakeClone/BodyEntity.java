package edu.trinity.cs.kmacpher.SnakeClone;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.trinity.cs.gamecore.Location;
import edu.trinity.cs.gamecore.Player;

/**
 * This class is the body of the snake. Each entity also stores a pointer to the entity after it.
 *  If the snake player runs into a bodyentity, the game is over. When the snake
 * gets a MushroomEntity, another BodyEntity is added is the SnakePlayer class. 
 * @author kmacpher
 *
 */

public class BodyEntity implements SnakeEntity {
	
/**
 * Constructor for BodyEntity. It takes the location, and a pointer to the Entity following it.
 * @param loc
 * @param n
 */
	public BodyEntity(Location<SnakeBlock,SnakeEntity> loc, BodyEntity n) {
        location = loc;
        next = n;
      
              
    }
	
	
	/**
     * Tells if this entity should follow links from one screen to another. 
     * @return a boolean value stating if the player follows links or not.
     */
	@Override
	public boolean followLinks() {
		return false;
	}


	/**
	 * no  Edit PropertiesPanel used
	 */
	@Override
	public Container getEditPropertiesPanel() {
		return null;
	}

	/**
	 * This returns the image that should be drawn for this entity. 
	 * What is returned will be scaled to the size of one block for drawing.
	 * @return Image object of entity 
	 */
	@Override
	public Image getImage() {
		if(img==null) {

			 try {
				img = ImageIO.read(new File("sphere_black.gif"));
			} catch (IOException e) {
				System.out.println("File not found. Default coloring will be used.");
				   img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
		        	Graphics g=img.getGraphics();
		    	    g.setColor(Color.black);
					g.fillRect(0,0,15,15);
		    	//  g.setColor(Color.red);
				//	g.fillRect(0,0,20,20);
				e.printStackTrace();
			}
	    }
        return img;
	}
	
	/**
	 * This returns the location of this entity.
	 * @return a location object of the current location of the entity
	 */
	@Override
	public Location<SnakeBlock, SnakeEntity> getLocation() {
		return location;
	}
	/**
	 * This method returns the next time at which the entity should have its update method called. 
	 * A negative value prevents it from being put on the event queue. BodyEntity will elongate when the snake eats a
	 * FoodEntity. It will also update each tick that the snake is moving because it must move with the snake.
	 * @return The next time that this entity should be updated or -1 if it shouldn't be.
	 */
	@Override
	public int getUpdateTime() {
		return -1;
		
	}


	/**
	 * This method returns how many partials across the entity should be. 
	 * For a "standard" entity that take up a whole block, this should return Location.getPartialsInWhole(); 
	 *
	 */
	@Override
	public int partialSizeX() {
		return Location.getPartialsInWhole();
	}

	/**
	 * This method returns how many partials up and down the entity should be. 
	 * For a "standard" entity that take up a whole block, this should return Location.getPartialsInWhole();
	 * @return How many partials vertically the entity should take up when drawn.
	 */
	@Override
	public int partialSizeY() {
		return Location.getPartialsInWhole();
	}

	/**
	 * sets location of the entity
	 */
	@Override
	public void setLocation(Location<SnakeBlock, SnakeEntity> loc) {
		oldLoc = location;
		location = loc;
		
	}

	/**
	 * Is not called. update() is called automatically
	 */
	@Override
	public void update(int time) {
		//nothing
	
		}
		
		
	
	/**
	 * The used update method!
	 * The update method is where any changes to the entity are made. This will mainly be moving the object, 
	 * but can also include collision detection and handling with other things in the game and updating animations. 
	 * For the bodyentity, each bodyentity sets the location of the entity after it to its old location, 
	 * and then updates the next location.
	 */
	public void update() {
		if (next != null) {
			next.setLocation(oldLoc);
			next.update();
	    }
	}
	
	private static transient Image img=null;
	Location<SnakeBlock,SnakeEntity> location;
	Location<SnakeBlock,SnakeEntity> oldLoc;
	BodyEntity next = null;
	private static final long serialVersionUID = 2008834819834783510L;
	public Player.GameStatus status = Player.GameStatus.GAME_RUNNING;
	

}
