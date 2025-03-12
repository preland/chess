package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLDao {
    private final String[] gameStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private final String[] userStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private final String[] authStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    public SQLDao() throws DataAccessException {
        try {
            DatabaseManager.createDatabase();
            try (var conn = DatabaseManager.getConnection()) {
                for (var statement : gameStatements) {
                    try (var preparedStatement = conn.prepareStatement(statement)) {
                        preparedStatement.executeUpdate();
                    }
                }
                for (var statement : userStatements) {
                    try (var preparedStatement = conn.prepareStatement(statement)) {
                        preparedStatement.executeUpdate();
                    }
                }
                for (var statement : authStatements) {
                    try (var preparedStatement = conn.prepareStatement(statement)) {
                        preparedStatement.executeUpdate();
                    }
                }
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new DataAccessException("500", e.getMessage());
        }
    }
    static void writeHashedPasswordToDatabase(String username, String hashedPassword) throws DataAccessException {
        throw new DataAccessException("0", "0");
    }
    static String readHashedPasswordFromDatabase(String username) throws DataAccessException {
        throw new DataAccessException("0", "0");
    }
    public void clear() throws DataAccessException {
        //games.clear();
        //users.clear();
        //auths.clear();
        try(var conn = DatabaseManager.getConnection()){
            conn.prepareStatement("TRUNCATE game").executeUpdate();
            conn.prepareStatement("TRUNCATE user").executeUpdate();
            conn.prepareStatement("TRUNCATE auth").executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
    public void createUser(String username, String password, String email) throws DataAccessException{
        if(username == null || password == null || email == null){
            throw new DataAccessException("400", "{\"message\": \"Error: bad request\"}");
        }
        if(getUser(username) == null){
            //users.add(new UserData(username, password, email));
            try(var conn = DatabaseManager.getConnection()){
                var statement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?,)");
                statement.setString(1, username);
                statement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
                statement.setString(3, email);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
            }
        }
        else {
            throw new DataAccessException("403", "{\"message\": \"Error: already taken\"}");
        }
    }
    public UserData getUser(String username) throws DataAccessException {
        //return users.stream().filter(e -> e.username().equals(username)).findFirst().orElse(null);
        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?");
            statement.setString(1, username);
            var result = statement.executeQuery();
            result.next();
            String retUsername = result.getString("username");
            String password = result.getString("password");
            String email = result.getString("email");
            return new UserData(retUsername, password, email);

        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
    public void createGame(String authorization, String gameName) throws DataAccessException{
        AuthData verify = getAuth(authorization);
        //GameData game = new GameData(games.size()+1, null, null, gameName, new ChessGame());
        //games.add(game);
        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?,)");
            statement.setString(1, null);
            statement.setString(2, null);
            statement.setString(3, gameName);
            statement.setString(4, new Gson().toJson(new ChessGame()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
    public List<GameData> listGames(String authorization) throws DataAccessException{
        AuthData verify = getAuth(authorization);
        List<GameData> games = new ArrayList<>();
        try(var conn = DatabaseManager.getConnection()) {
            var statement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game");
            var result = statement.executeQuery();
            while(result.next()){
                int gameID = result.getInt("gameID");
                String whiteUsername = result.getString("whiteUsername");
                String blackUsername = result.getString("blackUsername");
                String gameName = result.getString("gameName");
                ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
                games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
            }
            return games;
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
    public void updateGame(String authorization, String username, String playerColor, int gameID) throws DataAccessException{
        /*String whiteUser = null;
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
        games.replaceAll(e -> e.gameID() == gameID ? new GameData(gameID, finalWhiteUser, finalBlackUser, e.gameName(), e.game()) : e);*/
        GameData old;
        try(var conn = DatabaseManager.getConnection()) {
            var statement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?");
            statement.setString(1, String.valueOf(gameID));
            var result = statement.executeQuery();
            result.next();
            int retGameID = result.getInt("gameID");
            String whiteUsername = result.getString("whiteUsername");
            String blackUsername = result.getString("blackUsername");
            String gameName = result.getString("gameName");
            ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
            old = new GameData(retGameID, whiteUsername, blackUsername, gameName, game);
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
        try(var conn = DatabaseManager.getConnection()) {
            var statement = conn.prepareStatement("UPDATE game SET whiteUsername=?, blackUsername=? WHERE gameID=?");
            statement.setString(1, String.valueOf(old.gameID()));
            var result = statement.executeQuery();
            result.next();
            switch(playerColor) {
                case "WHITE":
                    if(old.whiteUsername() != null){
                        throw new DataAccessException("403", "{\"message\": \"Error: forbidden\"}");
                    }
                    statement.setString(1, username);
                    statement.setString(2, String.valueOf(old.gameID()));
                    break;
                case "BLACK":
                    if(old.blackUsername() != null){
                        throw new DataAccessException("403", "{\"message\": \"Error: forbidden\"}");
                    }
                    statement.setString(1, String.valueOf(old.gameID()));
                    statement.setString(2, username);
                    break;
                default:
                    throw new DataAccessException("400", "{\"message\": \"Error: bad request\"}");
            }


        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }

    }
    public AuthData createAuth(String username, String password) throws DataAccessException{
        //todo: this is probably wrong
        //UserData user = users.stream().filter(e -> e.username().equals(username)).filter(e -> e.password().equals(password)).findFirst().orElseThrow(
        //        () -> new DataAccessException("401", "{\"message\": \"Error: forbidden\"}"));
        UserData user = getUser(username);
        //userdata password is hashed at this point
        if(!BCrypt.checkpw(password, user.password())) {
            throw new DataAccessException("401", "{\"message\": \"Error: forbidden\"}");
        }
        //at this point user should be proven to exist with given password

        String authToken = generateToken();
        //auths.add(new AuthData(authToken, user.username()));
        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("INSERT INTO auth (username, authToken) VALUES(?, ?)");
            statement.setString(1, username);
            statement.setString(2, authToken);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
        return getAuth(authToken);
    }
    public AuthData getAuth(String authToken) throws DataAccessException{
        System.out.println("n: " + authToken);
        //for(AuthData a : auths) {
        //    System.out.println(a.authToken());
        //}
        //System.out.println("oop: "+ auth);
        //return auths.stream().filter(e -> e.authToken().equals(authToken)).findFirst().orElseThrow(
        //        () -> new DataAccessException("401", "{\"message\": \"Error: unauthorized\"}"));
        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("SELECT username, authToken FROM auth WHERE authToken=?");
            statement.setString(1, authToken);
            var result = statement.executeQuery();
            result.next();
            String retAuthToken = result.getString("authToken");
            String username = result.getString("username");
            return new AuthData(retAuthToken, username);
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
    public void deleteAuth(String authorization) throws DataAccessException {
        //todo: throw exception sometimes?
        //if(!auths.removeIf(e -> e.authToken().equals(authorization))) {
        //    throw new DataAccessException("401", "{\"message\": \"Error: unauthorized\"}");
        //}
        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("DELETE FROM auth WHERE authToken=?");
                statement.setString(1, authorization);
                statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }

    }
    static String generateToken() {
        return UUID.randomUUID().toString();
    }
    static void storeUserPassword(String username, String clearTextPassword) throws DataAccessException{
        String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());

        // write the hashed password in database along with the user's other information
        try{writeHashedPasswordToDatabase(username, hashedPassword);} catch (DataAccessException e) {
            throw e;
        }
    }
    boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException {
        // read the previously hashed password from the database
        var hashedPassword = readHashedPasswordFromDatabase(username);

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }
}
