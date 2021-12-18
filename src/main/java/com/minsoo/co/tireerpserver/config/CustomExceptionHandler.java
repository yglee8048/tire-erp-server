package com.minsoo.co.tireerpserver.config;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.InternalServerException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> exceptionHandler(WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.INTERNAL_SERVER_ERROR, InternalServerException.class.getSimpleName(), SystemMessage.INTERNAL_SERVER_ERROR, request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> exceptionHandler(BadRequestException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> exceptionHandler(NotFoundException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }
}
