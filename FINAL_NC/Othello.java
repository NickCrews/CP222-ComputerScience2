/*
Othello.java
Nick Crews
12/13/15

A game of Othello.
This is a state machine. Changes only happen as a result of calls from elsewhere (eg Main.java)
*/
import java.awt.Point;
import java.util.Set;
import java.util.HashSet;

public class Othello{
	
	Player playerOne;
	Player playerTwo;
	Board board;

	Player currentPlayer;

	public Othello(int size, Player a, Player b){
		playerOne = a;
		playerTwo = b;
		board = Board.startingSetup(size);
		currentPlayer = playerOne;
	}

	public Point play(){
		// do one turn. Returns the move played, or null if that player's move was skipped

		Point move = null;
		// check to see if the current player has a move
		if (board.hasMove(currentPlayer.color)){

			// get a move, and keep on getting it until it's valid
			move = currentPlayer.getMove(board);
			while (!board.isValidMove(move, currentPlayer.color)){
				System.out.println("INVALID MOVE");
				move = currentPlayer.getMove(board);
			}

			// make the move
			board.place(move, currentPlayer.color);
		}
		swapPlayers();
		return move;
	}

	public Board board(){
		// get a deep copy of this board
		return board.clone();
	}

	public Board boardWithPossibleMoves(){
		// get a copy of the board, but with the possible moves marked
		Board copy = board.clone();
		Set<Point> moves = copy.moves(currentPlayer.color);
		for (Point move : moves){
			copy.set(move, Board.POSSIBLE);
		}
		return copy;
	}

	public boolean isGameover(){
		return board.isGameover();
	}

	public Player currentPlayer(){
		return currentPlayer;
	}

	public Player otherPlayer(){
		// get the player who is not the current player
		return (currentPlayer == playerOne) ? playerTwo : playerOne;
	}

	public Player winner(){
		// get the Player who won
		int w = board.winner();
		return (w == Board.TIE) ? null : (w == playerOne.color) ? playerOne : playerTwo;
	}

	public String toString(){
		return currentPlayer + "'s turn\n" + board.toString();
	}

	public void swapPlayers(){
		// swap which is the current player
		currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
	}

	

}



