package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminCreateRequest {

    @NotNull(message = "유저 아이디는 필수 값입니다.")
    @JsonProperty("user_id")
    private String userId;

    @NotNull(message = "유저 비밀번호는 필수 값입니다.")
    @JsonProperty("user_pw")
    private String userPw;

    @JsonProperty("name")
    private String name;

    @NotNull(message = "역할이 정상적으로 입력되지 않았습니다.")
    @JsonProperty("role")
    private AdminRole role;

    @Builder
    public AdminCreateRequest(String userId, String userPw, String name, AdminRole role) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.role = role;
    }
}
