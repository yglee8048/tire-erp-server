package com.minsoo.co.tireerpserver.model.tire.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireDotRequest {

    @Schema(name = "dot", description = "DOT")
    @NotEmpty(message = "dot 는 필수 값입니다.")
    private String dot;

    @Schema(name = "price", description = "가격")
    @NotNull(message = "가격은 필수 값입니다.")
    @Positive(message = "가격은 양수여야 합니다.")
    private Integer price;

}
