package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.entity.management.Brand;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.BRAND;
import static org.assertj.core.api.Assertions.*;

public class BrandServiceTest extends ServiceTest {

    /**
     * 제조사 이름은 중복일 수 없다.
     */
    @Test
    @DisplayName("제조사 생성")
    void create() {
        // GIVEN
        BrandRequest brandRequest = BRAND("제조사 테스트");
        BrandRequest duplicateRequest = BRAND("제조사 테스트");

        // WHEN
        Brand brand = brandService.create(brandRequest);

        // THEN
        assertThat(brand.getName()).isEqualTo("제조사 테스트");
        assertThatThrownBy(() -> brandService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("제조사 수정")
    void update() {
        // GIVEN
        Brand brand1 = brandService.create(BRAND("테스트"));
        Brand brand2 = brandService.create(BRAND("테스트-2"));

        // WHEN
        Brand updated = brandService.update(brand1.getId(), BRAND("테스트-1"));
        BrandRequest duplicate = BRAND("테스트-1");

        // THEN
        assertThat(updated.getName()).isEqualTo("테스트-1");
        assertThatThrownBy(() -> brandService.update(brand2.getId(), duplicate))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("제조사 삭제")
    void removeById() {
        // GIVEN
        Brand brand = brandService.create(BRAND("제조사"));

        // WHEN
        brandService.removeById(brand.getId());

        // THEN
        assertThatThrownBy(() -> brandService.findById(brand.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
