/*
Permutations.java
Nick Crews
11/23/15

Recursively finds all the permutations of a set.
Takes a string as input and prints out all permutations of the string.
*/
import java.util.*;

public class Scramble{
	
	public static void main(String[] args){

		// get the input String
		System.out.print("Please enter a String to find all permutations of: ");
		String input = new Scanner(System.in).nextLine();

		// make our output ArrayList and then fill it with permutations of input
		ArrayList<String> output = new ArrayList<String>();
		permutations(input, output);

		// print out all the permutations
		for (String s : output){
			System.out.println(s);
		}
		
		// double check the number of permutations
		System.out.println("There were " + output.size() + 
			" combinations. There were supposed to be " + factorial(input.length()) + ".");
		
	}

	public static int factorial(int n){
		// simple recursive method to find the factorial of a number
		
		// base case
	    if (n <= 1){
	    	return 1;
	    }
        else {
            return n * factorial(n-1);
        }
	}

	public static void permutations(String in, ArrayList<String> out){
		// A wrapper method for the recursive perm(). This is what should be called externally
		perm(in, 0, out);
	}


	private static void perm(String in, int index, ArrayList<String> out){
		/* 
		A recursive function which takes a String as an input and finds all the 
		permutations of it, adding them to the output ArrayList out.
		Should inititially be called with index = 0, wrapped with the permutations() method.
		*/

		// memoize the length of the String for speed and readability
		int len = in.length();

		// Here is our base case. If everything is fixed, we're done
		if (len - index == 1){
			out.add(in);
			return;
		}

		// These are the characters which will be kept constant
		String prefix = in.substring(0, index);
		// now go through the rest of the String and swap things out 
		// to be the character following prefix
		for (int i = index; i < len; i++){

			// This is the character which will be placed after prefix
			String newFixed = in.substring(i,i+1);
			// These are the rest of the characters
			String rest = in.substring(index,i) + in.substring(i+1, len);
			// combine our prefix, along with the rearranged rest of the String
			String combined = prefix + newFixed + rest;

			// now find the permutations of this new scrambled String, with one more character fixed
			perm(combined, index+1, out);
		}
	} //end perm()

}