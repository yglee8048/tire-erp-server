package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class TireServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TireServiceTest.class);

    @Autowired
    BrandService brandService;

    @Autowired
    TireService tireService;

    /**
     * 기본적인 타이어 CUD 테스트 (브랜드 생성 포함)
     * 타이어 상품 ID가 중복되는 경우, 예외가 발생해야 한다.
     */
    @Test
    @DisplayName("타이어 생성 & 조회 & 수정 & 삭제")
    void tireTest() {
        log.info("브랜드 생성 테스트");
        BrandResponse createdBrand = brandService.create(BRAND("테스트 브랜드"));

        assertThat(brandService.findById(createdBrand.getBrandId())).isEqualTo(createdBrand);

        log.info("타이어 생성 테스트");
        TireResponse createdTire = tireService.create(TIRE(createdBrand.getBrandId(), "PRODUCT_ID_01", "생성테스트"));
        assertThat(tireService.findById(createdTire.getTireId())).isEqualTo(createdTire);

        log.info("타이어 상품 ID 중복 생성 테스트");
        assertThatThrownBy(() -> tireService.create(TIRE(createdBrand.getBrandId(), "PRODUCT_ID_01", "중복테스트")))
                .isInstanceOf(AlreadyExistException.class);

        log.info("타이어 수정 테스트");
        TireResponse updatedTire = tireService.update(createdTire.getTireId(), TIRE(createdBrand.getBrandId(), "PRODUCT_ID_02", "수정테스트"));
        assertThat(createdTire.getProductId()).isEqualTo("PRODUCT_ID_01");
        assertThat(updatedTire.getProductId()).isEqualTo("PRODUCT_ID_02");
        assertThat(createdTire.getLabel()).isEqualTo("생성테스트");
        assertThat(updatedTire.getLabel()).isEqualTo("수정테스트");

        log.info("타이어 삭제 테스트");
        tireService.removeById(updatedTire.getTireId());
        assertThatThrownBy(() -> tireService.findById(updatedTire.getTireId()))
                .isInstanceOf(NotFoundException.class);
    }
}