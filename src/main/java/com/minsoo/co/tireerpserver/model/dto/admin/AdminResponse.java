package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import lombok.Data;

@Data
public class AdminResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private AdminRole role;

    private AdminResponse(Admin admin) {
        this.id = admin.getId();
        this.userId = admin.getUserId();
        this.name = admin.getName();
        this.role = admin.getRole();
    }

    public static AdminResponse of(Admin admin) {
        return new AdminResponse(admin);
    }
}
