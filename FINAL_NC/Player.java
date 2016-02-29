/*
Player.java
Nick Crews
12/13/15

*/
import java.awt.Point;

public abstract class Player{

	// unmodifiable public color
	public final int color;

	public Player(int color){
		if (color != Board.BLACK && color != Board.WHITE){
			throw new IllegalArgumentException("color can only be BLACK or WHITE.");
		}
		this.color = color;
	}

	public int otherColor(){
		return (color == Board.BLACK) ? Board.WHITE : Board.BLACK;
	}

	public String toString(){
		return (isHuman()?"Human":"Computer") + " player (" 
			+ Board.symbols.get(color) + ")";
	}
	
	public abstract Point getMove(Board b);

	public abstract boolean isHuman();



}