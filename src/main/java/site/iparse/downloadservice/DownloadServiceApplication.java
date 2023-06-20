package site.iparse.downloadservice;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.DownloadService;

import java.util.UUID;

@SpringBootApplication
//@EnableConfigurationProperties({DbProperties.class})
@ConfigurationPropertiesScan
@Slf4j
public class DownloadServiceApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(DownloadServiceApplication.class, args);
		var definitionCount= context.getBeanDefinitionCount();

		DownloadService downloadService = context.getBean(DownloadService.class);
		ConnectionData connectionData = ConnectionData.builder()
				.downloadUrl("https://www.olsale.co.il/product/141855?af=2055&bid=true")
				.taskUuid(UUID.randomUUID())
				.build();
		ResponseData responseData = downloadService.getResponseData(connectionData);
		System.out.println("Headers: " + responseData.getHeaders());
		System.out.println("Cookies: " + responseData.getCookies());
		System.out.println("StatusCode: " + responseData.getStatusCode());
		System.out.println("StatusMessage: " + responseData.getStatusMessage());
		System.out.println("ContentType: " + responseData.getContentType());
		System.out.println("Charset: " + responseData.getCharset());



		System.out.println("definitionCount: " + definitionCount);
	}

	@PostConstruct
	private void someMethod(){
		log.warn("PostConstruct method is executed. >>> warn");
		log.info("PostConstruct method is executed. >>> info");
	}

	@PreDestroy
	private void anotherMethod(){
		log.info("PreDestroy method is executed");
	}

}
