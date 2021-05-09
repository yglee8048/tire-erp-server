package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.AccountRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Enumerated(STRING)
    @OneToMany(mappedBy = "account", fetch = EAGER, cascade = ALL)
    private final Set<Authority> authorities = new HashSet<>();

    public Account(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
        this.authorities.add(Authority.of(this, AccountRole.GUEST));
    }

    public static Account of(String userId, String userPw) {
        return new Account(userId, userPw);
    }
}
