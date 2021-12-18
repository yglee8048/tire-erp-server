package com.minsoo.co.tireerpserver.model.request.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
