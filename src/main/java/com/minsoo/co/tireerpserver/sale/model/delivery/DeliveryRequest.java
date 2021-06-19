package com.minsoo.co.tireerpserver.sale.model.delivery;

import com.minsoo.co.tireerpserver.shared.model.AddressDTO;
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
public class DeliveryRequest {

    @Schema(name = "recipient_name", description = "수령인", example = "홍길동")
    @NotEmpty(message = "수령인은 필수 값입니다.")
    private String recipientName;

    @Schema(name = "recipient_address", description = "수령인 주소")
    @NotNull(message = "수령인 주소는 필수 값입니다.")
    private AddressDTO recipientAddress;

    @Schema(name = "recipient_phone_number", description = "수령인 전화번호", example = "010-1234-5678")
    @NotEmpty(message = "수령인 전화번호는 필수 값입니다.")
    private String recipientPhoneNumber;

    @Schema(name = "delivery_company", description = "배달 업체", example = "CJ 대한통운")
    @NotEmpty(message = "배달 업체는 필수 값입니다.")
    private String deliveryCompany;

    @Schema(name = "invoice_number", description = "송장번호", example = "442394464783")
    @NotEmpty(message = "송장번호는 필수 값입니다.")
    private Integer invoiceNumber;
}
