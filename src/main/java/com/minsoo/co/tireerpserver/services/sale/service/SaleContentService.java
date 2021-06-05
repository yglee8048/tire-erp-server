package com.minsoo.co.tireerpserver.services.sale.service;

import com.minsoo.co.tireerpserver.services.sale.repository.SaleContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleContentService {

    private final SaleContentRepository saleContentRepository;
}
