package com.minsoo.co.tireerpserver.api.v1.model.dto.sale.delivery;

import com.minsoo.co.tireerpserver.api.v1.model.dto.general.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DeliveryResponse {

    @Schema(description = "배달 ID", example = "2991")
    private Long deliveryId;

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "수령인", example = "홍길동")
    private String recipient;

    @Schema(description = "수령인 주소")
    private AddressDTO recipientAddress;

    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhoneNumber;

    @Schema(description = "배달 업체", example = "CJ 대한통운")
    private String deliveryCompany;

    @Schema(description = "송장번호", example = "442394464783")
    private Integer invoiceNumber;
}
