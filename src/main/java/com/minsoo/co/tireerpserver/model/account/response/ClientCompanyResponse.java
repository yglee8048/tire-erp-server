package com.minsoo.co.tireerpserver.model.account.response;

import com.minsoo.co.tireerpserver.entity.account.ClientCompany;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientCompanyResponse {

    private Long clientCompanyId;
    private String name;
    private String description;
    private BusinessInfoDTO businessInfo;

    public ClientCompanyResponse(ClientCompany clientCompany) {
        this.clientCompanyId = clientCompany.getId();
        this.name = clientCompany.getName();
        this.description = clientCompany.getDescription();
        this.businessInfo = new BusinessInfoDTO(clientCompany.getBusinessInfo());
    }
}
