package ui;

import java.util.Scanner;

import utils.ServerFacade;

public class PreLogin {
    ServerFacade server;
    public PreLogin(ServerFacade server) {
        this.server = server;
    }
    public void run() {
        boolean quit = false;
        Scanner scan = new Scanner(System.in);
        while(!quit) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[0]) {
                case "login":
                    handleLogin(input);
                    break;
                case "register":
                    handleRegister(input);
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
    static void handleLogin(String[] input){
        if(input.length != 3) {
            handleHelp();
            return;
        }
        String username = input[1];
        String password = input[2];
        System.out.println("login text");
    }
    static void handleRegister(String[] input){
        if(input.length != 4) {
            handleHelp();
            return;
        }
        String username = input[1];
        String password = input[2];
        String email = input[3];
        System.out.println("register text");
    }
    static void handleHelp(){
        System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - create account");
        System.out.println("login <USERNAME> <PASSWORD> - login to account");
        System.out.println("quit - exits application");
        System.out.println("help - show this info");
    }
}
