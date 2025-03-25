import ui.PreLogin;
import utils.ServerFacade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to chess, type help to get help");
        ServerFacade server = new ServerFacade();
        PreLogin ui = new PreLogin(server);
        ui.run();
        //loop here
        //print out "console" prefix
        //await user input
        //do action based on input
        //if action is quit, then quit
    }


}