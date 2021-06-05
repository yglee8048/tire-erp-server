package com.minsoo.co.tireerpserver.api.v1.model.dto.account.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @Schema(description = "계정 아이디", example = "user123", required = true)
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;

    @Schema(description = "계정 비밀번호", example = "password1234*", required = true)
    @NotEmpty(message = "계정 비밀번호는 필수 값입니다.")
    private String userPw;
}
