package site.iparse.downloadservice.service.download;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.mapper.ResponseDataMapper;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.download-service.v3.enabled", havingValue = "true")
//@Primary
@Slf4j
public class DownloadServiceHttpClientImpl implements DownloadService {

    private final ResponseDataMapper responseDataMapper;
    @Override
    public ResponseData getResponseData(@Valid ConnectionData connectionData) {
        Optional<HttpResponse<String>> response = getResponse(connectionData);
        return response.map(responseDataMapper::map).orElse(null);
    }

    private Optional<HttpResponse<String>> getResponse(ConnectionData connectionData) {
        try {
            HttpClient httpClient = getHttpClient(connectionData);
            HttpRequest.Builder requestBuilder = getRequestBuilder(connectionData);
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Optional.ofNullable(httpResponse);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.warn("Error while getting response from url: " + connectionData.getDownloadUrl(), e);
            return Optional.empty();
        }
    }

    private HttpRequest.Builder getRequestBuilder(ConnectionData connectionData) throws URISyntaxException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(new URI(connectionData.getDownloadUrl()));
        if (connectionData.getHttpMethod() != null) {
            setHttpMethod(connectionData, requestBuilder);
        }

        if (connectionData.getHeaders() != null) {
            setHeaders(connectionData, requestBuilder);
        }

        if (connectionData.getCookies() != null) {
            setCookies(connectionData, requestBuilder);
        }
        return requestBuilder;
    }

    private HttpClient getHttpClient(ConnectionData connectionData) {
        HttpClient httpClient;
        if (connectionData.getProxyHost() != null && connectionData.getProxyPort() != 0) {
            HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                    .proxy(ProxySelector.of(
                            new InetSocketAddress(connectionData.getProxyHost(), connectionData.getProxyPort())));
            if (connectionData.getProxyUsername() != null
                    && connectionData.getProxyPassword() != null
                    && connectionData.getProxyUsername().length() > 0
                    && connectionData.getProxyPassword().length() > 0) {
                clientBuilder.authenticator(getAuthenticator(connectionData));
            }
            httpClient = clientBuilder.build();
        } else {
            httpClient = HttpClient.newHttpClient();
        }
        return httpClient;
    }

    private Authenticator getAuthenticator(ConnectionData connectionData) {
        return new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        connectionData.getProxyUsername(), connectionData.getProxyPassword().toCharArray());
            }
        };
    }

    private void setCookies(ConnectionData connectionData, HttpRequest.Builder requestBuilder) {
        connectionData.getCookies().forEach((name, value) -> requestBuilder.header("Cookie", name + "=" + value));
    }

    private void setHeaders(ConnectionData connectionData, HttpRequest.Builder requestBuilder) {
        connectionData.getHeaders().forEach(requestBuilder::header);
    }

    private void setHttpMethod(ConnectionData connectionData, HttpRequest.Builder requestBuilder) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
        if ("POST".equals(connectionData.getHttpMethod())) {
            bodyPublisher = HttpRequest.BodyPublishers.ofString(connectionData.getRequestBody());
        }
        requestBuilder.method(connectionData.getHttpMethod(), bodyPublisher);
    }

    @PostConstruct
    private void someMethod() {
        log.info("Active candidate for DownloadService is DownloadServiceHttpClientImpl");
    }
}
