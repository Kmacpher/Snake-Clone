package edu.trinity.cs.kmacpher.SnakeClone;

import java.util.Iterator;

/**
 * This class is a basic implementation of a linked list that can hold GameEntity objects. Students will use it for some of the early assignments. 
 * This class is a generic that takes the general block type and the general entity type for each game.
 * @author kmacpher
 *
 * @param <SnakeBlock>
 * @param <SnakeEntity>
 */
public class SnakeEntityList { //doesn't need to be generic

	/**
	 * A class that holds each node in the list that corresponds to an entity in the list. A node
	 * holds the data in the node and a pointer to the next node in the list
	 */
	public static class Node {
		public Node(SnakeEntity d, Node n) {  //n is what it points to
			data = d;
			next = n;
		}
		private SnakeEntity data;
		private Node next;
	}
	
	
	/**
	 * Adds the specified entity to the list. 
	 * @param the entity to be added
	 */
	public void add(SnakeEntity entity) {
		head = new Node(entity, head);
		
	}	
	
	/**
	 * Returns an Iterator object that can be used to "walk through" the list of entities. 
	 * See java.util.Iterator for the methods of the Iterator interface. Does not support the (optional)
	 * remove operation (throws an exception). 
	 * @return Iterator that will walk through the list
	 */
	public java.util.Iterator<SnakeEntity> createIterator() {
		
		return new listIterator();
	}
	
	/**
	 * Searches for the specified entity in the screen and removes it if found. If not found it throws an exception. 
	 * @param entity to be removed
	 * @throws java.util.NoSuchElementException
	 */
	public void remove(SnakeEntity entity) throws java.util.NoSuchElementException {
		
		if (head.data.equals(entity)) {
			head = head.next;
			return;
			
		}
		for(Node iter = head; iter.next != null; iter = iter.next) {
			if (iter.next.data.equals(entity)) {
				iter.next = iter.next.next;
			  return;
			}
		} 
		
	
	} 
	
	/**
	 * Removes all entities from the list
	 */
	public void removeall() {			
		head = null;
			
	}
	
	/**
	 * Returns true if the list is empty by checking if head is null
	 * @return boolean
	 */
	public boolean isEmpty() {
		return (head == null);
	}
	
	
	private Node head;


	//classes
	class listIterator implements Iterator<SnakeEntity> {

		public listIterator() {
			
			
		}
		/**
		 * Returns true if the iteration has more elements.
		 */  
		public boolean hasNext() {
			return (iterNode != null);
		}

		/**
		 * Returns the next element in the iteration.
		 */
		public SnakeEntity next() {
			Node tempNode = iterNode;
			iterNode = iterNode.next;
			return tempNode.data;
			
			}
			
		
		/**
		 * Not implemented (optional operation for Iterator).
		 */
		public void remove() throws UnsupportedOperationException { 
			throw new UnsupportedOperationException();
		}
 
		private Node iterNode = head;
		
	}
	
}

