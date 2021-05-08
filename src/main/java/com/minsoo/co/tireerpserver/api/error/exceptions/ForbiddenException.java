package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("해당 작업을 수행할 권한이 부족합니다.");
    }

    public ForbiddenException(String error) {
        super(error);
    }
}
