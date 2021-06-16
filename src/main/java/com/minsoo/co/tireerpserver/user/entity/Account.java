package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.service.AccountService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "account_type")
@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(name = "account_username_unique", columnNames = {"username"})})
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(STRING)
    private AccountRole role;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Account(AdminRequest adminRequest, AccountService accountService) {
        this.username = adminRequest.getUserId();
        this.password = accountService.createPassword();
        this.role = adminRequest.getRole();
        this.nickname = adminRequest.getNickname();
        this.description = adminRequest.getDescription();
        this.name = adminRequest.getName();
        this.email = adminRequest.getEmail();
        this.phoneNumber = adminRequest.getPhoneNumber();
    }
}
