package com.minsoo.co.tireerpserver.api.error;

import com.minsoo.co.tireerpserver.api.error.exceptions.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(Exception e, WebRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler(AlreadyConfirmedException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(AlreadyConfirmedException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(AlreadyExistException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(BadRequestException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(CanNotDeleteException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(CanNotDeleteException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(ForbiddenException e, WebRequest request) {
        return errorResponse(HttpStatus.FORBIDDEN, e, request);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(NotEnoughStockException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(NotFoundException e, WebRequest request) {
        return errorResponse(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public final ResponseEntity<ApiResponse<Object>> exceptionHandler(UnAuthorizedException e, WebRequest request) {
        return errorResponse(HttpStatus.UNAUTHORIZED, e, request);
    }

    private ResponseEntity<ApiResponse<Object>> errorResponse(HttpStatus status, Exception e, WebRequest request) {
        return ApiResponse.error(status, e.getClass().getSimpleName(), e.getLocalizedMessage(), request.getDescription(false));
    }
}
