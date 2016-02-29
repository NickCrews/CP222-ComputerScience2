/*
Files.java
Nick Crews
11/18/15

GUI based program. Given a name and a File, check to see if that name is in the file. If not, add it to the end of the file.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Files extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		// create our instance of a File
		new Files();
	}

	private JTextField fileField;
	private JLabel fileLabel;
	private JButton fileButton;
	private JTextField nameField;
	private JLabel nameLabel;
	private JButton goButton;


	public Files(){
		super("File and Name");
		setSize(600, 500);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
      	// setup our GUI. There are 
      	getContentPane().setLayout(new GridLayout(2, 1));
      	setupGUI();

      	// show everything
      	setVisible(true); 
	}

	private void setupGUI(){
		// There are two main areas in our GUI
		JPanel mainPanel = new JPanel(new GridLayout(2,3));
		JPanel instructionPanel = new JPanel();

		// add all the stuff to the main panel
		fileLabel = new JLabel("File:");
		fileField = new JTextField();
		fileButton = new JButton("Choose File");
		fileButton.setActionCommand("file");
      	fileButton.addActionListener(this);

      	nameLabel = new JLabel("Name:");
      	nameField = new JTextField();
      	goButton = new JButton("Go!");
      	goButton.setActionCommand("go");
      	goButton.addActionListener(this);

      	mainPanel.add(fileLabel);
      	mainPanel.add(fileField);
      	mainPanel.add(fileButton);
      	mainPanel.add(nameLabel);
      	mainPanel.add(nameField);
      	mainPanel.add(goButton);


      	// add stuff to the instruction panel
 		JLabel instructions = new JLabel("Pick a file and a name, then hit Go!");
 		instructionPanel.add(instructions);

		add(mainPanel);
		add(instructionPanel);

	}

	private void go(String fileString, String name){
		//Called whenever the "Go!" button is pressed. Deals with all possible inputs in the fields

		// First let's check to make sure there was something in the fields and its of the form we want
		if (fileString.equals("") && name.equals("")){
			message("You didn't enter a file or name.");
		}
		else if (fileString.equals("") ){
			message("You didn't enter a file.");
		}
		else if (name.equals("")){
			message("You didn't enter a name.");
		}
		else if (!fileString.endsWith(".txt") && !fileString.endsWith(".text")){
			message("The file must be a .txt or .text file.");
		}
		else{
			// ok, there was something in both fields. Continue.
			// Attempt to search for the name in the file and add it if it's not there
			int result = checkAndAdd(new File(fileString), name);
			switch (result) {
				case 1:
					message("That name was found in the file!");
					break;
				case 2:
					message("That name was added to the file!");
					break;
				case 0:
					message("There was a problem with that file.");
					break;
			}
		}
	}

	private int checkAndAdd(File f, String name){
		/*Searches through a file F for the String name. 
		If found, return 1.
		Else, add the name to the end of the file, and return 2.
		If there was an error along the way, return 0
		*/

		// Let's try to read through the file, and if necesarry write to it
		try {


			Scanner sc = new Scanner(f);
			// look through the file
			while (sc.hasNextLine()){
				if (sc.nextLine().toLowerCase().contains(name.toLowerCase())){
					// That name was in the file!
					sc.close();
					return 1;
				}
			}
			sc.close();


			// the name wasn't found in the file. Add it to the end.
			 BufferedWriter bw = new BufferedWriter(new FileWriter(f.getPath(), true));
			 bw.write("\n" + name);
			 bw.flush();
			 bw.close();
			 // the name was succesfully written to the file!
			 return 2;
			
			
		}
		// there was an error while doing stuff with the scanner
		catch (IOError e){
			return 0;
		}
		catch (FileNotFoundException e){
			return 0;
		}
		// there was an error with writing
		catch (IOException e){
			return 0;
		}

	}


	private String chooseFile(){
		/* called whenever the fileButton is pressed. 
		Opens a JFileChooser for the user to pick from.
		Returns the String of the file path, or a blank string if none is selected*/

		// make a JFileChooser instance, with the beginning path of the file chooser to the current directory
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		// make a filter so we can only pick .txt or .text files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", new String[] {"txt", "text"});
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);

		// now show the chooser to the user
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			return chooser.getSelectedFile().getPath();
		}
		// The user cancelled. Return a blank String
		else return "";
	}


	public void actionPerformed(ActionEvent e){
		// called whenever one of the buttons is pressed
		String action = e.getActionCommand();
		switch(action) {

			// the "Choose File" button was pressed
			case "file":
				String fileString = chooseFile();
				if (!fileString.equals("")){
					// The user actually picked a file, so deal with it
					fileField.setText(fileString);
				}
				// the user cancelled choosing a file, so do nothing
				break;

			// The "go" button was pressed
			case "go":
				go(fileField.getText(), nameField.getText());
				break;
		}
	
	}

	private void message(String mess){
		// wrapper method for showing a notification
		JOptionPane.showMessageDialog(this, mess, "Warning", JOptionPane.WARNING_MESSAGE);
	}


}





