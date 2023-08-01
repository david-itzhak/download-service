package site.iparse.downloadservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


import java.util.Map;
import java.util.UUID;

@Builder
@Data
public class ConnectionData {

    @NotNull(message = "Task UUID cannot be null")
    private UUID taskUuid;
    @NotBlank (message = "Download URL cannot be blank")
    private String downloadUrl;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private String proxyHost;
    private Integer proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private String requestBody;
    private int attemptCount;
    private boolean followRedirects;
}
