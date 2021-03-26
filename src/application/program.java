package application;

import chess.Chessmatch;


public class program {

	public static void main(String[] args) {
		Chessmatch chessmatch = new Chessmatch();
		UI.printBoard(chessmatch.getPieces());
		

	}

}
