/**
 * 
 */
package edu.trinity.cs.kmacpher.SnakeClone;

import java.util.Random;
import java.util.Vector;
import javax.swing.*;
import edu.trinity.cs.gamecore.GameSetup;
import edu.trinity.cs.gamecore.Location;
import edu.trinity.cs.gamecore.MainFrame;
import edu.trinity.cs.gamecore.Player;
import edu.trinity.cs.gamecore.PriorityQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;


/**<p>This is a simple implementation of the SnakeSetup interface.  It tells the game
 * infrastructure how to render things and is used to find common information.</p>
 * This can be a simple way to keep track of information that lots of different
 * entities or blocks need to get hold of.</p>
 * 
 * SnakeSetup sets up all the field and initial variables. It also sets up the menus where the 
 * player can set the difficulty level and restart the game.
 * 
 * @author kmacpher
 * 

 *
 */
public class SnakeSetup implements GameSetup<SnakeBlock,SnakeEntity> {
    
    /**
     * This method returns the singleton of the SnakeSetup class.
     */
    public static SnakeSetup instance() {
        if(bgsInstance==null) {
            bgsInstance=new SnakeSetup();
        }
        return bgsInstance;
    }
    
    /**
     * initializes all of the variables that are related to the general
     * state of the game. This sets the outer perimeter to wallBlocks, as well as two lines in the middle 
     * of the screen. This also sets a random redmushroomEntity, and sets the player in the middle of the
     * screen, and creates the priority queue.
     */
    private SnakeSetup() {
        Location.setPartialsInWhole(1);

        Vector<SnakeScreen> vect=null;

		if (vect == null) { // (BLM)
        vect=new Vector<SnakeScreen>();
        
		firstScreen=new SnakeScreen(); 
	    int sx = (int) firstScreen.getSize().getWidth();
		int sy = (int) firstScreen.getSize().getHeight();
		
	
		for (int i=10; i < (sx-10); ++i) {	
			WallBlock Block1 = new WallBlock();				//sets Wallblocks in two parallel lines on field. 
			firstScreen.setBlock(9,i,Block1);				
			WallBlock Block2 = new WallBlock();
			firstScreen.setBlock(26, i, Block2);
		}
		
		
		for (int j = 0; j < sx; ++j) {
			for (int i = 1; i < sy-1; ++i) {
				WallBlock blockleft = new WallBlock();
				firstScreen.setBlock(0, i, blockleft);			//sets the outer border of wallblocks
				WallBlock blockright = new WallBlock();
				firstScreen.setBlock(sx-1, i, blockright);
				WallBlock blockup = new WallBlock();
				firstScreen.setBlock(j, 0, blockup);
				WallBlock blockdown = new WallBlock();
				firstScreen.setBlock(j, sy-1, blockdown);
			}
		}
		
		Random g = new Random();
		int Fx;
		int Fy;
			do {
				Fx = g.nextInt(sx);
				Fy = g.nextInt(sy);
		  } while( (!(firstScreen.getBlock(Fx, Fy) instanceof GroundBlock)));
			
		RedMushroomEntity Food = new RedMushroomEntity(new Location<SnakeBlock, SnakeEntity>(firstScreen,Fx,Fy));
		firstScreen.addEntity(Food);

		vect.add(firstScreen);
		}

        // Setup basic variables.
		int sx = (int) firstScreen.getSize().getWidth();
		int sy = (int) firstScreen.getSize().getHeight();

				
        localPlayer=new SnakePlayer(new Location<SnakeBlock,SnakeEntity>(firstScreen,sx/2,sy/2));
        firstScreen.addEntity(localPlayer);


        // Set up the original queue and add all entities.
        priorityQueue=new ListBasedPriorityQueue();
        for(int i=0; i<vect.size(); i++) {
        	SnakeScreen scr=vect.get(i);
            for(Iterator<SnakeEntity> iter=scr.createEntityIterator(); iter.hasNext(); ) {
            	SnakeEntity ge=iter.next();
                priorityQueue.add(ge);
            }
        }
    }

    /**
     * This method is called in the constructor of a main frame so that the
     * SnakeSetup class will be able to communicate with the frame that the
     * game is being played in.
     * @param mf The MainFrame object the game is being played in.
     */
    public void setMainFrame(MainFrame<SnakeBlock,SnakeEntity> mf) {
        mainFrame = mf;
    }

    /**
     * Returns the instance of SnakePlayer subclass.
     */
    public SnakePlayer getLocalPlayer() {
        return localPlayer;
    }

    /**
     * Returns the priority queue for the game.  (uses list)
     */
    public PriorityQueue<SnakeBlock,SnakeEntity> getPriorityQueue() {
        return priorityQueue;
    }

    /**
     * Tells the display class is this game has a scrolling background in the x direction.
     *
     * @return This value tells the program how many squares across it should draw around the player.  Return -1 for no scrolling.
     */
    public int getScrollingX() {
        return -1;
    }

    /**
     * Tells the display class is this game has a scrolling background in the y direction.
     *
     * @return This value tells the program how many squares vertically it should draw around the player.  Return -1 for no scrolling.
     */
    public int getScrollingY() {
        return -1;
    }

