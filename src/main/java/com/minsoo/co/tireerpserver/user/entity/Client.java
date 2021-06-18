package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.shared.entity.Address;
import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.model.client.ClientRequest;
import com.minsoo.co.tireerpserver.user.service.AccountService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "client")
public class Client extends Account {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    @Embedded
    private Address address;

    public Client(ClientRequest clientRequest, ClientCompany clientCompany, AccountService accountService) {
        this.username = clientRequest.getUserId();
        this.password = accountService.createPassword();
        this.role = AccountRole.valueOf(clientRequest.getRole().name());
        this.nickname = clientRequest.getNickname();
        this.description = clientRequest.getDescription();
        this.name = clientRequest.getName();
        this.email = clientRequest.getEmail();
        this.phoneNumber = clientRequest.getPhoneNumber();
        this.clientCompany = clientCompany;
        this.address = Address.of(clientRequest.getAddress());
    }

    public static Client of(ClientRequest clientRequest, ClientCompany clientCompany, AccountService accountService) {
        return new Client(clientRequest, clientCompany, accountService);
    }

    public Client update(ClientRequest clientRequest, ClientCompany clientCompany) {
        this.username = clientRequest.getUserId();
        this.role = AccountRole.valueOf(clientRequest.getRole().name());
        this.nickname = clientRequest.getNickname();
        this.description = clientRequest.getDescription();
        this.name = clientRequest.getName();
        this.email = clientRequest.getEmail();
        this.phoneNumber = clientRequest.getPhoneNumber();
        this.clientCompany = clientCompany;
        this.address = Address.of(clientRequest.getAddress());

        return this;
    }
}
