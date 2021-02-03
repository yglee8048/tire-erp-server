package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseError {

    public NotFoundException() {
        super(ApiResponseCode.NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
