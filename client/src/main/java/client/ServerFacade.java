package client;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.GameData;
import utils.CreateGameResult;
import utils.ListGamesResult;
import utils.LoginResult;
import utils.ServiceException;
import websocket.commands.UserGameCommand;

import javax.websocket.DeploymentException;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class ServerFacade {
    String url;
    WebsocketManager connect;
    public ServerFacade(int port) {
        this.url = "http://localhost:" + port;
    }

    public String login(String username, String password) {

        try {
            Map map = Map.of("username", username, "password", password);
            String body = new Gson().toJson(map);
            String req = request("POST", "/session", body, null);
            //System.out.println("h");
            //System.out.println(req);
            //System.out.println("h");
            String auth = new Gson().<LoginResult>fromJson(req, LoginResult.class).authToken();
            //System.out.println(auth);
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
            ret = "Failed to register! ";
            return ret;
        }
        return ret;
    }
    String request(String type, String path, String body, String auth) throws URISyntaxException, IOException {
        System.setProperty("javax.net.debug", "all");
        try {
            //System.out.println(body);
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
            ret = "";
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
            ret = "Failed to log out!";
            return ret;
        }
        return ret;
    }

    public String joinGame(int id, String teamColor, String auth) {
        String ret;

        try {
            int realid = getID(id, auth);
            Map map = Map.of("playerColor", teamColor, "gameID", realid);
            String body = new Gson().toJson(map);
            request("PUT", "/game", body, auth);
            ret = "Successfully joined game!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to join game!";
            return ret;
        }
        return ret;
    }
    public int getID(int id, String auth) throws URISyntaxException, IOException {
        //yeah this is verrrry good
        try {
            String req = request("GET", "/game", null, auth);
            List<GameData> games = new Gson().fromJson(req, ListGamesResult.class).games();
            int realid = games.get(id - 1).gameID();
            return realid;
        } catch (URISyntaxException | IOException | IndexOutOfBoundsException e) {
            throw e;
        }
    }
    public String listGames(String auth) {
        String ret;
        try {
            String req = request("GET", "/game", null, auth);
            List<GameData> games = new Gson().fromJson(req, ListGamesResult.class).games();
            for(int i = 0; i < games.size(); i++) {
                GameData game = games.get(i);
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
                System.out.println("id: " + (i+1) + ", name: " + game.gameName() + ", White: " + white + ", Black: " + black);
            }
            ret = "Successfully listed games!";
        } catch (URISyntaxException | IOException | ServiceException | NullPointerException e) {
            ret = "Failed to list games!";
            return ret;
        }
        return ret;
    }

    //websocket stuffs
    public void connect() {
        try {
            this.connect = new WebsocketManager(url);
        } catch (DeploymentException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void makeMove(String[] input, int id, String auth) {
        ChessMove move;
        switch(input[5]) {
            case "ROOK":
                move = new ChessMove(new ChessPosition(Integer.parseInt(input[1]),Integer.parseInt(input[2])), new ChessPosition(Integer.parseInt(input[3]), Integer.parseInt(input[4])), ChessPiece.PieceType.ROOK);
                break;
            case "KNIGHT":
                move = new ChessMove(new ChessPosition(Integer.parseInt(input[1]),Integer.parseInt(input[2])), new ChessPosition(Integer.parseInt(input[3]), Integer.parseInt(input[4])), ChessPiece.PieceType.KNIGHT);
                break;
            case "BISHOP":
                move = new ChessMove(new ChessPosition(Integer.parseInt(input[1]),Integer.parseInt(input[2])), new ChessPosition(Integer.parseInt(input[3]), Integer.parseInt(input[4])), ChessPiece.PieceType.BISHOP);
                break;
            case "QUEEN":
                move = new ChessMove(new ChessPosition(Integer.parseInt(input[1]),Integer.parseInt(input[2])), new ChessPosition(Integer.parseInt(input[3]), Integer.parseInt(input[4])), ChessPiece.PieceType.QUEEN);
                break;
            default:
                move = new ChessMove(new ChessPosition(Integer.parseInt(input[1]),Integer.parseInt(input[2])), new ChessPosition(Integer.parseInt(input[3]), Integer.parseInt(input[4])), null);
                break;
        }
        UserGameCommand cmd = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE, auth, id);
        cmd.setMove(move);
        String message = new Gson().toJson(cmd);
        try {
            connect.send(message);
        } catch (IOException e) {
            System.out.println("Failed to make move!");
        }
    }

    public void resign(int id, String auth) {
        UserGameCommand cmd = new UserGameCommand(UserGameCommand.CommandType.RESIGN, auth, id);
        String message = new Gson().toJson(cmd);
        try {
            connect.send(message);
        } catch (IOException e) {
            System.out.println("Failed to resign!");
        }
    }

    public void observe(int id, String auth, String[] input) {
        UserGameCommand cmd = new UserGameCommand(UserGameCommand.CommandType.CONNECT, auth, id);

        String message = new Gson().toJson(cmd);
        try {
            connect.send(message);
        } catch (IOException e) {
            System.out.println("Failed to resign!");
        }
    }
}
