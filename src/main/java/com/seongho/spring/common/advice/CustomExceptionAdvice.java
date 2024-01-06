package com.seongho.spring.common.advice;

import com.seongho.spring.common.exception.*;
import com.seongho.spring.common.exception.dto.ExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Custom Exception 처리를 위한 Advice
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice {


    @ExceptionHandler({AuthException.class})
    protected ResponseEntity<ExceptionDto> authException(AuthException ex) {
        log.error("Auth Error occurred: ", ex);
        return createResponseEntity(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
    }

    @ExceptionHandler({DuplicatedException.class})
    protected ResponseEntity<ExceptionDto> duplicatedException(DuplicatedException ex) {
        log.error("Duplicated Error occurred: ", ex);
        return createResponseEntity(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ExceptionDto> notFoundException(NotFoundException ex) {
        log.error("Not Found Error occurred: ", ex);
        return createResponseEntity(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
    }

    @ExceptionHandler({ServiceFailedException.class})
    protected ResponseEntity<ExceptionDto> serviceFailedException(ServiceFailedException ex) {
        log.error("Service Failed Error occurred: ", ex);
        return createResponseEntity(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<ExceptionDto> validatonException(ValidationException ex) {
        log.error("Validation Error occurred: ", ex);
        return createResponseEntity(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
    }

    private ResponseEntity<ExceptionDto> createResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(status.value(), message));
    }
}
