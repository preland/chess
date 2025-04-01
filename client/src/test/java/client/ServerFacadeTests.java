package client;

import org.junit.jupiter.api.*;
import passoff.server.TestServerFacade;
import server.Server;
import utils.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade();

    }

    @BeforeEach
    void clearServer(){
        TestServerFacade setupFacade = new TestServerFacade("localhost", Integer.toString(8080));
        setupFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerPositive() {
        Assertions.assertEquals("Successfully registered!", serverFacade.register("test", "test", "test"));
    }
    @Test
    public void registerNegative() {
        Assertions.assertNotEquals("Successfully registered!", serverFacade.register(null, "test", "test"));
    }
    @Test
    public void loginPositive() {
        serverFacade.register("test1", "test", "test");
        Assertions.assertNotEquals("", serverFacade.login("test1", "test"));
    }
    @Test
    public void loginNegative() {
        serverFacade.register("test2", "test", "test");
        Assertions.assertEquals("", serverFacade.login("test2", "fake"));
    }
    @Test
    public void createGamePositive() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        Assertions.assertNotEquals("", serverFacade.createGame("name", auth));
    }
    @Test
    public void createGameNegative() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        Assertions.assertEquals("", serverFacade.createGame("name", "fake"));
    }
    @Test
    public void logoutPositive() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        Assertions.assertEquals("Successfully logged out!", serverFacade.logout(auth));
    }
    @Test
    public void logoutNegative() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        Assertions.assertEquals("Failed to log out!", serverFacade.logout("fake"));

    }
    @Test
    public void joinGamePositive() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        serverFacade.createGame("name", auth);
        Assertions.assertEquals("Successfully joined game!", serverFacade.joinGame(1, "WHITE", auth));

    }
    @Test
    public void joinGameNegative() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        serverFacade.createGame("name", auth);
        Assertions.assertEquals("Failed to join game!", serverFacade.joinGame(1, "WHITE", "fake"));
    }
    @Test
    public void listGamesPositive() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        serverFacade.createGame("name", auth);
        Assertions.assertEquals("Successfully listed games!", serverFacade.listGames(auth));
    }
    @Test
    public void listGamesNegative() {
        serverFacade.register("test1", "test", "test");
        String auth = serverFacade.login("test1", "test");
        serverFacade.createGame("name", auth);
        Assertions.assertEquals("Failed to list games!", serverFacade.listGames("fake"));
    }
}
