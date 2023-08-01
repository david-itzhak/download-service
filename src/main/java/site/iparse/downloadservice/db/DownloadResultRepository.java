package site.iparse.downloadservice.db;

import org.springframework.data.jpa.repository.JpaRepository;
import site.iparse.downloadservice.db.entity.DownloadResult;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DownloadResultRepository extends JpaRepository<DownloadResult, Long> {

    Optional<DownloadResult> findById(Long id);

    List<DownloadResult> findByTaskUuid(UUID uuid);

    void deleteAllByResponseTimestampLessThan(Timestamp timestamp);
//    void deleteAllByResponseTimestampIsNull();

}
