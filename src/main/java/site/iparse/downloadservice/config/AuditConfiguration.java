package site.iparse.downloadservice.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@Slf4j
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("default_user");
    }

    @PostConstruct
    private void logSchedulerConfigState() {
        log.info("AuditConfiguration is enabled");
    }
}
