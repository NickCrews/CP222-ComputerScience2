/*
Nick Crews
Board.java
12/13/15

A class which represents a board state in othello. The Board knows nothing of the Player object. 
It only keeps track of things by color.
*/
import java.awt.Point;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Board{

	// some enumerations
	public static final int BLACK = -1;
	public static final int WHITE = 1;
	public static final int EMPTY = 0;
	public static final int POSSIBLE = 2;
	public static final int TIE = 3;

	// make the symbols which will be printed for each piece
	public static Map<Integer, String> symbols = new HashMap<>();
	static {
		symbols.put(EMPTY,"_");
        symbols.put(BLACK,"○");
        symbols.put(WHITE, "●");
        symbols.put(POSSIBLE, "·");
    }

    // instance variables
	public final int SIZE;
	private int[][] data;

	public Board(int[][] data){
		this.data = data;
		this.SIZE = data.length;
	}

	public int[][] data(){
		// return a copy of this Board's data int[][]
		int[][] dataCopy = new int[data.length][];
		for (int i = 0; i < data.length; i++) {
			dataCopy[i] = Arrays.copyOf(data[i], data[i].length);
		}
		return dataCopy;
	}

	public Set<Point> moves(final int color){
		// get all the possible moves for a certain color
		Set<Point> out = new HashSet<>();
		// go through all the positions on the board
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				// is there a move at this location?
				Point move = new Point(i, j);
				if(isValidMove(move, color)){
					out.add(move);
				}
			}
		}
		return out;
	}

	public void place(final Point move, final int color){
		// place a piece of the specified color at the specified location
		set(move, color); 
		// flip all the resulting pieces
		flip(flippedPieces(move, color));
	}

	public boolean isValidMove(final Point move, final int color){
		// first check that this placement is good
		if (!isValidIndex(move) || isOccupied(move)) {
			return false;
		}
		// if this move doesnt flip some pieces, it's not valid
		if (flippedPieces(move, color).isEmpty()){
			return false;
		}
		return true;
	}

	public Set<Point> flippedPieces(final Point move, final int color){
		// check which spaces should be flipped
		Set<Point> flipped = new HashSet<>();
		// let's explore in every one of the eight directions
		for (Direction d: Direction.values()){
			// make a set to store the pieces that might need to get flipped
			Set<Point> possibles = new HashSet<>();
			Point current = d.next(move);
			// expand outward
			while(isValidIndex(current) && get(current) == otherColor(color)){
				// if the nearby pieces are the other color, add them
				possibles.add(current);
				current = d.next(current);
			}
			// ok, if we're still on the board and we've run into the original color again
			// add those possibles to the definitely flipped
			if (isValidIndex(current) && get(current) == color){
				flipped.addAll(possibles);
			}
		}
		return flipped;
	}

	public void flip(final Set<Point> pts){
		for (Point pt: pts){
			set(pt, Board.otherColor(get(pt)));
		}
	}

	public void set(final Point pt, final int color){
		data[pt.x][pt.y] = color;
	}

	public int get(final Point pt){
		return data[pt.x][pt.y];
	}

	public static Board startingSetup(int size){
		int[][] data = new int[size][size];
		data[size/2-1][size/2-1] = BLACK;
		data[size/2-1][size/2] = WHITE;
		data[size/2][size/2-1] = WHITE;
		data[size/2][size/2] = BLACK;
		return new Board(data);
	}

	public String toString(){
		// make the label for columns
		String out = " ";
		for (int i = 0; i < SIZE; i++){
			out += " " + i;
		}
		out+="\n";

		// make the board
		for (int i = 0; i < SIZE; i++){
			// make the row label and opening [
			out += i +"[";
			for (int j = 0; j < SIZE; j++){
				// add the right symbol
				out += symbols.get(data[i][j]);
				// separate elements with |
				if (j != SIZE-1) {out+="|";}
			}
			out += "]";
			// go to the next line except for the last line
			if (i != SIZE-1) {out+="\n";}
		}
		return out;
	}

	private boolean isValidIndex(final Point pt){
		// make sure this index is within bounds
		return (pt.x>=0 && pt.x<SIZE && pt.y>=0 && pt.y<SIZE);
	}

	private boolean isOccupied(final Point pt){
		// is there already a piece here?
		return data[pt.x][pt.y] == BLACK || data[pt.x][pt.y] == WHITE;
	}

	public static int otherColor(int color){
		switch (color){
			case BLACK:
				return WHITE;
			case WHITE:
				return BLACK;
			default:
				return EMPTY;
		}
	}

	public Board clone(){
		// get a deep copy of this board
		return new Board(data());
	}

	public Set<Board> children(int color){
		// get all the Boards which result from making all the possible moves of the specified color

		// get all the possible moves
		Set<Point> moves = moves(color);
		Set<Board> children = new HashSet<Board>();

		// apply all the moves to clones of this board, and add the children
		for (Point move : moves){
			Board newBoard = this.clone();
			newBoard.place(move, color);
			children.add(newBoard);
		}
		return children;
	}

	public boolean isGameover(){
		// do neither player have a move?
		return (!hasMove(BLACK) && !hasMove(WHITE));
	}

	public boolean hasMove(int color){
		// does this color hava move?
		return !moves(color).isEmpty();
	}

	public int winner(){
		// get the color of the player who has the most number of pieces
		// could be called at anytime, not just endgame!
		int b = pieceCount(BLACK);
		int w = pieceCount(WHITE);
		return (b==w) ? TIE : (b>w) ? BLACK : WHITE;
	}

	public int pieceCount(int color){
		// how many pieces are there on the board of this color?
		int out = 0;
		for (int[] row : data){
			for (int piece : row){
				if (piece == color){out++;}
			}
		}
		return out;
	}

	// used to iterate out from a placement to find flipped pieces
	public enum Direction {
		NORTH(-1, 0),
		SOUTH(+1, 0),
		WEST(0, -1),
		EAST(0, +1),
		NORTHWEST(-1, -1),
		SOUTHEAST(+1, +1),
		SOUTHWEST(+1, -1),
		NORTHEAST(-1, +1);
		private int rowstep;
		private int colstep;

		private Direction(int rowstep, int colstep) {
			this.rowstep = rowstep;
			this.colstep = colstep;
		}

		public Point next(Point point) {
			return new Point(point.x + rowstep, point.y + colstep);
		}
	}

}





