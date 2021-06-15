package com.minsoo.co.tireerpserver.user.model.client.company;

import com.minsoo.co.tireerpserver.shared.model.BusinessInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyRequest {

    @Schema(name = "name", description = "이름")
    @NotEmpty(message = "이름은 필수 값입니다.")
    private String name;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "business_info", description = "사업자 정보")
    BusinessInfoDTO businessInfo;
}
