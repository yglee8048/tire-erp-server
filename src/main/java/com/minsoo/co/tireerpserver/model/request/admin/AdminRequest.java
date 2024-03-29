package com.minsoo.co.tireerpserver.model.request.admin;

import com.minsoo.co.tireerpserver.constant.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    @Schema(name = "user_id", description = "계정 아이디")
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;

    @Schema(name = "password", description = "계정 비밀번호")
    @NotEmpty(message = "계정 비밀번호는 필수 값입니다.")
    private String password;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "name", description = "이름")
    private String name;

    @Email
    @Schema(name = "email", description = "이메일")
    private String email;

    @Schema(name = "phone_number", description = "핸드폰 번호")
    private String phoneNumber;

    // TODO: custom validation 어노테이션 만들기
    @Schema(name = "role", description = "계정 권한")
    @NotNull(message = "계정 권한은 필수 값입니다.")
    private AccountRole role;
}
