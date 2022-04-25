package com.minsoo.co.tireerpserver.entity.admin;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.constant.AccountType;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Account;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.request.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.request.admin.AdminUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin")
@DiscriminatorValue(AccountType.ADMIN)
public class Admin extends Account {

    public static Admin of(AdminCreateRequest adminCreateRequest, PasswordEncoder passwordEncoder) {
        Admin admin = new Admin();
        return admin.update(new AdminUpdateRequest(adminCreateRequest), passwordEncoder);
    }

    public Admin update(AdminUpdateRequest adminUpdateRequest, PasswordEncoder passwordEncoder) {
        this.username = adminUpdateRequest.getUserId();
        if (StringUtils.hasText(adminUpdateRequest.getPassword())) {
            this.password = passwordEncoder.encode(adminUpdateRequest.getPassword());
        }
        this.description = adminUpdateRequest.getDescription();
        this.name = adminUpdateRequest.getName();
        this.email = adminUpdateRequest.getEmail();
        this.phoneNumber = adminUpdateRequest.getPhoneNumber();
        if (!Arrays.asList(AccountRole.ROOT, AccountRole.ADMIN).contains(adminUpdateRequest.getRole())) {
            throw new BadRequestException(SystemMessage.INVALID_ROLE);
        }
        this.role = adminUpdateRequest.getRole();
        return this;
    }
}
