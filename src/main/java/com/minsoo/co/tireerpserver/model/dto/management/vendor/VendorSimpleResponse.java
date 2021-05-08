package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.minsoo.co.tireerpserver.model.entity.Vendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class VendorSimpleResponse {

    @Schema(name = "매입처 ID", example = "2091")
    private Long vendorId;

    @Schema(name = "매입처 이름", example = "피렐리 코리아")
    private String name;

    private VendorSimpleResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
    }

    public static VendorSimpleResponse of(Vendor vendor) {
        return new VendorSimpleResponse(vendor);
    }
}
