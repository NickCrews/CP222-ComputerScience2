/*
Stack.java
Nick Crews
11/30/15

An implementation of the Stack data structure using a linked list
*/

public class Stack{
	
	private SingleLinkedList list;

	public Stack(){
		this.list = new SingleLinkedList();
	}

	public void push(Object newData){
		this.list.add(newData);
	}

	public Object pop(){
		if (this.length() > 0){
			return this.list.pop();
		}
		else {
			return null;
		}		
	}

	public Object peek(){
		if (this.length() > 0){
			return this.list.get(0);
		}
		else {
			return null;
		}
	}

	public int length(){
		return this.list.length();
	}

	public boolean isEmpty(){
		return list.length() == 0;
	}

	public void clear(){
		// removes everything
		list.clear();
	}

	public String toString(){
		return this.list.toString();
	}

}