package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.iparse.downloadservice.dto.ResponseData;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ResponseConvertorTest {

    @Mock
    private Connection.Response mockedResponse;

    private ResponseConvertor responseConvertor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        responseConvertor = new ResponseConvertor();
    }
        @Test
    void testConvertToResponseData() {
        // Create a mock response
        when(mockedResponse.statusCode()).thenReturn(200);
        when(mockedResponse.statusMessage()).thenReturn("OK");
        when(mockedResponse.headers()).thenReturn(Map.of("k1", "v1", "k2", "v2"));
        when(mockedResponse.cookies()).thenReturn(Map.of("k1", "v1", "k2", "v2"));
        when(mockedResponse.contentType()).thenReturn("text/html");
        when(mockedResponse.charset()).thenReturn("UTF-8");
        when(mockedResponse.body()).thenReturn("Response body");

        // Test the convertToResponseData method
        ResponseData responseData = responseConvertor.convertToResponseData(mockedResponse);

        // Verify the converted response data
        assertEquals(200, responseData.getStatusCode());
        assertEquals("OK", responseData.getStatusMessage());
        assertFalse(responseData.getHeaders().isEmpty());
        assertFalse(responseData.getCookies().isEmpty());
        assertEquals("text/html", responseData.getContentType());
        assertEquals("UTF-8", responseData.getCharset());
        assertEquals("Response body", responseData.getHttpBody());
    }
}