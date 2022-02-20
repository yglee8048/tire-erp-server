package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.utils.DateTimeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String detail;
    private T data;

    private ApiResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    private ApiResponse(HttpStatus status, String message, T data) {
        this(status, null, message, null, data);
    }

    private ApiResponse(HttpStatus status, String error, String message, String detail) {
        this(status, error, message, detail, null);
    }

    private ApiResponse(HttpStatus status, String error, String message, String detail, T data) {
        this.timestamp = DateTimeUtils.toString(LocalDateTime.now());
        this.status = status.value();
        this.error = error;
        this.message = message;
        this.detail = detail;
        this.data = data;
    }

    public static ApiResponse<Void> NO_CONTENT() {
        return new ApiResponse<>(HttpStatus.OK, SystemMessage.OK);
    }

    public static <T> ApiResponse<T> OK(T data) {
        return new ApiResponse<>(HttpStatus.OK, SystemMessage.OK, data);
    }

    public static <T> ApiResponse<T> CREATED(T data) {
        return new ApiResponse<>(HttpStatus.CREATED, SystemMessage.OK, data);
    }

    public static ApiResponse<Void> ERROR(HttpStatus status, String error, String message, String detail) {
        return new ApiResponse<>(status, error, message, detail);
    }
}
