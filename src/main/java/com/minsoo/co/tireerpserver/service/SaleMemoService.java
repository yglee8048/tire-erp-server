package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.repository.SaleMemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleMemoService {

    private final SaleMemoRepository saleMemoRepository;
}
