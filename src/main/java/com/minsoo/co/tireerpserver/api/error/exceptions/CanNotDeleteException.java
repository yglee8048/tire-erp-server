package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CanNotDeleteException extends RuntimeException {

    public CanNotDeleteException() {
        super("관련 데이터가 존재하여 삭제할 수 없습니다.");
    }

    public CanNotDeleteException(String error) {
        super(error);
    }
}
