package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.entity.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.tire.TireMemo;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TireMemoServiceTest extends ServiceTest {

    @Test
    @DisplayName("타이어 메모 생성")
    void create() {
        // GIVEN
        Brand brand = brandService.create(BRAND("브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("패턴"));
        Tire tire = tireService.create(TIRE("ID", pattern.getId(), 22));

        // WHEN
        TireMemo tireMemo = tireMemoService.create(tire.getId(), TIRE_MEMO("메모", false));

        // THEN
        assertThat(tireMemo.getMemo()).isEqualTo("메모");
        assertThat(tireMemo.getLock()).isEqualTo(false);
    }

    @Test
    @DisplayName("타이어 메모 수정")
    void update() {
        // GIVEN
        Brand brand = brandService.create(BRAND("브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("패턴"));
        Tire tire = tireService.create(TIRE("ID", pattern.getId(), 22));
        TireMemo tireMemo = tireMemoService.create(tire.getId(), TIRE_MEMO("메모", false));

        // WHEN
        TireMemo updated = tireMemoService.update(tire.getId(), tireMemo.getId(), TIRE_MEMO("변경", true));
        clear();

        // THEN
        assertThat(updated.getMemo()).isEqualTo("변경");
        assertThat(updated.getLock()).isEqualTo(true);
    }

    @Test
    @DisplayName("타이어 메모 삭제")
    void removeById() {
        // GIVEN
        Brand brand = brandService.create(BRAND("브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("패턴"));
        Tire tire = tireService.create(TIRE("ID", pattern.getId(), 22));
        TireMemo tireMemo = tireMemoService.create(tire.getId(), TIRE_MEMO("메모", false));

        // WHEN
        tireMemoService.removeById(tireMemo.getId());

        // THEN
        assertThatThrownBy(() -> tireMemoService.findById(tireMemo.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}