package com.minsoo.co.tireerpserver.user.model.account;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class AccountResponse {

    @Schema(name = "account_id")
    private Long accountId;

    @Schema(name = "user_id")
    private String userId;

    @Schema(name = "role")
    private AccountRole role;

    @Schema(name = "nickname")
    private String nickname;

    public AccountResponse(Account account) {
        this.accountId = account.getId();
        this.userId = account.getUsername();
        this.role = account.getRole();
        this.nickname = account.getNickname();
    }
}
