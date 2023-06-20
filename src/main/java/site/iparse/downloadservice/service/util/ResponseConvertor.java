package site.iparse.downloadservice.service.util;

import org.jsoup.Connection;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ResponseData;

@Component
public class ResponseConvertor {
    public ResponseData convertToResponseData(Connection.Response response) {
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
}
