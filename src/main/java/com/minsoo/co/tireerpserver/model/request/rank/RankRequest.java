package com.minsoo.co.tireerpserver.model.request.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class RankRequest {

    @Schema(name = "name", description = "이름")
    @NotEmpty(message = "이름은 필수 값입니다.")
    private String name;
    
    @Schema(name = "description", description = "설명")
    private String description;
}
