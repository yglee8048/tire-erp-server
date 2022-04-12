package com.minsoo.co.tireerpserver.constant;

public abstract class SystemMessage {

    public static final String INTERNAL_SERVER_ERROR = "동작 중 예상하지 못 한 오류가 발생했습니다.";

    public static final String NOT_FOUND = "존재하지 않는 데이터입니다.";
    public static final String DISCREPANCY_STOCK_QUANTITY = "재고의 수량이 일치하지 않습니다.";
    public static final String NICKNAME_DUPLICATE = "재고의 별칭은 중복 될 수 없습니다.";
    public static final String ALREADY_CONFIRMED = "이미 확정된 데이터입니다.";
    public static final String INVALID_PASSWORD = "비밀번호가 일치하지 않습니다.";
    public static final String USERNAME_DUPLICATE = "계정의 ID 는 중복 될 수 없습니다.";

    public static final String METHOD_ARGUMENT_INVALID = "잘못된 요청입니다. 누락되었거나, 부적절한 요청 내용이 존재합니다.";
    public static final String STOCK_NOT_SELECTED = "출고 위치가 선택 되지 않았습니다.";
    public static final String NOT_ENOUGH_STOCK = "재고가 부족 합니다.";
    public static final String INVALID_STOCK_REQUEST = "비정상적인 재고 요청 입니다.";
    public static final String INVALID_ROLE = "부적절한 권한 요청 입니다.";

    public static final String USER_NAME_NOT_FOUND = "존재 하지 않는 사용자 입니다.";
    public static final String UNAUTHENTICATED = "인증 되지 않은 사용자 입니다.";
    public static final String FORBIDDEN = "해당 요청에 대한 권한이 없습니다.";

    public static final String OK = "요청이 성공했습니다.";
}
