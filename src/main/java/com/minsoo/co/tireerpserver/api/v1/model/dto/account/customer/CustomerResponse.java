package com.minsoo.co.tireerpserver.api.v1.model.dto.account.customer;

import com.minsoo.co.tireerpserver.api.v1.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.services.account.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class CustomerResponse {

    @Schema(description = "ID", example = "2937")
    private Long customerId;

    @Schema(description = "아이디", example = "user1234")
    private String userId;

    @Schema(description = "이름", example = "피렐리 코리아")
    private String name;

    @Schema(description = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(description = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;

    private CustomerResponse(Customer customer) {
        this.customerId = customer.getId();
        this.userId = customer.getUserId();
        this.name = customer.getName();
        this.description = customer.getDescription();
        this.businessInfo = BusinessInfoDTO.of(customer.getBusinessInfo());
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer);
    }
}
