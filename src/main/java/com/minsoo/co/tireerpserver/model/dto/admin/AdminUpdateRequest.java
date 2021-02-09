package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminUpdateRequest {

    @NotEmpty(message = "admin 객체의 id 가 필요합니다.")
    @JsonProperty("id")
    private Long id;

    @NotEmpty(message = "유저 아이디는 필수 값입니다.")
    @JsonProperty("user_id")
    private String userId;

    @NotEmpty(message = "유저 비밀번호는 필수 값입니다.")
    private String userPw;

    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "역할이 정상적으로 입력되지 않았습니다.")
    @JsonProperty("role")
    private AdminRole role;
}
