package com.minsoo.co.tireerpserver.api.v1.model.dto.account.customer;

import com.minsoo.co.tireerpserver.api.v1.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.services.account.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class CustomerResponse {

    @Schema(name = "customer_id", description = "customer_id")
    private Long customerId;

    @Schema(name = "account_id", description = "account_id")
    private Long accountId;

    @Schema(name = "name", description = "이름", example = "피렐리 코리아")
    private String name;

    @Schema(name = "description", description = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(name = "business_info", description = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;

    private CustomerResponse(Customer customer) {
        this.customerId = customer.getId();
        this.accountId = customer.getAccount().getId();
        this.name = customer.getName();
        this.description = customer.getDescription();
        this.businessInfo = BusinessInfoDTO.of(customer.getBusinessInfo());
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer);
    }
}
