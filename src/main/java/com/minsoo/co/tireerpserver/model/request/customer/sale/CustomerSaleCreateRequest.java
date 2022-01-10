package com.minsoo.co.tireerpserver.model.request.customer.sale;

import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
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
public class CustomerSaleCreateRequest {

    @Schema(name = "transaction_date", description = "거래 일자")
    @NotNull(message = "거래 일자는 필수 값입니다.")
    private LocalDate transactionDate;

    @Schema(name = "release_date", description = "출고 일자")
    private LocalDate releaseDate;

    @Schema(name = "desired_delivery_date", description = "배송 희망 일자")
    private LocalDate desiredDeliveryDate;

    @Schema(name = "delivery", description = "배송 정보")
    @NotNull(message = "배송 정보는 필수 값입니다.")
    private CustomerDeliveryRequest delivery;

    @Schema(name = "contents", description = "매출 항목")
    @NotNull(message = "매출 항목은 필수 값입니다.")
    List<CustomerSaleContentRequest> contents;

    @Schema(name = "memos", description = "매출 메모")
    List<CustomerSaleMemoRequest> memos;
}
