package com.minsoo.co.tireerpserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error(authException.getMessage(), authException);

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noStore().getHeaderValue());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> body = ApiResponse.ERROR(status, authException.getClass().getSimpleName(), authException.getMessage(), null);
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}