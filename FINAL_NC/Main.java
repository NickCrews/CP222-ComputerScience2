/*
Main.java
Nick Crews
12/13/15

The controller for our Othello game. Called as "java Main"

*/

import java.awt.Point;
   





public class Main{
	
	public static void main(String[] args){

		int BOARD_SIZE = 8;

		// make our two players
		// swap them both to ComputerPlayers for a good time!
		// Player playerOne = new ComputerPlayer(Board.BLACK);
		Player playerOne = new HumanPlayer(Board.BLACK);
		Player playerTwo = new ComputerPlayer(Board.WHITE);

		// make the game, with the right size and the two specified players
		Othello game = new Othello(BOARD_SIZE, playerOne, playerTwo);

		// play our game until gameover!
		while (!game.isGameover()){
			// print out some stuff so we know what is going on
			System.out.println("==================================================================");
			System.out.println(game.currentPlayer() + "'s turn: ");
			System.out.println(game.boardWithPossibleMoves());

			// get the move from the player.
			Point move = game.play();

			// will be null if that player's turn was skipped
			if (move != null){
				System.out.println(game.otherPlayer() + " played at (" + move.x + "," + move.y + ")");
			}
			else{
				System.out.println(game.otherPlayer() + "had their turn skipped!");
			}
		}

		// find and declare the winner
		Player winner = game.winner();
		// winner will be null if there was a tie
		if (winner != null){
			System.out.println("CONGRATULATIONS TO THE WINNER: " + winner);
		}
		else {
			System.out.println("THE GAME WAS A TIE!!");
		}
		


	}


}