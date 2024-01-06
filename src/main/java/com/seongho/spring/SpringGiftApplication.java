package com.seongho.spring;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

import static com.seongho.spring.common.log.MyVelogLogger.logServerStart;

@SpringBootApplication
@EnableJpaAuditing
public class SpringGiftApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGiftApplication.class, args);
        logServerStart();
    }

    /**
     * TimeZone을 한국 시간으로 설정
     */
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
