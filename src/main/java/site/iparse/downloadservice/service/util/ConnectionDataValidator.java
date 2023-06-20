package site.iparse.downloadservice.service.util;

import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ConnectionData;

import java.util.Objects;

@Component
public class ConnectionDataValidator {
    public void validateConnectionData(ConnectionData connectionData) {
        Objects.requireNonNull(connectionData, "The ConnectionData object must not be null.");
        Objects.requireNonNull(connectionData.getTaskUuid(), "The ConnectionData object must contain a non-null 'downloadUrl' taskUuid.");
        Objects.requireNonNull(connectionData.getDownloadUrl(), "The ConnectionData object must contain a non-null 'downloadUrl' field.");
    }
}
