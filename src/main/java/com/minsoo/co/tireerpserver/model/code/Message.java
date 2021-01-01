package com.minsoo.co.tireerpserver.model.code;

public enum Message {

    UNEXPECTED("Unexpected error is occurred."),
    NOTFOUND("존재하지 않는 자원입니다."),
    UNAUTHORIZED("사용자 인증에 실패하였습니다."),
    FORBIDDEN("해당 작업을 수행할 권한이 부족합니다."),
    NOT_ENOUGH_STOCK("재고가 부족합니다."),
    ALREADY_CONFIRMED("이미 확정되어 수정할 수 없습니다."),
    NOT_EMPTY_STOCK("재고가 남아있는 창고/타이어는 삭제할 수 없습니다."),
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static String COMPLETE_MESSAGE(String task) {
        return "[ " + task + " ] is completed successfully.";
    }
}
