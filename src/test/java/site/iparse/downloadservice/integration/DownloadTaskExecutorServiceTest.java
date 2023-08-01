package site.iparse.downloadservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.DownloadResultDto;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.download.DownloadService;
import site.iparse.downloadservice.service.download.downloadServiceUtil.MapToJsonConverter;
import site.iparse.downloadservice.service.execute.DownloadTaskExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
class DownloadTaskExecutorServiceIT extends IntegrationTestBase {

    @MockBean
    private DownloadService downloadService;

    private final DownloadTaskExecutorService downloadTaskExecutorService;
    private final MapToJsonConverter<String, String> mapToJsonConverter;

    @Test
    void executeDownloadTask() throws JsonProcessingException {

        // Arrange
        Map<String, String> headers = Map.of("Content-Type", "application/json", "Cache-Control", "no-cache");
        Map<String, String> cookies = Map.of("olsale", "eyJhbGciOiJIUzI1NiJ9.ODdCQzhEQTctN0Y4Qi00MTAyLUE4MkYtMzU4QUM5MEU0REMx.gnR2petTo7NtHvhBUCA2G-07Bx6BYq51ct7aItAipAA",
                "sessionId", "s%3A6519A93F-5CAB-48B6-BAFF-E209F23274AF.CJgl8lXa1Yhef%2BFsGJCplk928zJnATP2x3UE2pu6mPU)");

        UUID uuid = UUID.randomUUID();
        ConnectionData connectionData = ConnectionData.builder()
                .downloadUrl("https://example.com")
                .httpMethod("GET")
                .taskUuid(uuid)
                .headers(headers)
                .cookies(cookies)
                .proxyHost("localhost")
                .proxyPort(8080)
                .build();

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2023, 7, 30, 10, 15, 48));
        ResponseData responseData = ResponseData.builder()
                .statusCode(200)
                .statusMessage("OK")
                .contentType("application/json")
                .charset("UTF-8")
                .headers(headers)
                .cookies(cookies)
                .httpBody("{\"key\": \"value\"}")
                .responseTimestamp(timestamp)
                .build();

        when(downloadService.getResponseData(connectionData)).thenReturn(responseData);

        // Act
        DownloadResultDto downloadResultDto = downloadTaskExecutorService.executeDownloadTask(connectionData);

        // Assert
        assertEquals(DownloadResultDto.class, downloadResultDto.getClass());
        assertEquals(11L, downloadResultDto.getId());
        assertEquals(uuid, downloadResultDto.getTaskUuid());
        assertEquals("https://example.com", downloadResultDto.getDownloadUrl());
        assertEquals(200, downloadResultDto.getDownloadStatus());
        assertEquals("OK", downloadResultDto.getDownloadStatusMessage());
        assertEquals("application/json", downloadResultDto.getResponseContentType());
        assertEquals(mapToJsonConverter.convertToJson(headers), downloadResultDto.getResponseHeaders());
        assertEquals(mapToJsonConverter.convertToJson(cookies), downloadResultDto.getResponseCookies());
        assertEquals("{\"key\": \"value\"}", downloadResultDto.getDownloadText());
        assertEquals(timestamp, downloadResultDto.getResponseTimestamp());
    }
}