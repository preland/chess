package ui;

import java.util.Objects;
import java.util.Scanner;

import client.ServerFacade;

public class PreLogin {
    ServerFacade server;
    boolean loggedIn = false;
    PostLogin ui;
    public PreLogin(ServerFacade server) {
        this.server = server;
        this.ui = new PostLogin();
    }
    public void run() {
        boolean quit = false;
        Scanner scan = new Scanner(System.in);
        String auth = "";
        while(!quit) {
            System.out.print(">>> ");
            String[] input = scan.nextLine().split(" ");
            switch (input[0]) {
                case "login":
                    auth = handleLogin(input);
                    break;
                case "register":
                    auth = handleRegister(input);
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
            if(loggedIn) {
                quit = ui.run(server, auth);
                loggedIn = false;
            }
        }
    }
    String handleLogin(String[] input){
        if(input.length != 3) {
            handleHelp();
            return "";
        }
        String username = input[1];
        String password = input[2];
        String auth = this.server.login(username, password);
        if(!Objects.equals(auth, "")) {
            System.out.println("Successfully logged in!");
            this.loggedIn = true;
        } else {
            System.out.println("Failed to login!");
        }
        //System.out.println("login text");
        return auth;
    }
    String handleRegister(String[] input){
        String auth = "";
        if(input.length != 4) {
            handleHelp();
            return auth;
        }

        String username = input[1];
        String password = input[2];
        String email = input[3];
        if(Objects.equals(this.server.register(username, password, email), "Successfully registered!")){
            System.out.println("Successfully registered!");
            auth = this.server.login(username, password);
            if(!Objects.equals(auth, "")) {
                System.out.println("Successfully logged in!");
                this.loggedIn = true;
            } else {
                System.out.println("Failed to login!");
            }
        }
        else {
            System.out.println("Failed to register!");
        }
        return auth;
        //System.out.println("register text");
    }
    void handleHelp(){
        System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - create account");
        System.out.println("login <USERNAME> <PASSWORD> - login to account");
        System.out.println("quit - exits application");
        System.out.println("help - show this info");
    }
}
