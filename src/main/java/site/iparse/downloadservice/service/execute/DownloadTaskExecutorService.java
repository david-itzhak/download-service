package site.iparse.downloadservice.service.execute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.iparse.downloadservice.db.DownloadResultRepository;
import site.iparse.downloadservice.db.entity.DownloadResult;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.DownloadResultDto;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.mapper.DownloadResultEntityMapper;
import site.iparse.downloadservice.mapper.DownloadResultDtoMapper;
import site.iparse.downloadservice.service.download.DownloadService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadTaskExecutorService {

    private final DownloadService downloadService;
    private final DownloadResultEntityMapper downloadResultEntityMapper;
    private final DownloadResultRepository downloadResultRepository;
    private final DownloadResultDtoMapper downloadResultDtoMapper;


    @Transactional
    public DownloadResultDto executeDownloadTask(ConnectionData connectionData) {
        ResponseData responseData = downloadService.getResponseData(connectionData);
        DownloadResult downloadResultEntity = downloadResultEntityMapper.map(connectionData, responseData);
        DownloadResult saved = downloadResultRepository.save(downloadResultEntity);
        return downloadResultDtoMapper.map(saved);
    }
}
