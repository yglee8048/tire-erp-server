package com.minsoo.co.tireerpserver.account.model.admin;

import com.minsoo.co.tireerpserver.account.code.AdminRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    @Schema(description = "아이디", example = "user123", required = true)
    @NotEmpty(message = "관리자 계정  아이디는 필수 값입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "password1234*", required = true)
    @NotEmpty(message = "관리자 비밀번호는 필수 값입니다.")
    private String userPw;

    @Schema(description = "이름", example = "김철수")
    private String name;

    @Schema(description = "설명", example = "21년 3월 신규 채용")
    private String description;

    @Schema(description = "역할", example = "SUPER_ADMIN", required = true)
    @NotNull(message = "관리자 역할이 정상적으로 입력되지 않았습니다.")
    private AdminRole role;
}
