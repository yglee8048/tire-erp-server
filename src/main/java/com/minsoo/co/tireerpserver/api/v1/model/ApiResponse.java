package com.minsoo.co.tireerpserver.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class ApiResponse<T> {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    @Schema(name = "메세지", example = "요청이 성공하였습니다.")
    private String message;

    @Schema(name = "상세 정보", example = "아이디는 필수 값입니다.")
    private String detail;

    @Schema(name = "데이터")
    private T data;

    private ApiResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
    }

    private ApiResponse(HttpStatus status, String message, String detail) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
        this.detail = detail;
    }

    public ApiResponse(HttpStatus status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String error, String message, String detail) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
        this.message = message;
        this.detail = detail;
    }

    public static final ApiResponse<Object> OK = new ApiResponse<>(HttpStatus.OK, "요청이 성공하였습니다.");

    public static <T> ApiResponse<T> OK(T data) {
        return new ApiResponse<>(HttpStatus.OK, "요청이 성공하였습니다.", data);
    }

    public static ResponseEntity<ApiResponse<Object>> CREATED(URI uri) {
        return ResponseEntity.created(uri)
                .body(new ApiResponse<>(HttpStatus.CREATED, "자원이 생성되었습니다.", uri.toString()));
    }

    public static <T> ResponseEntity<ApiResponse<T>> of(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(status, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> of(HttpStatus status, HttpHeaders headers, String message, T data) {
        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(new ApiResponse<>(status, message, data));
    }

    public static ResponseEntity<ApiResponse<Object>> error(HttpStatus status, String error, String message, String detail) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(status, error, message, detail));
    }

    public static ResponseEntity<Object> validError(HttpStatus status, String error, String message, String detail) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(status, error, message, detail));
    }
}
