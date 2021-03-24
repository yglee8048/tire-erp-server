package com.minsoo.co.tireerpserver.model.dto.sale.delivery;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DeliveryResponse {

    @ApiModelProperty(value = "배달 ID", example = "2991")
    private Long deliveryId;

    @ApiModelProperty(value = "매출 ID", example = "2991")
    private Long saleId;

    @ApiModelProperty(value = "수령인", example = "홍길동")
    private String recipient;

    @ApiModelProperty(value = "수령인 주소")
    private AddressDTO recipientAddress;

    @ApiModelProperty(value = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhoneNumber;

    @ApiModelProperty(value = "배달 업체", example = "CJ 대한통운")
    private String deliveryCompany;

    @ApiModelProperty(value = "송장번호", example = "442394464783")
    private Integer invoiceNumber;
}
