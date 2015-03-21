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
	 * This class makes up the enemy Entites that the snakePlayer must avoid to stay alive. When the Player
	 * eats a mushroom, an enemy is randomly placed on the screen. If the Player collides with an enemy,
	 * the Player dies and the game is over. GhostEntities occur at all levels of play.
	 *
	 */
	public class GhostEntity implements SnakeEntity {

		/**
		 * Constructor takes the entity location and places it there on the screen.
		 * @param loc
		 */
		public GhostEntity(Location<SnakeBlock,SnakeEntity> loc) {
	        location = loc;
	              
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
		 * no properties panel
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
						img = ImageIO.read(new File("Boo Mushroom.png"));
					} catch (IOException e) {
						System.out.println("File not found. Default coloring will be used.");
					    img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
			        	Graphics g=img.getGraphics();
			    	    g.setColor(Color.white);
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
		 * A negative value prevents it from being put on the event queue. FoodEntity will update when it collides (eaten)
		 * by the snake player. right now though returns 0. will be changed later.
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
			location = loc;
			
		}

		/**
		 * does nothing. collision is detected in snakePlayer
		 * @param time - this is the current time in the game
		 */
		@Override
		public void update(int arg0) {
			 
			
		}
		
		private static transient Image img=null;
		Location<SnakeBlock,SnakeEntity> location;
		private static final long serialVersionUID = 2008834819834783510L;
		public Player.GameStatus status = Player.GameStatus.GAME_RUNNING;
		

	}


