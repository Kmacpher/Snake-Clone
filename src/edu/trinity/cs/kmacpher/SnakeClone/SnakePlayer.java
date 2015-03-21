package edu.trinity.cs.kmacpher.SnakeClone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.trinity.cs.gamecore.Location;
import edu.trinity.cs.gamecore.Player;
import edu.trinity.cs.gamecore.Screen;


/**
 * Implementation of Player and SnakeEntity.
 * The main entity in SnakeClone is the Snake itself, or more specifically, the snake head which will control the 
 * movement of the rest of the body. The snake moves up, down, right, and left from the arrow keys (or wasd keys). When the snake
 * hits a mushroomEntity, the snake's movement is not disrupted and it elongates by adding a bodyEntity. 
 * If the mushroom is a normal red mushroom, score is increased by 5 points. If it is a Bee mushroom, score is
 * increased by 20 point. Bee mushrooms show up 20% of the time.
 *  When the snake collides with a wallblock, bodyEntity, or Gooma/GhostEntity, gameStatus will be set to player dead, and a life is lost.
 * 
 *  Might want to make a getScore method at some point for the snakesetup to call
 * 
 * 
 */
public class SnakePlayer implements Player<SnakeBlock,SnakeEntity>, SnakeEntity, java.awt.event.KeyListener {
	
	/**
	 * Constructor for SnakePlayer takes the initial location for the player
	 * @param loc
	 */
    public SnakePlayer(Location<SnakeBlock,SnakeEntity> loc) {
       	location = loc;
              
    }
    
    private static final long serialVersionUID=457609283376092346l;

    /**This method reads in a keyEvent from the keyboard consisting of the up right down and left arrow keys.
     * When a key is pressed, a boolean value associated with that key is defined as true.
     * The snake cannot abruptly change from a direction to the opposite direction, to avoid the snake running
     * into the body entities following it. For example, if the player is going right, they must press up or down first,
     * then left inorder to go left.
     * @param a KeyEvent
     * 
     */
    @Override
    public void keyPressed(KeyEvent e) {
    	    	
    	int code = e.getKeyCode();		
 	
    	if (code == java.awt.event.KeyEvent.VK_LEFT) {
    		if (!right) {
    			left = true;
    			up = false;
    			down = false;
    		}
    	}
    	
    	if (code == java.awt.event.KeyEvent.VK_DOWN) {
    		if (!up) {
    			down = true;
    			right = false;
    			left = false;
    		}
    	}
    	
    	if (code == java.awt.event.KeyEvent.VK_RIGHT) {
    		if (!left) {
    		    right = true;
    		    down = false;
    		    up = false;
    		}
    	}
    	
    	if (code == java.awt.event.KeyEvent.VK_UP) {
    		if (!down) {
    			up = true;
    			right = false;
    			left = false;
    		}
    	}    	    	
    }
    
