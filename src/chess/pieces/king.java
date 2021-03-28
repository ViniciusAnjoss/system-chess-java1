package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.color; 



    public class king extends  ChessPiece {
	
	public king (Board board, color color) {
		super (board, color); 
	}

    @Override

	public String toString() {
		return "K"; 
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}
}