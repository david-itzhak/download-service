package site.iparse.downloadservice.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import site.iparse.downloadservice.integration.annotation.IT;

@IT
@Sql({
        "classpath:sql/data.sql"
})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:alpine");

    @BeforeAll
    static void containerStart(){
        POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
    }

}
