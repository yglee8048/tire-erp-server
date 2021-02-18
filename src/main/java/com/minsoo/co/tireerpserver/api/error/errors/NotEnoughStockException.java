package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStockException extends BaseError {

    public NotEnoughStockException() {
        super(ApiResponseCode.NOT_ENOUGH_STOCK.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public NotEnoughStockException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
