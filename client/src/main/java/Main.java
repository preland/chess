import chess.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boolean quit = false;
        Scanner scan = new Scanner(System.in);
        //System.out.println("♕ 240 Chess Client: " + piece);
        System.out.println("Welcome to chess, type help to get help");

        while(!quit) {
            System.out.print(">>> ");
            switch (scan.nextLine()) {
                case "help":
                    break;
                default:
                    quit = true;
                    break;
            }
        }
        //loop here
        //print out "console" prefix
        //await user input
        //do action based on input
        //if action is quit, then quit
    }

}