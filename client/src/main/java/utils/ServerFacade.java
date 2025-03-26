package utils;

public class ServerFacade {
    String url;
    public ServerFacade() {
        this.url = "http://localhost:8080";
    }

    public boolean login(String username, String password) {
        return true;
    }

    public boolean register(String username, String password, String email) {
        return true;
    }
}
