/*
PortfolioTester.java
Nick Crews
12/11/15

Test the various data structures in my portfolio
*/

import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class PortfolioTester{
	
	public static void main(String[] args){

		// make some data to test
		int[] ints = gen(15,15);
		String[] strings = {"The", "quick", "brown fox", 
			"JUMPED", "jumped", "over the", "!!!Lazy dog!!!"};



		// Test the Linked List
		// ------------------------------------------------
		print("----------------------------------\nTesting the Linked List.\n");

		SingleLinkedList sll = new SingleLinkedList();

		print(sll);
		for (String s: strings){
			sll.add(s);
		}
		print(sll);


		print(sll.find("This is not on the list"));
		print(sll.get(3));
		sll.insert("INSERTED1", 7);
		sll.insert("INSERTED2", 0);
		sll.insert("INSERTED3", -4);
		print(sll);
		Object removed = sll.remove(4);
		print(removed);
		print(sll);
		removed = sll.remove("NOT THERE");
		print(removed);
		print(sll);
		removed = sll.remove("!!!Lazy dog!!!");
		print(removed);
		print(sll);
		removed = sll.remove(-1);
		print(removed);
		print(sll);
		print(sll.length());
		sll.clear();
		print(sll);





		// Test the Heap
		// -----------------------------------------
		print("----------------------------------\nTesting the Heap.\n");
		// make our heap
		Heap<Integer> heap = new Heap<>();


		print("With integers:");
		// add everything to our heap
		for (int i : ints){
			heap.add(i);
		}
		print(heap);
		// now get the sorted list back out
		print(heap.sort());



		print("With Strings:");
		Heap<String> heap2 = new Heap<>();

		for (String s : strings){
			heap2.add(s);
		}
		print(heap2);
		print(heap2.peek());
		print(heap2);
		print(heap2.pop());
		print(heap2);
		print(heap2.replace("THIS IS A REPLACEMENT"));
		print(heap2);
		print(heap2.size());
		print(heap2.isEmpty());

		// now get the sorted list back out
		print(heap2.sort());
		print(heap2.isEmpty());


		// Test the Stack
		// -----------------------------------------
		print("----------------------------------\nTesting the Stack.\n");

		Stack stack = new Stack();
		print(stack);
		for (String s : strings){
			stack.push(s);
		}
		print(stack);

		print(stack.length());

		print(stack.peek());

		while(!stack.isEmpty()){
			print(stack.pop());
		}

		print(stack);
		for (Integer i: ints){
			stack.push(i);
		}
		print(stack);
		stack.clear();
		print(stack);

		


		// Test the Binary Tree
		// -----------------------------------------
		print("----------------------------------\nTesting the Binary Tree.\n");

		BinarySearchTree<String> bst = new BinarySearchTree<String>();
		print(bst);
		for (String s : strings){
			bst.add(s);
		}
		print(bst);

		print(bst.find("brown fox"));
		print(bst.find("this is not in the tree"));
		print(bst.remove("brown fox"));
		print(bst);
		print(bst.remove("this is not in the tree"));
		print(bst);
		print(bst.toArrayList());
		bst.clear();
		print(bst);




		// Test the Queue
		// -----------------------------------------
		print("----------------------------------\nTesting the Queue.\n");
		Queue q = new Queue();
		print(q);
		for (String s : strings){
			q.push(s);
		}
		print(q);
		print(q.peek());
		while(!q.isEmpty()){
			print(q.pop());
		}

		print(q);
		for (Integer i: ints){
			q.push(i);
		}
		print(q);
		print(q.length());
		q.clear();
		print(q);
	}



	private static void print(Object obj){
		System.out.println(obj);
	}

	public static int[] gen(int numberEntries, int max){
		// make an array of random ints, of length numberEntries, and max value max
		int[] out = new int[numberEntries];
		Random r = new Random();
		for (int i = 0; i < numberEntries; i++){
			out[i] = r.nextInt(max);
		}
		return out;
	}

}