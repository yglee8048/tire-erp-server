package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.BRAND;
import static org.assertj.core.api.Assertions.*;

public class BrandServiceTest extends ServiceTest {

    /**
     * 브랜드 이름은 중복일 수 없다.
     */
    @Test
    @DisplayName("브랜드 생성")
    void create() {
        // GIVEN
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        BrandRequest duplicateRequest = BRAND("브랜드 테스트");

        // WHEN
        Brand brand = brandService.create(brandRequest);

        // THEN
        assertThat(brand.getName()).isEqualTo("브랜드 테스트");
        assertThatThrownBy(() -> brandService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }
}
