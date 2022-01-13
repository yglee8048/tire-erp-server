package com.minsoo.co.tireerpserver.utils;

import com.minsoo.co.tireerpserver.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Slf4j
public class SecurityUtils {

    public static String getContextUsername() {
        return getOptionalUsername().orElseThrow(AuthenticationException::new);
    }

    public static Optional<String> getOptionalUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }
}
