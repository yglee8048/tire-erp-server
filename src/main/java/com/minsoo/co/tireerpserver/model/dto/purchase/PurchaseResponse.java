package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class PurchaseResponse {

    @ApiModelProperty(value = "ID", example = "20019")
    @JsonProperty("purchase_id")
    private Long purchaseId;

    @ApiModelProperty(value = "매입처")
    @JsonProperty("vendor")
    private VendorResponse vendor;

    @ApiModelProperty(value = "타이어 DOT")
    @JsonProperty("tire_dot")
    private TireDotResponse tireDot;

    @ApiModelProperty(value = "창고")
    @JsonProperty("warehouse")
    private WarehouseSimpleResponse warehouse;

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
