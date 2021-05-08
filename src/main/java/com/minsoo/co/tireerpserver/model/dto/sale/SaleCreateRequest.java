package com.minsoo.co.tireerpserver.model.dto.sale;

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
public class SaleCreateRequest {

    @Schema(name = "고객 ID", example = "2991", required = true)
    @NotNull(message = "고객 ID 는 필수 값입니다.")
    private Long customerId;

    @Schema(name = "매출 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매출 일자는 필수 값입니다.")
    private LocalDate saleDate;

    @Schema(name = "매출 내용", required = true)
    @NotNull(message = "매출 내용은 필수 값입니다.")
    List<SaleCreateRequestContent> contents;
}
