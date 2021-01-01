package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.code.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseError {

    public NotFoundException() {
        super(Message.NOTFOUND.getMessage(), HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
