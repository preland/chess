package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//this should be a singleton
public class MemoryDAO {
    private static MemoryDAO instance = null;


    //games
    List<GameData> games;
    //users
    List<UserData> users;
    //auth stuff
    List<AuthData> auths;

    private MemoryDAO() {
        games = new ArrayList<>();
        users = new ArrayList<>();
        auths = new ArrayList<>();
    }

    public static MemoryDAO getInstance() {
        if (instance == null) {
            instance = new MemoryDAO();
        }

        return instance;
    }

    public void clear() throws DataAccessException {
        games.clear();
        users.clear();
        auths.clear();
    }
    public void createUser(String username, String password, String email) throws DataAccessException{
        if(username == null || password == null || email == null){
            throw new DataAccessException("400", "{\"message\": \"Error: bad request\"}");
        }
        if(getUser(username) == null){
            users.add(new UserData(username, password, email));
        }
        else {
            throw new DataAccessException("403", "{\"message\": \"Error: already taken\"}");
        }
    }
    public UserData getUser(String username) {
        return users.stream().filter(e -> e.username().equals(username)).findFirst().orElse(null);
    }
    public GameData createGame(String authorization, String gameName) throws DataAccessException{
        AuthData verify = getAuth(authorization);
        GameData game = new GameData(games.size()+1, null, null, gameName, new ChessGame());
        games.add(game);
        return game;
    }
    public List<GameData> listGames(String authorization) throws DataAccessException{
        AuthData verify = getAuth(authorization);
        return games;
    }
    public void updateGame(String authorization, String username, String playerColor, int gameID) throws DataAccessException{
        String whiteUser = null;
        String blackUser = null;
        System.out.println(playerColor);
        if(playerColor == null){
            throw new DataAccessException("400", "{\"message\": \"Error: bad request\"}");
        }
        GameData old = games.stream().filter(e -> e.gameID()==gameID).findFirst().orElseThrow(
            () -> new DataAccessException("400", "{\"message\": \"Error: bad request\"}"));
        switch(playerColor) {
            case "WHITE":
                if(old.whiteUsername() != null){
                    throw new DataAccessException("403", "{\"message\": \"Error: forbidden\"}");
                }
                whiteUser = username;
                blackUser = old.blackUsername();
                break;
            case "BLACK":
                if(old.blackUsername() != null){
                    throw new DataAccessException("403", "{\"message\": \"Error: forbidden\"}");
                }
                blackUser = username;
                whiteUser = old.whiteUsername();
                break;
            default:
                throw new DataAccessException("400", "{\"message\": \"Error: bad request\"}");
        }
        String finalWhiteUser = whiteUser;
        String finalBlackUser = blackUser;
        games.replaceAll(e -> e.gameID() == gameID ? new GameData(gameID, finalWhiteUser, finalBlackUser, e.gameName(), e.game()) : e);

    }
    public AuthData createAuth(String username, String password) throws DataAccessException{
        //todo: this is probably wrong
        UserData user = users.stream().filter(e -> e.username().equals(username)).filter(e -> e.password().equals(password)).findFirst().orElseThrow(
            () -> new DataAccessException("401", "{\"message\": \"Error: forbidden\"}"));
        //at this point user should be proven to exist with given password
        String authToken = generateToken();
        auths.add(new AuthData(authToken, user.username()));
        return getAuth(authToken);
    }
    public AuthData getAuth(String authToken) throws DataAccessException{
        System.out.println("n: " + authToken);
        for(AuthData a : auths) {
            System.out.println(a.authToken());
        }
        //System.out.println("oop: "+ auth);
        return auths.stream().filter(e -> e.authToken().equals(authToken)).findFirst().orElseThrow(
            () -> new DataAccessException("401", "{\"message\": \"Error: unauthorized\"}"));
    }
    public void deleteAuth(String authorization) throws DataAccessException {
        //todo: throw exception sometimes?
        auths.removeIf(e -> e.authToken().equals(authorization));

    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}
