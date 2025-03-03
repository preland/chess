package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

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
        if (instance == null)
            instance = new MemoryDAO();

        return instance;
    }

    public void clear() throws DataAccessException {
        games.clear();
        users.clear();
        auths.clear();
    }
    public void createUser(String username, String password, String email) throws DataAccessException{
        users.add(new UserData(username, password, email));
    }
    public UserData getUser(String username) throws DataAccessException{
        return users.stream().filter(e -> e.username().equals(username)).findFirst().orElseThrow( () -> new DataAccessException("h"));
    }
    public void createGame() throws DataAccessException{
        //todo: check auth
    }
    public List<GameData> listGames(String authorization) throws DataAccessException{
        //todo: check auth
        return games;
    }
    public void updateGame() throws DataAccessException{
        //todo: this will be fun .-.
    }
    public AuthData createAuth(String username, String password) throws DataAccessException{
        //todo: this is probably wrong
        UserData user = users.stream().filter(e -> e.username().equals(username)).filter(e -> e.password().equals(password)).findFirst().orElseThrow( () -> new DataAccessException("h"));
        //at this point user should be proven to exist with given password
        String authToken = "replacemelater";
        auths.add(new AuthData(authToken, user.username()));
        return getAuth(authToken);
    }
    public AuthData getAuth(String authToken) throws DataAccessException{
        return auths.stream().filter(e -> e.authToken().equals(authToken)).findFirst().orElseThrow( () -> new DataAccessException("h"));
    }
    public void deleteAuth(String authorization) throws DataAccessException {
        //todo: throw exception sometimes?
        auths.removeIf(e -> e.authToken().equals(authorization));

    }

}
