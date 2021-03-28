	package chess.pieces;

	import boardgame.Board;
	import boardgame.Position;
	import chess.ChessPiece;
	import chess.color;

	public class Pawn extends ChessPiece {

		public Pawn(Board board, color color) {
			super(board, color);
		}

		@Override
		public boolean[][] possibleMoves() {
			boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

			Position p = new Position(0, 0);

			if (getColor() == color.WHITE) {
				p.setValues(Position.getRow() - 1, Position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereisAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(Position.getRow() - 2, Position.getColumn());
				Position p2 = new Position(Position.getRow() - 1, Position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereisAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereisAPiece(p2) && getMoveCount() == 0) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(Position.getRow() - 1, Position.getColumn() - 1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}			
				p.setValues(Position.getRow() - 1, Position.getColumn() + 1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}			
			}
			else {
				p.setValues(Position.getRow() + 1, Position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereisAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(Position.getRow() + 2, Position.getColumn());
				Position p2 = new Position(Position.getRow() + 1, Position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereisAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereisAPiece(p2) && getMoveCount() == 0) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(Position.getRow() + 1, Position.getColumn() - 1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}			
				p.setValues(Position.getRow() + 1, Position.getColumn() + 1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}	
			}
			return mat;
		}

		@Override
		public String toString() {
			return "P";
		}

	}