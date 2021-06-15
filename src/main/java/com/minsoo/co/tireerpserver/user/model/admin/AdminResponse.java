package com.minsoo.co.tireerpserver.user.model.admin;

import com.minsoo.co.tireerpserver.user.code.AdminRole;
import com.minsoo.co.tireerpserver.user.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class AdminResponse {

    @Schema(description = "ID", example = "2937")
    private Long adminId;

    @Schema(description = "account_id", example = "user123")
    private Long accountId;

    @Schema(description = "이름", example = "김철수")
    private String name;

    @Schema(description = "설명", example = "21년 3월 신규 채용")
    private String description;

    @Schema(description = "역할", example = "SUPER_ADMIN")
    private AdminRole role;

    private AdminResponse(Admin admin) {
        this.adminId = admin.getId();
        this.accountId = admin.getAccount().getId();
        this.name = admin.getName();
        this.role = admin.getRole();
    }

    public static AdminResponse of(Admin admin) {
        return new AdminResponse(admin);
    }
}
