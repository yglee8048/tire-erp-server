package com.minsoo.co.tireerpserver.model.dto.response;

import com.minsoo.co.tireerpserver.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements EnumType {

    OK("요청이 성공하였습니다."),
    SERVER_ERROR("서버 에러입니다."),
    NOT_FOUND("존재하지 않는 자원입니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    UNAUTHORIZED("사용자 인증에 실패하였습니다."),
    FORBIDDEN("해당 작업을 수행할 권한이 부족합니다."),
    NOT_ENOUGH_STOCK("재고가 부족합니다."),
    ALREADY_CONFIRMED("이미 확정되어 수정할 수 없습니다."),
    NOT_EMPTY_STOCK("재고가 남아있는 창고/타이어는 삭제할 수 없습니다."),
    ;

    private final String message;

    @Override
    public String getId() {
        return name();
    }

    @Override
    public String getText() {
        return message;
    }
}
