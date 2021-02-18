package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseResponse {

    @ApiModelProperty(value = "매입 ID", example = "20019")
    @JsonProperty("purchase_id")
    private Long purchaseId;

    @ApiModelProperty(value = "타이어")
    @JsonProperty("tire")
    private TireResponse tire;

    @ApiModelProperty(value = "타이어 DOT ID", example = "20019")
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @ApiModelProperty(value = "DOT", example = "1223")
    @JsonProperty("dot")
    private String dot;

    @ApiModelProperty(value = "매입처")
    @JsonProperty("vendor")
    private VendorResponse vendor;

    @ApiModelProperty(value = "매입 가격", example = "450000")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "매입 수량", example = "45")
    @JsonProperty("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "매입 상태", example = "CONFIRMED")
    @JsonProperty("status")
    private PurchaseStatus status;

    @ApiModelProperty(value = "매입 일자", example = "2021-02-18")
    @JsonProperty("purchased_date")
    private LocalDate purchasedDate;
}
