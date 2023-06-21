package site.iparse.downloadservice.service.downloadServiceJsoupImplUtil;

import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ConnectionData;

import java.util.Objects;

@Component
public class ConnectionDataValidator {
    public void validateConnectionData(ConnectionData connectionData) {
        Objects.requireNonNull(connectionData, "The ConnectionData object must not be null.");
        Objects.requireNonNull(connectionData.getTaskUuid(), "The ConnectionData object must contain a non-null 'taskUuid' field.");
        Objects.requireNonNull(connectionData.getDownloadUrl(), "The ConnectionData object must contain a non-null 'downloadUrl' field.");
    }
}
