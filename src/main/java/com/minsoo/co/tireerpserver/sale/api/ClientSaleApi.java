package com.minsoo.co.tireerpserver.sale.api;

import com.minsoo.co.tireerpserver.sale.service.SaleService;
import com.minsoo.co.tireerpserver.user.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/clients/api/v1")
@RequiredArgsConstructor
public class ClientSaleApi {

    private final SaleService saleService;
}
