package com.minsoo.co.tireerpserver.shared.error;

import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.shared.error.exceptions.*;
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
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(Exception e, WebRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler(AlreadyConfirmedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(AlreadyConfirmedException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(AlreadyExistException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(BadRequestException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(CanNotDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(CanNotDeleteException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(ForbiddenException e, WebRequest request) {
        return errorResponse(HttpStatus.FORBIDDEN, e, request);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(NotEnoughStockException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(NotFoundException e, WebRequest request) {
        return errorResponse(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(UnAuthorizedException e, WebRequest request) {
        return errorResponse(HttpStatus.UNAUTHORIZED, e, request);
    }

    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiResponse<Void>> exceptionHandler(InvalidStateException e, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ApiResponse.validError(status, ex.getClass() != null ? ex.getClass().getSimpleName() : status.name(),
                ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : ex.getLocalizedMessage(),
                request.getDescription(false));
    }

    private ResponseEntity<ApiResponse<Void>> errorResponse(HttpStatus status, Exception e, WebRequest request) {
        return ApiResponse.error(status, e.getClass().getSimpleName(), e.getLocalizedMessage(), request.getDescription(false));
    }
}
