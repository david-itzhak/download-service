package site.iparse.downloadservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Builder
@Data
public class ConnectionData {
    private UUID taskUuid;
    private String downloadUrl;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private String host;
    private int port;
    private String requestBody;
    private int attemptCount;
    private boolean followRedirects;
}
