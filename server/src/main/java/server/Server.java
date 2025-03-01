package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.post("/user", (request, response) -> {
            response.status(200);
            return "hello";
        });
        Spark.post("/session", (request, response) -> {
            response.status(200);
            return "jello";
        });
        Spark.delete("/session", (request, response) -> {
            response.status(200);
            return "bello";
        });
        Spark.get("/game", (request, response) -> {
            response.status(200);
            return "yello";
        });
        Spark.post("/game", (request, response) -> {
            response.status(200);
            return "mello";
        });
        Spark.put("/game", (request, response) -> {
            response.status(200);
            return "rello";
        });
        Spark.delete("/db", (request, response) -> {
            response.status(200);
            return "tello";
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
