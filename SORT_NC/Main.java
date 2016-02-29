/*
Main.java
Nick Crews
12/2/15

Class just ot hold the main method for the Sorting example


We expect selection sort to have N(N-1)/2 comparisons. 
So for our lists of size 10, 100, 1000, and 1000 we should see
45, 4950, 499500, and 49995000 comparisons, respectively. This is indeed what we observe.

*/

import java.util.Arrays;
import java.util.ArrayList;


public class Main{
	
	public static void main(String[] args){

		// here are all our algorithms to test
		String[] algorithms = {"selection", "bubble", "insertion", "quick", "merge"};

		// make an ArrayList holding all our unsorted lists
		int[] sizes = {0, 1, 2, 10, 100, 500};
		ArrayList<int[]> lists = new ArrayList<int[]>();
		for (int size : sizes){
			lists.add(ListGen.gen(size, size));
		}
		
		// now test each algorithm with every list
		for (String alg : algorithms){
			for (int[] list: lists){

				// check to see that our algorithm worked
				// comment this out to clean up the terminal output
				int[] result = test(alg, list);
				System.out.println(alg +"Sort() took the list " + Arrays.toString(list) 
					+ " and sorted it into " + Arrays.toString(result));
				

				// check how many comparisons our algorithm took
				// comment this out to clean up the terminal output
				int comps = benchmark(alg, list);
				System.out.println("With a list of length " + list.length
					+ ", " + alg + "Sort() took " + comps + " comparisons.");
			}
		}
	}

	public static int[] test(String algorithm, int[] list){
		// test an algorithm to make sure it actually sorts correctly
		int[] copy = Arrays.copyOf(list, list.length);
		switch (algorithm){
			case "selection":
				Sort.selectionSort(copy);
				break;
			case "bubble":
				Sort.bubbleSort(copy);
				break;
			case "insertion":
				Sort.insertionSort(copy);
				break;
			case "quick":
				Sort.quickSort(copy);
				break;
			case "merge":
				Sort.mergeSort(copy);
				break;
		}
		return copy;
	}

	public static int benchmark(String algorithm, int[] list){
		// count the number of comparisons an algorithm took
		Sort.counter = 0;
		int[] copy = Arrays.copyOf(list, list.length);
		switch (algorithm){
			case "selection":
				Sort.selectionSort(copy);
				break;
			case "bubble":
				Sort.bubbleSort(copy);
				break;
			case "insertion":
				Sort.insertionSort(copy);
				break;
			case "quick":
				Sort.quickSort(copy);
				break;
			case "merge":
				Sort.mergeSort(copy);
				break;
		}
		return Sort.counter;
	}

}