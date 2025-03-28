package client;

import org.junit.jupiter.api.*;
import server.Server;
import utils.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
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

}
