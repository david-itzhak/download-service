package site.iparse.downloadservice.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.hamcrest.collection.IsCollectionWithSize;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testcontainers.shaded.org.hamcrest.collection.IsCollectionWithSize.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class DownloadResultControllerTest extends IntegrationTestBase {

    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    private final MockMvc mockMvc;

    @Test
    void executeTaskAndCreateResult() {

    }

    @Test
    void findAllDownloadResults() throws Exception {

        String responseBody = "[{\"id\":1,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440000\",\"downloadUrl\":\"https://example1.com\",\"downloadText\":\"Lorem ipsum dolor sit amet...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-11T07:30:00.000+00:00\"},{\"id\":2,\"taskUuid\":\"00000000-0000-0000-0000-000000000000\",\"downloadUrl\":\"https://example2.ru\",\"downloadText\":null,\"downloadStatus\":null,\"downloadStatusMessage\":null,\"responseContentType\":null,\"responseHeaders\":null,\"responseCookies\":null,\"responseTimestamp\":null},{\"id\":3,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440001\",\"downloadUrl\":\"https://example3.de\",\"downloadText\":\"Lorem ipsum dolor sit amet...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/plain\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-10T07:45:00.000+00:00\"},{\"id\":4,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440002\",\"downloadUrl\":\"https://example4.il/page1\",\"downloadText\":\"Page 1 content...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-11T08:00:00.000+00:00\"},{\"id\":5,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440003\",\"downloadUrl\":\"https://example5.fr/page2\",\"downloadText\":\"Page 2 content...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-06-11T08:01:00.000+00:00\"},{\"id\":6,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440004\",\"downloadUrl\":\"https://example6.gb/page3\",\"downloadText\":\"Page 3 content...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2022-07-11T08:02:00.000+00:00\"},{\"id\":7,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440004\",\"downloadUrl\":\"https://example7.ua\",\"downloadText\":null,\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-11T08:45:00.000+00:00\"},{\"id\":8,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440006\",\"downloadUrl\":\"https://example8.com\",\"downloadText\":\"Lorem ipsum dolor sit amet...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":null,\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-11T09:00:00.000+00:00\"},{\"id\":9,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440007\",\"downloadUrl\":\"https://example9.es\",\"downloadText\":\"Lorem ipsum dolor sit amet...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/plain\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-07-11T09:15:00.000+00:00\"},{\"id\":10,\"taskUuid\":\"123e4567-e89b-12d3-a456-426655440008\",\"downloadUrl\":\"https://example10.nl\",\"downloadText\":\"Lorem ipsum dolor sit amet...\",\"downloadStatus\":200,\"downloadStatusMessage\":\"OK\",\"responseContentType\":\"text/html\",\"responseHeaders\":\"Header1: Value1\",\"responseCookies\":\"Cookie1=Value1\",\"responseTimestamp\":\"2023-05-11T09:15:00.000+00:00\"}]";
        mockMvc.perform(get("/downloads"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(responseBody))
                .andExpect(content().json(responseBody))
        ;

    }

    @Test
    void findDownloadResultById() {
    }

    @Test
    void findDownloadResultByUuid() {
    }
}