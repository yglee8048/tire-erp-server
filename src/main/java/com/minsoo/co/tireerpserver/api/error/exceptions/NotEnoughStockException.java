package com.minsoo.co.tireerpserver.api.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super("재고가 부족합니다.");
    }

    public NotEnoughStockException(String error) {
        super(error);
    }
}
