package site.iparse.downloadservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ResponseData {

    String httpBody;
    Map<String, String> headers;
    Map<String, String> cookies;
    Integer statusCode;
    String statusMessage;
    String contentType;
    String charset;
}
