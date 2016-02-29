/*
TraditionalCalculator.java
Nick Crews
12/2/15

Uses an operand and operator stack to compute expressions in normal format, eg 2*(5-4)+3
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TraditionalCalculator{

	// private static Map<String, Integer> order
	// 	= new HashMap<String, Integer>(); {{
	//     put("-", 0);
	//     put("+", 0);
	//     put("/", 1);
	//     put("*", 1);
	// }};
	
	public static void main(String[] args){
		System.out.println("Enter an expression in normal notation: ");
		String in = new Scanner(System.in).nextLine();

		TraditionalCalculator calc = new TraditionalCalculator();
		int out = calc.compute(in);


		System.out.println(out);
	}

	public int compute(String inString){
		// computes an expression in reverse polish notation

		// split our string up into terms
		String[] in = TraditionalCalculator.split(inString);

		System.out.print(Arrays.toString(in));

		// our stack holds numbers, stored as ints
		Stack operandStack = new Stack();
		Stack operatorStack = new Stack();

		for (String next : in){
			if (isNumeric(next)){
				operandStack.push(Integer.parseInt(next));
			}
			else if (next.equals("(")){
				operatorStack.push(next);
			}
			else if ("+-*/".contains(next)){
	
			}
			else if ( next.equals(")") ){
				// String op = operatorStack.pop();
				while (!operatorStack.pop().equals("(")){
					operate(operandStack, operatorStack);
				}
			}

		}
		if (operandStack.length() > 1){
			throw new RuntimeException("'" + inString + "' is not a valid input. It contains too many operands.");
		}
		else {
			return (int) operandStack.pop();
		}
	}

	private void operate(Stack operandStack, Stack operatorStack){

		if (operandStack.length() < 2){
			throw new RuntimeException("Not enough operands in the stack!");
		}
		if (operatorStack.length() < 1){
			throw new RuntimeException("Not enough operators in the stack!");
		}

		int b = (int) operandStack.pop();
		int a = (int) operandStack.pop();
		String oper = (String) operatorStack.pop();

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
			default:
				throw new RuntimeException("Unrecognized operator!");
		}

		operandStack.push(result);
	}

	public static String[] split(String in){

		ArrayList<String> out = new ArrayList<String>();

		String currentTerm = "";

		for (int i = 0; i < in.length(); i++){
		    String s = in.substring(i, i+1);        
		    if (isNumeric(s)){
		    	currentTerm += s;
		    }
		    else if ( "+-*/()".contains(s) ) {
		    	out.add(currentTerm);
		    	out.add(s);
		    	currentTerm = "";
		    }
		    else {
		    	throw new RuntimeException("'" + in + 
		    		"' is not a valid input. Unrecognized characters.");
		    }
		}

		out.add(currentTerm);

		return out.toArray(new String[out.size()]);
	}

	public static int order(String a, String b){

		return 0;
	}

	public static boolean isNumeric(String s) {  
    	return s.matches("[-+]?\\d*\\.?\\d+");  
	} 

}