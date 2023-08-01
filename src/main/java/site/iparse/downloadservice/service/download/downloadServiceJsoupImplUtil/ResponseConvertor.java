package site.iparse.downloadservice.service.download.downloadServiceJsoupImplUtil;

import org.jsoup.Connection;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ResponseData;

import java.sql.Timestamp;

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
                .responseTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
