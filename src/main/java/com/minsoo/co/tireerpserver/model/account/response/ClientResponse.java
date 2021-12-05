package com.minsoo.co.tireerpserver.model.account.response;

import com.minsoo.co.tireerpserver.constant.ClientRole;
import com.minsoo.co.tireerpserver.entity.account.Client;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientResponse {

    private Long clientId;
    private String userId;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private ClientCompanyResponse clientCompany;
    private ClientRole role;
    private AddressDTO address;

    public ClientResponse(Client client) {
        this.clientId = client.getId();
        this.userId = client.getUsername();
        this.description = client.getDescription();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.clientCompany = new ClientCompanyResponse(client.getClientCompany());
        this.role = client.getRole();
        this.address = new AddressDTO(client.getAddress());
    }
}
