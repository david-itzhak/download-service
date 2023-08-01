package site.iparse.downloadservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.iparse.downloadservice.db.DownloadResultRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseCleanup {

    private final DownloadResultRepository downloadResultRepository;

    @Value("${database.cleanup.minus_hours}")
    private Integer hours;

    @Scheduled(initialDelayString = "${database.cleanup.delay}", fixedRateString = "${database.cleanup.interval}")
    @Async
    @Transactional
    public void deleteOldRecords() {
        try {
            LocalDateTime threshold = LocalDateTime.now().minusHours(hours);
            Timestamp timestamp = Timestamp.valueOf(threshold);
            downloadResultRepository.deleteAllByResponseTimestampLessThan(timestamp);
            log.info("Deleted records older than: " + threshold);
        } catch (Exception e) {
            log.warn("Error while deleting old records", e);
        }
    }
}
