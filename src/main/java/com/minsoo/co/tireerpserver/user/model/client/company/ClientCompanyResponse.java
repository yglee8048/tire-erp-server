package com.minsoo.co.tireerpserver.user.model.client.company;

import com.minsoo.co.tireerpserver.shared.model.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientCompanyResponse {

    @Schema(name = "client_company_id")
    private Long clientCompanyId;

    @Schema(name = "name", description = "이름")
    private String name;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "business_info", description = "사업자 정보")
    BusinessInfoDTO businessInfo;

    public ClientCompanyResponse(ClientCompany clientCompany) {
        this.clientCompanyId = clientCompany.getId();
        this.name = clientCompany.getName();
        this.description = clientCompany.getDescription();
        this.businessInfo = BusinessInfoDTO.of(clientCompany.getBusinessInfo());
    }

    public static ClientCompanyResponse of(ClientCompany clientCompany) {
        return new ClientCompanyResponse(clientCompany);
    }
}
