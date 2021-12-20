package com.minsoo.co.tireerpserver.model.response.account;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.entity.account.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminResponse {

    private Long adminId;
    private String userId;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private AccountRole role;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public AdminResponse(Admin admin) {
        this.adminId = admin.getId();
        this.userId = admin.getUsername();
        this.description = admin.getDescription();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
        this.role = admin.getRole();

        this.createdAt = admin.getCreatedAt();
        this.lastModifiedAt = admin.getLastModifiedAt();
        this.createdBy = admin.getCreatedBy();
        this.lastModifiedBy = admin.getLastModifiedBy();
    }
}
