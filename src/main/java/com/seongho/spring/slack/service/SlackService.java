package com.seongho.spring.slack.service;

import com.seongho.spring.common.exception.NoSuchServiceException;
import com.seongho.spring.common.service.RedisService;
import com.seongho.spring.crypto.service.CryptoScheduledService;
import com.seongho.spring.slack.dto.SlackSlashCommandDto;
import com.seongho.spring.slack.entity.SlackCommandHistory;
import com.seongho.spring.slack.enums.CommandType;
import com.seongho.spring.slack.repository.SlackCommandHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.INVALID_INPUT_VALUE;
import static com.seongho.spring.common.exception.enums.ExceptionStatus.NO_SUCH_COMMAND;

@Service
@Transactional
@RequiredArgsConstructor
public class SlackService {

    private final SlackCommandHistoryRepository historyRepository;
    private final CryptoScheduledService cryptoScheduledService;
    private final RedisService redisService;

    public static final String COMMAND_PREFIX = "/";
    public static final String COMMAND_INFO = "info";
    public static final String COMMAND_ALARM = "alarm";
    public static final String REDIS_ARGUMENTS_SEPARATOR = "::";

    public ResponseEntity<String> handleSlashCommand(SlackSlashCommandDto commandDto) {
        saveCommandHistory(commandDto);
        String channelId = commandDto.channelId();
        String parsedCommand = parseCommand(commandDto.command());
        String arguments = commandDto.text();

        return switch (parsedCommand) {
            case COMMAND_INFO -> callInfoService(channelId);
            case COMMAND_ALARM -> callAlarmService(channelId, Integer.parseInt(arguments));
            default -> throw new NoSuchServiceException(NO_SUCH_COMMAND);
        };
    }

    private ResponseEntity<String> callInfoService(String channelId) {
        cacheInfoTask(channelId);
        cryptoScheduledService.startCurrenyInfoTask();
        return ResponseEntity.ok("1분마다 시세 정보를 알려드릴게요 :smile:");
    }

    private ResponseEntity<String> callAlarmService(String channelId, int targetPrice) {
        cacheAlarmTask(channelId, targetPrice);
        cryptoScheduledService.startCurrencyAlarmTask();
        return ResponseEntity.ok("말씀하신 가격에 도달하면 알려드릴게요 :smile:");
    }

    private String parseCommand(String command) {
        if (command.startsWith(COMMAND_PREFIX)) {
            return command.substring(COMMAND_PREFIX.length());
        }
        throw new NoSuchServiceException(INVALID_INPUT_VALUE);
    }

    private void saveCommandHistory(SlackSlashCommandDto commandDto) {
        SlackCommandHistory history = commandDto.toEntity();
        historyRepository.save(history);
    }

    private void cacheInfoTask(String channelId) {
        redisService.setData(CommandType.INFO.getPrefix(), channelId);
    }

    private void cacheAlarmTask(String channelId, int targetPrice) {
        String value = channelId + REDIS_ARGUMENTS_SEPARATOR + targetPrice;
        redisService.setData(CommandType.ALARM.getPrefix(), value);
    }
}
