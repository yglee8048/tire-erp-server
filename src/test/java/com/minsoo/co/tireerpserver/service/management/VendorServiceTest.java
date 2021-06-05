package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.services.management.entity.Vendor;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.VENDOR;
import static org.assertj.core.api.Assertions.*;

public class VendorServiceTest extends ServiceTest {

    /**
     * 매입처 이름은 중복일 수 없다.
     */
    @Test
    @DisplayName("매입처 생성")
    void create() {
        // GIVEN
        VendorRequest vendorRequest = VENDOR("매입처 테스트");
        VendorRequest duplicateRequest = VENDOR("매입처 테스트");

        // WHEN
        Vendor vendor = vendorService.create(vendorRequest);

        // THEN
        assertThat(vendor.getName()).isEqualTo("매입처 테스트");
        assertThatThrownBy(() -> vendorService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("매입처 수정")
    void update() {
        // GIVEN
        Vendor vendor1 = vendorService.create(VENDOR("매입처"));
        Vendor vendor2 = vendorService.create(VENDOR("매입처-2"));

        // WHEN
        Vendor updated = vendorService.update(vendor1.getId(), VENDOR("매입처-1"));
        clear();

        // THEN
        assertThat(updated.getName()).isEqualTo("매입처-1");
        assertThatThrownBy(() -> vendorService.update(vendor2.getId(), VENDOR("매입처-1")))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("매입처 삭제")
    void removeById() {
        // GIVEN
        Vendor vendor = vendorService.create(VENDOR("매입처"));

        // WHEN
        vendorService.removeById(vendor.getId());

        // THEN
        assertThatThrownBy(() -> vendorService.findById(vendor.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
