package ui;

import utils.ServerFacade;

import java.util.Scanner;

public class PostLogin {
    public PostLogin(ServerFacade server) {

    }
    public void run() {
        boolean quit = false;
        Scanner scan = new Scanner(System.in);
        while(!quit) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[1]) {
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
                    handleLogout();
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
    }

    private void handleCreate(String[] input) {
    }

    private void handleLogout() {
    }

    private void handleObserve(String[] input) {
        if(input.length != 2) {
            handleHelp();
            return;
        }
        int id = Integer.parseInt(input[1]);
    }

    private void handleJoin(String[] input) {
        if(input.length != 3) {
            handleHelp();
            return;
        }
        int id = Integer.parseInt(input[1]);
        String teamColor = input[2];

    }

    private void handleList() {
    }

    static void handleLogin(){
        System.out.println("login text");
    }
    static void handleRegister(){
        System.out.println("register text");
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
