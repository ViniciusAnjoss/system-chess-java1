package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Tower;
import chess.pieces.king;
import chess.ChessPiece;
	



public class Chessmatch {
	private int Turn;
	private color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public Chessmatch() {
		board = new Board(8, 8);
		Turn =1;
		currentPlayer = color.WHITE;
		initialsetup();
	}
	
	public int getTurn() {
		return Turn;
	}
	public color currentPlayer () {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undomove(source, target, capturedPiece);
			throw new ChessExeption("You can't put yourself in check");
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undomove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereisAPiece(position)) {
			throw new ChessExeption("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
			throw new ChessExeption("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMoves()) {
			throw new ChessExeption("There is no possible moves for the chosen piece");
		}
	}
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessExeption("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		Turn++;
		currentPlayer = (currentPlayer == color.WHITE) ? color.BLACK: color.WHITE;
	}
	
	private color opponent(color Color) {
		return (Color ==color.WHITE) ? color.BLACK : color.WHITE;	
	}

	private ChessPiece King(color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if(p instanceof king) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");

	}
	
	private boolean testCheck(color color) {
		Position kingPosition = King(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
			
	private void PlaceNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
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

