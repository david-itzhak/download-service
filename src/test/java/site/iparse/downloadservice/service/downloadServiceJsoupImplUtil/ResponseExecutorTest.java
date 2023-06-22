package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.jsoup.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.iparse.downloadservice.dto.ConnectionData;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ResponseExecutorTest {

    @Mock
    private ConnectionDataValidator connectionDataValidator;
    @Mock
    private ConnectionCreator connectionCreator;
    @Mock
    private Connection connection;
    @Mock
    private Connection.Response response;

    @InjectMocks
    private ResponseExecutor responseExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        responseExecutor = new ResponseExecutor(connectionDataValidator, connectionCreator);
    }

    @Test
    public void testGetResponse() throws IOException {
        // Create mock connection data
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

        // Mock the behavior of the validator
        doNothing().when(connectionDataValidator).validateConnectionData(connectionData);

        // Mock the behavior of the connection creator
        when(connectionCreator.createConnection(connectionData)).thenReturn(connection);

        // Mock the behavior of the connection execute method
        when(connection.execute()).thenReturn(response);

        // Call the method being tested
        Connection.Response actualResponse = responseExecutor.getResponse(connectionData);

        // Verify that the validator and connection creator were called
        verify(connectionDataValidator).validateConnectionData(connectionData);
        verify(connectionCreator).createConnection(connectionData);

        // Assert that the actual response matches the mocked response
        Assertions.assertEquals(response, actualResponse);
    }
}