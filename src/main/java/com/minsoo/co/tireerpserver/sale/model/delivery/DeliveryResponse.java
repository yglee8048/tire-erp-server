package com.minsoo.co.tireerpserver.sale.model.delivery;

import com.minsoo.co.tireerpserver.sale.entity.Delivery;
import com.minsoo.co.tireerpserver.shared.model.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

    @Schema(description = "배달 ID", example = "2991")
    private Long deliveryId;

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "수령인 이름", example = "홍길동")
    private String recipientName;

    @Schema(description = "수령인 주소")
    private AddressDTO recipientAddress;

    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhoneNumber;

    @Schema(description = "배달 업체", example = "CJ 대한통운")
    private String deliveryCompany;

    @Schema(description = "송장번호", example = "442394464783")
    private Integer invoiceNumber;

    public static DeliveryResponse of(Delivery delivery) {
        return DeliveryResponse.builder()
                .deliveryId(delivery.getId())
                .saleId(delivery.getSale().getId())
                .recipientName(delivery.getRecipient().getName())
                .recipientAddress(AddressDTO.of(delivery.getRecipient().getAddress()))
                .recipientPhoneNumber(delivery.getRecipient().getPhoneNumber())
                .deliveryCompany(delivery.getDeliveryCompany())
                .invoiceNumber(delivery.getInvoiceNumber())
                .build();
    }
}
