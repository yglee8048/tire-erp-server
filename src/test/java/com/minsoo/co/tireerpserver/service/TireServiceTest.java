package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class TireServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TireServiceTest.class);

    @Autowired
    BrandService brandService;

    @Autowired
    TireService tireService;

    @Test
    @DisplayName("타이어 생성 & 조회 & 수정 & 삭제")
    void tireTest() {
        // 브랜드 생성
        log.info("브랜드 생성 테스트");
        BrandRequest brandCreateRequest = BrandRequest.builder()
                .name("테스트 브랜드")
                .description("21-02-20 테스트")
                .build();
        BrandResponse createdBrand = brandService.create(brandCreateRequest);

        assertThat(brandService.findById(createdBrand.getBrandId())).isEqualTo(createdBrand);
        log.info("브랜드 생성 완료");

        // 타이어 생성
        log.info("타이어 생성 테스트");
        TireRequest tireCreateRequest = TireRequest.builder()
                .brandId(createdBrand.getBrandId())
                .label("어슐런스")
                .width(165)
                .flatnessRatio(60)
                .inch(14)
                .loadIndex(79)
                .speedIndex("H")
                .season("겨울")
                .price(120000)
                .runFlat(true)
                .option(TireOption.SEAL)
                .oe("AO")
                .pattern("PZero")
                .productId("20210220A")
                .build();
        TireResponse createdTire = tireService.create(tireCreateRequest);
        assertThat(tireService.findById(createdTire.getTireId())).isEqualTo(createdTire);
        log.info("타이어 생성 완료");

        // 타이어 상품 ID 중복 생성
        log.info("타이어 상품 ID 중복 생성 테스트");
        TireRequest tireDuplicateCreateRequest = TireRequest.builder()
                .brandId(createdBrand.getBrandId())
                .label("어슐런스")
                .width(165)
                .flatnessRatio(60)
                .inch(14)
                .loadIndex(79)
                .speedIndex("H")
                .season("겨울")
                .price(120000)
                .runFlat(true)
                .option(TireOption.SEAL)
                .oe("AO")
                .pattern("PZero")
                .productId("20210220A")
                .build();
        assertThatThrownBy(() -> tireService.create(tireDuplicateCreateRequest))
                .isInstanceOf(AlreadyExistException.class);
        log.info("타이어 상품 ID 중복 생성 테스트 완료");

        // 타이어 수정
        log.info("타이어 수정 테스트");
        TireRequest tireUpdateRequest = TireRequest.builder()
                .brandId(createdBrand.getBrandId())
                .label("어슐런스")
                .width(165)
                .flatnessRatio(60)
                .inch(14)
                .loadIndex(80)  // 79 -> 80
                .speedIndex("H")
                .season("여름")   // 겨울 -> 여름
                .price(120000)
                .runFlat(true)
                .option(TireOption.SEAL)
                .oe("AO")
                .pattern("PZero")
                .productId("20210220A")
                .build();
        TireResponse updatedTire = tireService.update(createdTire.getTireId(), tireUpdateRequest);
        assertThat(createdTire.getLoadIndex()).isEqualTo(79);
        assertThat(updatedTire.getLoadIndex()).isEqualTo(80);
        assertThat(createdTire.getSeason()).isEqualTo("겨울");
        assertThat(updatedTire.getSeason()).isEqualTo("여름");
        log.info("타이어 수정 테스트 완료");

        // 타이어 삭제
        log.info("타이어 삭제 테스트");
        tireService.removeById(updatedTire.getTireId());
        assertThatThrownBy(() -> tireService.findById(updatedTire.getTireId()))
                .isInstanceOf(NotFoundException.class);
        log.info("타이어 삭제 테스트 완료");
    }
}