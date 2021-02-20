package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CanNotDeleteException extends BaseError {

    public CanNotDeleteException() {
        super(ApiResponseCode.CAN_NOT_DELETE.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public CanNotDeleteException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
