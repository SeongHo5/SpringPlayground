package com.seongho.spring.crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MarketPriceDto {

    // 마켓명
    @JsonProperty("market")
    String market;

    // 캔들 기준 시각(UTC 기준)
    @JsonProperty("candle_date_time_utc")
    String candleDateTimeUtc;

    // 캔들 기준 시각(KST 기준)
    @JsonProperty("candle_date_time_kst")
    String candleDateTimeKst;

    // 시가
    @JsonProperty("opening_price")
    BigDecimal openingPrice;

    // 고가
    @JsonProperty("high_price")
    BigDecimal highPrice;

    // 저가
    @JsonProperty("low_price")
    BigDecimal lowPrice;

    // 종가
    @JsonProperty("trade_price")
    BigDecimal tradePrice;

    // 해당 캔들에서 마지막 틱이 저장된 시각
    @JsonProperty("timestamp")
    long timestamp;

    // 누적 거래 금액
    @JsonProperty("candle_acc_trade_price")
    BigDecimal candleAccTradePrice;

    // 누적 거래량
    @JsonProperty("candle_acc_trade_volume")
    BigDecimal candleAccTradeVolume;

    // 분 단위(유닛)
    @JsonProperty("unit")
    int unit;
}
