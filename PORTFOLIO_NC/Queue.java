/*
Queue.java
Nick Crews
12/11/15

A single ended queue implemented with a doubly linked list
*/

public class Queue{
	
	DoubleLinkedList list;

	public Queue(){
		list = new DoubleLinkedList();
	}

	public void push(Object newObject){
		// add to the right/tail of the list
		list.addRight(newObject);
	}

	public Object peek(){
		// get the left/head of the list
		return list.get(0);
	}

	public Object pop(){
		// get and remove the left/head of the list
		return list.remove(0);
	}

	public int length(){
		return list.length();
	}

	public boolean isEmpty(){
		return length() == 0;
	}

	public void clear(){
		list.clear();
	}

	public String toString(){
		return list.toString();
	}
	

}
