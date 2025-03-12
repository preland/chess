package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) {
                    throw new Exception("Unable to load db.properties");
                }
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
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
    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof ChessGame p) ps.setString(i + 1, p.toString());
                    //todo: add more like above
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
    static void writeHashedPasswordToDatabase(String username, String hashedPassword) throws DataAccessException {
        throw new DataAccessException("0", "0");
    }
    static String readHashedPasswordFromDatabase(String username) throws DataAccessException {
        throw new DataAccessException("0", "0");
    }
    public void addEntry(String category, String json) throws DataAccessException {
        var statement = "INSERT INTO " + category + " (name, type, json) VALUES (?, ?, ?)";
        var id = executeUpdate(statement, /*todo: add things here*/ json);
    }
    public void clear() throws DataAccessException {
        //games.clear();
        //users.clear();
        //auths.clear();
        String statement = "TRUNCATE game";
        executeUpdate(statement);
        statement = "TRUNCATE user";
        executeUpdate(statement);
        statement = "TRUNCATE auth";
        executeUpdate(statement);
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
        if(!auths.removeIf(e -> e.authToken().equals(authorization))) {
            throw new DataAccessException("401", "{\"message\": \"Error: unauthorized\"}");
        }

    }
    static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
