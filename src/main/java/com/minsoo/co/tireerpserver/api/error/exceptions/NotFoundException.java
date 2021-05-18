package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("존재하지 않는 자원입니다.");
    }

    public NotFoundException(String error) {
        super(error);
    }

    public NotFoundException(String resource, Long id) {
        super(String.format("존재하지 않는 %s 입니다. (id: %d)", resource, id));
    }
}
