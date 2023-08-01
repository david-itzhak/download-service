package site.iparse.downloadservice.mapper;

import org.springframework.stereotype.Component;
import site.iparse.downloadservice.db.entity.DownloadResult;
import site.iparse.downloadservice.dto.DownloadResultDto;

@Component
public class DownloadResultDtoMapper implements Mapper<DownloadResult, DownloadResultDto> {
    @Override
    public DownloadResultDto map(DownloadResult from) {
        return DownloadResultDto.builder()
                .id(from.getId())
                .taskUuid(from.getTaskUuid())
                .downloadUrl(from.getDownloadUrl())
                .downloadText(from.getDownloadText())
                .downloadStatus(from.getDownloadStatus())
                .downloadStatusMessage(from.getDownloadStatusMessage())
                .responseContentType(from.getResponseContentType())
                .responseHeaders(from.getRequestHeaders())
                .responseCookies(from.getResponseCookies())
                .responseTimestamp(from.getResponseTimestamp())
                .build();
    }
}