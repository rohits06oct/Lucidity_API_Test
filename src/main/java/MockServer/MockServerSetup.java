package MockServer;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockServerSetup {
    private static ClientAndServer mockServer;

    public static void startMockServer() {
        try {
            mockServer = ClientAndServer.startClientAndServer(1080);

            // Reading mock configuration from JSON file
            JSONParser parser = new JSONParser();
            JSONArray configArray = (JSONArray) parser.parse(new FileReader("src/main/java/MockServer/mock-config.json"));

            for (Object obj : configArray) {
                JSONObject mockConfig = (JSONObject) obj;

                String method = (String) mockConfig.get("method");
                String path = (String) mockConfig.get("path");
                int statusCode = ((Long) mockConfig.get("statusCode")).intValue();
                String responseBody = (String) mockConfig.get("body");

                HttpRequest httpRequest = request().withMethod(method).withPath(path);

                if (mockConfig.containsKey("queryStringParameters")) {
                    JSONObject queryParams = (JSONObject) mockConfig.get("queryStringParameters");
                    for (Object key : queryParams.keySet()) {
                        String param = (String) key;
                        String value = (String) queryParams.get(param);
                        httpRequest = httpRequest.withQueryStringParameter(param, value);
                    }
                }

                HttpResponse httpResponse = response().withStatusCode(statusCode).withBody(responseBody);
                mockServer.when(httpRequest).respond(httpResponse);
                System.out.println("Loaded Mock Configuration: " + mockConfig.toJSONString());
            }

            System.out.println("MockServer started at http://localhost:1080");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMockServer() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }

    public static void main(String[] args) {
        startMockServer();
    }
}
