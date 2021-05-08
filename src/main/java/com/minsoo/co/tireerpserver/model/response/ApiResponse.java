package com.minsoo.co.tireerpserver.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiResponse<T> {

    @ApiModelProperty(value = "상태 코드", example = "OK")
    private final HttpStatus status;

    @ApiModelProperty(value = "메세지", example = "요청이 성공하였습니다.")
    private final String message;

    @ApiModelProperty(value = "상세 정보", example = "아이디는 필수 값입니다.")
    private String detail;

    @ApiModelProperty(value = "데이터")
    private T data;

    private ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private ApiResponse(HttpStatus status, String message, String detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static final ApiResponse<String> DEFAULT_OK = new ApiResponse<>(HttpStatus.OK, "요청이 성공하였습니다.");

    public static <T> ApiResponse<T> createOK(T data) {
        return new ApiResponse<>(HttpStatus.OK, "요청이 성공하였습니다.", data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, String detail) {
        return new ApiResponse<>(status, message, detail);
    }
}
