package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import site.iparse.downloadservice.dto.ConnectionData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionCreatorTest {

    private ConnectionCreator connectionCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        connectionCreator = new ConnectionCreator();
    }

    @Test
    void testCreateConnectionGet() {
        String downloadUrl = "https://example.com";
        String method = "GET";
        Map<String, String> headers = Map.of(
                "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15",
                "Accept-Encoding", "compress");
        Map<String, String> cookies = Collections.singletonMap("session", "12345");
        String host = "proxy.example.com";
        int port = 8080;
        UUID randomUUID = UUID.randomUUID();
        boolean followRedirects = true;
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl(downloadUrl)
                .method(method)
                .taskUuid(randomUUID)
                .headers(headers)
                .cookies(cookies)
                .host(host)
                .port(port)
                .followRedirects(followRedirects)
                .build();

        Connection createdConnection = connectionCreator.createConnection(connectionData);

        assertEquals(downloadUrl, createdConnection.request().url().toString());
        assertEquals(Connection.Method.GET, createdConnection.request().method());
        assertEquals(headers, createdConnection.request().headers());
        assertEquals(cookies, createdConnection.request().cookies());
        assertEquals(host + "/<unresolved>", createdConnection.request().proxy().address().toString().split(":")[0]);
        assertEquals(port, Integer.parseInt(createdConnection.request().proxy().address().toString().split(":")[1]));
        assertEquals(followRedirects, createdConnection.request().followRedirects());
    }

    @Test
    void testCreateConnectionPost() {
        String downloadUrl = "https://example.com";
        String method = "POST";
        String requestBody = "Test request body";
        Map<String, String> headers = Map.of(
                "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15",
                "Accept-Encoding", "compress");
        Map<String, String> cookies = Collections.singletonMap("session", "12345");
        String host = "proxy.example.com";
        int port = 8080;
        UUID randomUUID = UUID.randomUUID();
        boolean followRedirects = true;
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl(downloadUrl)
                .method(method)
                .taskUuid(randomUUID)
                .requestBody(requestBody)
                .headers(headers)
                .cookies(cookies)
                .host(host)
                .port(port)
                .followRedirects(followRedirects)
                .build();

        Connection createdConnection = connectionCreator.createConnection(connectionData);

        assertEquals(downloadUrl, createdConnection.request().url().toString());
        assertEquals(Connection.Method.POST, createdConnection.request().method());
        assertEquals(requestBody, createdConnection.request().requestBody());
        assertEquals(headers, createdConnection.request().headers());
        assertEquals(cookies, createdConnection.request().cookies());
        assertEquals(host + "/<unresolved>", createdConnection.request().proxy().address().toString().split(":")[0]);
        assertEquals(port, Integer.parseInt(createdConnection.request().proxy().address().toString().split(":")[1]));
        assertEquals(followRedirects, createdConnection.request().followRedirects());
    }

    @Test
    void testCreateConnectionPostWithDefaultHeaders() {
        String downloadUrl = "https://example.com";
        String method = "POST";
        String requestBody = "Test request body";
        Map<String, String> headers = Map.of(
                "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36",
                "Accept-Encoding", "gzip");
        Map<String, String> cookies = Collections.singletonMap("session", "12345");
        String host = "proxy.example.com";
        int port = 8080;
        UUID randomUUID = UUID.randomUUID();
        boolean followRedirects = true;
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl(downloadUrl)
                .method(method)
                .taskUuid(randomUUID)
                .requestBody(requestBody)
                .cookies(cookies)
                .host(host)
                .port(port)
                .followRedirects(followRedirects)
                .build();

        Connection createdConnection = connectionCreator.createConnection(connectionData);

        assertEquals(downloadUrl, createdConnection.request().url().toString());
        assertEquals(Connection.Method.POST, createdConnection.request().method());
        assertEquals(requestBody, createdConnection.request().requestBody());
        assertEquals(headers, createdConnection.request().headers());
        assertEquals(cookies, createdConnection.request().cookies());
        assertEquals(host + "/<unresolved>", createdConnection.request().proxy().address().toString().split(":")[0]);
        assertEquals(port, Integer.parseInt(createdConnection.request().proxy().address().toString().split(":")[1]));
        assertEquals(followRedirects, createdConnection.request().followRedirects());
    }


    @Test
    void testCreateConnectionPostWithoutUrl() {

        ConnectionData connectionData = ConnectionData.builder()
                .build();

        assertThrows(org.jsoup.helper.ValidationException.class, () -> connectionCreator.createConnection(connectionData));
    }

    @Test
    void testCreateConnectionPostWithoutParaml() {
        String downloadUrl = "https://example.com";
        Map<String, String> headers = Map.of(
                "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36",
                "Accept-Encoding", "gzip");
        Map<String, String> cookies = new HashMap<>();
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl(downloadUrl)
                .build();

        Connection createdConnection = connectionCreator.createConnection(connectionData);

        assertEquals(downloadUrl, createdConnection.request().url().toString());
        assertEquals(Connection.Method.GET, createdConnection.request().method());
        assertNull(createdConnection.request().requestBody());
        assertEquals(headers, createdConnection.request().headers());
        assertEquals(cookies, createdConnection.request().cookies());
        assertNull(createdConnection.request().proxy());
        assertFalse(createdConnection.request().followRedirects());
    }

}
