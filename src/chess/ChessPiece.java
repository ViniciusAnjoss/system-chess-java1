package chess;

import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private color color;

	public ChessPiece(boardgame.Board board, color Color) {
		super(board);
		this.color = Color;
	}

	public color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.formPosition(Position);
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
}


