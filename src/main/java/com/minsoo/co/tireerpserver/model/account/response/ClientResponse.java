package com.minsoo.co.tireerpserver.model.account.response;

import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientResponse {

    private Long accountId;
    private String userId;
    private String password;
    private String nickName;
    private String description;
    private String name;
    private String email;
    private String phoneNumber;
    private ClientCompanyResponse clientCompany;
    private AddressDTO address;
}
