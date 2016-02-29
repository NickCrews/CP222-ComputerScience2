/*
Main.java
Nick Crews
12/7/15

The controller for our example with a Binary Search Tree
*/

import java.util.Scanner;

public class Main{
	
	public static void main(String[] args){

		// make our tree
		BinarySearchTree<Double
		> bst = new BinarySearchTree<>();

		// add some data to the tree. complete with repeats and some nice structure
		Double[] data = {10.,15.,5.,6.,16.,16.,3.,20.,-18.,7.,7.,.7,.2,.1,1.2,1.1,1.4,.13,8.};
		for (Double i : data){
			bst.add(i);
		}
		System.out.println("The tree is currently: " + bst);
		
		// ask the user for a new Integer
		System.out.print("Enter a number to find or add: ");
		Double in = Double.valueOf(new Scanner(System.in).nextLine());

		// If it's not in the tree, add it
		boolean found = bst.find(in);
		if (found){
			System.out.println("That number is in the tree!");
		}
		else {
			bst.add(in);
			System.out.println("Added that number to the tree!");
			System.out.println("The tree is now: " + bst);
		}
	}
}