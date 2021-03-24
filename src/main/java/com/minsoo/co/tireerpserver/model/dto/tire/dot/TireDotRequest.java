package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireDotRequest {

    @ApiModelProperty(value = "타이어 DOT", example = "4014", required = true)
    @NotEmpty(message = "dot 는 필수 값입니다.")
    private String dot;
}
