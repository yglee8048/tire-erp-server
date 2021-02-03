package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseError {

    public ForbiddenException(String error) {
        super(error, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException() {
        super(ApiResponseCode.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
}
