
package edu.trinity.cs.kmacpher.SnakeClone;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.trinity.cs.gamecore.Location;


	/**Creates a block used for the internal and outer walls of the game. If the entity runs into one of these walls, a life is lost
	 * and the level starts over. 
	 * @param args
	 */

public class WallBlock implements SnakeBlock {
			
			/**
			 * Constructor for field block
			 * @param args
			 */
			public WallBlock() {
		    }
			
			/**
			 * Builds and returns the image of the field block
			 * @return img
			 */
		    public Image getImage(){
		        if(img==null) {

					 try {
						img = ImageIO.read(new File("Retro Exclamation Block.png"));
					} catch (IOException e) {
						System.out.println("File not found. Default coloring will be used.");
						img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
			        	Graphics g=img.getGraphics();
			        	g.setColor(Color.orange);
						g.fillRect(0,0,15,15);
					//	g.setColor(Color.red);
					//	g.fillRect(0,0,20,20);
						e.printStackTrace();
					} 	
			        
		        }
		        return img;
		        
		    }
		    
		    /**
		     * This method returns a panel that should be set up so that users can edit the properties of this block.
		     *  @return panel used to edit block properties
		     */
		    public Container getEditPropertiesPanel(){ return null; }

		    /**
		     * Returns the location that this block links to. When an entity that follows links moves onto a block with a link it will be "transported" to that location.
		     * If a given type of block should never have a link from it, this can simply return null. 
		     * @return location the block links to or null
		     */
		    public Location<SnakeBlock,SnakeEntity> getLinkLocation(){ return link; }

		    /**
		     * Sets the location that the block links to
		     */
		    public void setLinkLocation(Location<SnakeBlock,SnakeEntity> linkLocation){
		        link=linkLocation;
		    }

			
		    /**
		     * Returns a boolean value for whether this block is passable or not. if not passable, and
		     * the snake collides with it, then the snake dies.
		     */
		    @Override
			public boolean isPassable(SnakeBlock B) {
				return false;
			}
			

		    private Location<SnakeBlock,SnakeEntity> link = null;
		    private static transient Image img=null;		    		  
		    private static final long serialVersionUID=457609283376092346l;

}
