package com.minsoo.co.tireerpserver.entity.admin;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.constant.AccountType;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Account;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.request.admin.AdminRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static Admin of(AdminRequest adminRequest, PasswordEncoder passwordEncoder) {
        Admin admin = new Admin();
        return admin.update(adminRequest, passwordEncoder);
    }

    public Admin update(AdminRequest adminRequest, PasswordEncoder passwordEncoder) {
        this.username = adminRequest.getUserId();
        this.password = passwordEncoder.encode(adminRequest.getPassword());
        this.description = adminRequest.getDescription();
        this.name = adminRequest.getName();
        this.email = adminRequest.getEmail();
        this.phoneNumber = adminRequest.getPhoneNumber();
        if (!Arrays.asList(AccountRole.ROOT, AccountRole.ADMIN).contains(adminRequest.getRole())) {
            throw new BadRequestException(SystemMessage.INVALID_ROLE);
        }
        this.role = adminRequest.getRole();
        return this;
    }
}
