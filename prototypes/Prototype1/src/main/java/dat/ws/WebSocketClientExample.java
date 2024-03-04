package dat.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class WebSocketClientExample extends WebSocketClient {

    public WebSocketClientExample(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            // Create a WebSocket client instance
            WebSocketClientExample client = new WebSocketClientExample(new URI("ws://localhost:8080/ws"));

            // Connect to the WebSocket server
            client.connect();

            // Send messages to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message to send (type 'exit' to quit): ");
                String message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                client.send(message);
            }

            // Close the WebSocket connection
            client.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
