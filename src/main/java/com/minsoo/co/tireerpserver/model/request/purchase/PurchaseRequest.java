package com.minsoo.co.tireerpserver.model.request.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    @Schema(name = "vendor_id", description = "vendor_id", example = "20019", required = true)
    @NotNull(message = "vendor_id 는 필수 값입니다.")
    private Long vendorId;

    @Schema(name = "transaction_date", description = "매입 일자", example = "2021-02-18", required = true)
    @NotNull(message = "거래 일자는 필수 값입니다.")
    private LocalDate transactionDate;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "contents", description = "매입 항목", required = true)
    @NotNull(message = "매입 항목은 필수 값입니다.")
    List<PurchaseContentRequest> contents;
}
