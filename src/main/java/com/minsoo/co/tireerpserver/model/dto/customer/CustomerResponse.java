package com.minsoo.co.tireerpserver.model.dto.customer;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CustomerResponse {

    @ApiModelProperty(value = "ID", example = "2937")
    private Long customerId;

    @ApiModelProperty(value = "아이디", example = "user1234")
    private String userId;

    @ApiModelProperty(value = "이름", example = "피렐리 코리아")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @ApiModelProperty(value = "사업자 관련 정보")
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
