package com.minsoo.co.tireerpserver.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {

    public static final ApiResponse<String> DEFAULT_OK = new ApiResponse<>(ApiResponseCode.OK);
    public static final ApiResponse<String> BAD_REQUEST = new ApiResponse<>(ApiResponseCode.BAD_REQUEST);
    public static final ApiResponse<String> DEFAULT_UNAUTHORIZED = new ApiResponse<>(ApiResponseCode.UNAUTHORIZED);

    @ApiModelProperty(value = "응답 코드", example = "OK")
    private ApiResponseCode code;

    @ApiModelProperty(value = "메세지", example = "요청이 성공하였습니다.")
    private String message;

    @ApiModelProperty(value = "데이터")
    private T data;

    private ApiResponse(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponse(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
    }

    public static <T> ApiResponse<T> createOK(T contents) {
        return new ApiResponse<>(ApiResponseCode.OK, contents);
    }
}
