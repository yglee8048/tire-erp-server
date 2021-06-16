package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.shared.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue(value = "CLIENT")
@Entity
@Table(name = "client")
public class Client extends Account {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    @Embedded
    private Address address;
}
