package com.minsoo.co.tireerpserver.model.account.response;

import com.minsoo.co.tireerpserver.entity.account.Admin;
import com.minsoo.co.tireerpserver.constant.AdminRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponse {

    private Long adminId;
    private String userId;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private AdminRole role;

    public AdminResponse(Admin admin) {
        this.adminId = admin.getId();
        this.userId = admin.getUsername();
        this.description = admin.getDescription();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
        this.role = admin.getRole();
    }
}
