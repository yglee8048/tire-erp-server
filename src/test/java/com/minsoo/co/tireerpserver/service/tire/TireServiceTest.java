package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.model.TireRequest;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

class TireServiceTest extends ServiceTest {

    /**
     * 타이어 ID가 중복되는 경우, 예외가 발생해야 한다.
     */
    @Test
    @DisplayName("타이어 생성")
    void create() {
        // GIVEN
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        TireRequest tireRequest = TIRE("PRODUCT_ID_01", pattern.getId(), 11);
        TireRequest duplicateRequest = TIRE("PRODUCT_ID_01", pattern.getId(), 11);

        // WHEN
        Tire tire = tireService.create(tireRequest);

        // THEN
        assertThat(tire.getTireIdentification()).isEqualTo("PRODUCT_ID_01");
        assertThatThrownBy(() -> tireService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("타이어 수정")
    void update() {
        // GIVEN
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));

        // WHEN
        Tire updated = tireService.update(tire.getId(), TIRE("PRODUCT_ID_02", pattern.getId(), 22));
        clear();

        // THEN
        assertThat(updated.getTireIdentification()).isEqualTo("PRODUCT_ID_02");
        assertThat(updated.getInch()).isEqualTo(22);
        assertThat(updated.getSize().endsWith("22")).isTrue();
    }

    @Test
    @DisplayName("타이어 삭제")
    void removeById() {
        // GIVEN
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));

        // WHEN
        tireService.removeById(tire.getId());

        // THEN
        assertThat(tireService.findAll().size()).isEqualTo(0);
        assertThatThrownBy(() -> tireService.findById(tire.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}