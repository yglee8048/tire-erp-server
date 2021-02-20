package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.CanNotDeleteException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.repository.BrandRepository;
import com.minsoo.co.tireerpserver.repository.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireService {

    private final BrandRepository brandRepository;
    private final TireRepository tireRepository;
    private final PurchaseRepository purchaseRepository;
    private final StockRepository stockRepository;

    public List<TireResponse> findAll() {
        return tireRepository.findAll()
                .stream()
                .map(TireResponse::of)
                .collect(Collectors.toList());
    }

    public TireResponse findById(Long id) {
        return TireResponse.of(tireRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public TireResponse create(TireRequest createRequest) {
        if (tireRepository.existsByProductId(createRequest.getProductId())) {
            throw new AlreadyExistException("이미 존재하는 상품 ID 입니다.");
        }
        Brand brand = brandRepository.findById(createRequest.getBrandId()).orElseThrow(NotFoundException::new);
        return TireResponse.of(tireRepository.save(Tire.of(createRequest, brand)));
    }

    @Transactional
    public TireResponse update(Long id, TireRequest updateRequest) {
        Brand brand = brandRepository.findById(updateRequest.getBrandId()).orElseThrow(NotFoundException::new);
        Tire tire = tireRepository.findById(id).orElseThrow(NotFoundException::new);
        tire.update(updateRequest, brand);
        return TireResponse.of(tire);
    }

    @Transactional
    public void removeById(Long id) {
        Tire tire = tireRepository.findById(id).orElseThrow(NotFoundException::new);
        // validate
        List<TireDot> tireDots = new ArrayList<>(tire.getTireDots());
        // 재고가 남아있으면 삭제할 수 없다.
        if (stockRepository.existsByTireDotIn(tireDots) && stockRepository.sumQuantityByTireDots(tireDots) > 0) {
            throw new CanNotDeleteException("해당 타이어에 대한 재고가 남아있어 삭제할 수 없습니다.");
        }
        // 관련된 매입 내역을 삭제한다.
        purchaseRepository.deleteAllByTireDotIn(tireDots);
        // 타이어 삭제
        //TODO: dot 와 memo 를 bulk 삭제하자.
        tireRepository.delete(tire);
    }
}
