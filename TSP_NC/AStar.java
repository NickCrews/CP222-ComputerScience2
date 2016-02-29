/*
AStar.java
Nick Crews
12/10/15

Uses the A Star search algorithm to solve the travelling salesman problem.
Basically uses a bunch of Nodes, which represent possible paths. 
Each node has a fCost, which is the sum of a gCost and an hCost. 
The gCost is the known exact cost used to get to the node from the start.
The hCost is an estimated amount from a hueristic function which estimates 
the cost needed to complete the path. 
It is the sum of all the minimum arrival costs for the unvisited cities. 
This makes it an underestimate, which makes the algorithm slower than optimum, 
but guarantees that we find the shortest path (it is "admissible").

So we put the start Node on a priority queue. We take the Node with the lowest fCost off the queue.
If it represents a complete path, we're done, this is the shortest. 
Otherwise, explore the children of this Node and add them to the queue. 
Repeat.

We use two inner classes:
Node: to represent the paths
Analyzer: takes care of figuring out the costs for the Nodes
*/


import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Arrays;

public class AStar{

	private int numCities;
	// What determines the cost of our different Nodes
	private Analyzer analyzer;

	// keep track of some stats
	public int maxQueueSize;
	public int nodesVisited;
	
	public AStar(int[][] costMatrix){
		numCities = costMatrix.length;
		analyzer = new Analyzer(costMatrix);
		maxQueueSize = 0;
		nodesVisited = 0;
	}


	// The main brute of our program
	public int[] solve(){

		// make a priority queue for the Nodes up for evaluation
		PriorityQueue<Node> q = new PriorityQueue<>();

		// put the starting Node in the open queue
		Node startingNode = makeStartingNode();
		q.add(startingNode);

		// while there's something in the the queue
		while (!q.isEmpty()){
			maxQueueSize = Math.max(q.size(), maxQueueSize);
			nodesVisited++;
			// get the top Node
			Node current = q.poll();

			// System.out.println("Exploring Node " + current);

			// is the current Node the solution?
			// This must be the shortest solution since it's on top of the queue
			if (current.isComplete()){
				return current.soFar;
			}
			// Otherwise go through all the children of this node
			for (Node child:current.children()){
				// add each child to the open queue with the priority of g()+f()
				q.add(child);
			}

		}
		return null;
	}

	private Node makeStartingNode(){
		// make the first Node we start with. soFar should only be [0], 
		// and notYet should be 1,2,3,...N, 0
		HashSet<Integer> notYet = new HashSet<>();
		for (int i = 0; i < numCities; i++){
			notYet.add(i);
		}
		return new Node(new int[]{0}, notYet);
	}









	private class Node implements Comparable<Node>{
		// a List of all the cities visited so far
		int[] soFar;
		// A set of all the cities that still need to be visited
		HashSet<Integer> notYet;

		// the known, exact travel cost to get from the start to here
		int gCost;
		// the heuristic cost, the prediced cost to get from here to the end, 
		// AKA the completed path. This is an UNDERESTIMATION of the true cost, 
		// (AKA it is admissible) in order to guarantee that we find the the shortest path
		int hCost;
		// The sum of fCost and gCost
		int fCost;
		

		// constructor with explicit arguments, used at the beginning for our beginning Node
		public Node(int[] soFar, HashSet<Integer> notYet){
			this.soFar = soFar;
			this.notYet = notYet;
			gCost = analyzer.g(this);
			hCost = analyzer.h(this);
			fCost = gCost + hCost;
		}

		// Constructor called when making a child of a Node. 
		// parent is the parent Node, and newCity is the city which we are moving from 
		// notYet to soFar
		public Node(Node parent, int newCity){
			// tack on the new city onto this nodes soFar
			this.soFar = Arrays.copyOf(parent.soFar, parent.soFar.length+1);
			this.soFar[this.soFar.length-1] = newCity;

			// remove the newCity from the not yet
			this.notYet = new HashSet<Integer>(parent.notYet);
			this.notYet.remove(newCity);
			
			// calculate the various costs. supply the parent to speed up the calculations
			gCost = analyzer.g(this, parent);
			hCost = analyzer.h(this, parent);
			fCost = gCost + hCost;
		}

