package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireService {

    private final BrandRepository brandRepository;
    private final TireRepository tireRepository;

    public List<Tire> findAll() {
        return tireRepository.findAllFetchBrand();
    }

    public Tire findById(Long id) {
        return tireRepository.findOneFetchBrandById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Tire create(TireRequest createRequest) {
        if (tireRepository.existsByProductId(createRequest.getProductId())) {
            throw new AlreadyExistException("이미 존재하는 상품 ID 입니다.");
        }
        Brand brand = brandRepository.findById(createRequest.getBrandId()).orElseThrow(() -> new NotFoundException("제조사", createRequest.getBrandId()));

        return tireRepository.save(Tire.of(createRequest, brand));
    }

    @Transactional
    public void update(Long id, TireRequest updateRequest) {
        Brand brand = brandRepository.findById(updateRequest.getBrandId()).orElseThrow(() -> new NotFoundException("제조사", id));
        Tire tire = tireRepository.findById(id).orElseThrow(() -> new NotFoundException("타이어", id));

        tire.update(updateRequest, brand);
    }
}
