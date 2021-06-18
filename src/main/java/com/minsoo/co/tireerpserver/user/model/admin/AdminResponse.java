package com.minsoo.co.tireerpserver.user.model.admin;

import com.minsoo.co.tireerpserver.user.code.AdminRole;
import com.minsoo.co.tireerpserver.user.entity.Admin;
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
    private AdminRole role;

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

    public AdminResponse(Admin admin) {
        this.adminId = admin.getId();
        this.userId = admin.getUsername();
        this.role = AdminRole.valueOf(admin.getRole().name());
        this.nickname = admin.getNickname();
        this.description = admin.getDescription();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
    }

    public static AdminResponse of(Admin admin) {
        return new AdminResponse(admin);
    }
}
