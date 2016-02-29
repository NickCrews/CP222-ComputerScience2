/*
Stack.java
Nick Crews
11/30/15

An implementation of the Stack data structure using a linked list
*/

public class Stack{
	
	private LinkedList list;

	public Stack(){
		this.list = new LinkedList();
	}

	public void push(Object newData){
		this.list.appendTail(newData);
	}

	public Object pop(){
		if (this.length() > 0){
			return this.list.remove(-1);
		}
		else {
			return null;
		}		
	}

	public Object peek(){
		if (this.length() > 0){
			return this.list.get(-1);
		}
		else {
			return null;
		}
	}

	public int length(){
		return this.list.length();
	}

	public void clear(){
		// removes everything
		for (int i = 0; i < this.length(); i++){
			this.pop();
		}
	}

	public String toString(){
		return this.list.toString();
	}

}