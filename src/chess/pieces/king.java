package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.color; 



    public class king extends  ChessPiece {
	
	private ChessMatch ChessMatch;

	public king (Board board, color color, ChessMatch chessmatch) {
		super (board, color); 
		this.ChessMatch = chessmatch;
	}

    @Override

	public String toString() {
		return "K"; 
	}
    
    private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Tower && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

    
    private boolean canMove(Position position) {
    	ChessPiece p = (ChessPiece)getBoard().piece(position);
    	return p == null || p.getColor() != getColor();
    }

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);

		// above
		p.setValues(Position.getRow() - 1, Position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValues(Position.getRow() + 1, Position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValues(Position.getRow(), Position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(Position.getRow(), Position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(Position.getRow() - 1, Position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValues(Position.getRow() - 1, Position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValues(Position.getRow() + 1, Position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// se
		p.setValues(Position.getRow() + 1, Position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		if (getMoveCount() == 0 && !ChessMatch.getCheck()) {
			// #specialmove castling kingside rook
			Position posT1 = new Position(Position.getRow(), Position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				Position p1 = new Position(Position.getRow(), Position.getColumn() + 1);
				Position p2 = new Position(Position.getRow(), Position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[Position.getRow()][Position.getColumn() + 2] = true;
				}
			}
			// #specialmove castling queenside rook
			Position posT2 = new Position(Position.getRow(), Position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position p1 = new Position(Position.getRow(), Position.getColumn() - 1);
				Position p2 = new Position(Position.getRow(), Position.getColumn() - 2);
				Position p3 = new Position(Position.getRow(), Position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[Position.getRow()][Position.getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}
}