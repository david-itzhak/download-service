package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.iparse.downloadservice.dto.ConnectionData;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionDataValidatorTest {

    private ConnectionDataValidator connectionDataValidator;

    @BeforeEach
    void setUp(){
        connectionDataValidator = new ConnectionDataValidator();
    }

    @Test
    void validateConnectionData() {
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

        assertDoesNotThrow(() ->  connectionDataValidator.validateConnectionData(connectionData));
    }

    @Test
    void validateNullConnectionData() {
        ConnectionData connectionData = null;
        assertThrows(NullPointerException.class, () ->  connectionDataValidator.validateConnectionData(connectionData));
    }

    @Test
    void validateNullUrl() {
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
                .method(method)
                .taskUuid(randomUUID)
                .headers(headers)
                .cookies(cookies)
                .host(host)
                .port(port)
                .followRedirects(followRedirects)
                .build();
        assertThrows(NullPointerException.class, () ->  connectionDataValidator.validateConnectionData(connectionData));
    }

    @Test
    void validateNullUuid() {
        String downloadUrl = "https://example.com";
        String method = "GET";
        Map<String, String> headers = Map.of(
                "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15",
                "Accept-Encoding", "compress");
        Map<String, String> cookies = Collections.singletonMap("session", "12345");
        String host = "proxy.example.com";
        int port = 8080;
        boolean followRedirects = true;
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl(downloadUrl)
                .method(method)
                .headers(headers)
                .cookies(cookies)
                .host(host)
                .port(port)
                .followRedirects(followRedirects)
                .build();
        assertThrows(NullPointerException.class, () ->  connectionDataValidator.validateConnectionData(connectionData));
    }
}