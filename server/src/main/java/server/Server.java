package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import service.CreateGameRequest;
import service.ServiceException;
import spark.*;

import static spark.Spark.before;
import static spark.Spark.options;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        /*options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            return "OK";
        });
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");  // Allow all origins for simplicity
            res.header("Access-Control-Allow-Headers", "Authorization, Content-Type");  // Allow specific headers
            res.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        });*/
        //register user
        Spark.post("/user", (request, response) -> {
            try{
                String body = UserHandler.RegisterHandler(request.body());
                response.status(200);
                System.out.println(body);
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
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                String body = UserHandler.LogoutHandler(request.headers("authorization"));
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        //list games
        Spark.get("/game", (request, response) -> {
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                String body = GameHandler.ListGameHandler(request.headers("authorization"));
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        //create game
        Spark.post("/game", (request, response) -> {
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                var serializer = new Gson();
                String auth = request.headers("authorization");
                JsonObject json = JsonParser.parseString(request.body()).getAsJsonObject();
                json.addProperty("authorization", auth);
                String req = serializer.toJson(json);
                String body = GameHandler.CreateGameHandler(req);
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        //join game
        Spark.put("/game", (request, response) -> {
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                var serializer = new Gson();
                String auth = request.headers("authorization");
                JsonObject json = JsonParser.parseString(request.body()).getAsJsonObject();
                json.addProperty("authorization", auth);
                String req = serializer.toJson(json);
                String body = GameHandler.JoinGameHandler(req);
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        //clear database
        Spark.delete("/db", (request, response) -> {
            response.status(200);
            GameHandler.ClearDatabaseHandler();
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