    /**
     * If this returns true, the drawing routine will use an optimization of
     * drawing the images for all the blocks once to a large image and just
     * putting up that image as long as the player hasn't changed screens.  This
     * can be significantly faster.  However, if it is true, it means that changes
     * to blocks won't normally be drawn so you can't have animated blocks, or
     * moving blocks, or blocks that disappear (doors).  This flag is checked
     * at every step so you could have it return true most of the time, but return
     * false when some event has occurred that should require the blocks to be
     * redrawn.
     * @return Whether or not the framework should double-buffer the blocks as a background image.
     */
    public boolean useDrawOptimization() {
        return true;
    }

    /**
     * This function returns a JMenuBar object that will be added to the main
     * display window.  Adds a new MenuBar called settings, and under it a filemenu called 'difficulty'. 
     * There are 3 options: easy, normal, hard.
     * hard sets difficulty = 1, normal = 2 and easy = 3.
     * So for a game on easy, update is called every 3 ticks.
     * 
     */
    public JMenuBar getMenuBar() {
        JMenuBar menuBar=new JMenuBar();
        final JMenu fileMenu=new JMenu("File");
        
        JMenuItem startItem=new JMenuItem("Start Game"); 
        startItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { restartGame(); }
        });
        fileMenu.add(startItem);
        
        
        JMenuItem item=new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        fileMenu.add(item);
        
        final JMenu SetMenu = new JMenu("Settings");
        
        JMenuItem diffItem=new JMenuItem("Difficulty");
        diffItem.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
                 String[] options = { "Easy", "Normal", "Hard" };
                 String in = (String)
             JOptionPane.showInputDialog(fileMenu,
                 "Choose a difficulty level",
                 "Difficulty",
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 options,
                 options[0]);
         setDifficulty(in);
         localPlayer.updateGameStatusPanel();
             }
        });
        SetMenu.add(diffItem);
        
        menuBar.add(fileMenu);
        menuBar.add(SetMenu);
        return menuBar;
        
        
    }

    /**
     * This function returns the difficulty that is needed to increment ticks in the entity and player classes
     * @return difficulty
     */
    public void setDifficulty(String D) {
    	if(D == null) {
    		difficulty = 3;
    	}
    	else if (D.equals("Easy")) {
    		difficulty = 3;
    	}
    	else if (D.equals("Normal")) {
    		difficulty = 2;
    	}
    	else difficulty = 1;
    	
    	restartGame();
    }
    
    /**
     * gets the difficulty. Is static so methods outside SnakeSetup can access the difficulty without
     * an object
     * @return difficulty
     */
    public static int getDifficulty() {
       	return difficulty;
    }
    
    /**
     * Gets the string from the difficulty variable to print on the lower panel.
     * @return String representation of the string.
     */
    public static String getDifficultyString() {
    	if (difficulty == 3) return "Easy";
    	else if (difficulty ==2) return "Normal";
    	else return "Hard";
    }
    
       
    /**
     * This function will automatically be called when the player begins to return
     * something other than GAME_RUNNING. Is called when the player dies, and gives the option to 
     * restart the game.
     */
    public void stopGame() {
        
      if(getLocalPlayer().gameStatus()==Player.GameStatus.PLAYER_DEAD) {
            int val = JOptionPane.showConfirmDialog(mainFrame,"Oh no! Your snake was crushed! Your score was " + localPlayer.getScore() + ".\nTry again?" ,"GAME OVER",JOptionPane.YES_NO_OPTION);
            if (val == 0) {
            	restartGame();
            }
        }
    
    }

    /**
     * This method is called from the event handler for the start option on the
     * menu.  It can be replaced if you want to use different menu options. 
     */
    private void startGame() {
        mainFrame.getDisplay().requestFocus();
        mainFrame.getTimer().start();
    }
    /**
     * this method resets the player, score, and blocks, effectively resetting the game without creating a new mainframe.
     */
    private void restartGame() {  
    	
    	firstScreen.removeAll();
        firstScreen.addEntity(localPlayer);
        localPlayer.setLocation(new Location<SnakeBlock,SnakeEntity>(firstScreen,18,18));
        localPlayer.resetStatus();
        
        Random g = new Random();
		int Fx;
		int Fy;
			do {
				Fx = g.nextInt((int) firstScreen.getSize().getWidth()); 
				Fy = g.nextInt((int) firstScreen.getSize().getHeight());
		  } while( (!(firstScreen.getBlock(Fx, Fy) instanceof GroundBlock)));
		RedMushroomEntity Food = new RedMushroomEntity(new Location<SnakeBlock, SnakeEntity>(firstScreen,Fx,Fy));
		firstScreen.addEntity(Food);
		
		localPlayer.setScore(0);
		localPlayer.updateGameStatusPanel();
    	startGame();
    }
    
    private static SnakePlayer localPlayer = null;
   private PriorityQueue<SnakeBlock,SnakeEntity> priorityQueue = null;
    private SnakeScreen firstScreen;
    private MainFrame<SnakeBlock,SnakeEntity> mainFrame;
    private static SnakeSetup bgsInstance;
    private static int difficulty = 3;  //easy is default
   
    

    
    

}
