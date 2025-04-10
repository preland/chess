package ui;

import client.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public class Gameplay {
    ServerFacade server;
    String auth = "";
    int id;
    String board;
    boolean color;
    public boolean run(ServerFacade server, String auth, int id, boolean color) {
        this.server = server;
        this.auth = auth;
        this.id = id;
        this.color = color;
        boolean leave = false;
        Scanner scan = new Scanner(System.in);
        while(!leave) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[0]) {
                case "redraw":
                    handleRedraw();
                    break;
                case "make_move":
                    handleMakeMove(input);
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
        return true;
    }

    private void handleMakeMove(String [] input) {
        if(input.length != 5 | input.length != 6) {
            handleHelp();
            return;
        }
        server.makeMove(input, id, auth);
    }

    private void handleRedraw() {
        print_board(board, color);
        //server.observe(id, auth, null);
    }

    private void handleMoves(String[] input) {
        if(input.length != 3) {
            handleHelp();
            return;
        }
        server.observe(id, auth, input);
    }

    private void handleResign() {
        System.out.println("Are you sure?(y/N)");
        Scanner scan = new Scanner(System.in);
        String[] input = scan.nextLine().split(" ");
        if(input[0].charAt(0) == 'y') {
            server.resign(id, auth);
        } else {
            System.out.println("Cancelled");
        }
    }

    static void handleHelp(){
        System.out.println("redraw - redraw board");
        System.out.println("make_move <start_col> <start_row> <end_col> <end_row> (promotion_piece) - make a move");
        System.out.println("resign - resign from game");
        System.out.println("moves <row> <col> - show moves at position");
        System.out.println("leave - leave game");
        System.out.println("help - show this info");
    }
    void print_board(String unformBoardWhite, boolean black) {
        //String initBoardWhite = "#a0cdefgh#8RNBQKBNR87PPPPPPPP76........65........54........43........32pppppppp21rnbqkbnr1#a0cdefgh#";
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            tmp.append(String.valueOf(8 - i));
            for (int j = 0; j < 8; j++) {
                tmp.append(unformBoardWhite.charAt(j+(i*8)));
            }
            tmp.append(String.valueOf(8 - i));
            if(i!=7) {
                tmp.append(String.valueOf(7 - i));
            }
        }
        String boardWhite = "#a0cdefgh#" + tmp + "#a0cdefgh#";
        String boardBlack = new StringBuilder(boardWhite).reverse().toString();
        String board = black ? boardBlack : boardWhite;
        boolean isWhite = false;
        StringBuilder outBoard = new StringBuilder();
        for (int i = 0; i < 100;i++) {
            if(isWhite) {
                outBoard.append(SET_BG_COLOR_DARK_GREEN);
                isWhite = false;
            } else {
                outBoard.append(SET_BG_COLOR_WHITE);
                isWhite = true;
            }
            switch(board.charAt(i)) {
                case '#':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append("   ");
                    break;
                case 'a':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '0':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ "b" + " ");
                    break;
                case 'c':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case 'd':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case 'e':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case 'f':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case 'g':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case 'h':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '1':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '2':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '3':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '4':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '5':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '6':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '7':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
                case '8':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ board.charAt(i) + " ");
                    break;
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
            if(i%10==9) {
                outBoard.append("\n");
                isWhite = !isWhite;
            }
        }
    }
}
