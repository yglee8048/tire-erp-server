package com.minsoo.co.tireerpserver.model.management.request;

import com.minsoo.co.tireerpserver.model.AddressDTO;
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
public class WarehouseRequest {

    @Schema(name = "name", description = "이름", example = "왕십리 메인 창고", required = true)
    @NotEmpty(message = "창고 이름은 필수 값입니다.")
    private String name;

    @Schema(name = "description", description = "설명", example = "메인 판매 타이어 보관")
    private String description;

    @Schema(name = "address", description = "주소")
    private AddressDTO address;
}
