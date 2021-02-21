package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class VendorSimpleResponse {

    @ApiModelProperty(value = "매입처 ID", example = "2091")
    @JsonProperty("vendor_id")
    private Long vendorId;

    @ApiModelProperty(value = "매입처 이름", example = "피렐리 코리아")
    @JsonProperty("name")
    private String name;

    private VendorSimpleResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
    }

    public static VendorSimpleResponse of(Vendor vendor) {
        return new VendorSimpleResponse(vendor);
    }
}