    /**
	 * The update method is where any changes to the entity are made. This will mainly be moving the object, 
	 * but can also include collision detection and handling with other things in the game and updating animations. 
	 * For SnakeClone, will include movement, elongation, and interaction with blocks if animation changes before the game is over.
	 * When there is a collision with a mushroom, this method also updates score depending on which mushroom.
	 * this method also calls the update method of the next bodyEntity after the head.
	 * 
	 *
	 * @param time - this is the current time in the game
	 */
	@Override
	public void update(int time) {
		Random g = new Random();
		Boolean b = false;
		
		ticks = time + SnakeSetup.getDifficulty();  
		oldLoc = location;
		
		if (left) {
			setLocation(location.partialMove(-1, 0));
	;
			
		}
		if (right) {
			setLocation(location.partialMove(1, 0));
	
															
		}
		if (up)	{
			setLocation(location.partialMove(0, -1));
			
		}
		if (down) {
			setLocation(location.partialMove(0, 1));
		}
		xloc = location.getLocX();
        yloc = location.getLocY();
        ThisScreen = location.getScreen();
		if (ThisScreen.getBlock(xloc, yloc) instanceof WallBlock) {
			status = Player.GameStatus.PLAYER_DEAD;
		}
		if (ThisScreen.getBlock(xloc, yloc) instanceof GroundBlock) {
			status = Player.GameStatus.GAME_RUNNING;
		}
		
		
		Iterator<SnakeEntity> Iter = ThisScreen.createEntityIterator();
		
		while(Iter.hasNext()) {
			SnakeEntity e = Iter.next();
			if (location.equals(e.getLocation())) {
				if (e instanceof BodyEntity) {
					status = Player.GameStatus.PLAYER_DEAD;
				}
				else if ((e instanceof RedMushroomEntity)||(e instanceof BeeMushroomEntity)) {             //or green or blue. maybe use a method to update score
					ThisScreen.removeEntity(e);

					int Sx = (int) ThisScreen.getSize().getWidth();
					int Sy = (int) ThisScreen.getSize().getHeight();
					int Fx;
					int Fy;
					
					if (SnakeSetup.getDifficulty()!=3) {
						do {
							Fx = g.nextInt(Sx);
							Fy = g.nextInt(Sy);
						} while( (!(ThisScreen.getBlock(Fx, Fy) instanceof GroundBlock)));

						GoombaEntity Goomba = new GoombaEntity(new Location<SnakeBlock, SnakeEntity>(ThisScreen,Fx,Fy));
						ThisScreen.addEntity(Goomba);
					}
					
					do {
						Fx = g.nextInt(Sx);
						Fy = g.nextInt(Sy);
					} while( (!(ThisScreen.getBlock(Fx, Fy) instanceof GroundBlock)));
					
					GhostEntity Block2 = new GhostEntity(new Location<SnakeBlock, SnakeEntity>(ThisScreen,Fx,Fy));
					ThisScreen.addEntity(Block2);
					
					do {
						do {
							Fx = g.nextInt(Sx);
							Fy = g.nextInt(Sy);
						} while( (!(ThisScreen.getBlock(Fx, Fy) instanceof GroundBlock)));
					} while (collision(Fx, Fy));
					
					System.out.println(mushroom);
					if(mushroom==1) {
						score += 20;
					}
					else score += 5;
					
					mushroom = g.nextInt(5);
					
					if (mushroom==1) {
						BeeMushroomEntity Food1 = new BeeMushroomEntity(new Location<SnakeBlock,SnakeEntity>(ThisScreen,Fx,Fy));
						ThisScreen.addEntity(Food1);
					}
					else {
						RedMushroomEntity Food = new RedMushroomEntity(new Location<SnakeBlock, SnakeEntity>(ThisScreen,Fx,Fy));
						ThisScreen.addEntity(Food);
					}
					
					BodyEntity Body = new BodyEntity(oldLoc, next);
					ThisScreen.addEntity(Body);
					next = Body;

					
					updateGameStatusPanel();
					
					b = true;
				}
				else if ((e instanceof GoombaEntity)||(e instanceof GhostEntity)) {
					status = Player.GameStatus.PLAYER_DEAD;
				}
			
			}
		}		

		if (next != null && b == false) {
			next.setLocation(oldLoc);
			next.update(); 
		}
	}

	/**
	 * checks to see if two coordinates that form a location object coincide with the location of an entity
	 * this method only shows if there is a collision, not what you collided with. So, is only used for 
	 * preventing two entities from occupying the same spot, not for controlling events.
	 * return true or false
	 */
	public boolean collision(int Fx, int Fy) {
		
		Iterator<SnakeEntity> Iter2 = ThisScreen.createEntityIterator();
		while(Iter2.hasNext()) {
			SnakeEntity s = Iter2.next();
			Location<SnakeBlock, SnakeEntity> loc = new Location<SnakeBlock, SnakeEntity>(ThisScreen,Fx,Fy); 
			if (loc.equals(s.getLocation())) return true;			
		}	
		return false;
	}
	
	
	/**
	 * This method returns the score. It is called at end game to show the user what their end score is and 
	 * called for showing the score on the bottom panel.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * this method sets the score. This will really only be used when trying to restart the game and wanting to set score = 0
	 * @param the int to set score equal to
	 */
	public void setScore(int i) {
		score = i;
	}
	
