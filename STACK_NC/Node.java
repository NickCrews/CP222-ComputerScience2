/*
Node.java
Nick Crews
11/20/15

One of the nodes within our LinkedList. Can hold any type of Object.
*/


public class Node{
		
	private Object data;
	private Node next;
	private Node previous;

	public Node(Object data){
		this.data = data;
		this.next = null;
		this.previous = null;
	}

	public Node(Object data, Node next){
		this.data = data;
		this.next = next;
		this.previous = null;
	}

	public Node(Object data, Node next, Node previous){
		this.data = data;
		this.next = next;
		this.previous = previous;
	}

	public boolean isTail(){
		return (this.next == null);
	}

	public boolean isHead(){
		return (this.previous == null);
	}

	public boolean isEnd(){
		return isHead() || isTail();
	}

	public Node getNext(){
		return this.next;
	}

	public void setNext(Node next){
		this.next = next;
	}

	public Node getPrevious(){
		return this.previous;
	}

	public void setPrevious(Node previous){
		this.previous = previous;
	}

	public Object getData(){
		return this.data;
	}

	public void setData(Object newData){
		this.data = newData;
	}

	public String toString(){
		return "Node containing " + this.data.getClass() +
			" object with string representation: " + this.data.toString();
	}


}