package com.seongho.spring.slack.service;

import com.seongho.spring.config.SlackFeignConfig;
import com.seongho.spring.slack.dto.SlackMessageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slack", url = "https://slack.com/api", configuration = SlackFeignConfig.class)
public interface SlackFeignClient {

    @PostMapping("/chat.postMessage")
    void postMessage(@RequestBody SlackMessageRequestDto request);

    @PostMapping("/conversations.join")
    void joinChannel(@RequestBody SlackMessageRequestDto request);

}
