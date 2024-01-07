package com.seongho.spring.common.jwt.dto;


import lombok.Builder;

@Builder
public record JwtReissueResponseDto(String accessToken, String refreshToken, Long accessTokenExpiresIn) {

}
