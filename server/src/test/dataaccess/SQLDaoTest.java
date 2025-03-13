package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
        AuthData auth = sqldb.createAuth("test", "pwd");
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
    }
    @Test
    void createUserNegative() throws DataAccessException {
    }
    @Test
    void getUserPositive() throws DataAccessException {
    }

    @Test
    void getUserNegative() throws DataAccessException {
    }

    @Test
    void createGamePositive() throws DataAccessException {
    }
    @Test
    void createGameNegative() throws DataAccessException {
    }
    @Test
    void listGamesPositive() {
    }
    @Test
    void listGamesNegative() {
    }
    @Test
    void updateGamePositive() {
    }
    @Test
    void updateGameNegative() {
    }
    @Test
    void createAuthPositive() {
    }
    @Test
    void createAuthNegative() {
    }
    @Test
    void getAuthPositive() {
    }
    @Test
    void getAuthNegative() {
    }
    @Test
    void deleteAuthPositive() {
    }
    @Test
    void deleteAuthNegative() {
    }
}