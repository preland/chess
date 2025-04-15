package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLDao;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import spark.Spark;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebsocketHandler {
    /*public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/ws", WebsocketHandler.class);
        Spark.get("/connect", (req, res) -> "HTTP response: " + req.params(":msg"));
    }*/

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println("i twas here");
        Server.sessions.put(session, 0);
    }
    @OnWebSocketClose
    public void onClose(Session session, int num, String str) {
        Server.sessions.remove(session);
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch(cmd.getCommandType()) {
            case CONNECT:
                handleConnect(session, message);
                break;
            case LEAVE:
                handleLeave(session, message);
                break;
            case MAKE_MOVE:
                handleMove(session, message);
                break;
            case RESIGN:
                handleResign(session, message);
                break;
            default:
                break;
        }
            System.out.println(message);
    }

    private void handleResign(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
    }

    private void handleMove(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
    }

    private void handleLeave(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
    }

    private void handleConnect(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        Server.sessions.replace(session, cmd.getGameID());
        String printout;
        try {
            AuthData auth = SQLDao.getAuth(cmd.getAuthToken());
            GameData game = GameService.getGame(auth.authToken(), 0);
            if(cmd.isObserve()){
                ChessGame.TeamColor color;
                switch(cmd.getTeamColor()) {
                    case "WHITE":
                        color = ChessGame.TeamColor.WHITE;
                        break;
                    case "BLACK":
                        color = ChessGame.TeamColor.BLACK;
                        break;
                    default:
                        throw new DataAccessException("400", "invalid color");
                }
                printout = "player has joined game as " + cmd.getTeamColor().toLowerCase() + "!";
            } else {
                printout = "player has joined game as an observer!";
            }
            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(printout);
            sendNotificationOthers(session, notification);

            ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            msg.setGame(game.game());
            sendMessage(session, msg);
        } catch (DataAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendNotificationOthers(Session session, ServerMessage notification) throws IOException {
        sendNotification(session, notification, false);
    }

    //currently just sends a notif if the game is the same
    private void sendNotification(Session session, ServerMessage message, boolean toSelf) throws IOException {

        for(Session s : Server.sessions.keySet()) {
            if(Server.sessions.get(s) == 0) {
                continue;
            }
            if(!toSelf && s == session) {
                continue;
            }
            if(Server.sessions.get(s).equals(Server.sessions.get(session))) {
                this.sendMessage(session, message);
            }
        }
    }

    private void sendMessage(Session session, ServerMessage message) throws IOException {
        String msg = new Gson().toJson(message);
        System.out.println("sending msg: " + msg);
        session.getRemote().sendString(msg);
    }


}
