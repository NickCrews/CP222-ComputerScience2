/*
Calculator.java
Nick Crews
11/17/15

Practice with the GUI class to make a simple word calculator. 
Includes: 
a text field for inputting a sentence
display the number of words and characters
buttons to sort alphabetically or only show words with <= 4 characters
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


// This is our single class, which is both our GUI and our main thinker
public class Calculator extends JFrame implements ActionListener{

	// out inputField and outputField variables. 
	// The rest of the components of our GUI don't need to be referenced at an instance level
	private JTextField inputField;
	private JTextArea outputArea;

	// how many characters is the cutoff for sorting by word length
	private static int MAX_WORD_LENGTH = 4;

	public static void main(String[] args){
		// create our instance of a Calculator
		new Calculator();
	}

	public Calculator(){

		// first make our basic GUI
		super("Sentence Calculator");
		setSize(600, 500);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
      	/*
      	Now we need to add our different components and organize them.
      	We will have an input box at top, then our buttons and interactables,
      	then our output at the bottom
      	*/
      	getContentPane().setLayout(new GridLayout(3, 1));
      	JPanel inputPanel = makeInputPanel();
      	JPanel buttonPanel = makeButtonPanel();
      	JPanel outputPanel = makeOutputPanel();
      	add(inputPanel);
      	add(buttonPanel);
      	add(outputPanel);

      	// show everything
      	setVisible(true); 
	}

	private JPanel makeInputPanel(){
		// sets up the top input area of our GUI

		// make our panel which holds all the stuff at the top of our GUI
		JPanel inputPanel = new JPanel(new GridLayout(2,1));
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// make an input filed and a label to go in this panel
		inputField = new JTextField();
      	inputField.setActionCommand("input");
      	inputField.addActionListener(this);
      	JLabel inputLabel = new JLabel("Input: ");

      	// add the stuff to the panel
      	inputPanel.add(inputLabel);
      	inputPanel.add(inputField);
      	
      	return inputPanel;
	}

	private JPanel makeButtonPanel(){
		// Sets up the middle button area of our GUI


		// set up the panel which hols all the buttons
		JPanel buttonPanel = new JPanel(new GridLayout());
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// make our button to show number of words
      	JButton dispWordButton = new JButton("Number of words");
      	dispWordButton.setActionCommand("numWords");
      	dispWordButton.addActionListener(this);

      	// make our button for the number of characters
      	JButton dispCharButton = new JButton("Number of characters");
      	dispCharButton.setActionCommand("numChars");
      	dispCharButton.addActionListener(this);

      	// make our button to sort the words alphabetically
      	JButton alphButton = new JButton("Sort Alphabetically");
      	alphButton.setActionCommand("sortAlph");
      	alphButton.addActionListener(this);

      	// make our button to sort the words by length
      	JButton lengthButton = new JButton("Filter by length");
      	lengthButton.setActionCommand("sortLength");
      	lengthButton.addActionListener(this);

      	// add all the buttons to the panel
		buttonPanel.add(dispWordButton);
      	buttonPanel.add(dispCharButton);
      	buttonPanel.add(alphButton);
      	buttonPanel.add(lengthButton);

      	return buttonPanel;
      	
	}

	private JPanel makeOutputPanel(){
		// Sets up the bottom output area of our GUI
		JPanel outputPanel = new JPanel(new GridLayout(2,1));
		outputPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// make the stuff that goes inside this panel
      	outputArea = new JTextArea();
      	outputArea.setEditable(false);
      	JScrollPane scrollableOutputArea = new JScrollPane(outputArea);
      	JLabel outputLabel = new JLabel("Output:");

      	// add the outputLabel and area to the panel
		outputPanel.add(outputLabel);
      	outputPanel.add(scrollableOutputArea);
      	
      	return outputPanel;
	}

	static private int numWords(String in){
		// returns the number of tokens (delimited by whitespace) in the given string
		if (in.equals("")) return 0;
		else return in.split(" +").length;
	}

	static private int numChars(String in){
		// returns the number of nonwhitespace characters
		// does this by simply deleting all the whitespace and then finding length of result
		return in.replaceAll("\\s+", "").length();
	}

	static private String[] sortAlph(String in){
		// Given a string in, separates in into tokens (delimited by whitespace),
		// sorts the tokens in alphabetical order, and returns this array of Strings
		String[] tokens = in.trim().split(" +");
		Arrays.sort(tokens);
		return tokens;

	}

	static private String[] sortLength(String in){
		// given a String in, first split it into tokens.
		String[] tokens = in.trim().split(" +");

		// make an arraylist to hold all the short words
		ArrayList<String> list = new ArrayList<String>();
		// go through all the tokens and add them to the list if they are short enough
		for (String word : tokens) {
			if (word.length() <= MAX_WORD_LENGTH){
				list.add(word);
			}
		}

		// convert the ArrayList to a String[]
		String[] out = list.toArray(new String[list.size()]);
		return out;
	}


	public void actionPerformed(ActionEvent e){
		// Some action was performed on our GUI. Deal with it.
		String action = e.getActionCommand();
		// What was input? we'll use this later
		String input = inputField.getText();
		switch (action){
			case "input":
				outputArea.setText("You entered the sentence: " + input);
				break;
			case "numWords":
				outputArea.setText("There are " + numWords(input) + " words in the given sentence.");
				break;
			case "numChars":
				outputArea.setText("There are " + numChars(input) + " characters in the given sentence.");
				break;
			case "sortAlph":
				outputArea.setText("The sorted version of the input is:\n" + String.join("\n", sortAlph(input)));
				break;
			case "sortLength":
				outputArea.setText("Words that have " 
					+ MAX_WORD_LENGTH + " words or less:\n" + String.join("\n", sortLength(input)) );
				break;

		}

	}





}