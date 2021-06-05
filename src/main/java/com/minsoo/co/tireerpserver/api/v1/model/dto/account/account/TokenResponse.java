package com.minsoo.co.tireerpserver.api.v1.model.dto.account.account;

import lombok.*;

@Data
@NoArgsConstructor
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
