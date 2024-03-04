package dat.http;

import dat.config.ApplicationConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpClientUtil {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static String sendGetRequest(String endpoint) throws Exception {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:7070/api/v1/" + endpoint))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String sendPostRequest(String endpoint, String requestBody) throws Exception {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:7070/api/v1/" + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String sendPutRequest(String endpoint, String requestBody) throws Exception {
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:7070/api/v1/" + endpoint))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String sendDeleteRequest(String endpoint) throws Exception {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:7070/api/v1/" + endpoint))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}