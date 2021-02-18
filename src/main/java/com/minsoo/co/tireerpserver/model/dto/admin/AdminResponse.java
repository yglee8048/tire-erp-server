package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class AdminResponse {

    @ApiModelProperty(value = "ID", example = "2937")
    @JsonProperty("admin_id")
    private Long adminId;

    @ApiModelProperty(value = "아이디", example = "user123")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty(value = "이름", example = "김철수")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "역할", example = "SUPER_ADMIN")
    @JsonProperty("role")
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
