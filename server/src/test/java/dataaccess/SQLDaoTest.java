package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLDaoTest {

    @BeforeEach
    void setUp() throws DataAccessException{
        DatabaseManager.createDatabase();
        SQLDao sqldb = new SQLDao();
        sqldb.clear();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.clear();
    }

    @Test
    void clear() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "pwd", "a");
        AuthData auth = sqldb.createAuth("test", "pwd", true);
        sqldb.clear();

        try(var conn = DatabaseManager.getConnection()){
            var statement = conn.prepareStatement("SELECT username, authToken FROM auth WHERE username=?");
            statement.setString(1, auth.username());
            var result = statement.executeQuery();
            assertFalse(result.next());
        } catch (SQLException e) {
            throw new DataAccessException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Test
    void createUserPositive() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        assertDoesNotThrow( () -> sqldb.createUser("test", "pwd", "a"));
    }
    @Test
    void createUserNegative() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        assertThrows(DataAccessException.class, () -> sqldb.createUser("test", "pwd", null));
    }
    @Test
    void getUserPositive() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "pwd", "a");

        UserData user = sqldb.getUser("test");
        assertNotNull(user);
    }

    @Test
    void getUserNegative() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "pwd", "a");

        UserData user = sqldb.getUser("asdf");
        assertNull(user);
    }

    @Test
    void createGamePositive() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        GameData game = sqldb.createGame(auth.authToken(), "game1");
        assertEquals(null, game.whiteUsername());
        assertEquals(null, game.blackUsername());
        assertEquals(1, game.gameID());
        assertEquals("game1", game.gameName());
    }
    @Test
    void createGameNegative() throws DataAccessException {
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        assertThrows(DataAccessException.class, () -> sqldb.createGame("auth.authToken()", "game1"));
    }
    @Test
    void listGamesPositive() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        GameData game = sqldb.createGame(auth.authToken(), "game1");
        List<GameData> games = sqldb.listGames(auth.authToken());
        assertEquals(game, games.get(0));
    }
    @Test
    void listGamesNegative() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        GameData game = sqldb.createGame(auth.authToken(), "game1");
        assertThrows(DataAccessException.class, () -> sqldb.listGames("asdf"));
    }
    @Test
    void updateGamePositive() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        GameData game = sqldb.createGame(auth.authToken(), "game1");
        assertDoesNotThrow( () -> sqldb.updateGame(auth.authToken(), auth.username(), "BLACK", 1));
    }
    @Test
    void updateGameNegative() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        GameData game = sqldb.createGame(auth.authToken(), "game1");
        assertThrows(DataAccessException.class,  () -> sqldb.updateGame(auth.authToken(), auth.username(), "GRAY", 1));
    }
    @Test
    void createAuthPositive() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        assertEquals(auth, sqldb.getAuth(auth.authToken()));
    }
    @Test
    void createAuthNegative() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        assertThrows(DataAccessException.class, () -> sqldb.createAuth("test", "asdf", true));
    }
    @Test
    void getAuthPositive() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        AuthData storedAuth = sqldb.getAuth(auth.authToken());
        assertEquals(auth, storedAuth);
    }
    @Test
    void getAuthNegative() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        assertThrows(DataAccessException.class, () -> sqldb.getAuth("false"));
    }
    @Test
    void deleteAuthPositive() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        sqldb.deleteAuth(auth.authToken());
        assertThrows(DataAccessException.class, () -> sqldb.getAuth(auth.authToken()));
    }
    @Test
    void deleteAuthNegative() throws DataAccessException{
        SQLDao sqldb = new SQLDao();
        sqldb.createUser("test", "password", "email");
        AuthData auth = sqldb.createAuth("test", "password", true);
        assertThrows(DataAccessException.class, () -> sqldb.deleteAuth("auth.authToken()"));
    }
}