/*
Main.java
Nick Crews
12/5/15

The controller for our heap and heapsort demo
*/

import java.util.*;
import java.io.*;

public class Main{
	
	public static void main(String[] args){

		// make a file filled with random integers
		ListGen.write(50, 50, "data.txt");

		// make our heap
		Heap<Integer> heap = new Heap<>();

		// recover our array of ints
		int[] unsorted = ListGen.read("data.txt");

		// add everything to our heap
		System.out.println("Unsorted:");
		for (int i : unsorted){
			System.out.println(i);
			heap.add(i);
		}

		// now get the sorted list back out
		System.out.println("Sorted:");
		for (int i : heap.sort()){
			System.out.println(i);
		}
	}

}