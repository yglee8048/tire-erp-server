package com.minsoo.co.tireerpserver.exception;

import com.minsoo.co.tireerpserver.constant.SystemMessage;

public class InternalServerException extends RuntimeException {

    public InternalServerException() {
        super(SystemMessage.INTERNAL_SERVER_ERROR);
    }
}
