package com.minsoo.co.tireerpserver.api.v1.model.dto.account.admin;

import com.minsoo.co.tireerpserver.services.account.code.AdminRole;
import com.minsoo.co.tireerpserver.services.account.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class AdminResponse {

    @Schema(description = "ID", example = "2937")
    private Long adminId;

    @Schema(description = "아이디", example = "user123")
    private String userId;

    @Schema(description = "이름", example = "김철수")
    private String name;

    @Schema(description = "설명", example = "21년 3월 신규 채용")
    private String description;

    @Schema(description = "역할", example = "SUPER_ADMIN")
    private AdminRole role;

    private AdminResponse(Admin admin) {
        this.adminId = admin.getId();
        this.userId = admin.getUserId();
        this.name = admin.getName();
        this.role = admin.getRole();
    }

    public static AdminResponse of(Admin admin) {
        return new AdminResponse(admin);
    }
}
