package site.iparse.downloadservice.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "download_result")
public class DownloadResult implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_uuid", unique = true, nullable = false)
    private UUID taskUuid;

    @Column(name = "download_url", unique = true, nullable = false)
    private String downloadUrl;

    @Column(name = "download_text")
    private String downloadText;

    @Column(name = "download_status")
    private String downloadStatus;

    @Column(name = "download_status_message")
    private String downloadStatusMessage;

//    @Column(name = "download_param")
//    private String downloadParam;

    @Column(name = "response_format")
    private String responseFormat;

    @Column(name = "response_headers")
    private String responseHeaders;

    @Column(name = "response_cookies")
    private String responseCookies;

    @Column(name = "response_timestamp")
    private Timestamp responseTimestamp;

}
