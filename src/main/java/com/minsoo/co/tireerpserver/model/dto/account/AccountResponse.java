package com.minsoo.co.tireerpserver.model.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AccountResponse {

    @Schema(name = "ID", example = "2937")
    private Long accountId;

    @Schema(name = "계정 아이디", example = "user123", required = true)
    private String userId;

    @Schema(name = "계정 비밀번호", example = "password1234*", required = true)
    private String userPw;
}
