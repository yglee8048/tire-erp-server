package com.minsoo.co.tireerpserver.entity.account;

import com.minsoo.co.tireerpserver.model.account.request.AdminRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin")
@DiscriminatorValue(AccountType.ADMIN)
public class Admin extends Account {

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AdminRole role;

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
        this.role = adminRequest.getRole();
        return this;
    }
}
