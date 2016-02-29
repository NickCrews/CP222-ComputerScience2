/*
LinkedListBase.java
Nick Crews
11/20/15

An abstract Class of the LinkedList containing the basic methods for a Linked List.
Refrains from adding methods that could mess up the ordering, if that is something desired.
*/

import java.util.Arrays;

public class DoubleLinkedList{

	protected Node head;
	protected Node tail;
	protected int length;

	public DoubleLinkedList(){
		this.length = 0;
		this.head = null;
		this.tail = null;
	}

	public void add(Object newData){
		addLeft(newData);
	}

	public void addRight(Object newData){
		// add this data to the tail of our linked list
		Node newNode = new Node(newData, tail, null);
		if (length > 0){
			tail.next = newNode;
		}
		else {
			head = newNode;
		}
		tail = newNode;
		length++;
	}

	public void addLeft(Object newData){
		// add this data to the head of our list
		Node newNode = new Node(newData, null, head);
		if (length > 0){
			head.previous = newNode;
		}
		else {
			tail = newNode;
		}
		head = newNode;
		length++;
	}

	public Object get(int index){
		// get the data stored at this index
		return getNode(index).data;
	}

	public int find(Object query){
		// returns the index of the first occurence of an object which matches query
		Node current = head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < length; i++){
			if (current.data.equals(query)){
				return i;
			}
			current = current.next;
		}
		// if it wasn't found, return -1
		return -1;
	}

	public Object remove(int index){
		// remove the node at the given index
		return remove(getNode(index));	
	}

	public Object remove(Object query){
		// remove the first entry from the list which matches query
		return remove(getNode(query));
	}

	public Object[] toArray(){
		// return an array of objects holding the entire contents of the list, in order
		Object[] out = new Object[length];
		Node current = head;
		for (int i = 0; i < length; i++){
			out[i] = current.data;
			current = current.next;	
		}
		return out;
	}

	public void insert(Object newData, int index){
		// add this data in the indicated position


		// check that our index was valid
		if (index > this.length || index < -length-1){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}

		// make the Node to add
		Node newNode = new Node(newData);

		// deal with the special cases of adding to either of the ends
		if (index == 0 || index == -this.length-1){
			addLeft(newData);
		}
		else if (index == this.length || index == -1){
			addRight(newData);
		}


		// ok, now we know that we're trying to insert in the middle of the list
		else{
			// index is the desired position AFTER the insertion, so we bump everything >= index forward

			// get the two Nodes which will be on either side of our inserted Node
			Node after = getNode(index);
			Node before = after.previous;

			// change the links properly
			newNode.previous = before;
			newNode.next = after;
			before.next = newNode;
			after.previous = newNode;
			length++;
		} 
	}

	public void set(Object newData, int index){
		getNode(index).data = newData;
	}

	public int length(){
		return length;
	}

	public boolean isEmpty(){
		return length == 0;
	}

	public void clear(){
		head = null;
		tail= null;
		length = 0;
	}

	public String toString(){
		// return a String representation
		return Arrays.toString(this.toArray());
	}




	protected Node getNode(int index){
		// First do some checking that our index is acceptable
		if (this.length == 0) {
			throw new IndexOutOfBoundsException("LinkedList is empty!");
		}
		if (index >= length || index < -length){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}

		// ok, we know that is a good index
		if (index >= 0){
			Node current = head;
			for ( int i  = 0; i < index; i++){
				current = current.next;
			}
			return current;
		}	
		// let's use negative indexing like python
		else {
			Node current = tail;
			for (int i = -1; i > index; i--){
				current = current.previous;
			}
			return current;
		}
	}

	protected Node getNode(Object query){
		// get the FIRST node in our list which matches the given query
		Node current = head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < length; i++){
			if (current.data.equals(query)){
				return current;
			}
			current = current.next;
		}
		// if we didn't find something that matched that query, return null
		return null;
	}

	protected Object remove(Node n){
		// attempts to remove a node. returns whether or not successful
		if (n == null){
			return null;
		}

		// deal with the special cases of adding to either of the ends
		if (n == head && n == tail){
			head = null;
			tail = null;
		}
		else if (n == head){
			Node newHead = n.next;
			newHead.previous = null;
			head = newHead;
		}
		else if (n == tail){
			Node newTail = n.previous;
			newTail.next = null;
			tail = newTail;
		}
		// so the node must be middle one, with nodes on either side
		else {
			Node before = n.previous;
			Node after = n.next;
			before.next = after;
			after.previous = before;
		}
		this.length--;
		return n.data;
	}

	protected class Node{
		
		private Object data;
		private Node next;
		private Node previous;

		Node(Object data){
			this.data = data;
			this.next = null;
			this.previous = null;
		}

		Node(Object data, Node next, Node previous){
			this.data = data;
			this.next = next;
			this.previous = previous;
		}
	}

}