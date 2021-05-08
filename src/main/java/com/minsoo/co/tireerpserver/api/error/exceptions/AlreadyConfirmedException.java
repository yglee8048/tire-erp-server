package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyConfirmedException extends RuntimeException {

    public AlreadyConfirmedException() {
        super("이미 확정되어 수정할 수 없습니다.");
    }

    public AlreadyConfirmedException(String error) {
        super(error);
    }
}
