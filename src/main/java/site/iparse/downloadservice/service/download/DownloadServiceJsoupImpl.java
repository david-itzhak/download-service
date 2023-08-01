package site.iparse.downloadservice.service.download;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.download.downloadServiceJsoupImplUtil.ResponseConvertor;
import site.iparse.downloadservice.service.download.downloadServiceJsoupImplUtil.ResponseExecutor;

@Service
@ConditionalOnProperty(name = "app.download-service.v1.enabled", havingValue = "true")
@Slf4j
public class DownloadServiceJsoupImpl implements DownloadService {

    private final ResponseConvertor responseConvertor;
    private final ResponseExecutor responseExecutor;

    private DownloadServiceJsoupImpl(ResponseConvertor responseConvertor,
                                     ResponseExecutor responseExecutor) {
        this.responseConvertor = responseConvertor;
        this.responseExecutor = responseExecutor;
    }

    @Override
    public ResponseData getResponseData(ConnectionData connectionData) {

        Connection.Response response = responseExecutor.getResponse(connectionData);
        return responseConvertor.convertToResponseData(response);
    }

    @PostConstruct
    private void initialize() {
        log.info("Active candidate for DownloadService is DownloadServiceJsoupImpl");
    }
}
