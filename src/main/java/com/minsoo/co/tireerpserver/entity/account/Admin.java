package com.minsoo.co.tireerpserver.entity.account;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
