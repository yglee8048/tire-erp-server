package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyConfirmedException extends BaseError {


    public AlreadyConfirmedException() {
        super(ApiResponseCode.ALREADY_CONFIRMED.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public AlreadyConfirmedException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
