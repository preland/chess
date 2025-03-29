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

        try {
            Map map = Map.of("username", username, "password", password);
            String body = new Gson().toJson(map);
            request("POST", "/session", body);
            return true;
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            return false;
        }
    }

    public String register(String username, String password, String email) {
        String ret;

        try {
            Map map = Map.of("username", username, "password", password, "email", email);
            String body = new Gson().toJson(map);
            request("POST", "/user", body);
            ret = "Successfully registered!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to register: " + e.toString(); //todo: remove debug info here
            return ret;
        }
        return ret;
    }
    String request(String type, String path, String body) throws URISyntaxException, IOException {
        System.setProperty("javax.net.debug", "all");
        try {
            System.out.println(body);
            HttpURLConnection http = (HttpURLConnection) new URI(url+path).toURL().openConnection();
            http.setRequestMethod(type);
            if(body != null) {
                http.setDoOutput(true);
                http.addRequestProperty("Content-Type", "application/json");
                var stream = http.getOutputStream();
                stream.write(body.getBytes());
                stream.flush();
                stream.close();
            }
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
                 return input.toString();
            }

        } catch (URISyntaxException | IOException | ServiceException e) {
            throw e;
        }
    }
}
