package com.minsoo.co.tireerpserver.model.response.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
