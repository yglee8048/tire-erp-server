package com.minsoo.co.tireerpserver.model.dto.account.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
