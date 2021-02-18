package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class PurchaseRequest {

    @ApiModelProperty(value = "매입처 ID", example = "20019")
    @NotNull(message = "매입처 ID 는 필수 값입니다.")
    @JsonProperty("vendor_id")
    private Long vendorId;

    @ApiModelProperty(value = "타이어 ID", example = "20019")
    @NotNull(message = "타이어 ID 는 필수 값입니다.")
    @JsonProperty("tire_id")
    private Long tireId;

    @ApiModelProperty(value = "DOT", example = "1223")
    @NotNull(message = "DOT 는 필수 값입니다.")
    @JsonProperty("dot")
    private String dot;

    @ApiModelProperty(value = "창고 이름", example = "20019")
    @NotNull(message = "창고 이름은 필수 값입니다.")
    @JsonProperty("warehouse_name")
    private Long warehouseName;

    @ApiModelProperty(value = "매입 가격", example = "450000")
    @NotNull(message = "매입 가격은 필수 값입니다.")
    @Positive(message = "매입 가격은 양수여야 합니다.")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "매입 수량", example = "45")
    @NotNull(message = "매입 수량은 필수 값입니다.")
    @Positive(message = "매입 수량은 양수여야 합니다.")
    @JsonProperty("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "매입 일자", example = "2021-02-18")
    @NotNull(message = "매입 일자는 필수 값입니다.")
    @JsonProperty("purchased_date")
    private LocalDate purchasedDate;
}
