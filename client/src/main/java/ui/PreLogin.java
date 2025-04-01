package ui;

import java.util.Objects;
import java.util.Scanner;

import utils.ServerFacade;

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
            if(loggedIn) {
                quit = ui.run(auth);
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
    void handleRegister(String[] input){
        if(input.length != 4) {
            handleHelp();
            return;
        }

        String username = input[1];
        String password = input[2];
        String email = input[3];
        System.out.println(this.server.register(username, password, email));
        //System.out.println("register text");
    }
    void handleHelp(){
        System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - create account");
        System.out.println("login <USERNAME> <PASSWORD> - login to account");
        System.out.println("quit - exits application");
        System.out.println("help - show this info");
    }
}
