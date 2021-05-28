package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
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
}
