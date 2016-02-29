/*
LinkedListBase.java
Nick Crews
11/20/15

An abstract Class of the LinkedList containing the basic methods for a Linked List.
Refrains from adding methods that could mess up the ordering, if that is something desired.
*/

import java.util.Arrays;

public abstract class LinkedListBase{

	protected Node head;
	protected Node tail;
	protected int length;

	public LinkedListBase(){
		this.length = 0;
		this.head = null;
		this.tail = null;
	}

	public Object get(int index){
		// get the data stored at this index
		return getNode(index).getData();
	}

	protected Node getNode(int index){
		// First do some checking that our index is acceptable
		if (this.length == 0) {
			throw new IndexOutOfBoundsException("LinkedList is empty!");
		}
		if (index >= this.length || index < -this.length){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}

		// ok, we know that is a good index
		if (index >= 0){
			Node node = this.head;
			for ( int i  = 0; i < index; i++){
				node = node.getNext();
			}
			return node;
		}	
		// let's use negative indexing like python
		else {
			Node node = this.tail;
			for (int i = -1; i > index; i--){
				node = node.getPrevious();
			}
			return node;
		}
	}

	public Object get(Object query){
		// get the first data stored in our list which matches the given query
		Node n = this.getNode(query);
		if (n == null){
			return null;
		}
		else {
			return n.getData();
		}	
	}

	protected Node getNode(Object query){
		// get the FIRST node in our list which matches the given query
		Node node = this.head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			if (node.getData().equals(query)){
				return node;
			}
			node = node.getNext();
		}
		// if we didn't find something that matched that query, return null
		return null;
	}

	public int find(Object query){
		// returns the index of the first occurence of an object which matches query
		Node node = this.head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			if (node.getData().equals(query)){
				return i;
			}
			node = node.getNext();
		}
		// if it wasn't found, return -1
		return -1;
	}

	public boolean remove(int index){
		// remove the node at the given index
		return remove(getNode(index));	
	}

	public boolean remove(Object query){
		// remove the first entry from the list which matches query
		return remove(getNode(query));
	}

	protected boolean remove(Node n){
		// attempts to remove a node. returns whether or not successful
		if (n == null){
			return false;
		}

		// deal with the special cases of adding to either of the ends
		if (n == this.head && n == this.tail){
			this.head = null;
			this.tail = null;
		}
		else if (n == this.head){
			Node newHead = n.getNext();
			newHead.setPrevious(null);
			this.head = newHead;
		}
		else if (n == this.tail){
			Node newTail = n.getPrevious();
			newTail.setNext(null);
			this.tail = newTail;
		}
		// so the node must be middle one, with nodes on either side
		else {
			Node previous = n.getPrevious();
			Node next = n.getNext();
			previous.setNext(next);
			next.setPrevious(previous);
		}
		this.length--;
		return true;
	}

	public Object[] getAll(){
		// return an array of objects holding the entire contents of the list, in order
		Object[] out = new Object[this.length];
		Node n = this.head;
		for (int i = 0; i < this.length; i++){
			out[i] = n.getData();
			n = n.getNext();	
		}
		return out;
	}

	public String[] toStringArray(){
		// same thing as getAll(), but converts each Object entry to a String 
		Object[] obj = this.getAll();
		return Arrays.copyOf(obj, obj.length, String[].class);
	}

	public String toString(){
		// return a String representation
		return "{" + String.join(", ", this.toStringArray()) + "}";
	}

}