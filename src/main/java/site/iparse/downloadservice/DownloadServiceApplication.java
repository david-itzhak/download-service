package site.iparse.downloadservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
//@EnableConfigurationProperties({DbProperties.class})
@ConfigurationPropertiesScan
@Slf4j
public class DownloadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownloadServiceApplication.class, args);
    }
}
