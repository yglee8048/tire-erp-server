package com.minsoo.co.tireerpserver.user.model.client;

import com.minsoo.co.tireerpserver.shared.model.AddressDTO;
import com.minsoo.co.tireerpserver.user.code.AccountRole;
import com.minsoo.co.tireerpserver.user.code.ClientRole;
import com.minsoo.co.tireerpserver.user.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientResponse {

    @Schema(name = "client_id")
    private Long clientId;

    @Schema(name = "client_company_id")
    private Long clientCompanyId;

    @Schema(name = "user_id", description = "계정 아이디")
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

    public ClientResponse(Client client) {
        this.clientId = client.getId();
        this.clientCompanyId = client.getClientCompany().getId();
        this.userId = client.getUsername();
        this.role = ClientRole.valueOf(client.getRole().name());
        this.nickname = client.getNickname();
        this.description = client.getDescription();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.address = AddressDTO.of(client.getAddress());
    }

    public static ClientResponse of(Client client) {
        return new ClientResponse(client);
    }
}
