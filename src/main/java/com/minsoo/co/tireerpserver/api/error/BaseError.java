package com.minsoo.co.tireerpserver.api.error;

import org.springframework.http.HttpStatus;

public abstract class BaseError extends RuntimeException {

    private final String error;

    private final HttpStatus httpStatus;

    public BaseError(String error, HttpStatus httpStatus) {
        super(error);
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