	/**
	 * This method returns the next time at which the entity should have its update method called. 
	 * A negative value prevents it from being put on the event queue. Snake will always be updated (constantly moving)
	 * even if there is no input from user, but how long between updates will depend on difficulty, which will either
	 * be determined at the start, or gradually increase.
	 * @return The next time that this entity should be updated or -1 if it shouldn't be.
	 */
	@Override
	public int getUpdateTime() {
		return ticks;
	}
	
	
	/**
     * Tells if this entity should follow links from one screen to another. 
     * there are no links in this game.
     * @return a boolean value stating if the player follows links or not.
     */
	@Override
	public boolean followLinks() {
		return false;
	}

	/**
	 * This method should return a java.awt.Container with GUI components set up in it to edit the properties 
	 * of this entity. The entity should not have a reference to the Container because that could interfere 
	 * with saving to disk. I recommend using a javax.swing.JPanel as your Container, but any Container should work. 
	 * This method is only called in the screen editor, not during the game. If your entity doesn't have any 
	 * properties that should be changeable in the editor this should return null. (will always be null for this game)
	 */
	@Override
	public Container getEditPropertiesPanel() {
		return null;
	}

	/**
	 * This returns the image that should be drawn for this entity. Is called from a file. If the file is not foudn,
	 * then the default will be used. The player starts looking to the right and the direction will change 
	 * depending on the direction that the player is moving.
	 * What is returned will be scaled to the size of one block for drawing.
	 * @return Image object of entity 
	 */
	@Override
	public Image getImage() {

		if(img==null) {
			try {
				img = ImageIO.read(new File("Chain Chomp.png"));
			} catch (IOException e) {
				System.out.println("File not found. Default coloring will be used.");
				img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
			    Graphics g=img.getGraphics();
			    g.setColor(Color.black);
				g.fillRect(0,0,15,15);
		//	    g.setColor(Color.red);
		//	    g.fillRect(0,0,20,20);
				e.printStackTrace();
			}
			return img;
		}
        
        else {
        	if (down) {
				try {
					img = ImageIO.read(new File("Chain Chomp Downright.png"));
				} catch (IOException e) {
					System.out.println("File not found. Default coloring will be used.");
					img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
					Graphics g=img.getGraphics();
					g.setColor(Color.black);
					g.fillRect(0,0,15,15);
				//	g.setColor(Color.red);
				//	g.fillRect(0,0,20,20);
					e.printStackTrace();
				}

			}
			else if (left) {
				try {
					img = ImageIO.read(new File("Chain Chomp Reverse.png"));
				} catch (IOException e) {
					System.out.println("File not found. Default coloring will be used.");
					img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
					Graphics g=img.getGraphics();
					g.setColor(Color.black);
					g.fillRect(0,0,15,15);
				//	g.setColor(Color.red);
				//	g.fillRect(0,0,20,20);
					e.printStackTrace();
				}
			}
			else if (up) {
				try {
					img = ImageIO.read(new File("Chain Chomp Upright.png"));
				} catch (IOException e) {
					System.out.println("File not found. Default coloring will be used.");
					img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
					Graphics g=img.getGraphics();
					g.setColor(Color.black);
					g.fillRect(0,0,15,15);
				//	g.setColor(Color.red);
				//	g.fillRect(0,0,20,20);
					e.printStackTrace();
				}
			}
			else if (right) {		
						
				try {
					img = ImageIO.read(new File("Chain Chomp.png"));
				} catch (IOException e) {
					System.out.println("File not found. Default coloring will be used.");
					img=new BufferedImage(15,15,BufferedImage.TYPE_INT_RGB);
					Graphics g=img.getGraphics();
					g.setColor(Color.white);
					g.fillRect(0,0,15,15);
				//	g.setColor(Color.red);
				//	g.fillRect(0,0,20,20);
					e.printStackTrace();
				}
			}
        	return img;
        }
	}

