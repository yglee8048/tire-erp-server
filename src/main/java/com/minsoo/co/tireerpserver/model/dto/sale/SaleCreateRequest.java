package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateRequest {

    @ApiModelProperty(value = "고객 ID", example = "2991", required = true)
    @NotNull(message = "고객 ID 는 필수 값입니다.")
    @JsonProperty("customer_id")
    private Long customerId;

    @ApiModelProperty(value = "매출 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매출 일자는 필수 값입니다.")
    @JsonProperty("sale_date")
    private LocalDate saleDate;

    @ApiModelProperty(value = "매출 내용", required = true)
    @NotNull(message = "매출 내용은 필수 값입니다.")
    @JsonProperty("contents")
    List<SaleCreateRequestContent> contents;
}
