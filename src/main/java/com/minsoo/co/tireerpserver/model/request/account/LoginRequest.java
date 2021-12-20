package com.minsoo.co.tireerpserver.model.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LoginRequest {

    @Schema(name = "user_id", description = "계정 아이디")
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;
    
    @Schema(name = "password", description = "계정 비밀번호")
    @NotEmpty(message = "계정 비밀번호는 필수 값입니다.")
    private String password;
}
