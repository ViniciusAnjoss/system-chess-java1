package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.color;



    public class king extends  ChessPiece {
	
	public king (Board board, color color) {
		super (board, color);
	}

	public String toString() {
		return "K";
	}
}