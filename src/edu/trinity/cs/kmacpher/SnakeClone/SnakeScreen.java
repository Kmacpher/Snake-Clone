/**
 * 
 */
package edu.trinity.cs.kmacpher.SnakeClone;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.trinity.cs.gamecore.Screen;

/**This class represents a single "screen" in the game. This given by a fixed grid of unchanging entries that specify 
 * what is to be drawn and give information on how the user is supposed to interact with it. It also has a list of objects
 * that are in the room. This includes both the moving and non-moving entities for the game. This is where the
 * dimensions of the screen are set.
 * @author kmacpher
 *
 */
public class SnakeScreen implements Screen<SnakeBlock,SnakeEntity> {

	/**
	 * constructor that sets the x and y coordinates of the screen
	 */
	public SnakeScreen() {
		xdim = 36;
		ydim = 36;  
		blockGrid = new SnakeBlock[xdim][ydim];					
		list = new SnakeEntityList();
		
			for(int i=0; i < xdim ; ++i) {
				for(int j=0; j < ydim ; ++j) {
					GroundBlock Block = new GroundBlock();		//sets the groundblocks that the snake can pass over
					blockGrid[i][j] = Block;  				
				}
			}
		}
	
	
	/**
	 * Adds the specified entity to the list for this screen
	 * @param the entity to be added to the screen
	 */
	@Override
	public void addEntity(SnakeEntity entity) {
		list.add(entity);
		
	}
	/**
	 * Returns an Iterator object that can be used to "walk through" the list of entities on this screen.
	 * All of the objects returned by the iterator should be of type GameEntity. If they aren't the display code
	 * will throw an exception when it tries to draw them. Your implementation does not need to be able to
	 * support the remove operation. It is optional, but if you don't you should write the method to 
	 * throw an UnsupportedOperationException. 
	 * @return an Iterator object that can be used to "walk through" the list of entities on this screen.
	 */
	@Override
	public Iterator<SnakeEntity> createEntityIterator() {
		Iterator<SnakeEntity> iterator = list.createIterator();
		return iterator;
		
	}

	/**
	 * This method returns a panel that should be set up so that users can edit the properties of this screen.
	 * @return a panel used to edit properties of the screen
	 */
	@Override
	public Container editPropertiesPanel() {
		return null;
	}

	/**
	 * returns the block at point (x,y)
	 * @param y, x
	 * @return the block at location (x,y)
	 */
	@Override
	public SnakeBlock getBlock(int dx, int dy) {
		return blockGrid[dx][dy];
	}

	/**
	 *Takes a number between 0 and getNumBlockTypes()-1 and returns an instance of a block corresponding to that number.
	 * As mentioned with getNumBlockTypes() they don't actually have to be different types, they can be the same type, 
	 * but with different properties if that helps.
	 * @param The integer specifying what type to return
	 * @return A block of the type associated with the input integer.
	 */
	@Override
	public SnakeBlock getBlockOfType(int type) {   
		if (type == 0) {
			return (SnakeBlock) new GroundBlock();
		}
		else if (type == 1) {
			return (SnakeBlock) new WallBlock();
		}
			else return null;
	}

	/**
	 * Takes a number between 0 and getNumEntityTypes()-1 and returns an instance of an entity corresponding to that number.
	 * As mentioned with getNumEntityTypes() they don't actually have to be different types, they can be the same type, 
	 * but with different properties if that helps.
	 * @param The integer specifying what type to return
	 * @return An entity of the type associated with the input integer.
	 */
	@Override
	public SnakeEntity getEntityOfType(int type) {    
		if (type == 0) {
			return (SnakeEntity) new BodyEntity(null, null); 	
			}
		else if (type == 1) {
			return (SnakeEntity) new RedMushroomEntity(null);
			}
		else if (type == 2) {
			return (SnakeEntity) new GoombaEntity(null);
		}
		else if (type == 3) {
			return (SnakeEntity) new GhostEntity(null);
		}
		else if (type == 4) {
			return (SnakeEntity) new BeeMushroomEntity(null);
		}
		else return null;
	}

	/**
	 * Returns the number of different blocks that the screen editor should set up for adding on this type of screen.
	 * @return the number of block types
	 */
	@Override
	public int getNumBlockTypes() {   
		return 2;
	}

	/**
	 * Returns the number of different entities that the screen editor should set up for adding on this type of screen.
	 * @return the number of entity types
	 */
	@Override
	public int getNumEntityTypes() {    
		return 5;
	}

	/**
	 * This method returns how many blocks the screen is in width and height. 
	 * @return an object of type Dimension with the size of the screen in blocks.
	 */
	@Override
	public Dimension getSize() {
		Dimension Dim = new Dimension(xdim,ydim);
		return Dim;
	}

	/**
	 * This method searches for the specified entity in this screen and removes it if found. If not found it throws an exception. 
	 * @param the entity you want to remove from the screen
	 * @throws NoSuchElementException
	 */
	@Override
	public void removeEntity(SnakeEntity entity) throws NoSuchElementException {
		list.remove(entity);
		
	}
	
	/**
	 * Removes all entities from the entity list. Used for Restart()
	 */
	public void removeAll() { 
		list.removeall();
	}

	/**
	 * Sets the block at location x,y to be b. 
	 * @param       x - The x location to be set.
    				y - The y location to be set.
    				b - The block that you want it to be set to.
	 */
	@Override
	public void setBlock(int dx, int dy, SnakeBlock block) {
		blockGrid[dx][dy] = block;
		
	}
	
	private int xdim;
	private int ydim;
	private SnakeBlock[][] blockGrid;
	private SnakeEntityList list;
	private static final long serialVersionUID = 1L; //need or not? was added

}
