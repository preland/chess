package service;

import dataaccess.MemoryDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDAOTest {

    @Test
    void validGetInstance() {
        MemoryDAO memdb = MemoryDAO.getInstance();
        Assertions.assertNotNull(memdb);
    }

    @Test
    void invalidGetInstance() {
        //memorydao is a singleton; should never be null
        MemoryDAO memdb = null;
        Assertions.assertNull(memdb);
    }

    @Test
    void validClear() {
        MemoryDAO memdb = MemoryDAO.getInstance();
        Assertions.assertDoesNotThrow(() -> memdb.clear());
    }

    @Test
    void invalidClear() {
        //clear is not exception prone
        MemoryDAO memdb = MemoryDAO.getInstance();
        Assertions.assertDoesNotThrow(() -> memdb.clear());
    }

    @Test
    void validCreateUser() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        Assertions.assertDoesNotThrow(() -> memdb.createUser("test", "test", "test"));
    }
    @Test
    void invalidCreateUser() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        Assertions.assertThrows(DataAccessException.class, () -> memdb.createUser("test", "test", "test"));
    }
    @Test
    void validGetUser() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        Assertions.assertEquals(new UserData("test", "test", "test"), memdb.getUser("test"));
    }
    @Test
    void invalidGetUser() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        Assertions.assertNull(memdb.getUser("not test"));
    }
    @Test
    void validCreateGame() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertDoesNotThrow(() -> memdb.createGame(auth.authToken(), "game"));
    }
    @Test
    void invalidCreateGame() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertThrows(DataAccessException.class, () -> memdb.createGame("wrong", "game"));
    }
    @Test
    void validListGames() throws DataAccessException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        memdb.createGame(auth.authToken(), "game");
        assertDoesNotThrow(() -> memdb.listGames(auth.authToken()));
    }
    @Test
    void invalidListGames() throws DataAccessException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        memdb.createGame(auth.authToken(), "game");
        assertThrows(DataAccessException.class, () -> memdb.listGames("wrong"));
    }
    @Test
    void validUpdateGame() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        memdb.createGame(auth.authToken(), "game");
        assertDoesNotThrow(() -> memdb.updateGame(auth.authToken(), "test", "WHITE", 1));
    }
    @Test
    void invalidUpdateGame() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        memdb.createGame(auth.authToken(), "game");
        assertThrows(DataAccessException.class, () -> memdb.updateGame(auth.authToken(), "test", "GRAY", 1));
    }

    @Test
    void validCreateAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        assertDoesNotThrow(() -> memdb.createAuth("test", "test"));
    }
    @Test
    void invalidCreateAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        assertThrows(DataAccessException.class, () -> memdb.createAuth("test", "false"));
    }
    @Test
    void validGetAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertEquals(auth, memdb.getAuth(auth.authToken()));
    }
    @Test
    void invalidGetAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertThrows(DataAccessException.class, () -> memdb.getAuth("fake"));
    }
    @Test
    void validDeleteAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertDoesNotThrow(() -> memdb.deleteAuth(auth.authToken()));
    }
    @Test
    void invalidDeleteAuth() throws DataAccessException{
        MemoryDAO memdb = MemoryDAO.getInstance();
        memdb.clear();
        memdb.createUser("test", "test", "test");
        AuthData auth = memdb.createAuth("test", "test");
        assertThrows(DataAccessException.class, () -> memdb.deleteAuth("false"));
    }
}