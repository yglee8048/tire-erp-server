package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

    public InternalServerException() {
        super("서버에서 에러가 발생했습니다.");
    }

    public InternalServerException(String error) {
        super(error);
    }
}
