package utils;

import com.google.gson.Gson;
import model.GameData;
import service.ServiceException;
import service.requestresult.CreateGameResult;
import service.requestresult.ListGamesResult;
import service.requestresult.LoginResult;
import service.requestresult.LogoutResult;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ServerFacade {
    String url;
    public ServerFacade() {
        this.url = "http://localhost:8080";
    }

    public String login(String username, String password) {

        try {
            Map map = Map.of("username", username, "password", password);
            String body = new Gson().toJson(map);
            String req = request("POST", "/session", body, null);
            System.out.println("h");
            System.out.println(req);
            System.out.println("h");
            String auth = new Gson().<LoginResult>fromJson(req, LoginResult.class).authToken();
            System.out.println(auth);
            return auth;
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            return "";
        }
    }

    public String register(String username, String password, String email) {
        String ret;

        try {
            Map map = Map.of("username", username, "password", password, "email", email);
            String body = new Gson().toJson(map);
            request("POST", "/user", body, null);
            ret = "Successfully registered!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to register! ";// + e.toString(); //todo: remove debug info here
            return ret;
        }
        return ret;
    }
    String request(String type, String path, String body, String auth) throws URISyntaxException, IOException {
        System.setProperty("javax.net.debug", "all");
        try {
            System.out.println(body);
            HttpURLConnection http = (HttpURLConnection) new URI(url+path).toURL().openConnection();
            http.setRequestMethod(type);
            if(auth != null) {
                http.addRequestProperty("authorization", auth);
            }
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
                 return new String (tempBody.readAllBytes());
            }

        } catch (URISyntaxException | IOException | ServiceException e) {
            throw e;
        }
    }

    public String createGame(String name, String auth) {
        String ret;

        try {
            Map map = Map.of("gameName", name);
            String body = new Gson().toJson(map);
            String req = request("POST", "/game", body, auth);
            String id = String.valueOf(new Gson().<CreateGameResult>fromJson(req, CreateGameResult.class).gameID());
            ret = id;
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "";// + e.toString(); //todo: remove debug info here
            return ret;
        }
        return ret;
    }

    public String logout(String auth) {
        String ret;

        try {
            request("DELETE", "/session", null, auth);
            ret = "Successfully logged out!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to log out!";// + e.toString(); //todo: remove debug info here
            return ret;
        }
        return ret;
    }

    /*public String observe(int id, String auth) {
        return "";
    }*/

    public String joinGame(int id, String teamColor, String auth) {
        String ret;

        try {
            Map map = Map.of("playerColor", teamColor, "gameID", id);
            String body = new Gson().toJson(map);
            request("PUT", "/game", body, auth);
            ret = "Successfully joined game!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to join game!";
            return ret;
        }
        return ret;
    }

    public String listGames(String auth) {
        String ret;
        try {
            String req = request("GET", "/game", null, auth);
            List<GameData> games = new Gson().fromJson(req, ListGamesResult.class).games();
            for(GameData game : games) {
                if(game == null) {
                    continue;
                }
                String white, black;
                if(game.whiteUsername() == null) {
                    white = "Open!";
                } else {
                    white = game.whiteUsername();
                }
                if(game.blackUsername() == null) {
                    black = "Open!";
                } else {
                    black = game.blackUsername();
                }
                System.out.println("id: " + game.gameID() + ", name: " + game.gameName() + ", White: " + white + ", Black: " + black);
            }
            ret = "Successfully listed games!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to list games!";
            return ret;
        }
        return ret;
    }
}
