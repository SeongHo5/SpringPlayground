package com.seongho.spring.slack.event.listener;

import com.seongho.spring.common.service.RedisService;
import com.seongho.spring.slack.entity.SlackNotificationHistory;
import com.seongho.spring.slack.enums.CommandType;
import com.seongho.spring.slack.event.SlackNotificationEvent;
import com.seongho.spring.slack.dto.SlackMessageRequestDto;
import com.seongho.spring.slack.repository.SlackNotificationHistoryRepository;
import com.seongho.spring.slack.service.SlackFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.seongho.spring.slack.service.SlackService.REDIS_ARGUMENTS_SEPARATOR;

@Component
@RequiredArgsConstructor
public class SlackEventListener {

    private final SlackFeignClient slackClient;
    private final RedisService redisService;
    private final SlackNotificationHistoryRepository historyRepository;

    @EventListener
    @Transactional
    public void handleNotificationEvent(SlackNotificationEvent event) {
        String channelId = getChannelId(event);
        saveNotificationHistory(event, channelId);

        SlackMessageRequestDto request = new SlackMessageRequestDto(channelId, event.getMessage());

        slackClient.postMessage(request);
    }

    private String getChannelId(SlackNotificationEvent event) {
        String key = event.getType().getPrefix();
        String data = redisService.getData(key);
        if (key.equals(CommandType.ALARM.getPrefix())) {
            return parseAlarmData(data);
        }
        return data;
    }

    private String parseAlarmData(String data) {
        String[] parsedData = data.split(REDIS_ARGUMENTS_SEPARATOR);
        return parsedData[0];
    }

    private void saveNotificationHistory(SlackNotificationEvent event, String channelId) {
        SlackNotificationHistory history = SlackNotificationHistory.builder()
                .channelId(channelId)
                .type(event.getType().name())
                .message(event.getMessage())
                .build();
        historyRepository.save(history);
    }

}
