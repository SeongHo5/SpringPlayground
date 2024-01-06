package com.seongho.spring.common.exception;

import com.seongho.spring.common.exception.enums.ExceptionStatus;
import lombok.Getter;
@Getter
public final class NotFoundException extends RuntimeException {

    private final int statusCode;
    private final String message;

    public NotFoundException(ExceptionStatus status) {
        this.statusCode = status.getStatusCode();
        this.message = status.getMessage();
    }
}