	/**
	 * This returns the location of this entity.
	 * @return a location object of the current location of the entity
	 */
	@Override
	public Location<SnakeBlock, SnakeEntity> getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	
	/**
	 * This method returns how many partials across the entity should be. 
	 * For a "standard" entity that take up a whole block, this should return Location.getPartialsInWhole(); 
	 *
	 */
	@Override
	public int partialSizeX() {
		return Location.getPartialsInWhole(); //right now is set to 1
	}

	/**
	 * This method returns how many partials up and down the entity should be. 
	 * For a "standard" entity that take up a whole block, this should return Location.getPartialsInWhole();
	 * Snake will probably not take up a whole.. play around with this. 
	 * @return How many partials vertically the entity should take up when drawn.
	 */
	@Override
	public int partialSizeY() {
		return Location.getPartialsInWhole(); //right now is set to 1
	}

	/**
	 * sets location of the entity
	 */
	@Override
	public void setLocation(Location<SnakeBlock, SnakeEntity> loc) {
		location = loc;
		
	}
	
	/**
	 * Sets status back to GAME_RUNNING. will be used to reset the game.	
	 */
	public void resetStatus() {
		status = Player.GameStatus.GAME_RUNNING;
		left = false;
    	right = false;
    	up = false;
    	down = false;
	}
	

	/**
	 * Returns one of the three constants telling what the status of the game is. 
	 * @return GAME_RUNNING, PLAYER_DEAD, or GAME_SUCCESS
	 */
	@Override
	public edu.trinity.cs.gamecore.Player.GameStatus gameStatus() {
		return status;
	
	}
	
	
	/**
	 * This method returns a GUI container that shows extra information in the game. 
	 * For example, this might be where you display the score. For Snake clone, it shows the score on the left
	 * and the difficulty level on the right. It uses a Jpanel with a Borderlayout.
	 * 
	 */
	@Override
	public Container getGameStatusPanel(edu.trinity.cs.gamecore.Player.PanelLoc Loc) {
		if (Loc.equals(Player.PanelLoc.SOUTH)) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(label, BorderLayout.WEST);
			
			panel.add(label2, BorderLayout.EAST);
			return panel;
		}
		else return null;
	}
	
	/**
	 * This method updates the GameStatusPanel when it needs to update the score or the difficulty setting
	 */
	public void updateGameStatusPanel() {
		label.setText("     Score: " + score);
		label2.setText("Difficulty: " + SnakeSetup.getDifficultyString() + "     ");
	}
	
	/**
	 * Method used to universalize the ticks so the same ticks are used for each update methods
	 * @return ticks
	 */
	public int getTicks() {
		return ticks;
	}
	

	
	private static transient Image img=null;
	Location<SnakeBlock,SnakeEntity> location;
	Location<SnakeBlock,SnakeEntity> oldLoc;
	public Player.GameStatus status = Player.GameStatus.GAME_RUNNING;
	private int ticks;
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private int xloc;  //these will probably later be doubles
	private int yloc;
	private Screen<SnakeBlock,SnakeEntity> ThisScreen;
	private int score;
	final JLabel label = new JLabel("     Score: " + score, SwingConstants.LEFT);
	final JLabel label2 = new JLabel("Difficulty: " + SnakeSetup.getDifficultyString() + "     ", SwingConstants.RIGHT);
	private int mushroom = 0;   
	private BodyEntity next = null;
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}