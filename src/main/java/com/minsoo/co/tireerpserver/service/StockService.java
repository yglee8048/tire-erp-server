package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;


}
