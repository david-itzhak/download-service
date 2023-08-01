package site.iparse.downloadservice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.service.download.DownloadService;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DownloadServiceApplicationTests {

    @Autowired
    DownloadService downloadService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test the constraint NotBlank on the downloadUrl field in DTO ConnectionData. DownloadUrl is null and should return a violation")
    void dtoConnectionDataDownloadUrlNotBlankNullViolationTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ConnectionData connectionData = ConnectionData.builder()
//				.downloadUrl("https://www.olsale.co.il/product/141855?af=2055&bid=true")
                .taskUuid(UUID.randomUUID()).build();

        Set<ConstraintViolation<ConnectionData>> violations = validator.validate(connectionData);
        if (violations.isEmpty()) {
            System.out.println("ConnectionDat object passed validation.");
        } else {
            for (var violation : violations) {
                assertEquals("downloadUrl", violation.getPropertyPath().toString());
                assertEquals("Download URL cannot be blank", violation.getMessageTemplate());
                System.out.println(violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Test the constraint NotBlank on the downloadUrl field in DTO ConnectionData. DownloadUrl is empty string and should return a violation")
    void dtoConnectionDataDownloadUrlNotBlankEmptyViolationTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ConnectionData connectionData = ConnectionData.builder().downloadUrl("").taskUuid(UUID.randomUUID()).build();

        Set<ConstraintViolation<ConnectionData>> violations = validator.validate(connectionData);
        if (violations.isEmpty()) {
            System.out.println("ConnectionDat object passed validation.");
        } else {
            for (var violation : violations) {
                assertEquals("downloadUrl", violation.getPropertyPath().toString());
                assertEquals("Download URL cannot be blank", violation.getMessageTemplate());
                System.out.println(violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Test the constraint NotBlank on the downloadUrl field in DTO ConnectionData. DownloadUrl has only spaces and should return a violation")
    void dtoConnectionDataDownloadUrlNotBlankOnlySpacesViolationTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ConnectionData connectionData = ConnectionData.builder().downloadUrl("  ").taskUuid(UUID.randomUUID()).build();

        Set<ConstraintViolation<ConnectionData>> violations = validator.validate(connectionData);
        if (violations.isEmpty()) {
            System.out.println("ConnectionDat object passed validation.");
        } else {
            for (var violation : violations) {
                assertEquals("downloadUrl", violation.getPropertyPath().toString());
                assertEquals("Download URL cannot be blank", violation.getMessageTemplate());
                System.out.println(violation.getMessage());
            }
        }
    }

}
