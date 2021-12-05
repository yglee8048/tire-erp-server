package com.minsoo.co.tireerpserver.entity.account;

import com.minsoo.co.tireerpserver.constant.AccountType;
import com.minsoo.co.tireerpserver.constant.ClientRole;
import com.minsoo.co.tireerpserver.entity.Address;
import com.minsoo.co.tireerpserver.model.account.request.ClientRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
@DiscriminatorValue(AccountType.CLIENT)
public class Client extends Account {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id", referencedColumnName = "client_company_id")
    private ClientCompany clientCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ClientRole role;

    @Embedded
    private Address address;

    public static Client of(ClientCompany clientCompany, ClientRequest clientRequest, PasswordEncoder passwordEncoder) {
        Client client = new Client();
        return client.update(clientCompany, clientRequest, passwordEncoder);
    }

    public Client update(ClientCompany clientCompany, ClientRequest clientRequest, PasswordEncoder passwordEncoder) {
        this.username = clientRequest.getUserId();
        this.password = passwordEncoder.encode(clientRequest.getPassword());
        this.description = clientRequest.getDescription();
        this.name = clientRequest.getName();
        this.email = clientRequest.getEmail();
        this.phoneNumber = clientRequest.getPhoneNumber();
        this.clientCompany = clientCompany;
        this.role = clientRequest.getRole();
        this.address = new Address(clientRequest.getAddress());
        return this;
    }
}
