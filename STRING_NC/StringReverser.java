/*
StringReverser.java
Nick Crews
11/16/15

Takes a string, inputted by the users, and prints out the words in reverse order.
*/


import java.util.Scanner;

// fairly stubby class which holds the main method for reversing the order of words in a user-inputted string
public class StringReverser {

	// The method which does all our work. Doesn't use any parameters given, doesn't return anything
    public static void main(String[] args) {

    	// make a scanner and read a line from the user as a string
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Give me a sentence to invert: ");
    	String input = reader.nextLine();

    	// Split the string into an array of strings
        // First trim whitespace, and then treat multiple whitespace as one delimiter
        String[] tokens = input.trim().split(" +");

        // print out each token in reverse order
        System.out.println("The reversed string is: ");
        for (int i = tokens.length - 1; i >= 0; i--) {
            System.out.print(tokens[i] + " ");
        }
        // to make it look pretty
        System.out.println();
       
    }

}