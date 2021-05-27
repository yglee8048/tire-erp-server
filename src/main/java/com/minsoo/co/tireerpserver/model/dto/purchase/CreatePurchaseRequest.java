package com.minsoo.co.tireerpserver.model.dto.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseRequest {

    @Schema(name = "vendor_id", description = "vendor_id", example = "20019", required = true)
    @NotNull(message = "vendor_id 는 필수 값입니다.")
    private Long vendorId;

    @Schema(name = "purchase_date", description = "매입 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매입 일자는 필수 값입니다.")
    private LocalDate purchaseDate;

    @Schema(name = "contents", description = "매입 항목", required = true)
    @NotNull(message = "매입 항목은 필수 값입니다.")
    List<CreatePurchaseContentRequest> contents;
}
