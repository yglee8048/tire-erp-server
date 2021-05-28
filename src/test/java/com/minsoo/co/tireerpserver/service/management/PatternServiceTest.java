package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.BRAND;
import static com.minsoo.co.tireerpserver.utils.RequestBuilder.PATTERN;
import static org.assertj.core.api.Assertions.*;

public class PatternServiceTest extends ServiceTest {

    /**
     * 패턴 이름은 중복일 수 없다.
     */
    @Test
    @DisplayName("패턴 생성")
    void create() {
        // GIVEN
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        PatternRequest createRequest = PATTERN("패턴 이름");
        PatternRequest duplicateRequest = PATTERN("패턴 이름");

        // WHEN
        Brand brand = brandService.create(brandRequest);
        Pattern pattern = patternService.create(brand.getId(), createRequest);

        // THEN
        assertThat(pattern.getName()).isEqualTo("패턴 이름");
        assertThatThrownBy(() -> patternService.create(brand.getId(), duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("패턴 수정")
    void update() {
        // GIVEN
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        PatternRequest createRequest = PATTERN("패턴 이름");
        PatternRequest updateRequest = PATTERN("패턴 이름 수정");

        // WHEN
        Brand brand = brandService.create(brandRequest);
        Pattern pattern = patternService.create(brand.getId(), createRequest);
        assertThat(pattern.getName()).isEqualTo("패턴 이름");
        Pattern updated = patternService.update(brand.getId(), pattern.getId(), updateRequest);

        // THEN
        assertThat(updated.getName()).isEqualTo("패턴 이름 수정");
    }

    @Test
    @DisplayName("패턴 삭제")
    void removeByIds() {
        // GIVEN
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        PatternRequest createRequest = PATTERN("패턴 이름");

        // WHEN
        Brand brand = brandService.create(brandRequest);
        Pattern pattern = patternService.create(brand.getId(), createRequest);
        assertThat(patternService.findById(brand.getId(), pattern.getId()))
                .isNotNull();
        patternService.removeById(brand.getId(), pattern.getId());

        // THEN
        assertThatThrownBy(() -> patternService.findById(brand.getId(), pattern.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
