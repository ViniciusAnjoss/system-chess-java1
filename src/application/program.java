package application;
import java.util.InputMismatchException;
import java.util.Scanner;
import chess.ChessExeption;
import chess.Chessmatch;
import chess.ChessPiece;
import chess.ChessPosition;
public class program {
	
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Chessmatch chessMatch = new Chessmatch();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
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

