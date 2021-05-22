package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    private String productId;

    private Long patternId;

    private boolean onSale;

    private Integer width;

    private Integer flatnessRatio;

    private Integer inch;

    private Integer loadIndex;

    private String speedIndex;

    private List<TireOption> options;

    private String oe;

    private String countryOfManufacture;

    private String originalVehicle;

    private String note;

    private String group;

    private String pr;

    private String lr;
}
