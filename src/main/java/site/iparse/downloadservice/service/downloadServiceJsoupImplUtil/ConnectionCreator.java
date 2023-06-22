package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ConnectionData;

import java.util.Map;

@Component
public class ConnectionCreator {

    private final static Map<String, String> defaultHeaders = Map.of(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36",
            "Accept-Encoding", "gzip");
    public Connection createConnection(ConnectionData connectionData) {
        Connection connection = Jsoup.connect(connectionData.getDownloadUrl());
        setRequestMethod(connection, connectionData.getMethod());
        setRequestBody(connection, connectionData.getRequestBody());
        setHeaders(connection, connectionData.getHeaders());
        setCookies(connection, connectionData.getCookies());
        setProxy(connection, connectionData.getHost(), connectionData.getPort());
        connection.followRedirects(connectionData.isFollowRedirects());
        return connection;
    }

    private void setRequestBody(Connection connection, String requestBody) {
        if (requestBody != null && !requestBody.isEmpty()) {
            connection.requestBody(requestBody);
        }
    }

    private void setRequestMethod(Connection connection, String method) {
        if (method != null && !method.isEmpty()) {
            connection.method(Connection.Method.valueOf(method.toUpperCase()));
        }
    }

    private void setHeaders(Connection connection, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            connection.headers(headers);
        } else {
            connection.headers(defaultHeaders);
        }
    }

    private void setCookies(Connection connection, Map<String, String> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            connection.cookies(cookies);
        }
    }

    private void setProxy(Connection connection, String host, int port) {
        if (host != null && !host.isEmpty() && port != 0) {
            connection.proxy(host, port);
        }
    }
}
