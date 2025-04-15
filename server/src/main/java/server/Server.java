package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import org.eclipse.jetty.websocket.api.Session;
import service.ServiceException;
import spark.*;

import java.util.HashMap;

import static spark.Spark.before;
import static spark.Spark.options;

public class Server {

    static HashMap<Session, Integer> sessions = new HashMap();

    public int run(int desiredPort) {
        try { DatabaseManager.createDatabase(); } catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", WebsocketHandler.class);

        Spark.post("/user", (request, response) -> {
            try{
                String body = UserHandler.registerHandler(request.body());
                response.status(200);
                System.out.println(body);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        Spark.post("/session", (request, response) -> {
            try{
                String body = UserHandler.loginHandler(request.body());
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        Spark.delete("/session", (request, response) -> {
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                String body = UserHandler.logoutHandler(request.headers("authorization"));
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        Spark.get("/game", (request, response) -> {
            request.headers().forEach(header -> {
                System.out.println(header + ": " + request.headers(header));
            });
            try{
                String body = GameHandler.listGameHandler(request.headers("authorization"));
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
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
                String body = GameHandler.createGameHandler(req);
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
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
                String body = GameHandler.joinGameHandler(req);
                response.status(200);
                return body;
            } catch (ServiceException e) {
                response.status(Integer.parseInt(e.code));
                return e.body;
            }
        });
        Spark.delete("/db", (request, response) -> {
            response.status(200);
            GameHandler.clearDatabaseHandler();
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
