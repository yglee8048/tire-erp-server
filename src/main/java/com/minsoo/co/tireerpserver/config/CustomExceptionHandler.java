package com.minsoo.co.tireerpserver.config;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.InternalServerException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@Slf4j
@RestController
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> exceptionHandler(Exception e, WebRequest request) {
        log.error("An unexpected exception occurred: {}", e.getMessage(), e);
        return ApiResponse.ERROR(HttpStatus.INTERNAL_SERVER_ERROR, InternalServerException.class.getSimpleName(), SystemMessage.INTERNAL_SERVER_ERROR, request.getDescription(false));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> exceptionHandler(BadRequestException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> exceptionHandler(NotFoundException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> exceptionHandler(UsernameNotFoundException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> exceptionHandler(AuthenticationException e, WebRequest request) {
        return ApiResponse.ERROR(HttpStatus.UNAUTHORIZED, e.getClass().getSimpleName(), e.getMessage(), request.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.ERROR(status, ex.getMessage(),
                        Optional.ofNullable(ex.getBindingResult().getAllErrors().get(0))
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .orElse(SystemMessage.METHOD_ARGUMENT_INVALID),
                        request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }
}
