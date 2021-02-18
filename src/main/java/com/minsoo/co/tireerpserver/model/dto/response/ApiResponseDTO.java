package com.minsoo.co.tireerpserver.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponseDTO<T> {

    public static final ApiResponseDTO<String> DEFAULT_OK = new ApiResponseDTO<>(ApiResponseCode.OK);
    public static final ApiResponseDTO<String> BAD_REQUEST = new ApiResponseDTO<>(ApiResponseCode.BAD_REQUEST);
    public static final ApiResponseDTO<String> DEFAULT_UNAUTHORIZED = new ApiResponseDTO<>(ApiResponseCode.UNAUTHORIZED);

    @ApiModelProperty(value = "응답 코드", example = "OK")
    private ApiResponseCode code;

    @ApiModelProperty(value = "메세지", example = "요청이 성공하였습니다.")
    private String message;

    @ApiModelProperty(value = "데이터")
    private T data;

    private ApiResponseDTO(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponseDTO(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
    }

    public static <T> ApiResponseDTO<T> createOK(T contents) {
        return new ApiResponseDTO<>(ApiResponseCode.OK, contents);
    }
}
