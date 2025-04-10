package ui;

import client.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLogin {
    ServerFacade server;
    String auth = "";
    Gameplay ui = new Gameplay();
    public PostLogin() {
    }
    public boolean run(ServerFacade server, String auth) {
        this.server = server;
        this.auth = auth;
        boolean quit = false;
        boolean logout = false;
        Scanner scan = new Scanner(System.in);
        while(!quit && !logout) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[0]) {
                case "create":
                    handleCreate(input);
                    break;
                case "list":
                    handleList();
                    break;
                case "join":
                    handleJoin(input);
                    break;
                case "observe":
                    handleObserve(input);
                    break;
                case "logout":
                    if(handleLogout()) {
                        logout = true;
                    } else {
                        System.out.println("Failed to logout!");
                    }
                    break;
                case "help":
                    handleHelp();
                    break;
                case "quit":
                    quit = true;
                    //handleQuit();
                    break;
                default:
                    handleHelp();
                    break;
            }
        }
        return quit;
    }

    private void handleCreate(String[] input) {
        if(input.length != 2) {
            handleHelp();
            return;
        }
        String name = input[1];
        String id = this.server.createGame(name, auth);
        if(!Objects.equals(id, "")) {
            System.out.println("Successfully created game with id: " + id + "!");
        } else {
            System.out.println("Failed to create game!");
        }
    }

    private boolean handleLogout() {

        System.out.println(this.server.logout(auth));
        return true;
    }

    private void handleObserve(String[] input) {
        boolean secret = false;
        if(input.length == 3) {
            if (!Objects.equals(input[2], "secretUSEBLACK")) {
                handleHelp();
                return;
            } else {
                secret = true;
            }
        } else if(input.length != 2) {
            handleHelp();
            return;
        }
        int id = Integer.parseInt(input[1]);
        try {
            int realid = this.server.getID(id, auth);
        } catch (URISyntaxException e) {
            System.out.println("Failed to observe game!");
        } catch (IOException e) {
            System.out.println("Failed to observe game!");
        }
        //System.out.println(this.server.observe(id, auth));
        //for now just print out template
        String initBoardWhite = "#a0cdefgh#8RNBQKBNR87PPPPPPPP76........65........54........43........32pppppppp21rnbqkbnr1#a0cdefgh#";
        String initBoardBlack = "#hgfedc0a#1rnbkqbnr12pppppppp23........34........45........56........67PPPPPPPP78RNBKQBNR8#hgfedc0a#";
        String initBoard = secret ? initBoardBlack : initBoardWhite;
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
            switch(initBoard.charAt(i)) {
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
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
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
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case 'd':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case 'e':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case 'f':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case 'g':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case 'h':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '1':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '2':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '3':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '4':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '5':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '6':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '7':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
                    break;
                case '8':
                    outBoard.append(RESET_BG_COLOR);
                    outBoard.append(SET_BG_COLOR_DARK_GREY);
                    outBoard.append(SET_TEXT_COLOR_WHITE);
                    outBoard.append(" "+ initBoard.charAt(i) + " ");
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
        System.out.println(outBoard);
    }

    private void handleJoin(String[] input) {
        if(input.length != 3) {
            handleHelp();
            return;
        }
        int id = Integer.parseInt(input[1]);
        String teamColor = input[2];
        System.out.println(this.server.joinGame(id, teamColor, auth));
        if(Objects.equals(teamColor, "BLACK")) {
            input[2] = "secretUSEBLACK";
            handleObserve(input);
        } else {
            String[] newput = Arrays.copyOf(input, input.length-1);
            handleObserve(newput);
        }

        //open websocket connection

    }

    private void handleList() {
        System.out.println(this.server.listGames(auth));
    }

    static void handleHelp(){
        System.out.println("create <NAME> - create game");
        System.out.println("list - list games");
        System.out.println("join <ID> [WHITE|BLACK]- join a game");
        System.out.println("observe <ID> - view a game");
        System.out.println("logout - logout of account");
        System.out.println("quit - exits application");
        System.out.println("help - show this info");
    }
}
