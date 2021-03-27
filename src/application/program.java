package application;

import java.util.Scanner;
import java.util.InputMismatchException;

import chess.ChessExeption;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Chessmatch;




public class program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Chessmatch chessMatch = new Chessmatch();

		while (true) {
			try {
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessmove(source, target);
			}
			catch (ChessExeption e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}

