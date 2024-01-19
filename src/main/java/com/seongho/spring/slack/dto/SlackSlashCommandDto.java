package com.seongho.spring.slack.dto;

import com.seongho.spring.slack.entity.SlackCommandHistory;
import lombok.Value;


public record SlackSlashCommandDto(
        String channelId,
        String channelName,
        String userId,
        String userName,
        String command,
        String text
) {

    public SlackCommandHistory toEntity() {
        return SlackCommandHistory.builder()
                                  .channelId(channelId)
                                  .channelName(channelName)
                                  .userId(userId)
                                  .userName(userName)
                                  .command(command)
                                  .text(text)
                                  .build();
    }

}
