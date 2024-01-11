package com.seongho.spring.common.advice;


import com.seongho.spring.common.exception.dto.ExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Exception 처리를 위한 Advice
 * <p>
 * 자세한 로그는 서버 로그로만 기록하고,
 * <p>
 * 클라이언트에게는 상태 코드와 간단한 메세지만 전달하도록 구성한다.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    /**
     * 파라미터가 유효하지 않을 때(조건에 맞지 않을 때) 발생하는 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionDto> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        StringBuilder errorMessage = new StringBuilder("Parameter Condition Not Satisfied: ");
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
        }

        log.error(errorMessage.toString());
        return createResponseEntity(HttpStatus.BAD_REQUEST, errorMessage.toString());
    }

    /**
     * 필수 파라미터가 누락되었을 때 발생하는 예외
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ExceptionDto> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String errorMessage = "Parameter [" + ex.getParameterName() + "] Is Required";

        log.error(errorMessage);
        return createResponseEntity(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * 지원하지 않는 HTTP Method를 통해 API가 호출되었을 때 발생하는 예외
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ExceptionDto> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        String supportedMethods = Arrays.toString(Objects.requireNonNull(ex.getSupportedMethods()));

        errorMessageBuilder.append("Method [").append(ex.getMethod()).append("] Is Not Supported");

        if (!supportedMethods.isEmpty()) {
            errorMessageBuilder.append(", Use ").append(supportedMethods).append(" Instead");
        }
        String errorMessage = errorMessageBuilder.toString();

        log.error("Unsupported HTTP Method Error occurred: ", ex);
        return createResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
    }

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity<ExceptionDto> nullPointerException(NullPointerException ex) {
        log.error("NullPointerException occurred: ", ex);
        return createResponseEntity(HttpStatus.NOT_FOUND, "Not Found / Please Contact to Admin");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Cannot Read Request Body: ", ex);
        return createResponseEntity(HttpStatus.BAD_REQUEST, "Request body is not readable or invalid format");
    }

    private ResponseEntity<ExceptionDto> createResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(status.value(), message));
    }

}
