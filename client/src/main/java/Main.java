import ui.PreLogin;
import client.ServerFacade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to chess, type help to get help");
        ServerFacade server = new ServerFacade(8080);
        PreLogin ui = new PreLogin(server);
        ui.run();
        //loop here
        //print out "console" prefix
        //await user input
        //do action based on input
        //if action is quit, then quit
    }


}