package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    @ApiModelProperty(value = "아이디", example = "user123")
    @NotNull(message = "관리자 계정  아이디는 필수 값입니다.")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty(value = "비밀번호", example = "password1234*")
    @NotNull(message = "관리자 비밀번호는 필수 값입니다.")
    @JsonProperty("user_pw")
    private String userPw;

    @ApiModelProperty(value = "이름", example = "김철수")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "21년 3월 신규 채용")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "역할", example = "SUPER_ADMIN")
    @NotNull(message = "관리자 역할이 정상적으로 입력되지 않았습니다.")
    @JsonProperty("role")
    private AdminRole role;
}
