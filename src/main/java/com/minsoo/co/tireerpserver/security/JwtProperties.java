package com.minsoo.co.tireerpserver.security;

public interface JwtProperties {
    String SECRET = "key";
    int EXPIRATION_TIME = 60000 * 10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
