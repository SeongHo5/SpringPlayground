package com.seongho.spring.slack.controller;

import com.seongho.spring.slack.service.SlackService;
import com.seongho.spring.slack.dto.SlackSlashCommandDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slack")
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/slash-command")
    public ResponseEntity<String> handleSlashCommand(
            final @RequestParam("channel_id") String channelId,
            final @RequestParam("channel_name") String channelName,
            final @RequestParam("user_id") String userId,
            final @RequestParam("user_name") String userName,
            final @RequestParam("command") String command,
            final @RequestParam("text") String text
    ) {
        SlackSlashCommandDto commandDto =
                new SlackSlashCommandDto(
                        channelId,
                        channelName,
                        userId,
                        userName,
                        command,
                        text);
        return slackService.handleSlashCommand(commandDto);
    }
}
