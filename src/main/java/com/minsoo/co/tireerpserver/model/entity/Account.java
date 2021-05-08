package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.AccountRole;
import com.minsoo.co.tireerpserver.model.entity.base.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Account extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(STRING)
    @Column(name = "role")
    private AccountRole role;

    @Builder
    public Account(String email, String name, AccountRole role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Account update(String name) {
        this.name = name;

        return this;
    }
}
