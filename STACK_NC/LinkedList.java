/*
LinkedList.java
Nick Crews
11/20/15

An extension of LinkedListBase, adding the behavior of being an ordered list, 
with arbitray insertions at any index
*/

public class LinkedList extends LinkedListBase{

	public void appendTail(Object newData){
		// add this data to the tail of our linked list
		Node newNode = new Node(newData);
		newNode.setPrevious(this.tail);
		newNode.setNext(null);
		if (this.length > 0){
			this.tail.setNext(newNode);
		}
		else {
			this.head = newNode;
		}
		this.tail = newNode;
		this.length++;
	}

	public void appendHead(Object newData){
		// add this data to the head of our list
		Node newNode = new Node(newData);
		newNode.setPrevious(null);
		newNode.setNext(this.head);
		if (this.length > 0){
			this.head.setPrevious(newNode);
		}
		else {
			this.tail = newNode;
		}
		this.head = newNode;
		this.length++;
	}

	public void insert(Object newData, int index){
		// add this data in the indicated position


		// check that our index was valid
		if (index > this.length || index < -this.length-1){
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}

		// make the Node to add
		Node newNode = new Node(newData);

		// deal with the special cases of adding to either of the ends
		if (index == 0 || index == -this.length-1){
			appendHead(newData);
		}
		else if (index == this.length || index == -1){
			appendTail(newData);
		}


		// ok, now we know that we're trying to insert in the middle
		else{
			Node oldNode = getNode(index);
			Node prev = oldNode.getPrevious();
			newNode.setPrevious(prev);
			newNode.setNext(oldNode);
			prev.setNext(newNode);
			oldNode.setPrevious(newNode);
			this.length++;
		} 
	}

	public void replace(Object newData, int index){
		getNode(index).setData(newData);
	}

	public void replace(Object newData, Object query){
		getNode(query).setData(newData);
	}

}