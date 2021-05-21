//package com.minsoo.co.tireerpserver.service;
//
//import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
//import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
//import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
//import com.minsoo.co.tireerpserver.service.management.BrandService;
//import com.minsoo.co.tireerpserver.service.tire.TireService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
//import static org.assertj.core.api.Assertions.*;
//
//class TireServiceTest extends ServiceTest {
//
//    @Autowired
//    BrandService brandService;
//
//    @Autowired
//    TireService tireService;
//
//    /**
//     * 기본적인 타이어 CU 테스트 (브랜드 생성 포함)
//     * 타이어 상품 ID가 중복되는 경우, 예외가 발생해야 한다.
//     */
//    @Test
//    @DisplayName("타이어 생성 & 조회 & 수정 & 삭제")
//    void tireTest() {
//        log.info("브랜드 생성 테스트");
//        Brand createdBrand = brandService.create(BRAND("테스트 브랜드"));
//        clear();
//
//        assertThat(brandService.findById(createdBrand.getId())).isEqualTo(createdBrand);
//        clear();
//
//        log.info("타이어 생성 테스트");
//        TireResponse createdTire = tireService.create(TIRE(createdBrand.getId(), "PRODUCT_ID_01", 11, "생성테스트"));
//        clear();
//
//        assertThat(tireService.findById(createdTire.getTireId())).isEqualTo(createdTire);
//        clear();
//
//        log.info("타이어 상품 ID 중복 생성 테스트");
//        assertThatThrownBy(() -> tireService.create(TIRE(createdBrand.getId(), "PRODUCT_ID_01", 11, "중복테스트")))
//                .isInstanceOf(AlreadyExistException.class);
//        clear();
//
//        log.info("타이어 수정 테스트");
//        TireResponse updatedTire = tireService.update(createdTire.getTireId(), TIRE(createdBrand.getId(), "PRODUCT_ID_02", 11, "수정테스트"));
//        clear();
//
//        assertThat(createdTire.getProductId()).isEqualTo("PRODUCT_ID_01");
//        assertThat(updatedTire.getProductId()).isEqualTo("PRODUCT_ID_02");
//        assertThat(createdTire.getPattern()).isEqualTo("생성테스트");
//        assertThat(updatedTire.getPattern()).isEqualTo("수정테스트");
//    }
//}