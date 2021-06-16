package com.minsoo.co.tireerpserver.user.model.admin;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponse {

    @Schema(name = "admin_id")
    private Long adminId;

    @Schema(name = "user_id", description = "계정 아이디")
    private String userId;

    @Schema(name = "role", description = "권한")
    private AccountRole role;

    @Schema(name = "nickname", description = "닉네임")
    private String nickname;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "name", description = "이름")
    private String name;

    @Schema(name = "email", description = "이메일")
    private String email;

    @Schema(name = "phone_number", description = "핸드폰 번호")
    private String phoneNumber;
}
