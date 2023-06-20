package site.iparse.downloadservice.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TestPropValue {

    @Value("${some.text}")
    public String text;

//    public TestPropValue(@Value("${some.text}") String text) {
//        this.text = text;
//    }

    @PostConstruct
    public void printAny(){
        log.info("printAny: " + text);
    }
}
