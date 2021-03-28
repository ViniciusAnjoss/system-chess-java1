package chess;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;

public abstract class ChessPiece extends Piece {
	
	private color color;

	public ChessPiece(boardgame.Board board, color Color) {
		super(board);
		this.color = Color;
	}

	public color getColor() {
		return color;
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
}


