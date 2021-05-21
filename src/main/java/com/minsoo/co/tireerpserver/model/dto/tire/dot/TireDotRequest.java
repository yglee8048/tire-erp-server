package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireDotRequest {

    private String dot;

    private Long retailPrice;
}
