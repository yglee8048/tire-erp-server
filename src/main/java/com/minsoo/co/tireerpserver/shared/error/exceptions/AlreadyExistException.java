package com.minsoo.co.tireerpserver.shared.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException() {
        super("이미 존재하는 자원입니다.");
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
