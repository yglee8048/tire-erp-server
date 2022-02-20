package com.minsoo.co.tireerpserver.model.request.sale;

import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleMemoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleMemoRequest {

    @Schema(description = "내용", example = "3월 5일까지 긴급 출고 요망.")
    private String memo;

    @Schema(description = "잠금 여부 (true=비공개/false=공개)", example = "false")
    @NotNull(message = "잠금 여부는 필수 값입니다.")
    private boolean lock;

    public SaleMemoRequest(CustomerSaleMemoRequest customerSaleMemoRequest) {
        this.memo = customerSaleMemoRequest.getMemo();
        this.lock = false;
    }
}
