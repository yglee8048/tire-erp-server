package com.minsoo.co.tireerpserver.tire.model.dot;

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
public class TireDotRequest {

    @Schema(name = "dot", description = "DOT")
    @NotEmpty(message = "dot 는 필수 값입니다.")
    private String dot;
}
