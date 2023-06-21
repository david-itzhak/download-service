package site.iparse.downloadservice.service;

import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.downloadServiceJsoupImplUtil.ResponseConvertor;
import site.iparse.downloadservice.service.downloadServiceJsoupImplUtil.ResponseExecutor;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ConditionalOnProperty(name = "app.download-service.v1.enabled", havingValue = "true")
//@SpringBootTest
class DownloadServiceJsoupImplTest {

    @Mock
    private Connection.Response mockedResponse;
    @Mock
    private ResponseConvertor responseConvertor;
    @Mock
    private ResponseExecutor responseExecutor;

//    @Spy
    @InjectMocks
    private DownloadServiceJsoupImpl downloadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetResponseData() {
        Map<String, String> headers = Map.of("Content-Type", "application/json", "Cache-Control", "no-cache");
        Map<String, String> cookies = Map.of("olsale", "eyJhbGciOiJIUzI1NiJ9.ODdCQzhEQTctN0Y4Qi00MTAyLUE4MkYtMzU4QUM5MEU0REMx.gnR2petTo7NtHvhBUCA2G-07Bx6BYq51ct7aItAipAA",
                "sessionId", "s%3A6519A93F-5CAB-48B6-BAFF-E209F23274AF.CJgl8lXa1Yhef%2BFsGJCplk928zJnATP2x3UE2pu6mPU)");

        // Arrange
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl("https://example.com")
                .method("GET")
                .taskUuid(UUID.randomUUID())
                .headers(Map.of("hk1", "vk1", "hk2", "vk2"))
                .cookies(Map.of("hc1", "vc1", "hc2", "vc2"))
                .host("localhost")
                .port(8080)
                .build();
        doReturn(mockedResponse).when(responseExecutor).getResponse(connectionData);
        doReturn(ResponseData.builder()
                .statusCode(200)
                .statusMessage("OK")
                .contentType("application/json")
                .charset("UTF-8")
                .headers(headers)
                .cookies(cookies)
                .httpBody("{\"key\": \"value\"}")
                .build()).when(responseConvertor).convertToResponseData(mockedResponse);

        // Act
        ResponseData responseData = downloadService.getResponseData(connectionData);

        // Assert
        assertEquals(ResponseData.class, responseData.getClass());
        assertEquals(200, responseData.getStatusCode());
        assertEquals("OK", responseData.getStatusMessage());
        assertEquals("application/json", responseData.getContentType());
        assertEquals("UTF-8", responseData.getCharset());
        assertEquals(headers, responseData.getHeaders());
        assertEquals(cookies, responseData.getCookies());
        assertEquals("{\"key\": \"value\"}", responseData.getHttpBody());
        verify(responseExecutor).getResponse(any(ConnectionData.class));
        verify(responseExecutor).getResponse(connectionData);
        verify(responseExecutor, times(1)).getResponse(connectionData);
        verify(responseConvertor).convertToResponseData(mockedResponse);
        verify(responseConvertor, times(1)).convertToResponseData(mockedResponse);
    }

//    void testGetResponseData() {
//        // Create a mock connection data
//        ConnectionData connectionData = ConnectionData.builder()
//                .downloadUrl("https://example.com")
//                .method("GET")
//                .build();
//
//        // Mock the getResponse method
//        doReturn(mockedResponse).when(downloadService).getResponse(connectionData);
//
//        // Create a mock response data
//        ResponseData mockedResponseData = ResponseData.builder()
//                .statusCode(200)
//                .statusMessage("OK")
//                .build();
//
//        // Mock the convertToResponseData method
//        doReturn(mockedResponseData).when(downloadService).convertToResponseData(mockedResponse);
//
//        // Test the getResponseData method
//        ResponseData responseData = downloadService.getResponseData(connectionData);
//
//        // Verify the methods were called and the expected response data was returned
//        verify(downloadService).getResponse(connectionData);
//        verify(downloadService).convertToResponseData(mockedResponse);
//        assertEquals(mockedResponseData, responseData);
//    }

//    @Test
//    void testValidateConnectionData() {
//        // Test with valid connection data
//        ConnectionData connectionData = ConnectionData.builder()
//                .downloadUrl("https://example.com")
//                .method("GET")
//                .taskUuid(UUID.randomUUID())
//                .build();
//        downloadService.validateConnectionData(connectionData);
//
//        // Test with null connection data
//        assertThrows(NullPointerException.class, () -> downloadService.validateConnectionData(null));
//
//        // Test with null taskUuid
//        ConnectionData invalidConnectionData1 = ConnectionData.builder()
//                .downloadUrl("https://example.com")
//                .method("GET")
//                .build();
//        assertThrows(NullPointerException.class, () -> downloadService.validateConnectionData(invalidConnectionData1));
//
//        // Test with null downloadUrl
//        ConnectionData invalidConnectionData2 = ConnectionData.builder()
//                .method("GET")
//                .taskUuid(UUID.randomUUID())
//                .build();
//        assertThrows(NullPointerException.class, () -> downloadService.validateConnectionData(invalidConnectionData2));
//    }

//    @Test
//    void testConvertToResponseData() {
//        // Create a mock response
//        when(mockedResponse.statusCode()).thenReturn(200);
//        when(mockedResponse.statusMessage()).thenReturn("OK");
//        when(mockedResponse.headers()).thenReturn(Map.of("k1", "v1", "k2", "v2"));
//        when(mockedResponse.cookies()).thenReturn(Map.of("k1", "v1", "k2", "v2"));
//        when(mockedResponse.contentType()).thenReturn("text/html");
//        when(mockedResponse.charset()).thenReturn("UTF-8");
//        when(mockedResponse.body()).thenReturn("Response body");
//
//        // Test the convertToResponseData method
//        ResponseData responseData = downloadService.convertToResponseData(mockedResponse);
//
//        // Verify the converted response data
//        assertEquals(200, responseData.getStatusCode());
//        assertEquals("OK", responseData.getStatusMessage());
//        assertFalse(responseData.getHeaders().isEmpty());
//        assertFalse(responseData.getCookies().isEmpty());
//        assertEquals("text/html", responseData.getContentType());
//        assertEquals("UTF-8", responseData.getCharset());
//        assertEquals("Response body", responseData.getHttpBody());
//    }
}