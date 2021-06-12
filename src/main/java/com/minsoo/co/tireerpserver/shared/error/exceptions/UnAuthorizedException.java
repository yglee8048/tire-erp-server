package com.minsoo.co.tireerpserver.shared.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        super("사용자 인증에 실패하였습니다.");
    }

    public UnAuthorizedException(String error) {
        super(error);
    }
}
