package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.model.account.AccountRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
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

    private Account(String username, String encodedPw, String nickname) {
        this.username = username;
        this.password = encodedPw;
        this.role = AccountRole.GUEST;
        this.nickname = nickname;
    }

    public static Account of(AccountRequest accountRequest, PasswordEncoder passwordEncoder) {
        return new Account(accountRequest.getUserId(), passwordEncoder.encode(accountRequest.getUserPw()), accountRequest.getNickname());
    }

    public Account updatePw(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }
}
