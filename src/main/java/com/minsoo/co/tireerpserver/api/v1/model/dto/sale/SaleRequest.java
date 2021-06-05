package com.minsoo.co.tireerpserver.api.v1.model.dto.sale;

import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.content.SaleContentRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    @Schema(name = "customer_id", description = "customer_id")
    @NotNull(message = "customer_id 는 필수 값입니다.")
    private Long customerId;

    @Schema(name = "transaction_date", description = "거래 일자")
    @NotNull(message = "거래 일자는 필수 값입니다.")
    private LocalDate transactionDate;

    @Schema(name = "confirmed_date", description = "확정 일자")
    @NotNull(message = "확정 일자는 필수 값입니다.")
    private LocalDate confirmedDate;

    @Schema(name = "desired_delivery_date", description = "배송 희망 일자")
    private LocalDate desiredDeliveryDate;

    @Schema(name = "contents", description = "매출 항목")
    @NotNull(message = "매출 항목은 필수 값입니다.")
    List<SaleContentRequest> contents;
}
