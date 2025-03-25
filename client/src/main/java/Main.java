import chess.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boolean quit = false;
        Scanner scan = new Scanner(System.in);
        //System.out.println("♕ 240 Chess Client: " + piece);
        System.out.println("Welcome to chess, type help to get help");

        while(!quit) {
            System.out.print(">>> ");
            switch (scan.nextLine()) {
                case "login":
                    break;
                case "logout":
                    break;
                case "new_game":
                    break;
                case "join_game":
                    break;
                case "help":
                    break;
                case "redraw":
                    break;
                case "leave":
                    break;
                case "quit":
                    break;
                case "move":
                    break;
                case "show_moves":
                    break;
                default:
                    //help stuff here
                    break;
            }
        }
        //loop here
        //print out "console" prefix
        //await user input
        //do action based on input
        //if action is quit, then quit
    }
    void handleLogin(){
        System.out.println("help text");
    }
    void handleLogout(){
        System.out.println("help text");
    }
    void handleNewGame(){
        System.out.println("help text");
    }
    void handleJoinGame(){
        System.out.println("help text");
    }
    void handleHelp(){
        System.out.println("help text");
    }
    void handleRedraw(){
        System.out.println("help text");
    }
    void handleLeave(){
        System.out.println("help text");
    }
    void handleQuit(){
        System.out.println("help text");
    }
    void handleMove(){
        System.out.println("help text");
    }
    void handleShowMoves(){
        System.out.println("help text");
    }

}