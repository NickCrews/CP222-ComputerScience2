/*
SortedStringLinkedList.java
Nick Crews
11/21/15

Extension of LinkedListBase
Different from LinkedList in that instances only store Strings, 
and entries are maintained in lexicographic order
*/

public class SortedStringLinkedList extends LinkedListBase{

	public int add(String newString){
		// add a new entry in the correct lexicographic position
		Node newNode = new Node(newString);

		// now go through the list and find where to insert our new Node
		// break out when we've gone too far, and then deal with it
		Node node = this.head;
		int i = 0;
		for ( ; i < this.length; i++){
			String data = (String) node.getData();
			int order = newString.compareToIgnoreCase(data);

			// we already have this animal
			if (order == 0){
				return -1;
			}

			// we've gone too far! Our newNode should be inserted before node
			if (order < 0){
				break;
			}
			node = node.getNext();
		}

		// ok, so the new node belongs at the very beginning
		if (i == 0){
			appendHead(newNode);
		}
		// it belongs at the end
		else if (i == this.length) {
			appendTail(newNode);
		}
		// it belongs somehwere in the middle
		else {
			node.getPrevious().setNext(newNode);
			newNode.setPrevious(node.getPrevious());
			newNode.setNext(node);
			node.setPrevious(newNode);
		}
		// we succesfully added the node!
		// now add one to the length and return the index where we added it
		this.length++;
		return i;
	}

	protected void appendHead(Node newNode){
	// private method for adding an entry in the special case to the beginning of the list
		newNode.setPrevious(null);
		newNode.setNext(this.head);
		if (this.length > 0){
			this.head.setPrevious(newNode);
		}
		else {
			this.tail = newNode;
		}
		this.head = newNode;
	}

	protected void appendTail(Node newNode){
	// add this data to the tail of our linked list
		newNode.setPrevious(this.tail);
		newNode.setNext(null);
		if (this.length > 0){
			this.tail.setNext(newNode);
		}
		else {
			this.head = newNode;
		}
		this.tail = newNode;
	}

	
	protected Node getNode(Object queryObject){
	// overrides the search feature to be case insensitive
		String query = (String) queryObject;
		Node node = this.head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			String s = (String) node.getData();
			if (s.equalsIgnoreCase(query)){
				return node;
			}
			node = node.getNext();
		}
		return null;
	}


	public int find(Object queryObject){
		// overrides the inherited method to make it so case is ignored
		String query = (String) queryObject;
		Node node = this.head;
		// go through the list looking for a node which contains some data which matches the query
		for (int i = 0; i < this.length; i++){
			String s = (String) node.getData();
			if (s.equalsIgnoreCase(query)){
				return i;
			}
			node = node.getNext();
		}
		// if it wasn't found, return -1
		return -1;
	}

}