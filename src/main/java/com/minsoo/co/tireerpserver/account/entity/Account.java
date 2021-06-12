package com.minsoo.co.tireerpserver.account.entity;

import com.minsoo.co.tireerpserver.account.code.AccountRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "account"
        , uniqueConstraints = {@UniqueConstraint(name = "account_username_unique", columnNames = {"username"})})
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

    private Account(String username, String encodedPw) {
        this.username = username;
        this.password = encodedPw;
        this.role = AccountRole.GUEST;
    }

    public static Account of(String userId, String encodedPw) {
        return new Account(userId, encodedPw);
    }

    public Account updatePw(String encodedPw) {
        this.password = encodedPw;
        return this;
    }
}
