/*
ComputerPlayer.java
Nick Crews
12/13/15

*/
import java.awt.Point;
import java.util.Set;

public class ComputerPlayer extends Player{

	// how far deep in the game tree will we explore at most?
	public static final int MAXPLYS = 6;

	public ComputerPlayer(int color){
		super(color);
	}
	
	public Point getMove(final Board board){

		// get all the possible moves to make
		Set<Point> moves = board.moves(this.color);
		// make a variable to save the best move once we find it, and a variable to save the curent best score
		Point bestMove = null;
		int bestScore = Integer.MIN_VALUE;

		// go through all the possible moves
		for (Point move : moves){
			// look at the score that this move would result in
			Board child = board.clone();
			child.place(move, this.color);
			// start exploring farther down the game tree, going a max depth of MAXPLYS, starting from the opponents (minimizer) perspective
			int score = alphaBeta(child, Integer.MIN_VALUE, Integer.MAX_VALUE, MAXPLYS, false);

			System.out.println("\ntop score for the inititial move\n" + child + "\nis " + score);

			// was this move the best one so far?
			if (score >= bestScore){
				bestScore = score;
				bestMove = move;
			}

		}
		// return the best one we found. otherwise null if there are no moves
		return bestMove;
	}

	public boolean isHuman(){
		return false;
	}


	public int alphaBeta(Board board, int alpha, int beta, int depthLeft, boolean isMaximizer){

		// System.out.println("alphaBeta() on \n" + board +
		// 	"alpha:" + alpha + " beta:" +beta + " depth:" + depth + " maxDepth:" + maxDepth);

		// Have we gone as far as we want?
		if (depthLeft == 0){
			int score = Evaluator.evaluate(board, this.color);
			// System.out.println("returning score " + score);
			return score;
		}

		// Are we at the end of the game?
		if (board.isGameover()){
			int score = Evaluator.evaluateEndGame(board, this.color);
			// System.out.println("returning END OF GAME score " + score);
			return score;

		}

		// are we at a max or min node?		
		if (isMaximizer){
			// we're at a maximizing node, the computer

			// what is the max score which can result from this node?
			int maxScore = Integer.MIN_VALUE;

			// get all the possible child boards, which result from all possible moves
			Set<Board> children = board.children(this.color);

			// if we don't have any moves, skip this turn and give it back to the other player
			if (children.isEmpty()){
				return alphaBeta(board, alpha, beta, depthLeft-1, false);
			}

			// otherwise, try all the moves. We might get a cutoff along the way
			for (Board child : children){
				// can we get a higher score?
				maxScore = Math.max(maxScore, alphaBeta(child, alpha, beta, depthLeft-1, false));
				alpha = Math.max(alpha, maxScore);

				// check for alpha-beat cutoff
				if (maxScore >= beta){
					// We would never reach this node!
					return maxScore;
				}
			}
			// if we didn't get a cutoff, return the maxScore found so far
			return maxScore;
		}
		else {
			// we are at a minimizer node

			// what is the minimum score which could resultfrom this node?
			int minScore = Integer.MAX_VALUE;

			// get all the possible child boards, which result from all possible moves
			Set<Board> children = board.children(Board.otherColor(this.color));
			// if we don't have any moves, skip this turn and give it back to the other player
			if (children.isEmpty()){
				return alphaBeta(board, alpha, beta, depthLeft-1, true);
			}

			// otherwise, try all the moves. We might get a cutoff along the way
			for (Board child : children){
				// can we get a smaller score?
				minScore = Math.min(minScore, alphaBeta(child, alpha, beta, depthLeft-1, true));
				beta = Math.min(beta, minScore);

				// check for alpha-beta cutoff
				if (minScore <= alpha){
					// we would never reach this node!
					return minScore;
				}
			}
			// if we didn't get a cutoff, return the minScore found so far
			return minScore;
		}
	}





	public static class Evaluator {

		// I just picked these weights out of a hat.
		// position is basically the only thing considered, 
		// but I left M and C non zero in case you want to use a board which isn't 8 by 8

		// weights for the mobility
		public static final int M = 2;
		// and pieceCount
		public static final int C = 1;
		// and piece position
		public static final int P = 100;

		// the values for all of the various positions. Only works for 8 by 8 boards
		public static final int[][] POSITION_VALUES = new int[][]
		{{50, -1, 5, 2, 2, 5, -2, 50},
		{-1, -10, 1, 1, 1, 1, -10, -1},
		{5,   1,  1, 1, 1, 1,  1,   5},
		{2,   1,  1, 0, 0, 1,  1,   2},
		{2,   1,  1, 0, 0, 1,  1,   2},
		{5,   1,  1, 1, 1, 1,  1,   5},
		{-1, -10, 1, 1, 1, 1, -10, -1},
		{50, -1, 5, 2, 2, 5, -2, 50}};



		public static int evaluate(Board b, int color){
			// if the board is 8 by 8 we can use positional heuristic. Otherwise default to pieceCount and mobility only
			if (b.SIZE == 8){
				int other = Board.otherColor(color);

				// get the difference between this color and the other color's number of moves
				int m = b.moves(color).size()-b.moves(other).size();

				// what is the difference between number of pieces
				int c = b.pieceCount(color) - b.pieceCount(other);

				// get the score based on positions
				int p = 0;
				int[][] data = b.data();
				// go through the board
				for (int i = 0; i < b.SIZE; i++){
					for (int j = 0; j <b.SIZE; j++){
						// what color piece is here?
						int piece = data[i][j];

						// if the piece is this color, we will count it. 
						// if its the other color, it counts as negative
						// if it's empty or possible, ignore it
						int scalar = 0;
						if (piece == color){scalar = 1;}
						else if (piece == Board.otherColor(color)) {scalar = -1;}
						p += scalar*POSITION_VALUES[i][j];
					}
				}

				// now figure out the value of each positions
				return m*M + c*C + p*P;
			}
			else{
				// otherwise, the board isn;t 8 by 8.
				// default to pieceCount and mobility only
				int other = Board.otherColor(color);

				// get the difference between this color and the other color's number of moves
				int m = b.moves(color).size()-b.moves(other).size();

				// what is the difference between number of pieces
				int c = b.pieceCount(color) - b.pieceCount(other);

				return m*M + c*C;

			}

			
		}



		// called whenever the game is over
		public static int evaluateEndGame(Board b, int color){
			// returns "infinity," 0, or "negative infinity" if the player wins, ties, or loses
			int w = b.winner();
			return (w == Board.TIE) ? 0 : (w == color) ? Integer.MAX_VALUE : Integer.MIN_VALUE;

		}

	}

}