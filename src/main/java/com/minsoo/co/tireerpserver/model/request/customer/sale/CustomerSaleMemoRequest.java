package com.minsoo.co.tireerpserver.model.request.customer.sale;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaleMemoRequest {

    @Schema(description = "내용", example = "3월 5일까지 긴급 출고 요망.")
    private String memo;
}
