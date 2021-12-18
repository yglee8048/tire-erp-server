package com.minsoo.co.tireerpserver.entity.account;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.constant.AccountType;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.Address;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.request.account.ClientRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
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
        if (!Arrays.asList(AccountRole.CLIENT, AccountRole.GUEST).contains(clientRequest.getRole())) {
            throw new BadRequestException(SystemMessage.INVALID_ROLE);
        }
        this.role = clientRequest.getRole();
        this.address = new Address(clientRequest.getAddress());
        return this;
    }
}
