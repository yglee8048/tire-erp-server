package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.repository.query.StockQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireQueryService {

    private final StockQueryRepositoryImpl stockQueryRepositoryImpl;
}
