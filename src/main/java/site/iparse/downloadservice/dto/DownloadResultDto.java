package site.iparse.downloadservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
@FieldNameConstants
public class DownloadResultDto {

    private Long id;
    private UUID taskUuid;
    private String downloadUrl;
    private String downloadText;
    private Integer downloadStatus;
    private String downloadStatusMessage;
    private String responseContentType;
    private String responseHeaders;
    private String responseCookies;
    private Timestamp responseTimestamp;
}
