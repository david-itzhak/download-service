package site.iparse.downloadservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.RelationTargetAuditMode;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "download_result")
//@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class DownloadResult extends AuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_uuid", nullable = false)
    private UUID taskUuid;

    @Column(name = "download_url", nullable = false, length = 1024)
    private String downloadUrl;

    @Column(name = "http_method", length = 8)
    private String httpMethod;

    @Column(name = "request_headers", length = 2048)
    private String requestHeaders;

    @Column(name = "request_cookies", length = 1024)
    private String requestCookies;

    @Column(name = "proxy_host", length = 1024)
    private String proxyHost;

    @Column(name = "proxy_port")
    private Integer proxyPort;

    @Column(name = "proxy_username")
    private String proxyUsername;

    @Column(name = "proxy_password")
    private String proxyPassword;

    @Column(name = "download_text", columnDefinition = "text")
    private String downloadText;

    @Column(name = "download_status")
    private Integer downloadStatus;

    @Column(name = "download_status_message", length = 16)
    private String downloadStatusMessage;

    @Column(name = "response_content_type", length = 128)
    private String responseContentType;

    @Column(name = "response_headers", length = 2048)
    private String responseHeaders;

    @Column(name = "response_cookies", length = 1024)
    private String responseCookies;

    @Column(name = "response_timestamp")
    private Timestamp responseTimestamp;

    @Column(name = "attempt_count")
    private Integer attemptCount;

}
