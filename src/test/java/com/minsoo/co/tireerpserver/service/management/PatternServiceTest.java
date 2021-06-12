package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.management.model.brand.BrandRequest;
import com.minsoo.co.tireerpserver.management.model.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
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
        Brand brand = brandService.create(BRAND("브랜드 테스트"));
        Pattern pattern1 = patternService.create(brand.getId(), PATTERN("패턴 이름"));
        Pattern pattern2 = patternService.create(brand.getId(), PATTERN("패턴 이름-2"));
        assertThat(pattern1.getName()).isEqualTo("패턴 이름");

        // WHEN
        PatternRequest patternRequest = PATTERN("패턴 이름 수정");
        Pattern updated = patternService.update(brand.getId(), pattern1.getId(), patternRequest);
        clear();

        // THEN
        assertThat(updated.getName()).isEqualTo("패턴 이름 수정");
        assertThatThrownBy(() -> patternService.update(brand.getId(), pattern2.getId(), patternRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("패턴 삭제")
    void removeByIds() {
        // GIVEN
        Brand brand = brandService.create(BRAND("브랜드 테스트"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("패턴 이름"));
        assertThat(patternService.findById(brand.getId(), pattern.getId()))
                .isNotNull();

        // WHEN
        patternService.removeById(brand.getId(), pattern.getId());

        // THEN
        assertThatThrownBy(() -> patternService.findById(brand.getId(), pattern.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
