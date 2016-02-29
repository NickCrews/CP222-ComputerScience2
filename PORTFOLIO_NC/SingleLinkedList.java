

public class SingleLinkedList{
	
	protected Node head;
	protected int length;

	public SingleLinkedList(){
		this.length = 0;
		this.head = null;
	}

	public void add(Object newData){
		// add this data to the head of our list
		Node newNode = new Node(newData);
		newNode.next = head;
		head = newNode;
		length++;
	}

	public void insert(Object newData, int index){
		// add this data in the indicated position

		// check that our index was valid
		if (index > length || index < -length-1){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}
		// we can use negative indexing too like python
		if (index < 0){
			index = length+index+1;
		}
		// are we inserting at the beginning?
		if (index == 0){
			add(newData);
			return;
		}

		// make the Node to add
		Node newNode = new Node(newData);

		// we are going to inser the new Node between these two
		Node previous = null;
		Node after = head;
		for (int i = 0; i < index; i++){
			previous = after;
			after = after.next;
		}
		// we already took care of the case of inserting at the beginning
		previous.next = newNode;
		newNode.next = after;
		length++;
	}

	public Object get(int index){
		// get the data stored at this index
		if (length == 0) {
			throw new IndexOutOfBoundsException("LinkedList is empty!");
		}
		if (index >= length || index < -length){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}
		// we can use negative indexing too like python
		if (index < 0){
			index = this.length+index;
		}
		// now go through the list to the right place
		Node current = head;
		for (int i = 0; i < index; i++){
			current = current.next;
		}
		return current.data;
	}

	public int find(Object query){
		// returns the index of the first occurence of an object which matches query
		Node current = head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			if (current.data.equals(query)){
				return i;
			}
			current = current.next;
		}
		// if it wasn't found, return -1
		return -1;
	}

	public Object pop(){
		return removeNode(head, null);
	}

	public Object remove(int index){
		// remove and return the node at the given index
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("LinkedList is empty!");
		}
		if (index >= length || index < -length){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}
		// we can use negative indexing too like python
		if (index < 0){
			index = this.length+index;
		}

		Node previous = null;
		Node current = head;
		for (int i = 0; i < index; i++){
			previous = current;
			current = current.next;
		}
		return removeNode(current, previous);
	}

	public Object remove(Object query){
		// remove and return the first entry from the list which matches query. 
		// returns null if the object isn't found
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("LinkedList is empty!");
		}
		
		Node previous = null;
		Node current = head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			if (current.data.equals(query)){
				return removeNode(current, previous);
			}
			previous = current;
			current = current.next;
		}
		return null;
	}

	public int length(){
		return length;
	}

	public boolean isEmpty(){
		return length==0;
	}

	public void clear(){
		// remove all the elements of the list
		head = null;
		length = 0;
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

	public String toString(){
		// return a String representation
		String out = "[";
	    Node current = head;
	    while ( current != null ) {
	        out += current.data;
	        current = current.next;
	        if (current != null){out+=", ";}
	    }
	    out += "]";
	    return out;
		}


	protected Object removeNode(Node n, Node previous){
		// attempts to remove a node. returns the data in the removed Node

		// deal with the special cases of removing the beginning
		if (n == head){
			head = n.next;
		}
		else {
			previous.next = n.next;
		}
		length--;
		return n.data;
	}


	private class Node{
		
		private Object data;
		private Node next;

		Node(Object data){
			this.data = data;
			this.next = null;
		}

	}

}