package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.color;



public class Tower extends  ChessPiece{
	
	public Tower (Board board, color color) {
		super (board, color);
	}

	public String toString() {
		return "T";
	}
}