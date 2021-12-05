package com.minsoo.co.tireerpserver.model.account.response;

import com.minsoo.co.tireerpserver.entity.account.AdminRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponse {

    private Long accountId;
    private String userId;
    private String password;
    private String nickName;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private AdminRole role;
}
