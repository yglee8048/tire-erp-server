package com.minsoo.co.tireerpserver.model.dto.admin;

import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AdminResponse {

    @Schema(name = "ID", example = "2937")
    private Long adminId;

    @Schema(name = "아이디", example = "user123")
    private String userId;

    @Schema(name = "이름", example = "김철수")
    private String name;

    @Schema(name = "설명", example = "21년 3월 신규 채용")
    private String description;

    @Schema(name = "역할", example = "SUPER_ADMIN")
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
