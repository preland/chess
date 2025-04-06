package ui;

import client.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public class Gameplay {
    ServerFacade server;
    String auth = "";
    public boolean run(ServerFacade server, String auth) {
        this.server = server;
        this.auth = auth;
        boolean leave = false;
        Scanner scan = new Scanner(System.in);
        while(!leave) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[0]) {
                case "redraw":
                    handleRedraw(input);
                    break;
                case "make_move":
                    handleMakeMove();
                    break;
                case "resign":
                    handleResign();
                    break;
                case "moves":
                    handleMoves(input);
                    break;
                case "help":
                    handleHelp();
                    break;
                case "leave":
                    leave = true;
                    //handleQuit();
                    break;
                default:
                    handleHelp();
                    break;
            }
        }
    }
    static void handleHelp(){
        System.out.println("redraw - redraw board");
        System.out.println("make_move <start_col,start_row> <end_col,end_row> (promotion_piece) - make a move");
        System.out.println("resign - resign from game");
        System.out.println("moves <row,col> - show moves at position");
        System.out.println("leave - leave game");
        System.out.println("help - show this info");
    }
    void print_board(String board) {
        boolean isWhite = false;
        StringBuilder outBoard = new StringBuilder();
        for (int i = 0; i < 64;i++) {
            if(isWhite) {
                outBoard.append(SET_BG_COLOR_DARK_GREEN);
                isWhite = false;
            } else {
                outBoard.append(SET_BG_COLOR_WHITE);
                isWhite = true;
            }
            switch(board.charAt(i)) {
                case 'R':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_ROOK);
                    break;
                case 'N':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_KNIGHT);
                    break;
                case 'B':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_BISHOP);
                    break;
                case 'Q':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_QUEEN);
                    break;
                case 'K':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_KING);
                    break;
                case 'P':
                    outBoard.append(SET_TEXT_COLOR_BLACK);
                    outBoard.append(BLACK_PAWN);
                    break;
                case 'r':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_ROOK);
                    break;
                case 'n':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_KNIGHT);
                    break;
                case 'b':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_BISHOP);
                    break;
                case 'q':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_QUEEN);
                    break;
                case 'k':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_KING);
                    break;
                case 'p':
                    outBoard.append(SET_TEXT_COLOR_LIGHT_GREY);
                    outBoard.append(BLACK_PAWN);
                    break;
                default:
                    outBoard.append("   ");
                    break;
            }
            outBoard.append(RESET_BG_COLOR);
            outBoard.append(RESET_TEXT_COLOR);
            if(i%8==7) {
                outBoard.append("\n");
                isWhite = !isWhite;
            }
        }
        System.out.println(outBoard);
    }
}
