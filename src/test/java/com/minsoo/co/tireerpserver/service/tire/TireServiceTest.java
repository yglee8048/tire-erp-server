package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

class TireServiceTest extends ServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    PatternService patternService;

    @Autowired
    TireService tireService;

    /**
     * 기본적인 타이어 CU 테스트 (브랜드 생성 포함)
     * 타이어 상품 ID가 중복되는 경우, 예외가 발생해야 한다.
     */
    @Test
    @DisplayName("타이어 생성")
    void create() {
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        clear();

        log.debug("타이어 생성 테스트");
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        clear();

        Tire found = tireService.findById(tire.getId());
        assertThat(found.getProductId()).isEqualTo("PRODUCT_ID_01");

        log.debug("타이어 상품 ID 중복 생성 테스트");
        assertThatThrownBy(() -> tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11)))
                .isInstanceOf(AlreadyExistException.class);
        clear();

        log.debug("타이어 수정 테스트");
        Tire updated = tireService.update(tire.getId(), TIRE("PRODUCT_ID_02", pattern.getId(), 22));

        assertThat(updated.getProductId()).isEqualTo("PRODUCT_ID_02");

        assertThat(tire.getSize()).isNotEqualTo(updated.getSize());
        assertThat(updated.getSize().endsWith("22")).isTrue();
    }

    /**
     * TODO: 타이어 삭제 테스트
     */
    @Test
    @DisplayName("타이어 삭제 테스트")
    void removeTireTest() {

    }
}