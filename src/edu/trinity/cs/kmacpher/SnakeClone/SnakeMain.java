
package edu.trinity.cs.kmacpher.SnakeClone;

import edu.trinity.cs.gamecore.MainFrame;


/**
 * SnakeMain Class
 * 
 * Author: Karen MacPherson
 * 
 * The game is a clone of the classic snake game, where
 * the user controls the head of a snake (chain chomp from mario) using the arrow keys.
 * The body will follow path of the head, 
 * and attempts to eat food(mushrooms) as it is generated while avoiding obstacles (enemies in the 
 * forms of the mario enemies Goomba and a Ghost). Every time 
 * the snake eats a piece of food, the snake will grow a unit and the user
 * will accumulate points. The larger snake makes it harder for
 * the user to collect food without running into itself or the accumulated enemies.
 * 
 * 
 * The snake 'dies' if it runs into a wall, or itself, a
 * feildBlock, or 
 * When the snake picks up a piece of food, another will appear randomly, 
 * along with 1 or 2 new random enemies. These do not disappear. The food could be a 
 * RedMushroom (80%) worth 5 points or a BeeMushroom (20%) worth 20 points.
 * 
 * Different difficulties of play affect the speed of the snake, and the number of enemies that
 * appear after the snake eats a mushroom.
 * 
 * To implement the game in the framework, I might use 2
 * different kinds of block. One type that encases the screen and creates the inner barriers.
 *  
 * The mushrooms, snake, snakebodies, and enemies are all used as entites.
 * 
 * The object of the game is to direct the Chain Chomp snake around the screen to eat mushrooms, and 
 * accumulate as many points as possible before the snake collides with an enemy, block, or itself.
 * This ends the game and the Player is given the option to restart the game and play again.
 *  
 * 
 */
 

public class SnakeMain {
	
	public static void main(String[] args) {
		
		MainFrame<SnakeBlock,SnakeEntity> m = new MainFrame<SnakeBlock,SnakeEntity>(SnakeSetup.instance());
		m.setVisible(true);
		m.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
		
		}
}
