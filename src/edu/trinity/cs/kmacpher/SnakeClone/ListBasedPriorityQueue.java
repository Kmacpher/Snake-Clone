package edu.trinity.cs.kmacpher.SnakeClone;


import edu.trinity.cs.gamecore.PriorityQueue;


/**
 * The PriorityQueue interface is used to keep track of which entities should be updated in any given time step.
 * This is implemented as a list because there are never that many entities in the queue, however, the heap-based
 * queue is much faster.
 * @author kmacpher
 *
 */
public class ListBasedPriorityQueue implements PriorityQueue<SnakeBlock,SnakeEntity> {

	/**
	 * 
	 * A class that holds each node in the list that corresponds to an entity in the list. A node
	 * holds the data in the node and a pointer to the next node in the list
	 */
	
	private static class Node {
		public Node(SnakeEntity d, Node n) {  //n is what it points to
			data = d;
			next = n;
		}
		private SnakeEntity data;
		private Node next;
	}
	
	
	
	/**This is for testing only
	 * @param args
	 */
	public static void main(String[] args) {
		
		

	}
	
	/**
	 * his method adds a GameEntity into the queue. It calls the getUpdateTime() method
	 * of the GameEntity to determine where it should be placed in the queue.
	 * @param entity to be added to the queue
	 */
	public void add(SnakeEntity e) {
		
		if (head==null) {
			head = new Node(e, null);
		}
		else if (e.getUpdateTime() < head.data.getUpdateTime()) {
			head = new Node(e, head);
		}
		else {
			Node n = head;
			while (n.next != null) {
				if (e.getUpdateTime() < n.next.data.getUpdateTime()) { 
					n.next = new Node(e, n.next);
					return;
				}
				else {
					n = n.next;
					if (n.next==null) {
						n.next = new Node(e, null);
						return;
					}
				}
			}
		}
		
	}

	/**This method should return the first entity in the queue. Notice that it does not remove it.
	 * A separate call to removeFirst must be made to remove the item from the queue.
	 * @return the first GameEntity in the queue or null if the queue is empty
	 */
	@Override
	public SnakeEntity getFirst() {
		return head.data;
		
	}
	
	/**
	 * This method removes the first entity from the queue assuming one is present. 
	 */
	@Override
	public void removeFirst() {
		head = head.next;
		
		
	}

	private Node head;
}
