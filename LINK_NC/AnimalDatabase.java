/*
Animals.java
Nick Crews
11/20/15

The controller for our program, holding the main method which is called.

Write a program to keep an alphabetical list of animals by 
building a singly linked list. Include an appropriate GUI for input and output. 
There should be at least be a text field for inputting an animal name 
and buttons for performing the following tasks:  
Add a name, Delete a name, Find a name, List all names.  
The output should appear in a text area that is scrollable.
You may want to display the list after each operation. 
*/

import java.awt.event.*;

public class AnimalDatabase implements ActionListener{

	public static void main(String[] args){
		new AnimalDatabase();
	}

	// our database owns both our list to store the animals, and a gui to interact with the user
	private GUI gui;
	private SortedStringLinkedList list;

	public AnimalDatabase(){
		this.gui = new GUI(this);
		this.list = new SortedStringLinkedList();
	}

	public void actionPerformed(ActionEvent e){
		// called whenever one of the buttons is pressed
		String action = e.getActionCommand();
		String s = this.gui.getInput().trim();
		if (s.equals("")){
			this.gui.message("Please enter an Animal");
		}
		else {
			switch (action) {
				case "addName":
					int addedIndex = this.list.add(s);
					this.gui.showEntries(this.list.toStringArray());
					if (addedIndex >= 0){
						this.gui.message("Added " + s + " to the Linked List in position " 
							+ addedIndex + ".");
					}
					else{
						this.gui.message("'" + s + "' is already in the Linked List.");
					}
					break;
				case "listAll":
					this.gui.showEntries(this.list.toStringArray());
					this.gui.message("Here are the contents of the Linked List.");
					break;
				case "deleteName":
					boolean successfullyDeleted = this.list.remove(s);
					this.gui.showEntries(this.list.toStringArray());
					if (successfullyDeleted){
						this.gui.message("Removed " + s + " from the Linked List.");
					}
					else {
						this.gui.message("Couldn't find that animal in the Linked List.");
					}
					break;
				case "findName":
					int index = this.list.find(s);
					if (index == -1){
						this.gui.message("Couldn't find '" + s + "' in the Linked List.");
					}
					else {
						this.gui.message("Found '" + s + "' on the Linked List at index " + index);
					}
			}
		}
	}


}