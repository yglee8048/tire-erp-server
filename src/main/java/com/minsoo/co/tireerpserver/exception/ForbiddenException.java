package com.minsoo.co.tireerpserver.exception;

import com.minsoo.co.tireerpserver.constant.SystemMessage;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super(SystemMessage.FORBIDDEN);
    }
}
