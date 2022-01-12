package com.minsoo.co.tireerpserver.model.request.sale;

import com.minsoo.co.tireerpserver.constant.DeliveryOption;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerDeliveryRequest;
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
public class DeliveryRequest {

    @NotEmpty(message = "수취인 이름은 필수 값 입니다.")
    @Schema(name = "recipient_name", description = "수취인 이름")
    private String recipientName;

    @Valid
    @NotNull(message = "address 는 필수 값 입니다.")
    @Schema(name = "address", description = "배송 주소")
    private AddressDTO address;

    @NotEmpty(message = "수취인 전화 번호는 필수 값 입니다.")
    @Schema(name = "recipient_phone_number", description = "수취인 전화 번호")
    private String recipientPhoneNumber;

    @Schema(name = "delivery_option", description = "배송 방법")
    private DeliveryOption deliveryOption;

    @Schema(name = "delivery_company", description = "택배사")
    private String deliveryCompany;

    @Schema(name = "invoice_number", description = "송장 번호")
    private String invoiceNumber;

    public DeliveryRequest(CustomerDeliveryRequest customerDeliveryRequest) {
        this.recipientName = customerDeliveryRequest.getRecipientName();
        this.address = customerDeliveryRequest.getAddress();
        this.recipientPhoneNumber = customerDeliveryRequest.getRecipientPhoneNumber();
        this.deliveryOption = customerDeliveryRequest.getDeliveryOption();
        this.deliveryCompany = customerDeliveryRequest.getDeliveryCompany();
        this.invoiceNumber = null;
    }
}
