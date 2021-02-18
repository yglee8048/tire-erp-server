package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class VendorSimpleResponse {

    @ApiModelProperty(value = "매입처 ID", example = "2091")
    @JsonProperty("vendor_id")
    private Long vendorId;

    @ApiModelProperty(value = "매입처 이름", example = "피렐리 코리아")
    @JsonProperty("name")
    private String name;
}
