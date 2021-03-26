package chess;

import boardgame.Board;
import chess.pieces.Tower;
import chess.pieces.king;
import boardgame.Position;


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
     private void initialsetup() {
    	 board.placePiece(new Tower (board, color.WHITE),new Position(2, 1));
    	 board.placePiece(new king (board, color.BLACK),new Position(0, 4));
    	 board.placePiece(new king (board, color.WHITE),new Position(7, 4));
    	  }
}
