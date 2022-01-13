package com.minsoo.co.tireerpserver.entity.client;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.constant.AccountType;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.Address;
import com.minsoo.co.tireerpserver.entity.account.Account;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.request.client.ClientRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
@DiscriminatorValue(AccountType.CLIENT)
public class Client extends Account {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id", referencedColumnName = "client_company_id")
    private ClientCompany clientCompany;

    @Embedded
    private Address address;

    private Client(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public static Client of(ClientCompany clientCompany, ClientRequest clientRequest, PasswordEncoder passwordEncoder) {
        Client client = new Client(clientCompany);
        return client.update(clientRequest, passwordEncoder);
    }

    public Client update(ClientRequest clientRequest, PasswordEncoder passwordEncoder) {
        this.username = clientRequest.getUserId();
        this.password = passwordEncoder.encode(clientRequest.getPassword());
        this.description = clientRequest.getDescription();
        this.name = clientRequest.getName();
        this.email = clientRequest.getEmail();
        this.phoneNumber = clientRequest.getPhoneNumber();
        if (!Arrays.asList(AccountRole.CLIENT, AccountRole.GUEST).contains(clientRequest.getRole())) {
            throw new BadRequestException(SystemMessage.INVALID_ROLE);
        }
        this.role = clientRequest.getRole();
        this.address = new Address(clientRequest.getAddress());
        return this;
    }
}
