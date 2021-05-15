package com.minsoo.co.tireerpserver.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@Getter
@ToString
public class ApiResponse<T> {

    @Schema(name = "메세지", example = "요청이 성공하였습니다.")
    private final String message;

    @Schema(name = "상세 정보", example = "아이디는 필수 값입니다.")
    private String detail;

    @Schema(name = "데이터")
    private T data;

    private ApiResponse(String message) {
        this.message = message;
    }

    private ApiResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static final ResponseEntity<ApiResponse<String>> OK = ResponseEntity.ok(new ApiResponse<>("요청이 성공하였습니다."));

    public static <T> ResponseEntity<ApiResponse<T>> OK(T data) {
        return ResponseEntity.ok(new ApiResponse<>("요청이 성공하였습니다.", data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> CREATED(URI uri) {
        return ResponseEntity.created(uri)
                .body(new ApiResponse<>("자원이 생성되었습니다."));
    }

    public static <T> ResponseEntity<ApiResponse<T>> of(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> of(HttpStatus status, String message, String detail) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(message, detail));
    }
}
