package chess;

import boardgame.Piece;

public abstract class ChessPiece extends Piece {
	
	private color color;

	public ChessPiece(boardgame.Board board, color Color) {
		super(board);
		this.color = Color;
	}

	public color getColor() {
		return color;
	}

	
	
	

}
