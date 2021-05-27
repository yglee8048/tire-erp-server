package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorRequest {

    @Schema(name = "name", description = "이름", example = "피렐리 코리아", required = true)
    @NotEmpty(message = "매입처 이름은 필수 값입니다.")
    private String name;

    @Schema(name = "description", description = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(name = "business_info", description = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;
}
