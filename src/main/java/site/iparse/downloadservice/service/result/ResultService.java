package site.iparse.downloadservice.service.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.iparse.downloadservice.db.DownloadResultRepository;
import site.iparse.downloadservice.dto.DownloadResultDto;
import site.iparse.downloadservice.mapper.DownloadResultDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultService {

    private final DownloadResultRepository downloadResultRepository;
    private final DownloadResultDtoMapper downloadResultDtoMapper;

    public List<DownloadResultDto> findAll(){
        return downloadResultRepository.findAll().stream()
                .map(downloadResultDtoMapper::map)
                .toList();
    }

    public Optional<DownloadResultDto> findById(Long id){
        return downloadResultRepository.findById(id)
                .map(downloadResultDtoMapper::map);
    }

    public List<DownloadResultDto> findByUuid(String uuidString){
        UUID uuid = UUID.fromString(uuidString);
        return downloadResultRepository.findByTaskUuid(uuid).stream()
                .map(downloadResultDtoMapper::map)
                .toList();
    }
}
