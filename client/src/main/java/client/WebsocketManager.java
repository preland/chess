package client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketManager extends Endpoint {
    public Session session;
    public WebsocketManager(String url) throws DeploymentException, URISyntaxException, IOException {
        try {
            URI uri = new URI("ws://" + url + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                handleMessage(message);
            }

        });


        } catch (URISyntaxException | DeploymentException | IOException e) {
            throw e;
        }

    }

    private void handleMessage(String message) {
        System.out.println(message);
    }
    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
