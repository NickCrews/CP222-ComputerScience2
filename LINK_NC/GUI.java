/*
GUI.java
Nick Crews
11/20/15

Our user interface for our program
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame{

	// These are the objects which will need to be remembered later on
	private AnimalDatabase listener;
	private JTextField nameField;
	private JLabel outputLabel;
	private JTextArea outputArea;

	public GUI(AnimalDatabase listener){

		// do our basic stuff
		super("Animal Database");
		setSize(700, 200);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// save who owns this instance. we'll set this to be our ActionListener
		this.listener = listener;
		
		setupGUI();
		// show everything
      	setVisible(true); 
	}

	private void setupGUI(){

		// make all of our components
		nameField = new JTextField("", 20);
		JLabel nameFieldLabel = new JLabel("Name:");

		JButton addNameB = new JButton("Add Name");
		addNameB.setActionCommand("addName");
		addNameB.addActionListener(this.listener);

		JButton findNameB = new JButton("Find Name");
		findNameB.setActionCommand("findName");
		findNameB.addActionListener(this.listener);

		JButton deleteNameB = new JButton("Delete Name");
		deleteNameB.setActionCommand("deleteName");
		deleteNameB.addActionListener(this.listener);

		JButton listAllNamesB = new JButton("List All Animals");
		listAllNamesB.setActionCommand("listAll");
		listAllNamesB.addActionListener(this.listener);

		outputLabel = new JLabel("");
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		JScrollPane scrollableOutputArea = new JScrollPane(outputArea);



		// make our layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// add all the components to our layout
		layout.setHorizontalGroup(layout.createSequentialGroup()
		    .addComponent(nameFieldLabel)
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		        .addComponent(nameField)
		        .addGroup(layout.createSequentialGroup()
	                .addComponent(addNameB)
	                .addComponent(findNameB))
		        .addGroup(layout.createSequentialGroup()
	                .addComponent(deleteNameB)
	                .addComponent(listAllNamesB))
		        .addComponent(outputLabel))
		    .addComponent(scrollableOutputArea)
		);

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(nameFieldLabel)
					.addComponent(nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(addNameB)
					.addComponent(findNameB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(deleteNameB)
					.addComponent(listAllNamesB))
				.addComponent(outputLabel))
			.addComponent(scrollableOutputArea)
		);

	}

	public String getInput(){
		// what is the current name in the field?
		return this.nameField.getText();
	}

	public void message(String m){
		// show a status message
		this.outputLabel.setText(m);
	}

	public void showEntries(String[] entries){
		// Show a list of Strings in the ourputArea
		String formatted = String.join("\n", entries);
		this.outputArea.setText(formatted);
	}
	

}