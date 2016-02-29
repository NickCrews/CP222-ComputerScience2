/*
BinarySearchTree.java
Nick Crews
12/7/15

An implementation of a binary search tree. 
Each Node has the property that the left child has a smaller value than it
and each right child has a larger value than it
*/

import java.util.ArrayList;

public class BinarySearchTree<E extends Comparable<? super E>>{
	
	private Node root;
	private int size;

	public BinarySearchTree(){
		root = null;
		size = 0;
	}

	public void add(E obj){
		// adds  the new obj to the tree in the proper place

		// once we find where the object goes, we will need to save some things to actually add it
		Node current = root;
		Node previous = null;
		int order = 0;
		// start at the root and work down until you get to a leaf
		while (current != null){
			previous = current;
			order = obj.compareTo(current.data);
			current = (order <= 0) ? current.left : current.right;
		}

		// make a new Node and add it in the right place
		Node newNode = new Node(obj);
		// was the tree empty, are we still at the root?
		if (previous == null){
			root = newNode;
		}
		// okay, so tack on the newNode as the proper child of the previous Node
		// we know this new Node is a leaf, so we don't have to worry about any other references
		else {
			if (order <= 0){
			previous.left = newNode;
			}
			else {
				previous.right = newNode;
			}
		}
		size++;
	}

	public boolean find(E obj){
		// returns whether or not the given object can be found in the tree

		// start at the root and work towards the leaves
		Node current = root;
		while (current != null){
			int order = obj.compareTo(current.data);
			if (order == 0) {
				// we have found the right node!
				return true;
			}
			// this isn't the right Node. Go another level deeper
			current = (order < 0) ? current.left : current.right;
		}
		return false;
	}

	public boolean remove(E obj){
		// attempts to remove an element from the tree. 
		// Returns whether or not the element was succesfully found and removed

		// start at the root and work towards the leaves
		Node current = root;
		// we need to save the previous Node as well for the deletion process
		Node previous = null;
		while (current != null){
			int order = obj.compareTo(current.data);
			if (order == 0) {
				// we have found the right node!
				deleteNode(current, previous);
				size--;
				return true;
			}
			// this isn't the right Node. Go another level deeper
			previous = current;
			current = (order < 0) ? current.left : current.right;
		}
		return false;
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public void clear(){
		// removes all the elements
		root = null;
		size = 0;
	}

	public ArrayList<E> toArrayList(){
		// get all the elements in the tree, in natural order
		ArrayList<E> out = new ArrayList<E>();
		traverse(root, out);
		return out;
	}

	public String toString(){
		return toArrayList().toString();
	}

	private class Node{
		Node left;
		Node right;
		E data;

		Node(E newData){
			data = newData;
			left = null;
			right = null;
		}
	}

	private void deleteNode(Node current, Node parent){
		// there are three cases, the node has 0, 1, or 2 children

		// check for the case with no children. It's easy, just remove it and set the parent to null
		if (current.left == null && current.right == null){
			// is this node the root?
			if (parent == null){
				root = null;
			}
			// otherwise, set the proper branch of the parent Node to null
			else if (parent.left == current){
				parent.left = null;
			}
			else {
				parent.right = null;
			}
		}


		// check for the case with only one child. 
		// Pretty easy, just reroute the parent's pointer to the child of current node
		else if (current.left == null){
			// is this node the root?
			if (parent == null){
				root = current.right;
			}
			// otherwise, set the proper branch of the parent Node to current Node's child
			else if (parent.left == current){
				parent.left = current.right;
			}
			else {
				parent.right = current.right;
			}
		}
		// the other possibility for one child
		else if (current.right == null){
			// is this node the root?
			if (parent == null){
				root = current.left;
			}
			// otherwise, set the proper branch of the parent Node to current Node's child
			else if (parent.left == current){
				parent.left = current.left;
			}
			else {
				parent.right = current.left;
			}
		}


		// the final possibility of current having two children is a bit tougher.
		// Effectively we replace the current Node with the next largest one.
		// This can be found as the leftmost Node of the right subtree
		else{
			// find the next smallest Node (and it's parent, which is important fro removing it)
			Node nextSmallest = current.right;
			Node nextSmallestParent = current;
			while (nextSmallest.left != null){
				nextSmallestParent = nextSmallest;
				nextSmallest = nextSmallest.left;
			}

			// move the data from nextSmallest to current
			current.data = nextSmallest.data;

			// now delete the nextSmallest
			deleteNode(nextSmallest, nextSmallestParent);
		}


	}

	private void traverse(Node current, ArrayList<E> out){
		// recursively in-order traverse the tree and 
		// add the encountered elements to the given ArrayList
		if (current == null) {return;}
		traverse(current.left, out);
		out.add(current.data);
		traverse(current.right, out);
	}



}






