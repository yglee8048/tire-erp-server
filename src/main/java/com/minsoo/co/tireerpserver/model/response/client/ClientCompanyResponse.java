package com.minsoo.co.tireerpserver.model.response.client;

import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ClientCompanyResponse {

    private Long clientCompanyId;
    private String name;
    private String description;
    private BusinessInfoDTO businessInfo;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public ClientCompanyResponse(ClientCompany clientCompany) {
        this.clientCompanyId = clientCompany.getId();
        this.name = clientCompany.getName();
        this.description = clientCompany.getDescription();
        this.businessInfo = new BusinessInfoDTO(clientCompany.getBusinessInfo());

        this.createdAt = clientCompany.getCreatedAt();
        this.lastModifiedAt = clientCompany.getLastModifiedAt();
        this.createdBy = clientCompany.getCreatedBy();
        this.lastModifiedBy = clientCompany.getLastModifiedBy();
    }
}
