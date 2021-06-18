package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.user.code.AccountRole;
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
@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(name = "account_username_unique", columnNames = {"username"})})
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id", nullable = false)
    protected Long id;

    @Column(name = "username", nullable = false)
    protected String username;

    @Column(name = "password", nullable = false)
    protected String password;

    @Enumerated(STRING)
    protected AccountRole role;

    @Column(name = "nickname")
    protected String nickname;

    @Column(name = "description")
    protected String description;

    @Column(name = "name")
    protected String name;

    @Column(name = "email")
    protected String email;

    @Column(name = "phone_number")
    protected String phoneNumber;
}
