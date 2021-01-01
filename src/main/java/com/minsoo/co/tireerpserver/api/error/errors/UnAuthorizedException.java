package com.minsoo.co.tireerpserver.api.error.errors;

import com.minsoo.co.tireerpserver.api.error.BaseError;
import com.minsoo.co.tireerpserver.model.code.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends BaseError {

    public UnAuthorizedException(String error) {
        super(error, HttpStatus.UNAUTHORIZED);
    }

    public UnAuthorizedException() {
        super(Message.UNAUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
