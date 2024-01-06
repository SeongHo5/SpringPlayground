package com.seongho.spring.common.exception;

import com.seongho.spring.common.exception.enums.ExceptionStatus;
import lombok.Getter;

@Getter
public final class ServiceFailedException extends RuntimeException {

    private final int statusCode;
    private final String message;

    public ServiceFailedException(ExceptionStatus ex) {
        this.statusCode = ex.getStatusCode();
        this.message = ex.getMessage();
    }
}
