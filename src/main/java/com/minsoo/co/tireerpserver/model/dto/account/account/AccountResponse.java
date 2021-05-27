package com.minsoo.co.tireerpserver.model.dto.account.account;

import com.minsoo.co.tireerpserver.model.entity.entities.account.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class AccountResponse {

    @Schema(description = "ID", example = "2937")
    private Long accountId;

    @Schema(description = "계정 아이디", example = "user123", required = true)
    private String userId;

    @Schema(description = "계정 비밀번호", example = "password1234*", required = true)
    private String userPw;

    public AccountResponse(Account account) {
        this.accountId = account.getId();
        this.userId = account.getUserId();
        this.userPw = account.getUserPw();
    }
}
