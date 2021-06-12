package com.minsoo.co.tireerpserver.shared.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.minsoo.co.tireerpserver.account.entity.Account;
import com.minsoo.co.tireerpserver.account.repository.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * BasicAuthenticationFilter: 권한이나 인증이 필요한 url 을 요청하면 실행되는 필터
 * 권한이나 인증이 필요하지 않은 경우 해당 필터는 실행되지 않는다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AccountRepository accountRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if (jwtHeader != null && jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String jwtToken = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(jwtToken)
                    .getClaim("username").asString();

            // 서명이 정상
            if (username != null) {
                Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
                PrincipalDetails principalDetails = new PrincipalDetails(account);
                // JWT 토큰이 유효하면 Authentication 객체를 생성한다.
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                // 강제로 Security 세션에 Authentication 객체를 저장하여 로그인 처리
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
