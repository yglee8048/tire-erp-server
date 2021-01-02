package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminUpdateRequest {

    @NotEmpty
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_pw")
    private String userPw;

    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonProperty("role")
    private AdminRole role;
}
