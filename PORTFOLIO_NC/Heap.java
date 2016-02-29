/*
Heap.java
Nick Crews
12/3/15

An implementation of the Heap data structure which stores objects that implement Comparable. 
Orders elements so that elements with a lower compareTo() value have the highest priority
For example the String "a" will come before "b", and the int "2" before "5".
If you want to reverse this ordering, make your own custom class with your custom compareTo() method.
*/

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Heap<E extends Comparable<? super E>>{
	
	protected ArrayList<E> arr;

	public Heap(){
		arr = new ArrayList<E>();
	}

	protected void add(E obj){
		// add a new leaf, so we can call arr.set() on the new index
		arr.add(null);

		// start at the bottom of the tree, in the next empty spot for a leaf
		// then, demote all parents which are larger than the new element
		int index = size()-1;
		while (index > 0 && obj.compareTo(parent(index)) < 0){
			arr.set(index, parent(index));
			index = parentIndex(index);
		}

		// add this new object in the right place
		this.arr.set(index, obj);
	}

	public E pop() throws NoSuchElementException{
		// get the top element and then resort
		if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        // get the first element in our data ArrayList
		E root = arr.get(0);
		// move the last element to the front, delete the last one, and then fix the heap condition
		arr.set(0, arr.get(size()-1));
		arr.remove(size()-1);
		fix(0);

		return root;
	}

	public E peek() throws NoSuchElementException{
		// get the top priority element of the heap
		if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
		return arr.get(0);
	}

	public E replace(E newObj) throws NoSuchElementException{
		// pop and return the min element, and add a new element and sort
		// effectively the same thing as calling pop() and then add(), but faster, eliminating a few unnecessary steps
		if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        // store the highest priority element
		E root = arr.get(0);
		// put the new object in the top position, and then fix the heap condition
		arr.set(0, newObj);
		fix(0);
		return root;
	}

	public int size(){
		return arr.size();
	}

	public boolean isEmpty(){
		return size() <= 0;
	}

	protected void fix(int iparent){
		// recursively fix our heap

		// get the two child indices and declare a variable to later store which is smaller
		int i1 = childOneIndex(iparent);
		int i2 = childTwoIndex(iparent);
		int imin;

		// base case
		// if neither of the children exist, then this parent node is a leaf, and we're done
		if (i1 >= size() && i2 >= size()){
			return;
		}
		// ok, maybe there is only one child. 
		// Since our heap is balanced, this can only happen when the last parent only has childOne
		else if (i2 >= size()){
			imin = i1;
		}
		// ok, the parent has two children. find the index of the smaller one
		else{
			imin = (arr.get(i1).compareTo(arr.get(i2)) < 0) ? i1 : i2; 
		}


		// So now we know which child is smaller.
		// now compare the parent to the child. If the child is smaller,
		// swap the parent and child, and call fix() on the new child

		// get the value of the parent and the minimum child. 
		// This just reduces the number of arr.get() calls
		E vparent = arr.get(iparent);
		E vmin = arr.get(imin);
		// does the child have higher priority than the parent?
		if (vmin.compareTo(vparent) < 0){
			// swap the parent and child, and call fix() on the new child
			arr.set(imin, vparent);
			arr.set(iparent, vmin);
			fix(imin);
		}
	}

	public String toString(){
		return arr.toString();
	}

	public ArrayList<E> sort(){
		// returns the sorted contents of this heap by popping the min element and then resorting
		// ****THE HEAP IS EMPTY AFTER THIS****
		ArrayList<E> out = new ArrayList<>();
		while (!isEmpty()){
			out.add(pop());
		}
		return out;
	}

	protected int childOneIndex(int parentIndex){
		return 2*parentIndex + 1;
	}

	protected int childTwoIndex(int parentIndex){
		return 2*parentIndex + 2;
	}

	protected int parentIndex(int childIndex){
		return (childIndex-1)/2;
	}

	protected E parent(int childIndex){
		return arr.get((childIndex-1)/2);
	}

	




}