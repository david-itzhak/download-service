package site.iparse.downloadservice.service.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.dto.ConnectionData;

import java.io.IOException;

@Component
@Slf4j
public class ResponseExecutor {

    private final ConnectionDataValidator connectionDataValidator;
    private final ConnectionCreator connectionCreator;
    private ResponseExecutor(ConnectionDataValidator connectionDataValidator,
                                     ConnectionCreator connectionCreator) {
        this.connectionDataValidator = connectionDataValidator;
        this.connectionCreator = connectionCreator;
    }
    public Connection.Response getResponse(ConnectionData connectionData) {
        connectionDataValidator.validateConnectionData(connectionData);
        Connection connection = connectionCreator.createConnection(connectionData);
        try {
            return connection.execute();
        } catch (IOException e) {
            log.error("Error executing connection: {}", e.getMessage());
            throw new RuntimeException("Failed to execute connection.", e);
        }
    }
}
