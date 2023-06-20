package site.iparse.downloadservice.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;

import java.io.IOException;

@Service
@ConditionalOnProperty(name = "app.download-service.v1.enabled", havingValue = "true")
//@Primary
@Slf4j
public class DownloadServiceJsoupImpl implements DownloadService {
    @Override
    public ResponseData getResponseData(ConnectionData connectionData) {
        Connection.Response response = getResponse(connectionData);
        ResponseData responseData = convertToResponseData(response);
        return responseData;
    }

    private Connection.Response getResponse(ConnectionData connectionData) {
        Connection.Response response = null;
        Connection connection;
        try {
            if (connectionData.getDownloadUrl() != null) {
                connection = Jsoup.connect(connectionData.getDownloadUrl());
            } else {
                throw new IllegalArgumentException("The ConnectionData object contains null in a url field. The url field is necessary and it should contain a valid URL address.");
            }
            if (connectionData.getMethod() != null) {
                Connection.Method method = "POST".equals(connectionData.getMethod())
                        ? Connection.Method.POST
                        : Connection.Method.GET;
                connection.method(method);
                connection.requestBody(connectionData.getRequestBody());
            }
            if (connectionData.getHeaders() != null) {
                connection.headers(connectionData.getHeaders());
            }
            if (connectionData.getCookies() != null) {
                connection.cookies(connectionData.getCookies());
            }
            if (connectionData.getHost() != null && connectionData.getPort() != 0) {
                connection.proxy(connectionData.getHost(), connectionData.getPort());
            }
            connection.followRedirects(true);
            response = connection.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    
    private ResponseData convertToResponseData(Connection.Response response) {
        return ResponseData.builder()
                .statusCode(response.statusCode())
                .statusMessage(response.statusMessage())
                .headers(response.headers())
                .cookies(response.cookies())
                .contentType(response.contentType())
                .charset(response.charset())
                .httpBody(response.body())
                .build();
    }

    @PostConstruct
    private void someMethod() {
        log.info("Active candidate for DownloadService is DownloadServiceJsoupImpl");
    }
}
