/*
Calculator.java
Nick Crews
11/30/15

Evaluates an expression in reverse polish notation
*/

import java.util.Scanner;

public class RPNCalculator{



	public static void main(String[] args){
		// get our input
		System.out.println("Enter an expression in reverse polish notation," + 
			" with terms separated by spaces: ");
		String in = new Scanner(System.in).nextLine().trim();

		// calculate
		RPNCalculator calc = new RPNCalculator();
		int out = calc.compute(in);

		// display
		System.out.println("The answer is: " + out);
	}





	// our stack holds numbers, stored as ints
	Stack stack;

	public RPNCalculator(){
		this.stack = new Stack();
	}

	public int compute(String inString){
		// computes an expression in reverse polish notation

		// split our string up into terms
		String[] in = inString.split(" ");

		// go through all the terms
		for (String next : in){
			if (isNumeric(next)){
				stack.push(Integer.parseInt(next));
			}
			else if ("+-*/".contains(next) ) {
				operate(next);
			}
			// if it's anything besides a number or operator, the input was bad
			else {
				throw new RuntimeException("Didn't recognize the token '" + next +"'");
			}
		}
		// make sure that the input in its entirety was valid. 
		// If there were too many operands for the operators, this catches it
		if (stack.length() > 1){
			throw new RuntimeException("'" + inString + "' is not a valid input. It contains too many operands.");
		}

		// otherwise, we're good, the answer is the last one in the stack
		return (int) stack.pop();
	}

	private void operate(String oper){
		// perform the operation oper on the first two items on the stack, 
		// and push the result back onto the stack

		if (stack.length() < 2){
			throw new RuntimeException("Invalid input. There are not enough operands for the " +
				oper + " operand.");
		}
		int b = (int) stack.pop();
		int a = (int) stack.pop();

		int result;
		switch (oper){
			case "+":
				result = a + b;
				break;
			case "-":
				result = a - b;
				break;
			case "*":
				result = a * b;
				break;
			case "/":
				result = a / b;
				break;
			// uh oh, that wasn't a valid operator
			default:
				throw new RuntimeException("Unrecognized operator!");
		}

		stack.push(result);
	}

	public static boolean isNumeric(String s) { 
		// is the string a representation of a number 
    	return s.matches("[-+]?\\d*\\.?\\d+");  
	} 
}

