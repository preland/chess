package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
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
        Server.userSessions.put(session, 0);
    }
    @OnWebSocketClose
    public void onClose(Session session, int num, String str) {
        Server.userSessions.remove(session);
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch(cmd.getCommandType()) {
            case CONNECT:
                break;
            case LEAVE:
                break;
            case MAKE_MOVE:
                break;
            case RESIGN:
                break;
            default:
                break;
        }
        try {
            System.out.println(message);
            sendMessage(session, message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessage(Session session, String message) throws IOException {
        System.out.println("sending msg: " + message);
        session.getRemote().sendString(message);
    }


}
