package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TireDotServiceTest extends ServiceTest {

    @Test
    @DisplayName("타이어 DOT 생성")
    void create() {
        // GIVEN
        Brand brand = brandService.create(BRAND("brand"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("pattern"));
        Tire tire = tireService.create(TIRE("id", pattern.getId(), 12));

        // WHEN
        TireDot tireDot = tireDotService.create(tire.getId(), TIRE_DOT("1111", 1000));
        TireDotRequest duplicate = TIRE_DOT("1111", 2000);

        // THEN
        assertThat(tireDot.getDot()).isEqualTo("1111");
        assertThat(tireDot.getRetailPrice()).isEqualTo(1000);
        assertThatThrownBy(() -> tireDotService.create(tire.getId(), duplicate))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("타이어 DOT 수정")
    void update() {
        // GIVEN
        Brand brand = brandService.create(BRAND("brand"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("pattern"));
        Tire tire = tireService.create(TIRE("id", pattern.getId(), 12));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 1000));
        TireDot tireDot02 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 1000));

        // WHEN
        TireDot updated = tireDotService.update(tire.getId(), tireDot01.getId(), TIRE_DOT("1112", 2000));
        TireDotRequest invalidRequest = TIRE_DOT("1112", 2000);
        clear();

        // THEN
        assertThat(updated.getDot()).isEqualTo("1112");
        assertThat(updated.getRetailPrice()).isEqualTo(2000);
        assertThatThrownBy(() -> tireDotService.update(tire.getId(), tireDot02.getId(), invalidRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("타이어 DOT 삭제")
    void removeById() {
        // GIVEN
        Brand brand = brandService.create(BRAND("brand"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("pattern"));
        Tire tire = tireService.create(TIRE("id", pattern.getId(), 12));
        TireDot tireDot = tireDotService.create(tire.getId(), TIRE_DOT("1111", 1000));

        // WHEN
        tireDotService.removeById(tireDot.getId());

        // THEN
        assertThatThrownBy(() -> tireDotService.findById(tireDot.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}