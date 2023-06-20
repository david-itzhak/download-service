package site.iparse.downloadservice.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "app.download-service.v2.enabled", havingValue = "true")
@Slf4j
public class DownloadServiceHttpURLConnectionImpl implements DownloadService {

    @Override
    public ResponseData getResponseData(ConnectionData connectionData) {
        HttpURLConnection connection = null;
        try {
            connection = createConnection(connectionData);
            String responseBody = getResponseBody(connection);
            ResponseData responseData = convertToResponseData(connection, responseBody);
            return responseData;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private HttpURLConnection createConnection(ConnectionData connectionData) throws IOException {
        HttpURLConnection connection;
        // Create URL object
        URL url = new URL(connectionData.getDownloadUrl());
        if (connectionData.getHost() != null && connectionData.getPort() != 0) {
            InetSocketAddress proxyAddress = new InetSocketAddress(connectionData.getHost(), connectionData.getPort());
            // Create Proxy object
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
            connection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }

        if (connectionData.getMethod() != null) {
            connection.setRequestMethod(connectionData.getMethod());
            if ("POST".equals(connectionData.getMethod())) {
                connection.setDoOutput(true);
                writeRequestBody(connection, connectionData.getRequestBody());
            }
        }

        if (connectionData.getHeaders() != null) {
            for (Map.Entry<String, String> entry : connectionData.getHeaders().entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        if (connectionData.getCookies() != null) {
            StringBuilder cookieBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : connectionData.getCookies().entrySet()) {
                if (cookieBuilder.length() > 0) {
                    cookieBuilder.append("; ");
                }
                cookieBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
            connection.setRequestProperty("Cookie", cookieBuilder.toString());
        }


        return connection;
    }

    private void writeRequestBody(HttpURLConnection connection, String requestBody) throws IOException {
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(requestBody.getBytes());
        }
    }

    private String getResponseBody(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        }
    }

    private ResponseData convertToResponseData(HttpURLConnection connection, String responseBody) throws IOException {
        Map<String, List<String>> headersMap = connection.getHeaderFields();

        return ResponseData.builder()
                .statusCode(connection.getResponseCode())
                .statusMessage(connection.getResponseMessage())
                .headers(convertEntryValueListOfMapToString(headersMap, ","))
                .cookies(convertEntryValueListOfMapToString(headersMap, ";"))
                .contentType(connection.getHeaderField("Content-Type"))
                .charset(getCharsetFromContentType(connection.getHeaderField("Content-Type")))
                .httpBody(responseBody)
                .build();
    }

    private String getCharsetFromContentType(String contentType) {
        if (contentType != null && contentType.contains("charset=")) {
            return contentType.split("charset=")[1];
        }
        return null;
    }

    private Map<String, String> convertEntryValueListOfMapToString(Map<String, List<String>> initialMap, String delimiter) {
        Map<String, String> convertedMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : initialMap.entrySet()) {
            String entryKey = entry.getKey();
            List<String> valuesList = entry.getValue();

            // If there are multiple values, concatenate them with the specified delimiter
            String concatenatedValue = String.join(delimiter, valuesList);

            convertedMap.put(entryKey, concatenatedValue);
        }

        return convertedMap;
    }

    @PostConstruct
    private void someMethod(){
        log.info("Active candidate for DownloadService is DownloadServiceHttpURLConnectionImpl");
    }

}
