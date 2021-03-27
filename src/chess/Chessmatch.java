package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Tower;
import chess.pieces.king;
import chess.ChessExeption;	



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
	public ChessPiece performChessmove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validatesourcePosition(source);
		Piece capturedPiece = makemove(source, target);
		return (ChessPiece)capturedPiece;
	}
	private Piece makemove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
		
	}
	
	private void validatesourcePosition(Position position) {
		if(!board.thereisAPiece(position)) {
			throw new ChessExeption("There is no piece on source position");
		}
		if (!board.piece(position).isThereAnyPossibleMoves()) {
				
			throw new ChessExeption ("There is no possible moves for the chosen piece");	
		}
		
	}
			
				
	private void PlaceNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
     private void initialsetup() {
    	 PlaceNewPiece('c', 1, new Tower(board, color.WHITE));
         PlaceNewPiece('c', 2, new Tower(board, color.WHITE));
         PlaceNewPiece('d', 2, new Tower(board, color.WHITE));
         PlaceNewPiece('e', 2, new Tower(board, color.WHITE));
         PlaceNewPiece('e', 1, new Tower(board, color.WHITE));
         PlaceNewPiece('d', 1, new king(board, color.WHITE));

         PlaceNewPiece('c', 7, new Tower(board, color.BLACK));
         PlaceNewPiece('c', 8, new Tower(board, color.BLACK));
         PlaceNewPiece('d', 7, new Tower(board, color.BLACK));
         PlaceNewPiece('e', 7, new Tower(board, color.BLACK));
         PlaceNewPiece('e', 8, new Tower(board, color.BLACK));
         PlaceNewPiece('d', 8, new king(board, color.BLACK));
 	}
}
