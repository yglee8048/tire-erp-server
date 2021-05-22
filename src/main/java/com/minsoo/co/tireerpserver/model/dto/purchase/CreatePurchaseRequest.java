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

    @Schema(name = "매입처 ID", example = "20019", required = true)
    @NotNull(message = "매입처 ID 는 필수 값입니다.")
    private Long vendorId;

    @Schema(name = "매입 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매입 일자는 필수 값입니다.")
    private LocalDate purchaseDate;

    @Schema(name = "매입 내용", required = true)
    @NotNull(message = "매입 내용은 필수 값입니다.")
    List<CreatePurchaseContentRequest> contents;
}
