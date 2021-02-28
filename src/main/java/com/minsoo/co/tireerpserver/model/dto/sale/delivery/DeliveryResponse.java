package com.minsoo.co.tireerpserver.model.dto.sale.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DeliveryResponse {

    @JsonProperty("delivery_id")
    private Long deliveryId;

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("recipient_address")
    private AddressDTO recipientAddress;

    @JsonProperty("recipient_phone_number")
    private String recipientPhoneNumber;

    @JsonProperty("delivery_company")
    private String deliveryCompany;

    @JsonProperty("invoice_number")
    private Integer invoiceNumber;
}
