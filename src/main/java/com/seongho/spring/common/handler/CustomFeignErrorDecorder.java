package com.seongho.spring.common.handler;

import com.seongho.spring.common.exception.ServiceFailedException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.*;

public class CustomFeignErrorDecorder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (isServerError(response)) {
            handleRetryAfterHeader(response);
            throw new ServiceFailedException(EXTERNAL_API_ERROR);
        }
        if (isClientError(response)) {
            throw new ServiceFailedException(FAILED_HTTP_REQUEST);
        }
        throw new ServiceFailedException(UNKNOWN_ERROR);
    }

    private boolean isClientError(Response response) {
        return response.status() >= 400 && response.status() <= 499;
    }

    private boolean isServerError(Response response) {
        return response.status() >= 500 && response.status() <= 599;
    }

    private void handleRetryAfterHeader(Response response) {
        if (response.headers().containsKey("Retry-After")) {
            String retryAfter = response.headers().get("Retry-After").iterator().next();
            throw new RetryableException(response.status(), response.reason(), response.request().httpMethod(), Long.parseLong(retryAfter), response.request());
        }
    }

}
