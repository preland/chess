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
        TestServerFacade setupFacade = new TestServerFacade("localhost", Integer.toString(port));
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
        Assertions.assertTrue(serverFacade.login("test1", "test"));
    }
    @Test
    public void loginNegative() {
        serverFacade.register("test2", "test", "test");
        Assertions.assertFalse(serverFacade.login("test2", "fake"));
    }
}
