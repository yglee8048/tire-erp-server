package com.minsoo.co.tireerpserver.user.model.customer;

import com.minsoo.co.tireerpserver.shared.model.BusinessInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @Schema(description = "아이디", example = "user1234", required = true)
    @NotEmpty(message = "아이디는 필수 값입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "password1234*", required = true)
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private String userPw;

    @Schema(description = "이름", example = "피렐리 코리아")
    private String name;

    @Schema(description = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(description = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;
}
