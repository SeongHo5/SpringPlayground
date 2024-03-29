package com.seongho.spring.common.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.seongho.spring.common.constant.AuthConstant.BEARER_PREFIX;


public class HttpEntityUtils {

    private HttpEntityUtils() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<String> createHttpEntity(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken);
        return new HttpEntity<>(headers);
    }
}

