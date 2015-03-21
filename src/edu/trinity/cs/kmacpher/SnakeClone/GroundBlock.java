
package edu.trinity.cs.kmacpher.SnakeClone;

import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.trinity.cs.gamecore.Location;


	/**GroundBlock is the background block that the playing field is made on. the snake can go through these blocks and they
	 * have no other special properties. Is represented in the game as grass.
	 *
	 */

public class GroundBlock implements SnakeBlock {
	
	private static final long serialVersionUID = 2178453384137237135L;

	/**
	 * Constructor for food block
	 * 
	 */
	public GroundBlock() {
    }
	
	/**
	 * Builds and returns the image of the ground block. 
	 * @return img
	 */
    public Image getImage(){
        if(img==null) {

			 try {
				img = ImageIO.read(new File("grass.jpg"));
			} catch (IOException e) {
				System.out.println("File not found. Default coloring will be used.");
				img=new BufferedImage(30,30,BufferedImage.TYPE_INT_RGB);
	        	Graphics g=img.getGraphics();
	    	    g.setColor(Color.green);
				g.fillRect(0,0,30,30);
	    	//    g.setColor(Color.red);
			//	g.fillRect(0,0,10,10);
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
		return true;
	}

    private Location<SnakeBlock,SnakeEntity> link = null;
    private static transient Image img=null;		  
   
	

}
