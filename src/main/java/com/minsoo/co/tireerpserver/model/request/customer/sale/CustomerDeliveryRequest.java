package com.minsoo.co.tireerpserver.model.request.customer.sale;

import com.minsoo.co.tireerpserver.constant.DeliveryOption;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDeliveryRequest {

    @NotEmpty(message = "수취인 이름은 필수 값입니다.")
    @Schema(name = "recipient_name", description = "수취인 이름")
    private String recipientName;

    @Valid
    @NotNull(message = "address 는 필수값입니다.")
    @Schema(name = "address", description = "배송 주소")
    private AddressDTO address;

    @NotEmpty(message = "수취인 전화번호는 필수 값입니다.")
    @Schema(name = "recipient_phone_number", description = "수취인 전화번호")
    private String recipientPhoneNumber;

    @Schema(name = "delivery_option", description = "배송방법")
    private DeliveryOption deliveryOption;

    @Schema(name = "delivery_company", description = "택배사")
    private String deliveryCompany;
}
