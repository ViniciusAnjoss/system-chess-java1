package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessExeption("erro de inicializa��o, os valores validos s�o de a1 a h8.");
		}
		this.column = column;
		this.row = row;
	}
	public char getColumn() {
		return column;
	}
	
	public int getRow() {
		return row;
	}
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');	
	}
	protected static ChessPosition formPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()),8 - position.getRow());
	}
	
	public String toString() {
		return "" + column + row;
		
	}
	

}
