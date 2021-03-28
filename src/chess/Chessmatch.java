package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.king;
import chess.pieces.Pawn;
import chess.pieces.Tower;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;


public class ChessMatch {

	private int turn;
	private color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
			undoMove(source, target, capturedPiece);
			throw new ChessExeption("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p =(ChessPiece)board.removePiece(source);
		p.increseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// #specialmove castling kingside rook
				if (p instanceof king && target.getColumn() == source.getColumn() + 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
					Position targetT = new Position(source.getRow(), source.getColumn() + 1);
					ChessPiece Tower = (ChessPiece)board.removePiece(sourceT);
					board.placePiece(Tower, targetT);
					Tower.increseMoveCount();
				}

				// #specialmove castling queenside rook
				if (p instanceof king && target.getColumn() == source.getColumn() - 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
					Position targetT = new Position(source.getRow(), source.getColumn() - 1);
					ChessPiece Tower = (ChessPiece)board.removePiece(sourceT);
					board.placePiece(Tower, targetT);
					Tower.increseMoveCount();
				}		
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p =(ChessPiece) board.removePiece(target);
		p.decreseMoveCount();
		board.placePiece(p, source);
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	
	
	// #specialmove castling kingside rook
			if (p instanceof king && target.getColumn() == source.getColumn() + 2) {
				Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
				Position targetT = new Position(source.getRow(), source.getColumn() + 1);
				ChessPiece rook = (ChessPiece)board.removePiece(targetT);
				board.placePiece(rook, sourceT);
				rook.decreseMoveCount();
			}

			// #specialmove castling queenside rook
			if (p instanceof king && target.getColumn() == source.getColumn() - 2) {
				Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
				Position targetT = new Position(source.getRow(), source.getColumn() - 1);
				ChessPiece Tower = (ChessPiece)board.removePiece(targetT);
				board.placePiece(Tower, sourceT);
				Tower.decreseMoveCount();
			}
}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereisAPiece(position)) {
			throw new ChessExeption("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
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
		turn++;
		currentPlayer = (currentPlayer == color.WHITE) ? color.BLACK : color.WHITE;
	}
	
	private color opponent(color color) {
		return (color == color.WHITE) ? color.BLACK : color.WHITE;
	}
	
	private ChessPiece king(color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof king) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	private boolean testCheck(color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
     
	private void initialSetup() {
        placeNewPiece('a', 1, new Tower(board, color.WHITE));
        placeNewPiece('b', 1, new Knight(board, color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, color.WHITE));
        placeNewPiece('d', 1, new Queen(board, color.WHITE));
        placeNewPiece('e', 1, new king(board, color.WHITE,this));
        placeNewPiece('f', 1, new Bishop(board, color.WHITE));
        placeNewPiece('g', 1, new Knight(board, color.WHITE));
        placeNewPiece('h', 1, new Tower(board, color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, color.WHITE));
        
        
        placeNewPiece('a', 8, new Tower(board, color.BLACK));
        placeNewPiece('b', 8, new Knight(board, color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, color.BLACK));
        placeNewPiece('d', 8, new Queen(board, color.BLACK));
        placeNewPiece('e', 8, new king(board, color.BLACK,this));
        placeNewPiece('f', 8, new Bishop(board, color.BLACK));
        placeNewPiece('g', 8, new Knight(board, color.BLACK));
        placeNewPiece('h', 8, new Tower(board, color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, color.BLACK));
	}
}
	
		
	
	


