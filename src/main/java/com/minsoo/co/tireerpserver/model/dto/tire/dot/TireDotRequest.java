package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireDotRequest {

    @ApiModelProperty(value = "타이어 DOT", example = "4014")
    @NotNull(message = "dot 는 필수 값입니다.")
    @JsonProperty("dot")
    private String dot;
}
