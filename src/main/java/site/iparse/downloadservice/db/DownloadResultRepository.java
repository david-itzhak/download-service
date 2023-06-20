package site.iparse.downloadservice.db;

import org.springframework.data.jpa.repository.JpaRepository;
import site.iparse.downloadservice.db.entity.DownloadResult;

import java.util.Optional;

public interface DownloadResultRepository extends JpaRepository<DownloadResult, Integer> {

    Optional<DownloadResult> findById(Integer id);

    void delete(DownloadResult entity);
}
