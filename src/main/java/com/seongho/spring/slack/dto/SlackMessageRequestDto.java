package com.seongho.spring.slack.dto;


public record SlackMessageRequestDto(String channel, String text) {
}
