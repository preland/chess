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
                    handleObserve(input, true);
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

    private void handleObserve(String[] input, boolean isObserve) {
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
        int realid;
        try {
            realid = this.server.getID(id, auth);
        } catch (URISyntaxException | IOException e) {
            System.out.println("Failed to observe game!");
            return;
        }
        server.connect();
        server.observe(realid, auth, input, isObserve);
        ui.run(server, auth, id, secret); // player white == false
        //System.out.println(this.server.observe(id, auth));
        //for now just print out template
        //Gameplay.printBoard(board, secret);
        //System.out.println(outBoard);
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
            handleObserve(input, false);
        } else {
            String[] newput = Arrays.copyOf(input, input.length-1);
            handleObserve(newput, false);
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
