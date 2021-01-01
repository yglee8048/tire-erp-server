package com.minsoo.co.tireerpserver.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import lombok.Data;

@Data
public class AdminResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_pw")
    private String userPw;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private AdminRole role;
}
