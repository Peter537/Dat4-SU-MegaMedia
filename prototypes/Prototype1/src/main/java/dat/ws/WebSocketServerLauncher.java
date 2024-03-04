package dat.ws;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import java.io.IOException;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;

public class WebSocketServerLauncher {

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path().addPrefixPath("/ws", websocket((exchange, channel) -> {
                    channel.getReceiveSetter().set(getListener());
                    channel.resumeReceives();
                })))
//                .setHandler(exchange -> {
//                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//                            exchange.getResponseSender().send("Hello World");
//                        }
//                )
                .build();
        System.out.println("Starting server on port 8080");
        server.start();
        System.out.println("Server started on port 8080");
    }

    private static AbstractReceiveListener getListener() {
        return new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                String messageData = message.getData();
                System.out.println("Received message: " + messageData);
                for (WebSocketChannel session : channel.getPeerConnections()) {
                    WebSockets.sendText(messageData, session, null);
                }
            }
        };
    }
}
