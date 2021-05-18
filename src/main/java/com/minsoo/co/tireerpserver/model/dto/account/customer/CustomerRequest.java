package com.minsoo.co.tireerpserver.model.dto.account.customer;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @Schema(name = "아이디", example = "user1234", required = true)
    @NotEmpty(message = "아이디는 필수 값입니다.")
    private String userId;

    @Schema(name = "비밀번호", example = "password1234*", required = true)
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private String userPw;

    @Schema(name = "이름", example = "피렐리 코리아")
    private String name;

    @Schema(name = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(name = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;
}
