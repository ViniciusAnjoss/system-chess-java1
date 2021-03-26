package boardgame;

public class Piece {

	protected Position Position;
	private Board Board;
	
	public Piece(Board board) {
		this.Board = board;
		Position = null;
		
		
	}

	protected Board getBoard() {
		return Board;
	}


	
}
