/*
Main.java
Nick Crews
12/10/15

Reads in a cost matrix and uses AStar to solve the TSP. Prints out some stats too
*/

import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;


public class Main{
	
	public static void main(String[] args){

		// read in our cost matrix
		if (args.length != 1){ 
			System.out.println("Please supply a cost-matrix filename as a command line argument.");
			return;
		}
		int[][] costMatrix = readCosts(args[0]);

		// let's see how long this takes to solve
		long startTime = System.nanoTime();

		// make our AStar searcher
		AStar solver = new AStar(costMatrix);
		// do the business
		int[] solution = solver.solve();
		

		// and stop our stopwatch
		long endTime = System.nanoTime();
		// display some statistics
		System.out.println("The best path found was " + Arrays.toString(solution)
			+ " with a cost of " + cost(solution, costMatrix));
		System.out.println("That took " + (endTime-startTime) / Math.pow(10, 9) + " seconds.");
		System.out.println("We visited a total of " + solver.nodesVisited + " Nodes.");
		System.out.println("At the longest, our queue had " + solver.maxQueueSize + " Nodes.");
	}


	public static int[][] readCosts(String fileName){
		try {
			Scanner sc = new Scanner(new File(fileName));
			int size = Integer.parseInt(sc.nextLine());
			
			int[][] out = new int[size][size];
			for (int i = 0; i < size; i++){
				String[] tokens = sc.nextLine().split(" ");
				for (int j = 0; j < size; j++){
					out[i][j] = Integer.parseInt(tokens[j]);
					// System.out.println(out[i][j]);
				}
			}
			return out;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static int cost(int[] path, int[][] costMatrix){
			// if we don't know the parent, calculate the total gCost from scratch
			int out = 0;
			for (int i = 1; i < path.length; i++){
				int from = path[i-1];
				int to = path[i];
				out += costMatrix[from][to];
			}
			return out;
		}

}