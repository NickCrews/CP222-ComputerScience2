/*
Sort.java
Nick Crews
12/2/15

A bunch of different sorting algorithms. 
*/

import java.util.Arrays;

public class Sort{


	// I found this global variable to be the easiest way of keeping track of the order of the different algorithms,
	// since some methods are recursive.
	// counter must be reset and read externally between calls
	// It's a bit arbitrary, but I chose to only count comparisons bewteen elements of an array,
	// not comparisons between indices etc.
	static public int counter;

	public static void selectionSort(int[] arr){
		// find the min number to put in first slot, then the next lowest to put in 2nd, etc.
		for (int i = 0; i < arr.length-1; i++){
			// where is the min in the rest of the array?
			int minIndex = i;
			for (int j = i+1; j < arr.length; j++){
				counter++;
				if (arr[j] < arr[minIndex]){
					minIndex = j;
				}
			}
			// put the minimum in the right spot
			swap(arr, i, minIndex);
		}
	}

	public static void bubbleSort(int[] in){
		// iterate through our array many times, each time starting one more forward, 
		// "bubbling" high numbers farther down each time
		for (int i = 0; i < in.length; i++){
			for (int j = i; j < in.length-1; j++){
				counter++;
				// shift the value at j up, if applicable
				if (in[j] > in[j+1]){
					swap(in, j, j+1);
				}
			}
		}
	}


	public static void insertionSort(int[] in){

		// go through the array, each time sorting in[0] to in[i]
		for (int i = 1; i < in.length; i++){
			// current is the next unsorted element. 
			// We need to figure out where it goes in the sorted sequence before it
			int current = in[i];
			// here is our index that cascades back down to 0
			int j = i;
			counter++;
			while (j > 0 && current < in[j-1]){
				counter++;
				// shift everything up a slot if we need to
				in[j] = in[j-1];
				j--;
			}
			// now remember to actually place current in the correct spot
			in[j] = current;
		}
	}

	public static void quickSort(int[] in){
		// wapper method for quicksort
		quickSort(in, 0, in.length-1);
	}

	

	public static void mergeSort(int[] in){

		// base case
		if (in.length <= 1){
			return;
		}

		// split our array into two arrays holding each half
		int[] first = new int[in.length / 2];
		int[] second = new int[in.length - first.length];
		for (int i = 0; i < first.length; i++){
			first[i] = in[i];
		}
		for (int i = 0; i < second.length; i++){
			second[i] = in[i + first.length];
		}

		// sort each half
		mergeSort(first);
		mergeSort(second);
		// stick them back together
		merge(first, second, in);
	}

	public static void shellSort(int[] in){

	}




	private static void merge(int[] a, int[] b, int[] out){
		// expects two sorted arrays a and b, and an output array to put them in

		// the three moving indices for our arrays
		int ai = 0;
		int bi = 0;
		int outi = 0;

		// go through a and b until we've run into the end of either one
		while (ai < a.length && bi < b.length){
			counter++;
			// place the smaller of a or b into out, and move onto the next index of a or b
			if (a[ai] < b[bi]){
				out[outi] = a[ai];
				ai++;
			}
			else {
				out[outi] = b[bi];
				bi++;
			}
			// move on to the next index of out
			outi++;
		}

		// if there are still some left in a or b, copy them into out
		while(ai < a.length){
			out[outi] = a[ai];
			ai++;
			outi++;
		}
		while(bi < b.length){
			out[outi] = b[bi];
			bi++;
			outi++;
		}

	}

	private static void swap(int[] arr, int a, int b){
		// helper method for swapping values in an array
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	private static void quickSort(int[] arr, int from, int to){
		// What actually does quicksort
		// recieves an array to sort in place, and the indices of what range to sort, both INCLUSIVE

		// base case, this section is length one or less
		if (from >= to){
			return;
		}

		// arbitrarily pick pivot as the first value in this range
		int pivot = arr[from];
		// make our moving pointers
		int i = from+1;
		int j = to;
		
		// now we need to partition our range so all elements <= pivot are to the left,
		// and all elements >= pivot are on the right
		while (i<=j){
			// move our one pointer inward
			// slightly awkward nested conditionals are needed to get the true counter
			// otherwise would put both in the while conditional
			counter++;
			while (arr[i]<=pivot){
				i++;
				if (i >= to){break;}
				counter++;
			}
			// move the other one inward
			counter++;
			while (arr[j]>=pivot){
				j--;
				if (j <= from) {break;}
				counter++;
			}

			/*
			swap the two values at the pointers, if applicable
			Here is the reason we need to actively break:
			our outer while loop requires a less than OR EQUAL TO since there is the case
			where we are sorting a list of length 2, and i and j will start with the same value
			but still need to move
			Thus, to avoid an infinite loop where the pointers are stuck on either end of the range,
			we need to break 
			*/
			if (i<j){
				swap(arr, i, j);
			}
			else {
				break;
			}
		}

		// move our pivot element into the right place
		swap(arr, from, j);
		
		// sort the two halves
		quickSort(arr, from, j-1);
		quickSort(arr, j+1, to);
	}

	private static void print(int[] arr){
		System.out.println(Arrays.toString(arr));
	}


}