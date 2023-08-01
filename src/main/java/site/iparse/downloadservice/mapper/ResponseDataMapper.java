package site.iparse.downloadservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.download.downloadServiceUtil.EntryValueListOfMapToStringConverter;

import java.net.http.HttpResponse;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class ResponseDataMapper implements Mapper<HttpResponse<String>, ResponseData>{

    private final EntryValueListOfMapToStringConverter entryValueListOfMapToStringConverter;
    @Override
    public ResponseData map(HttpResponse<String> response) {
        return ResponseData.builder()
                .statusCode(response.statusCode())
                .statusMessage(null)
                .headers(entryValueListOfMapToStringConverter.convertEntryValueListOfMapToString(response.headers().map(), ","))
                .cookies(entryValueListOfMapToStringConverter.convertEntryValueListOfMapToString(response.headers().map(), ";"))
                .contentType(response.headers().firstValue("Content-Type").orElse(null))
                .charset(response.headers().firstValue("Content-Type")
                        .map(contentType -> contentType.split("charset=")[1]).orElse(null))
                .httpBody(response.body())
                .responseTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
