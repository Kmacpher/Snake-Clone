package edu.trinity.cs.kmacpher.SnakeClone;
import edu.trinity.cs.gamecore.Block;
 /**
  * An interface for the different kinds of blocks used in the SnakeClone
  * @author kmacpher
  *
  */
public interface SnakeBlock extends Block<SnakeBlock,SnakeEntity> {
	
	public boolean isPassable(SnakeBlock B);
}
