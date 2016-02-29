/*
HumanPlayer.java
Nick Crews
12/13/15

The human player
*/

import java.awt.Point;
import java.util.Scanner;

public class HumanPlayer extends Player{

	public HumanPlayer(int color){
		super(color);
	}

	public Point getMove(Board board){
		return readMove(this);
	}

	public boolean isHuman(){
		return true;
	}

	public static Point readMove(Player player){
		// no sort of input checking. if you don't play nice you just get an error!
		System.out.println("Enter a move for " + player + ": ");
		Scanner sc = new Scanner(System.in);
		int x = sc.nextInt();
		int y = sc.nextInt();
		return new Point(x, y);
	}
}