package utils;

import com.google.gson.Gson;
import service.ServiceException;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ServerFacade {
    String url;
    public ServerFacade() {
        this.url = "http://localhost:8080";
    }

    public boolean login(String username, String password) {
        return true;
    }

    public String register(String username, String password, String email) {
        String ret;
        Map map = Map.of("username", username, "password", password, "email", email);
        String body = new Gson().toJson(map);
        try {
            request("POST", "/user", body);
            ret = "Successfully registered!";
        } catch (URISyntaxException | IOException | ServiceException e) {
            ret = "Failed to register: " + e.toString();
            return ret;
        }
        return ret;
    }
    public String request(String type, String path, String body) throws URISyntaxException, IOException {
        try {
            HttpURLConnection http = (HttpURLConnection) new URI(url+path).toURL().openConnection();
            http.setRequestMethod(type);
            http.getOutputStream().write(body.getBytes());
            http.connect();
            try {
                if (http.getResponseCode() == 401) {
                    throw new ServiceException("401", "Unauthorized");
                }
            } catch (IOException e) {
                throw e;
            }
            try (InputStream tempBody = http.getInputStream()) {
                InputStreamReader input = new InputStreamReader(tempBody);
                 return new Gson().fromJson(input, String.class);
            }

        } catch (URISyntaxException | IOException | ServiceException e) {
            throw e;
        }
    }
}
