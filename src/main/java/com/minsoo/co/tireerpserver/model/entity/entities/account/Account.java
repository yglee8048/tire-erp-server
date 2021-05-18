package com.minsoo.co.tireerpserver.model.entity.entities.account;

import com.minsoo.co.tireerpserver.model.code.AccountRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"))
    @Column(name = "role", nullable = false)
    private Set<AccountRole> roles = new HashSet<>();

    private Account(String userId, String encodedPw) {
        this.userId = userId;
        this.userPw = encodedPw;
        this.roles = Set.of(AccountRole.GUEST);
    }

    public static Account of(String userId, String encodedPw) {
        return new Account(userId, encodedPw);
    }

    public Account updatePw(String encodedPw) {
        this.userPw = encodedPw;
        return this;
    }

    public Account updateRole(AccountRole role) {
        switch (role) {
            case GUEST:
                this.roles = Set.of(AccountRole.GUEST);
                break;
            case CUSTOMER:
                this.roles = Set.of(AccountRole.GUEST, AccountRole.CUSTOMER);
                break;
            case ADMIN:
                this.roles = Set.of(AccountRole.GUEST, AccountRole.CUSTOMER, AccountRole.ADMIN);
                break;
            case SUPER_ADMIN:
                this.roles = Set.of(AccountRole.GUEST, AccountRole.CUSTOMER, AccountRole.ADMIN, AccountRole.SUPER_ADMIN);
                break;
            default:
                break;
        }
        return this;
    }
}
