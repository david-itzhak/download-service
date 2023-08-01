package site.iparse.downloadservice;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import site.iparse.downloadservice.db.DownloadResultRepository;
import site.iparse.downloadservice.db.entity.DownloadResult;
import site.iparse.downloadservice.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class RepositoryTests extends IntegrationTestBase {

    private final DownloadResultRepository downloadResultRepository;

    @Test
    void checkSelect(){
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
        List<DownloadResult> byTaskUuid = downloadResultRepository.findByTaskUuid(uuid);
        assertThat(byTaskUuid).hasSize(1);
        System.out.println(byTaskUuid);
    }

    @Test
    void checkAuditing(){
        DownloadResult downloadResult = downloadResultRepository.findById(2L).get();
        downloadResult.setDownloadText("pa-pa...");
        downloadResultRepository.saveAndFlush(downloadResult);
        System.out.println(downloadResult);
    }

    @Test
    void saveTest() {
        UUID randomUUID = UUID.randomUUID();

        DownloadResult downloadResult = DownloadResult.builder()
                .taskUuid(randomUUID)
                .downloadUrl("https://www.example.com")
                .build();
        DownloadResult saved = downloadResultRepository.save(downloadResult);


        List<DownloadResult> byTaskUuid = downloadResultRepository.findByTaskUuid(randomUUID);
        byTaskUuid.forEach(x -> assertEquals(saved, x));

    }

    @Test
    void deleteTest() {
        downloadResultRepository.deleteById(1L);
        assertFalse(downloadResultRepository.existsById(12L));

    }
}
