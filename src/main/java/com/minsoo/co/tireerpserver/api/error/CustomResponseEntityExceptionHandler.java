package com.minsoo.co.tireerpserver.api.error;

import com.minsoo.co.tireerpserver.api.error.exceptions.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(Exception e, WebRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler(AlreadyConfirmedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(AlreadyConfirmedException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(AlreadyExistException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(BadRequestException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(CanNotDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(CanNotDeleteException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(ForbiddenException e, WebRequest request) {
        return errorResponse(HttpStatus.FORBIDDEN, e, request);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(NotEnoughStockException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(NotFoundException e, WebRequest request) {
        return errorResponse(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(UnAuthorizedException e, WebRequest request) {
        return errorResponse(HttpStatus.UNAUTHORIZED, e, request);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ApiResponse.validError(status, ex.getClass().getSimpleName(), "잘못된 요청입니다.",
                ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : ex.getLocalizedMessage());
    }

    private ResponseEntity<ApiResponse<Object>> errorResponse(HttpStatus status, Exception e, WebRequest request) {
        return ApiResponse.error(status, e.getClass().getSimpleName(), e.getLocalizedMessage(), request.getDescription(false));
    }
}
