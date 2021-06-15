package com.minsoo.co.tireerpserver.user.model.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @Schema(name = "user_id", description = "계정 아이디", example = "user123")
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;

    @Schema(name = "user_pw", description = "계정 비밀번호", example = "password1234*")
    @NotEmpty(message = "계정 비밀번호는 필수 값입니다.")
    private String userPw;

    @Schema(name = "nickname", description = "별명", example = "금호1")
    @NotEmpty(message = "별명은 필수 값입니다.")
    private String nickname;
}
