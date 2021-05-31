package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireDotRequest {

    @Schema(name = "dot", description = "DOT")
    @NotEmpty(message = "dot 는 필수 값입니다.")
    private String dot;

    @Schema(name = "retail_price", description = "소비자금액")
    private Integer retailPrice;
}
