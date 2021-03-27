package boardgame;

public abstract class Piece {

	protected Position Position;
	private Board Board;
	
	public Piece(Board board) {
		this.Board = board;
		Position = null;
		
		
	}

	protected Board getBoard() {
		return Board;
	}
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMoves() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}

	

