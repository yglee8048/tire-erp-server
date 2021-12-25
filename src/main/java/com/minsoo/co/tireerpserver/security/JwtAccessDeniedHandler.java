package com.minsoo.co.tireerpserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);

        //필요한 권한이 없이 접근하려 할때 403
        HttpStatus status = HttpStatus.FORBIDDEN;

        response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noStore().getHeaderValue());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> body = ApiResponse.ERROR(status, accessDeniedException.getClass().getSimpleName(), accessDeniedException.getMessage(), null);
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
