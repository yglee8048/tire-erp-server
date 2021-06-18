package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.service.AccountService;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue(value = "ADMIN")
@Entity
@Table(name = "admin")
public class Admin extends Account {

    public Admin(AdminRequest adminRequest, AccountService accountService) {
        this.username = adminRequest.getUserId();
        this.password = accountService.createPassword();
        this.role = AccountRole.valueOf(adminRequest.getRole().name());
        this.nickname = adminRequest.getNickname();
        this.description = adminRequest.getDescription();
        this.name = adminRequest.getName();
        this.email = adminRequest.getEmail();
        this.phoneNumber = adminRequest.getPhoneNumber();
    }

    public static Admin of(AdminRequest adminRequest, AccountService accountService) {
        return new Admin(adminRequest, accountService);
    }

    public Admin update(AdminRequest adminRequest) {
        this.username = adminRequest.getUserId();
        this.role = AccountRole.valueOf(adminRequest.getRole().name());
        this.nickname = adminRequest.getNickname();
        this.description = adminRequest.getDescription();
        this.name = adminRequest.getName();
        this.email = adminRequest.getEmail();
        this.phoneNumber = adminRequest.getPhoneNumber();

        return this;
    }

    public Admin updatePw(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);

        return this;
    }
}
