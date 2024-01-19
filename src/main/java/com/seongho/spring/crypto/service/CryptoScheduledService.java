package com.seongho.spring.crypto.service;

import com.seongho.spring.common.service.RedisService;
import com.seongho.spring.common.service.SchedulingService;
import com.seongho.spring.crypto.dto.MarketPriceDto;
import com.seongho.spring.slack.enums.CommandType;
import com.seongho.spring.slack.event.SlackNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static com.seongho.spring.slack.enums.CommandType.ALARM;
import static com.seongho.spring.slack.enums.CommandType.INFO;
import static com.seongho.spring.slack.service.SlackService.REDIS_ARGUMENTS_SEPARATOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoScheduledService {

    private final RedisService redisService;
    private final SchedulingService schedulingService;
    private final UpbitFeignClient upbitClient;
    private final ApplicationEventPublisher eventPublisher;
    public static final String MARKET_BTC = "KRW-BTC";

    public void startCurrenyInfoTask() {
        schedulingService.startScheduledTask(this::fetchCurrencyInfo, Duration.ofMinutes(1));
        publishNotificationEvent(INFO, "1분마다 시세 정보를 알려드릴게요 :smile:");
    }

    public void startCurrencyAlarmTask() {
        schedulingService.startScheduledTask(this::fetchTradePrice, Duration.ofMinutes(1));
        publishNotificationEvent(ALARM, "말씀하신 가격에 도달하면 알려드릴게요 :smile:");
    }

    // ========== PRIVATE METHODS ========== //

    private void fetchTradePrice() {
        List<MarketPriceDto> response = upbitClient.getCandlesMinutes(1, MARKET_BTC, 1);
        BigDecimal tradePrice = response.get(0).getTradePrice();
        checkIfReachedTargetPrice(tradePrice);
    }

    private void fetchCurrencyInfo() {
        List<MarketPriceDto> response = upbitClient.getCandlesMinutes(1, MARKET_BTC, 1);
        BigDecimal tradePrice = response.get(0).getTradePrice();
        String message = "현재가: " + tradePrice;
        publishNotificationEvent(INFO, message);
    }

    private void checkIfReachedTargetPrice(BigDecimal tradePrice) {
        String data = redisService.getData(CommandType.ALARM.getPrefix());
        String targetPrice = parseAlarmData(data);

        boolean isReached = tradePrice.compareTo(new BigDecimal(targetPrice)) >= 0;
        if (isReached) {
            String message = "목표가 도달! 현재가: " + tradePrice + ", 목표가: " + targetPrice;
            publishNotificationEvent(ALARM, message);
        }
    }

    private String parseAlarmData(String data) {
        String[] parsedData = data.split(REDIS_ARGUMENTS_SEPARATOR);
        return parsedData[1];
    }

    private void publishNotificationEvent(CommandType type, String message) {
        eventPublisher.publishEvent(new SlackNotificationEvent(this, type, message));
    }
}

