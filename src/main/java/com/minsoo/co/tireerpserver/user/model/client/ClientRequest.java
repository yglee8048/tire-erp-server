package com.minsoo.co.tireerpserver.user.model.client;

import com.minsoo.co.tireerpserver.shared.model.AddressDTO;
import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.code.ClientRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @Schema(name = "client_company_id")
    @NotNull(message = "고객사 ID 는 필수 값입니다.")
    private Long clientCompanyId;

    @Schema(name = "user_id", description = "계정 아이디")
    @NotEmpty(message = "계정 아이디는 필수 값입니다.")
    private String userId;

    @Schema(name = "role", description = "권한")
    private ClientRole role;

    @Schema(name = "nickname", description = "닉네임")
    private String nickname;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "name", description = "이름")
    private String name;

    @Schema(name = "email", description = "이메일")
    private String email;

    @Schema(name = "phone_number", description = "핸드폰 번호")
    private String phoneNumber;

    @Schema(name = "address", description = "주소")
    private AddressDTO address;
}
