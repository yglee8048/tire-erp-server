package com.minsoo.co.tireerpserver.exception;

import com.minsoo.co.tireerpserver.constant.SystemMessage;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {

    public AuthenticationException() {
        super(SystemMessage.UNAUTHENTICATED);
    }
}
