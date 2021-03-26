package chess;

import boardgame.Board;
import chess.pieces.Tower;
import chess.pieces.king;



public class Chessmatch {
	
	private Board board;
	
	public Chessmatch() {
		board = new Board(8, 8);
		initialsetup();
	}
	
	public ChessPiece [][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
	 return mat;
	}
	private void PlaceNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
     private void initialsetup() {
    	PlaceNewPiece('b', 6, new Tower (board, color.WHITE));
        PlaceNewPiece('e', 8, new king (board, color.BLACK));
    	PlaceNewPiece('e', 1, new king (board, color.WHITE));
    	  }
}
