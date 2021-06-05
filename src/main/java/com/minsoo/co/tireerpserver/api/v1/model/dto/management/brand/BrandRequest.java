package com.minsoo.co.tireerpserver.api.v1.model.dto.management.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {

    @Schema(name = "name", description = "이름", example = "금호타이어")
    @NotEmpty(message = "제조사 이름은 필수 값입니다.")
    private String name;

    @Schema(name = "description", description = "설명", example = "20년 5월부터 계약 시작")
    private String description;
}