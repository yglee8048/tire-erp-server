package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireSimpleResponse {

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "pattern", description = "패턴 정보")
    private PatternSimpleResponse pattern;

    @Schema(name = "tire_identification", description = "타이어 ID")
    private String tireIdentification;

    @Schema(name = "on_sale", description = "판매상태")
    private Boolean onSale;

    @Schema(name = "size", description = "표기형식")
    private String size;

    @Schema(name = "load_index", description = "하중지수")
    private Integer loadIndex;

    @Schema(name = "speed_index", description = "속도지수")
    private String speedIndex;

    public TireSimpleResponse(Tire tire) {
        this.tireId = tire.getId();
        this.pattern = PatternSimpleResponse.of(tire.getPattern());
        this.onSale = tire.getOnSale();
        this.tireIdentification = tire.getTireIdentification();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
    }

    public static TireSimpleResponse of(Tire tire) {
        return new TireSimpleResponse(tire);
    }
}