		public int compareTo(Node other){
			// The Nodes are ordered in the Priority Queue based on their fCost,
			// which is the sum of their already travelled gCost and their predicted hCost
			int rank = fCost - other.fCost;
			if (rank != 0){return rank;}
			// otherwise break the tie by favoring the further explored Node
			else{
				return (notYet.size() < other.notYet.size()) ? -1 : 1;
			}
		}

		// get all the children of this node.
		// basically, these result from moving one city from notYet to soFar 
		public Node[] children(){
			// make our array to hold the results. 
			// We only choose starting city if there's nothing else, 
			// so the size of this array is a little complicated
			Node[] out = new Node[Math.max(1, notYet.size()-1)];
			
			// pick which city we are going to visit next
			int i = 0;
			for (Integer city : notYet){
				// only choose starting city if there's nothing else
				int startingCity = 0;
				if (city == startingCity && notYet.size()>1){
					// skip it
					continue;
				}
				// otherwise, make a child Node 
				else{
					out[i] = new Node(this, city);
					i++;
				}
			}
			return out;
		}

		public String toString(){
			return "soFar=" + Arrays.toString(soFar) 
				+ " notYet=" + notYet
				+ " gCost=" + gCost
				+ " hCost=" + hCost
				+ " fCost=" + fCost;
		}

		public boolean isComplete(){
			return notYet.size() == 0;
		}

	}














	private class Analyzer{
		// a private class which takes care of analyzing the costs for all the Nodes

		// given two cities, what is the cost for travelling between them?
		private int[][] costMatrix;
		// given a city, what is the minimum cost for arriving at that city
		private int[] minArrivalCosts;

		public Analyzer(int[][] costMatrix){
			this.costMatrix = costMatrix;
			minArrivalCosts = calcMinArrivalCosts(costMatrix);
		}

		public int g(Node node){
			// if we don't know the parent, calculate the total gCost from scratch
			int out = 0;
			for (int i = 1; i < node.soFar.length; i++){
				int from = node.soFar[i-1];
				int to = node.soFar[i];
				out += costMatrix[from][to];
			}
			return out;
		}

		public int g(Node node, Node parent){
			// Calculates the travel cost by adding 
			// the most recent travel cost to the travel cost of the parent
			int lastCity = node.soFar[node.soFar.length-2];
			int newCity = node.soFar[node.soFar.length-1];
			return parent.gCost + costMatrix[lastCity][newCity];
		}

		
		public int h(Node node){
			// if we don't have access to the parent, calculate the hCost from scratch
			int minFutureCost = 0;
			for (Integer city:node.notYet){
				minFutureCost += minArrivalCosts[city];
			}
			return minFutureCost;
		}

		public int h(Node node, Node parent){
			// calculates the heurisitc cost for a node
			// This is ismply the hCost of the parent, 
			// minus the arrivalCost of the city we just added
			int justAdded = node.soFar[node.soFar.length-1];
			return parent.hCost - minArrivalCosts[justAdded];
		}

		
		private int[] calcMinArrivalCosts(int[][]costs){
			// make our output
			int[] out = new int[costs.length];
			// go through every column
			for (int i = 0; i < costs.length;i++){
				// store the min entry per column
				int min = Integer.MAX_VALUE;
				// go through every entry in the column
				for (int j = 0; j<costs.length; j++){
					int entry = costs[j][i];
					// disregard the 0's in the diagonal
					if (entry == 0) {continue;}
					min = Math.min(min, entry);
				}
				// store the min arrival cost for that city
				out[i] = min;
			}
			return out;
		}

	}





}






