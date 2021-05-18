package com.minsoo.co.tireerpserver.api.error;

import com.minsoo.co.tireerpserver.api.error.exceptions.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(Exception e, WebRequest request) {
        return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(AlreadyConfirmedException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(AlreadyConfirmedException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(AlreadyExistException e, WebRequest request) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(BadRequestException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(CanNotDeleteException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(CanNotDeleteException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(ForbiddenException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.FORBIDDEN,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(NotEnoughStockException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(NotFoundException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.NOT_FOUND,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public final ResponseEntity<ApiResponse<String>> exceptionHandler(UnAuthorizedException e, WebRequest request) {
        return ApiResponse.of(HttpStatus.UNAUTHORIZED,
                e.getLocalizedMessage(),
                request.getDescription(false));
    }
}
