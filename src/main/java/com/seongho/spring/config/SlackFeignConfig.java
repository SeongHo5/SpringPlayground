package com.seongho.spring.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class SlackFeignConfig {

    @Value("${slack.oauth.token}")
    private String slackOauthToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate
                -> requestTemplate
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + slackOauthToken);
    }

}
