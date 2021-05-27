package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

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

    @Schema(name = "capacity", description = "용량", example = "20000")
    @Positive(message = "창고 용량은 양수여야 합니다.")
    private Integer capacity;

    @Schema(name = "address", description = "주소")
    private AddressDTO address;
}
