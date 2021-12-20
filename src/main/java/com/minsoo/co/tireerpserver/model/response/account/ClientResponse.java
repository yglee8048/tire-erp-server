package com.minsoo.co.tireerpserver.model.response.account;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import com.minsoo.co.tireerpserver.entity.account.Client;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ClientResponse {

    private Long clientId;
    private Long clientCompanyId;
    private String userId;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private AccountRole role;
    private AddressDTO address;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public ClientResponse(Client client) {
        this.clientId = client.getId();
        this.clientCompanyId = client.getClientCompany().getId();
        this.userId = client.getUsername();
        this.description = client.getDescription();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.role = client.getRole();
        this.address = new AddressDTO(client.getAddress());

        this.createdAt = client.getCreatedAt();
        this.lastModifiedAt = client.getLastModifiedAt();
        this.createdBy = client.getCreatedBy();
        this.lastModifiedBy = client.getLastModifiedBy();
    }
}
