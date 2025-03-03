package server;

import dataaccess.DataAccessException;
import service.ServiceException;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.


        //register user
        Spark.post("/user", (request, response) -> {
            try{
                String body = UserHandler.RegisterHandler(request.body());
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        //login
        Spark.post("/session", (request, response) -> {
            try{
                String body = UserHandler.LoginHandler(request.body());
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
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
            return "{}";
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
