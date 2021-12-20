package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireDotResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    public TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();

        this.createdAt = tireDot.getCreatedAt();
        this.lastModifiedAt = tireDot.getLastModifiedAt();
        this.createdBy = tireDot.getCreatedBy();
        this.lastModifiedBy = tireDot.getLastModifiedBy();
    }
}
