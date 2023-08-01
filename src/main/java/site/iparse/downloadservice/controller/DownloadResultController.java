package site.iparse.downloadservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.DownloadResultDto;
import site.iparse.downloadservice.service.execute.DownloadTaskExecutorService;
import site.iparse.downloadservice.service.result.ResultService;

import java.util.List;

@RestController("downloads")
@RequestMapping(path = "/downloads", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class DownloadResultController {

    private final DownloadTaskExecutorService downloadTaskExecutorService;
    private final ResultService resultService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DownloadResultDto executeTaskAndCreateResult(@RequestBody @Valid ConnectionData connectionData) {
        return downloadTaskExecutorService.executeDownloadTask(connectionData);
    }

    @GetMapping()
    public List<DownloadResultDto> findAllDownloadResults() {
        return resultService.findAll();
    }

    @GetMapping("/id/{id}")
    public DownloadResultDto findDownloadResultById(@PathVariable Long id) {
        return resultService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/uuid/{uuid}")
    public List<DownloadResultDto> findDownloadResultByUuid(@PathVariable String uuid) {
        return resultService.findByUuid(uuid);
    }
}
