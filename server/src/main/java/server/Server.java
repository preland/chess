package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //register user
        Spark.post("/user", (request, response) -> {
            response.status(200);
            return "hello";
        });
        //login
        Spark.post("/session", (request, response) -> {
            response.status(200);
            return "jello";
        });
        //logout
        Spark.delete("/session", (request, response) -> {
            response.status(200);
            return "bello";
        });
        //list games
        Spark.get("/game", (request, response) -> {
            response.status(200);
            return "yello";
        });
        //create game
        Spark.post("/game", (request, response) -> {
            response.status(200);
            return "mello";
        });
        //join game
        Spark.put("/game", (request, response) -> {
            response.status(200);
            return "rello";
        });
        //clear database
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
