package site.iparse.downloadservice.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import site.iparse.downloadservice.dto.DbProperties;
import site.iparse.downloadservice.integration.IntegrationTestBase;
import site.iparse.downloadservice.integration.annotation.IT;

@RequiredArgsConstructor
class DownloadServiceIT extends IntegrationTestBase {

    private final DbProperties dbProperties;

    @Test
    void test() {
        System.out.println(dbProperties);
    }
}
