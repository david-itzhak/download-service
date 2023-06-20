package site.iparse.downloadservice.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "app.download-service.v3.enabled", havingValue = "true")
//@Primary
@Slf4j
public class DownloadServiceHttpClientImpl implements DownloadService {
    @Override
    public ResponseData getResponseData(ConnectionData connectionData) {
        HttpResponse<String> response = getResponse(connectionData);
        ResponseData responseData = convertToResponseData(response);
        return responseData;
    }

    private HttpResponse<String> getResponse(ConnectionData connectionData) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(new URI(connectionData.getDownloadUrl()));

            if (connectionData.getMethod() != null) {
                HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
                if ("POST".equals(connectionData.getMethod())) {
                    bodyPublisher = HttpRequest.BodyPublishers.ofString(connectionData.getRequestBody());
                }
                requestBuilder.method(connectionData.getMethod(), bodyPublisher);
            }

            if (connectionData.getHeaders() != null) {
                connectionData.getHeaders().forEach(requestBuilder::header);
            }

            if (connectionData.getCookies() != null) {
                connectionData.getCookies().forEach((name, value) -> requestBuilder.header("Cookie", name + "=" + value));
            }

            if (connectionData.getHost() != null && connectionData.getPort() != 0) {
                HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                        .proxy(java.net.ProxySelector.of(
                                new InetSocketAddress(connectionData.getHost(), connectionData.getPort())));
                httpClient = clientBuilder.build();
            }

            HttpRequest request = requestBuilder.build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseData convertToResponseData(HttpResponse<String> response) {
        return ResponseData.builder()
                .statusCode(response.statusCode())
                .statusMessage(null)
                .headers(convertEntryValueListOfMapToString(response.headers().map(), ","))
                .cookies(convertEntryValueListOfMapToString(response.headers().map(),  ";"))
                .contentType(response.headers().firstValue("Content-Type").orElse(null))
                .charset(response.headers().firstValue("Content-Type")
                        .map(contentType -> contentType.split("charset=")[1]).orElse(null))
                .httpBody(response.body())
                .build();
    }

    public static Map<String, String> convertEntryValueListOfMapToString(Map<String, List<String>> initialMap, String delimiter) {
        Map<String, String> convertedMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : initialMap.entrySet()) {
            String entryKey = entry.getKey();
            List<String> valuesList = entry.getValue();

            // If there are multiple values, concatenate them with semicolons
            String concatenatedValue = String.join(";", valuesList);

            convertedMap.put(entryKey, concatenatedValue);
        }

        return convertedMap;
    }

    @PostConstruct
    private void someMethod() {
        log.info("Active candidate for DownloadService is DownloadServiceHttpClientImpl");
    }
}
