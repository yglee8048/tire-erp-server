package com.minsoo.co.tireerpserver.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsoo.co.tireerpserver.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 인증이 시도되면(/login) 실행되는 메서드
     * username 과 password 를 통해 인증을 시도한다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 1. username 과 password 읽는다.
            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.readValue(request.getInputStream(), Account.class);
            // 2. login 을 시도한다.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
            // 3. 리턴하여 세션에 저장한다: 리턴 시 session 에 저장된다. (권한 처리를 위해 저장)
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException ioe) {
            log.error(ioe.getLocalizedMessage());
        }
        return null;
    }

    /**
     * attemptAuthentication 메서드를 통해 인증이 성공하면 실행되는 메서드
     * JWT 토큰을 만들어서 토큰을 헤더에 넣어 response 해준다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getAccount().getId())
                .withClaim("username", principalDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
