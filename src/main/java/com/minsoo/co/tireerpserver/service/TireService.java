package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.repository.TireRepository;
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

    private final TireRepository tireRepository;
    private final BrandService brandService;

    public List<Tire> findAll() {
        return tireRepository.findAll();
    }

    public Tire findById(Long id) {
        return tireRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Tire create(TireRequest createRequest) {
        Brand brand = brandService.findById(createRequest.getBrandId());
        return tireRepository.save(Tire.of(createRequest, brand));
    }

    @Transactional
    public Tire update(Long tireId, TireRequest updateRequest) {
        Brand brand = brandService.findById(updateRequest.getBrandId());
        Tire tire = this.findById(tireId);
        tire.update(updateRequest, brand);
        return tire;
    }

    @Transactional
    public void remove(Long id) {
        tireRepository.delete(this.findById(id));
    }
}
