package com.seongho.spring.common.jwt.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {

}
