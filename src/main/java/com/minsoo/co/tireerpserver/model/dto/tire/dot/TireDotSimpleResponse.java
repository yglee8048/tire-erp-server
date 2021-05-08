package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.model.entity.TireDot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireDotSimpleResponse {

    @Schema(name = "타이어 DOT ID", example = "21")
    private Long tireDotId;

    @Schema(name = "타이어 ID", example = "21")
    private Long tireId;

    @Schema(name = "타이어 DOT", example = "4014")
    private String dot;

    private TireDotSimpleResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();
    }

    public static TireDotSimpleResponse of(TireDot tireDot) {
        return new TireDotSimpleResponse(tireDot);
    }
}
