package com.minsoo.co.tireerpserver.model.dto.account.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @Schema(name = "계정 아이디", example = "user123", required = true)
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;

    @Schema(name = "계정 비밀번호", example = "password1234*", required = true)
    @NotEmpty(message = "계정 비밀번호는 필수 값입니다.")
    private String userPw;
}
