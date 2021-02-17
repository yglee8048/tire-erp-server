package com.minsoo.co.tireerpserver.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

    OK("요청이 성공하였습니다.", HttpStatus.OK),
    SERVER_ERROR("서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("존재하지 않는 자원입니다.", HttpStatus.NOT_FOUND),
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("사용자 인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("해당 작업을 수행할 권한이 부족합니다.", HttpStatus.FORBIDDEN),
    NOT_ENOUGH_STOCK("재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    ALREADY_CONFIRMED("이미 확정되어 수정할 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EMPTY_STOCK("재고가 남아있는 창고/타이어는 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
