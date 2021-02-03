package com.minsoo.co.tireerpserver.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ApiResponseDTO<T> {

    public static final ApiResponseDTO<String> DEFAULT_OK = new ApiResponseDTO<>(ApiResponseCode.OK);
    public static final ApiResponseDTO<String> DEFAULT_UNAUTHORIZED = new ApiResponseDTO<>(ApiResponseCode.UNAUTHORIZED);

    private ApiResponseCode code;
    private String message;
    private T contents;

    private ApiResponseDTO(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponseDTO(ApiResponseCode status, T contents) {
        this.bindStatus(status);
        this.contents = contents;
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
    }

    public static <T> ApiResponseDTO<T> createOK(T contents) {
        return new ApiResponseDTO<>(ApiResponseCode.OK, contents);
    }
}
